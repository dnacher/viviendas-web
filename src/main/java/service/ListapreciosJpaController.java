/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Listaprecios;
import entities.ListapreciosPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Material;
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
public class ListapreciosJpaController implements Serializable {

    public ListapreciosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Listaprecios listaprecios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (listaprecios.getListapreciosPK() == null) {
            listaprecios.setListapreciosPK(new ListapreciosPK());
        }
        listaprecios.getListapreciosPK().setMaterialIdmaterial(listaprecios.getMaterial().getIdmaterial());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Material material = listaprecios.getMaterial();
            if (material != null) {
                material = em.getReference(material.getClass(), material.getIdmaterial());
                listaprecios.setMaterial(material);
            }
            em.persist(listaprecios);
            if (material != null) {
                material.getListapreciosList().add(listaprecios);
                material = em.merge(material);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findListaprecios(listaprecios.getListapreciosPK()) != null) {
                throw new PreexistingEntityException("Listaprecios " + listaprecios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Listaprecios listaprecios) throws NonexistentEntityException, RollbackFailureException, Exception {
        listaprecios.getListapreciosPK().setMaterialIdmaterial(listaprecios.getMaterial().getIdmaterial());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Listaprecios persistentListaprecios = em.find(Listaprecios.class, listaprecios.getListapreciosPK());
            Material materialOld = persistentListaprecios.getMaterial();
            Material materialNew = listaprecios.getMaterial();
            if (materialNew != null) {
                materialNew = em.getReference(materialNew.getClass(), materialNew.getIdmaterial());
                listaprecios.setMaterial(materialNew);
            }
            listaprecios = em.merge(listaprecios);
            if (materialOld != null && !materialOld.equals(materialNew)) {
                materialOld.getListapreciosList().remove(listaprecios);
                materialOld = em.merge(materialOld);
            }
            if (materialNew != null && !materialNew.equals(materialOld)) {
                materialNew.getListapreciosList().add(listaprecios);
                materialNew = em.merge(materialNew);
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
                ListapreciosPK id = listaprecios.getListapreciosPK();
                if (findListaprecios(id) == null) {
                    throw new NonexistentEntityException("The listaprecios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ListapreciosPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Listaprecios listaprecios;
            try {
                listaprecios = em.getReference(Listaprecios.class, id);
                listaprecios.getListapreciosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaprecios with id " + id + " no longer exists.", enfe);
            }
            Material material = listaprecios.getMaterial();
            if (material != null) {
                material.getListapreciosList().remove(listaprecios);
                material = em.merge(material);
            }
            em.remove(listaprecios);
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

    public List<Listaprecios> findListapreciosEntities() {
        return findListapreciosEntities(true, -1, -1);
    }

    public List<Listaprecios> findListapreciosEntities(int maxResults, int firstResult) {
        return findListapreciosEntities(false, maxResults, firstResult);
    }

    private List<Listaprecios> findListapreciosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Listaprecios.class));
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

    public Listaprecios findListaprecios(ListapreciosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Listaprecios.class, id);
        } finally {
            em.close();
        }
    }

    public int getListapreciosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Listaprecios> rt = cq.from(Listaprecios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
