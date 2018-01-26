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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "otrosgastos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Otrosgastos.findAll", query = "SELECT o FROM Otrosgastos o")
    , @NamedQuery(name = "Otrosgastos.findByIdotrosGastos", query = "SELECT o FROM Otrosgastos o WHERE o.otrosgastosPK.idotrosGastos = :idotrosGastos")
    , @NamedQuery(name = "Otrosgastos.findBySecuencia", query = "SELECT o FROM Otrosgastos o WHERE o.secuencia = :secuencia")
    , @NamedQuery(name = "Otrosgastos.findByDescripcion", query = "SELECT o FROM Otrosgastos o WHERE o.descripcion = :descripcion")
    , @NamedQuery(name = "Otrosgastos.findByMonto", query = "SELECT o FROM Otrosgastos o WHERE o.monto = :monto")
    , @NamedQuery(name = "Otrosgastos.findByFecha", query = "SELECT o FROM Otrosgastos o WHERE o.fecha = :fecha")
    , @NamedQuery(name = "Otrosgastos.findByPago", query = "SELECT o FROM Otrosgastos o WHERE o.pago = :pago")
    , @NamedQuery(name = "Otrosgastos.findByUnidadidUnidad", query = "SELECT o FROM Otrosgastos o WHERE o.otrosgastosPK.unidadidUnidad = :unidadidUnidad")
    , @NamedQuery(name = "Otrosgastos.findByActivo", query = "SELECT o FROM Otrosgastos o WHERE o.activo = :activo")})
public class Otrosgastos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OtrosgastosPK otrosgastosPK;
    @Column(name = "secuencia")
    private Integer secuencia;
    @Size(max = 80)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "pago")
    private Boolean pago;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "concepto_idconcepto", referencedColumnName = "idconcepto")
    @ManyToOne(optional = false)
    private Concepto conceptoIdconcepto;
    @JoinColumn(name = "monto_idmonto", referencedColumnName = "idmonto")
    @ManyToOne(optional = false)
    private Monto montoIdmonto;
    @JoinColumn(name = "unidad_idUnidad", referencedColumnName = "idUnidad", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Unidad unidad;

    public Otrosgastos() {
    }

    public Otrosgastos(OtrosgastosPK otrosgastosPK) {
        this.otrosgastosPK = otrosgastosPK;
    }

    public Otrosgastos(OtrosgastosPK otrosgastosPK, boolean activo) {
        this.otrosgastosPK = otrosgastosPK;
        this.activo = activo;
    }

    public Otrosgastos(int idotrosGastos, int unidadidUnidad) {
        this.otrosgastosPK = new OtrosgastosPK(idotrosGastos, unidadidUnidad);
    }

    public OtrosgastosPK getOtrosgastosPK() {
        return otrosgastosPK;
    }

    public void setOtrosgastosPK(OtrosgastosPK otrosgastosPK) {
        this.otrosgastosPK = otrosgastosPK;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Concepto getConceptoIdconcepto() {
        return conceptoIdconcepto;
    }

    public void setConceptoIdconcepto(Concepto conceptoIdconcepto) {
        this.conceptoIdconcepto = conceptoIdconcepto;
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
        hash += (otrosgastosPK != null ? otrosgastosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Otrosgastos)) {
            return false;
        }
        Otrosgastos other = (Otrosgastos) object;
        if ((this.otrosgastosPK == null && other.otrosgastosPK != null) || (this.otrosgastosPK != null && !this.otrosgastosPK.equals(other.otrosgastosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Otrosgastos[ otrosgastosPK=" + otrosgastosPK + " ]";
    }
    
}
