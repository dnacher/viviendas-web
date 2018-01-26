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
public class HistorialmontoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idhistorialMonto")
    private int idhistorialMonto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_idmonto")
    private int montoIdmonto;

    public HistorialmontoPK() {
    }

    public HistorialmontoPK(int idhistorialMonto, int montoIdmonto) {
        this.idhistorialMonto = idhistorialMonto;
        this.montoIdmonto = montoIdmonto;
    }

    public int getIdhistorialMonto() {
        return idhistorialMonto;
    }

    public void setIdhistorialMonto(int idhistorialMonto) {
        this.idhistorialMonto = idhistorialMonto;
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
        hash += (int) idhistorialMonto;
        hash += (int) montoIdmonto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialmontoPK)) {
            return false;
        }
        HistorialmontoPK other = (HistorialmontoPK) object;
        if (this.idhistorialMonto != other.idhistorialMonto) {
            return false;
        }
        if (this.montoIdmonto != other.montoIdmonto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.HistorialmontoPK[ idhistorialMonto=" + idhistorialMonto + ", montoIdmonto=" + montoIdmonto + " ]";
    }
    
}
