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
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import test.entity.Image;

/**
 *
 * @author alumne
 */
@WebServlet(name = "modifyImg", urlPatterns = {"/modifyImg"})
public class modifyImg extends HttpServlet {

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
        List<Image> imgList = new ArrayList<>();
        response.setContentType("text/html;charset=UTF-8");
        try {
            String query;
            PreparedStatement statement;
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");
                  
            
            // Select information from users and images and show in the web
            query = "SELECT * FROM Image WHERE Image.id = " + request.getParameter("id");
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(); 
            
            while (rs.next()){
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String keywords = rs.getString("keywords");
                String author = rs.getString("author");
                String creator = rs.getString("creator");
                java.util.Date capturingdate = rs.getDate("capturingdate");
                java.util.Date storagedate = rs.getDate("storagedate");
                String filename = rs.getString("filename");
                Boolean encrypted = rs.getBoolean("encrypted");
                Image img = new Image(id, title, description, keywords, author, creator, capturingdate, storagedate, filename, encrypted);
                imgList.add(img);
            }
            
            
            
            //System.out.println(rs);
            request.getSession().setAttribute("imgList", imgList);
            statement.close();
            connection.close();
            response.setContentType("text/html;charset=UTF-8");
            try {
                ViewManager.nextView(request, response, "/views/modifyImg.jsp");
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
        Connection connection = null;
        response.setContentType("text/html;charset=UTF-8");
        try {
            String query;
            PreparedStatement statement;
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");
            
            // update image metadata
            query = "UPDATE Image SET Title = ?, Description = ?, Keywords = ?, Author = ?, Creator = ? WHERE id = " + request.getParameter("id"); 
            //query = "UPDATE Image SET Title = ? WHERE id = " + request.getParameter("id"); 
            System.out.println(query);
            statement = connection.prepareStatement(query);
            statement.setString(1, (String) request.getParameter("title"));
            statement.setString(2, (String) request.getParameter("description"));
            statement.setString(3, (String) request.getParameter("keywords"));
            statement.setString(4, (String) request.getParameter("author"));
            statement.setString(5, (String) request.getParameter("creator"));
            //String date = request.getParameter("capturingdate");
            //String[] dateArray = date.split("-");
            //statement.setDate(6, new Date(Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]),Integer.parseInt(dateArray[2])));
            //statement.setString(7, request.getParameter("creator"));
            //statement.setBoolean(8, "on".equals(request.getParameter("encrypt")));
            System.out.println("Executing");
            statement.executeUpdate();
            System.out.println("Executed");
            statement.close();
            System.out.println("statement closed");
            connection.close();
            System.out.println("connection closed");
            
            System.out.println("modified");
            
            
            response.setContentType("text/html;charset=UTF-8");
            try {
                ViewManager.nextView(request, response, "/views/editedImg.jsp");
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
