/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "seguro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seguro.findAll", query = "SELECT s FROM Seguro s"),
    @NamedQuery(name = "Seguro.findByIdSeguro", query = "SELECT s FROM Seguro s WHERE s.idSeguro = :idSeguro"),
    @NamedQuery(name = "Seguro.findByNombre", query = "SELECT s FROM Seguro s WHERE s.nombre = :nombre")})
public class Seguro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_seguro")
    private Integer idSeguro;
    @Size(max = 25)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "id_seguro", referencedColumnName = "id_empresa", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Empresa empresa;
    @OneToMany(mappedBy = "seguro")
    private Collection<Tour> tourCollection;

    public Seguro() {
    }

    public Seguro(Integer idSeguro) {
        this.idSeguro = idSeguro;
    }

    public Integer getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Integer idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Empresa empresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Collection<Tour> tourCollection() {
        return tourCollection;
    }

    public void setTourCollection(Collection<Tour> tourCollection) {
        this.tourCollection = tourCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeguro != null ? idSeguro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seguro)) {
            return false;
        }
        Seguro other = (Seguro) object;
        if ((this.idSeguro == null && other.idSeguro != null) || (this.idSeguro != null && !this.idSeguro.equals(other.idSeguro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Seguro[ idSeguro=" + idSeguro + " ]";
    }
    
}
