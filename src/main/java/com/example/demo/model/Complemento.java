/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "complemento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Complemento.findAll", query = "SELECT c FROM Complemento c"),
    @NamedQuery(name = "Complemento.findByIdComplemento", query = "SELECT c FROM Complemento c WHERE c.idComplemento = :idComplemento"),
    @NamedQuery(name = "Complemento.findByNombre", query = "SELECT c FROM Complemento c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Complemento.findByUrlImagen", query = "SELECT c FROM Complemento c WHERE c.urlImagen = :urlImagen")})
public class Complemento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_complemento")
    private Integer idComplemento;
    @Size(max = 25)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 250)
    @Column(name = "url_imagen")
    private String urlImagen;
    @JoinTable(name = "complemento_paquete", joinColumns = {
        @JoinColumn(name = "id_comp", referencedColumnName = "id_complemento")}, inverseJoinColumns = {
        @JoinColumn(name = "id_paq", referencedColumnName = "id_paq")})
    @ManyToMany
    private List<Paquete> paqueteList;

    public Complemento() {
    }

    public Complemento(Integer idComplemento) {
        this.idComplemento = idComplemento;
    }

    public Integer getIdComplemento() {
        return idComplemento;
    }

    public void setIdComplemento(Integer idComplemento) {
        this.idComplemento = idComplemento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @XmlTransient
    public List<Paquete> getPaqueteList() {
        return paqueteList;
    }

    public void setPaqueteList(List<Paquete> paqueteList) {
        this.paqueteList = paqueteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComplemento != null ? idComplemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complemento)) {
            return false;
        }
        Complemento other = (Complemento) object;
        if ((this.idComplemento == null && other.idComplemento != null) || (this.idComplemento != null && !this.idComplemento.equals(other.idComplemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Complemento[ idComplemento=" + idComplemento + " ]";
    }
    
}
