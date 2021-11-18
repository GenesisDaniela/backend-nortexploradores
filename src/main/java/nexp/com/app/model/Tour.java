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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "tour")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tour.findAll", query = "SELECT t FROM Tour t"),
    @NamedQuery(name = "Tour.findByIdTour", query = "SELECT t FROM Tour t WHERE t.idTour = :idTour"),
    @NamedQuery(name = "Tour.findByMinCupos", query = "SELECT t FROM Tour t WHERE t.minCupos = :minCupos"),
    @NamedQuery(name = "Tour.findByMaxCupos", query = "SELECT t FROM Tour t WHERE t.maxCupos = :maxCupos"),
    @NamedQuery(name = "Tour.findByFechaLlegada", query = "SELECT t FROM Tour t WHERE t.fechaLlegada = :fechaLlegada"),
    @NamedQuery(name = "Tour.findByFechaSalida", query = "SELECT t FROM Tour t WHERE t.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "Tour.findByCantCupos", query = "SELECT t FROM Tour t WHERE t.cantCupos = :cantCupos"),
    @NamedQuery(name = "Tour.findByEstado", query = "SELECT t FROM Tour t WHERE t.estado = :estado")})
public class Tour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tour")
    private Integer idTour;
    @Column(name = "min_cupos")
    private Short minCupos;
    @Column(name = "max_cupos")
    private Short maxCupos;
    @Column(name = "fecha_llegada")
    @Temporal(TemporalType.DATE)
    private Date fechaLlegada;
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @Column(name = "cant_cupos")
    private Integer cantCupos;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleado empleado;
    @JoinColumn(name = "paquete", referencedColumnName = "id_paq")
    @ManyToOne
    private Paquete paquete;
    @JoinColumn(name = "seguro", referencedColumnName = "id_seguro")
    @ManyToOne
    private Seguro seguro;
    @OneToMany(mappedBy = "tour")
    private Collection<Calificacion> calificacionCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tour")
    private Collection<TransporteTour> transporteTourCollection;

    @OneToMany(mappedBy = "tour")
    private Collection<Compra> compraCollection;

    public Tour() {
    }

    public Tour(Integer idTour) {
        this.idTour = idTour;
    }

    public Tour(Integer idTour, String estado) {
        this.idTour = idTour;
        this.estado = estado;
    }

    public Collection<Calificacion> calificacionCollection() {
        return calificacionCollection;
    }

    public void setCalificacionCollection(Collection<Calificacion> calificacionCollection) {
        this.calificacionCollection = calificacionCollection;
    }

    public Collection<TransporteTour> transporteTourCollection() {
        return transporteTourCollection;
    }

    public void setTransporteTourCollection(Collection<TransporteTour> transporteTourCollection) {
        this.transporteTourCollection = transporteTourCollection;
    }

    public Collection<Compra> compraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    public Integer getIdTour() {
        return idTour;
    }

    public void setIdTour(Integer idTour) {
        this.idTour = idTour;
    }

    public Short getMinCupos() {
        return minCupos;
    }

    public void setMinCupos(Short minCupos) {
        this.minCupos = minCupos;
    }

    public Short getMaxCupos() {
        return maxCupos;
    }

    public void setMaxCupos(Short maxCupos) {
        this.maxCupos = maxCupos;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Integer getCantCupos() {
        return cantCupos;
    }

    public void setCantCupos(Integer cantCupos) {
        this.cantCupos = cantCupos;
    }

    public String getEstado() { return estado; }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String isEstado() {
        return estado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Paquete getPaquete() {
        return paquete;
    }

    public void setPaquete(Paquete paquete) {
        this.paquete = paquete;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTour != null ? idTour.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tour)) {
            return false;
        }
        Tour other = (Tour) object;
        if ((this.idTour == null && other.idTour != null) || (this.idTour != null && !this.idTour.equals(other.idTour))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Tour[ idTour=" + idTour + " ]";
    }
    
}
