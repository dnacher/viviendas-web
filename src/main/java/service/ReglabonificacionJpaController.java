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
import entities.Reglabonificacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import service.exceptions.IllegalOrphanException;
import service.exceptions.NonexistentEntityException;
import service.exceptions.PreexistingEntityException;
import service.exceptions.RollbackFailureException;

/**
 *
 * @author danielnacher
 */
public class ReglabonificacionJpaController implements Serializable {

    public ReglabonificacionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reglabonificacion reglabonificacion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (reglabonificacion.getConvenioList() == null) {
            reglabonificacion.setConvenioList(new ArrayList<Convenio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Convenio> attachedConvenioList = new ArrayList<Convenio>();
            for (Convenio convenioListConvenioToAttach : reglabonificacion.getConvenioList()) {
                convenioListConvenioToAttach = em.getReference(convenioListConvenioToAttach.getClass(), convenioListConvenioToAttach.getConvenioPK());
                attachedConvenioList.add(convenioListConvenioToAttach);
            }
            reglabonificacion.setConvenioList(attachedConvenioList);
            em.persist(reglabonificacion);
            for (Convenio convenioListConvenio : reglabonificacion.getConvenioList()) {
                Reglabonificacion oldReglaBonificacionidreglaBonificacionOfConvenioListConvenio = convenioListConvenio.getReglaBonificacionidreglaBonificacion();
                convenioListConvenio.setReglaBonificacionidreglaBonificacion(reglabonificacion);
                convenioListConvenio = em.merge(convenioListConvenio);
                if (oldReglaBonificacionidreglaBonificacionOfConvenioListConvenio != null) {
                    oldReglaBonificacionidreglaBonificacionOfConvenioListConvenio.getConvenioList().remove(convenioListConvenio);
                    oldReglaBonificacionidreglaBonificacionOfConvenioListConvenio = em.merge(oldReglaBonificacionidreglaBonificacionOfConvenioListConvenio);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findReglabonificacion(reglabonificacion.getIdreglaBonificacion()) != null) {
                throw new PreexistingEntityException("Reglabonificacion " + reglabonificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reglabonificacion reglabonificacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Reglabonificacion persistentReglabonificacion = em.find(Reglabonificacion.class, reglabonificacion.getIdreglaBonificacion());
            List<Convenio> convenioListOld = persistentReglabonificacion.getConvenioList();
            List<Convenio> convenioListNew = reglabonificacion.getConvenioList();
            List<String> illegalOrphanMessages = null;
            for (Convenio convenioListOldConvenio : convenioListOld) {
                if (!convenioListNew.contains(convenioListOldConvenio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Convenio " + convenioListOldConvenio + " since its reglaBonificacionidreglaBonificacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Convenio> attachedConvenioListNew = new ArrayList<Convenio>();
            for (Convenio convenioListNewConvenioToAttach : convenioListNew) {
                convenioListNewConvenioToAttach = em.getReference(convenioListNewConvenioToAttach.getClass(), convenioListNewConvenioToAttach.getConvenioPK());
                attachedConvenioListNew.add(convenioListNewConvenioToAttach);
            }
            convenioListNew = attachedConvenioListNew;
            reglabonificacion.setConvenioList(convenioListNew);
            reglabonificacion = em.merge(reglabonificacion);
            for (Convenio convenioListNewConvenio : convenioListNew) {
                if (!convenioListOld.contains(convenioListNewConvenio)) {
                    Reglabonificacion oldReglaBonificacionidreglaBonificacionOfConvenioListNewConvenio = convenioListNewConvenio.getReglaBonificacionidreglaBonificacion();
                    convenioListNewConvenio.setReglaBonificacionidreglaBonificacion(reglabonificacion);
                    convenioListNewConvenio = em.merge(convenioListNewConvenio);
                    if (oldReglaBonificacionidreglaBonificacionOfConvenioListNewConvenio != null && !oldReglaBonificacionidreglaBonificacionOfConvenioListNewConvenio.equals(reglabonificacion)) {
                        oldReglaBonificacionidreglaBonificacionOfConvenioListNewConvenio.getConvenioList().remove(convenioListNewConvenio);
                        oldReglaBonificacionidreglaBonificacionOfConvenioListNewConvenio = em.merge(oldReglaBonificacionidreglaBonificacionOfConvenioListNewConvenio);
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
                Integer id = reglabonificacion.getIdreglaBonificacion();
                if (findReglabonificacion(id) == null) {
                    throw new NonexistentEntityException("The reglabonificacion with id " + id + " no longer exists.");
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
            Reglabonificacion reglabonificacion;
            try {
                reglabonificacion = em.getReference(Reglabonificacion.class, id);
                reglabonificacion.getIdreglaBonificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reglabonificacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Convenio> convenioListOrphanCheck = reglabonificacion.getConvenioList();
            for (Convenio convenioListOrphanCheckConvenio : convenioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reglabonificacion (" + reglabonificacion + ") cannot be destroyed since the Convenio " + convenioListOrphanCheckConvenio + " in its convenioList field has a non-nullable reglaBonificacionidreglaBonificacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(reglabonificacion);
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

    public List<Reglabonificacion> findReglabonificacionEntities() {
        return findReglabonificacionEntities(true, -1, -1);
    }

    public List<Reglabonificacion> findReglabonificacionEntities(int maxResults, int firstResult) {
        return findReglabonificacionEntities(false, maxResults, firstResult);
    }

    private List<Reglabonificacion> findReglabonificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reglabonificacion.class));
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

    public Reglabonificacion findReglabonificacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reglabonificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getReglabonificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reglabonificacion> rt = cq.from(Reglabonificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
