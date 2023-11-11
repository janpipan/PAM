/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package test.controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import test.entity.Image;

/**
 *
 * @author alumne
 */
@WebServlet(name = "deleteImg", urlPatterns = {"/deleteImg"})
public class deleteImg extends HttpServlet {
    private static final String SAVE_DIR = "/home/alumne/imgs";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet deleteImg</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteImg at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        Connection connection = null;
        String query;
        PreparedStatement statement;
        try {
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");
                  
            
            // get image id
            int imageId = Integer.parseInt(request.getParameter("id"));
            
            // get image path
            query = "SELECT filename FROM Image WHERE Image.id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, imageId);
            
            ResultSet rs = statement.executeQuery();  
            
            
            String filePath = null;
            String fileName = null;
            while (rs.next()){
                fileName = rs.getString("filename");
            }
            if (fileName != null) {
                filePath = SAVE_DIR + File.separator + fileName;
            }
            
            
            query = "DELETE FROM Encryption WHERE Encryption.Picture_id = ?" ;
            try (PreparedStatement deleteEncryptionStatement = connection.prepareStatement(query)){
                deleteEncryptionStatement.setInt(1, imageId);
                deleteEncryptionStatement.executeUpdate();
            }
           
            // delete image
            query = "DELETE FROM Image WHERE Image.id = ?";
            try (PreparedStatement deleteImageStatement = connection.prepareStatement(query)){
                deleteImageStatement.setInt(1, imageId);
                deleteImageStatement.executeUpdate();
            }
            if (filePath != null){
                try {
                    Path path = Paths.get(filePath);
                    Files.delete(path);
                    System.out.println("File deleted succesfully");
                } catch (IOException e) {
                    System.err.println("Unable to delete the file:" + e.getMessage());
                }
            }
            
            
            connection.close();
            
            
            
            response.setContentType("text/html;charset=UTF-8");
            try {
                ViewManager.nextView(request, response, "/views/deleteImg.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                if (dispatcher != null) {
                    dispatcher.forward(request,response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        processRequest(request, response);
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
