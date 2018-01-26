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
import entities.Material;
import entities.Trabajoxmaterial;
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
public class TrabajoxmaterialJpaController implements Serializable {

    public TrabajoxmaterialJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trabajoxmaterial trabajoxmaterial) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Trabajo trabajoOrphanCheck = trabajoxmaterial.getTrabajo();
        if (trabajoOrphanCheck != null) {
            Trabajoxmaterial oldTrabajoxmaterialOfTrabajo = trabajoOrphanCheck.getTrabajoxmaterial();
            if (oldTrabajoxmaterialOfTrabajo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Trabajo " + trabajoOrphanCheck + " already has an item of type Trabajoxmaterial whose trabajo column cannot be null. Please make another selection for the trabajo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Trabajo trabajo = trabajoxmaterial.getTrabajo();
            if (trabajo != null) {
                trabajo = em.getReference(trabajo.getClass(), trabajo.getIdTrabajo());
                trabajoxmaterial.setTrabajo(trabajo);
            }
            Material materialIdmaterial = trabajoxmaterial.getMaterialIdmaterial();
            if (materialIdmaterial != null) {
                materialIdmaterial = em.getReference(materialIdmaterial.getClass(), materialIdmaterial.getIdmaterial());
                trabajoxmaterial.setMaterialIdmaterial(materialIdmaterial);
            }
            em.persist(trabajoxmaterial);
            if (trabajo != null) {
                trabajo.setTrabajoxmaterial(trabajoxmaterial);
                trabajo = em.merge(trabajo);
            }
            if (materialIdmaterial != null) {
                materialIdmaterial.getTrabajoxmaterialList().add(trabajoxmaterial);
                materialIdmaterial = em.merge(materialIdmaterial);
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

    public void edit(Trabajoxmaterial trabajoxmaterial) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Trabajoxmaterial persistentTrabajoxmaterial = em.find(Trabajoxmaterial.class, trabajoxmaterial.getTrabajoidTrabajo());
            Trabajo trabajoOld = persistentTrabajoxmaterial.getTrabajo();
            Trabajo trabajoNew = trabajoxmaterial.getTrabajo();
            Material materialIdmaterialOld = persistentTrabajoxmaterial.getMaterialIdmaterial();
            Material materialIdmaterialNew = trabajoxmaterial.getMaterialIdmaterial();
            List<String> illegalOrphanMessages = null;
            if (trabajoNew != null && !trabajoNew.equals(trabajoOld)) {
                Trabajoxmaterial oldTrabajoxmaterialOfTrabajo = trabajoNew.getTrabajoxmaterial();
                if (oldTrabajoxmaterialOfTrabajo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Trabajo " + trabajoNew + " already has an item of type Trabajoxmaterial whose trabajo column cannot be null. Please make another selection for the trabajo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (trabajoNew != null) {
                trabajoNew = em.getReference(trabajoNew.getClass(), trabajoNew.getIdTrabajo());
                trabajoxmaterial.setTrabajo(trabajoNew);
            }
            if (materialIdmaterialNew != null) {
                materialIdmaterialNew = em.getReference(materialIdmaterialNew.getClass(), materialIdmaterialNew.getIdmaterial());
                trabajoxmaterial.setMaterialIdmaterial(materialIdmaterialNew);
            }
            trabajoxmaterial = em.merge(trabajoxmaterial);
            if (trabajoOld != null && !trabajoOld.equals(trabajoNew)) {
                trabajoOld.setTrabajoxmaterial(null);
                trabajoOld = em.merge(trabajoOld);
            }
            if (trabajoNew != null && !trabajoNew.equals(trabajoOld)) {
                trabajoNew.setTrabajoxmaterial(trabajoxmaterial);
                trabajoNew = em.merge(trabajoNew);
            }
            if (materialIdmaterialOld != null && !materialIdmaterialOld.equals(materialIdmaterialNew)) {
                materialIdmaterialOld.getTrabajoxmaterialList().remove(trabajoxmaterial);
                materialIdmaterialOld = em.merge(materialIdmaterialOld);
            }
            if (materialIdmaterialNew != null && !materialIdmaterialNew.equals(materialIdmaterialOld)) {
                materialIdmaterialNew.getTrabajoxmaterialList().add(trabajoxmaterial);
                materialIdmaterialNew = em.merge(materialIdmaterialNew);
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
                Integer id = trabajoxmaterial.getTrabajoidTrabajo();
                if (findTrabajoxmaterial(id) == null) {
                    throw new NonexistentEntityException("The trabajoxmaterial with id " + id + " no longer exists.");
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
            Trabajoxmaterial trabajoxmaterial;
            try {
                trabajoxmaterial = em.getReference(Trabajoxmaterial.class, id);
                trabajoxmaterial.getTrabajoidTrabajo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trabajoxmaterial with id " + id + " no longer exists.", enfe);
            }
            Trabajo trabajo = trabajoxmaterial.getTrabajo();
            if (trabajo != null) {
                trabajo.setTrabajoxmaterial(null);
                trabajo = em.merge(trabajo);
            }
            Material materialIdmaterial = trabajoxmaterial.getMaterialIdmaterial();
            if (materialIdmaterial != null) {
                materialIdmaterial.getTrabajoxmaterialList().remove(trabajoxmaterial);
                materialIdmaterial = em.merge(materialIdmaterial);
            }
            em.remove(trabajoxmaterial);
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

    public List<Trabajoxmaterial> findTrabajoxmaterialEntities() {
        return findTrabajoxmaterialEntities(true, -1, -1);
    }

    public List<Trabajoxmaterial> findTrabajoxmaterialEntities(int maxResults, int firstResult) {
        return findTrabajoxmaterialEntities(false, maxResults, firstResult);
    }

    private List<Trabajoxmaterial> findTrabajoxmaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trabajoxmaterial.class));
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

    public Trabajoxmaterial findTrabajoxmaterial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trabajoxmaterial.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrabajoxmaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trabajoxmaterial> rt = cq.from(Trabajoxmaterial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
