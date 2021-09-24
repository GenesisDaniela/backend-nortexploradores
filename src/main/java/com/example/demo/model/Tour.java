/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "tour")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tour.findAll", query = "SELECT t FROM Tour t"),
    @NamedQuery(name = "Tour.findByIdTour", query = "SELECT t FROM Tour t WHERE t.idTour = :idTour"),
    @NamedQuery(name = "Tour.findByFechaLlegada", query = "SELECT t FROM Tour t WHERE t.fechaLlegada = :fechaLlegada"),
    @NamedQuery(name = "Tour.findByFechaSalida", query = "SELECT t FROM Tour t WHERE t.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "Tour.findByPrecio", query = "SELECT t FROM Tour t WHERE t.precio = :precio"),
    @NamedQuery(name = "Tour.findByMinCupos", query = "SELECT t FROM Tour t WHERE t.minCupos = :minCupos"),
    @NamedQuery(name = "Tour.findByMaxCupos", query = "SELECT t FROM Tour t WHERE t.maxCupos = :maxCupos")})
public class Tour implements Serializable {

    @Size(max = 250)
    @Column(name = "url_imagen")
    private String urlImagen;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tour")
    private Integer idTour;
    @Column(name = "fecha_llegada")
    @Temporal(TemporalType.DATE)
    private Date fechaLlegada;
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @Column(name = "precio")
    private Integer precio;
    @Column(name = "min_cupos")
    private Short minCupos;
    @Column(name = "max_cupos")
    private Short maxCupos;
    @OneToMany(mappedBy = "tour")
    private Collection<Paquete> paqueteCollection;
    @JoinColumn(name = "ruta", referencedColumnName = "id_ruta")
    @ManyToOne
    private Ruta ruta;
    @JoinColumn(name = "seguro", referencedColumnName = "id_seguro")
    @ManyToOne
    private Seguro seguro;

    public Tour() {
    }

    public Tour(Integer idTour) {
        this.idTour = idTour;
    }

    public Integer getIdTour() {
        return idTour;
    }

    public void setIdTour(Integer idTour) {
        this.idTour = idTour;
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

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
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

    public Collection<Paquete> paqueteCollection() {
        return paqueteCollection;
    }

    public void setPaqueteCollection(Collection<Paquete> paqueteCollection) {
        this.paqueteCollection = paqueteCollection;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
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
        return "com.example.demo.model.Tour[ idTour=" + idTour + " ]";
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
    
}
