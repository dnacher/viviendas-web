/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import entities.Listaprecios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author danielnacher
 */
@Stateless
public class ListapreciosFacade extends AbstractFacade<Listaprecios> {

    @PersistenceContext(unitName = "com.mycompany_vivienda_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListapreciosFacade() {
        super(Listaprecios.class);
    }
    
}
