/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "tecnico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tecnico.findAll", query = "SELECT t FROM Tecnico t")
    , @NamedQuery(name = "Tecnico.findByIdTecnico", query = "SELECT t FROM Tecnico t WHERE t.idTecnico = :idTecnico")
    , @NamedQuery(name = "Tecnico.findByNombre", query = "SELECT t FROM Tecnico t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Tecnico.findByApellido", query = "SELECT t FROM Tecnico t WHERE t.apellido = :apellido")
    , @NamedQuery(name = "Tecnico.findByTelefono", query = "SELECT t FROM Tecnico t WHERE t.telefono = :telefono")
    , @NamedQuery(name = "Tecnico.findByMail", query = "SELECT t FROM Tecnico t WHERE t.mail = :mail")
    , @NamedQuery(name = "Tecnico.findByCalificacion", query = "SELECT t FROM Tecnico t WHERE t.calificacion = :calificacion")
    , @NamedQuery(name = "Tecnico.findByEstado", query = "SELECT t FROM Tecnico t WHERE t.estado = :estado")
    , @NamedQuery(name = "Tecnico.findByFechaInicioEstado", query = "SELECT t FROM Tecnico t WHERE t.fechaInicioEstado = :fechaInicioEstado")
    , @NamedQuery(name = "Tecnico.findByFechaFinEstado", query = "SELECT t FROM Tecnico t WHERE t.fechaFinEstado = :fechaFinEstado")
    , @NamedQuery(name = "Tecnico.findByActivo", query = "SELECT t FROM Tecnico t WHERE t.activo = :activo")})
public class Tecnico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTecnico")
    private Integer idTecnico;
    @Size(max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 60)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono")
    private Integer telefono;
    @Size(max = 60)
    @Column(name = "mail")
    private String mail;
    @Column(name = "calificacion")
    private Integer calificacion;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fechaInicioEstado")
    @Temporal(TemporalType.DATE)
    private Date fechaInicioEstado;
    @Column(name = "fechaFinEstado")
    @Temporal(TemporalType.DATE)
    private Date fechaFinEstado;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tecnicoidTrabajador")
    private List<Historialtrabajo> historialtrabajoList;

    public Tecnico() {
    }

    public Tecnico(Integer idTecnico) {
        this.idTecnico = idTecnico;
    }

    public Integer getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(Integer idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaInicioEstado() {
        return fechaInicioEstado;
    }

    public void setFechaInicioEstado(Date fechaInicioEstado) {
        this.fechaInicioEstado = fechaInicioEstado;
    }

    public Date getFechaFinEstado() {
        return fechaFinEstado;
    }

    public void setFechaFinEstado(Date fechaFinEstado) {
        this.fechaFinEstado = fechaFinEstado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Historialtrabajo> getHistorialtrabajoList() {
        return historialtrabajoList;
    }

    public void setHistorialtrabajoList(List<Historialtrabajo> historialtrabajoList) {
        this.historialtrabajoList = historialtrabajoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTecnico != null ? idTecnico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tecnico)) {
            return false;
        }
        Tecnico other = (Tecnico) object;
        if ((this.idTecnico == null && other.idTecnico != null) || (this.idTecnico != null && !this.idTecnico.equals(other.idTecnico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Tecnico[ idTecnico=" + idTecnico + " ]";
    }
    
}
