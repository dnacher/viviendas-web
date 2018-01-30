/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Tipobonificacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface TipobonificacionFacadeLocal {

    void create(Tipobonificacion tipobonificacion);

    void edit(Tipobonificacion tipobonificacion);

    void remove(Tipobonificacion tipobonificacion);

    Tipobonificacion find(Object id);

    List<Tipobonificacion> findAll();

    List<Tipobonificacion> findRange(int[] range);

    int count();
    
}
