/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "trabajoxmaterial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajoxmaterial.findAll", query = "SELECT t FROM Trabajoxmaterial t")
    , @NamedQuery(name = "Trabajoxmaterial.findByTrabajoidTrabajo", query = "SELECT t FROM Trabajoxmaterial t WHERE t.trabajoidTrabajo = :trabajoidTrabajo")
    , @NamedQuery(name = "Trabajoxmaterial.findByCantidad", query = "SELECT t FROM Trabajoxmaterial t WHERE t.cantidad = :cantidad")})
public class Trabajoxmaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Trabajo_idTrabajo")
    private Integer trabajoidTrabajo;
    @Column(name = "Cantidad")
    private Integer cantidad;
    @JoinColumn(name = "Trabajo_idTrabajo", referencedColumnName = "idTrabajo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Trabajo trabajo;
    @JoinColumn(name = "material_idmaterial", referencedColumnName = "idmaterial")
    @ManyToOne(optional = false)
    private Material materialIdmaterial;

    public Trabajoxmaterial() {
    }

    public Trabajoxmaterial(Integer trabajoidTrabajo) {
        this.trabajoidTrabajo = trabajoidTrabajo;
    }

    public Integer getTrabajoidTrabajo() {
        return trabajoidTrabajo;
    }

    public void setTrabajoidTrabajo(Integer trabajoidTrabajo) {
        this.trabajoidTrabajo = trabajoidTrabajo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Material getMaterialIdmaterial() {
        return materialIdmaterial;
    }

    public void setMaterialIdmaterial(Material materialIdmaterial) {
        this.materialIdmaterial = materialIdmaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trabajoidTrabajo != null ? trabajoidTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajoxmaterial)) {
            return false;
        }
        Trabajoxmaterial other = (Trabajoxmaterial) object;
        if ((this.trabajoidTrabajo == null && other.trabajoidTrabajo != null) || (this.trabajoidTrabajo != null && !this.trabajoidTrabajo.equals(other.trabajoidTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Trabajoxmaterial[ trabajoidTrabajo=" + trabajoidTrabajo + " ]";
    }
    
}
