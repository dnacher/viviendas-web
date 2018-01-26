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
@Table(name = "monto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Monto.findAll", query = "SELECT m FROM Monto m")
    , @NamedQuery(name = "Monto.findByIdmonto", query = "SELECT m FROM Monto m WHERE m.idmonto = :idmonto")
    , @NamedQuery(name = "Monto.findByDescripcion", query = "SELECT m FROM Monto m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "Monto.findBySimbolo", query = "SELECT m FROM Monto m WHERE m.simbolo = :simbolo")
    , @NamedQuery(name = "Monto.findByTipoMonto", query = "SELECT m FROM Monto m WHERE m.tipoMonto = :tipoMonto")
    , @NamedQuery(name = "Monto.findByValorPesos", query = "SELECT m FROM Monto m WHERE m.valorPesos = :valorPesos")
    , @NamedQuery(name = "Monto.findByFechaActualizacion", query = "SELECT m FROM Monto m WHERE m.fechaActualizacion = :fechaActualizacion")
    , @NamedQuery(name = "Monto.findByActivo", query = "SELECT m FROM Monto m WHERE m.activo = :activo")})
public class Monto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmonto")
    private Integer idmonto;
    @Size(max = 45)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 45)
    @Column(name = "simbolo")
    private String simbolo;
    @Size(max = 45)
    @Column(name = "tipoMonto")
    private String tipoMonto;
    @Column(name = "valorPesos")
    private Integer valorPesos;
    @Column(name = "fechaActualizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "montoIdmonto")
    private List<Convenio> convenioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monto")
    private List<Cuotaconvenio> cuotaconvenioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "montoIdmonto")
    private List<Gastoscomunes> gastoscomunesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "monto")
    private List<Historialmonto> historialmontoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "montoIdmonto")
    private List<Otrosgastos> otrosgastosList;

    public Monto() {
    }

    public Monto(Integer idmonto) {
        this.idmonto = idmonto;
    }

    public Integer getIdmonto() {
        return idmonto;
    }

    public void setIdmonto(Integer idmonto) {
        this.idmonto = idmonto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getTipoMonto() {
        return tipoMonto;
    }

    public void setTipoMonto(String tipoMonto) {
        this.tipoMonto = tipoMonto;
    }

    public Integer getValorPesos() {
        return valorPesos;
    }

    public void setValorPesos(Integer valorPesos) {
        this.valorPesos = valorPesos;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    @XmlTransient
    public List<Cuotaconvenio> getCuotaconvenioList() {
        return cuotaconvenioList;
    }

    public void setCuotaconvenioList(List<Cuotaconvenio> cuotaconvenioList) {
        this.cuotaconvenioList = cuotaconvenioList;
    }

    @XmlTransient
    public List<Gastoscomunes> getGastoscomunesList() {
        return gastoscomunesList;
    }

    public void setGastoscomunesList(List<Gastoscomunes> gastoscomunesList) {
        this.gastoscomunesList = gastoscomunesList;
    }

    @XmlTransient
    public List<Historialmonto> getHistorialmontoList() {
        return historialmontoList;
    }

    public void setHistorialmontoList(List<Historialmonto> historialmontoList) {
        this.historialmontoList = historialmontoList;
    }

    @XmlTransient
    public List<Otrosgastos> getOtrosgastosList() {
        return otrosgastosList;
    }

    public void setOtrosgastosList(List<Otrosgastos> otrosgastosList) {
        this.otrosgastosList = otrosgastosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmonto != null ? idmonto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monto)) {
            return false;
        }
        Monto other = (Monto) object;
        if ((this.idmonto == null && other.idmonto != null) || (this.idmonto != null && !this.idmonto.equals(other.idmonto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Monto[ idmonto=" + idmonto + " ]";
    }
    
}
