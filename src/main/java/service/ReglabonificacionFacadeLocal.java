/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Reglabonificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface ReglabonificacionFacadeLocal {

    void create(Reglabonificacion reglabonificacion);

    void edit(Reglabonificacion reglabonificacion);

    void remove(Reglabonificacion reglabonificacion);

    Reglabonificacion find(Object id);

    List<Reglabonificacion> findAll();

    List<Reglabonificacion> findRange(int[] range);

    int count();
    
}
