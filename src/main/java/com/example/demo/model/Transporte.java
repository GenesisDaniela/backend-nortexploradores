/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "transporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transporte.findAll", query = "SELECT t FROM Transporte t"),
    @NamedQuery(name = "Transporte.findByIdTransporte", query = "SELECT t FROM Transporte t WHERE t.idTransporte = :idTransporte"),
    @NamedQuery(name = "Transporte.findByPuestos", query = "SELECT t FROM Transporte t WHERE t.puestos = :puestos"),
    @NamedQuery(name = "Transporte.findByModelo", query = "SELECT t FROM Transporte t WHERE t.modelo = :modelo"),
    @NamedQuery(name = "Transporte.findByColor", query = "SELECT t FROM Transporte t WHERE t.color = :color")})
public class Transporte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "id_transporte")
    private String idTransporte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puestos")
    private short puestos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "modelo")
    private String modelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "color")
    private String color;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transporte")
    private Collection<Ruta> rutaCollection;
    @JoinColumn(name = "empresa", referencedColumnName = "id_empresa")
    @ManyToOne
    private Empresa empresa;

    public Transporte() {
    }

    public Transporte(String idTransporte) {
        this.idTransporte = idTransporte;
    }

    public Transporte(String idTransporte, short puestos, String modelo, String color) {
        this.idTransporte = idTransporte;
        this.puestos = puestos;
        this.modelo = modelo;
        this.color = color;
    }

    public String getIdTransporte() {
        return idTransporte;
    }

    public void setIdTransporte(String idTransporte) {
        this.idTransporte = idTransporte;
    }

    public short getPuestos() {
        return puestos;
    }

    public void setPuestos(short puestos) {
        this.puestos = puestos;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Collection<Ruta> rutaCollection() {
        return rutaCollection;
    }

    public void setRutaCollection(Collection<Ruta> rutaCollection) {
        this.rutaCollection = rutaCollection;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransporte != null ? idTransporte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transporte)) {
            return false;
        }
        Transporte other = (Transporte) object;
        if ((this.idTransporte == null && other.idTransporte != null) || (this.idTransporte != null && !this.idTransporte.equals(other.idTransporte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Transporte[ idTransporte=" + idTransporte + " ]";
    }
    
}
