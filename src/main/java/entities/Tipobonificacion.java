/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "tipobonificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipobonificacion.findAll", query = "SELECT t FROM Tipobonificacion t")
    , @NamedQuery(name = "Tipobonificacion.findByIdtipoBonificacion", query = "SELECT t FROM Tipobonificacion t WHERE t.idtipoBonificacion = :idtipoBonificacion")
    , @NamedQuery(name = "Tipobonificacion.findByTipoBonificacion", query = "SELECT t FROM Tipobonificacion t WHERE t.tipoBonificacion = :tipoBonificacion")
    , @NamedQuery(name = "Tipobonificacion.findByValor", query = "SELECT t FROM Tipobonificacion t WHERE t.valor = :valor")
    , @NamedQuery(name = "Tipobonificacion.findByActivo", query = "SELECT t FROM Tipobonificacion t WHERE t.activo = :activo")})
public class Tipobonificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoBonificacion")
    private Integer idtipoBonificacion;
    @Column(name = "tipoBonificacion")
    private Integer tipoBonificacion;
    @Column(name = "valor")
    private Integer valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(mappedBy = "tipoBonificacion")
    private List<Convenio> convenioList;

    public Tipobonificacion() {
    }

    public Tipobonificacion(Integer idtipoBonificacion) {
        this.idtipoBonificacion = idtipoBonificacion;
    }

    public Tipobonificacion(Integer idtipoBonificacion, boolean activo) {
        this.idtipoBonificacion = idtipoBonificacion;
        this.activo = activo;
    }

    public Integer getIdtipoBonificacion() {
        return idtipoBonificacion;
    }

    public void setIdtipoBonificacion(Integer idtipoBonificacion) {
        this.idtipoBonificacion = idtipoBonificacion;
    }

    public Integer getTipoBonificacion() {
        return tipoBonificacion;
    }

    public void setTipoBonificacion(Integer tipoBonificacion) {
        this.tipoBonificacion = tipoBonificacion;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoBonificacion != null ? idtipoBonificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipobonificacion)) {
            return false;
        }
        Tipobonificacion other = (Tipobonificacion) object;
        if ((this.idtipoBonificacion == null && other.idtipoBonificacion != null) || (this.idtipoBonificacion != null && !this.idtipoBonificacion.equals(other.idtipoBonificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Tipobonificacion[ idtipoBonificacion=" + idtipoBonificacion + " ]";
    }
    
}
