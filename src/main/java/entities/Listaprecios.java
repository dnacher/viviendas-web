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
@Table(name = "listaprecios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listaprecios.findAll", query = "SELECT l FROM Listaprecios l")
    , @NamedQuery(name = "Listaprecios.findByIdlistaPrecios", query = "SELECT l FROM Listaprecios l WHERE l.listapreciosPK.idlistaPrecios = :idlistaPrecios")
    , @NamedQuery(name = "Listaprecios.findByPrecio", query = "SELECT l FROM Listaprecios l WHERE l.precio = :precio")
    , @NamedQuery(name = "Listaprecios.findByCantidad", query = "SELECT l FROM Listaprecios l WHERE l.cantidad = :cantidad")
    , @NamedQuery(name = "Listaprecios.findByFecha", query = "SELECT l FROM Listaprecios l WHERE l.fecha = :fecha")
    , @NamedQuery(name = "Listaprecios.findByMaterialIdmaterial", query = "SELECT l FROM Listaprecios l WHERE l.listapreciosPK.materialIdmaterial = :materialIdmaterial")
    , @NamedQuery(name = "Listaprecios.findByActivo", query = "SELECT l FROM Listaprecios l WHERE l.activo = :activo")})
public class Listaprecios implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListapreciosPK listapreciosPK;
    @Column(name = "precio")
    private Integer precio;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "material_idmaterial", referencedColumnName = "idmaterial", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Material material;

    public Listaprecios() {
    }

    public Listaprecios(ListapreciosPK listapreciosPK) {
        this.listapreciosPK = listapreciosPK;
    }

    public Listaprecios(int idlistaPrecios, int materialIdmaterial) {
        this.listapreciosPK = new ListapreciosPK(idlistaPrecios, materialIdmaterial);
    }

    public ListapreciosPK getListapreciosPK() {
        return listapreciosPK;
    }

    public void setListapreciosPK(ListapreciosPK listapreciosPK) {
        this.listapreciosPK = listapreciosPK;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listapreciosPK != null ? listapreciosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listaprecios)) {
            return false;
        }
        Listaprecios other = (Listaprecios) object;
        if ((this.listapreciosPK == null && other.listapreciosPK != null) || (this.listapreciosPK != null && !this.listapreciosPK.equals(other.listapreciosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Listaprecios[ listapreciosPK=" + listapreciosPK + " ]";
    }
    
}
