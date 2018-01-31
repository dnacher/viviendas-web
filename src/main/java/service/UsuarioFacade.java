/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Usuario;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jboss.logging.Logger;

/**
 *
 * @author Nacher
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_vivienda_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    
    @Override
    public Usuario traerUsuarioXNombre(String usuario) throws Exception {
        Logger.getLogger(this.getClass()).log(org.jboss.logging.Logger.Level.INFO, "Comenzando traer Usuario por Nombre");
        String consulta = "select u from Usuario u ";
        String cadena = "";
        boolean conWhere = false;
        if (usuario != null) {
            conWhere = true;
            cadena = cadena + " (u.nombre" + "=:nombreUsuario) ";
        }
        java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.INFO, " cadena {0}", cadena);
        if (conWhere) {
            consulta = consulta + " where " + cadena;
            java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.INFO, " consulta {0}", consulta);

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
    
}
