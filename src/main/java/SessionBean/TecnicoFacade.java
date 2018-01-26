/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import entities.Tecnico;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author danielnacher
 */
@Stateless
public class TecnicoFacade extends AbstractFacade<Tecnico> {

    @PersistenceContext(unitName = "com.mycompany_vivienda_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TecnicoFacade() {
        super(Tecnico.class);
    }
    
}
