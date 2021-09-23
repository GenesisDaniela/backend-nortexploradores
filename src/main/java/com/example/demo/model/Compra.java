/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import com.example.demo.security.entity.Usuario;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIdCompra", query = "SELECT c FROM Compra c WHERE c.idCompra = :idCompra"),
    @NamedQuery(name = "Compra.findByCantidadPasajeros", query = "SELECT c FROM Compra c WHERE c.cantidadPasajeros = :cantidadPasajeros"),
    @NamedQuery(name = "Compra.findByTotalCompra", query = "SELECT c FROM Compra c WHERE c.totalCompra = :totalCompra")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_compra")
    private Integer idCompra;
    @Column(name = "cantidad_pasajeros")
    private Integer cantidadPasajeros;
    @Column(name = "total_compra")
    private Integer totalCompra;
    @JoinColumn(name = "descuento", referencedColumnName = "id_descuento")
    @ManyToOne
    private Descuento descuento;
    @JoinColumn(name = "paquete", referencedColumnName = "id_paq")
    @ManyToOne
    private Paquete paquete;
    @JoinColumn(name = "transaccion", referencedColumnName = "reference_sale")
    @ManyToOne
    private Transaccionp transaccion;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "idCompra")
    private Collection<DetalleCompra> detalleCompraCollection;

    public Compra() {
    }

    public Compra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Integer getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(Integer cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public Integer getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Integer totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Descuento descuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public Paquete paquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public Transaccionp transaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccionp transaccion) {
        this.transaccion = transaccion;
    }

    public Usuario usuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<DetalleCompra> detalleCompraCollection() {
        return detalleCompraCollection;
    }

    public void setDetalleCompraCollection(Collection<DetalleCompra> detalleCompraCollection) {
        this.detalleCompraCollection = detalleCompraCollection;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Compra[ idCompra=" + idCompra + " ]";
    }
    
}
