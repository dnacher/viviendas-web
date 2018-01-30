/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Tipoduracion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface TipoduracionFacadeLocal {

    void create(Tipoduracion tipoduracion);

    void edit(Tipoduracion tipoduracion);

    void remove(Tipoduracion tipoduracion);

    Tipoduracion find(Object id);

    List<Tipoduracion> findAll();

    List<Tipoduracion> findRange(int[] range);

    int count();
    
}
