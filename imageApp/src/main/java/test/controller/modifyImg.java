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
import java.util.Arrays;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import test.entity.Image;
import test.util.Encrypt;
import test.util.FileUtil;
import test.util.PasswordHashing;

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
    // encryption variables
    private static byte[] keySalt = null;
    private static byte[] passwordSalt = null;
    private static byte[] ivBytes = null;
    private static byte[] passwordHash = null;
    private static String keyPassword = null;
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
        ResultSet rs;
        
        Part filePart;
        
        OutputStream out = null;
        InputStream filecontent = null;
        int read;
        
        
        
        
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
            String saveFileName = request.getParameter("oldFileName");
            filePart = request.getPart("file");
            String encryptPasswordCurrent = request.getParameter("encryptPasswordCurrent");
            keyPassword = request.getParameter("encryptPasswordNew");
            int imageId = Integer.parseInt(request.getParameter("id"));
            
            System.out.println("keypassword");
            System.out.println(keyPassword.equals(""));
            System.out.println(keyPassword);
            
            
            // if image was encrypted
            if (!encryptPasswordCurrent.equals("")) {
                
                
                // get encryption metadata
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
                
                
                // check if password hashes match
                byte[] hashedPassword = PasswordHashing.hashPassword(encryptPasswordCurrent, passwordSalt);
                if (!Arrays.equals(hashedPassword, passwordHash)){
                    try {
                        ViewManager.nextView(request, response, "/views/error.jsp");
                    } catch (Exception e) {
                        e.printStackTrace();
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        if (dispatcher != null) {
                            dispatcher.forward(request,response);
                        }
                    }
                    // if encryption password is correct
                } else {
                    // check if user provided new file 
                    if (!getFileName(filePart).equals("")) {
                        
                        if (keyPassword.equals("")){
                            keyPassword = encryptPasswordCurrent;
                            System.out.println(keyPassword);
                            saveFileName = updateImage(filePart, connection, imageId, true, true);
                        } else {
                            saveFileName = updateImage(filePart, connection, imageId, true, false);
                        }
                    // if user did not provide file
                    } else  {
                        // if user provided new file decrypt file and encrypt it with new password
                        if (!keyPassword.equals("")) {
                            SecretKey key;
                            IvParameterSpec iv;
                            
                            key = Encrypt.getKeyFromPassword(encryptPasswordCurrent, new String(keySalt));
                            iv = new IvParameterSpec(ivBytes);
                            
                            // set temp file path
                            String tempFile = SAVE_DIR + File.separator + "temp." + getFileExtension(Paths.get(saveFileName));
                            String saveFilePath = SAVE_DIR + File.separator + saveFileName;
                            
                            // decrypt file
                            Encrypt.decryptFile(key, iv, new File(saveFilePath), new File(tempFile));
                            
                            // delete old encrypted file
                            FileUtil.deleteFile(saveFileName);
                            // set new key
                            key = Encrypt.getKeyFromPassword(keyPassword, new String(keySalt));
                            // encrypt new file 
                            Encrypt.encryptFile(key, iv, new File(tempFile), new File(saveFilePath));

                            // delete temp file
                            FileUtil.deleteFile(tempFile);
                            
                            
                            query = "UPDATE Encryption SET Password_hash = ? WHERE Picture_id = ?";
                            passwordHash = PasswordHashing.hashPassword(keyPassword, passwordSalt);
                            statement = connection.prepareStatement(query);
                            statement.setBytes(1, passwordHash);
                            statement.setInt(2, imageId);


                            statement.executeUpdate();
                            statement.close();
                        }
                    }
                    
                    
                }
            } 
            // update image metadata
            query = "UPDATE Image SET Title = ?, Description = ?, Keywords = ?, Author = ?, Creator = ?, Filename = ? WHERE id = ?"; 

            statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, keywords);
            statement.setString(4,  author);
            statement.setString(5, creator);
            statement.setString(6,saveFileName);
            statement.setInt(7, imageId);
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
    
    // replaces old image, updates encryption parameters and returns new image name
    private String updateImage(Part filePart, Connection connection, int imageId, boolean encryption, boolean oldPassword) {
        String newFileName = getFileName(filePart);
        String fileName = null;
        ResultSet rs;

        // get image current metadata
        try {
            String query = "SELECT * FROM Image WHERE Image.Id = ?" ;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,imageId);
            rs = statement.executeQuery();
            
            //String filePath = null;
            while (rs.next()){
                fileName = rs.getString("filename");
            }
            
            // delete old file 
            if (fileName != null){
                FileUtil.deleteFile(SAVE_DIR + File.separator + fileName);
            }
            
            // save new file 
            Path p = Paths.get(newFileName);
            newFileName = p.getFileName().toString();
            String savePath = SAVE_DIR + File.separator + newFileName;
            FileUtil.saveFile(savePath, filePart.getInputStream());
            
            if (encryption) {
                SecretKey key;
                IvParameterSpec iv;
                if (!oldPassword) {
                    System.out.println("new password");
                    System.out.println(keyPassword);
                    keySalt = Encrypt.generateSalt();
                    passwordSalt = Encrypt.generateSalt();
                    iv = Encrypt.generateIv();
                    ivBytes = iv.getIV();
                } else {
                    iv = new IvParameterSpec(ivBytes);
                }
                newFileName = "encrypted-" + newFileName;
                String encryptedPath = SAVE_DIR + File.separator + newFileName;

                key = Encrypt.getKeyFromPassword(keyPassword, new String(keySalt));
                
                
                Encrypt.encryptFile(key, iv, new File(savePath), new File(encryptedPath));
                
                FileUtil.deleteFile(savePath);
                
                passwordHash = PasswordHashing.hashPassword(keyPassword, passwordSalt);
                
                query = "UPDATE Encryption SET Key_salt = ?, Password_salt = ?, Init_vector = ?, Password_hash = ? WHERE Picture_id = ?";
                
                statement = connection.prepareStatement(query);
                statement.setBytes(1,keySalt);
                statement.setBytes(2, passwordSalt);
                statement.setBytes(3, ivBytes);
                statement.setBytes(4, passwordHash);
                statement.setInt(5, imageId);
                statement.executeUpdate();
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileName;
    }
    
    private static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        
        if (dotIndex == -1 || dotIndex == fileName.length() -1){
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }
    
}
