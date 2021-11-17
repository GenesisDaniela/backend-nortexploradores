/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexp.com.app.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GenesisDanielaVJ
 */
@Entity
@Table(name = "transaccionp")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Transaccionp.findAll", query = "SELECT t FROM Transaccionp t"),
        @NamedQuery(name = "Transaccionp.findByTransactionId", query = "SELECT t FROM Transaccionp t WHERE t.transactionId = :transactionId"),
        @NamedQuery(name = "Transaccionp.findByDate", query = "SELECT t FROM Transaccionp t WHERE t.date = :date"),
        @NamedQuery(name = "Transaccionp.findByValue", query = "SELECT t FROM Transaccionp t WHERE t.value = :value"),
        @NamedQuery(name = "Transaccionp.findByResponseMessagePol", query = "SELECT t FROM Transaccionp t WHERE t.responseMessagePol = :responseMessagePol")})
public class Transaccionp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "value")
    private Long value;
    @Size(max = 25)
    @Column(name = "response_message_pol")
    private String responseMessagePol;
    @Size(max = 150)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "reference_sale", referencedColumnName = "id_compra")
    @ManyToOne
    private Compra referenceSale;

    public Transaccionp() {
    }

    public Transaccionp(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getResponseMessagePol() {
        return responseMessagePol;
    }

    public void setResponseMessagePol(String responseMessagePol) {
        this.responseMessagePol = responseMessagePol;
    }

    public Compra getReferenceSale() {
        return referenceSale;
    }

    public void setReferenceSale(Compra referenceSale) {
        this.referenceSale = referenceSale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccionp)) {
            return false;
        }
        Transaccionp other = (Transaccionp) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nexp.com.app.model.Transaccionp[ transactionId=" + transactionId + " ]";
    }

}