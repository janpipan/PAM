/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author alumne
 */
@Entity
@Table(name = "IMAGE")
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findById", query = "SELECT i FROM Image i WHERE i.id = :id"),
    @NamedQuery(name = "Image.findByTitle", query = "SELECT i FROM Image i WHERE i.title = :title"),
    @NamedQuery(name = "Image.findByKeywords", query = "SELECT i FROM Image i WHERE i.keywords = :keywords"),
    @NamedQuery(name = "Image.findByAuthor", query = "SELECT i FROM Image i WHERE i.author = :author"),
    @NamedQuery(name = "Image.findByCreator", query = "SELECT i FROM Image i WHERE i.creator = :creator"),
    @NamedQuery(name = "Image.findByCapturingdate", query = "SELECT i FROM Image i WHERE i.capturingdate = :capturingdate"),
    @NamedQuery(name = "Image.findByStoragedate", query = "SELECT i FROM Image i WHERE i.storagedate = :storagedate"),
    @NamedQuery(name = "Image.findByFilename", query = "SELECT i FROM Image i WHERE i.filename = :filename"),
    @NamedQuery(name = "Image.findByEncrypted", query = "SELECT i FROM Image i WHERE i.encrypted = :encrypted")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "TITLE")
    private String title;
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "KEYWORDS")
    private String keywords;
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "CREATOR")
    private String creator;
    @Column(name = "CAPTURINGDATE")
    @Temporal(TemporalType.DATE)
    private Date capturingdate;
    @Column(name = "STORAGEDATE")
    @Temporal(TemporalType.DATE)
    private Date storagedate;
    @Column(name = "FILENAME")
    private String filename;
    @Column(name = "ENCRYPTED")
    private Boolean encrypted;

    public Image() {
    }

    public Image(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCapturingdate() {
        return capturingdate;
    }

    public void setCapturingdate(Date capturingdate) {
        this.capturingdate = capturingdate;
    }

    public Date getStoragedate() {
        return storagedate;
    }

    public void setStoragedate(Date storagedate) {
        this.storagedate = storagedate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
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
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "test.entity.Image[ id=" + id + " ]";
    }
    
}
