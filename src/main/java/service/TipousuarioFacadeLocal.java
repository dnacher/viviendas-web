/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Tipousuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface TipousuarioFacadeLocal {

    void create(Tipousuario tipousuario);

    void edit(Tipousuario tipousuario);

    void remove(Tipousuario tipousuario);

    Tipousuario find(Object id);

    List<Tipousuario> findAll();

    List<Tipousuario> findRange(int[] range);

    int count();
    
}
