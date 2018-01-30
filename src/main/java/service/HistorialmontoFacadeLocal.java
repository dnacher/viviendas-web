/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Historialmonto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface HistorialmontoFacadeLocal {

    void create(Historialmonto historialmonto);

    void edit(Historialmonto historialmonto);

    void remove(Historialmonto historialmonto);

    Historialmonto find(Object id);

    List<Historialmonto> findAll();

    List<Historialmonto> findRange(int[] range);

    int count();
    
}
