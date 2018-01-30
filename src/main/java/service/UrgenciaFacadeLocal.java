/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Urgencia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface UrgenciaFacadeLocal {

    void create(Urgencia urgencia);

    void edit(Urgencia urgencia);

    void remove(Urgencia urgencia);

    Urgencia find(Object id);

    List<Urgencia> findAll();

    List<Urgencia> findRange(int[] range);

    int count();
    
}
