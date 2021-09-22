/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.example.demo.security.entity.Usuario;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pasajero")
    private Integer idPasajero;
    @Column(name = "es_cotizante")
    private Boolean esCotizante;
    @JoinColumn(name = "id_pasajero", referencedColumnName = "id_persona", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;

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
        return "com.example.demo.model.Pasajero[ idPasajero=" + idPasajero + " ]";
    }
    
}
