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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "seguro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seguro.findAll", query = "SELECT s FROM Seguro s"),
    @NamedQuery(name = "Seguro.findByIdSeguro", query = "SELECT s FROM Seguro s WHERE s.idSeguro = :idSeguro"),
    @NamedQuery(name = "Seguro.findByNombre", query = "SELECT s FROM Seguro s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "Seguro.findByDescripcion", query = "SELECT s FROM Seguro s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Seguro.findByPrecio", query = "SELECT s FROM Seguro s WHERE s.precio = :precio")})
public class Seguro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_seguro")
    private Integer idSeguro;
    @Size(max = 25)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio")
    private int precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "empresa", referencedColumnName = "id_empresa")
    @ManyToOne(optional = false)
    private Empresa empresa;
    @OneToMany(mappedBy = "seguro")
    private Collection<Tour> tourCollection;

    public Seguro() {
    }

    public Seguro(Integer idSeguro) {
        this.idSeguro = idSeguro;
    }

    public Seguro(Integer idSeguro, String descripcion, int precio) {
        this.idSeguro = idSeguro;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Integer getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Integer idSeguro) {
        this.idSeguro = idSeguro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean getEstado() { return estado; }

    public void setEstado(boolean estado) { this.estado = estado; }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        hash += (idSeguro != null ? idSeguro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seguro)) {
            return false;
        }
        Seguro other = (Seguro) object;
        if ((this.idSeguro == null && other.idSeguro != null) || (this.idSeguro != null && !this.idSeguro.equals(other.idSeguro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Seguro[ idSeguro=" + idSeguro + " ]";
    }
    
}
