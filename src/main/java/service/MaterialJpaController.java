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
import entities.Trabajoxmaterial;
import java.util.ArrayList;
import java.util.List;
import entities.Listaprecios;
import entities.Material;
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
public class MaterialJpaController implements Serializable {

    public MaterialJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Material material) throws RollbackFailureException, Exception {
        if (material.getTrabajoxmaterialList() == null) {
            material.setTrabajoxmaterialList(new ArrayList<Trabajoxmaterial>());
        }
        if (material.getListapreciosList() == null) {
            material.setListapreciosList(new ArrayList<Listaprecios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trabajoxmaterial> attachedTrabajoxmaterialList = new ArrayList<Trabajoxmaterial>();
            for (Trabajoxmaterial trabajoxmaterialListTrabajoxmaterialToAttach : material.getTrabajoxmaterialList()) {
                trabajoxmaterialListTrabajoxmaterialToAttach = em.getReference(trabajoxmaterialListTrabajoxmaterialToAttach.getClass(), trabajoxmaterialListTrabajoxmaterialToAttach.getTrabajoidTrabajo());
                attachedTrabajoxmaterialList.add(trabajoxmaterialListTrabajoxmaterialToAttach);
            }
            material.setTrabajoxmaterialList(attachedTrabajoxmaterialList);
            List<Listaprecios> attachedListapreciosList = new ArrayList<Listaprecios>();
            for (Listaprecios listapreciosListListapreciosToAttach : material.getListapreciosList()) {
                listapreciosListListapreciosToAttach = em.getReference(listapreciosListListapreciosToAttach.getClass(), listapreciosListListapreciosToAttach.getListapreciosPK());
                attachedListapreciosList.add(listapreciosListListapreciosToAttach);
            }
            material.setListapreciosList(attachedListapreciosList);
            em.persist(material);
            for (Trabajoxmaterial trabajoxmaterialListTrabajoxmaterial : material.getTrabajoxmaterialList()) {
                Material oldMaterialIdmaterialOfTrabajoxmaterialListTrabajoxmaterial = trabajoxmaterialListTrabajoxmaterial.getMaterialIdmaterial();
                trabajoxmaterialListTrabajoxmaterial.setMaterialIdmaterial(material);
                trabajoxmaterialListTrabajoxmaterial = em.merge(trabajoxmaterialListTrabajoxmaterial);
                if (oldMaterialIdmaterialOfTrabajoxmaterialListTrabajoxmaterial != null) {
                    oldMaterialIdmaterialOfTrabajoxmaterialListTrabajoxmaterial.getTrabajoxmaterialList().remove(trabajoxmaterialListTrabajoxmaterial);
                    oldMaterialIdmaterialOfTrabajoxmaterialListTrabajoxmaterial = em.merge(oldMaterialIdmaterialOfTrabajoxmaterialListTrabajoxmaterial);
                }
            }
            for (Listaprecios listapreciosListListaprecios : material.getListapreciosList()) {
                Material oldMaterialOfListapreciosListListaprecios = listapreciosListListaprecios.getMaterial();
                listapreciosListListaprecios.setMaterial(material);
                listapreciosListListaprecios = em.merge(listapreciosListListaprecios);
                if (oldMaterialOfListapreciosListListaprecios != null) {
                    oldMaterialOfListapreciosListListaprecios.getListapreciosList().remove(listapreciosListListaprecios);
                    oldMaterialOfListapreciosListListaprecios = em.merge(oldMaterialOfListapreciosListListaprecios);
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

    public void edit(Material material) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Material persistentMaterial = em.find(Material.class, material.getIdmaterial());
            List<Trabajoxmaterial> trabajoxmaterialListOld = persistentMaterial.getTrabajoxmaterialList();
            List<Trabajoxmaterial> trabajoxmaterialListNew = material.getTrabajoxmaterialList();
            List<Listaprecios> listapreciosListOld = persistentMaterial.getListapreciosList();
            List<Listaprecios> listapreciosListNew = material.getListapreciosList();
            List<String> illegalOrphanMessages = null;
            for (Trabajoxmaterial trabajoxmaterialListOldTrabajoxmaterial : trabajoxmaterialListOld) {
                if (!trabajoxmaterialListNew.contains(trabajoxmaterialListOldTrabajoxmaterial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trabajoxmaterial " + trabajoxmaterialListOldTrabajoxmaterial + " since its materialIdmaterial field is not nullable.");
                }
            }
            for (Listaprecios listapreciosListOldListaprecios : listapreciosListOld) {
                if (!listapreciosListNew.contains(listapreciosListOldListaprecios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Listaprecios " + listapreciosListOldListaprecios + " since its material field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Trabajoxmaterial> attachedTrabajoxmaterialListNew = new ArrayList<Trabajoxmaterial>();
            for (Trabajoxmaterial trabajoxmaterialListNewTrabajoxmaterialToAttach : trabajoxmaterialListNew) {
                trabajoxmaterialListNewTrabajoxmaterialToAttach = em.getReference(trabajoxmaterialListNewTrabajoxmaterialToAttach.getClass(), trabajoxmaterialListNewTrabajoxmaterialToAttach.getTrabajoidTrabajo());
                attachedTrabajoxmaterialListNew.add(trabajoxmaterialListNewTrabajoxmaterialToAttach);
            }
            trabajoxmaterialListNew = attachedTrabajoxmaterialListNew;
            material.setTrabajoxmaterialList(trabajoxmaterialListNew);
            List<Listaprecios> attachedListapreciosListNew = new ArrayList<Listaprecios>();
            for (Listaprecios listapreciosListNewListapreciosToAttach : listapreciosListNew) {
                listapreciosListNewListapreciosToAttach = em.getReference(listapreciosListNewListapreciosToAttach.getClass(), listapreciosListNewListapreciosToAttach.getListapreciosPK());
                attachedListapreciosListNew.add(listapreciosListNewListapreciosToAttach);
            }
            listapreciosListNew = attachedListapreciosListNew;
            material.setListapreciosList(listapreciosListNew);
            material = em.merge(material);
            for (Trabajoxmaterial trabajoxmaterialListNewTrabajoxmaterial : trabajoxmaterialListNew) {
                if (!trabajoxmaterialListOld.contains(trabajoxmaterialListNewTrabajoxmaterial)) {
                    Material oldMaterialIdmaterialOfTrabajoxmaterialListNewTrabajoxmaterial = trabajoxmaterialListNewTrabajoxmaterial.getMaterialIdmaterial();
                    trabajoxmaterialListNewTrabajoxmaterial.setMaterialIdmaterial(material);
                    trabajoxmaterialListNewTrabajoxmaterial = em.merge(trabajoxmaterialListNewTrabajoxmaterial);
                    if (oldMaterialIdmaterialOfTrabajoxmaterialListNewTrabajoxmaterial != null && !oldMaterialIdmaterialOfTrabajoxmaterialListNewTrabajoxmaterial.equals(material)) {
                        oldMaterialIdmaterialOfTrabajoxmaterialListNewTrabajoxmaterial.getTrabajoxmaterialList().remove(trabajoxmaterialListNewTrabajoxmaterial);
                        oldMaterialIdmaterialOfTrabajoxmaterialListNewTrabajoxmaterial = em.merge(oldMaterialIdmaterialOfTrabajoxmaterialListNewTrabajoxmaterial);
                    }
                }
            }
            for (Listaprecios listapreciosListNewListaprecios : listapreciosListNew) {
                if (!listapreciosListOld.contains(listapreciosListNewListaprecios)) {
                    Material oldMaterialOfListapreciosListNewListaprecios = listapreciosListNewListaprecios.getMaterial();
                    listapreciosListNewListaprecios.setMaterial(material);
                    listapreciosListNewListaprecios = em.merge(listapreciosListNewListaprecios);
                    if (oldMaterialOfListapreciosListNewListaprecios != null && !oldMaterialOfListapreciosListNewListaprecios.equals(material)) {
                        oldMaterialOfListapreciosListNewListaprecios.getListapreciosList().remove(listapreciosListNewListaprecios);
                        oldMaterialOfListapreciosListNewListaprecios = em.merge(oldMaterialOfListapreciosListNewListaprecios);
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
                Integer id = material.getIdmaterial();
                if (findMaterial(id) == null) {
                    throw new NonexistentEntityException("The material with id " + id + " no longer exists.");
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
            Material material;
            try {
                material = em.getReference(Material.class, id);
                material.getIdmaterial();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The material with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Trabajoxmaterial> trabajoxmaterialListOrphanCheck = material.getTrabajoxmaterialList();
            for (Trabajoxmaterial trabajoxmaterialListOrphanCheckTrabajoxmaterial : trabajoxmaterialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the Trabajoxmaterial " + trabajoxmaterialListOrphanCheckTrabajoxmaterial + " in its trabajoxmaterialList field has a non-nullable materialIdmaterial field.");
            }
            List<Listaprecios> listapreciosListOrphanCheck = material.getListapreciosList();
            for (Listaprecios listapreciosListOrphanCheckListaprecios : listapreciosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the Listaprecios " + listapreciosListOrphanCheckListaprecios + " in its listapreciosList field has a non-nullable material field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(material);
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

    public List<Material> findMaterialEntities() {
        return findMaterialEntities(true, -1, -1);
    }

    public List<Material> findMaterialEntities(int maxResults, int firstResult) {
        return findMaterialEntities(false, maxResults, firstResult);
    }

    private List<Material> findMaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Material.class));
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

    public Material findMaterial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Material.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Material> rt = cq.from(Material.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
