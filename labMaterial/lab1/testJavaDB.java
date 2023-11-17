/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author silviall
 */
@WebServlet(name = "testJavaDB", urlPatterns = {"/testJavaDB"})
public class testJavaDB extends HttpServlet {

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
       Connection connection = null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String query;
            PreparedStatement statement;
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            
            /*
            // SQL Commands to create the database can be found in file database.sql		 		  
            */            

            /* 
               COMMENT THE FOLLOWING CODE IF YOU ALREADY HAVE 
               CREATED AND FILLED THE TABLES
            */
            
            // Delete existing table. Comment this line if you have already created and filled it
            query = "drop table image";
            statement = connection.prepareStatement(query);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.                      
            statement.executeUpdate();
			/* END OF DROP TABLE */
			
            query =   "create table image (id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "title varchar (256) NOT NULL, description varchar (1024) NOT NULL, "
                    + "keywords varchar (256) NOT NULL, author varchar (256) NOT NULL, creator varchar (256) NOT NULL, capture_date varchar (10) NOT NULL, "
                    + "storage_date varchar (10) NOT NULL, filename varchar (512) NOT NULL, encrypted int NOT NULL, "
                    + "primary key (id))";

            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            // With preparedStatement, SQL Injection and other problems when inserting values in the database can be avoided
                        
            query = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME, ENCRYPTED) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);            
            statement.setString(1, "test1");
            statement.setString(2, "This is image 1");
            statement.setString(3, "Keyword11, Keyword12");
            statement.setString(4, "Silvia");
            statement.setString(5, "Silvia");
            statement.setString(6, "2020/03/02");
            statement.setString(7, "2021/06/05");
            statement.setString(8, "file1.jpg");    
            statement.setInt(9, 0);
            statement.executeUpdate();      
            
            query = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME, ENCRYPTED) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);            
            statement.setString(1, "test2");
            statement.setString(2, "This is image 2");
            statement.setString(3, "Keyword21, Keyword22");
            statement.setString(4, "Silvia");
            statement.setString(5, "Silvia");
            statement.setString(6, "2020/02/01");
            statement.setString(7, "2021/06/03");
            statement.setString(8, "file2.jpg");    
            statement.setInt(9, 0);
            statement.executeUpdate(); 

            /* 
               END COMMENT 
            */            
            
            // Select information from images and show in the web
            query = "select * from image";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();                                    

            while (rs.next()) {
                // read the result set
                out.println("<br>Id image = " + rs.getString("id"));
                out.println("Titulo = " + rs.getString("title"));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
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
        processRequest(request, response);
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