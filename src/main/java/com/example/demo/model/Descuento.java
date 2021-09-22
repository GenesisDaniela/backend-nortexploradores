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
@Table(name = "descuento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descuento.findAll", query = "SELECT d FROM Descuento d"),
    @NamedQuery(name = "Descuento.findByIdDescuento", query = "SELECT d FROM Descuento d WHERE d.idDescuento = :idDescuento"),
    @NamedQuery(name = "Descuento.findByPorcentaje", query = "SELECT d FROM Descuento d WHERE d.porcentaje = :porcentaje"),
    @NamedQuery(name = "Descuento.findByDescripcion", query = "SELECT d FROM Descuento d WHERE d.descripcion = :descripcion")})
public class Descuento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_descuento")
    private Integer idDescuento;
    @Column(name = "porcentaje")
    private Integer porcentaje;
    @Size(max = 25)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "descuento")
    private Collection<Compra> compraCollection;

    public Descuento() {
    }

    public Descuento(Integer idDescuento) {
        this.idDescuento = idDescuento;
    }

    public Integer getIdDescuento() {
        return idDescuento;
    }

    public void setIdDescuento(Integer idDescuento) {
        this.idDescuento = idDescuento;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Collection<Compra> compraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDescuento != null ? idDescuento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descuento)) {
            return false;
        }
        Descuento other = (Descuento) object;
        if ((this.idDescuento == null && other.idDescuento != null) || (this.idDescuento != null && !this.idDescuento.equals(other.idDescuento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Descuento[ idDescuento=" + idDescuento + " ]";
    }
    
}
