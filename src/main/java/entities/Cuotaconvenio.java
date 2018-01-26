/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "cuotaconvenio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuotaconvenio.findAll", query = "SELECT c FROM Cuotaconvenio c")
    , @NamedQuery(name = "Cuotaconvenio.findByIdcuotaConvenio", query = "SELECT c FROM Cuotaconvenio c WHERE c.cuotaconvenioPK.idcuotaConvenio = :idcuotaConvenio")
    , @NamedQuery(name = "Cuotaconvenio.findByNumeroCuota", query = "SELECT c FROM Cuotaconvenio c WHERE c.numeroCuota = :numeroCuota")
    , @NamedQuery(name = "Cuotaconvenio.findByDescripcion", query = "SELECT c FROM Cuotaconvenio c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "Cuotaconvenio.findByPago", query = "SELECT c FROM Cuotaconvenio c WHERE c.pago = :pago")
    , @NamedQuery(name = "Cuotaconvenio.findByConvenioIdconvenio", query = "SELECT c FROM Cuotaconvenio c WHERE c.cuotaconvenioPK.convenioIdconvenio = :convenioIdconvenio")
    , @NamedQuery(name = "Cuotaconvenio.findByConveniounidadidUnidad", query = "SELECT c FROM Cuotaconvenio c WHERE c.cuotaconvenioPK.conveniounidadidUnidad = :conveniounidadidUnidad")
    , @NamedQuery(name = "Cuotaconvenio.findByMontoIdmonto", query = "SELECT c FROM Cuotaconvenio c WHERE c.cuotaconvenioPK.montoIdmonto = :montoIdmonto")
    , @NamedQuery(name = "Cuotaconvenio.findByTieneBonificacion", query = "SELECT c FROM Cuotaconvenio c WHERE c.tieneBonificacion = :tieneBonificacion")})
public class Cuotaconvenio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CuotaconvenioPK cuotaconvenioPK;
    @Column(name = "numeroCuota")
    private Integer numeroCuota;
    @Size(max = 80)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "pago")
    private Boolean pago;
    @Column(name = "tieneBonificacion")
    private Boolean tieneBonificacion;
    @JoinColumns({
        @JoinColumn(name = "convenio_idconvenio", referencedColumnName = "idconvenio", insertable = false, updatable = false)
        , @JoinColumn(name = "convenio_unidad_idUnidad", referencedColumnName = "unidad_idUnidad", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Convenio convenio;
    @JoinColumn(name = "monto_idmonto", referencedColumnName = "idmonto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Monto monto;

    public Cuotaconvenio() {
    }

    public Cuotaconvenio(CuotaconvenioPK cuotaconvenioPK) {
        this.cuotaconvenioPK = cuotaconvenioPK;
    }

    public Cuotaconvenio(int idcuotaConvenio, int convenioIdconvenio, int conveniounidadidUnidad, int montoIdmonto) {
        this.cuotaconvenioPK = new CuotaconvenioPK(idcuotaConvenio, convenioIdconvenio, conveniounidadidUnidad, montoIdmonto);
    }

    public CuotaconvenioPK getCuotaconvenioPK() {
        return cuotaconvenioPK;
    }

    public void setCuotaconvenioPK(CuotaconvenioPK cuotaconvenioPK) {
        this.cuotaconvenioPK = cuotaconvenioPK;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getPago() {
        return pago;
    }

    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    public Boolean getTieneBonificacion() {
        return tieneBonificacion;
    }

    public void setTieneBonificacion(Boolean tieneBonificacion) {
        this.tieneBonificacion = tieneBonificacion;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public Monto getMonto() {
        return monto;
    }

    public void setMonto(Monto monto) {
        this.monto = monto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuotaconvenioPK != null ? cuotaconvenioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuotaconvenio)) {
            return false;
        }
        Cuotaconvenio other = (Cuotaconvenio) object;
        if ((this.cuotaconvenioPK == null && other.cuotaconvenioPK != null) || (this.cuotaconvenioPK != null && !this.cuotaconvenioPK.equals(other.cuotaconvenioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Cuotaconvenio[ cuotaconvenioPK=" + cuotaconvenioPK + " ]";
    }
    
}
