/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "paquete")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paquete.findAll", query = "SELECT p FROM Paquete p"),
    @NamedQuery(name = "Paquete.findByIdPaq", query = "SELECT p FROM Paquete p WHERE p.idPaq = :idPaq"),
    @NamedQuery(name = "Paquete.findByPrecio", query = "SELECT p FROM Paquete p WHERE p.precio = :precio"),
    @NamedQuery(name = "Paquete.findByEstado", query = "SELECT p FROM Paquete p WHERE p.estado = :estado"),
    @NamedQuery(name = "Paquete.findByUrlImagen", query = "SELECT p FROM Paquete p WHERE p.urlImagen = :urlImagen"),
    @NamedQuery(name = "Paquete.findByRecomendacion", query = "SELECT p FROM Paquete p WHERE p.recomendacion = :recomendacion"),
    @NamedQuery(name = "Paquete.findByNombre", query = "SELECT p FROM Paquete p WHERE p.nombre = :nombre")})
public class Paquete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_paq")
    private Integer idPaq;
    @Column(name = "precio")
    private Integer precio;
    @Size(max = 10)
    @Column(name = "estado")
    private String estado;
    @Size(max = 255)
    @Column(name = "url_imagen")
    private String urlImagen;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 255)
    @Column(name = "recomendacion")
    private String recomendacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "tour")
    private Collection<SolicitudTour> solicitudTourCollection;
    @JoinColumn(name = "alojamiento", referencedColumnName = "id_alojamiento")
    @ManyToOne
    private Alojamiento alojamiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paquete")
    private Collection<Actividad> actividadCollection;
    @OneToMany(mappedBy = "paquete")
    private Collection<Tour> tourCollection;
    @JoinColumn(name = "municipio", referencedColumnName = "id_muni")
    @ManyToOne(optional = false)
    private Municipio municipio;

    public Paquete() {
    }

    public Paquete(Integer idPaq) {
        this.idPaq = idPaq;
    }

    public Paquete(Integer idPaq, String nombre) {
        this.idPaq = idPaq;
        this.nombre = nombre;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Integer getIdPaq() {
        return idPaq;
    }

    public void setIdPaq(Integer idPaq) {
        this.idPaq = idPaq;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<SolicitudTour> solicitudPaqueteCollection() {
        return solicitudTourCollection;
    }

    public void setSolicitudPaqueteCollection(Collection<SolicitudTour> solicitudTourCollection) {
        this.solicitudTourCollection = solicitudTourCollection;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Collection<Actividad> actividadCollection() {
        return actividadCollection;
    }

    public void setActividadCollection(Collection<Actividad> actividadCollection) {
        this.actividadCollection = actividadCollection;
    }

    public Collection<Tour> tourCollection() {
        return tourCollection;
    }

    public void setTourCollection(Collection<Tour> tourCollection) {
        this.tourCollection = tourCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaq != null ? idPaq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paquete)) {
            return false;
        }
        Paquete other = (Paquete) object;
        if ((this.idPaq == null && other.idPaq != null) || (this.idPaq != null && !this.idPaq.equals(other.idPaq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Paquete[ idPaq=" + idPaq + " ]";
    }
    
}
