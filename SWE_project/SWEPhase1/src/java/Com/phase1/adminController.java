/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Com.phase1;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lubna
 */
@WebService
@Path("adminCon")
public class adminController 
{
    public UserModel m = new UserModel();
    @GET
    @Path("/allRegister")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> allRegisteredUsers() throws ClassNotFoundException, SQLException 
    {
       return m.findAll();
    }
}
