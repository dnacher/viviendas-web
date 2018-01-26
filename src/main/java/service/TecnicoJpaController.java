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
import entities.Historialtrabajo;
import entities.Tecnico;
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
public class TecnicoJpaController implements Serializable {

    public TecnicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tecnico tecnico) throws RollbackFailureException, Exception {
        if (tecnico.getHistorialtrabajoList() == null) {
            tecnico.setHistorialtrabajoList(new ArrayList<Historialtrabajo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Historialtrabajo> attachedHistorialtrabajoList = new ArrayList<Historialtrabajo>();
            for (Historialtrabajo historialtrabajoListHistorialtrabajoToAttach : tecnico.getHistorialtrabajoList()) {
                historialtrabajoListHistorialtrabajoToAttach = em.getReference(historialtrabajoListHistorialtrabajoToAttach.getClass(), historialtrabajoListHistorialtrabajoToAttach.getHistorialtrabajoPK());
                attachedHistorialtrabajoList.add(historialtrabajoListHistorialtrabajoToAttach);
            }
            tecnico.setHistorialtrabajoList(attachedHistorialtrabajoList);
            em.persist(tecnico);
            for (Historialtrabajo historialtrabajoListHistorialtrabajo : tecnico.getHistorialtrabajoList()) {
                Tecnico oldTecnicoidTrabajadorOfHistorialtrabajoListHistorialtrabajo = historialtrabajoListHistorialtrabajo.getTecnicoidTrabajador();
                historialtrabajoListHistorialtrabajo.setTecnicoidTrabajador(tecnico);
                historialtrabajoListHistorialtrabajo = em.merge(historialtrabajoListHistorialtrabajo);
                if (oldTecnicoidTrabajadorOfHistorialtrabajoListHistorialtrabajo != null) {
                    oldTecnicoidTrabajadorOfHistorialtrabajoListHistorialtrabajo.getHistorialtrabajoList().remove(historialtrabajoListHistorialtrabajo);
                    oldTecnicoidTrabajadorOfHistorialtrabajoListHistorialtrabajo = em.merge(oldTecnicoidTrabajadorOfHistorialtrabajoListHistorialtrabajo);
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

    public void edit(Tecnico tecnico) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tecnico persistentTecnico = em.find(Tecnico.class, tecnico.getIdTecnico());
            List<Historialtrabajo> historialtrabajoListOld = persistentTecnico.getHistorialtrabajoList();
            List<Historialtrabajo> historialtrabajoListNew = tecnico.getHistorialtrabajoList();
            List<String> illegalOrphanMessages = null;
            for (Historialtrabajo historialtrabajoListOldHistorialtrabajo : historialtrabajoListOld) {
                if (!historialtrabajoListNew.contains(historialtrabajoListOldHistorialtrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialtrabajo " + historialtrabajoListOldHistorialtrabajo + " since its tecnicoidTrabajador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Historialtrabajo> attachedHistorialtrabajoListNew = new ArrayList<Historialtrabajo>();
            for (Historialtrabajo historialtrabajoListNewHistorialtrabajoToAttach : historialtrabajoListNew) {
                historialtrabajoListNewHistorialtrabajoToAttach = em.getReference(historialtrabajoListNewHistorialtrabajoToAttach.getClass(), historialtrabajoListNewHistorialtrabajoToAttach.getHistorialtrabajoPK());
                attachedHistorialtrabajoListNew.add(historialtrabajoListNewHistorialtrabajoToAttach);
            }
            historialtrabajoListNew = attachedHistorialtrabajoListNew;
            tecnico.setHistorialtrabajoList(historialtrabajoListNew);
            tecnico = em.merge(tecnico);
            for (Historialtrabajo historialtrabajoListNewHistorialtrabajo : historialtrabajoListNew) {
                if (!historialtrabajoListOld.contains(historialtrabajoListNewHistorialtrabajo)) {
                    Tecnico oldTecnicoidTrabajadorOfHistorialtrabajoListNewHistorialtrabajo = historialtrabajoListNewHistorialtrabajo.getTecnicoidTrabajador();
                    historialtrabajoListNewHistorialtrabajo.setTecnicoidTrabajador(tecnico);
                    historialtrabajoListNewHistorialtrabajo = em.merge(historialtrabajoListNewHistorialtrabajo);
                    if (oldTecnicoidTrabajadorOfHistorialtrabajoListNewHistorialtrabajo != null && !oldTecnicoidTrabajadorOfHistorialtrabajoListNewHistorialtrabajo.equals(tecnico)) {
                        oldTecnicoidTrabajadorOfHistorialtrabajoListNewHistorialtrabajo.getHistorialtrabajoList().remove(historialtrabajoListNewHistorialtrabajo);
                        oldTecnicoidTrabajadorOfHistorialtrabajoListNewHistorialtrabajo = em.merge(oldTecnicoidTrabajadorOfHistorialtrabajoListNewHistorialtrabajo);
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
                Integer id = tecnico.getIdTecnico();
                if (findTecnico(id) == null) {
                    throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.");
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
            Tecnico tecnico;
            try {
                tecnico = em.getReference(Tecnico.class, id);
                tecnico.getIdTecnico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tecnico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Historialtrabajo> historialtrabajoListOrphanCheck = tecnico.getHistorialtrabajoList();
            for (Historialtrabajo historialtrabajoListOrphanCheckHistorialtrabajo : historialtrabajoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tecnico (" + tecnico + ") cannot be destroyed since the Historialtrabajo " + historialtrabajoListOrphanCheckHistorialtrabajo + " in its historialtrabajoList field has a non-nullable tecnicoidTrabajador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tecnico);
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

    public List<Tecnico> findTecnicoEntities() {
        return findTecnicoEntities(true, -1, -1);
    }

    public List<Tecnico> findTecnicoEntities(int maxResults, int firstResult) {
        return findTecnicoEntities(false, maxResults, firstResult);
    }

    private List<Tecnico> findTecnicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tecnico.class));
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

    public Tecnico findTecnico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tecnico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTecnicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tecnico> rt = cq.from(Tecnico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
