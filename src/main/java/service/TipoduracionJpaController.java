/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Tipoduracion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Trabajo;
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
public class TipoduracionJpaController implements Serializable {

    public TipoduracionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipoduracion tipoduracion) throws RollbackFailureException, Exception {
        if (tipoduracion.getTrabajoList() == null) {
            tipoduracion.setTrabajoList(new ArrayList<Trabajo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trabajo> attachedTrabajoList = new ArrayList<Trabajo>();
            for (Trabajo trabajoListTrabajoToAttach : tipoduracion.getTrabajoList()) {
                trabajoListTrabajoToAttach = em.getReference(trabajoListTrabajoToAttach.getClass(), trabajoListTrabajoToAttach.getIdTrabajo());
                attachedTrabajoList.add(trabajoListTrabajoToAttach);
            }
            tipoduracion.setTrabajoList(attachedTrabajoList);
            em.persist(tipoduracion);
            for (Trabajo trabajoListTrabajo : tipoduracion.getTrabajoList()) {
                Tipoduracion oldTipoDuracionidtipoDuracionOfTrabajoListTrabajo = trabajoListTrabajo.getTipoDuracionidtipoDuracion();
                trabajoListTrabajo.setTipoDuracionidtipoDuracion(tipoduracion);
                trabajoListTrabajo = em.merge(trabajoListTrabajo);
                if (oldTipoDuracionidtipoDuracionOfTrabajoListTrabajo != null) {
                    oldTipoDuracionidtipoDuracionOfTrabajoListTrabajo.getTrabajoList().remove(trabajoListTrabajo);
                    oldTipoDuracionidtipoDuracionOfTrabajoListTrabajo = em.merge(oldTipoDuracionidtipoDuracionOfTrabajoListTrabajo);
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

    public void edit(Tipoduracion tipoduracion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipoduracion persistentTipoduracion = em.find(Tipoduracion.class, tipoduracion.getIdtipoDuracion());
            List<Trabajo> trabajoListOld = persistentTipoduracion.getTrabajoList();
            List<Trabajo> trabajoListNew = tipoduracion.getTrabajoList();
            List<String> illegalOrphanMessages = null;
            for (Trabajo trabajoListOldTrabajo : trabajoListOld) {
                if (!trabajoListNew.contains(trabajoListOldTrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trabajo " + trabajoListOldTrabajo + " since its tipoDuracionidtipoDuracion field is not nullable.");
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
            tipoduracion.setTrabajoList(trabajoListNew);
            tipoduracion = em.merge(tipoduracion);
            for (Trabajo trabajoListNewTrabajo : trabajoListNew) {
                if (!trabajoListOld.contains(trabajoListNewTrabajo)) {
                    Tipoduracion oldTipoDuracionidtipoDuracionOfTrabajoListNewTrabajo = trabajoListNewTrabajo.getTipoDuracionidtipoDuracion();
                    trabajoListNewTrabajo.setTipoDuracionidtipoDuracion(tipoduracion);
                    trabajoListNewTrabajo = em.merge(trabajoListNewTrabajo);
                    if (oldTipoDuracionidtipoDuracionOfTrabajoListNewTrabajo != null && !oldTipoDuracionidtipoDuracionOfTrabajoListNewTrabajo.equals(tipoduracion)) {
                        oldTipoDuracionidtipoDuracionOfTrabajoListNewTrabajo.getTrabajoList().remove(trabajoListNewTrabajo);
                        oldTipoDuracionidtipoDuracionOfTrabajoListNewTrabajo = em.merge(oldTipoDuracionidtipoDuracionOfTrabajoListNewTrabajo);
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
                Integer id = tipoduracion.getIdtipoDuracion();
                if (findTipoduracion(id) == null) {
                    throw new NonexistentEntityException("The tipoduracion with id " + id + " no longer exists.");
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
            Tipoduracion tipoduracion;
            try {
                tipoduracion = em.getReference(Tipoduracion.class, id);
                tipoduracion.getIdtipoDuracion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoduracion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trabajo> trabajoListOrphanCheck = tipoduracion.getTrabajoList();
            for (Trabajo trabajoListOrphanCheckTrabajo : trabajoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipoduracion (" + tipoduracion + ") cannot be destroyed since the Trabajo " + trabajoListOrphanCheckTrabajo + " in its trabajoList field has a non-nullable tipoDuracionidtipoDuracion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoduracion);
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

    public List<Tipoduracion> findTipoduracionEntities() {
        return findTipoduracionEntities(true, -1, -1);
    }

    public List<Tipoduracion> findTipoduracionEntities(int maxResults, int firstResult) {
        return findTipoduracionEntities(false, maxResults, firstResult);
    }

    private List<Tipoduracion> findTipoduracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipoduracion.class));
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

    public Tipoduracion findTipoduracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipoduracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoduracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipoduracion> rt = cq.from(Tipoduracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
