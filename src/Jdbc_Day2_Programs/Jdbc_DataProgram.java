package Jdbc_Day2_Programs;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Jdbc_DataProgram {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

   
    	String url = "jdbc:mysql://localhost:3306/School";
        String username = "root";
        String password = "password";
        String query = "SELECT * FROM students"; 

       
        Class.forName("com.mysql.cj.jdbc.Driver");

     
        Connection con = DriverManager.getConnection(url, username, password);

      
        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(query);

      
        while (rs.next()) {
            int id = rs.getInt("id");         
            String name = rs.getString("name"); 
            System.out.println("ID: " + id + " | Name: " + name);
        }

       
        st.close();
        con.close();
    }
}


