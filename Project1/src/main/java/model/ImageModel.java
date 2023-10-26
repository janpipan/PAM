/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import entity.Image;
import jakarta.transaction.UserTransaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author alumne
 */
public class ImageModel {
    
    UserTransaction utx;
    EntityManager em;
    
    public ImageModel(EntityManager em, UserTransaction utx){
        this.utx = utx;
        this.em = em;
    }
    
    public void addImage(Image img) throws Exception{
        utx.begin();
        em.persist(img);
        em.flush();
        utx.commit();
    }
    
    public List<Image> retrieveAll(){
        Query q = em.createNamedQuery("Image.findAll");
        return q.getResultList();
    } 
    
    
}
