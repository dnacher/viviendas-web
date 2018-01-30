/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Historialtrabajo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface HistorialtrabajoFacadeLocal {

    void create(Historialtrabajo historialtrabajo);

    void edit(Historialtrabajo historialtrabajo);

    void remove(Historialtrabajo historialtrabajo);

    Historialtrabajo find(Object id);

    List<Historialtrabajo> findAll();

    List<Historialtrabajo> findRange(int[] range);

    int count();
    
}
