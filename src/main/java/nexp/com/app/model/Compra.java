/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import nexp.com.app.security.model.Usuario;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "compra")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
        @NamedQuery(name = "Compra.findByIdCompra", query = "SELECT c FROM Compra c WHERE c.idCompra = :idCompra"),
        @NamedQuery(name = "Compra.findByCantidadPasajeros", query = "SELECT c FROM Compra c WHERE c.cantidadPasajeros = :cantidadPasajeros"),
        @NamedQuery(name = "Compra.findByTotalCompra", query = "SELECT c FROM Compra c WHERE c.totalCompra = :totalCompra"),
        @NamedQuery(name = "Compra.findByEstado", query = "SELECT c FROM Compra c WHERE c.estado = :estado"),
        @NamedQuery(name = "Compra.findByFecha", query = "SELECT c FROM Compra c WHERE c.fecha = :fecha")})
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_compra")
    private Long idCompra;
    @Column(name = "cantidad_pasajeros")
    private Integer cantidadPasajeros;
    @Column(name = "total_compra")
    private Integer totalCompra;
    @Size(max = 20)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "referenceSale")
    private Collection<Transaccionp> transaccionpCollection;
    @OneToMany(mappedBy = "compra")
    private Collection<DetalleCompra> detalleCompraCollection;
    @JoinColumn(name = "tour", referencedColumnName = "id_tour")
    @ManyToOne
    private Tour tour;
    @JoinColumn(name = "reserva", referencedColumnName = "id_reserva")
    @ManyToOne
    private Reserva reserva;
    @JoinColumn(name = "descuento", referencedColumnName = "id_descuento")
    @ManyToOne
    private Descuento descuento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compra")
    private Collection<Devolucion> devolucionCollection;

    public Compra() {
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Compra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<Transaccionp> transaccionpCollection() {
        return transaccionpCollection;
    }

    public void setTransaccionpCollection(Collection<Transaccionp> transaccionpCollection) {
        this.transaccionpCollection = transaccionpCollection;
    }

    public Collection<Devolucion> devolucionCollection() {
        return devolucionCollection;
    }

    public void setDevolucionCollection(Collection<Devolucion> devolucionCollection) {
        this.devolucionCollection = devolucionCollection;
    }

    public Collection<DetalleCompra> detalleCompraCollection() {
        return detalleCompraCollection;
    }

    public void setDetalleCompraCollection(Collection<DetalleCompra> detalleCompraCollection) {
        this.detalleCompraCollection = detalleCompraCollection;
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
        return "nexp.com.app.model.Compra[ idCompra=" + idCompra + " ]";
    }

}
