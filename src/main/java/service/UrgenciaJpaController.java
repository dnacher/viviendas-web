/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Trabajo;
import entities.Urgencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import service.exceptions.IllegalOrphanException;
import service.exceptions.NonexistentEntityException;
import service.exceptions.RollbackFailureException;

/**
 *
 * @author danielnacher
 */
public class UrgenciaJpaController implements Serializable {

    public UrgenciaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Urgencia urgencia) throws RollbackFailureException, Exception {
        if (urgencia.getTrabajoList() == null) {
            urgencia.setTrabajoList(new ArrayList<Trabajo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trabajo> attachedTrabajoList = new ArrayList<Trabajo>();
            for (Trabajo trabajoListTrabajoToAttach : urgencia.getTrabajoList()) {
                trabajoListTrabajoToAttach = em.getReference(trabajoListTrabajoToAttach.getClass(), trabajoListTrabajoToAttach.getIdTrabajo());
                attachedTrabajoList.add(trabajoListTrabajoToAttach);
            }
            urgencia.setTrabajoList(attachedTrabajoList);
            em.persist(urgencia);
            for (Trabajo trabajoListTrabajo : urgencia.getTrabajoList()) {
                Urgencia oldUrgenciaIdurgenciaOfTrabajoListTrabajo = trabajoListTrabajo.getUrgenciaIdurgencia();
                trabajoListTrabajo.setUrgenciaIdurgencia(urgencia);
                trabajoListTrabajo = em.merge(trabajoListTrabajo);
                if (oldUrgenciaIdurgenciaOfTrabajoListTrabajo != null) {
                    oldUrgenciaIdurgenciaOfTrabajoListTrabajo.getTrabajoList().remove(trabajoListTrabajo);
                    oldUrgenciaIdurgenciaOfTrabajoListTrabajo = em.merge(oldUrgenciaIdurgenciaOfTrabajoListTrabajo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Urgencia urgencia) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Urgencia persistentUrgencia = em.find(Urgencia.class, urgencia.getIdurgencia());
            List<Trabajo> trabajoListOld = persistentUrgencia.getTrabajoList();
            List<Trabajo> trabajoListNew = urgencia.getTrabajoList();
            List<String> illegalOrphanMessages = null;
            for (Trabajo trabajoListOldTrabajo : trabajoListOld) {
                if (!trabajoListNew.contains(trabajoListOldTrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trabajo " + trabajoListOldTrabajo + " since its urgenciaIdurgencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Trabajo> attachedTrabajoListNew = new ArrayList<Trabajo>();
            for (Trabajo trabajoListNewTrabajoToAttach : trabajoListNew) {
                trabajoListNewTrabajoToAttach = em.getReference(trabajoListNewTrabajoToAttach.getClass(), trabajoListNewTrabajoToAttach.getIdTrabajo());
                attachedTrabajoListNew.add(trabajoListNewTrabajoToAttach);
            }
            trabajoListNew = attachedTrabajoListNew;
            urgencia.setTrabajoList(trabajoListNew);
            urgencia = em.merge(urgencia);
            for (Trabajo trabajoListNewTrabajo : trabajoListNew) {
                if (!trabajoListOld.contains(trabajoListNewTrabajo)) {
                    Urgencia oldUrgenciaIdurgenciaOfTrabajoListNewTrabajo = trabajoListNewTrabajo.getUrgenciaIdurgencia();
                    trabajoListNewTrabajo.setUrgenciaIdurgencia(urgencia);
                    trabajoListNewTrabajo = em.merge(trabajoListNewTrabajo);
                    if (oldUrgenciaIdurgenciaOfTrabajoListNewTrabajo != null && !oldUrgenciaIdurgenciaOfTrabajoListNewTrabajo.equals(urgencia)) {
                        oldUrgenciaIdurgenciaOfTrabajoListNewTrabajo.getTrabajoList().remove(trabajoListNewTrabajo);
                        oldUrgenciaIdurgenciaOfTrabajoListNewTrabajo = em.merge(oldUrgenciaIdurgenciaOfTrabajoListNewTrabajo);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = urgencia.getIdurgencia();
                if (findUrgencia(id) == null) {
                    throw new NonexistentEntityException("The urgencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Urgencia urgencia;
            try {
                urgencia = em.getReference(Urgencia.class, id);
                urgencia.getIdurgencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The urgencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trabajo> trabajoListOrphanCheck = urgencia.getTrabajoList();
            for (Trabajo trabajoListOrphanCheckTrabajo : trabajoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Urgencia (" + urgencia + ") cannot be destroyed since the Trabajo " + trabajoListOrphanCheckTrabajo + " in its trabajoList field has a non-nullable urgenciaIdurgencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(urgencia);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Urgencia> findUrgenciaEntities() {
        return findUrgenciaEntities(true, -1, -1);
    }

    public List<Urgencia> findUrgenciaEntities(int maxResults, int firstResult) {
        return findUrgenciaEntities(false, maxResults, firstResult);
    }

    private List<Urgencia> findUrgenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Urgencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Urgencia findUrgencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Urgencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getUrgenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Urgencia> rt = cq.from(Urgencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
