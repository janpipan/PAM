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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import test.entity.Image;

/**
 *
 * @author alumne
 */
@WebServlet(name = "searchImg", urlPatterns = {"/searchImg"})
public class searchImg extends HttpServlet {

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
        
        request.getSession().setAttribute("searchList", null);
        response.setContentType("text/html;charset=UTF-8");
        try {
            ViewManager.nextView(request, response, "/views/searchImg.jsp");
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
        //processRequest(request, response);
        Connection connection = null;
        List<Image> searchList = new ArrayList<>();
        response.setContentType("text/html;charset=UTF-8");
        try {
            String query = "";
            PreparedStatement statement;
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");
                  
            
            // Select information from users and images and show in the web
            String parameter = (String) request.getParameter("parameter");
            System.out.println(parameter);
            //boolean secondParameter = request.getParameter("secondParameter").equals("on");
            if (request.getParameter("secondParameter") != null) {
                String parameter2 = (String) request.getParameter("parameter-2");
                System.out.println(parameter);
                System.out.println(parameter2);
                // if first parameter is keyword search and second parameter is date search
                if ((parameter.equals("keywords") && (parameter2.equals("capturingdate") || parameter2.equals("storagedate")))){
                    query = "SELECT * FROM Image WHERE Image." + parameter + " Like '%" + (String) request.getParameter("searchQuery") + "%' AND Image." + parameter2 + "= '" + (String) request.getParameter("searchDate-2") + "'";
                // if first parameter is title/author/creator and second parameter is date search
                } else if (!parameter.equals("keywords") && !(parameter.equals("capturingdate") || parameter.equals("storagedate")) && (parameter2.equals("capturingdate") || parameter2.equals("storagedate"))) {
                    query = "SELECT * FROM Image WHERE Image." + parameter + "= '" + (String) request.getParameter("searchQuery") + "' AND Image." + parameter2 + "= '" + (String) request.getParameter("searchDate-2") + "'";
                // if first parameter is date search and second parameter is keyword search
                } else if ((parameter.equals("capturingdate") || parameter.equals("storagedate")) && parameter2.equals("keywords")) {
                    query = "SELECT * FROM Image WHERE Image." + parameter2 + " Like '%" + (String) request.getParameter("searchQuery-2") + "%' AND Image." + parameter + "= '" + (String) request.getParameter("searchDate") + "'";
                // if second parameter is title/author/creator and first parameter is date search
                } else if (!parameter2.equals("keywords") && !(parameter2.equals("capturingdate") || parameter2.equals("storagedate")) && (parameter.equals("capturingdate") || parameter.equals("storagedate"))) {
                    query = "SELECT * FROM Image WHERE Image." + parameter2 + "= '" + (String) request.getParameter("searchQuery-2") + "' AND Image." + parameter + "= '" + (String) request.getParameter("searchDate") + "'";
                // if first and second parameters are date searches
                } else if ((parameter.equals("capturingdate") || parameter.equals("storagedate")) && (parameter2.equals("capturingdate") || parameter2.equals("storagedate"))) {
                    query = "SELECT * FROM Image WHERE Image." + parameter + "= '" + (String) request.getParameter("searchDate") + "' AND Image." + parameter2 + "= '" + (String) request.getParameter("searchDate-2") + "'";
                // if first and second parameters are keyword searches
                } else if (parameter.equals("keywords") && parameter2.equals("keywords")){
                    query = "SELECT * FROM Image WHERE Image." + parameter + " Like '%" + (String) request.getParameter("searchQuery") + "%' AND Image." + parameter2 + " Like '%" + (String) request.getParameter("searchQuery-2") + "'" ;
                // if first and second parameters are title/author/creator search
                } else {
                    query = "SELECT * FROM Image WHERE Image." + parameter + " = '" + (String) request.getParameter("searchQuery") + "' AND Image." + parameter2 + "= '" + (String) request.getParameter("searchQuery-2")  + "'";
                }
            } else {
                switch (parameter) {
                    case "keywords":
                        query = "SELECT * FROM Image WHERE Image." + parameter + " Like '%" + (String) request.getParameter("searchQuery") + "%'";
                        break;
                    case "capturingdate":
                    case "storagedate":
                        query = "SELECT * FROM Image WHERE Image." + parameter + "= '" + (String) request.getParameter("searchDate") + "'";
                        break;
                    default:
                        query = "SELECT * FROM Image WHERE Image." + parameter + " = '" + (String) request.getParameter("searchQuery") + "'";
                        break;
                }
            }
            
            
            System.out.println(query);
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();  
            
            
            while (rs.next()){
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String keywords = rs.getString("keywords");
                String author = rs.getString("author");
                String creator = rs.getString("creator");
                Date capturingdate = rs.getDate("capturingdate");
                Date storagedate = rs.getDate("storagedate");
                String filename = rs.getString("filename");
                Boolean encrypted = rs.getBoolean("encrypted");
                Image img = new Image(id, title, description, keywords, author, creator, capturingdate, storagedate, filename, encrypted);
                searchList.add(img);
            }
            
            request.getSession().setAttribute("searchList", searchList);
            
            
            statement.close();
            connection.close();
            response.setContentType("text/html;charset=UTF-8");
            try {
                ViewManager.nextView(request, response, "/views/searchImg.jsp");
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
