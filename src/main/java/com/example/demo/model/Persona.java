/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona"),
    @NamedQuery(name = "Persona.findByPerNom", query = "SELECT p FROM Persona p WHERE p.perNom = :perNom"),
    @NamedQuery(name = "Persona.findBySdoNom", query = "SELECT p FROM Persona p WHERE p.sdoNom = :sdoNom"),
    @NamedQuery(name = "Persona.findByPerApell", query = "SELECT p FROM Persona p WHERE p.perApell = :perApell"),
    @NamedQuery(name = "Persona.findBySdoApell", query = "SELECT p FROM Persona p WHERE p.sdoApell = :sdoApell"),
    @NamedQuery(name = "Persona.findBySexo", query = "SELECT p FROM Persona p WHERE p.sexo = :sexo"),
    @NamedQuery(name = "Persona.findByFechaNac", query = "SELECT p FROM Persona p WHERE p.fechaNac = :fechaNac"),
    @NamedQuery(name = "Persona.findByCel", query = "SELECT p FROM Persona p WHERE p.cel = :cel"),
    @NamedQuery(name = "Persona.findByCorreo", query = "SELECT p FROM Persona p WHERE p.correo = :correo"),
    @NamedQuery(name = "Persona.findByDir", query = "SELECT p FROM Persona p WHERE p.dir = :dir")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_persona")
    private Integer idPersona;
    @Size(max = 25)
    @Column(name = "per_nom")
    private String perNom;
    @Size(max = 25)
    @Column(name = "sdo_nom")
    private String sdoNom;
    @Size(max = 25)
    @Column(name = "per_apell")
    private String perApell;
    @Size(max = 25)
    @Column(name = "sdo_apell")
    private String sdoApell;
    @Column(name = "sexo")
    private Short sexo;
    @Column(name = "fecha_nac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Size(max = 10)
    @Column(name = "cel")
    private String cel;
    @Size(max = 25)
    @Column(name = "correo")
    private String correo;
    @Size(max = 255)
    @Column(name = "dir")
    private String dir;
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    @ManyToOne
    private TipoIdentificacion idTipo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Pasajero pasajero;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Empleado empleado;

    public Persona() {
    }

    public Persona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getPerNom() {
        return perNom;
    }

    public void setPerNom(String perNom) {
        this.perNom = perNom;
    }

    public String getSdoNom() {
        return sdoNom;
    }

    public void setSdoNom(String sdoNom) {
        this.sdoNom = sdoNom;
    }

    public String getPerApell() {
        return perApell;
    }

    public void setPerApell(String perApell) {
        this.perApell = perApell;
    }

    public String getSdoApell() {
        return sdoApell;
    }

    public void setSdoApell(String sdoApell) {
        this.sdoApell = sdoApell;
    }

    public Short getSexo() {
        return sexo;
    }

    public void setSexo(Short sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public TipoIdentificacion getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(TipoIdentificacion idTipo) {
        this.idTipo = idTipo;
    }

    public Pasajero pasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Empleado empleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Persona[ idPersona=" + idPersona + " ]";
    }
    
}
