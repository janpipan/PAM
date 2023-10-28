/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package test.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.model.ImageModel;

/**
 *
 * @author alumne
 */
@WebServlet(name = "registerImg", urlPatterns = {"/registerImg"})
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        ImageModel imgModel = (ImageModel) context.getAttribute("imageModel");
        System.out.println(imgModel.retrieveAll());
    }
    

    protected void processPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        Connection con = null;
        String query;
        PreparedStatement statement;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");

            System.out.println("connected");
            
            
            
     
            query = "INSERT INTO image(Title, Description, Keywords, Author, Creator, CapturingDate, Filename, Encrypted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            statement = con.prepareStatement(query);
            statement.setString(1, (String) req.getParameter("title"));
            statement.setString(2, (String) req.getParameter("description"));
            statement.setString(3, (String) req.getParameter("keywords"));
            statement.setString(4, (String) req.getParameter("author"));
            statement.setString(5, (String) req.getParameter("creator"));
            String date = req.getParameter("capturingdate");
            String[] dateArray = date.split("-");
            statement.setDate(6, new Date(Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]),Integer.parseInt(dateArray[2])));
            statement.setString(7, req.getParameter("creator"));
            statement.setBoolean(8, "on".equals(req.getParameter("encrypt")));
            statement.executeUpdate();  
            
            
            System.out.println("New image meta data added");
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            Logger.getLogger(registerImg.class.getName()).log(Level.SEVERE, null, e);
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
