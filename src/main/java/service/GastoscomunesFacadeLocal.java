/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Gastoscomunes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface GastoscomunesFacadeLocal {

    void create(Gastoscomunes gastoscomunes);

    void edit(Gastoscomunes gastoscomunes);

    void remove(Gastoscomunes gastoscomunes);

    Gastoscomunes find(Object id);

    List<Gastoscomunes> findAll();

    List<Gastoscomunes> findRange(int[] range);

    int count();
    
}
