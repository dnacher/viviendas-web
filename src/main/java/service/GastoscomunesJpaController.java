/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Gastoscomunes;
import entities.GastoscomunesPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Monto;
import entities.Unidad;
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
public class GastoscomunesJpaController implements Serializable {

    public GastoscomunesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gastoscomunes gastoscomunes) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (gastoscomunes.getGastoscomunesPK() == null) {
            gastoscomunes.setGastoscomunesPK(new GastoscomunesPK());
        }
        gastoscomunes.getGastoscomunesPK().setUnidadidUnidad(gastoscomunes.getUnidad().getIdUnidad());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Monto montoIdmonto = gastoscomunes.getMontoIdmonto();
            if (montoIdmonto != null) {
                montoIdmonto = em.getReference(montoIdmonto.getClass(), montoIdmonto.getIdmonto());
                gastoscomunes.setMontoIdmonto(montoIdmonto);
            }
            Unidad unidad = gastoscomunes.getUnidad();
            if (unidad != null) {
                unidad = em.getReference(unidad.getClass(), unidad.getIdUnidad());
                gastoscomunes.setUnidad(unidad);
            }
            em.persist(gastoscomunes);
            if (montoIdmonto != null) {
                montoIdmonto.getGastoscomunesList().add(gastoscomunes);
                montoIdmonto = em.merge(montoIdmonto);
            }
            if (unidad != null) {
                unidad.getGastoscomunesList().add(gastoscomunes);
                unidad = em.merge(unidad);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findGastoscomunes(gastoscomunes.getGastoscomunesPK()) != null) {
                throw new PreexistingEntityException("Gastoscomunes " + gastoscomunes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gastoscomunes gastoscomunes) throws NonexistentEntityException, RollbackFailureException, Exception {
        gastoscomunes.getGastoscomunesPK().setUnidadidUnidad(gastoscomunes.getUnidad().getIdUnidad());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Gastoscomunes persistentGastoscomunes = em.find(Gastoscomunes.class, gastoscomunes.getGastoscomunesPK());
            Monto montoIdmontoOld = persistentGastoscomunes.getMontoIdmonto();
            Monto montoIdmontoNew = gastoscomunes.getMontoIdmonto();
            Unidad unidadOld = persistentGastoscomunes.getUnidad();
            Unidad unidadNew = gastoscomunes.getUnidad();
            if (montoIdmontoNew != null) {
                montoIdmontoNew = em.getReference(montoIdmontoNew.getClass(), montoIdmontoNew.getIdmonto());
                gastoscomunes.setMontoIdmonto(montoIdmontoNew);
            }
            if (unidadNew != null) {
                unidadNew = em.getReference(unidadNew.getClass(), unidadNew.getIdUnidad());
                gastoscomunes.setUnidad(unidadNew);
            }
            gastoscomunes = em.merge(gastoscomunes);
            if (montoIdmontoOld != null && !montoIdmontoOld.equals(montoIdmontoNew)) {
                montoIdmontoOld.getGastoscomunesList().remove(gastoscomunes);
                montoIdmontoOld = em.merge(montoIdmontoOld);
            }
            if (montoIdmontoNew != null && !montoIdmontoNew.equals(montoIdmontoOld)) {
                montoIdmontoNew.getGastoscomunesList().add(gastoscomunes);
                montoIdmontoNew = em.merge(montoIdmontoNew);
            }
            if (unidadOld != null && !unidadOld.equals(unidadNew)) {
                unidadOld.getGastoscomunesList().remove(gastoscomunes);
                unidadOld = em.merge(unidadOld);
            }
            if (unidadNew != null && !unidadNew.equals(unidadOld)) {
                unidadNew.getGastoscomunesList().add(gastoscomunes);
                unidadNew = em.merge(unidadNew);
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
                GastoscomunesPK id = gastoscomunes.getGastoscomunesPK();
                if (findGastoscomunes(id) == null) {
                    throw new NonexistentEntityException("The gastoscomunes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GastoscomunesPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Gastoscomunes gastoscomunes;
            try {
                gastoscomunes = em.getReference(Gastoscomunes.class, id);
                gastoscomunes.getGastoscomunesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gastoscomunes with id " + id + " no longer exists.", enfe);
            }
            Monto montoIdmonto = gastoscomunes.getMontoIdmonto();
            if (montoIdmonto != null) {
                montoIdmonto.getGastoscomunesList().remove(gastoscomunes);
                montoIdmonto = em.merge(montoIdmonto);
            }
            Unidad unidad = gastoscomunes.getUnidad();
            if (unidad != null) {
                unidad.getGastoscomunesList().remove(gastoscomunes);
                unidad = em.merge(unidad);
            }
            em.remove(gastoscomunes);
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

    public List<Gastoscomunes> findGastoscomunesEntities() {
        return findGastoscomunesEntities(true, -1, -1);
    }

    public List<Gastoscomunes> findGastoscomunesEntities(int maxResults, int firstResult) {
        return findGastoscomunesEntities(false, maxResults, firstResult);
    }

    private List<Gastoscomunes> findGastoscomunesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gastoscomunes.class));
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

    public Gastoscomunes findGastoscomunes(GastoscomunesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gastoscomunes.class, id);
        } finally {
            em.close();
        }
    }

    public int getGastoscomunesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gastoscomunes> rt = cq.from(Gastoscomunes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
