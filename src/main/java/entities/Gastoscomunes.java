/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "gastoscomunes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gastoscomunes.findAll", query = "SELECT g FROM Gastoscomunes g")
    , @NamedQuery(name = "Gastoscomunes.findByIdGastosComunes", query = "SELECT g FROM Gastoscomunes g WHERE g.gastoscomunesPK.idGastosComunes = :idGastosComunes")
    , @NamedQuery(name = "Gastoscomunes.findByUnidadidUnidad", query = "SELECT g FROM Gastoscomunes g WHERE g.gastoscomunesPK.unidadidUnidad = :unidadidUnidad")
    , @NamedQuery(name = "Gastoscomunes.findByMonto", query = "SELECT g FROM Gastoscomunes g WHERE g.monto = :monto")
    , @NamedQuery(name = "Gastoscomunes.findByIsBonificacion", query = "SELECT g FROM Gastoscomunes g WHERE g.isBonificacion = :isBonificacion")
    , @NamedQuery(name = "Gastoscomunes.findByEstado", query = "SELECT g FROM Gastoscomunes g WHERE g.estado = :estado")
    , @NamedQuery(name = "Gastoscomunes.findByFechaPago", query = "SELECT g FROM Gastoscomunes g WHERE g.fechaPago = :fechaPago")
    , @NamedQuery(name = "Gastoscomunes.findByPeriodo", query = "SELECT g FROM Gastoscomunes g WHERE g.periodo = :periodo")
    , @NamedQuery(name = "Gastoscomunes.findByActivo", query = "SELECT g FROM Gastoscomunes g WHERE g.activo = :activo")})
public class Gastoscomunes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GastoscomunesPK gastoscomunesPK;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "isBonificacion")
    private Boolean isBonificacion;
    @Column(name = "estado")
    private Integer estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "periodo")
    private Integer periodo;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "monto_idmonto", referencedColumnName = "idmonto")
    @ManyToOne(optional = false)
    private Monto montoIdmonto;
    @JoinColumn(name = "unidad_idUnidad", referencedColumnName = "idUnidad", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Unidad unidad;

    public Gastoscomunes() {
    }

    public Gastoscomunes(GastoscomunesPK gastoscomunesPK) {
        this.gastoscomunesPK = gastoscomunesPK;
    }

    public Gastoscomunes(GastoscomunesPK gastoscomunesPK, Date fechaPago) {
        this.gastoscomunesPK = gastoscomunesPK;
        this.fechaPago = fechaPago;
    }

    public Gastoscomunes(int idGastosComunes, int unidadidUnidad) {
        this.gastoscomunesPK = new GastoscomunesPK(idGastosComunes, unidadidUnidad);
    }

    public GastoscomunesPK getGastoscomunesPK() {
        return gastoscomunesPK;
    }

    public void setGastoscomunesPK(GastoscomunesPK gastoscomunesPK) {
        this.gastoscomunesPK = gastoscomunesPK;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Boolean getIsBonificacion() {
        return isBonificacion;
    }

    public void setIsBonificacion(Boolean isBonificacion) {
        this.isBonificacion = isBonificacion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Monto getMontoIdmonto() {
        return montoIdmonto;
    }

    public void setMontoIdmonto(Monto montoIdmonto) {
        this.montoIdmonto = montoIdmonto;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gastoscomunesPK != null ? gastoscomunesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gastoscomunes)) {
            return false;
        }
        Gastoscomunes other = (Gastoscomunes) object;
        if ((this.gastoscomunesPK == null && other.gastoscomunesPK != null) || (this.gastoscomunesPK != null && !this.gastoscomunesPK.equals(other.gastoscomunesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Gastoscomunes[ gastoscomunesPK=" + gastoscomunesPK + " ]";
    }
    
}
