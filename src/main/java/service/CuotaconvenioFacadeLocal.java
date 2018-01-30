/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Cuotaconvenio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Nacher
 */
@Local
public interface CuotaconvenioFacadeLocal {

    void create(Cuotaconvenio cuotaconvenio);

    void edit(Cuotaconvenio cuotaconvenio);

    void remove(Cuotaconvenio cuotaconvenio);

    Cuotaconvenio find(Object id);

    List<Cuotaconvenio> findAll();

    List<Cuotaconvenio> findRange(int[] range);

    int count();
    
}
