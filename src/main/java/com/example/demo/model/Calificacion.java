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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "calificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calificacion.findAll", query = "SELECT c FROM Calificacion c"),
    @NamedQuery(name = "Calificacion.findByIdCalificacion", query = "SELECT c FROM Calificacion c WHERE c.idCalificacion = :idCalificacion"),
    @NamedQuery(name = "Calificacion.findByPuntuacion", query = "SELECT c FROM Calificacion c WHERE c.puntuacion = :puntuacion"),
    @NamedQuery(name = "Calificacion.findByComentario", query = "SELECT c FROM Calificacion c WHERE c.comentario = :comentario")})
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_calificacion")
    private Integer idCalificacion;
    @Column(name = "puntuacion")
    private Integer puntuacion;
    @Size(max = 255)
    @Column(name = "comentario")
    private String comentario;
    @JoinColumn(name = "paquete", referencedColumnName = "id_paq")
    @ManyToOne
    private Paquete paquete;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;

    public Calificacion() {
    }

    public Calificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Integer getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Integer idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
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
        hash += (idCalificacion != null ? idCalificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.idCalificacion == null && other.idCalificacion != null) || (this.idCalificacion != null && !this.idCalificacion.equals(other.idCalificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Calificacion[ idCalificacion=" + idCalificacion + " ]";
    }
    
}
