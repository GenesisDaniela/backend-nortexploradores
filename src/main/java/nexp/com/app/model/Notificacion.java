/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import nexp.com.app.security.model.Usuario;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "notificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacion.findAll", query = "SELECT n FROM Notificacion n"),
    @NamedQuery(name = "Notificacion.findByIdNotificacion", query = "SELECT n FROM Notificacion n WHERE n.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "Notificacion.findByEstado", query = "SELECT n FROM Notificacion n WHERE n.estado = :estado"),
    @NamedQuery(name = "Notificacion.findByFecha", query = "SELECT n FROM Notificacion n WHERE n.fecha = :fecha")})
public class Notificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private short estado;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;
    @JoinColumn(name = "solicitudTour", referencedColumnName = "id_solicitud")
    @ManyToOne
    private SolicitudTour solicitudTour;
    @JoinColumn(name = "sugerencia", referencedColumnName = "id_sug")
    @ManyToOne
    private Sugerencia sugerencia;
    public Notificacion() {
    }

    public Notificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Notificacion(Integer idNotificacion, short estado) {
        this.idNotificacion = idNotificacion;
        this.estado = estado;
    }

    public SolicitudTour getSolicitudTour() {
        return solicitudTour;
    }

    public void setSolicitudTour(SolicitudTour solicitudTour) {
        this.solicitudTour = solicitudTour;
    }

    public Sugerencia getSugerencia() {
        return sugerencia;
    }

    public void setSugerencia(Sugerencia sugerencia) {
        this.sugerencia = sugerencia;
    }

    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificacion)) {
            return false;
        }
        Notificacion other = (Notificacion) object;
        if ((this.idNotificacion == null && other.idNotificacion != null) || (this.idNotificacion != null && !this.idNotificacion.equals(other.idNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Notificacion[ idNotificacion=" + idNotificacion + " ]";
    }
    
}
