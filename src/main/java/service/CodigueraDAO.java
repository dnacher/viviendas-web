/*
 * 
 * 
 */
package service;
import annotations.AtributoCodigo;
import annotations.AtributoNombre;
import utils.Utils;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 * @param <T>
 */
public class CodigueraDAO<T> implements Serializable {

    private EntityManager em;
    private final Class<T> clazz;

    public CodigueraDAO(EntityManager em, Class clase) {
        this.em = em;
        clazz = clase;
    }

    public List<T> obtenerTodos() throws Exception {
        try {
            String consulta = clazz.getSimpleName() + ".findAll";
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " parametrized type " + consulta);
            return em.createNamedQuery(consulta).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
            throw new Exception(ex);
        }
    }

    public List<T> obtenerActivos() throws Exception {
        try {
            String consulta = clazz.getSimpleName() + ".findByActivo";
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " parametrized type " + consulta);
            return em.createNamedQuery(consulta).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
            throw new Exception(ex);
        }
    }

    public List<T> busquedaSimple(String codigo, String nombre) {
        String campoCodigo = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoCodigo.class);
        String campoNombre = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoNombre.class);
        String consulta = "select s from " + clazz.getName() + " s ";
        String cadena = "";
        boolean conAnd = false;
        boolean conWhere = false;
        if (!Utils.esVacioONulo(codigo)) {
            conWhere = true;
            cadena = cadena + " (s." + campoCodigo + "=:codigo) ";
            conAnd = true;
        }
        if (!Utils.esVacioONulo(nombre)) {
            conWhere = true;
            if (conAnd) {
                cadena += " and ";
            }
            cadena = cadena + " (s." + campoNombre + " like :nombre) ";
        }
        if (conWhere) {
            consulta = consulta + " where " + cadena;
        }
        Query query = em.createQuery(consulta);
        if (!Utils.esVacioONulo(codigo)) {
            query.setParameter("codigo", codigo);
        }
        if (!Utils.esVacioONulo(nombre)) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        return query.getResultList();
    }

    public List<T> busquedaSimple(Integer codigo, String nombre) {
        String campoCodigo = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoCodigo.class);
        String campoNombre = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoNombre.class);
        String consulta = "select s from " + clazz.getName() + " s ";
        String cadena = "";
        boolean conAnd = false;
        boolean conWhere = false;
        if (codigo != null) {
            conWhere = true;
            cadena = cadena + " (s." + campoCodigo + "=:codigo) ";
            conAnd = true;
        }
        if (!Utils.esVacioONulo(nombre)) {
            conWhere = true;
            if (conAnd) {
                cadena += " and ";
            }
            cadena = cadena + " (s." + campoNombre + " like :nombre) ";
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " cadena " + cadena);
        if (conWhere) {
            consulta = consulta + " where " + cadena;
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " consulta " + consulta);

        }
        Query query = em.createQuery(consulta);
        if (codigo != null) {
            query.setParameter("codigo", codigo);
        }
        if (!Utils.esVacioONulo(nombre)) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        return query.getResultList();
    }

    public T guardar(T elemento) throws Exception {
        try {
            Field campoId = ReflectionUtils.obtenerCampoAnotado(clazz, Id.class);
            Object value = campoId.get(elemento);
            if (value == null) {
                em.persist(elemento);
            } else {
                elemento = em.merge(elemento);
            }
        } catch (Exception ex) {
            Logger.getLogger(CodigueraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
        return elemento;
    }

    public void eliminar(T elemento) throws Exception {
        try {
            Field campoId = ReflectionUtils.obtenerCampoAnotado(clazz, Id.class);
            Object value = campoId.get(elemento);
            elemento = em.find(clazz, value);
            em.remove(elemento);
        } catch (Exception ex) {
            Logger.getLogger(CodigueraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    public T obtenerPorId(Integer id) throws Exception {
        try {
            return em.find(clazz, id);
        } catch (Exception ex) {
            Logger.getLogger(CodigueraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }

    public List<T> obtenerPorCampo(Integer dato, String campo) {
        String consulta = "select s from " + clazz.getName() + " s ";
        String cadena = "";
        boolean conWhere = false;
        if (dato != null) {
            conWhere = true;
            cadena = cadena + " (s." + campo + "=:dato) ";
        }
        if (conWhere) {
            consulta = consulta + " where " + cadena;
        }
        Query query = em.createQuery(consulta);
        if (dato != null) {
            query.setParameter("dato", dato);
        }
        return query.getResultList();
    }

    public List<T> obtenerPorCodigo(String campo, String valor) throws Exception {
        try {

            String consulta = "select s from " + clazz.getName() + " s where s." + campo + "=:valor";
            Query query = em.createQuery(consulta);
            query.setParameter("valor", valor);

            return query.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(CodigueraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex);
        }
    }
}
