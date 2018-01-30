/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Monto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface MontoFacadeLocal {

    void create(Monto monto);

    void edit(Monto monto);

    void remove(Monto monto);

    Monto find(Object id);

    List<Monto> findAll();

    List<Monto> findRange(int[] range);

    int count();
    
}
