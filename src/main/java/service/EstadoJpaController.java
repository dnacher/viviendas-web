/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Estado;
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
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws RollbackFailureException, Exception {
        if (estado.getTrabajoList() == null) {
            estado.setTrabajoList(new ArrayList<Trabajo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trabajo> attachedTrabajoList = new ArrayList<Trabajo>();
            for (Trabajo trabajoListTrabajoToAttach : estado.getTrabajoList()) {
                trabajoListTrabajoToAttach = em.getReference(trabajoListTrabajoToAttach.getClass(), trabajoListTrabajoToAttach.getIdTrabajo());
                attachedTrabajoList.add(trabajoListTrabajoToAttach);
            }
            estado.setTrabajoList(attachedTrabajoList);
            em.persist(estado);
            for (Trabajo trabajoListTrabajo : estado.getTrabajoList()) {
                Estado oldEstadoIdestadoOfTrabajoListTrabajo = trabajoListTrabajo.getEstadoIdestado();
                trabajoListTrabajo.setEstadoIdestado(estado);
                trabajoListTrabajo = em.merge(trabajoListTrabajo);
                if (oldEstadoIdestadoOfTrabajoListTrabajo != null) {
                    oldEstadoIdestadoOfTrabajoListTrabajo.getTrabajoList().remove(trabajoListTrabajo);
                    oldEstadoIdestadoOfTrabajoListTrabajo = em.merge(oldEstadoIdestadoOfTrabajoListTrabajo);
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

    public void edit(Estado estado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estado persistentEstado = em.find(Estado.class, estado.getIdestado());
            List<Trabajo> trabajoListOld = persistentEstado.getTrabajoList();
            List<Trabajo> trabajoListNew = estado.getTrabajoList();
            List<String> illegalOrphanMessages = null;
            for (Trabajo trabajoListOldTrabajo : trabajoListOld) {
                if (!trabajoListNew.contains(trabajoListOldTrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trabajo " + trabajoListOldTrabajo + " since its estadoIdestado field is not nullable.");
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
            estado.setTrabajoList(trabajoListNew);
            estado = em.merge(estado);
            for (Trabajo trabajoListNewTrabajo : trabajoListNew) {
                if (!trabajoListOld.contains(trabajoListNewTrabajo)) {
                    Estado oldEstadoIdestadoOfTrabajoListNewTrabajo = trabajoListNewTrabajo.getEstadoIdestado();
                    trabajoListNewTrabajo.setEstadoIdestado(estado);
                    trabajoListNewTrabajo = em.merge(trabajoListNewTrabajo);
                    if (oldEstadoIdestadoOfTrabajoListNewTrabajo != null && !oldEstadoIdestadoOfTrabajoListNewTrabajo.equals(estado)) {
                        oldEstadoIdestadoOfTrabajoListNewTrabajo.getTrabajoList().remove(trabajoListNewTrabajo);
                        oldEstadoIdestadoOfTrabajoListNewTrabajo = em.merge(oldEstadoIdestadoOfTrabajoListNewTrabajo);
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
                Integer id = estado.getIdestado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getIdestado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trabajo> trabajoListOrphanCheck = estado.getTrabajoList();
            for (Trabajo trabajoListOrphanCheckTrabajo : trabajoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estado (" + estado + ") cannot be destroyed since the Trabajo " + trabajoListOrphanCheckTrabajo + " in its trabajoList field has a non-nullable estadoIdestado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estado);
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

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
