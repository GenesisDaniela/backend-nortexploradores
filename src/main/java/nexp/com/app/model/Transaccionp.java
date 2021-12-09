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
 * @author santi
 */
@Entity
@Table(name = "transaccionp")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Transaccionp.findAll", query = "SELECT t FROM Transaccionp t"),
        @NamedQuery(name = "Transaccionp.findByTransactionId", query = "SELECT t FROM Transaccionp t WHERE t.transactionId = :transactionId"),
        @NamedQuery(name = "Transaccionp.findByDate", query = "SELECT t FROM Transaccionp t WHERE t.date = :date"),
        @NamedQuery(name = "Transaccionp.findByPaymentMethodType", query = "SELECT t FROM Transaccionp t WHERE t.paymentMethodType = :paymentMethodType"),
        @NamedQuery(name = "Transaccionp.findByTransactionDate", query = "SELECT t FROM Transaccionp t WHERE t.transactionDate = :transactionDate"),
        @NamedQuery(name = "Transaccionp.findByTax", query = "SELECT t FROM Transaccionp t WHERE t.tax = :tax"),
        @NamedQuery(name = "Transaccionp.findByShippingCountry", query = "SELECT t FROM Transaccionp t WHERE t.shippingCountry = :shippingCountry"),
        @NamedQuery(name = "Transaccionp.findByDescription", query = "SELECT t FROM Transaccionp t WHERE t.description = :description"),
        @NamedQuery(name = "Transaccionp.findByCurrency", query = "SELECT t FROM Transaccionp t WHERE t.currency = :currency"),
        @NamedQuery(name = "Transaccionp.findByValue", query = "SELECT t FROM Transaccionp t WHERE t.value = :value"),
        @NamedQuery(name = "Transaccionp.findByPaymentMethodName", query = "SELECT t FROM Transaccionp t WHERE t.paymentMethodName = :paymentMethodName"),
        @NamedQuery(name = "Transaccionp.findByEmailBuyer", query = "SELECT t FROM Transaccionp t WHERE t.emailBuyer = :emailBuyer"),
        @NamedQuery(name = "Transaccionp.findByPaymentMethodId", query = "SELECT t FROM Transaccionp t WHERE t.paymentMethodId = :paymentMethodId"),
        @NamedQuery(name = "Transaccionp.findByResponseMessagePol", query = "SELECT t FROM Transaccionp t WHERE t.responseMessagePol = :responseMessagePol"),
        @NamedQuery(name = "Transaccionp.findByAttempts", query = "SELECT t FROM Transaccionp t WHERE t.attempts = :attempts")})
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
    @Column(name = "payment_method_type")
    private Short paymentMethodType;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Size(max = 5)
    @Column(name = "tax")
    private String tax;
    @Size(max = 5)
    @Column(name = "shipping_country")
    private String shippingCountry;
    @Size(max = 150)
    @Column(name = "description")
    private String description;
    @Size(max = 5)
    @Column(name = "currency")
    private String currency;
    @Column(name = "value")
    private Long value;
    @Size(max = 50)
    @Column(name = "payment_method_name")
    private String paymentMethodName;
    @Size(max = 25)
    @Column(name = "email_buyer")
    private String emailBuyer;
    @Column(name = "payment_method_id")
    private Short paymentMethodId;
    @Size(max = 25)
    @Column(name = "response_message_pol")
    private String responseMessagePol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "attempts")
    private short attempts;
    @JoinColumn(name = "reference_sale", referencedColumnName = "id_compra")
    @ManyToOne
    private Compra referenceSale;

    public Transaccionp() {
    }

    public Transaccionp(String transactionId) {
        this.transactionId = transactionId;
    }

    public Transaccionp(String transactionId, short attempts) {
        this.transactionId = transactionId;
        this.attempts = attempts;
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

    public Short getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(Short paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }

    public String getEmailBuyer() {
        return emailBuyer;
    }

    public void setEmailBuyer(String emailBuyer) {
        this.emailBuyer = emailBuyer;
    }

    public Short getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Short paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getResponseMessagePol() {
        return responseMessagePol;
    }

    public void setResponseMessagePol(String responseMessagePol) {
        this.responseMessagePol = responseMessagePol;
    }

    public short getAttempts() {
        return attempts;
    }

    public void setAttempts(short attempts) {
        this.attempts = attempts;
    }

    public Compra getReferenceSale() {
        return referenceSale;
    }

    public void setReferenceSale(Compra referenceSale) {
        this.referenceSale = referenceSale;
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