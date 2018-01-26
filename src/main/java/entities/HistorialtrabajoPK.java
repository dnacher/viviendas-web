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
public class HistorialtrabajoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idHistorialTrabajo")
    private int idHistorialTrabajo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Trabajo_idTrabajo")
    private int trabajoidTrabajo;

    public HistorialtrabajoPK() {
    }

    public HistorialtrabajoPK(int idHistorialTrabajo, int trabajoidTrabajo) {
        this.idHistorialTrabajo = idHistorialTrabajo;
        this.trabajoidTrabajo = trabajoidTrabajo;
    }

    public int getIdHistorialTrabajo() {
        return idHistorialTrabajo;
    }

    public void setIdHistorialTrabajo(int idHistorialTrabajo) {
        this.idHistorialTrabajo = idHistorialTrabajo;
    }

    public int getTrabajoidTrabajo() {
        return trabajoidTrabajo;
    }

    public void setTrabajoidTrabajo(int trabajoidTrabajo) {
        this.trabajoidTrabajo = trabajoidTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idHistorialTrabajo;
        hash += (int) trabajoidTrabajo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialtrabajoPK)) {
            return false;
        }
        HistorialtrabajoPK other = (HistorialtrabajoPK) object;
        if (this.idHistorialTrabajo != other.idHistorialTrabajo) {
            return false;
        }
        if (this.trabajoidTrabajo != other.trabajoidTrabajo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.HistorialtrabajoPK[ idHistorialTrabajo=" + idHistorialTrabajo + ", trabajoidTrabajo=" + trabajoidTrabajo + " ]";
    }
    
}
