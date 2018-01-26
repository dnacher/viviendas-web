/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Historialmonto;
import entities.HistorialmontoPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Monto;
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
public class HistorialmontoJpaController implements Serializable {

    public HistorialmontoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historialmonto historialmonto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (historialmonto.getHistorialmontoPK() == null) {
            historialmonto.setHistorialmontoPK(new HistorialmontoPK());
        }
        historialmonto.getHistorialmontoPK().setMontoIdmonto(historialmonto.getMonto().getIdmonto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Monto monto = historialmonto.getMonto();
            if (monto != null) {
                monto = em.getReference(monto.getClass(), monto.getIdmonto());
                historialmonto.setMonto(monto);
            }
            em.persist(historialmonto);
            if (monto != null) {
                monto.getHistorialmontoList().add(historialmonto);
                monto = em.merge(monto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findHistorialmonto(historialmonto.getHistorialmontoPK()) != null) {
                throw new PreexistingEntityException("Historialmonto " + historialmonto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historialmonto historialmonto) throws NonexistentEntityException, RollbackFailureException, Exception {
        historialmonto.getHistorialmontoPK().setMontoIdmonto(historialmonto.getMonto().getIdmonto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historialmonto persistentHistorialmonto = em.find(Historialmonto.class, historialmonto.getHistorialmontoPK());
            Monto montoOld = persistentHistorialmonto.getMonto();
            Monto montoNew = historialmonto.getMonto();
            if (montoNew != null) {
                montoNew = em.getReference(montoNew.getClass(), montoNew.getIdmonto());
                historialmonto.setMonto(montoNew);
            }
            historialmonto = em.merge(historialmonto);
            if (montoOld != null && !montoOld.equals(montoNew)) {
                montoOld.getHistorialmontoList().remove(historialmonto);
                montoOld = em.merge(montoOld);
            }
            if (montoNew != null && !montoNew.equals(montoOld)) {
                montoNew.getHistorialmontoList().add(historialmonto);
                montoNew = em.merge(montoNew);
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
                HistorialmontoPK id = historialmonto.getHistorialmontoPK();
                if (findHistorialmonto(id) == null) {
                    throw new NonexistentEntityException("The historialmonto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HistorialmontoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Historialmonto historialmonto;
            try {
                historialmonto = em.getReference(Historialmonto.class, id);
                historialmonto.getHistorialmontoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialmonto with id " + id + " no longer exists.", enfe);
            }
            Monto monto = historialmonto.getMonto();
            if (monto != null) {
                monto.getHistorialmontoList().remove(historialmonto);
                monto = em.merge(monto);
            }
            em.remove(historialmonto);
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

    public List<Historialmonto> findHistorialmontoEntities() {
        return findHistorialmontoEntities(true, -1, -1);
    }

    public List<Historialmonto> findHistorialmontoEntities(int maxResults, int firstResult) {
        return findHistorialmontoEntities(false, maxResults, firstResult);
    }

    private List<Historialmonto> findHistorialmontoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historialmonto.class));
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

    public Historialmonto findHistorialmonto(HistorialmontoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historialmonto.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialmontoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historialmonto> rt = cq.from(Historialmonto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
