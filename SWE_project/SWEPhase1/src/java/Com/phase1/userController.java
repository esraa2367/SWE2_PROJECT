/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Com.phase1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lubna
 */
@WebService
@Path("userCon")
public class userController 
{
    User u;
    public UserModel m = new UserModel();
   public  static String UName = "";
    /*@GET
    @Path("/allRegister")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> allRegisteredUsers() throws ClassNotFoundException, SQLException 
    {
       return m.findAll();
    }*/
//(String email,String username, String password, String type)
//(@PathParam ("email")String email, @PathParam ("username")String username, @PathParam ("password")String password, @PathParam ("type")String type)

    @GET
    @Path("/Register/{username}/{email}/{password}/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@PathParam("username") String username, @PathParam("email") String email, @PathParam("password") String password, @PathParam("type") String type) throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/Shopping", "Lubna", "1234");
        
        /*if (email == null || username == null || password == null || type == null) 
        {
            return "Incomplete Data";
        }*/
       
        if (m.Exist(email, username)== true) {
           
            return "Invalid";
        }
        else
        {
            m.Add(username, email, password, type);
            return "Done";
        }
       
    }
    @GET
    @Path("/login/{email}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    //public String login(@PathParam("email") String email,@PathParam("password") String password) throws ClassNotFoundException, SQLException 
     public String login(@PathParam("email") String emailOrName,@PathParam("password") String password) throws ClassNotFoundException, SQLException 
    {
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/Shopping", "Lubna", "1234");
        //ArrayList<User> pass = m.getPass(email,email);
        //if (pass.get(0).getPassword().equals(password))
        UName = emailOrName;
        if(m.getPass(emailOrName, emailOrName).get(0).getPassword().equals(password))
        {
            return "Valid";
        }
        else
        {
            return "Invalid";
        }
    }
    @GET
    @Path("/allRegister")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> allRegisteredUsers() throws ClassNotFoundException, SQLException 
    {
        ArrayList<User> empty = new ArrayList<User>();
        String defult = UName;
        if (defult.equals(""))
        {
            return empty;
        }
        if (m.isAdmin(defult,defult) == true)
        {
            return m.findAll();
       }
        
       return empty;
    }
    
}
