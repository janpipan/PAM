package test.imageapp.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    
    /**
    * GET method to list images 
    * @return
    */
    @Path("listImages")
    @GET    
    @Produces(MediaType.TEXT_HTML)
    public String listImages () 
    {                              
        return "<html><body>LIST</body></html>";
    }   

    /**
    * GET method to search images 
    * @param id
    * @return
    */
    @Path("searchID/{id}")
    @GET    
    @Produces(MediaType.TEXT_HTML)
    public String searchByID (@PathParam("id") int id) 
    {     
        return "<html><body>SEARCH ID</body></html>";
    }
    
    /**
    * GET method to search images 
    * @param title    
    * @return
    */
    @Path("searchTitle/{title}")
    @GET    
    @Produces(MediaType.TEXT_HTML)
    public String searchByTitle (@PathParam("title") String title) 
    {     
        return "<html><body>SEARCH TITLE</body></html>";
    }    
 
    /**
    * POST method to register a new image
    * @param title
    * @param description
    * @param keywords     
    * @param author
    * @param creator
    * @param capt_date    
    * @return
    */
    @Path("registerImages")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String registerImage (@FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("keywords") String keywords,
            @FormParam("author") String author,
            @FormParam("creator") String creator,
            @FormParam("capture") String capt_date) 
    {       
        
        Connection connection = null;
        String query;
        PreparedStatement statement;
        
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/ImageDB;user=alumne;password=alumne");
            
            query = "INSERT INTO image(Title, Description, Keywords, Author, Creator, CapturingDate) VALUES (?, ?, ?, ?, ?, ?)";
            
            statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, keywords);
            statement.setString(4, author);
            statement.setString(5, creator);
            statement.setDate(6,Date.valueOf(capt_date));
            statement.executeUpdate();  
            
            } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("image metadata added");
        
        return "<html><body>REGISTER</body></html>";
    }  
}
