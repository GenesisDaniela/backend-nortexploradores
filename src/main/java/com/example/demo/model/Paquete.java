/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "paquete")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paquete.findAll", query = "SELECT p FROM Paquete p"),
    @NamedQuery(name = "Paquete.findByIdPaq", query = "SELECT p FROM Paquete p WHERE p.idPaq = :idPaq"),
    @NamedQuery(name = "Paquete.findByDescripcion", query = "SELECT p FROM Paquete p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Paquete.findByPrecio", query = "SELECT p FROM Paquete p WHERE p.precio = :precio"),
    @NamedQuery(name = "Paquete.findByRecomendacion", query = "SELECT p FROM Paquete p WHERE p.recomendacion = :recomendacion")})
public class Paquete implements Serializable {

    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "recomendacion")
    private String recomendacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "url_imagen")
    private String urlImagen;
    @ManyToMany(mappedBy = "paqueteList")
    private List<Complemento> complementoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_paq")
    private Integer idPaq;
    @Column(name = "precio")
    private Integer precio;
    @OneToMany(mappedBy = "paquete")
    private Collection<Compra> compraCollection;
    @OneToMany(mappedBy = "paquete")
    private Collection<Calificacion> calificacionCollection;
    @JoinColumn(name = "alojamiento", referencedColumnName = "id_alojamiento")
    @ManyToOne
    private Alojamiento alojamiento;
    @JoinColumn(name = "tour", referencedColumnName = "id_tour")
    @ManyToOne
    private Tour tour;

    public Paquete() {
    }

    public Paquete(Integer idPaq) {
        this.idPaq = idPaq;
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


    public Collection<Compra> compraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    public Collection<Calificacion> calificacionCollection() {
        return calificacionCollection;
    }

    public void setCalificacionCollection(Collection<Calificacion> calificacionCollection) {
        this.calificacionCollection = calificacionCollection;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
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
        return "com.example.demo.model.Paquete[ idPaq=" + idPaq + " ]";
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @XmlTransient
    public List<Complemento> getComplementoList() {
        return complementoList;
    }

    public void setComplementoList(List<Complemento> complementoList) {
        this.complementoList = complementoList;
    }
    
}
