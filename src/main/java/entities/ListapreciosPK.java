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
public class ListapreciosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idlistaPrecios")
    private int idlistaPrecios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "material_idmaterial")
    private int materialIdmaterial;

    public ListapreciosPK() {
    }

    public ListapreciosPK(int idlistaPrecios, int materialIdmaterial) {
        this.idlistaPrecios = idlistaPrecios;
        this.materialIdmaterial = materialIdmaterial;
    }

    public int getIdlistaPrecios() {
        return idlistaPrecios;
    }

    public void setIdlistaPrecios(int idlistaPrecios) {
        this.idlistaPrecios = idlistaPrecios;
    }

    public int getMaterialIdmaterial() {
        return materialIdmaterial;
    }

    public void setMaterialIdmaterial(int materialIdmaterial) {
        this.materialIdmaterial = materialIdmaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idlistaPrecios;
        hash += (int) materialIdmaterial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListapreciosPK)) {
            return false;
        }
        ListapreciosPK other = (ListapreciosPK) object;
        if (this.idlistaPrecios != other.idlistaPrecios) {
            return false;
        }
        if (this.materialIdmaterial != other.materialIdmaterial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ListapreciosPK[ idlistaPrecios=" + idlistaPrecios + ", materialIdmaterial=" + materialIdmaterial + " ]";
    }
    
}
