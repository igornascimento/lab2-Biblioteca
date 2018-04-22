/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@Entity
@Table(name = "movimentation_book")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimentationBook.findAll", query = "SELECT m FROM MovimentationBook m")
    , @NamedQuery(name = "MovimentationBook.findById", query = "SELECT m FROM MovimentationBook m WHERE m.id = :id")
    , @NamedQuery(name = "MovimentationBook.findByMovimentationId", query = "SELECT m FROM MovimentationBook m WHERE m.movimentationId = :movimentationId")
    , @NamedQuery(name = "MovimentationBook.findByBookId", query = "SELECT m FROM MovimentationBook m WHERE m.bookId = :bookId")})
public class MovimentationBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "movimentation_id")
    private Integer movimentationId;
    @Column(name = "book_id")
    private Integer bookId;

    public MovimentationBook() {
    }

    public MovimentationBook(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovimentationId() {
        return movimentationId;
    }

    public void setMovimentationId(Integer movimentationId) {
        this.movimentationId = movimentationId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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
        if (!(object instanceof MovimentationBook)) {
            return false;
        }
        MovimentationBook other = (MovimentationBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MovimentationBook[ id=" + id + " ]";
    }
    
}
