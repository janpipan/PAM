/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package test.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.nio.file.Path;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import test.model.ImageModel;
import test.util.Encrypt;
import test.util.FileUtil;
import test.util.PasswordHashing;

/**
 *
 * @author alumne
 */
@WebServlet(name = "registerImg", urlPatterns = {"/registerImg"})
@MultipartConfig(fileSizeThreshold=1024*1024*2,
                 maxFileSize=1024*1024*10,
                 maxRequestSize=1024*1024*50,
                 location="/"
        )
public class registerImg extends HttpServlet {
    
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
        ServletContext context = getServletContext();
        ImageModel imgModel = (ImageModel) context.getAttribute("imageModel");
        System.out.println(imgModel.retrieveAll());
    }
    

    protected void processPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // db variables
        Connection connection = null;
        String query;
        PreparedStatement statement;
        
        // file variables
        Part filePart;
        String fileName, savePath;
        OutputStream out = null;
        InputStream filecontent = null;
        
        
        // encryption variables
        byte[] keySalt = null;
        byte[] passwordSalt = null;
        byte[] ivBytes = null;
        byte[] passwordHash = null;
        String keyPassword = null;
        
        
        
        int read;
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");

            //System.out.println("connected");
            
            filePart = req.getPart("file");
            fileName = getFileName(filePart);
            
             
            
            Path p = Paths.get(fileName);
            fileName = p.getFileName().toString();
            savePath = SAVE_DIR + File.separator + fileName;
            FileUtil.saveFile(savePath, filePart.getInputStream());
            
            if ("on".equals(req.getParameter("encrypt"))){
                keyPassword = req.getParameter("encryptPassword");
                keySalt = Encrypt.generateSalt();
                fileName = "encrypted-" + fileName;
                String encryptedPath = SAVE_DIR + File.separator + fileName;

                SecretKey key = Encrypt.getKeyFromPassword(keyPassword, new String(keySalt));
                IvParameterSpec iv = Encrypt.generateIv();
                ivBytes = iv.getIV();
                
                Encrypt.encryptFile(key, iv, new File(savePath), new File(encryptedPath));
                //Encrypt.decryptFile(key, iv, new File(encryptedPath), new File(SAVE_DIR + File.separator + "test"));
                
                FileUtil.deleteFile(savePath);
                
            } 
            
     
            query = "INSERT INTO image(Title, Description, Keywords, Author, Creator, CapturingDate, Filename, Encrypted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, (String) req.getParameter("title"));
            statement.setString(2, (String) req.getParameter("description"));
            statement.setString(3, (String) req.getParameter("keywords"));
            statement.setString(4, (String) req.getParameter("author"));
            statement.setString(5, (String) req.getParameter("creator"));
            statement.setDate(6,Date.valueOf(req.getParameter("capturingdate")));
            statement.setString(7, fileName);
            statement.setBoolean(8, "on".equals(req.getParameter("encrypt")));
            statement.executeUpdate();  
            
            //get key that was assigned to object
            ResultSet generatedKeys = statement.getGeneratedKeys();
            
            int imageId = -1;
            
            if (generatedKeys.next()) {
                imageId = generatedKeys.getInt(1);
            }
            // write image metadata
            statement.close();
            // save encryption metadata 
            if ("on".equals(req.getParameter("encrypt"))){
                query = "INSERT INTO encryption(Picture_id, Key_salt, Password_salt, Init_vector, Password_hash) VALUES (?, ?, ?, ?, ?)";
                passwordSalt = Encrypt.generateSalt();
                
                passwordHash = PasswordHashing.hashPassword(keyPassword, passwordSalt);
                
                statement = connection.prepareStatement(query);
                statement.setInt(1, imageId);
                statement.setBytes(2,keySalt);
                statement.setBytes(3, passwordSalt);
                statement.setBytes(4, ivBytes);
                statement.setBytes(5, passwordHash);
                statement.executeUpdate();
                statement.close();
            }
            
            
            connection.close();
            //System.out.println("New image meta data added");
            System.out.println("Image added successfuly");
            
            res.setContentType("text/html;charset=UTF-8");
            try {
                ViewManager.nextView(req, res, "/menu.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                RequestDispatcher dispatcher = req.getRequestDispatcher("/error.jsp");
                if (dispatcher != null) {
                    dispatcher.forward(req,res);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
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
