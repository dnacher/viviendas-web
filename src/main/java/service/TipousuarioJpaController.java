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
import entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import entities.Permisosusuario;
import entities.Tipousuario;
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
public class TipousuarioJpaController implements Serializable {

    public TipousuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipousuario tipousuario) throws RollbackFailureException, Exception {
        if (tipousuario.getUsuarioList() == null) {
            tipousuario.setUsuarioList(new ArrayList<Usuario>());
        }
        if (tipousuario.getPermisosusuarioList() == null) {
            tipousuario.setPermisosusuarioList(new ArrayList<Permisosusuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : tipousuario.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            tipousuario.setUsuarioList(attachedUsuarioList);
            List<Permisosusuario> attachedPermisosusuarioList = new ArrayList<Permisosusuario>();
            for (Permisosusuario permisosusuarioListPermisosusuarioToAttach : tipousuario.getPermisosusuarioList()) {
                permisosusuarioListPermisosusuarioToAttach = em.getReference(permisosusuarioListPermisosusuarioToAttach.getClass(), permisosusuarioListPermisosusuarioToAttach.getPagina());
                attachedPermisosusuarioList.add(permisosusuarioListPermisosusuarioToAttach);
            }
            tipousuario.setPermisosusuarioList(attachedPermisosusuarioList);
            em.persist(tipousuario);
            for (Usuario usuarioListUsuario : tipousuario.getUsuarioList()) {
                Tipousuario oldTipoUsuarioidOfUsuarioListUsuario = usuarioListUsuario.getTipoUsuarioid();
                usuarioListUsuario.setTipoUsuarioid(tipousuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldTipoUsuarioidOfUsuarioListUsuario != null) {
                    oldTipoUsuarioidOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldTipoUsuarioidOfUsuarioListUsuario = em.merge(oldTipoUsuarioidOfUsuarioListUsuario);
                }
            }
            for (Permisosusuario permisosusuarioListPermisosusuario : tipousuario.getPermisosusuarioList()) {
                Tipousuario oldTipousuarioIdOfPermisosusuarioListPermisosusuario = permisosusuarioListPermisosusuario.getTipousuarioId();
                permisosusuarioListPermisosusuario.setTipousuarioId(tipousuario);
                permisosusuarioListPermisosusuario = em.merge(permisosusuarioListPermisosusuario);
                if (oldTipousuarioIdOfPermisosusuarioListPermisosusuario != null) {
                    oldTipousuarioIdOfPermisosusuarioListPermisosusuario.getPermisosusuarioList().remove(permisosusuarioListPermisosusuario);
                    oldTipousuarioIdOfPermisosusuarioListPermisosusuario = em.merge(oldTipousuarioIdOfPermisosusuarioListPermisosusuario);
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

    public void edit(Tipousuario tipousuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tipousuario persistentTipousuario = em.find(Tipousuario.class, tipousuario.getId());
            List<Usuario> usuarioListOld = persistentTipousuario.getUsuarioList();
            List<Usuario> usuarioListNew = tipousuario.getUsuarioList();
            List<Permisosusuario> permisosusuarioListOld = persistentTipousuario.getPermisosusuarioList();
            List<Permisosusuario> permisosusuarioListNew = tipousuario.getPermisosusuarioList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its tipoUsuarioid field is not nullable.");
                }
            }
            for (Permisosusuario permisosusuarioListOldPermisosusuario : permisosusuarioListOld) {
                if (!permisosusuarioListNew.contains(permisosusuarioListOldPermisosusuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Permisosusuario " + permisosusuarioListOldPermisosusuario + " since its tipousuarioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            tipousuario.setUsuarioList(usuarioListNew);
            List<Permisosusuario> attachedPermisosusuarioListNew = new ArrayList<Permisosusuario>();
            for (Permisosusuario permisosusuarioListNewPermisosusuarioToAttach : permisosusuarioListNew) {
                permisosusuarioListNewPermisosusuarioToAttach = em.getReference(permisosusuarioListNewPermisosusuarioToAttach.getClass(), permisosusuarioListNewPermisosusuarioToAttach.getPagina());
                attachedPermisosusuarioListNew.add(permisosusuarioListNewPermisosusuarioToAttach);
            }
            permisosusuarioListNew = attachedPermisosusuarioListNew;
            tipousuario.setPermisosusuarioList(permisosusuarioListNew);
            tipousuario = em.merge(tipousuario);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Tipousuario oldTipoUsuarioidOfUsuarioListNewUsuario = usuarioListNewUsuario.getTipoUsuarioid();
                    usuarioListNewUsuario.setTipoUsuarioid(tipousuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldTipoUsuarioidOfUsuarioListNewUsuario != null && !oldTipoUsuarioidOfUsuarioListNewUsuario.equals(tipousuario)) {
                        oldTipoUsuarioidOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldTipoUsuarioidOfUsuarioListNewUsuario = em.merge(oldTipoUsuarioidOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Permisosusuario permisosusuarioListNewPermisosusuario : permisosusuarioListNew) {
                if (!permisosusuarioListOld.contains(permisosusuarioListNewPermisosusuario)) {
                    Tipousuario oldTipousuarioIdOfPermisosusuarioListNewPermisosusuario = permisosusuarioListNewPermisosusuario.getTipousuarioId();
                    permisosusuarioListNewPermisosusuario.setTipousuarioId(tipousuario);
                    permisosusuarioListNewPermisosusuario = em.merge(permisosusuarioListNewPermisosusuario);
                    if (oldTipousuarioIdOfPermisosusuarioListNewPermisosusuario != null && !oldTipousuarioIdOfPermisosusuarioListNewPermisosusuario.equals(tipousuario)) {
                        oldTipousuarioIdOfPermisosusuarioListNewPermisosusuario.getPermisosusuarioList().remove(permisosusuarioListNewPermisosusuario);
                        oldTipousuarioIdOfPermisosusuarioListNewPermisosusuario = em.merge(oldTipousuarioIdOfPermisosusuarioListNewPermisosusuario);
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
                Integer id = tipousuario.getId();
                if (findTipousuario(id) == null) {
                    throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.");
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
            Tipousuario tipousuario;
            try {
                tipousuario = em.getReference(Tipousuario.class, id);
                tipousuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipousuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = tipousuario.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipousuario (" + tipousuario + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable tipoUsuarioid field.");
            }
            List<Permisosusuario> permisosusuarioListOrphanCheck = tipousuario.getPermisosusuarioList();
            for (Permisosusuario permisosusuarioListOrphanCheckPermisosusuario : permisosusuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipousuario (" + tipousuario + ") cannot be destroyed since the Permisosusuario " + permisosusuarioListOrphanCheckPermisosusuario + " in its permisosusuarioList field has a non-nullable tipousuarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipousuario);
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

    public List<Tipousuario> findTipousuarioEntities() {
        return findTipousuarioEntities(true, -1, -1);
    }

    public List<Tipousuario> findTipousuarioEntities(int maxResults, int firstResult) {
        return findTipousuarioEntities(false, maxResults, firstResult);
    }

    private List<Tipousuario> findTipousuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipousuario.class));
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

    public Tipousuario findTipousuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipousuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipousuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipousuario> rt = cq.from(Tipousuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
