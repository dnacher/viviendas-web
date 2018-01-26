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
public class OtrosgastosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idotrosGastos")
    private int idotrosGastos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unidad_idUnidad")
    private int unidadidUnidad;

    public OtrosgastosPK() {
    }

    public OtrosgastosPK(int idotrosGastos, int unidadidUnidad) {
        this.idotrosGastos = idotrosGastos;
        this.unidadidUnidad = unidadidUnidad;
    }

    public int getIdotrosGastos() {
        return idotrosGastos;
    }

    public void setIdotrosGastos(int idotrosGastos) {
        this.idotrosGastos = idotrosGastos;
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
        hash += (int) idotrosGastos;
        hash += (int) unidadidUnidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtrosgastosPK)) {
            return false;
        }
        OtrosgastosPK other = (OtrosgastosPK) object;
        if (this.idotrosGastos != other.idotrosGastos) {
            return false;
        }
        if (this.unidadidUnidad != other.unidadidUnidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.OtrosgastosPK[ idotrosGastos=" + idotrosGastos + ", unidadidUnidad=" + unidadidUnidad + " ]";
    }
    
}
