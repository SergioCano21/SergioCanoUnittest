package com.mayab.quality.unittest;

import java.sql.*;

import com.mayab.quality.unittest.dao.DAOUser;
import com.mayab.quality.unittest.model.User;
import com.mayab.quality.unittest.service.UserService;
public class App{
    public static void main(String args[]) throws SQLException {
        /*Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3307/calidad2024";
            System.out.println("jdbcurl=" + dbURL);
            String strUserID = "root";
            String strPassword = "123456";
            con=DriverManager.getConnection(dbURL,strUserID,strPassword);
            System.out.println("Connected to the database.");
            Statement stmt=con.createStatement();
            System.out.println("Executing query");
            ResultSet rs=stmt.executeQuery("SELECT 1 FROM dual");
            while(rs.next())
                System.out.println(rs.getInt("1"));
            con.close();
        }catch(Exception e){ System.out.println(e);}
        finally {
            con.close();
        }*/
    	
    	
    	DAOUser dao = new DAOUser();
    	UserService service = new UserService(dao);
    	User user = new User("Nuevo nombre", "correo3@correo.com", "09876543212");
		user.setId(1);
		User newUser = service.updateUser(user);
		System.out.println(newUser.getId());
		System.out.println(newUser.getName());
		System.out.println(newUser.getEmail());
		System.out.println(newUser.getPassword());
    }
}