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
public class ConvenioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idconvenio")
    private int idconvenio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "unidad_idUnidad")
    private int unidadidUnidad;

    public ConvenioPK() {
    }

    public ConvenioPK(int idconvenio, int unidadidUnidad) {
        this.idconvenio = idconvenio;
        this.unidadidUnidad = unidadidUnidad;
    }

    public int getIdconvenio() {
        return idconvenio;
    }

    public void setIdconvenio(int idconvenio) {
        this.idconvenio = idconvenio;
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
        hash += (int) idconvenio;
        hash += (int) unidadidUnidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConvenioPK)) {
            return false;
        }
        ConvenioPK other = (ConvenioPK) object;
        if (this.idconvenio != other.idconvenio) {
            return false;
        }
        if (this.unidadidUnidad != other.unidadidUnidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ConvenioPK[ idconvenio=" + idconvenio + ", unidadidUnidad=" + unidadidUnidad + " ]";
    }
    
}
