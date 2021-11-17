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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "municipio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m"),
    @NamedQuery(name = "Municipio.findByIdMuni", query = "SELECT m FROM Municipio m WHERE m.idMuni = :idMuni"),
    @NamedQuery(name = "Municipio.findByUrlImagen", query = "SELECT m FROM Municipio m WHERE m.urlImagen = :urlImagen")})
public class Municipio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_muni")
    private Integer idMuni;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 250)
    @Column(name = "url_imagen")
    private String urlImagen;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "url_ubicacion")
    private String urlUbicacion;
    @JoinColumn(name = "id_depto", referencedColumnName = "id_depto")
    @ManyToOne
    private Departamento idDepto;

    public Municipio() {
    }

    public Municipio(Integer idMuni) {
        this.idMuni = idMuni;
    }

    public Municipio(Integer idMuni, String descripcion) {
        this.idMuni = idMuni;
        this.descripcion = descripcion;
    }

    public Integer getIdMuni() {
        return idMuni;
    }

    public void setIdMuni(Integer idMuni) {
        this.idMuni = idMuni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Departamento getIdDepto() {
        return idDepto;
    }

    public void setIdDepto(Departamento idDepto) {
        this.idDepto = idDepto;
    }

    public String getUrlUbicacion() {
        return urlUbicacion;
    }

    public void setUrlUbicacion(String urlUbicacion) {
        this.urlUbicacion = urlUbicacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMuni != null ? idMuni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        if ((this.idMuni == null && other.idMuni != null) || (this.idMuni != null && !this.idMuni.equals(other.idMuni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Municipio[ idMuni=" + idMuni + " ]";
    }
    
}
