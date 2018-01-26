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
import entities.Convenio;
import entities.Cuotaconvenio;
import entities.CuotaconvenioPK;
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
public class CuotaconvenioJpaController implements Serializable {

    public CuotaconvenioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuotaconvenio cuotaconvenio) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (cuotaconvenio.getCuotaconvenioPK() == null) {
            cuotaconvenio.setCuotaconvenioPK(new CuotaconvenioPK());
        }
        cuotaconvenio.getCuotaconvenioPK().setMontoIdmonto(cuotaconvenio.getMonto().getIdmonto());
        cuotaconvenio.getCuotaconvenioPK().setConveniounidadidUnidad(cuotaconvenio.getConvenio().getConvenioPK().getUnidadidUnidad());
        cuotaconvenio.getCuotaconvenioPK().setConvenioIdconvenio(cuotaconvenio.getConvenio().getConvenioPK().getIdconvenio());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Convenio convenio = cuotaconvenio.getConvenio();
            if (convenio != null) {
                convenio = em.getReference(convenio.getClass(), convenio.getConvenioPK());
                cuotaconvenio.setConvenio(convenio);
            }
            Monto monto = cuotaconvenio.getMonto();
            if (monto != null) {
                monto = em.getReference(monto.getClass(), monto.getIdmonto());
                cuotaconvenio.setMonto(monto);
            }
            em.persist(cuotaconvenio);
            if (convenio != null) {
                convenio.getCuotaconvenioList().add(cuotaconvenio);
                convenio = em.merge(convenio);
            }
            if (monto != null) {
                monto.getCuotaconvenioList().add(cuotaconvenio);
                monto = em.merge(monto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCuotaconvenio(cuotaconvenio.getCuotaconvenioPK()) != null) {
                throw new PreexistingEntityException("Cuotaconvenio " + cuotaconvenio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuotaconvenio cuotaconvenio) throws NonexistentEntityException, RollbackFailureException, Exception {
        cuotaconvenio.getCuotaconvenioPK().setMontoIdmonto(cuotaconvenio.getMonto().getIdmonto());
        cuotaconvenio.getCuotaconvenioPK().setConveniounidadidUnidad(cuotaconvenio.getConvenio().getConvenioPK().getUnidadidUnidad());
        cuotaconvenio.getCuotaconvenioPK().setConvenioIdconvenio(cuotaconvenio.getConvenio().getConvenioPK().getIdconvenio());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cuotaconvenio persistentCuotaconvenio = em.find(Cuotaconvenio.class, cuotaconvenio.getCuotaconvenioPK());
            Convenio convenioOld = persistentCuotaconvenio.getConvenio();
            Convenio convenioNew = cuotaconvenio.getConvenio();
            Monto montoOld = persistentCuotaconvenio.getMonto();
            Monto montoNew = cuotaconvenio.getMonto();
            if (convenioNew != null) {
                convenioNew = em.getReference(convenioNew.getClass(), convenioNew.getConvenioPK());
                cuotaconvenio.setConvenio(convenioNew);
            }
            if (montoNew != null) {
                montoNew = em.getReference(montoNew.getClass(), montoNew.getIdmonto());
                cuotaconvenio.setMonto(montoNew);
            }
            cuotaconvenio = em.merge(cuotaconvenio);
            if (convenioOld != null && !convenioOld.equals(convenioNew)) {
                convenioOld.getCuotaconvenioList().remove(cuotaconvenio);
                convenioOld = em.merge(convenioOld);
            }
            if (convenioNew != null && !convenioNew.equals(convenioOld)) {
                convenioNew.getCuotaconvenioList().add(cuotaconvenio);
                convenioNew = em.merge(convenioNew);
            }
            if (montoOld != null && !montoOld.equals(montoNew)) {
                montoOld.getCuotaconvenioList().remove(cuotaconvenio);
                montoOld = em.merge(montoOld);
            }
            if (montoNew != null && !montoNew.equals(montoOld)) {
                montoNew.getCuotaconvenioList().add(cuotaconvenio);
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
                CuotaconvenioPK id = cuotaconvenio.getCuotaconvenioPK();
                if (findCuotaconvenio(id) == null) {
                    throw new NonexistentEntityException("The cuotaconvenio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CuotaconvenioPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cuotaconvenio cuotaconvenio;
            try {
                cuotaconvenio = em.getReference(Cuotaconvenio.class, id);
                cuotaconvenio.getCuotaconvenioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuotaconvenio with id " + id + " no longer exists.", enfe);
            }
            Convenio convenio = cuotaconvenio.getConvenio();
            if (convenio != null) {
                convenio.getCuotaconvenioList().remove(cuotaconvenio);
                convenio = em.merge(convenio);
            }
            Monto monto = cuotaconvenio.getMonto();
            if (monto != null) {
                monto.getCuotaconvenioList().remove(cuotaconvenio);
                monto = em.merge(monto);
            }
            em.remove(cuotaconvenio);
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

    public List<Cuotaconvenio> findCuotaconvenioEntities() {
        return findCuotaconvenioEntities(true, -1, -1);
    }

    public List<Cuotaconvenio> findCuotaconvenioEntities(int maxResults, int firstResult) {
        return findCuotaconvenioEntities(false, maxResults, firstResult);
    }

    private List<Cuotaconvenio> findCuotaconvenioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuotaconvenio.class));
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

    public Cuotaconvenio findCuotaconvenio(CuotaconvenioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuotaconvenio.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuotaconvenioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuotaconvenio> rt = cq.from(Cuotaconvenio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
