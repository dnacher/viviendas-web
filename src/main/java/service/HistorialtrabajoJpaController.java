/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Historialtrabajo;
import entities.HistorialtrabajoPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Trabajo;
import entities.Tecnico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import service.exceptions.NonexistentEntityException;
import service.exceptions.PreexistingEntityException;
import service.exceptions.RollbackFailureException;

/**
 *
 * @author danielnacher
 */
public class HistorialtrabajoJpaController implements Serializable {

    public HistorialtrabajoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historialtrabajo historialtrabajo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (historialtrabajo.getHistorialtrabajoPK() == null) {
            historialtrabajo.setHistorialtrabajoPK(new HistorialtrabajoPK());
        }
        historialtrabajo.getHistorialtrabajoPK().setTrabajoidTrabajo(historialtrabajo.getTrabajo().getIdTrabajo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Trabajo trabajo = historialtrabajo.getTrabajo();
            if (trabajo != null) {
                trabajo = em.getReference(trabajo.getClass(), trabajo.getIdTrabajo());
                historialtrabajo.setTrabajo(trabajo);
            }
            Tecnico tecnicoidTrabajador = historialtrabajo.getTecnicoidTrabajador();
            if (tecnicoidTrabajador != null) {
                tecnicoidTrabajador = em.getReference(tecnicoidTrabajador.getClass(), tecnicoidTrabajador.getIdTecnico());
                historialtrabajo.setTecnicoidTrabajador(tecnicoidTrabajador);
            }
            em.persist(historialtrabajo);
            if (trabajo != null) {
                trabajo.getHistorialtrabajoList().add(historialtrabajo);
                trabajo = em.merge(trabajo);
            }
            if (tecnicoidTrabajador != null) {
                tecnicoidTrabajador.getHistorialtrabajoList().add(historialtrabajo);
                tecnicoidTrabajador = em.merge(tecnicoidTrabajador);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistorialtrabajo(historialtrabajo.getHistorialtrabajoPK()) != null) {
                throw new PreexistingEntityException("Historialtrabajo " + historialtrabajo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historialtrabajo historialtrabajo) throws NonexistentEntityException, RollbackFailureException, Exception {
        historialtrabajo.getHistorialtrabajoPK().setTrabajoidTrabajo(historialtrabajo.getTrabajo().getIdTrabajo());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historialtrabajo persistentHistorialtrabajo = em.find(Historialtrabajo.class, historialtrabajo.getHistorialtrabajoPK());
            Trabajo trabajoOld = persistentHistorialtrabajo.getTrabajo();
            Trabajo trabajoNew = historialtrabajo.getTrabajo();
            Tecnico tecnicoidTrabajadorOld = persistentHistorialtrabajo.getTecnicoidTrabajador();
            Tecnico tecnicoidTrabajadorNew = historialtrabajo.getTecnicoidTrabajador();
            if (trabajoNew != null) {
                trabajoNew = em.getReference(trabajoNew.getClass(), trabajoNew.getIdTrabajo());
                historialtrabajo.setTrabajo(trabajoNew);
            }
            if (tecnicoidTrabajadorNew != null) {
                tecnicoidTrabajadorNew = em.getReference(tecnicoidTrabajadorNew.getClass(), tecnicoidTrabajadorNew.getIdTecnico());
                historialtrabajo.setTecnicoidTrabajador(tecnicoidTrabajadorNew);
            }
            historialtrabajo = em.merge(historialtrabajo);
            if (trabajoOld != null && !trabajoOld.equals(trabajoNew)) {
                trabajoOld.getHistorialtrabajoList().remove(historialtrabajo);
                trabajoOld = em.merge(trabajoOld);
            }
            if (trabajoNew != null && !trabajoNew.equals(trabajoOld)) {
                trabajoNew.getHistorialtrabajoList().add(historialtrabajo);
                trabajoNew = em.merge(trabajoNew);
            }
            if (tecnicoidTrabajadorOld != null && !tecnicoidTrabajadorOld.equals(tecnicoidTrabajadorNew)) {
                tecnicoidTrabajadorOld.getHistorialtrabajoList().remove(historialtrabajo);
                tecnicoidTrabajadorOld = em.merge(tecnicoidTrabajadorOld);
            }
            if (tecnicoidTrabajadorNew != null && !tecnicoidTrabajadorNew.equals(tecnicoidTrabajadorOld)) {
                tecnicoidTrabajadorNew.getHistorialtrabajoList().add(historialtrabajo);
                tecnicoidTrabajadorNew = em.merge(tecnicoidTrabajadorNew);
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
                HistorialtrabajoPK id = historialtrabajo.getHistorialtrabajoPK();
                if (findHistorialtrabajo(id) == null) {
                    throw new NonexistentEntityException("The historialtrabajo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HistorialtrabajoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historialtrabajo historialtrabajo;
            try {
                historialtrabajo = em.getReference(Historialtrabajo.class, id);
                historialtrabajo.getHistorialtrabajoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialtrabajo with id " + id + " no longer exists.", enfe);
            }
            Trabajo trabajo = historialtrabajo.getTrabajo();
            if (trabajo != null) {
                trabajo.getHistorialtrabajoList().remove(historialtrabajo);
                trabajo = em.merge(trabajo);
            }
            Tecnico tecnicoidTrabajador = historialtrabajo.getTecnicoidTrabajador();
            if (tecnicoidTrabajador != null) {
                tecnicoidTrabajador.getHistorialtrabajoList().remove(historialtrabajo);
                tecnicoidTrabajador = em.merge(tecnicoidTrabajador);
            }
            em.remove(historialtrabajo);
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

    public List<Historialtrabajo> findHistorialtrabajoEntities() {
        return findHistorialtrabajoEntities(true, -1, -1);
    }

    public List<Historialtrabajo> findHistorialtrabajoEntities(int maxResults, int firstResult) {
        return findHistorialtrabajoEntities(false, maxResults, firstResult);
    }

    private List<Historialtrabajo> findHistorialtrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historialtrabajo.class));
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

    public Historialtrabajo findHistorialtrabajo(HistorialtrabajoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historialtrabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialtrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historialtrabajo> rt = cq.from(Historialtrabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
