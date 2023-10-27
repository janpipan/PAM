/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.transaction.UserTransaction;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.ImageModel;

/**
 *
 * @author alumne
 */
public class ContextListener implements ServletContextListener, HttpSessionListener {
    
    @Resource
    private UserTransaction utx;
    @PersistenceContext(unitName="imagePU")
    private EntityManager em;
    
    public void contextInitialized(ServletContextEvent event) {
        
        
        
        ServletContext context = event.getServletContext();
        System.out.println("Context listener initialized");
        if (em!=null){
            System.out.println("em is not null");
            context.setAttribute("imageModel", new ImageModel(em,utx));
        } else {
            System.out.println("em is null");
        }
        
        context.setAttribute("test", "test");
        
        
        
    }
    
    public void contextDestroyed(ServletContextEvent e) {
        
    }
    
    public void sessionCreated(HttpSessionEvent arg) {
        arg.getSession().setAttribute("session_data", "active");
    }
    
    public void sessionDestroyed(HttpSessionEvent arg) {
        arg.getSession().getAttribute("session_data");
    }

}
