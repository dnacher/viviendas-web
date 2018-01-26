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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trabajo.findAll", query = "SELECT t FROM Trabajo t")
    , @NamedQuery(name = "Trabajo.findByIdTrabajo", query = "SELECT t FROM Trabajo t WHERE t.idTrabajo = :idTrabajo")
    , @NamedQuery(name = "Trabajo.findByDescripcion", query = "SELECT t FROM Trabajo t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Trabajo.findByFechaCreacion", query = "SELECT t FROM Trabajo t WHERE t.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Trabajo.findByFechaVisita", query = "SELECT t FROM Trabajo t WHERE t.fechaVisita = :fechaVisita")
    , @NamedQuery(name = "Trabajo.findByCalificacion", query = "SELECT t FROM Trabajo t WHERE t.calificacion = :calificacion")
    , @NamedQuery(name = "Trabajo.findByDuracionEstimada", query = "SELECT t FROM Trabajo t WHERE t.duracionEstimada = :duracionEstimada")
    , @NamedQuery(name = "Trabajo.findByDuracionReal", query = "SELECT t FROM Trabajo t WHERE t.duracionReal = :duracionReal")
    , @NamedQuery(name = "Trabajo.findByActivo", query = "SELECT t FROM Trabajo t WHERE t.activo = :activo")})
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTrabajo")
    private Integer idTrabajo;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "fechaVisita")
    @Temporal(TemporalType.DATE)
    private Date fechaVisita;
    @Column(name = "calificacion")
    private Integer calificacion;
    @Column(name = "duracionEstimada")
    private Integer duracionEstimada;
    @Column(name = "duracionReal")
    private Integer duracionReal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private short activo;
    @JoinColumn(name = "Unidad_idUnidad", referencedColumnName = "idUnidad")
    @ManyToOne(optional = false)
    private Unidad unidadidUnidad;
    @JoinColumn(name = "estado_idestado", referencedColumnName = "idestado")
    @ManyToOne(optional = false)
    private Estado estadoIdestado;
    @JoinColumn(name = "grupo_idgrupo", referencedColumnName = "idgrupo")
    @ManyToOne(optional = false)
    private Grupo grupoIdgrupo;
    @JoinColumn(name = "urgencia_idurgencia", referencedColumnName = "idurgencia")
    @ManyToOne(optional = false)
    private Urgencia urgenciaIdurgencia;
    @JoinColumn(name = "tipoDuracion_idtipoDuracion", referencedColumnName = "idtipoDuracion")
    @ManyToOne(optional = false)
    private Tipoduracion tipoDuracionidtipoDuracion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trabajo")
    private List<Historialtrabajo> historialtrabajoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "trabajo")
    private Trabajoxmaterial trabajoxmaterial;

    public Trabajo() {
    }

    public Trabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Trabajo(Integer idTrabajo, short activo) {
        this.idTrabajo = idTrabajo;
        this.activo = activo;
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getDuracionEstimada() {
        return duracionEstimada;
    }

    public void setDuracionEstimada(Integer duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }

    public Integer getDuracionReal() {
        return duracionReal;
    }

    public void setDuracionReal(Integer duracionReal) {
        this.duracionReal = duracionReal;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    public Unidad getUnidadidUnidad() {
        return unidadidUnidad;
    }

    public void setUnidadidUnidad(Unidad unidadidUnidad) {
        this.unidadidUnidad = unidadidUnidad;
    }

    public Estado getEstadoIdestado() {
        return estadoIdestado;
    }

    public void setEstadoIdestado(Estado estadoIdestado) {
        this.estadoIdestado = estadoIdestado;
    }

    public Grupo getGrupoIdgrupo() {
        return grupoIdgrupo;
    }

    public void setGrupoIdgrupo(Grupo grupoIdgrupo) {
        this.grupoIdgrupo = grupoIdgrupo;
    }

    public Urgencia getUrgenciaIdurgencia() {
        return urgenciaIdurgencia;
    }

    public void setUrgenciaIdurgencia(Urgencia urgenciaIdurgencia) {
        this.urgenciaIdurgencia = urgenciaIdurgencia;
    }

    public Tipoduracion getTipoDuracionidtipoDuracion() {
        return tipoDuracionidtipoDuracion;
    }

    public void setTipoDuracionidtipoDuracion(Tipoduracion tipoDuracionidtipoDuracion) {
        this.tipoDuracionidtipoDuracion = tipoDuracionidtipoDuracion;
    }

    @XmlTransient
    public List<Historialtrabajo> getHistorialtrabajoList() {
        return historialtrabajoList;
    }

    public void setHistorialtrabajoList(List<Historialtrabajo> historialtrabajoList) {
        this.historialtrabajoList = historialtrabajoList;
    }

    public Trabajoxmaterial getTrabajoxmaterial() {
        return trabajoxmaterial;
    }

    public void setTrabajoxmaterial(Trabajoxmaterial trabajoxmaterial) {
        this.trabajoxmaterial = trabajoxmaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrabajo != null ? idTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabajo)) {
            return false;
        }
        Trabajo other = (Trabajo) object;
        if ((this.idTrabajo == null && other.idTrabajo != null) || (this.idTrabajo != null && !this.idTrabajo.equals(other.idTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Trabajo[ idTrabajo=" + idTrabajo + " ]";
    }
    
}
