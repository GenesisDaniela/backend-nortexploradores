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
@Table(name = "sugerencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sugerencia.findAll", query = "SELECT s FROM Sugerencia s"),
    @NamedQuery(name = "Sugerencia.findByIdSug", query = "SELECT s FROM Sugerencia s WHERE s.idSug = :idSug"),
    @NamedQuery(name = "Sugerencia.findByDescripcion", query = "SELECT s FROM Sugerencia s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sugerencia.findByFecha", query = "SELECT s FROM Sugerencia s WHERE s.fecha = :fecha")})
public class Sugerencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sug")
    private Integer idSug;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "sugerencia")
    private Collection<Notificacion> notificacionCollection;

    public Sugerencia() {
    }

    public Sugerencia(Integer idSug) {
        this.idSug = idSug;
    }

    public Integer getIdSug() {
        return idSug;
    }

    public void setIdSug(Integer idSug) {
        this.idSug = idSug;
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
        hash += (idSug != null ? idSug.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sugerencia)) {
            return false;
        }
        Sugerencia other = (Sugerencia) object;
        if ((this.idSug == null && other.idSug != null) || (this.idSug != null && !this.idSug.equals(other.idSug))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Sugerencia[ idSug=" + idSug + " ]";
    }
    
}
