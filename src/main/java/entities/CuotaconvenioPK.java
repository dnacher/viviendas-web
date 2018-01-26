/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author danielnacher
 */
@Embeddable
public class CuotaconvenioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idcuotaConvenio")
    private int idcuotaConvenio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "convenio_idconvenio")
    private int convenioIdconvenio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "convenio_unidad_idUnidad")
    private int conveniounidadidUnidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_idmonto")
    private int montoIdmonto;

    public CuotaconvenioPK() {
    }

    public CuotaconvenioPK(int idcuotaConvenio, int convenioIdconvenio, int conveniounidadidUnidad, int montoIdmonto) {
        this.idcuotaConvenio = idcuotaConvenio;
        this.convenioIdconvenio = convenioIdconvenio;
        this.conveniounidadidUnidad = conveniounidadidUnidad;
        this.montoIdmonto = montoIdmonto;
    }

    public int getIdcuotaConvenio() {
        return idcuotaConvenio;
    }

    public void setIdcuotaConvenio(int idcuotaConvenio) {
        this.idcuotaConvenio = idcuotaConvenio;
    }

    public int getConvenioIdconvenio() {
        return convenioIdconvenio;
    }

    public void setConvenioIdconvenio(int convenioIdconvenio) {
        this.convenioIdconvenio = convenioIdconvenio;
    }

    public int getConveniounidadidUnidad() {
        return conveniounidadidUnidad;
    }

    public void setConveniounidadidUnidad(int conveniounidadidUnidad) {
        this.conveniounidadidUnidad = conveniounidadidUnidad;
    }

    public int getMontoIdmonto() {
        return montoIdmonto;
    }

    public void setMontoIdmonto(int montoIdmonto) {
        this.montoIdmonto = montoIdmonto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcuotaConvenio;
        hash += (int) convenioIdconvenio;
        hash += (int) conveniounidadidUnidad;
        hash += (int) montoIdmonto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuotaconvenioPK)) {
            return false;
        }
        CuotaconvenioPK other = (CuotaconvenioPK) object;
        if (this.idcuotaConvenio != other.idcuotaConvenio) {
            return false;
        }
        if (this.convenioIdconvenio != other.convenioIdconvenio) {
            return false;
        }
        if (this.conveniounidadidUnidad != other.conveniounidadidUnidad) {
            return false;
        }
        if (this.montoIdmonto != other.montoIdmonto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.CuotaconvenioPK[ idcuotaConvenio=" + idcuotaConvenio + ", convenioIdconvenio=" + convenioIdconvenio + ", conveniounidadidUnidad=" + conveniounidadidUnidad + ", montoIdmonto=" + montoIdmonto + " ]";
    }
    
}
