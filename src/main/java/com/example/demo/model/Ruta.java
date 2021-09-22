/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ruta.findAll", query = "SELECT r FROM Ruta r"),
    @NamedQuery(name = "Ruta.findByIdRuta", query = "SELECT r FROM Ruta r WHERE r.idRuta = :idRuta")})
public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ruta")
    private Integer idRuta;
    @JoinColumn(name = "id_mun", referencedColumnName = "id_muni")
    @ManyToOne
    private Municipio idMun;
    @JoinColumn(name = "transporte", referencedColumnName = "id_transporte")
    @ManyToOne(optional = false)
    private Transporte transporte;
    @OneToMany(mappedBy = "ruta")
    private Collection<Tour> tourCollection;

    public Ruta() {
    }

    public Ruta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public Integer getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Integer idRuta) {
        this.idRuta = idRuta;
    }

    public Municipio getIdMun() {
        return idMun;
    }

    public void setIdMun(Municipio idMun) {
        this.idMun = idMun;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
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
        hash += (idRuta != null ? idRuta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ruta)) {
            return false;
        }
        Ruta other = (Ruta) object;
        if ((this.idRuta == null && other.idRuta != null) || (this.idRuta != null && !this.idRuta.equals(other.idRuta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.demo.model.Ruta[ idRuta=" + idRuta + " ]";
    }
    
}
