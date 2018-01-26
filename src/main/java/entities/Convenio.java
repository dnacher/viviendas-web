/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "convenio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Convenio.findAll", query = "SELECT c FROM Convenio c")
    , @NamedQuery(name = "Convenio.findByIdconvenio", query = "SELECT c FROM Convenio c WHERE c.convenioPK.idconvenio = :idconvenio")
    , @NamedQuery(name = "Convenio.findByDeudaTotal", query = "SELECT c FROM Convenio c WHERE c.deudaTotal = :deudaTotal")
    , @NamedQuery(name = "Convenio.findByCuotas", query = "SELECT c FROM Convenio c WHERE c.cuotas = :cuotas")
    , @NamedQuery(name = "Convenio.findBySaldoInicial", query = "SELECT c FROM Convenio c WHERE c.saldoInicial = :saldoInicial")
    , @NamedQuery(name = "Convenio.findByDescripcion", query = "SELECT c FROM Convenio c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Convenio.findByActivo", query = "SELECT c FROM Convenio c WHERE c.activo = :activo")
    , @NamedQuery(name = "Convenio.findByUnidadidUnidad", query = "SELECT c FROM Convenio c WHERE c.convenioPK.unidadidUnidad = :unidadidUnidad")})
public class Convenio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConvenioPK convenioPK;
    @Column(name = "deudaTotal")
    private Integer deudaTotal;
    @Column(name = "cuotas")
    private Integer cuotas;
    @Column(name = "saldoInicial")
    private Integer saldoInicial;
    @Size(max = 80)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "monto_idmonto", referencedColumnName = "idmonto")
    @ManyToOne(optional = false)
    private Monto montoIdmonto;
    @JoinColumn(name = "reglaBonificacion_idreglaBonificacion", referencedColumnName = "idreglaBonificacion")
    @ManyToOne(optional = false)
    private Reglabonificacion reglaBonificacionidreglaBonificacion;
    @JoinColumn(name = "unidad_idUnidad", referencedColumnName = "idUnidad", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Unidad unidad;
    @JoinColumn(name = "tipoBonificacion", referencedColumnName = "idtipoBonificacion")
    @ManyToOne
    private Tipobonificacion tipoBonificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convenio")
    private List<Cuotaconvenio> cuotaconvenioList;

    public Convenio() {
    }

    public Convenio(ConvenioPK convenioPK) {
        this.convenioPK = convenioPK;
    }

    public Convenio(int idconvenio, int unidadidUnidad) {
        this.convenioPK = new ConvenioPK(idconvenio, unidadidUnidad);
    }

    public ConvenioPK getConvenioPK() {
        return convenioPK;
    }

    public void setConvenioPK(ConvenioPK convenioPK) {
        this.convenioPK = convenioPK;
    }

    public Integer getDeudaTotal() {
        return deudaTotal;
    }

    public void setDeudaTotal(Integer deudaTotal) {
        this.deudaTotal = deudaTotal;
    }

    public Integer getCuotas() {
        return cuotas;
    }

    public void setCuotas(Integer cuotas) {
        this.cuotas = cuotas;
    }

    public Integer getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Integer saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Reglabonificacion getReglaBonificacionidreglaBonificacion() {
        return reglaBonificacionidreglaBonificacion;
    }

    public void setReglaBonificacionidreglaBonificacion(Reglabonificacion reglaBonificacionidreglaBonificacion) {
        this.reglaBonificacionidreglaBonificacion = reglaBonificacionidreglaBonificacion;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Tipobonificacion getTipoBonificacion() {
        return tipoBonificacion;
    }

    public void setTipoBonificacion(Tipobonificacion tipoBonificacion) {
        this.tipoBonificacion = tipoBonificacion;
    }

    @XmlTransient
    public List<Cuotaconvenio> getCuotaconvenioList() {
        return cuotaconvenioList;
    }

    public void setCuotaconvenioList(List<Cuotaconvenio> cuotaconvenioList) {
        this.cuotaconvenioList = cuotaconvenioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (convenioPK != null ? convenioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Convenio)) {
            return false;
        }
        Convenio other = (Convenio) object;
        if ((this.convenioPK == null && other.convenioPK != null) || (this.convenioPK != null && !this.convenioPK.equals(other.convenioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Convenio[ convenioPK=" + convenioPK + " ]";
    }
    
}
