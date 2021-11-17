/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import nexp.com.app.security.model.Usuario;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "pasajero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasajero.findAll", query = "SELECT p FROM Pasajero p"),
    @NamedQuery(name = "Pasajero.findByIdPasajero", query = "SELECT p FROM Pasajero p WHERE p.idPasajero = :idPasajero"),
    @NamedQuery(name = "Pasajero.findByEsCotizante", query = "SELECT p FROM Pasajero p WHERE p.esCotizante = :esCotizante")})
public class Pasajero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pasajero")
    private Integer idPasajero;
    @Column(name = "es_cotizante")
    private Boolean esCotizante;
    @JoinColumn(name = "persona", referencedColumnName = "id_persona")
    @OneToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pasajero")
    private Collection<DetalleCompra> detalleCompraCollection;

    public Pasajero() {
    }

    public Pasajero(Integer idPasajero) {
        this.idPasajero = idPasajero;
    }

    public Integer getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(Integer idPasajero) {
        this.idPasajero = idPasajero;
    }

    public Boolean getEsCotizante() {
        return esCotizante;
    }

    public void setEsCotizante(Boolean esCotizante) {
        this.esCotizante = esCotizante;
    }

    public Collection<DetalleCompra> detalleCompraCollection() {
        return detalleCompraCollection;
    }

    public void setDetalleCompraCollection(Collection<DetalleCompra> detalleCompraCollection) {
        this.detalleCompraCollection = detalleCompraCollection;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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
        hash += (idPasajero != null ? idPasajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasajero)) {
            return false;
        }
        Pasajero other = (Pasajero) object;
        if ((this.idPasajero == null && other.idPasajero != null) || (this.idPasajero != null && !this.idPasajero.equals(other.idPasajero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Pasajero[ idPasajero=" + idPasajero + " ]";
    }
    
}
