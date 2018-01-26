/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m")
    , @NamedQuery(name = "Material.findByIdmaterial", query = "SELECT m FROM Material m WHERE m.idmaterial = :idmaterial")
    , @NamedQuery(name = "Material.findByNombre", query = "SELECT m FROM Material m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Material.findByDescripcion", query = "SELECT m FROM Material m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "Material.findByCantidad", query = "SELECT m FROM Material m WHERE m.cantidad = :cantidad")
    , @NamedQuery(name = "Material.findByEntrada", query = "SELECT m FROM Material m WHERE m.entrada = :entrada")
    , @NamedQuery(name = "Material.findBySalida", query = "SELECT m FROM Material m WHERE m.salida = :salida")
    , @NamedQuery(name = "Material.findByActivo", query = "SELECT m FROM Material m WHERE m.activo = :activo")})
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmaterial")
    private Integer idmaterial;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "entrada")
    private Integer entrada;
    @Column(name = "salida")
    private Integer salida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialIdmaterial")
    private List<Trabajoxmaterial> trabajoxmaterialList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private List<Listaprecios> listapreciosList;

    public Material() {
    }

    public Material(Integer idmaterial) {
        this.idmaterial = idmaterial;
    }

    public Material(Integer idmaterial, boolean activo) {
        this.idmaterial = idmaterial;
        this.activo = activo;
    }

    public Integer getIdmaterial() {
        return idmaterial;
    }

    public void setIdmaterial(Integer idmaterial) {
        this.idmaterial = idmaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getEntrada() {
        return entrada;
    }

    public void setEntrada(Integer entrada) {
        this.entrada = entrada;
    }

    public Integer getSalida() {
        return salida;
    }

    public void setSalida(Integer salida) {
        this.salida = salida;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Trabajoxmaterial> getTrabajoxmaterialList() {
        return trabajoxmaterialList;
    }

    public void setTrabajoxmaterialList(List<Trabajoxmaterial> trabajoxmaterialList) {
        this.trabajoxmaterialList = trabajoxmaterialList;
    }

    @XmlTransient
    public List<Listaprecios> getListapreciosList() {
        return listapreciosList;
    }

    public void setListapreciosList(List<Listaprecios> listapreciosList) {
        this.listapreciosList = listapreciosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmaterial != null ? idmaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.idmaterial == null && other.idmaterial != null) || (this.idmaterial != null && !this.idmaterial.equals(other.idmaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Material[ idmaterial=" + idmaterial + " ]";
    }
    
}
