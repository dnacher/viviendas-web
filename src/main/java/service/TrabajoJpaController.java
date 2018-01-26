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
import entities.Unidad;
import entities.Estado;
import entities.Grupo;
import entities.Urgencia;
import entities.Tipoduracion;
import entities.Trabajoxmaterial;
import entities.Historialtrabajo;
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
public class TrabajoJpaController implements Serializable {

    public TrabajoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajo trabajo) throws RollbackFailureException, Exception {
        if (trabajo.getHistorialtrabajoList() == null) {
            trabajo.setHistorialtrabajoList(new ArrayList<Historialtrabajo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Unidad unidadidUnidad = trabajo.getUnidadidUnidad();
            if (unidadidUnidad != null) {
                unidadidUnidad = em.getReference(unidadidUnidad.getClass(), unidadidUnidad.getIdUnidad());
                trabajo.setUnidadidUnidad(unidadidUnidad);
            }
            Estado estadoIdestado = trabajo.getEstadoIdestado();
            if (estadoIdestado != null) {
                estadoIdestado = em.getReference(estadoIdestado.getClass(), estadoIdestado.getIdestado());
                trabajo.setEstadoIdestado(estadoIdestado);
            }
            Grupo grupoIdgrupo = trabajo.getGrupoIdgrupo();
            if (grupoIdgrupo != null) {
                grupoIdgrupo = em.getReference(grupoIdgrupo.getClass(), grupoIdgrupo.getIdgrupo());
                trabajo.setGrupoIdgrupo(grupoIdgrupo);
            }
            Urgencia urgenciaIdurgencia = trabajo.getUrgenciaIdurgencia();
            if (urgenciaIdurgencia != null) {
                urgenciaIdurgencia = em.getReference(urgenciaIdurgencia.getClass(), urgenciaIdurgencia.getIdurgencia());
                trabajo.setUrgenciaIdurgencia(urgenciaIdurgencia);
            }
            Tipoduracion tipoDuracionidtipoDuracion = trabajo.getTipoDuracionidtipoDuracion();
            if (tipoDuracionidtipoDuracion != null) {
                tipoDuracionidtipoDuracion = em.getReference(tipoDuracionidtipoDuracion.getClass(), tipoDuracionidtipoDuracion.getIdtipoDuracion());
                trabajo.setTipoDuracionidtipoDuracion(tipoDuracionidtipoDuracion);
            }
            Trabajoxmaterial trabajoxmaterial = trabajo.getTrabajoxmaterial();
            if (trabajoxmaterial != null) {
                trabajoxmaterial = em.getReference(trabajoxmaterial.getClass(), trabajoxmaterial.getTrabajoidTrabajo());
                trabajo.setTrabajoxmaterial(trabajoxmaterial);
            }
            List<Historialtrabajo> attachedHistorialtrabajoList = new ArrayList<Historialtrabajo>();
            for (Historialtrabajo historialtrabajoListHistorialtrabajoToAttach : trabajo.getHistorialtrabajoList()) {
                historialtrabajoListHistorialtrabajoToAttach = em.getReference(historialtrabajoListHistorialtrabajoToAttach.getClass(), historialtrabajoListHistorialtrabajoToAttach.getHistorialtrabajoPK());
                attachedHistorialtrabajoList.add(historialtrabajoListHistorialtrabajoToAttach);
            }
            trabajo.setHistorialtrabajoList(attachedHistorialtrabajoList);
            em.persist(trabajo);
            if (unidadidUnidad != null) {
                unidadidUnidad.getTrabajoList().add(trabajo);
                unidadidUnidad = em.merge(unidadidUnidad);
            }
            if (estadoIdestado != null) {
                estadoIdestado.getTrabajoList().add(trabajo);
                estadoIdestado = em.merge(estadoIdestado);
            }
            if (grupoIdgrupo != null) {
                grupoIdgrupo.getTrabajoList().add(trabajo);
                grupoIdgrupo = em.merge(grupoIdgrupo);
            }
            if (urgenciaIdurgencia != null) {
                urgenciaIdurgencia.getTrabajoList().add(trabajo);
                urgenciaIdurgencia = em.merge(urgenciaIdurgencia);
            }
            if (tipoDuracionidtipoDuracion != null) {
                tipoDuracionidtipoDuracion.getTrabajoList().add(trabajo);
                tipoDuracionidtipoDuracion = em.merge(tipoDuracionidtipoDuracion);
            }
            if (trabajoxmaterial != null) {
                Trabajo oldTrabajoOfTrabajoxmaterial = trabajoxmaterial.getTrabajo();
                if (oldTrabajoOfTrabajoxmaterial != null) {
                    oldTrabajoOfTrabajoxmaterial.setTrabajoxmaterial(null);
                    oldTrabajoOfTrabajoxmaterial = em.merge(oldTrabajoOfTrabajoxmaterial);
                }
                trabajoxmaterial.setTrabajo(trabajo);
                trabajoxmaterial = em.merge(trabajoxmaterial);
            }
            for (Historialtrabajo historialtrabajoListHistorialtrabajo : trabajo.getHistorialtrabajoList()) {
                Trabajo oldTrabajoOfHistorialtrabajoListHistorialtrabajo = historialtrabajoListHistorialtrabajo.getTrabajo();
                historialtrabajoListHistorialtrabajo.setTrabajo(trabajo);
                historialtrabajoListHistorialtrabajo = em.merge(historialtrabajoListHistorialtrabajo);
                if (oldTrabajoOfHistorialtrabajoListHistorialtrabajo != null) {
                    oldTrabajoOfHistorialtrabajoListHistorialtrabajo.getHistorialtrabajoList().remove(historialtrabajoListHistorialtrabajo);
                    oldTrabajoOfHistorialtrabajoListHistorialtrabajo = em.merge(oldTrabajoOfHistorialtrabajoListHistorialtrabajo);
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

    public void edit(Trabajo trabajo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Trabajo persistentTrabajo = em.find(Trabajo.class, trabajo.getIdTrabajo());
            Unidad unidadidUnidadOld = persistentTrabajo.getUnidadidUnidad();
            Unidad unidadidUnidadNew = trabajo.getUnidadidUnidad();
            Estado estadoIdestadoOld = persistentTrabajo.getEstadoIdestado();
            Estado estadoIdestadoNew = trabajo.getEstadoIdestado();
            Grupo grupoIdgrupoOld = persistentTrabajo.getGrupoIdgrupo();
            Grupo grupoIdgrupoNew = trabajo.getGrupoIdgrupo();
            Urgencia urgenciaIdurgenciaOld = persistentTrabajo.getUrgenciaIdurgencia();
            Urgencia urgenciaIdurgenciaNew = trabajo.getUrgenciaIdurgencia();
            Tipoduracion tipoDuracionidtipoDuracionOld = persistentTrabajo.getTipoDuracionidtipoDuracion();
            Tipoduracion tipoDuracionidtipoDuracionNew = trabajo.getTipoDuracionidtipoDuracion();
            Trabajoxmaterial trabajoxmaterialOld = persistentTrabajo.getTrabajoxmaterial();
            Trabajoxmaterial trabajoxmaterialNew = trabajo.getTrabajoxmaterial();
            List<Historialtrabajo> historialtrabajoListOld = persistentTrabajo.getHistorialtrabajoList();
            List<Historialtrabajo> historialtrabajoListNew = trabajo.getHistorialtrabajoList();
            List<String> illegalOrphanMessages = null;
            if (trabajoxmaterialOld != null && !trabajoxmaterialOld.equals(trabajoxmaterialNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Trabajoxmaterial " + trabajoxmaterialOld + " since its trabajo field is not nullable.");
            }
            for (Historialtrabajo historialtrabajoListOldHistorialtrabajo : historialtrabajoListOld) {
                if (!historialtrabajoListNew.contains(historialtrabajoListOldHistorialtrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialtrabajo " + historialtrabajoListOldHistorialtrabajo + " since its trabajo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (unidadidUnidadNew != null) {
                unidadidUnidadNew = em.getReference(unidadidUnidadNew.getClass(), unidadidUnidadNew.getIdUnidad());
                trabajo.setUnidadidUnidad(unidadidUnidadNew);
            }
            if (estadoIdestadoNew != null) {
                estadoIdestadoNew = em.getReference(estadoIdestadoNew.getClass(), estadoIdestadoNew.getIdestado());
                trabajo.setEstadoIdestado(estadoIdestadoNew);
            }
            if (grupoIdgrupoNew != null) {
                grupoIdgrupoNew = em.getReference(grupoIdgrupoNew.getClass(), grupoIdgrupoNew.getIdgrupo());
                trabajo.setGrupoIdgrupo(grupoIdgrupoNew);
            }
            if (urgenciaIdurgenciaNew != null) {
                urgenciaIdurgenciaNew = em.getReference(urgenciaIdurgenciaNew.getClass(), urgenciaIdurgenciaNew.getIdurgencia());
                trabajo.setUrgenciaIdurgencia(urgenciaIdurgenciaNew);
            }
            if (tipoDuracionidtipoDuracionNew != null) {
                tipoDuracionidtipoDuracionNew = em.getReference(tipoDuracionidtipoDuracionNew.getClass(), tipoDuracionidtipoDuracionNew.getIdtipoDuracion());
                trabajo.setTipoDuracionidtipoDuracion(tipoDuracionidtipoDuracionNew);
            }
            if (trabajoxmaterialNew != null) {
                trabajoxmaterialNew = em.getReference(trabajoxmaterialNew.getClass(), trabajoxmaterialNew.getTrabajoidTrabajo());
                trabajo.setTrabajoxmaterial(trabajoxmaterialNew);
            }
            List<Historialtrabajo> attachedHistorialtrabajoListNew = new ArrayList<Historialtrabajo>();
            for (Historialtrabajo historialtrabajoListNewHistorialtrabajoToAttach : historialtrabajoListNew) {
                historialtrabajoListNewHistorialtrabajoToAttach = em.getReference(historialtrabajoListNewHistorialtrabajoToAttach.getClass(), historialtrabajoListNewHistorialtrabajoToAttach.getHistorialtrabajoPK());
                attachedHistorialtrabajoListNew.add(historialtrabajoListNewHistorialtrabajoToAttach);
            }
            historialtrabajoListNew = attachedHistorialtrabajoListNew;
            trabajo.setHistorialtrabajoList(historialtrabajoListNew);
            trabajo = em.merge(trabajo);
            if (unidadidUnidadOld != null && !unidadidUnidadOld.equals(unidadidUnidadNew)) {
                unidadidUnidadOld.getTrabajoList().remove(trabajo);
                unidadidUnidadOld = em.merge(unidadidUnidadOld);
            }
            if (unidadidUnidadNew != null && !unidadidUnidadNew.equals(unidadidUnidadOld)) {
                unidadidUnidadNew.getTrabajoList().add(trabajo);
                unidadidUnidadNew = em.merge(unidadidUnidadNew);
            }
            if (estadoIdestadoOld != null && !estadoIdestadoOld.equals(estadoIdestadoNew)) {
                estadoIdestadoOld.getTrabajoList().remove(trabajo);
                estadoIdestadoOld = em.merge(estadoIdestadoOld);
            }
            if (estadoIdestadoNew != null && !estadoIdestadoNew.equals(estadoIdestadoOld)) {
                estadoIdestadoNew.getTrabajoList().add(trabajo);
                estadoIdestadoNew = em.merge(estadoIdestadoNew);
            }
            if (grupoIdgrupoOld != null && !grupoIdgrupoOld.equals(grupoIdgrupoNew)) {
                grupoIdgrupoOld.getTrabajoList().remove(trabajo);
                grupoIdgrupoOld = em.merge(grupoIdgrupoOld);
            }
            if (grupoIdgrupoNew != null && !grupoIdgrupoNew.equals(grupoIdgrupoOld)) {
                grupoIdgrupoNew.getTrabajoList().add(trabajo);
                grupoIdgrupoNew = em.merge(grupoIdgrupoNew);
            }
            if (urgenciaIdurgenciaOld != null && !urgenciaIdurgenciaOld.equals(urgenciaIdurgenciaNew)) {
                urgenciaIdurgenciaOld.getTrabajoList().remove(trabajo);
                urgenciaIdurgenciaOld = em.merge(urgenciaIdurgenciaOld);
            }
            if (urgenciaIdurgenciaNew != null && !urgenciaIdurgenciaNew.equals(urgenciaIdurgenciaOld)) {
                urgenciaIdurgenciaNew.getTrabajoList().add(trabajo);
                urgenciaIdurgenciaNew = em.merge(urgenciaIdurgenciaNew);
            }
            if (tipoDuracionidtipoDuracionOld != null && !tipoDuracionidtipoDuracionOld.equals(tipoDuracionidtipoDuracionNew)) {
                tipoDuracionidtipoDuracionOld.getTrabajoList().remove(trabajo);
                tipoDuracionidtipoDuracionOld = em.merge(tipoDuracionidtipoDuracionOld);
            }
            if (tipoDuracionidtipoDuracionNew != null && !tipoDuracionidtipoDuracionNew.equals(tipoDuracionidtipoDuracionOld)) {
                tipoDuracionidtipoDuracionNew.getTrabajoList().add(trabajo);
                tipoDuracionidtipoDuracionNew = em.merge(tipoDuracionidtipoDuracionNew);
            }
            if (trabajoxmaterialNew != null && !trabajoxmaterialNew.equals(trabajoxmaterialOld)) {
                Trabajo oldTrabajoOfTrabajoxmaterial = trabajoxmaterialNew.getTrabajo();
                if (oldTrabajoOfTrabajoxmaterial != null) {
                    oldTrabajoOfTrabajoxmaterial.setTrabajoxmaterial(null);
                    oldTrabajoOfTrabajoxmaterial = em.merge(oldTrabajoOfTrabajoxmaterial);
                }
                trabajoxmaterialNew.setTrabajo(trabajo);
                trabajoxmaterialNew = em.merge(trabajoxmaterialNew);
            }
            for (Historialtrabajo historialtrabajoListNewHistorialtrabajo : historialtrabajoListNew) {
                if (!historialtrabajoListOld.contains(historialtrabajoListNewHistorialtrabajo)) {
                    Trabajo oldTrabajoOfHistorialtrabajoListNewHistorialtrabajo = historialtrabajoListNewHistorialtrabajo.getTrabajo();
                    historialtrabajoListNewHistorialtrabajo.setTrabajo(trabajo);
                    historialtrabajoListNewHistorialtrabajo = em.merge(historialtrabajoListNewHistorialtrabajo);
                    if (oldTrabajoOfHistorialtrabajoListNewHistorialtrabajo != null && !oldTrabajoOfHistorialtrabajoListNewHistorialtrabajo.equals(trabajo)) {
                        oldTrabajoOfHistorialtrabajoListNewHistorialtrabajo.getHistorialtrabajoList().remove(historialtrabajoListNewHistorialtrabajo);
                        oldTrabajoOfHistorialtrabajoListNewHistorialtrabajo = em.merge(oldTrabajoOfHistorialtrabajoListNewHistorialtrabajo);
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
                Integer id = trabajo.getIdTrabajo();
                if (findTrabajo(id) == null) {
                    throw new NonexistentEntityException("The trabajo with id " + id + " no longer exists.");
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
            Trabajo trabajo;
            try {
                trabajo = em.getReference(Trabajo.class, id);
                trabajo.getIdTrabajo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Trabajoxmaterial trabajoxmaterialOrphanCheck = trabajo.getTrabajoxmaterial();
            if (trabajoxmaterialOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajo (" + trabajo + ") cannot be destroyed since the Trabajoxmaterial " + trabajoxmaterialOrphanCheck + " in its trabajoxmaterial field has a non-nullable trabajo field.");
            }
            List<Historialtrabajo> historialtrabajoListOrphanCheck = trabajo.getHistorialtrabajoList();
            for (Historialtrabajo historialtrabajoListOrphanCheckHistorialtrabajo : historialtrabajoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Trabajo (" + trabajo + ") cannot be destroyed since the Historialtrabajo " + historialtrabajoListOrphanCheckHistorialtrabajo + " in its historialtrabajoList field has a non-nullable trabajo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Unidad unidadidUnidad = trabajo.getUnidadidUnidad();
            if (unidadidUnidad != null) {
                unidadidUnidad.getTrabajoList().remove(trabajo);
                unidadidUnidad = em.merge(unidadidUnidad);
            }
            Estado estadoIdestado = trabajo.getEstadoIdestado();
            if (estadoIdestado != null) {
                estadoIdestado.getTrabajoList().remove(trabajo);
                estadoIdestado = em.merge(estadoIdestado);
            }
            Grupo grupoIdgrupo = trabajo.getGrupoIdgrupo();
            if (grupoIdgrupo != null) {
                grupoIdgrupo.getTrabajoList().remove(trabajo);
                grupoIdgrupo = em.merge(grupoIdgrupo);
            }
            Urgencia urgenciaIdurgencia = trabajo.getUrgenciaIdurgencia();
            if (urgenciaIdurgencia != null) {
                urgenciaIdurgencia.getTrabajoList().remove(trabajo);
                urgenciaIdurgencia = em.merge(urgenciaIdurgencia);
            }
            Tipoduracion tipoDuracionidtipoDuracion = trabajo.getTipoDuracionidtipoDuracion();
            if (tipoDuracionidtipoDuracion != null) {
                tipoDuracionidtipoDuracion.getTrabajoList().remove(trabajo);
                tipoDuracionidtipoDuracion = em.merge(tipoDuracionidtipoDuracion);
            }
            em.remove(trabajo);
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

    public List<Trabajo> findTrabajoEntities() {
        return findTrabajoEntities(true, -1, -1);
    }

    public List<Trabajo> findTrabajoEntities(int maxResults, int firstResult) {
        return findTrabajoEntities(false, maxResults, firstResult);
    }

    private List<Trabajo> findTrabajoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajo.class));
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

    public Trabajo findTrabajo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajo> rt = cq.from(Trabajo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
