/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "permisosusuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permisosusuario.findAll", query = "SELECT p FROM Permisosusuario p")
    , @NamedQuery(name = "Permisosusuario.findByPagina", query = "SELECT p FROM Permisosusuario p WHERE p.pagina = :pagina")
    , @NamedQuery(name = "Permisosusuario.findByPermiso", query = "SELECT p FROM Permisosusuario p WHERE p.permiso = :permiso")})
public class Permisosusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "pagina")
    private String pagina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "permiso")
    private int permiso;
    @JoinColumn(name = "tipousuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipousuario tipousuarioId;

    public Permisosusuario() {
    }

    public Permisosusuario(String pagina) {
        this.pagina = pagina;
    }

    public Permisosusuario(String pagina, int permiso) {
        this.pagina = pagina;
        this.permiso = permiso;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }

    public Tipousuario getTipousuarioId() {
        return tipousuarioId;
    }

    public void setTipousuarioId(Tipousuario tipousuarioId) {
        this.tipousuarioId = tipousuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagina != null ? pagina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permisosusuario)) {
            return false;
        }
        Permisosusuario other = (Permisosusuario) object;
        if ((this.pagina == null && other.pagina != null) || (this.pagina != null && !this.pagina.equals(other.pagina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Permisosusuario[ pagina=" + pagina + " ]";
    }
    
}
