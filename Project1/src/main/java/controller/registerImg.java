/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import entity.Image;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ImageModel;

/**
 *
 * @author alumne
 */
@WebServlet(name = "registerImg", urlPatterns = {"/registerImg"})
@MultipartConfig
public class registerImg extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            ViewManager.nextView(request, response, "/views/registerImg.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            if (dispatcher != null) {
                dispatcher.forward(request,response);
            }
        }
    }
    
    protected void processPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException, Exception {
        
            
        ServletContext context = getServletContext();
        
        
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String keywords = req.getParameter("keywords");
        String author = req.getParameter("author");
        String creator = req.getParameter("creator");
        String filename = req.getParameter("filename");
        Boolean encrypted = "on".equals(req.getParameter("encrypt"));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
        Date capturingDate;
        try {
            capturingDate = dateFormat.parse(req.getParameter("capturingdata"));
        } catch (ParseException e) {
            capturingDate = new Date();
        }
        Image img = new Image();
        img.setTitle(title);
        img.setDescription(description);
        img.setKeywords(keywords);
        img.setAuthor(author);
        img.setCreator(creator);
        img.setFilename(filename);
        img.setCapturingdata(capturingDate);
        img.setEncrypted(encrypted);
        
        
        ImageModel imgModel = (ImageModel) context.getAttribute("imageModel");
        //System.out.println(context.getAttribute("test"));
        imgModel.addImage(img);
        
        try {
            ViewManager.nextView(req, res, "/views/menu.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher dispatcher = req.getRequestDispatcher("/error.jsp");
            if (dispatcher != null) {
                dispatcher.forward(req, res);
            }
        }
        
        
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGet(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processPost(request, response);
        } catch (Exception ex) {
            Logger.getLogger(registerImg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
