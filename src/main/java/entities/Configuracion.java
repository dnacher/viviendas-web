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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "configuracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")
    , @NamedQuery(name = "Configuracion.findByNombreTabla", query = "SELECT c FROM Configuracion c WHERE c.nombreTabla = :nombreTabla")
    , @NamedQuery(name = "Configuracion.findById", query = "SELECT c FROM Configuracion c WHERE c.id = :id")})
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreTabla")
    private String nombreTabla;
    @Column(name = "id")
    private Integer id;

    public Configuracion() {
    }

    public Configuracion(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreTabla != null ? nombreTabla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.nombreTabla == null && other.nombreTabla != null) || (this.nombreTabla != null && !this.nombreTabla.equals(other.nombreTabla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Configuracion[ nombreTabla=" + nombreTabla + " ]";
    }
    
}
