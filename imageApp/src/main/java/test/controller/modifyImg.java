/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package test.controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
@MultipartConfig(fileSizeThreshold=1024*1024*2,
                 maxFileSize=1024*1024*10,
                 maxRequestSize=1024*1024*50,
                 location="/"
        )
public class modifyImg extends HttpServlet {
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
        // db variables
        Connection connection = null;
        String query;
        PreparedStatement statement;
        
        // file variables
        Part filePart;
        String newFileName, savePath;
        OutputStream out = null;
        InputStream filecontent = null;
        int read;
        
        
        // encryption variables
        byte[] keySalt = null;
        byte[] passwordSalt = null;
        byte[] ivBytes = null;
        byte[] passwordHash = null;
        String keyPassword = null;
        
        try {
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");
            
            
            // get parameters
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String keywords = request.getParameter("keywords");
            String author = request.getParameter("author");
            String creator = request.getParameter("creator");
            filePart = request.getPart("file");
            int imageId = Integer.parseInt(request.getParameter("id"));
            
            if (filePart != null) {
                newFileName = getFileName(filePart);
                String fileName = null;
                
                // get image current metadata
                query = "SELECT * FROM Image WHERE Image.Id = ?" ;
                statement = connection.prepareStatement(query);
                statement.setInt(1,imageId);
                ResultSet rs = statement.executeQuery();
                
                
                String filePath = null;
                while (rs.next()){
                    fileName = rs.getString("filename");
                }
                
                if (fileName != null){
                    filePath = SAVE_DIR + File.separator + fileName;
                    
                    try {
                        Path path = Paths.get(filePath);
                        Files.delete(path);
                        System.out.println("File deleted succesfully");
                    } catch (IOException e) {
                        System.err.println("Unable to delete the file:" + e.getMessage());
                    }
                }
                
                Path p = Paths.get(newFileName);
                newFileName = p.getFileName().toString();
                savePath = SAVE_DIR + File.separator + newFileName;
                
                out = new FileOutputStream(new File(savePath));
                filecontent = filePart.getInputStream();
                    
                
                out = new FileOutputStream(new File(savePath));
                filecontent = filePart.getInputStream();

                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                
                
                
            }
            
            
            
            
            
            
            
            
            
            
            // update image metadata
            query = "UPDATE Image SET Title = ?, Description = ?, Keywords = ?, Author = ?, Creator = ? WHERE id = ?"; 
            
            statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, keywords);
            statement.setString(4,  author);
            statement.setString(5, creator);
            statement.setInt(6, imageId);
            statement.executeUpdate();
            statement.close();
            connection.close();
            
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
    
    private String getFileName(Part part){
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items){
            if (s.trim().startsWith("filename")){
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

}
