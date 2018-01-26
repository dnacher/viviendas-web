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
import entities.Trabajo;
import java.util.ArrayList;
import java.util.List;
import entities.Convenio;
import entities.Gastoscomunes;
import entities.Otrosgastos;
import entities.Unidad;
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
public class UnidadJpaController implements Serializable {

    public UnidadJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Unidad unidad) throws RollbackFailureException, Exception {
        if (unidad.getTrabajoList() == null) {
            unidad.setTrabajoList(new ArrayList<Trabajo>());
        }
        if (unidad.getConvenioList() == null) {
            unidad.setConvenioList(new ArrayList<Convenio>());
        }
        if (unidad.getGastoscomunesList() == null) {
            unidad.setGastoscomunesList(new ArrayList<Gastoscomunes>());
        }
        if (unidad.getOtrosgastosList() == null) {
            unidad.setOtrosgastosList(new ArrayList<Otrosgastos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trabajo> attachedTrabajoList = new ArrayList<Trabajo>();
            for (Trabajo trabajoListTrabajoToAttach : unidad.getTrabajoList()) {
                trabajoListTrabajoToAttach = em.getReference(trabajoListTrabajoToAttach.getClass(), trabajoListTrabajoToAttach.getIdTrabajo());
                attachedTrabajoList.add(trabajoListTrabajoToAttach);
            }
            unidad.setTrabajoList(attachedTrabajoList);
            List<Convenio> attachedConvenioList = new ArrayList<Convenio>();
            for (Convenio convenioListConvenioToAttach : unidad.getConvenioList()) {
                convenioListConvenioToAttach = em.getReference(convenioListConvenioToAttach.getClass(), convenioListConvenioToAttach.getConvenioPK());
                attachedConvenioList.add(convenioListConvenioToAttach);
            }
            unidad.setConvenioList(attachedConvenioList);
            List<Gastoscomunes> attachedGastoscomunesList = new ArrayList<Gastoscomunes>();
            for (Gastoscomunes gastoscomunesListGastoscomunesToAttach : unidad.getGastoscomunesList()) {
                gastoscomunesListGastoscomunesToAttach = em.getReference(gastoscomunesListGastoscomunesToAttach.getClass(), gastoscomunesListGastoscomunesToAttach.getGastoscomunesPK());
                attachedGastoscomunesList.add(gastoscomunesListGastoscomunesToAttach);
            }
            unidad.setGastoscomunesList(attachedGastoscomunesList);
            List<Otrosgastos> attachedOtrosgastosList = new ArrayList<Otrosgastos>();
            for (Otrosgastos otrosgastosListOtrosgastosToAttach : unidad.getOtrosgastosList()) {
                otrosgastosListOtrosgastosToAttach = em.getReference(otrosgastosListOtrosgastosToAttach.getClass(), otrosgastosListOtrosgastosToAttach.getOtrosgastosPK());
                attachedOtrosgastosList.add(otrosgastosListOtrosgastosToAttach);
            }
            unidad.setOtrosgastosList(attachedOtrosgastosList);
            em.persist(unidad);
            for (Trabajo trabajoListTrabajo : unidad.getTrabajoList()) {
                Unidad oldUnidadidUnidadOfTrabajoListTrabajo = trabajoListTrabajo.getUnidadidUnidad();
                trabajoListTrabajo.setUnidadidUnidad(unidad);
                trabajoListTrabajo = em.merge(trabajoListTrabajo);
                if (oldUnidadidUnidadOfTrabajoListTrabajo != null) {
                    oldUnidadidUnidadOfTrabajoListTrabajo.getTrabajoList().remove(trabajoListTrabajo);
                    oldUnidadidUnidadOfTrabajoListTrabajo = em.merge(oldUnidadidUnidadOfTrabajoListTrabajo);
                }
            }
            for (Convenio convenioListConvenio : unidad.getConvenioList()) {
                Unidad oldUnidadOfConvenioListConvenio = convenioListConvenio.getUnidad();
                convenioListConvenio.setUnidad(unidad);
                convenioListConvenio = em.merge(convenioListConvenio);
                if (oldUnidadOfConvenioListConvenio != null) {
                    oldUnidadOfConvenioListConvenio.getConvenioList().remove(convenioListConvenio);
                    oldUnidadOfConvenioListConvenio = em.merge(oldUnidadOfConvenioListConvenio);
                }
            }
            for (Gastoscomunes gastoscomunesListGastoscomunes : unidad.getGastoscomunesList()) {
                Unidad oldUnidadOfGastoscomunesListGastoscomunes = gastoscomunesListGastoscomunes.getUnidad();
                gastoscomunesListGastoscomunes.setUnidad(unidad);
                gastoscomunesListGastoscomunes = em.merge(gastoscomunesListGastoscomunes);
                if (oldUnidadOfGastoscomunesListGastoscomunes != null) {
                    oldUnidadOfGastoscomunesListGastoscomunes.getGastoscomunesList().remove(gastoscomunesListGastoscomunes);
                    oldUnidadOfGastoscomunesListGastoscomunes = em.merge(oldUnidadOfGastoscomunesListGastoscomunes);
                }
            }
            for (Otrosgastos otrosgastosListOtrosgastos : unidad.getOtrosgastosList()) {
                Unidad oldUnidadOfOtrosgastosListOtrosgastos = otrosgastosListOtrosgastos.getUnidad();
                otrosgastosListOtrosgastos.setUnidad(unidad);
                otrosgastosListOtrosgastos = em.merge(otrosgastosListOtrosgastos);
                if (oldUnidadOfOtrosgastosListOtrosgastos != null) {
                    oldUnidadOfOtrosgastosListOtrosgastos.getOtrosgastosList().remove(otrosgastosListOtrosgastos);
                    oldUnidadOfOtrosgastosListOtrosgastos = em.merge(oldUnidadOfOtrosgastosListOtrosgastos);
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

    public void edit(Unidad unidad) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Unidad persistentUnidad = em.find(Unidad.class, unidad.getIdUnidad());
            List<Trabajo> trabajoListOld = persistentUnidad.getTrabajoList();
            List<Trabajo> trabajoListNew = unidad.getTrabajoList();
            List<Convenio> convenioListOld = persistentUnidad.getConvenioList();
            List<Convenio> convenioListNew = unidad.getConvenioList();
            List<Gastoscomunes> gastoscomunesListOld = persistentUnidad.getGastoscomunesList();
            List<Gastoscomunes> gastoscomunesListNew = unidad.getGastoscomunesList();
            List<Otrosgastos> otrosgastosListOld = persistentUnidad.getOtrosgastosList();
            List<Otrosgastos> otrosgastosListNew = unidad.getOtrosgastosList();
            List<String> illegalOrphanMessages = null;
            for (Trabajo trabajoListOldTrabajo : trabajoListOld) {
                if (!trabajoListNew.contains(trabajoListOldTrabajo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trabajo " + trabajoListOldTrabajo + " since its unidadidUnidad field is not nullable.");
                }
            }
            for (Convenio convenioListOldConvenio : convenioListOld) {
                if (!convenioListNew.contains(convenioListOldConvenio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Convenio " + convenioListOldConvenio + " since its unidad field is not nullable.");
                }
            }
            for (Gastoscomunes gastoscomunesListOldGastoscomunes : gastoscomunesListOld) {
                if (!gastoscomunesListNew.contains(gastoscomunesListOldGastoscomunes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gastoscomunes " + gastoscomunesListOldGastoscomunes + " since its unidad field is not nullable.");
                }
            }
            for (Otrosgastos otrosgastosListOldOtrosgastos : otrosgastosListOld) {
                if (!otrosgastosListNew.contains(otrosgastosListOldOtrosgastos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Otrosgastos " + otrosgastosListOldOtrosgastos + " since its unidad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Trabajo> attachedTrabajoListNew = new ArrayList<Trabajo>();
            for (Trabajo trabajoListNewTrabajoToAttach : trabajoListNew) {
                trabajoListNewTrabajoToAttach = em.getReference(trabajoListNewTrabajoToAttach.getClass(), trabajoListNewTrabajoToAttach.getIdTrabajo());
                attachedTrabajoListNew.add(trabajoListNewTrabajoToAttach);
            }
            trabajoListNew = attachedTrabajoListNew;
            unidad.setTrabajoList(trabajoListNew);
            List<Convenio> attachedConvenioListNew = new ArrayList<Convenio>();
            for (Convenio convenioListNewConvenioToAttach : convenioListNew) {
                convenioListNewConvenioToAttach = em.getReference(convenioListNewConvenioToAttach.getClass(), convenioListNewConvenioToAttach.getConvenioPK());
                attachedConvenioListNew.add(convenioListNewConvenioToAttach);
            }
            convenioListNew = attachedConvenioListNew;
            unidad.setConvenioList(convenioListNew);
            List<Gastoscomunes> attachedGastoscomunesListNew = new ArrayList<Gastoscomunes>();
            for (Gastoscomunes gastoscomunesListNewGastoscomunesToAttach : gastoscomunesListNew) {
                gastoscomunesListNewGastoscomunesToAttach = em.getReference(gastoscomunesListNewGastoscomunesToAttach.getClass(), gastoscomunesListNewGastoscomunesToAttach.getGastoscomunesPK());
                attachedGastoscomunesListNew.add(gastoscomunesListNewGastoscomunesToAttach);
            }
            gastoscomunesListNew = attachedGastoscomunesListNew;
            unidad.setGastoscomunesList(gastoscomunesListNew);
            List<Otrosgastos> attachedOtrosgastosListNew = new ArrayList<Otrosgastos>();
            for (Otrosgastos otrosgastosListNewOtrosgastosToAttach : otrosgastosListNew) {
                otrosgastosListNewOtrosgastosToAttach = em.getReference(otrosgastosListNewOtrosgastosToAttach.getClass(), otrosgastosListNewOtrosgastosToAttach.getOtrosgastosPK());
                attachedOtrosgastosListNew.add(otrosgastosListNewOtrosgastosToAttach);
            }
            otrosgastosListNew = attachedOtrosgastosListNew;
            unidad.setOtrosgastosList(otrosgastosListNew);
            unidad = em.merge(unidad);
            for (Trabajo trabajoListNewTrabajo : trabajoListNew) {
                if (!trabajoListOld.contains(trabajoListNewTrabajo)) {
                    Unidad oldUnidadidUnidadOfTrabajoListNewTrabajo = trabajoListNewTrabajo.getUnidadidUnidad();
                    trabajoListNewTrabajo.setUnidadidUnidad(unidad);
                    trabajoListNewTrabajo = em.merge(trabajoListNewTrabajo);
                    if (oldUnidadidUnidadOfTrabajoListNewTrabajo != null && !oldUnidadidUnidadOfTrabajoListNewTrabajo.equals(unidad)) {
                        oldUnidadidUnidadOfTrabajoListNewTrabajo.getTrabajoList().remove(trabajoListNewTrabajo);
                        oldUnidadidUnidadOfTrabajoListNewTrabajo = em.merge(oldUnidadidUnidadOfTrabajoListNewTrabajo);
                    }
                }
            }
            for (Convenio convenioListNewConvenio : convenioListNew) {
                if (!convenioListOld.contains(convenioListNewConvenio)) {
                    Unidad oldUnidadOfConvenioListNewConvenio = convenioListNewConvenio.getUnidad();
                    convenioListNewConvenio.setUnidad(unidad);
                    convenioListNewConvenio = em.merge(convenioListNewConvenio);
                    if (oldUnidadOfConvenioListNewConvenio != null && !oldUnidadOfConvenioListNewConvenio.equals(unidad)) {
                        oldUnidadOfConvenioListNewConvenio.getConvenioList().remove(convenioListNewConvenio);
                        oldUnidadOfConvenioListNewConvenio = em.merge(oldUnidadOfConvenioListNewConvenio);
                    }
                }
            }
            for (Gastoscomunes gastoscomunesListNewGastoscomunes : gastoscomunesListNew) {
                if (!gastoscomunesListOld.contains(gastoscomunesListNewGastoscomunes)) {
                    Unidad oldUnidadOfGastoscomunesListNewGastoscomunes = gastoscomunesListNewGastoscomunes.getUnidad();
                    gastoscomunesListNewGastoscomunes.setUnidad(unidad);
                    gastoscomunesListNewGastoscomunes = em.merge(gastoscomunesListNewGastoscomunes);
                    if (oldUnidadOfGastoscomunesListNewGastoscomunes != null && !oldUnidadOfGastoscomunesListNewGastoscomunes.equals(unidad)) {
                        oldUnidadOfGastoscomunesListNewGastoscomunes.getGastoscomunesList().remove(gastoscomunesListNewGastoscomunes);
                        oldUnidadOfGastoscomunesListNewGastoscomunes = em.merge(oldUnidadOfGastoscomunesListNewGastoscomunes);
                    }
                }
            }
            for (Otrosgastos otrosgastosListNewOtrosgastos : otrosgastosListNew) {
                if (!otrosgastosListOld.contains(otrosgastosListNewOtrosgastos)) {
                    Unidad oldUnidadOfOtrosgastosListNewOtrosgastos = otrosgastosListNewOtrosgastos.getUnidad();
                    otrosgastosListNewOtrosgastos.setUnidad(unidad);
                    otrosgastosListNewOtrosgastos = em.merge(otrosgastosListNewOtrosgastos);
                    if (oldUnidadOfOtrosgastosListNewOtrosgastos != null && !oldUnidadOfOtrosgastosListNewOtrosgastos.equals(unidad)) {
                        oldUnidadOfOtrosgastosListNewOtrosgastos.getOtrosgastosList().remove(otrosgastosListNewOtrosgastos);
                        oldUnidadOfOtrosgastosListNewOtrosgastos = em.merge(oldUnidadOfOtrosgastosListNewOtrosgastos);
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
                Integer id = unidad.getIdUnidad();
                if (findUnidad(id) == null) {
                    throw new NonexistentEntityException("The unidad with id " + id + " no longer exists.");
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
            Unidad unidad;
            try {
                unidad = em.getReference(Unidad.class, id);
                unidad.getIdUnidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trabajo> trabajoListOrphanCheck = unidad.getTrabajoList();
            for (Trabajo trabajoListOrphanCheckTrabajo : trabajoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Unidad (" + unidad + ") cannot be destroyed since the Trabajo " + trabajoListOrphanCheckTrabajo + " in its trabajoList field has a non-nullable unidadidUnidad field.");
            }
            List<Convenio> convenioListOrphanCheck = unidad.getConvenioList();
            for (Convenio convenioListOrphanCheckConvenio : convenioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Unidad (" + unidad + ") cannot be destroyed since the Convenio " + convenioListOrphanCheckConvenio + " in its convenioList field has a non-nullable unidad field.");
            }
            List<Gastoscomunes> gastoscomunesListOrphanCheck = unidad.getGastoscomunesList();
            for (Gastoscomunes gastoscomunesListOrphanCheckGastoscomunes : gastoscomunesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Unidad (" + unidad + ") cannot be destroyed since the Gastoscomunes " + gastoscomunesListOrphanCheckGastoscomunes + " in its gastoscomunesList field has a non-nullable unidad field.");
            }
            List<Otrosgastos> otrosgastosListOrphanCheck = unidad.getOtrosgastosList();
            for (Otrosgastos otrosgastosListOrphanCheckOtrosgastos : otrosgastosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Unidad (" + unidad + ") cannot be destroyed since the Otrosgastos " + otrosgastosListOrphanCheckOtrosgastos + " in its otrosgastosList field has a non-nullable unidad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(unidad);
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

    public List<Unidad> findUnidadEntities() {
        return findUnidadEntities(true, -1, -1);
    }

    public List<Unidad> findUnidadEntities(int maxResults, int firstResult) {
        return findUnidadEntities(false, maxResults, firstResult);
    }

    private List<Unidad> findUnidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Unidad.class));
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

    public Unidad findUnidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Unidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Unidad> rt = cq.from(Unidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
