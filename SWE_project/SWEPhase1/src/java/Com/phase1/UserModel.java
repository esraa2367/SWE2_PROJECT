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
public class UserModel 
{
    User u;

    public ArrayList<User> findAll() throws ClassNotFoundException, SQLException {
        ArrayList<User> tmp = new ArrayList();

        String query = "select * from info";
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/Shopping", "Lubna", "1234");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {
            User m = new User();
            m.setUserName(rs.getString("name"));
            m.setEmail(rs.getString("email"));
            //m.setPassword(rs.getString("password"));
            //m.setType(rs.getString("type"));
            tmp.add(m);
        }
        return tmp;
    }

    public boolean Exist(@PathParam("email") String email, @PathParam("userName") String userName) throws SQLException, ClassNotFoundException {
        ArrayList<User> UN = new ArrayList();
        ArrayList<User> EM = new ArrayList();
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/Shopping", "Lubna", "1234");
        String query1 = "select name from info where info.name = '" + userName + "' ";
        Statement stI = con.createStatement();
        ResultSet rsI = stI.executeQuery(query1);
        //get all user name and put it in the array list of user.
        while (rsI.next()) {
            User U = new User();
            U.setUserName(rsI.getString("name"));
            UN.add(U);
        }
        //check there exist any matching user name.
        if (UN.size() != 0) {
            return true;
        }

        String query2 = "select email from info where info.email = '" + email + "'";
        rsI = stI.executeQuery(query2);
        while (rsI.next()) {
            User U = new User();
            U.setEmail(rsI.getString("email"));
            EM.add(U);
        }
        //check there exist any matching email.
        if (UN.size() != 0) {

            return true;
        }
        return false;
    }
    public void Add(@PathParam("username") String username, @PathParam("email") String email, @PathParam("password") String password, @PathParam("type") String type) throws SQLException, ClassNotFoundException
    {
        Connection con = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/Shopping", "Lubna", "1234");
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
    }
}
