/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@Entity
@Table(name = "movimentation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimentation.findAll", query = "SELECT m FROM Movimentation m")
    , @NamedQuery(name = "Movimentation.findById", query = "SELECT m FROM Movimentation m WHERE m.id = :id")
    , @NamedQuery(name = "Movimentation.findByDate", query = "SELECT m FROM Movimentation m WHERE m.date = :date")
    , @NamedQuery(name = "Movimentation.findByCustomerId", query = "SELECT m FROM Movimentation m WHERE m.customerId = :customerId")})
public class Movimentation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "customer-id")
    private Integer customerId;

    public Movimentation() {
    }

    public Movimentation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimentation)) {
            return false;
        }
        Movimentation other = (Movimentation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Movimentation[ id=" + id + " ]";
    }
    
}
