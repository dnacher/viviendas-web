/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Concepto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Otrosgastos;
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
public class ConceptoJpaController implements Serializable {

    public ConceptoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Concepto concepto) throws RollbackFailureException, Exception {
        if (concepto.getOtrosgastosList() == null) {
            concepto.setOtrosgastosList(new ArrayList<Otrosgastos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Otrosgastos> attachedOtrosgastosList = new ArrayList<Otrosgastos>();
            for (Otrosgastos otrosgastosListOtrosgastosToAttach : concepto.getOtrosgastosList()) {
                otrosgastosListOtrosgastosToAttach = em.getReference(otrosgastosListOtrosgastosToAttach.getClass(), otrosgastosListOtrosgastosToAttach.getOtrosgastosPK());
                attachedOtrosgastosList.add(otrosgastosListOtrosgastosToAttach);
            }
            concepto.setOtrosgastosList(attachedOtrosgastosList);
            em.persist(concepto);
            for (Otrosgastos otrosgastosListOtrosgastos : concepto.getOtrosgastosList()) {
                Concepto oldConceptoIdconceptoOfOtrosgastosListOtrosgastos = otrosgastosListOtrosgastos.getConceptoIdconcepto();
                otrosgastosListOtrosgastos.setConceptoIdconcepto(concepto);
                otrosgastosListOtrosgastos = em.merge(otrosgastosListOtrosgastos);
                if (oldConceptoIdconceptoOfOtrosgastosListOtrosgastos != null) {
                    oldConceptoIdconceptoOfOtrosgastosListOtrosgastos.getOtrosgastosList().remove(otrosgastosListOtrosgastos);
                    oldConceptoIdconceptoOfOtrosgastosListOtrosgastos = em.merge(oldConceptoIdconceptoOfOtrosgastosListOtrosgastos);
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

    public void edit(Concepto concepto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Concepto persistentConcepto = em.find(Concepto.class, concepto.getIdconcepto());
            List<Otrosgastos> otrosgastosListOld = persistentConcepto.getOtrosgastosList();
            List<Otrosgastos> otrosgastosListNew = concepto.getOtrosgastosList();
            List<String> illegalOrphanMessages = null;
            for (Otrosgastos otrosgastosListOldOtrosgastos : otrosgastosListOld) {
                if (!otrosgastosListNew.contains(otrosgastosListOldOtrosgastos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Otrosgastos " + otrosgastosListOldOtrosgastos + " since its conceptoIdconcepto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Otrosgastos> attachedOtrosgastosListNew = new ArrayList<Otrosgastos>();
            for (Otrosgastos otrosgastosListNewOtrosgastosToAttach : otrosgastosListNew) {
                otrosgastosListNewOtrosgastosToAttach = em.getReference(otrosgastosListNewOtrosgastosToAttach.getClass(), otrosgastosListNewOtrosgastosToAttach.getOtrosgastosPK());
                attachedOtrosgastosListNew.add(otrosgastosListNewOtrosgastosToAttach);
            }
            otrosgastosListNew = attachedOtrosgastosListNew;
            concepto.setOtrosgastosList(otrosgastosListNew);
            concepto = em.merge(concepto);
            for (Otrosgastos otrosgastosListNewOtrosgastos : otrosgastosListNew) {
                if (!otrosgastosListOld.contains(otrosgastosListNewOtrosgastos)) {
                    Concepto oldConceptoIdconceptoOfOtrosgastosListNewOtrosgastos = otrosgastosListNewOtrosgastos.getConceptoIdconcepto();
                    otrosgastosListNewOtrosgastos.setConceptoIdconcepto(concepto);
                    otrosgastosListNewOtrosgastos = em.merge(otrosgastosListNewOtrosgastos);
                    if (oldConceptoIdconceptoOfOtrosgastosListNewOtrosgastos != null && !oldConceptoIdconceptoOfOtrosgastosListNewOtrosgastos.equals(concepto)) {
                        oldConceptoIdconceptoOfOtrosgastosListNewOtrosgastos.getOtrosgastosList().remove(otrosgastosListNewOtrosgastos);
                        oldConceptoIdconceptoOfOtrosgastosListNewOtrosgastos = em.merge(oldConceptoIdconceptoOfOtrosgastosListNewOtrosgastos);
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
                Integer id = concepto.getIdconcepto();
                if (findConcepto(id) == null) {
                    throw new NonexistentEntityException("The concepto with id " + id + " no longer exists.");
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
            Concepto concepto;
            try {
                concepto = em.getReference(Concepto.class, id);
                concepto.getIdconcepto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The concepto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Otrosgastos> otrosgastosListOrphanCheck = concepto.getOtrosgastosList();
            for (Otrosgastos otrosgastosListOrphanCheckOtrosgastos : otrosgastosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Concepto (" + concepto + ") cannot be destroyed since the Otrosgastos " + otrosgastosListOrphanCheckOtrosgastos + " in its otrosgastosList field has a non-nullable conceptoIdconcepto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(concepto);
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

    public List<Concepto> findConceptoEntities() {
        return findConceptoEntities(true, -1, -1);
    }

    public List<Concepto> findConceptoEntities(int maxResults, int firstResult) {
        return findConceptoEntities(false, maxResults, firstResult);
    }

    private List<Concepto> findConceptoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Concepto.class));
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

    public Concepto findConcepto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Concepto.class, id);
        } finally {
            em.close();
        }
    }

    public int getConceptoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Concepto> rt = cq.from(Concepto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
