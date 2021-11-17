package nexp.com.app.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import nexp.com.app.security.model.Usuario;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "cliente_pasajero")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ClientePasajero.findAll", query = "SELECT c FROM ClientePasajero c"),
        @NamedQuery(name = "ClientePasajero.findByIdClientepasajero", query = "SELECT c FROM ClientePasajero c WHERE c.idClientepasajero = :idClientepasajero")})
public class ClientePasajero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_clientepasajero")
    private Integer idClientepasajero;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "id_pasajero", referencedColumnName = "id_pasajero")
    @ManyToOne(optional = false)
    private Pasajero idPasajero;

    public ClientePasajero() {
    }

    public ClientePasajero(Integer idClientepasajero) {
        this.idClientepasajero = idClientepasajero;
    }

    public Integer getIdClientepasajero() {
        return idClientepasajero;
    }

    public void setIdClientepasajero(Integer idClientepasajero) {
        this.idClientepasajero = idClientepasajero;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Pasajero getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(Pasajero idPasajero) {
        this.idPasajero = idPasajero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClientepasajero != null ? idClientepasajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientePasajero)) {
            return false;
        }
        ClientePasajero other = (ClientePasajero) object;
        if ((this.idClientepasajero == null && other.idClientepasajero != null) || (this.idClientepasajero != null && !this.idClientepasajero.equals(other.idClientepasajero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.test.ClientePasajero[ idClientepasajero=" + idClientepasajero + " ]";
    }

}