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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Lubna
 */
@Path("userCon")
public class userController {

    User u;

    @GET
    @Path("/allRegister")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<User> allRegisteredUsers() throws ClassNotFoundException, SQLException {
        ArrayList<User> tmp = new ArrayList();
        
        String query = "select * from info";
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/Shopping", "Lubna", "1234");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            User m = new User() ;
            m.setUserName(rs.getString("name"));
            m.setEmail(rs.getString("email"));
            //m.setPassword(rs.getString("password"));
            //m.setType(rs.getString("type"));
            tmp.add(m);
        }
        return tmp;
    }
//(String email,String username, String password, String type)
//(@PathParam ("email")String email, @PathParam ("username")String username, @PathParam ("password")String password, @PathParam ("type")String type)

    @GET
    @Path("/Register/{username}/{email}/{password}/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@PathParam("username") String username, @PathParam("email") String email, @PathParam("password") String password, @PathParam("type") String type) throws ClassNotFoundException, SQLException {
        ArrayList<User> UN = new ArrayList();
        ArrayList<User> EM = new ArrayList();
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/Shopping", "Lubna", "1234");
        
        /*if (email == null || username == null || password == null || type == null) {
            System.out.println("Incomplete Data");
            return;jdbc:derby://localhost:1527/Shopping [Lubna on LUBNA]
        }*/
       // String query1 = "select username from UserInfo where UserInfo.username = '" + username + "'";
       String query1="select name from info where info.name = '"+username+"' ";
        //con.PreparedStatement.execute(query1);
        Statement stI = con.createStatement();
       ResultSet  rsI = stI.executeQuery(query1);
        //get all user name and put it in the array list of user.
        while (rsI.next()) {
            User U = new User();
            U.setUserName(rsI.getString("name"));
            //u.setEmail(rsI.getString("email"));
            //m.setPassword(rs.getString("password"));
            //m.setType(rs.getString("type"));
            UN.add(U);
        }
       if (UN.size() != 0) 
       {
           return"Invalid User Name";
       }      
        
       String query2 = "select email from info where info.email = '" + email + "'";
        rsI = stI.executeQuery(query2);
        while (rsI.next()) {
            User U = new User();
            // u.setUserName(rsI.getString("userName"));
            U.setEmail(rsI.getString("email"));
            //m.setPassword(rs.getString("password"));
            //m.setType(rs.getString("type"));
            EM.add(U);
        }
        if (UN.size() != 0) {
           
            return "Invalid email";
        }
        if (type.equals("Buyer")) {
            u = new Buyer();
        } else if (type.equals("storeOwner")) {
            u = new StoreOwner();
        } else if (type.equals("Administrator")) {
            u = new Administrator();
        }
        u.setUserName(username);
        u.setEmail(email);
        u.setPassword(password);
        String Query ="insert into info(name,email,password,type) values (?,?,?,?)";
        //"insert into USERINFO values (' " + username + " ',' " + email + " ',' " + password + " ',' " + type + " ')"
        PreparedStatement s = con.prepareStatement(Query);
        s.setString(1, username);
        s.setString(2, email);
        s.setString(3, password);
        s.setString(4, type);
        s.executeUpdate();
        //stI= con.createStatement();
        //rsI= stI.executeQuery(Query);
       // return UN;
       return "Done";
    }
}
