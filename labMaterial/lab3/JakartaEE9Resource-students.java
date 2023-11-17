package pam.pam1.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
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
        return "<html><body>REGISTER</body></html>";
    }     
}
