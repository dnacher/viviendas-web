/*
 * @author danielnacher
 */
package service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jboss.logging.Logger;
import entities.Usuario;

/**
 *
 * @author Usuario
 */
@Stateless
public class UsuarioSessionBean implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private javax.ejb.SessionContext ctx;
    private String codigoUsuario;

    /**
     * Devuelve todos los elementos del tipo
     *
     * @return
     */
    public List<Usuario> obtenerTodos() throws Exception {
        Logger.getLogger(this.getClass()).log(org.jboss.logging.Logger.Level.INFO, "Comenzando todos");
        try {
            CodigueraDAO<Usuario> codDAO = new CodigueraDAO<>(em, Usuario.class);
            return codDAO.obtenerTodos();
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public Usuario traerUsuarioXNombre(String usuario) throws Exception {
        Logger.getLogger(this.getClass()).log(org.jboss.logging.Logger.Level.INFO, "Comenzando traer Usuario por Nombre");
        String consulta = "select u from Usuario u ";
        String cadena = "";
        boolean conWhere = false;
        if (usuario != null) {
            conWhere = true;
            cadena = cadena + " (u.nombre" + "=:nombreUsuario) ";
        }
        java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.INFO, " cadena " + cadena);
        if (conWhere) {
            consulta = consulta + " where " + cadena;
            java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.INFO, " consulta " + consulta);

        }
        Query query = em.createQuery(consulta);
        if (usuario != null) {
            query.setParameter("nombreUsuario", usuario);
        }
        List<Usuario> lista = query.getResultList();
        if(lista.isEmpty()){
            return null;
        }else{
            return lista.get(0);
        }
    }

    /**
     * Guarda un elemento del tipo. Si el elemento no tiene id hace un
     * persistence, en caso contrario hace un merge.
     *
     * @param rolusu
     * @return
     * @throws GeneralException
     */
    public Usuario guardar(Usuario rolusu) throws Exception {
        if (rolusu != null) {
            try {
//                codigoUsuario = ctx.getCallerPrincipal().getName();
//                if (RelUsuRolBusinessValidation.validar(rolusu)) {
//                    CodigueraDAO<RelUsuRol> codDAO = new CodigueraDAO<>(em, RelUsuRol.class);
//                    boolean guardar = true;
//                    
//                    if(guardar){
//                        rolusu.setRurUltModUsu(codigoUsuario);
//                        //rolusu.setRurUltModOrigen(du.getOrigen());
//                        rolusu.setRurUltModFecha(new Date());
//                        return codDAO.guardar(rolusu);
//                    }
//                }
            } catch (Exception ex) {
//                Logger.getLogger(this.getClass().getName()).log(Logger.Level.FATAL, ex, ex);
//                throw new TechnicalException(ex);
            }

        }
        return rolusu;

    }

    /**
     * Carga los objetos relacionados en la versión indicada.
     *
     * @param rolusu
     * @return
     * @throws GeneralException
     */
    /**
     * Elimina el objeto indicado
     *
     * @param rolusu
     * @throws GeneralException
     */
    public void eliminar(Usuario rolusu) throws Exception {
        if (rolusu != null) {
//            try {
//                CodigueraDAO<RelUsuRol> codDAO = new CodigueraDAO<>(em, RelUsuRol.class);
//                codDAO.eliminar(rolusu);
//            } catch (Exception ex) {
//                Logger.getLogger(this.getClass().getName()).log(Logger.Level.FATAL, ex, ex);
//                throw new TechnicalException(ex);
//            }

        }
    }

    /**
     * Devuelve el objeto del tipo que tiene el id indicado
     *
     * @param id
     * @return
     * @throws GeneralException
     */
//    @Override
//    public RelUsuRol obtenerPorId(Integer id) throws GeneralException {
//        if (id != null) {
//            try {
//                CodigueraDAO<RelUsuRol> codDAO = new CodigueraDAO<>(em, RelUsuRol.class);
//                return codDAO.obtenerPorId(id);
//            } catch (Exception ex) {
//                Logger.getLogger(this.getClass().getName()).log(Logger.Level.FATAL, ex, ex);
//                throw new TechnicalException(ex);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Realiza la búsqueda de los elementos que satisfacen el criterio
//     * La búsqueda es por códig y nombre
//     * @param codigo
//     * @param nombre
//     * @return
//     * @throws GeneralException 
//     */
//    @Override
//    public Collection<RelUsuRol> buscarSimple(String codigo, String nombre) throws GeneralException {
//        CodigueraDAO<RelUsuRol> codDAO = new CodigueraDAO<>(em, RelUsuRol.class);
//        return codDAO.busquedaSimple(codigo, nombre);
//    }
}

//package service;
//
//import java.io.Serializable;
//import javax.persistence.Query;
//import javax.persistence.EntityNotFoundException;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//import entities.Tipousuario;
//import entities.Usuario;
//import java.util.List;
//import javax.annotation.Resource;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
//import service.exceptions.NonexistentEntityException;
//import service.exceptions.RollbackFailureException;
//
///**
// *
// * @author danielnacher
// */
//public class UsuarioJpaController implements Serializable {
//
//    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
//        this.utx = utx;
//        this.emf = emf;
//    }
//    
//    @Resource
//    private UserTransaction utx;
//    
//    @PersistenceContext
//    private EntityManagerFactory emf;
//
//    public EntityManager getEntityManager() {
//        return emf.createEntityManager();
//    }
//
//    public void create(Usuario usuario) throws RollbackFailureException, Exception {
//        EntityManager em = null;
//        try {
//            utx.begin();
//            em = getEntityManager();
//            Tipousuario tipoUsuarioid = usuario.getTipoUsuarioid();
//            if (tipoUsuarioid != null) {
//                tipoUsuarioid = em.getReference(tipoUsuarioid.getClass(), tipoUsuarioid.getId());
//                usuario.setTipoUsuarioid(tipoUsuarioid);
//            }
//            em.persist(usuario);
//            if (tipoUsuarioid != null) {
//                tipoUsuarioid.getUsuarioList().add(usuario);
//                tipoUsuarioid = em.merge(tipoUsuarioid);
//            }
//            utx.commit();
//        } catch (Exception ex) {
//            try {
//                utx.rollback();
//            } catch (Exception re) {
//                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void edit(Usuario usuario) throws NonexistentEntityException, RollbackFailureException, Exception {
//        EntityManager em = null;
//        try {
//            utx.begin();
//            em = getEntityManager();
//            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
//            Tipousuario tipoUsuarioidOld = persistentUsuario.getTipoUsuarioid();
//            Tipousuario tipoUsuarioidNew = usuario.getTipoUsuarioid();
//            if (tipoUsuarioidNew != null) {
//                tipoUsuarioidNew = em.getReference(tipoUsuarioidNew.getClass(), tipoUsuarioidNew.getId());
//                usuario.setTipoUsuarioid(tipoUsuarioidNew);
//            }
//            usuario = em.merge(usuario);
//            if (tipoUsuarioidOld != null && !tipoUsuarioidOld.equals(tipoUsuarioidNew)) {
//                tipoUsuarioidOld.getUsuarioList().remove(usuario);
//                tipoUsuarioidOld = em.merge(tipoUsuarioidOld);
//            }
//            if (tipoUsuarioidNew != null && !tipoUsuarioidNew.equals(tipoUsuarioidOld)) {
//                tipoUsuarioidNew.getUsuarioList().add(usuario);
//                tipoUsuarioidNew = em.merge(tipoUsuarioidNew);
//            }
//            utx.commit();
//        } catch (Exception ex) {
//            try {
//                utx.rollback();
//            } catch (Exception re) {
//                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
//            }
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = usuario.getIdUsuario();
//                if (findUsuario(id) == null) {
//                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
//        EntityManager em = null;
//        try {
//            utx.begin();
//            em = getEntityManager();
//            Usuario usuario;
//            try {
//                usuario = em.getReference(Usuario.class, id);
//                usuario.getIdUsuario();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
//            }
//            Tipousuario tipoUsuarioid = usuario.getTipoUsuarioid();
//            if (tipoUsuarioid != null) {
//                tipoUsuarioid.getUsuarioList().remove(usuario);
//                tipoUsuarioid = em.merge(tipoUsuarioid);
//            }
//            em.remove(usuario);
//            utx.commit();
//        } catch (Exception ex) {
//            try {
//                utx.rollback();
//            } catch (Exception re) {
//                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
//
//    public List<Usuario> findUsuarioEntities() {
//        return findUsuarioEntities(true, -1, -1);
//    }
//
//    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
//        return findUsuarioEntities(false, maxResults, firstResult);
//    }
//
//    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(Usuario.class));
//            Query q = em.createQuery(cq);
//            if (!all) {
//                q.setMaxResults(maxResults);
//                q.setFirstResult(firstResult);
//            }
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }
//
//    public Usuario findUsuario(Integer id) {
//        EntityManager em = getEntityManager();
//        try {
//            return em.find(Usuario.class, id);
//        } finally {
//            em.close();
//        }
//    }
//
//    public int getUsuarioCount() {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<Usuario> rt = cq.from(Usuario.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
//    
//}
