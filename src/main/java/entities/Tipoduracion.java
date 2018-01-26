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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tipoduracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoduracion.findAll", query = "SELECT t FROM Tipoduracion t")
    , @NamedQuery(name = "Tipoduracion.findByIdtipoDuracion", query = "SELECT t FROM Tipoduracion t WHERE t.idtipoDuracion = :idtipoDuracion")
    , @NamedQuery(name = "Tipoduracion.findByNombre", query = "SELECT t FROM Tipoduracion t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Tipoduracion.findByActivo", query = "SELECT t FROM Tipoduracion t WHERE t.activo = :activo")
    , @NamedQuery(name = "Tipoduracion.findByDescripcion", query = "SELECT t FROM Tipoduracion t WHERE t.descripcion = :descripcion")})
public class Tipoduracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoDuracion")
    private Integer idtipoDuracion;
    @Size(max = 45)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @Size(max = 80)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoDuracionidtipoDuracion")
    private List<Trabajo> trabajoList;

    public Tipoduracion() {
    }

    public Tipoduracion(Integer idtipoDuracion) {
        this.idtipoDuracion = idtipoDuracion;
    }

    public Tipoduracion(Integer idtipoDuracion, boolean activo) {
        this.idtipoDuracion = idtipoDuracion;
        this.activo = activo;
    }

    public Integer getIdtipoDuracion() {
        return idtipoDuracion;
    }

    public void setIdtipoDuracion(Integer idtipoDuracion) {
        this.idtipoDuracion = idtipoDuracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Trabajo> getTrabajoList() {
        return trabajoList;
    }

    public void setTrabajoList(List<Trabajo> trabajoList) {
        this.trabajoList = trabajoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoDuracion != null ? idtipoDuracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoduracion)) {
            return false;
        }
        Tipoduracion other = (Tipoduracion) object;
        if ((this.idtipoDuracion == null && other.idtipoDuracion != null) || (this.idtipoDuracion != null && !this.idtipoDuracion.equals(other.idtipoDuracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Tipoduracion[ idtipoDuracion=" + idtipoDuracion + " ]";
    }
    
}
