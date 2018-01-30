/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Cuotaconvenio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nacher
 */
@Stateless
public class CuotaconvenioFacade extends AbstractFacade<Cuotaconvenio> implements CuotaconvenioFacadeLocal {

    @PersistenceContext(unitName = "com.mycompany_vivienda_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuotaconvenioFacade() {
        super(Cuotaconvenio.class);
    }
    
}
