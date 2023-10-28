/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.model;

import jakarta.transaction.UserTransaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import test.entity.Image;

/**
 *
 * @author alumne
 */
public class ImageModel {
    UserTransaction utx;
    EntityManager em;
    
    public ImageModel(UserTransaction utx, EntityManager em) {
        this.utx = utx;
        this.em = em;
    }
    
    public List<Image> retrieveAll() {
        Query q = em.createNamedQuery("Image.findAll");
        return q.getResultList();
    }
    
}
