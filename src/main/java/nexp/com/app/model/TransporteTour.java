/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;


import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "transporte_tour")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "RutaTransporte.findAll", query = "SELECT r FROM TransporteTour r"),
        @NamedQuery(name = "RutaTransporte.findByIdRutaTransporte", query = "SELECT r FROM TransporteTour r WHERE r.idTransporteTour = :idRutaTransporte")})
public class TransporteTour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_transporte_tour")
    private Integer idTransporteTour;
    @JoinColumn(name = "transporte", referencedColumnName = "id_transporte")
    @ManyToOne(optional = false)
    private Transporte transporte;
    @JoinColumn(name = "tour", referencedColumnName = "id_tour")
    @ManyToOne(optional = false)
    private Tour tour;


    public TransporteTour() {
    }

    public TransporteTour(Integer idTransporteTour) {
        this.idTransporteTour = idTransporteTour;
    }

    public Integer getIdRutaTransporte() {
        return idTransporteTour;
    }

    public void setIdRutaTransporte(Integer idTransporteTour) {
        this.idTransporteTour = idTransporteTour;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransporteTour != null ? idTransporteTour.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransporteTour)) {
            return false;
        }
        TransporteTour other = (TransporteTour) object;
        if ((this.idTransporteTour == null && other.idTransporteTour != null) || (this.idTransporteTour != null && !this.idTransporteTour.equals(other.idTransporteTour))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebas.RutaTransporte[ idTransporteTour=" + idTransporteTour + " ]";
    }

}
