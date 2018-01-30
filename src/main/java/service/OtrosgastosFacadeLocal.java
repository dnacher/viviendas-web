/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Otrosgastos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface OtrosgastosFacadeLocal {

    void create(Otrosgastos otrosgastos);

    void edit(Otrosgastos otrosgastos);

    void remove(Otrosgastos otrosgastos);

    Otrosgastos find(Object id);

    List<Otrosgastos> findAll();

    List<Otrosgastos> findRange(int[] range);

    int count();
    
}
