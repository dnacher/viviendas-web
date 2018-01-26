/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "reglabonificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reglabonificacion.findAll", query = "SELECT r FROM Reglabonificacion r")
    , @NamedQuery(name = "Reglabonificacion.findByIdreglaBonificacion", query = "SELECT r FROM Reglabonificacion r WHERE r.idreglaBonificacion = :idreglaBonificacion")
    , @NamedQuery(name = "Reglabonificacion.findByDescripcion", query = "SELECT r FROM Reglabonificacion r WHERE r.descripcion = :descripcion")
    , @NamedQuery(name = "Reglabonificacion.findByDiaAPagar", query = "SELECT r FROM Reglabonificacion r WHERE r.diaAPagar = :diaAPagar")
    , @NamedQuery(name = "Reglabonificacion.findByActivo", query = "SELECT r FROM Reglabonificacion r WHERE r.activo = :activo")})
public class Reglabonificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idreglaBonificacion")
    private Integer idreglaBonificacion;
    @Size(max = 80)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "diaAPagar")
    private Integer diaAPagar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaBonificacionidreglaBonificacion")
    private List<Convenio> convenioList;

    public Reglabonificacion() {
    }

    public Reglabonificacion(Integer idreglaBonificacion) {
        this.idreglaBonificacion = idreglaBonificacion;
    }

    public Reglabonificacion(Integer idreglaBonificacion, boolean activo) {
        this.idreglaBonificacion = idreglaBonificacion;
        this.activo = activo;
    }

    public Integer getIdreglaBonificacion() {
        return idreglaBonificacion;
    }

    public void setIdreglaBonificacion(Integer idreglaBonificacion) {
        this.idreglaBonificacion = idreglaBonificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDiaAPagar() {
        return diaAPagar;
    }

    public void setDiaAPagar(Integer diaAPagar) {
        this.diaAPagar = diaAPagar;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreglaBonificacion != null ? idreglaBonificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reglabonificacion)) {
            return false;
        }
        Reglabonificacion other = (Reglabonificacion) object;
        if ((this.idreglaBonificacion == null && other.idreglaBonificacion != null) || (this.idreglaBonificacion != null && !this.idreglaBonificacion.equals(other.idreglaBonificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Reglabonificacion[ idreglaBonificacion=" + idreglaBonificacion + " ]";
    }
    
}
