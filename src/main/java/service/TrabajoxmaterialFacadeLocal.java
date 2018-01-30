/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Trabajoxmaterial;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface TrabajoxmaterialFacadeLocal {

    void create(Trabajoxmaterial trabajoxmaterial);

    void edit(Trabajoxmaterial trabajoxmaterial);

    void remove(Trabajoxmaterial trabajoxmaterial);

    Trabajoxmaterial find(Object id);

    List<Trabajoxmaterial> findAll();

    List<Trabajoxmaterial> findRange(int[] range);

    int count();
    
}
