/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import nexp.com.app.security.model.Usuario;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "solicitud_tour")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudPaquete.findAll", query = "SELECT s FROM SolicitudTour s"),
    @NamedQuery(name = "SolicitudPaquete.findByIdSolicitud", query = "SELECT s FROM SolicitudTour s WHERE s.idSolicitud = :idSolicitud"),
    @NamedQuery(name = "SolicitudPaquete.findByFecha", query = "SELECT s FROM SolicitudTour s WHERE s.fecha = :fecha")})
public class SolicitudTour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "tour", referencedColumnName = "id_tour")
    @ManyToOne
    private Tour tour;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "solicitudTour")
    private Collection<Notificacion> notificacionCollection;

    public SolicitudTour() {
    }

    public SolicitudTour(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<Notificacion> notificacionCollection() {
        return notificacionCollection;
    }

    public void setNotificacionCollection(Collection<Notificacion> notificacionCollection) {
        this.notificacionCollection = notificacionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudTour)) {
            return false;
        }
        SolicitudTour other = (SolicitudTour) object;
        if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.SolicitudPaquete[ idSolicitud=" + idSolicitud + " ]";
    }
    
}
