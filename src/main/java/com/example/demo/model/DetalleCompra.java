/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "detalle_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCompra.findAll", query = "SELECT d FROM DetalleCompra d"),
    @NamedQuery(name = "DetalleCompra.findByIdDetalle", query = "SELECT d FROM DetalleCompra d WHERE d.idDetalle = :idDetalle"),
    @NamedQuery(name = "DetalleCompra.findByFecha", query = "SELECT d FROM DetalleCompra d WHERE d.fecha = :fecha")})
public class DetalleCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @JoinColumn(name = "id_compra", referencedColumnName = "id_compra")
    @ManyToOne
    private Compra idCompra;

    public DetalleCompra() {
    }

    public DetalleCompra(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Compra getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Compra idCompra) {
        this.idCompra = idCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCompra)) {
            return false;
        }
        DetalleCompra other = (DetalleCompra) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.DetalleCompra[ idDetalle=" + idDetalle + " ]";
    }
    
}
