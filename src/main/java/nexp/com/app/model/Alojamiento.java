/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "alojamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alojamiento.findAll", query = "SELECT a FROM Alojamiento a"),
    @NamedQuery(name = "Alojamiento.findByIdAlojamiento", query = "SELECT a FROM Alojamiento a WHERE a.idAlojamiento = :idAlojamiento"),
    @NamedQuery(name = "Alojamiento.findByNombre", query = "SELECT a FROM Alojamiento a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Alojamiento.findByDir", query = "SELECT a FROM Alojamiento a WHERE a.dir = :dir"),
    @NamedQuery(name = "Alojamiento.findByPrecio", query = "SELECT a FROM Alojamiento a WHERE a.precio = :precio")})
public class Alojamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_alojamiento")
    private Integer idAlojamiento;
    @Size(max = 25)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "dir")
    private String dir;
    @Lob
    @Size(max = 16777215)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "precio")
    private Integer precio;
    @OneToMany(mappedBy = "alojamiento")
    private Collection<Paquete> paqueteCollection;

    public Alojamiento() {
    }

    public Alojamiento(Integer idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public Integer getIdAlojamiento() {
        return idAlojamiento;
    }

    public void setIdAlojamiento(Integer idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Collection<Paquete> paqueteCollection() {
        return paqueteCollection;
    }

    public void setPaqueteCollection(Collection<Paquete> paqueteCollection) {
        this.paqueteCollection = paqueteCollection;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlojamiento != null ? idAlojamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alojamiento)) {
            return false;
        }
        Alojamiento other = (Alojamiento) object;
        if ((this.idAlojamiento == null && other.idAlojamiento != null) || (this.idAlojamiento != null && !this.idAlojamiento.equals(other.idAlojamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Alojamiento[ idAlojamiento=" + idAlojamiento + " ]";
    }
    
}
