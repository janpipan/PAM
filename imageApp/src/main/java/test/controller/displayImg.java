/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import test.util.Encrypt;
import test.util.PasswordHashing;

/**
 *
 * @author alumne
 */
@WebServlet(name = "displayImg", urlPatterns = {"/displayImg"})
public class displayImg extends HttpServlet {
    
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
        
        //db variables
        Connection connection = null;
        String query = null;
        PreparedStatement statement = null;
        
        // file variables
        String imageName = null;
        Boolean encrypted = false;
        
        //encryption variables
        byte[] keySalt = null;
        byte[] passwordSalt = null;
        byte[] ivBytes = null;
        String keyPassword = null;
        byte[] passwordHash = null;
        
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");
            
            String imageId = request.getParameter("id");
            
            query = "SELECT filename, encrypted FROM Image WHERE Image.id = " + imageId;
            System.out.println(query);
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                imageName = rs.getString("filename");
                encrypted = rs.getBoolean("encrypted");
            }
            
            String imagePath = "/home/alumne/imgs/" + imageName;
            
            statement.close();
            
            // if encrypted check if password is correct
            if (encrypted) {
                query = "SELECT * FROM Encryption WHERE Encryption.Picture_id = " + imageId;
            
                statement = connection.prepareStatement(query);

                rs = statement.executeQuery();
                
                
                //parse encryption params
                while(rs.next()) {
                    keySalt = rs.getBytes("key_salt");
                    passwordSalt = rs.getBytes("password_salt");
                    ivBytes = rs.getBytes("init_vector");
                    passwordHash = rs.getBytes("password_hash");
                }
                statement.close();

                keyPassword = request.getParameter("password");
                
                // check if password hashes match
                byte[] hashedPassword = PasswordHashing.hashPassword(keyPassword, passwordSalt);
                if (Arrays.equals(hashedPassword, passwordHash)){

                    try {
                        SecretKey key = Encrypt.getKeyFromPassword(keyPassword, new String(keySalt));
                        IvParameterSpec iv = new IvParameterSpec(ivBytes);
                        Encrypt.decryptFile(key, iv, new File(imagePath), new File(SAVE_DIR + File.separator + "temp." + getFileExtension(Paths.get(imagePath))));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try (InputStream is = new FileInputStream(SAVE_DIR + File.separator + "temp." + getFileExtension(Paths.get(imagePath))); 
                            OutputStream os = response.getOutputStream()) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer,0, bytesRead);
                        }
                    }
                    // delete temp img
                    /*
                    try {
                        Path path = Paths.get(SAVE_DIR + File.separator + "temp.jpg");
                        Files.delete(path);
                        System.out.println("Temp file deleted succesfully");
                    } catch (IOException e) {
                        System.err.println("Unable to delete the file:" + e.getMessage());
                    }
                    */
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect password");
                }
            } else {
                String contentType = getServletContext().getMimeType(imagePath);
                response.setContentType(contentType);

                try (InputStream is = new FileInputStream(imagePath); 
                        OutputStream os = response.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer,0, bytesRead);
                    }
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
    
    private static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        
        if (dotIndex == -1 || dotIndex == fileName.length() -1){
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }
}
