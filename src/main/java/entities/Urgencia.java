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
@Table(name = "urgencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Urgencia.findAll", query = "SELECT u FROM Urgencia u")
    , @NamedQuery(name = "Urgencia.findByIdurgencia", query = "SELECT u FROM Urgencia u WHERE u.idurgencia = :idurgencia")
    , @NamedQuery(name = "Urgencia.findByNombre", query = "SELECT u FROM Urgencia u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Urgencia.findByDescripcion", query = "SELECT u FROM Urgencia u WHERE u.descripcion = :descripcion")
    , @NamedQuery(name = "Urgencia.findByActivo", query = "SELECT u FROM Urgencia u WHERE u.activo = :activo")})
public class Urgencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idurgencia")
    private Integer idurgencia;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 80)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "urgenciaIdurgencia")
    private List<Trabajo> trabajoList;

    public Urgencia() {
    }

    public Urgencia(Integer idurgencia) {
        this.idurgencia = idurgencia;
    }

    public Urgencia(Integer idurgencia, boolean activo) {
        this.idurgencia = idurgencia;
        this.activo = activo;
    }

    public Integer getIdurgencia() {
        return idurgencia;
    }

    public void setIdurgencia(Integer idurgencia) {
        this.idurgencia = idurgencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
        hash += (idurgencia != null ? idurgencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Urgencia)) {
            return false;
        }
        Urgencia other = (Urgencia) object;
        if ((this.idurgencia == null && other.idurgencia != null) || (this.idurgencia != null && !this.idurgencia.equals(other.idurgencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Urgencia[ idurgencia=" + idurgencia + " ]";
    }
    
}
