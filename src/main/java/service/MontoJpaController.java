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
import java.util.ArrayList;
import java.util.List;
import entities.Cuotaconvenio;
import entities.Gastoscomunes;
import entities.Historialmonto;
import entities.Monto;
import entities.Otrosgastos;
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
public class MontoJpaController implements Serializable {

    public MontoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Monto monto) throws RollbackFailureException, Exception {
        if (monto.getConvenioList() == null) {
            monto.setConvenioList(new ArrayList<Convenio>());
        }
        if (monto.getCuotaconvenioList() == null) {
            monto.setCuotaconvenioList(new ArrayList<Cuotaconvenio>());
        }
        if (monto.getGastoscomunesList() == null) {
            monto.setGastoscomunesList(new ArrayList<Gastoscomunes>());
        }
        if (monto.getHistorialmontoList() == null) {
            monto.setHistorialmontoList(new ArrayList<Historialmonto>());
        }
        if (monto.getOtrosgastosList() == null) {
            monto.setOtrosgastosList(new ArrayList<Otrosgastos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Convenio> attachedConvenioList = new ArrayList<Convenio>();
            for (Convenio convenioListConvenioToAttach : monto.getConvenioList()) {
                convenioListConvenioToAttach = em.getReference(convenioListConvenioToAttach.getClass(), convenioListConvenioToAttach.getConvenioPK());
                attachedConvenioList.add(convenioListConvenioToAttach);
            }
            monto.setConvenioList(attachedConvenioList);
            List<Cuotaconvenio> attachedCuotaconvenioList = new ArrayList<Cuotaconvenio>();
            for (Cuotaconvenio cuotaconvenioListCuotaconvenioToAttach : monto.getCuotaconvenioList()) {
                cuotaconvenioListCuotaconvenioToAttach = em.getReference(cuotaconvenioListCuotaconvenioToAttach.getClass(), cuotaconvenioListCuotaconvenioToAttach.getCuotaconvenioPK());
                attachedCuotaconvenioList.add(cuotaconvenioListCuotaconvenioToAttach);
            }
            monto.setCuotaconvenioList(attachedCuotaconvenioList);
            List<Gastoscomunes> attachedGastoscomunesList = new ArrayList<Gastoscomunes>();
            for (Gastoscomunes gastoscomunesListGastoscomunesToAttach : monto.getGastoscomunesList()) {
                gastoscomunesListGastoscomunesToAttach = em.getReference(gastoscomunesListGastoscomunesToAttach.getClass(), gastoscomunesListGastoscomunesToAttach.getGastoscomunesPK());
                attachedGastoscomunesList.add(gastoscomunesListGastoscomunesToAttach);
            }
            monto.setGastoscomunesList(attachedGastoscomunesList);
            List<Historialmonto> attachedHistorialmontoList = new ArrayList<Historialmonto>();
            for (Historialmonto historialmontoListHistorialmontoToAttach : monto.getHistorialmontoList()) {
                historialmontoListHistorialmontoToAttach = em.getReference(historialmontoListHistorialmontoToAttach.getClass(), historialmontoListHistorialmontoToAttach.getHistorialmontoPK());
                attachedHistorialmontoList.add(historialmontoListHistorialmontoToAttach);
            }
            monto.setHistorialmontoList(attachedHistorialmontoList);
            List<Otrosgastos> attachedOtrosgastosList = new ArrayList<Otrosgastos>();
            for (Otrosgastos otrosgastosListOtrosgastosToAttach : monto.getOtrosgastosList()) {
                otrosgastosListOtrosgastosToAttach = em.getReference(otrosgastosListOtrosgastosToAttach.getClass(), otrosgastosListOtrosgastosToAttach.getOtrosgastosPK());
                attachedOtrosgastosList.add(otrosgastosListOtrosgastosToAttach);
            }
            monto.setOtrosgastosList(attachedOtrosgastosList);
            em.persist(monto);
            for (Convenio convenioListConvenio : monto.getConvenioList()) {
                Monto oldMontoIdmontoOfConvenioListConvenio = convenioListConvenio.getMontoIdmonto();
                convenioListConvenio.setMontoIdmonto(monto);
                convenioListConvenio = em.merge(convenioListConvenio);
                if (oldMontoIdmontoOfConvenioListConvenio != null) {
                    oldMontoIdmontoOfConvenioListConvenio.getConvenioList().remove(convenioListConvenio);
                    oldMontoIdmontoOfConvenioListConvenio = em.merge(oldMontoIdmontoOfConvenioListConvenio);
                }
            }
            for (Cuotaconvenio cuotaconvenioListCuotaconvenio : monto.getCuotaconvenioList()) {
                Monto oldMontoOfCuotaconvenioListCuotaconvenio = cuotaconvenioListCuotaconvenio.getMonto();
                cuotaconvenioListCuotaconvenio.setMonto(monto);
                cuotaconvenioListCuotaconvenio = em.merge(cuotaconvenioListCuotaconvenio);
                if (oldMontoOfCuotaconvenioListCuotaconvenio != null) {
                    oldMontoOfCuotaconvenioListCuotaconvenio.getCuotaconvenioList().remove(cuotaconvenioListCuotaconvenio);
                    oldMontoOfCuotaconvenioListCuotaconvenio = em.merge(oldMontoOfCuotaconvenioListCuotaconvenio);
                }
            }
            for (Gastoscomunes gastoscomunesListGastoscomunes : monto.getGastoscomunesList()) {
                Monto oldMontoIdmontoOfGastoscomunesListGastoscomunes = gastoscomunesListGastoscomunes.getMontoIdmonto();
                gastoscomunesListGastoscomunes.setMontoIdmonto(monto);
                gastoscomunesListGastoscomunes = em.merge(gastoscomunesListGastoscomunes);
                if (oldMontoIdmontoOfGastoscomunesListGastoscomunes != null) {
                    oldMontoIdmontoOfGastoscomunesListGastoscomunes.getGastoscomunesList().remove(gastoscomunesListGastoscomunes);
                    oldMontoIdmontoOfGastoscomunesListGastoscomunes = em.merge(oldMontoIdmontoOfGastoscomunesListGastoscomunes);
                }
            }
            for (Historialmonto historialmontoListHistorialmonto : monto.getHistorialmontoList()) {
                Monto oldMontoOfHistorialmontoListHistorialmonto = historialmontoListHistorialmonto.getMonto();
                historialmontoListHistorialmonto.setMonto(monto);
                historialmontoListHistorialmonto = em.merge(historialmontoListHistorialmonto);
                if (oldMontoOfHistorialmontoListHistorialmonto != null) {
                    oldMontoOfHistorialmontoListHistorialmonto.getHistorialmontoList().remove(historialmontoListHistorialmonto);
                    oldMontoOfHistorialmontoListHistorialmonto = em.merge(oldMontoOfHistorialmontoListHistorialmonto);
                }
            }
            for (Otrosgastos otrosgastosListOtrosgastos : monto.getOtrosgastosList()) {
                Monto oldMontoIdmontoOfOtrosgastosListOtrosgastos = otrosgastosListOtrosgastos.getMontoIdmonto();
                otrosgastosListOtrosgastos.setMontoIdmonto(monto);
                otrosgastosListOtrosgastos = em.merge(otrosgastosListOtrosgastos);
                if (oldMontoIdmontoOfOtrosgastosListOtrosgastos != null) {
                    oldMontoIdmontoOfOtrosgastosListOtrosgastos.getOtrosgastosList().remove(otrosgastosListOtrosgastos);
                    oldMontoIdmontoOfOtrosgastosListOtrosgastos = em.merge(oldMontoIdmontoOfOtrosgastosListOtrosgastos);
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

    public void edit(Monto monto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Monto persistentMonto = em.find(Monto.class, monto.getIdmonto());
            List<Convenio> convenioListOld = persistentMonto.getConvenioList();
            List<Convenio> convenioListNew = monto.getConvenioList();
            List<Cuotaconvenio> cuotaconvenioListOld = persistentMonto.getCuotaconvenioList();
            List<Cuotaconvenio> cuotaconvenioListNew = monto.getCuotaconvenioList();
            List<Gastoscomunes> gastoscomunesListOld = persistentMonto.getGastoscomunesList();
            List<Gastoscomunes> gastoscomunesListNew = monto.getGastoscomunesList();
            List<Historialmonto> historialmontoListOld = persistentMonto.getHistorialmontoList();
            List<Historialmonto> historialmontoListNew = monto.getHistorialmontoList();
            List<Otrosgastos> otrosgastosListOld = persistentMonto.getOtrosgastosList();
            List<Otrosgastos> otrosgastosListNew = monto.getOtrosgastosList();
            List<String> illegalOrphanMessages = null;
            for (Convenio convenioListOldConvenio : convenioListOld) {
                if (!convenioListNew.contains(convenioListOldConvenio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Convenio " + convenioListOldConvenio + " since its montoIdmonto field is not nullable.");
                }
            }
            for (Cuotaconvenio cuotaconvenioListOldCuotaconvenio : cuotaconvenioListOld) {
                if (!cuotaconvenioListNew.contains(cuotaconvenioListOldCuotaconvenio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuotaconvenio " + cuotaconvenioListOldCuotaconvenio + " since its monto field is not nullable.");
                }
            }
            for (Gastoscomunes gastoscomunesListOldGastoscomunes : gastoscomunesListOld) {
                if (!gastoscomunesListNew.contains(gastoscomunesListOldGastoscomunes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gastoscomunes " + gastoscomunesListOldGastoscomunes + " since its montoIdmonto field is not nullable.");
                }
            }
            for (Historialmonto historialmontoListOldHistorialmonto : historialmontoListOld) {
                if (!historialmontoListNew.contains(historialmontoListOldHistorialmonto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historialmonto " + historialmontoListOldHistorialmonto + " since its monto field is not nullable.");
                }
            }
            for (Otrosgastos otrosgastosListOldOtrosgastos : otrosgastosListOld) {
                if (!otrosgastosListNew.contains(otrosgastosListOldOtrosgastos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Otrosgastos " + otrosgastosListOldOtrosgastos + " since its montoIdmonto field is not nullable.");
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
            monto.setConvenioList(convenioListNew);
            List<Cuotaconvenio> attachedCuotaconvenioListNew = new ArrayList<Cuotaconvenio>();
            for (Cuotaconvenio cuotaconvenioListNewCuotaconvenioToAttach : cuotaconvenioListNew) {
                cuotaconvenioListNewCuotaconvenioToAttach = em.getReference(cuotaconvenioListNewCuotaconvenioToAttach.getClass(), cuotaconvenioListNewCuotaconvenioToAttach.getCuotaconvenioPK());
                attachedCuotaconvenioListNew.add(cuotaconvenioListNewCuotaconvenioToAttach);
            }
            cuotaconvenioListNew = attachedCuotaconvenioListNew;
            monto.setCuotaconvenioList(cuotaconvenioListNew);
            List<Gastoscomunes> attachedGastoscomunesListNew = new ArrayList<Gastoscomunes>();
            for (Gastoscomunes gastoscomunesListNewGastoscomunesToAttach : gastoscomunesListNew) {
                gastoscomunesListNewGastoscomunesToAttach = em.getReference(gastoscomunesListNewGastoscomunesToAttach.getClass(), gastoscomunesListNewGastoscomunesToAttach.getGastoscomunesPK());
                attachedGastoscomunesListNew.add(gastoscomunesListNewGastoscomunesToAttach);
            }
            gastoscomunesListNew = attachedGastoscomunesListNew;
            monto.setGastoscomunesList(gastoscomunesListNew);
            List<Historialmonto> attachedHistorialmontoListNew = new ArrayList<Historialmonto>();
            for (Historialmonto historialmontoListNewHistorialmontoToAttach : historialmontoListNew) {
                historialmontoListNewHistorialmontoToAttach = em.getReference(historialmontoListNewHistorialmontoToAttach.getClass(), historialmontoListNewHistorialmontoToAttach.getHistorialmontoPK());
                attachedHistorialmontoListNew.add(historialmontoListNewHistorialmontoToAttach);
            }
            historialmontoListNew = attachedHistorialmontoListNew;
            monto.setHistorialmontoList(historialmontoListNew);
            List<Otrosgastos> attachedOtrosgastosListNew = new ArrayList<Otrosgastos>();
            for (Otrosgastos otrosgastosListNewOtrosgastosToAttach : otrosgastosListNew) {
                otrosgastosListNewOtrosgastosToAttach = em.getReference(otrosgastosListNewOtrosgastosToAttach.getClass(), otrosgastosListNewOtrosgastosToAttach.getOtrosgastosPK());
                attachedOtrosgastosListNew.add(otrosgastosListNewOtrosgastosToAttach);
            }
            otrosgastosListNew = attachedOtrosgastosListNew;
            monto.setOtrosgastosList(otrosgastosListNew);
            monto = em.merge(monto);
            for (Convenio convenioListNewConvenio : convenioListNew) {
                if (!convenioListOld.contains(convenioListNewConvenio)) {
                    Monto oldMontoIdmontoOfConvenioListNewConvenio = convenioListNewConvenio.getMontoIdmonto();
                    convenioListNewConvenio.setMontoIdmonto(monto);
                    convenioListNewConvenio = em.merge(convenioListNewConvenio);
                    if (oldMontoIdmontoOfConvenioListNewConvenio != null && !oldMontoIdmontoOfConvenioListNewConvenio.equals(monto)) {
                        oldMontoIdmontoOfConvenioListNewConvenio.getConvenioList().remove(convenioListNewConvenio);
                        oldMontoIdmontoOfConvenioListNewConvenio = em.merge(oldMontoIdmontoOfConvenioListNewConvenio);
                    }
                }
            }
            for (Cuotaconvenio cuotaconvenioListNewCuotaconvenio : cuotaconvenioListNew) {
                if (!cuotaconvenioListOld.contains(cuotaconvenioListNewCuotaconvenio)) {
                    Monto oldMontoOfCuotaconvenioListNewCuotaconvenio = cuotaconvenioListNewCuotaconvenio.getMonto();
                    cuotaconvenioListNewCuotaconvenio.setMonto(monto);
                    cuotaconvenioListNewCuotaconvenio = em.merge(cuotaconvenioListNewCuotaconvenio);
                    if (oldMontoOfCuotaconvenioListNewCuotaconvenio != null && !oldMontoOfCuotaconvenioListNewCuotaconvenio.equals(monto)) {
                        oldMontoOfCuotaconvenioListNewCuotaconvenio.getCuotaconvenioList().remove(cuotaconvenioListNewCuotaconvenio);
                        oldMontoOfCuotaconvenioListNewCuotaconvenio = em.merge(oldMontoOfCuotaconvenioListNewCuotaconvenio);
                    }
                }
            }
            for (Gastoscomunes gastoscomunesListNewGastoscomunes : gastoscomunesListNew) {
                if (!gastoscomunesListOld.contains(gastoscomunesListNewGastoscomunes)) {
                    Monto oldMontoIdmontoOfGastoscomunesListNewGastoscomunes = gastoscomunesListNewGastoscomunes.getMontoIdmonto();
                    gastoscomunesListNewGastoscomunes.setMontoIdmonto(monto);
                    gastoscomunesListNewGastoscomunes = em.merge(gastoscomunesListNewGastoscomunes);
                    if (oldMontoIdmontoOfGastoscomunesListNewGastoscomunes != null && !oldMontoIdmontoOfGastoscomunesListNewGastoscomunes.equals(monto)) {
                        oldMontoIdmontoOfGastoscomunesListNewGastoscomunes.getGastoscomunesList().remove(gastoscomunesListNewGastoscomunes);
                        oldMontoIdmontoOfGastoscomunesListNewGastoscomunes = em.merge(oldMontoIdmontoOfGastoscomunesListNewGastoscomunes);
                    }
                }
            }
            for (Historialmonto historialmontoListNewHistorialmonto : historialmontoListNew) {
                if (!historialmontoListOld.contains(historialmontoListNewHistorialmonto)) {
                    Monto oldMontoOfHistorialmontoListNewHistorialmonto = historialmontoListNewHistorialmonto.getMonto();
                    historialmontoListNewHistorialmonto.setMonto(monto);
                    historialmontoListNewHistorialmonto = em.merge(historialmontoListNewHistorialmonto);
                    if (oldMontoOfHistorialmontoListNewHistorialmonto != null && !oldMontoOfHistorialmontoListNewHistorialmonto.equals(monto)) {
                        oldMontoOfHistorialmontoListNewHistorialmonto.getHistorialmontoList().remove(historialmontoListNewHistorialmonto);
                        oldMontoOfHistorialmontoListNewHistorialmonto = em.merge(oldMontoOfHistorialmontoListNewHistorialmonto);
                    }
                }
            }
            for (Otrosgastos otrosgastosListNewOtrosgastos : otrosgastosListNew) {
                if (!otrosgastosListOld.contains(otrosgastosListNewOtrosgastos)) {
                    Monto oldMontoIdmontoOfOtrosgastosListNewOtrosgastos = otrosgastosListNewOtrosgastos.getMontoIdmonto();
                    otrosgastosListNewOtrosgastos.setMontoIdmonto(monto);
                    otrosgastosListNewOtrosgastos = em.merge(otrosgastosListNewOtrosgastos);
                    if (oldMontoIdmontoOfOtrosgastosListNewOtrosgastos != null && !oldMontoIdmontoOfOtrosgastosListNewOtrosgastos.equals(monto)) {
                        oldMontoIdmontoOfOtrosgastosListNewOtrosgastos.getOtrosgastosList().remove(otrosgastosListNewOtrosgastos);
                        oldMontoIdmontoOfOtrosgastosListNewOtrosgastos = em.merge(oldMontoIdmontoOfOtrosgastosListNewOtrosgastos);
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
                Integer id = monto.getIdmonto();
                if (findMonto(id) == null) {
                    throw new NonexistentEntityException("The monto with id " + id + " no longer exists.");
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
            Monto monto;
            try {
                monto = em.getReference(Monto.class, id);
                monto.getIdmonto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The monto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Convenio> convenioListOrphanCheck = monto.getConvenioList();
            for (Convenio convenioListOrphanCheckConvenio : convenioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Monto (" + monto + ") cannot be destroyed since the Convenio " + convenioListOrphanCheckConvenio + " in its convenioList field has a non-nullable montoIdmonto field.");
            }
            List<Cuotaconvenio> cuotaconvenioListOrphanCheck = monto.getCuotaconvenioList();
            for (Cuotaconvenio cuotaconvenioListOrphanCheckCuotaconvenio : cuotaconvenioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Monto (" + monto + ") cannot be destroyed since the Cuotaconvenio " + cuotaconvenioListOrphanCheckCuotaconvenio + " in its cuotaconvenioList field has a non-nullable monto field.");
            }
            List<Gastoscomunes> gastoscomunesListOrphanCheck = monto.getGastoscomunesList();
            for (Gastoscomunes gastoscomunesListOrphanCheckGastoscomunes : gastoscomunesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Monto (" + monto + ") cannot be destroyed since the Gastoscomunes " + gastoscomunesListOrphanCheckGastoscomunes + " in its gastoscomunesList field has a non-nullable montoIdmonto field.");
            }
            List<Historialmonto> historialmontoListOrphanCheck = monto.getHistorialmontoList();
            for (Historialmonto historialmontoListOrphanCheckHistorialmonto : historialmontoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Monto (" + monto + ") cannot be destroyed since the Historialmonto " + historialmontoListOrphanCheckHistorialmonto + " in its historialmontoList field has a non-nullable monto field.");
            }
            List<Otrosgastos> otrosgastosListOrphanCheck = monto.getOtrosgastosList();
            for (Otrosgastos otrosgastosListOrphanCheckOtrosgastos : otrosgastosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Monto (" + monto + ") cannot be destroyed since the Otrosgastos " + otrosgastosListOrphanCheckOtrosgastos + " in its otrosgastosList field has a non-nullable montoIdmonto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(monto);
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

    public List<Monto> findMontoEntities() {
        return findMontoEntities(true, -1, -1);
    }

    public List<Monto> findMontoEntities(int maxResults, int firstResult) {
        return findMontoEntities(false, maxResults, firstResult);
    }

    private List<Monto> findMontoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Monto.class));
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

    public Monto findMonto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Monto.class, id);
        } finally {
            em.close();
        }
    }

    public int getMontoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Monto> rt = cq.from(Monto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
