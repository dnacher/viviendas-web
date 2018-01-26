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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author danielnacher
 */
@Entity
@Table(name = "historialtrabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historialtrabajo.findAll", query = "SELECT h FROM Historialtrabajo h")
    , @NamedQuery(name = "Historialtrabajo.findByIdHistorialTrabajo", query = "SELECT h FROM Historialtrabajo h WHERE h.historialtrabajoPK.idHistorialTrabajo = :idHistorialTrabajo")
    , @NamedQuery(name = "Historialtrabajo.findByDescripcion", query = "SELECT h FROM Historialtrabajo h WHERE h.descripcion = :descripcion")
    , @NamedQuery(name = "Historialtrabajo.findByFecha", query = "SELECT h FROM Historialtrabajo h WHERE h.fecha = :fecha")
    , @NamedQuery(name = "Historialtrabajo.findByTrabajoidTrabajo", query = "SELECT h FROM Historialtrabajo h WHERE h.historialtrabajoPK.trabajoidTrabajo = :trabajoidTrabajo")})
public class Historialtrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistorialtrabajoPK historialtrabajoPK;
    @Size(max = 150)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "Trabajo_idTrabajo", referencedColumnName = "idTrabajo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Trabajo trabajo;
    @JoinColumn(name = "tecnico_idTrabajador", referencedColumnName = "idTecnico")
    @ManyToOne(optional = false)
    private Tecnico tecnicoidTrabajador;

    public Historialtrabajo() {
    }

    public Historialtrabajo(HistorialtrabajoPK historialtrabajoPK) {
        this.historialtrabajoPK = historialtrabajoPK;
    }

    public Historialtrabajo(int idHistorialTrabajo, int trabajoidTrabajo) {
        this.historialtrabajoPK = new HistorialtrabajoPK(idHistorialTrabajo, trabajoidTrabajo);
    }

    public HistorialtrabajoPK getHistorialtrabajoPK() {
        return historialtrabajoPK;
    }

    public void setHistorialtrabajoPK(HistorialtrabajoPK historialtrabajoPK) {
        this.historialtrabajoPK = historialtrabajoPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Tecnico getTecnicoidTrabajador() {
        return tecnicoidTrabajador;
    }

    public void setTecnicoidTrabajador(Tecnico tecnicoidTrabajador) {
        this.tecnicoidTrabajador = tecnicoidTrabajador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historialtrabajoPK != null ? historialtrabajoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historialtrabajo)) {
            return false;
        }
        Historialtrabajo other = (Historialtrabajo) object;
        if ((this.historialtrabajoPK == null && other.historialtrabajoPK != null) || (this.historialtrabajoPK != null && !this.historialtrabajoPK.equals(other.historialtrabajoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Historialtrabajo[ historialtrabajoPK=" + historialtrabajoPK + " ]";
    }
    
}
