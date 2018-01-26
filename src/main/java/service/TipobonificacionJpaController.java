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
import entities.Tipobonificacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import service.exceptions.NonexistentEntityException;
import service.exceptions.RollbackFailureException;

/**
 *
 * @author danielnacher
 */
public class TipobonificacionJpaController implements Serializable {

    public TipobonificacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipobonificacion tipobonificacion) throws RollbackFailureException, Exception {
        if (tipobonificacion.getConvenioList() == null) {
            tipobonificacion.setConvenioList(new ArrayList<Convenio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Convenio> attachedConvenioList = new ArrayList<Convenio>();
            for (Convenio convenioListConvenioToAttach : tipobonificacion.getConvenioList()) {
                convenioListConvenioToAttach = em.getReference(convenioListConvenioToAttach.getClass(), convenioListConvenioToAttach.getConvenioPK());
                attachedConvenioList.add(convenioListConvenioToAttach);
            }
            tipobonificacion.setConvenioList(attachedConvenioList);
            em.persist(tipobonificacion);
            for (Convenio convenioListConvenio : tipobonificacion.getConvenioList()) {
                Tipobonificacion oldTipoBonificacionOfConvenioListConvenio = convenioListConvenio.getTipoBonificacion();
                convenioListConvenio.setTipoBonificacion(tipobonificacion);
                convenioListConvenio = em.merge(convenioListConvenio);
                if (oldTipoBonificacionOfConvenioListConvenio != null) {
                    oldTipoBonificacionOfConvenioListConvenio.getConvenioList().remove(convenioListConvenio);
                    oldTipoBonificacionOfConvenioListConvenio = em.merge(oldTipoBonificacionOfConvenioListConvenio);
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

    public void edit(Tipobonificacion tipobonificacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipobonificacion persistentTipobonificacion = em.find(Tipobonificacion.class, tipobonificacion.getIdtipoBonificacion());
            List<Convenio> convenioListOld = persistentTipobonificacion.getConvenioList();
            List<Convenio> convenioListNew = tipobonificacion.getConvenioList();
            List<Convenio> attachedConvenioListNew = new ArrayList<Convenio>();
            for (Convenio convenioListNewConvenioToAttach : convenioListNew) {
                convenioListNewConvenioToAttach = em.getReference(convenioListNewConvenioToAttach.getClass(), convenioListNewConvenioToAttach.getConvenioPK());
                attachedConvenioListNew.add(convenioListNewConvenioToAttach);
            }
            convenioListNew = attachedConvenioListNew;
            tipobonificacion.setConvenioList(convenioListNew);
            tipobonificacion = em.merge(tipobonificacion);
            for (Convenio convenioListOldConvenio : convenioListOld) {
                if (!convenioListNew.contains(convenioListOldConvenio)) {
                    convenioListOldConvenio.setTipoBonificacion(null);
                    convenioListOldConvenio = em.merge(convenioListOldConvenio);
                }
            }
            for (Convenio convenioListNewConvenio : convenioListNew) {
                if (!convenioListOld.contains(convenioListNewConvenio)) {
                    Tipobonificacion oldTipoBonificacionOfConvenioListNewConvenio = convenioListNewConvenio.getTipoBonificacion();
                    convenioListNewConvenio.setTipoBonificacion(tipobonificacion);
                    convenioListNewConvenio = em.merge(convenioListNewConvenio);
                    if (oldTipoBonificacionOfConvenioListNewConvenio != null && !oldTipoBonificacionOfConvenioListNewConvenio.equals(tipobonificacion)) {
                        oldTipoBonificacionOfConvenioListNewConvenio.getConvenioList().remove(convenioListNewConvenio);
                        oldTipoBonificacionOfConvenioListNewConvenio = em.merge(oldTipoBonificacionOfConvenioListNewConvenio);
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
                Integer id = tipobonificacion.getIdtipoBonificacion();
                if (findTipobonificacion(id) == null) {
                    throw new NonexistentEntityException("The tipobonificacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipobonificacion tipobonificacion;
            try {
                tipobonificacion = em.getReference(Tipobonificacion.class, id);
                tipobonificacion.getIdtipoBonificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipobonificacion with id " + id + " no longer exists.", enfe);
            }
            List<Convenio> convenioList = tipobonificacion.getConvenioList();
            for (Convenio convenioListConvenio : convenioList) {
                convenioListConvenio.setTipoBonificacion(null);
                convenioListConvenio = em.merge(convenioListConvenio);
            }
            em.remove(tipobonificacion);
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

    public List<Tipobonificacion> findTipobonificacionEntities() {
        return findTipobonificacionEntities(true, -1, -1);
    }

    public List<Tipobonificacion> findTipobonificacionEntities(int maxResults, int firstResult) {
        return findTipobonificacionEntities(false, maxResults, firstResult);
    }

    private List<Tipobonificacion> findTipobonificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipobonificacion.class));
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

    public Tipobonificacion findTipobonificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipobonificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipobonificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipobonificacion> rt = cq.from(Tipobonificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
