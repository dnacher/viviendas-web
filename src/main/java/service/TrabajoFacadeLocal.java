/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Trabajo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface TrabajoFacadeLocal {

    void create(Trabajo trabajo);

    void edit(Trabajo trabajo);

    void remove(Trabajo trabajo);

    Trabajo find(Object id);

    List<Trabajo> findAll();

    List<Trabajo> findRange(int[] range);

    int count();
    
}
