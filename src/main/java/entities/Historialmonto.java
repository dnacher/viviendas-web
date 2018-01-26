/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "historialmonto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historialmonto.findAll", query = "SELECT h FROM Historialmonto h")
    , @NamedQuery(name = "Historialmonto.findByIdhistorialMonto", query = "SELECT h FROM Historialmonto h WHERE h.historialmontoPK.idhistorialMonto = :idhistorialMonto")
    , @NamedQuery(name = "Historialmonto.findByFechaActualizacion", query = "SELECT h FROM Historialmonto h WHERE h.fechaActualizacion = :fechaActualizacion")
    , @NamedQuery(name = "Historialmonto.findByValorPesos", query = "SELECT h FROM Historialmonto h WHERE h.valorPesos = :valorPesos")
    , @NamedQuery(name = "Historialmonto.findByMontoIdmonto", query = "SELECT h FROM Historialmonto h WHERE h.historialmontoPK.montoIdmonto = :montoIdmonto")
    , @NamedQuery(name = "Historialmonto.findByActivo", query = "SELECT h FROM Historialmonto h WHERE h.activo = :activo")})
public class Historialmonto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistorialmontoPK historialmontoPK;
    @Column(name = "fechaActualizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;
    @Column(name = "valorPesos")
    private Integer valorPesos;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "monto_idmonto", referencedColumnName = "idmonto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Monto monto;

    public Historialmonto() {
    }

    public Historialmonto(HistorialmontoPK historialmontoPK) {
        this.historialmontoPK = historialmontoPK;
    }

    public Historialmonto(int idhistorialMonto, int montoIdmonto) {
        this.historialmontoPK = new HistorialmontoPK(idhistorialMonto, montoIdmonto);
    }

    public HistorialmontoPK getHistorialmontoPK() {
        return historialmontoPK;
    }

    public void setHistorialmontoPK(HistorialmontoPK historialmontoPK) {
        this.historialmontoPK = historialmontoPK;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getValorPesos() {
        return valorPesos;
    }

    public void setValorPesos(Integer valorPesos) {
        this.valorPesos = valorPesos;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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
        hash += (historialmontoPK != null ? historialmontoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historialmonto)) {
            return false;
        }
        Historialmonto other = (Historialmonto) object;
        if ((this.historialmontoPK == null && other.historialmontoPK != null) || (this.historialmontoPK != null && !this.historialmontoPK.equals(other.historialmontoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Historialmonto[ historialmontoPK=" + historialmontoPK + " ]";
    }
    
}
