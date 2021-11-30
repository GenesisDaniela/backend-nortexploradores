/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import nexp.com.app.security.model.Usuario;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "solicitud_tour")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "SolicitudTour.findAll", query = "SELECT s FROM SolicitudTour s"),
        @NamedQuery(name = "SolicitudTour.findByIdSolicitud", query = "SELECT s FROM SolicitudTour s WHERE s.idSolicitud = :idSolicitud"),
        @NamedQuery(name = "SolicitudTour.findByFecha", query = "SELECT s FROM SolicitudTour s WHERE s.fecha = :fecha")})
public class SolicitudTour implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "alojamiento", referencedColumnName = "id_alojamiento")
    @ManyToOne
    private Alojamiento alojamiento;
    @JoinColumn(name = "municipio", referencedColumnName = "id_muni")
    @ManyToOne(optional = false)
    private Municipio municipio;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "tour", referencedColumnName = "id_tour")
    @ManyToOne(optional = false)
    private Tour tour;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(mappedBy = "solicitudTour")
    private Collection<Notificacion> notificacionCollection;

    public SolicitudTour() {
    }

    public SolicitudTour(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public SolicitudTour(Integer idSolicitud, Date fecha) {
        this.idSolicitud = idSolicitud;
        this.fecha = fecha;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }


    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEstado() { return estado;}

    public void setEstado(String estado) { this.estado = estado; }

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
        return "com.example.demo.modelo.SolicitudTour[ idSolicitud=" + idSolicitud + " ]";
    }


    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}