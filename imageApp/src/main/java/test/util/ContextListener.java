/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import jakarta.annotation.Resource;
import jakarta.jms.Connection;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.transaction.UserTransaction;
import javax.crypto.spec.IvParameterSpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import test.model.ImageModel;

/**
 *
 * @author alumne
 */
public class ContextListener implements ServletContextListener {
    
    
    @Override 
    public void contextInitialized(ServletContextEvent e) {
        
        ServletContext context = e.getServletContext();
        IvParameterSpec iv = Encrypt.generateIv();
        context.setAttribute("iv", iv);
        
        
        
        
        
        
    }
    
    public void contextDestroyed(ServletContextEvent e) {
    
    }
    
    public void sessionCreate(HttpSessionEvent arg0) {
        arg0.getSession().setAttribute("seession_data", "test");
    }
    
    public void sessionDestroyed(HttpSessionEvent arg0) {
        arg0.getSession().getAttribute("session_data");
    }
}
