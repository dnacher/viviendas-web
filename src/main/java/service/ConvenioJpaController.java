/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Convenio;
import entities.ConvenioPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Monto;
import entities.Reglabonificacion;
import entities.Unidad;
import entities.Tipobonificacion;
import entities.Cuotaconvenio;
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
public class ConvenioJpaController implements Serializable {

    public ConvenioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Convenio convenio) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (convenio.getConvenioPK() == null) {
            convenio.setConvenioPK(new ConvenioPK());
        }
        if (convenio.getCuotaconvenioList() == null) {
            convenio.setCuotaconvenioList(new ArrayList<Cuotaconvenio>());
        }
        convenio.getConvenioPK().setUnidadidUnidad(convenio.getUnidad().getIdUnidad());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Monto montoIdmonto = convenio.getMontoIdmonto();
            if (montoIdmonto != null) {
                montoIdmonto = em.getReference(montoIdmonto.getClass(), montoIdmonto.getIdmonto());
                convenio.setMontoIdmonto(montoIdmonto);
            }
            Reglabonificacion reglaBonificacionidreglaBonificacion = convenio.getReglaBonificacionidreglaBonificacion();
            if (reglaBonificacionidreglaBonificacion != null) {
                reglaBonificacionidreglaBonificacion = em.getReference(reglaBonificacionidreglaBonificacion.getClass(), reglaBonificacionidreglaBonificacion.getIdreglaBonificacion());
                convenio.setReglaBonificacionidreglaBonificacion(reglaBonificacionidreglaBonificacion);
            }
            Unidad unidad = convenio.getUnidad();
            if (unidad != null) {
                unidad = em.getReference(unidad.getClass(), unidad.getIdUnidad());
                convenio.setUnidad(unidad);
            }
            Tipobonificacion tipoBonificacion = convenio.getTipoBonificacion();
            if (tipoBonificacion != null) {
                tipoBonificacion = em.getReference(tipoBonificacion.getClass(), tipoBonificacion.getIdtipoBonificacion());
                convenio.setTipoBonificacion(tipoBonificacion);
            }
            List<Cuotaconvenio> attachedCuotaconvenioList = new ArrayList<Cuotaconvenio>();
            for (Cuotaconvenio cuotaconvenioListCuotaconvenioToAttach : convenio.getCuotaconvenioList()) {
                cuotaconvenioListCuotaconvenioToAttach = em.getReference(cuotaconvenioListCuotaconvenioToAttach.getClass(), cuotaconvenioListCuotaconvenioToAttach.getCuotaconvenioPK());
                attachedCuotaconvenioList.add(cuotaconvenioListCuotaconvenioToAttach);
            }
            convenio.setCuotaconvenioList(attachedCuotaconvenioList);
            em.persist(convenio);
            if (montoIdmonto != null) {
                montoIdmonto.getConvenioList().add(convenio);
                montoIdmonto = em.merge(montoIdmonto);
            }
            if (reglaBonificacionidreglaBonificacion != null) {
                reglaBonificacionidreglaBonificacion.getConvenioList().add(convenio);
                reglaBonificacionidreglaBonificacion = em.merge(reglaBonificacionidreglaBonificacion);
            }
            if (unidad != null) {
                unidad.getConvenioList().add(convenio);
                unidad = em.merge(unidad);
            }
            if (tipoBonificacion != null) {
                tipoBonificacion.getConvenioList().add(convenio);
                tipoBonificacion = em.merge(tipoBonificacion);
            }
            for (Cuotaconvenio cuotaconvenioListCuotaconvenio : convenio.getCuotaconvenioList()) {
                Convenio oldConvenioOfCuotaconvenioListCuotaconvenio = cuotaconvenioListCuotaconvenio.getConvenio();
                cuotaconvenioListCuotaconvenio.setConvenio(convenio);
                cuotaconvenioListCuotaconvenio = em.merge(cuotaconvenioListCuotaconvenio);
                if (oldConvenioOfCuotaconvenioListCuotaconvenio != null) {
                    oldConvenioOfCuotaconvenioListCuotaconvenio.getCuotaconvenioList().remove(cuotaconvenioListCuotaconvenio);
                    oldConvenioOfCuotaconvenioListCuotaconvenio = em.merge(oldConvenioOfCuotaconvenioListCuotaconvenio);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConvenio(convenio.getConvenioPK()) != null) {
                throw new PreexistingEntityException("Convenio " + convenio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Convenio convenio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        convenio.getConvenioPK().setUnidadidUnidad(convenio.getUnidad().getIdUnidad());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Convenio persistentConvenio = em.find(Convenio.class, convenio.getConvenioPK());
            Monto montoIdmontoOld = persistentConvenio.getMontoIdmonto();
            Monto montoIdmontoNew = convenio.getMontoIdmonto();
            Reglabonificacion reglaBonificacionidreglaBonificacionOld = persistentConvenio.getReglaBonificacionidreglaBonificacion();
            Reglabonificacion reglaBonificacionidreglaBonificacionNew = convenio.getReglaBonificacionidreglaBonificacion();
            Unidad unidadOld = persistentConvenio.getUnidad();
            Unidad unidadNew = convenio.getUnidad();
            Tipobonificacion tipoBonificacionOld = persistentConvenio.getTipoBonificacion();
            Tipobonificacion tipoBonificacionNew = convenio.getTipoBonificacion();
            List<Cuotaconvenio> cuotaconvenioListOld = persistentConvenio.getCuotaconvenioList();
            List<Cuotaconvenio> cuotaconvenioListNew = convenio.getCuotaconvenioList();
            List<String> illegalOrphanMessages = null;
            for (Cuotaconvenio cuotaconvenioListOldCuotaconvenio : cuotaconvenioListOld) {
                if (!cuotaconvenioListNew.contains(cuotaconvenioListOldCuotaconvenio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuotaconvenio " + cuotaconvenioListOldCuotaconvenio + " since its convenio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (montoIdmontoNew != null) {
                montoIdmontoNew = em.getReference(montoIdmontoNew.getClass(), montoIdmontoNew.getIdmonto());
                convenio.setMontoIdmonto(montoIdmontoNew);
            }
            if (reglaBonificacionidreglaBonificacionNew != null) {
                reglaBonificacionidreglaBonificacionNew = em.getReference(reglaBonificacionidreglaBonificacionNew.getClass(), reglaBonificacionidreglaBonificacionNew.getIdreglaBonificacion());
                convenio.setReglaBonificacionidreglaBonificacion(reglaBonificacionidreglaBonificacionNew);
            }
            if (unidadNew != null) {
                unidadNew = em.getReference(unidadNew.getClass(), unidadNew.getIdUnidad());
                convenio.setUnidad(unidadNew);
            }
            if (tipoBonificacionNew != null) {
                tipoBonificacionNew = em.getReference(tipoBonificacionNew.getClass(), tipoBonificacionNew.getIdtipoBonificacion());
                convenio.setTipoBonificacion(tipoBonificacionNew);
            }
            List<Cuotaconvenio> attachedCuotaconvenioListNew = new ArrayList<Cuotaconvenio>();
            for (Cuotaconvenio cuotaconvenioListNewCuotaconvenioToAttach : cuotaconvenioListNew) {
                cuotaconvenioListNewCuotaconvenioToAttach = em.getReference(cuotaconvenioListNewCuotaconvenioToAttach.getClass(), cuotaconvenioListNewCuotaconvenioToAttach.getCuotaconvenioPK());
                attachedCuotaconvenioListNew.add(cuotaconvenioListNewCuotaconvenioToAttach);
            }
            cuotaconvenioListNew = attachedCuotaconvenioListNew;
            convenio.setCuotaconvenioList(cuotaconvenioListNew);
            convenio = em.merge(convenio);
            if (montoIdmontoOld != null && !montoIdmontoOld.equals(montoIdmontoNew)) {
                montoIdmontoOld.getConvenioList().remove(convenio);
                montoIdmontoOld = em.merge(montoIdmontoOld);
            }
            if (montoIdmontoNew != null && !montoIdmontoNew.equals(montoIdmontoOld)) {
                montoIdmontoNew.getConvenioList().add(convenio);
                montoIdmontoNew = em.merge(montoIdmontoNew);
            }
            if (reglaBonificacionidreglaBonificacionOld != null && !reglaBonificacionidreglaBonificacionOld.equals(reglaBonificacionidreglaBonificacionNew)) {
                reglaBonificacionidreglaBonificacionOld.getConvenioList().remove(convenio);
                reglaBonificacionidreglaBonificacionOld = em.merge(reglaBonificacionidreglaBonificacionOld);
            }
            if (reglaBonificacionidreglaBonificacionNew != null && !reglaBonificacionidreglaBonificacionNew.equals(reglaBonificacionidreglaBonificacionOld)) {
                reglaBonificacionidreglaBonificacionNew.getConvenioList().add(convenio);
                reglaBonificacionidreglaBonificacionNew = em.merge(reglaBonificacionidreglaBonificacionNew);
            }
            if (unidadOld != null && !unidadOld.equals(unidadNew)) {
                unidadOld.getConvenioList().remove(convenio);
                unidadOld = em.merge(unidadOld);
            }
            if (unidadNew != null && !unidadNew.equals(unidadOld)) {
                unidadNew.getConvenioList().add(convenio);
                unidadNew = em.merge(unidadNew);
            }
            if (tipoBonificacionOld != null && !tipoBonificacionOld.equals(tipoBonificacionNew)) {
                tipoBonificacionOld.getConvenioList().remove(convenio);
                tipoBonificacionOld = em.merge(tipoBonificacionOld);
            }
            if (tipoBonificacionNew != null && !tipoBonificacionNew.equals(tipoBonificacionOld)) {
                tipoBonificacionNew.getConvenioList().add(convenio);
                tipoBonificacionNew = em.merge(tipoBonificacionNew);
            }
            for (Cuotaconvenio cuotaconvenioListNewCuotaconvenio : cuotaconvenioListNew) {
                if (!cuotaconvenioListOld.contains(cuotaconvenioListNewCuotaconvenio)) {
                    Convenio oldConvenioOfCuotaconvenioListNewCuotaconvenio = cuotaconvenioListNewCuotaconvenio.getConvenio();
                    cuotaconvenioListNewCuotaconvenio.setConvenio(convenio);
                    cuotaconvenioListNewCuotaconvenio = em.merge(cuotaconvenioListNewCuotaconvenio);
                    if (oldConvenioOfCuotaconvenioListNewCuotaconvenio != null && !oldConvenioOfCuotaconvenioListNewCuotaconvenio.equals(convenio)) {
                        oldConvenioOfCuotaconvenioListNewCuotaconvenio.getCuotaconvenioList().remove(cuotaconvenioListNewCuotaconvenio);
                        oldConvenioOfCuotaconvenioListNewCuotaconvenio = em.merge(oldConvenioOfCuotaconvenioListNewCuotaconvenio);
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
                ConvenioPK id = convenio.getConvenioPK();
                if (findConvenio(id) == null) {
                    throw new NonexistentEntityException("The convenio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ConvenioPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Convenio convenio;
            try {
                convenio = em.getReference(Convenio.class, id);
                convenio.getConvenioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The convenio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuotaconvenio> cuotaconvenioListOrphanCheck = convenio.getCuotaconvenioList();
            for (Cuotaconvenio cuotaconvenioListOrphanCheckCuotaconvenio : cuotaconvenioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Convenio (" + convenio + ") cannot be destroyed since the Cuotaconvenio " + cuotaconvenioListOrphanCheckCuotaconvenio + " in its cuotaconvenioList field has a non-nullable convenio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Monto montoIdmonto = convenio.getMontoIdmonto();
            if (montoIdmonto != null) {
                montoIdmonto.getConvenioList().remove(convenio);
                montoIdmonto = em.merge(montoIdmonto);
            }
            Reglabonificacion reglaBonificacionidreglaBonificacion = convenio.getReglaBonificacionidreglaBonificacion();
            if (reglaBonificacionidreglaBonificacion != null) {
                reglaBonificacionidreglaBonificacion.getConvenioList().remove(convenio);
                reglaBonificacionidreglaBonificacion = em.merge(reglaBonificacionidreglaBonificacion);
            }
            Unidad unidad = convenio.getUnidad();
            if (unidad != null) {
                unidad.getConvenioList().remove(convenio);
                unidad = em.merge(unidad);
            }
            Tipobonificacion tipoBonificacion = convenio.getTipoBonificacion();
            if (tipoBonificacion != null) {
                tipoBonificacion.getConvenioList().remove(convenio);
                tipoBonificacion = em.merge(tipoBonificacion);
            }
            em.remove(convenio);
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

    public List<Convenio> findConvenioEntities() {
        return findConvenioEntities(true, -1, -1);
    }

    public List<Convenio> findConvenioEntities(int maxResults, int firstResult) {
        return findConvenioEntities(false, maxResults, firstResult);
    }

    private List<Convenio> findConvenioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Convenio.class));
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

    public Convenio findConvenio(ConvenioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Convenio.class, id);
        } finally {
            em.close();
        }
    }

    public int getConvenioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Convenio> rt = cq.from(Convenio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
