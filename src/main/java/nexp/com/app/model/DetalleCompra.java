/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "detalle_compra")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "DetalleCompra.findAll", query = "SELECT d FROM DetalleCompra d"),
        @NamedQuery(name = "DetalleCompra.findByIdDetalle", query = "SELECT d FROM DetalleCompra d WHERE d.idDetalle = :idDetalle"),
        @NamedQuery(name = "DetalleCompra.findByFecha", query = "SELECT d FROM DetalleCompra d WHERE d.fecha = :fecha"),
        @NamedQuery(name = "DetalleCompra.findByValorUnit", query = "SELECT d FROM DetalleCompra d WHERE d.valorUnit = :valorUnit")})
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_unit")
    private int valorUnit;
    @JoinColumn(name = "compra", referencedColumnName = "id_compra")
    @ManyToOne
    private Compra compra;

    @JoinColumn(name = "pasajero", referencedColumnName = "id_pasajero")
    @ManyToOne
    private Pasajero pasajero;

    public DetalleCompra() {
    }

    public DetalleCompra(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public DetalleCompra(Integer idDetalle, int valorUnit) {
        this.idDetalle = idDetalle;
        this.valorUnit = valorUnit;
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

    public int getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(int valorUnit) {
        this.valorUnit = valorUnit;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
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
        return "nexp.com.app.model.DetalleCompra[ idDetalle=" + idDetalle + " ]";
    }

}
