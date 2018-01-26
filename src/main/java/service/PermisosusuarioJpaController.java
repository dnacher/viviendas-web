/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Permisosusuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Tipousuario;
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
public class PermisosusuarioJpaController implements Serializable {

    public PermisosusuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permisosusuario permisosusuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipousuario tipousuarioId = permisosusuario.getTipousuarioId();
            if (tipousuarioId != null) {
                tipousuarioId = em.getReference(tipousuarioId.getClass(), tipousuarioId.getId());
                permisosusuario.setTipousuarioId(tipousuarioId);
            }
            em.persist(permisosusuario);
            if (tipousuarioId != null) {
                tipousuarioId.getPermisosusuarioList().add(permisosusuario);
                tipousuarioId = em.merge(tipousuarioId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPermisosusuario(permisosusuario.getPagina()) != null) {
                throw new PreexistingEntityException("Permisosusuario " + permisosusuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permisosusuario permisosusuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Permisosusuario persistentPermisosusuario = em.find(Permisosusuario.class, permisosusuario.getPagina());
            Tipousuario tipousuarioIdOld = persistentPermisosusuario.getTipousuarioId();
            Tipousuario tipousuarioIdNew = permisosusuario.getTipousuarioId();
            if (tipousuarioIdNew != null) {
                tipousuarioIdNew = em.getReference(tipousuarioIdNew.getClass(), tipousuarioIdNew.getId());
                permisosusuario.setTipousuarioId(tipousuarioIdNew);
            }
            permisosusuario = em.merge(permisosusuario);
            if (tipousuarioIdOld != null && !tipousuarioIdOld.equals(tipousuarioIdNew)) {
                tipousuarioIdOld.getPermisosusuarioList().remove(permisosusuario);
                tipousuarioIdOld = em.merge(tipousuarioIdOld);
            }
            if (tipousuarioIdNew != null && !tipousuarioIdNew.equals(tipousuarioIdOld)) {
                tipousuarioIdNew.getPermisosusuarioList().add(permisosusuario);
                tipousuarioIdNew = em.merge(tipousuarioIdNew);
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
                String id = permisosusuario.getPagina();
                if (findPermisosusuario(id) == null) {
                    throw new NonexistentEntityException("The permisosusuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Permisosusuario permisosusuario;
            try {
                permisosusuario = em.getReference(Permisosusuario.class, id);
                permisosusuario.getPagina();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permisosusuario with id " + id + " no longer exists.", enfe);
            }
            Tipousuario tipousuarioId = permisosusuario.getTipousuarioId();
            if (tipousuarioId != null) {
                tipousuarioId.getPermisosusuarioList().remove(permisosusuario);
                tipousuarioId = em.merge(tipousuarioId);
            }
            em.remove(permisosusuario);
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

    public List<Permisosusuario> findPermisosusuarioEntities() {
        return findPermisosusuarioEntities(true, -1, -1);
    }

    public List<Permisosusuario> findPermisosusuarioEntities(int maxResults, int firstResult) {
        return findPermisosusuarioEntities(false, maxResults, firstResult);
    }

    private List<Permisosusuario> findPermisosusuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permisosusuario.class));
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

    public Permisosusuario findPermisosusuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permisosusuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisosusuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permisosusuario> rt = cq.from(Permisosusuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
