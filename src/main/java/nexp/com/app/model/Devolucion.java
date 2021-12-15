/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "devolucion")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Devolucion.findAll", query = "SELECT d FROM Devolucion d"),
        @NamedQuery(name = "Devolucion.findByIdDevolucion", query = "SELECT d FROM Devolucion d WHERE d.idDevolucion = :idDevolucion"),
        @NamedQuery(name = "Devolucion.findByCantidad", query = "SELECT d FROM Devolucion d WHERE d.cantidad = :cantidad"),
        @NamedQuery(name = "Devolucion.findByFecha", query = "SELECT d FROM Devolucion d WHERE d.fecha = :fecha")})
public class Devolucion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_devolucion")
    private Integer idDevolucion;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "compra", referencedColumnName = "id_compra")
    @ManyToOne
    private Compra compra;

    public Devolucion() {
    }

    public Devolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public Devolucion(Integer cantidad, LocalDate fecha, Compra compra) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.compra = compra;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Integer getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(Integer idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public Compra getCompra() {return compra;}

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDevolucion != null ? idDevolucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Devolucion)) {
            return false;
        }
        Devolucion other = (Devolucion) object;
        if ((this.idDevolucion == null && other.idDevolucion != null) || (this.idDevolucion != null && !this.idDevolucion.equals(other.idDevolucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Devolucion[ idDevolucion=" + idDevolucion + " ]";
    }

}
