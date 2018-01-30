/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Permisosusuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface PermisosusuarioFacadeLocal {

    void create(Permisosusuario permisosusuario);

    void edit(Permisosusuario permisosusuario);

    void remove(Permisosusuario permisosusuario);

    Permisosusuario find(Object id);

    List<Permisosusuario> findAll();

    List<Permisosusuario> findRange(int[] range);

    int count();
    
}
