/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "unidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidad.findAll", query = "SELECT u FROM Unidad u")
    , @NamedQuery(name = "Unidad.findByIdUnidad", query = "SELECT u FROM Unidad u WHERE u.idUnidad = :idUnidad")
    , @NamedQuery(name = "Unidad.findByBlock", query = "SELECT u FROM Unidad u WHERE u.block = :block")
    , @NamedQuery(name = "Unidad.findByTorre", query = "SELECT u FROM Unidad u WHERE u.torre = :torre")
    , @NamedQuery(name = "Unidad.findByPuerta", query = "SELECT u FROM Unidad u WHERE u.puerta = :puerta")
    , @NamedQuery(name = "Unidad.findByNombre", query = "SELECT u FROM Unidad u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Unidad.findByApellido", query = "SELECT u FROM Unidad u WHERE u.apellido = :apellido")
    , @NamedQuery(name = "Unidad.findByTelefono", query = "SELECT u FROM Unidad u WHERE u.telefono = :telefono")
    , @NamedQuery(name = "Unidad.findByMail", query = "SELECT u FROM Unidad u WHERE u.mail = :mail")
    , @NamedQuery(name = "Unidad.findByPropietarioInquilino", query = "SELECT u FROM Unidad u WHERE u.propietarioInquilino = :propietarioInquilino")
    , @NamedQuery(name = "Unidad.findByFechaIngreso", query = "SELECT u FROM Unidad u WHERE u.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "Unidad.findByActivo", query = "SELECT u FROM Unidad u WHERE u.activo = :activo")})
public class Unidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUnidad")
    private Integer idUnidad;
    @Size(max = 2)
    @Column(name = "block")
    private String block;
    @Column(name = "torre")
    private Integer torre;
    @Column(name = "puerta")
    private Integer puerta;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono")
    private Integer telefono;
    @Size(max = 45)
    @Column(name = "mail")
    private String mail;
    @Column(name = "propietarioInquilino")
    private Boolean propietarioInquilino;
    @Column(name = "fechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidadidUnidad")
    private List<Trabajo> trabajoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidad")
    private List<Convenio> convenioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidad")
    private List<Gastoscomunes> gastoscomunesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidad")
    private List<Otrosgastos> otrosgastosList;

    public Unidad() {
    }

    public Unidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Integer getTorre() {
        return torre;
    }

    public void setTorre(Integer torre) {
        this.torre = torre;
    }

    public Integer getPuerta() {
        return puerta;
    }

    public void setPuerta(Integer puerta) {
        this.puerta = puerta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean getPropietarioInquilino() {
        return propietarioInquilino;
    }

    public void setPropietarioInquilino(Boolean propietarioInquilino) {
        this.propietarioInquilino = propietarioInquilino;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Trabajo> getTrabajoList() {
        return trabajoList;
    }

    public void setTrabajoList(List<Trabajo> trabajoList) {
        this.trabajoList = trabajoList;
    }

    @XmlTransient
    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    @XmlTransient
    public List<Gastoscomunes> getGastoscomunesList() {
        return gastoscomunesList;
    }

    public void setGastoscomunesList(List<Gastoscomunes> gastoscomunesList) {
        this.gastoscomunesList = gastoscomunesList;
    }

    @XmlTransient
    public List<Otrosgastos> getOtrosgastosList() {
        return otrosgastosList;
    }

    public void setOtrosgastosList(List<Otrosgastos> otrosgastosList) {
        this.otrosgastosList = otrosgastosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUnidad != null ? idUnidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unidad)) {
            return false;
        }
        Unidad other = (Unidad) object;
        if ((this.idUnidad == null && other.idUnidad != null) || (this.idUnidad != null && !this.idUnidad.equals(other.idUnidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Unidad[ idUnidad=" + idUnidad + " ]";
    }
    
}
