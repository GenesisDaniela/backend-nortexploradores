/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "transporte")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Transporte.findAll", query = "SELECT t FROM Transporte t"),
        @NamedQuery(name = "Transporte.findByIdTransporte", query = "SELECT t FROM Transporte t WHERE t.idTransporte = :idTransporte"),
        @NamedQuery(name = "Transporte.findByPuestos", query = "SELECT t FROM Transporte t WHERE t.puestos = :puestos"),
        @NamedQuery(name = "Transporte.findByModelo", query = "SELECT t FROM Transporte t WHERE t.modelo = :modelo"),
        @NamedQuery(name = "Transporte.findByColor", query = "SELECT t FROM Transporte t WHERE t.color = :color"),
        @NamedQuery(name = "Transporte.findByPrecio", query = "SELECT t FROM Transporte t WHERE t.precio = :precio")})
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private int precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "empresa", referencedColumnName = "id_empresa")
    @ManyToOne
    private Empresa empresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transporte")
    private Collection<TransporteTour> transporteTourCollection;

    public Transporte() {}

    public Transporte(String idTransporte) {
        this.idTransporte = idTransporte;
    }

    public Transporte(String idTransporte, short puestos, String modelo, String color, int precio) {
        this.idTransporte = idTransporte;
        this.puestos = puestos;
        this.modelo = modelo;
        this.color = color;
        this.precio = precio;
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

    public int getPrecio() {
        return precio;
    }

    public boolean getEstado() { return estado; }

    public void setEstado(boolean estado) { this.estado = estado;}

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Collection<TransporteTour> transporteTourCollection() {
        return transporteTourCollection;
    }

    public void setRutaTransporteCollection(Collection<TransporteTour> transporteTourCollection) {
        this.transporteTourCollection = transporteTourCollection;
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
        return "nexp.com.app.model.Transporte[ idTransporte=" + idTransporte + " ]";
    }
    
}
