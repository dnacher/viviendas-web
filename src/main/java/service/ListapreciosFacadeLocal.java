/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Listaprecios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface ListapreciosFacadeLocal {

    void create(Listaprecios listaprecios);

    void edit(Listaprecios listaprecios);

    void remove(Listaprecios listaprecios);

    Listaprecios find(Object id);

    List<Listaprecios> findAll();

    List<Listaprecios> findRange(int[] range);

    int count();
    
}
