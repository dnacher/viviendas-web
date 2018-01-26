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
import entities.Concepto;
import entities.Monto;
import entities.Otrosgastos;
import entities.OtrosgastosPK;
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
public class OtrosgastosJpaController implements Serializable {

    public OtrosgastosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Otrosgastos otrosgastos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (otrosgastos.getOtrosgastosPK() == null) {
            otrosgastos.setOtrosgastosPK(new OtrosgastosPK());
        }
        otrosgastos.getOtrosgastosPK().setUnidadidUnidad(otrosgastos.getUnidad().getIdUnidad());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Concepto conceptoIdconcepto = otrosgastos.getConceptoIdconcepto();
            if (conceptoIdconcepto != null) {
                conceptoIdconcepto = em.getReference(conceptoIdconcepto.getClass(), conceptoIdconcepto.getIdconcepto());
                otrosgastos.setConceptoIdconcepto(conceptoIdconcepto);
            }
            Monto montoIdmonto = otrosgastos.getMontoIdmonto();
            if (montoIdmonto != null) {
                montoIdmonto = em.getReference(montoIdmonto.getClass(), montoIdmonto.getIdmonto());
                otrosgastos.setMontoIdmonto(montoIdmonto);
            }
            Unidad unidad = otrosgastos.getUnidad();
            if (unidad != null) {
                unidad = em.getReference(unidad.getClass(), unidad.getIdUnidad());
                otrosgastos.setUnidad(unidad);
            }
            em.persist(otrosgastos);
            if (conceptoIdconcepto != null) {
                conceptoIdconcepto.getOtrosgastosList().add(otrosgastos);
                conceptoIdconcepto = em.merge(conceptoIdconcepto);
            }
            if (montoIdmonto != null) {
                montoIdmonto.getOtrosgastosList().add(otrosgastos);
                montoIdmonto = em.merge(montoIdmonto);
            }
            if (unidad != null) {
                unidad.getOtrosgastosList().add(otrosgastos);
                unidad = em.merge(unidad);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOtrosgastos(otrosgastos.getOtrosgastosPK()) != null) {
                throw new PreexistingEntityException("Otrosgastos " + otrosgastos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Otrosgastos otrosgastos) throws NonexistentEntityException, RollbackFailureException, Exception {
        otrosgastos.getOtrosgastosPK().setUnidadidUnidad(otrosgastos.getUnidad().getIdUnidad());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Otrosgastos persistentOtrosgastos = em.find(Otrosgastos.class, otrosgastos.getOtrosgastosPK());
            Concepto conceptoIdconceptoOld = persistentOtrosgastos.getConceptoIdconcepto();
            Concepto conceptoIdconceptoNew = otrosgastos.getConceptoIdconcepto();
            Monto montoIdmontoOld = persistentOtrosgastos.getMontoIdmonto();
            Monto montoIdmontoNew = otrosgastos.getMontoIdmonto();
            Unidad unidadOld = persistentOtrosgastos.getUnidad();
            Unidad unidadNew = otrosgastos.getUnidad();
            if (conceptoIdconceptoNew != null) {
                conceptoIdconceptoNew = em.getReference(conceptoIdconceptoNew.getClass(), conceptoIdconceptoNew.getIdconcepto());
                otrosgastos.setConceptoIdconcepto(conceptoIdconceptoNew);
            }
            if (montoIdmontoNew != null) {
                montoIdmontoNew = em.getReference(montoIdmontoNew.getClass(), montoIdmontoNew.getIdmonto());
                otrosgastos.setMontoIdmonto(montoIdmontoNew);
            }
            if (unidadNew != null) {
                unidadNew = em.getReference(unidadNew.getClass(), unidadNew.getIdUnidad());
                otrosgastos.setUnidad(unidadNew);
            }
            otrosgastos = em.merge(otrosgastos);
            if (conceptoIdconceptoOld != null && !conceptoIdconceptoOld.equals(conceptoIdconceptoNew)) {
                conceptoIdconceptoOld.getOtrosgastosList().remove(otrosgastos);
                conceptoIdconceptoOld = em.merge(conceptoIdconceptoOld);
            }
            if (conceptoIdconceptoNew != null && !conceptoIdconceptoNew.equals(conceptoIdconceptoOld)) {
                conceptoIdconceptoNew.getOtrosgastosList().add(otrosgastos);
                conceptoIdconceptoNew = em.merge(conceptoIdconceptoNew);
            }
            if (montoIdmontoOld != null && !montoIdmontoOld.equals(montoIdmontoNew)) {
                montoIdmontoOld.getOtrosgastosList().remove(otrosgastos);
                montoIdmontoOld = em.merge(montoIdmontoOld);
            }
            if (montoIdmontoNew != null && !montoIdmontoNew.equals(montoIdmontoOld)) {
                montoIdmontoNew.getOtrosgastosList().add(otrosgastos);
                montoIdmontoNew = em.merge(montoIdmontoNew);
            }
            if (unidadOld != null && !unidadOld.equals(unidadNew)) {
                unidadOld.getOtrosgastosList().remove(otrosgastos);
                unidadOld = em.merge(unidadOld);
            }
            if (unidadNew != null && !unidadNew.equals(unidadOld)) {
                unidadNew.getOtrosgastosList().add(otrosgastos);
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
                OtrosgastosPK id = otrosgastos.getOtrosgastosPK();
                if (findOtrosgastos(id) == null) {
                    throw new NonexistentEntityException("The otrosgastos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OtrosgastosPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Otrosgastos otrosgastos;
            try {
                otrosgastos = em.getReference(Otrosgastos.class, id);
                otrosgastos.getOtrosgastosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The otrosgastos with id " + id + " no longer exists.", enfe);
            }
            Concepto conceptoIdconcepto = otrosgastos.getConceptoIdconcepto();
            if (conceptoIdconcepto != null) {
                conceptoIdconcepto.getOtrosgastosList().remove(otrosgastos);
                conceptoIdconcepto = em.merge(conceptoIdconcepto);
            }
            Monto montoIdmonto = otrosgastos.getMontoIdmonto();
            if (montoIdmonto != null) {
                montoIdmonto.getOtrosgastosList().remove(otrosgastos);
                montoIdmonto = em.merge(montoIdmonto);
            }
            Unidad unidad = otrosgastos.getUnidad();
            if (unidad != null) {
                unidad.getOtrosgastosList().remove(otrosgastos);
                unidad = em.merge(unidad);
            }
            em.remove(otrosgastos);
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

    public List<Otrosgastos> findOtrosgastosEntities() {
        return findOtrosgastosEntities(true, -1, -1);
    }

    public List<Otrosgastos> findOtrosgastosEntities(int maxResults, int firstResult) {
        return findOtrosgastosEntities(false, maxResults, firstResult);
    }

    private List<Otrosgastos> findOtrosgastosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Otrosgastos.class));
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

    public Otrosgastos findOtrosgastos(OtrosgastosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Otrosgastos.class, id);
        } finally {
            em.close();
        }
    }

    public int getOtrosgastosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Otrosgastos> rt = cq.from(Otrosgastos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
