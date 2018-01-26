/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author danielnacher
 */
@Embeddable
public class GastoscomunesPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idGastosComunes")
    private int idGastosComunes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unidad_idUnidad")
    private int unidadidUnidad;

    public GastoscomunesPK() {
    }

    public GastoscomunesPK(int idGastosComunes, int unidadidUnidad) {
        this.idGastosComunes = idGastosComunes;
        this.unidadidUnidad = unidadidUnidad;
    }

    public int getIdGastosComunes() {
        return idGastosComunes;
    }

    public void setIdGastosComunes(int idGastosComunes) {
        this.idGastosComunes = idGastosComunes;
    }

    public int getUnidadidUnidad() {
        return unidadidUnidad;
    }

    public void setUnidadidUnidad(int unidadidUnidad) {
        this.unidadidUnidad = unidadidUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idGastosComunes;
        hash += (int) unidadidUnidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GastoscomunesPK)) {
            return false;
        }
        GastoscomunesPK other = (GastoscomunesPK) object;
        if (this.idGastosComunes != other.idGastosComunes) {
            return false;
        }
        if (this.unidadidUnidad != other.unidadidUnidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.GastoscomunesPK[ idGastosComunes=" + idGastosComunes + ", unidadidUnidad=" + unidadidUnidad + " ]";
    }
    
}
