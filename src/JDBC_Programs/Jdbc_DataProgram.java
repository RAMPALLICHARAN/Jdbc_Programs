package JDBC_Programs;


import java.sql.*;

public class Jdbc_DataProgram {
    public static void main(String[] args) {
    	String url = "jdbc:mysql://localhost:3306/Students";
        String username = "root";
        String password = "Charan@99666";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement st = con.createStatement()) {

            String student = "CREATE TABLE IF NOT EXISTS student(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), number INT)";
            st.executeUpdate(student);

            String val = "INSERT INTO student(name,number) VALUES(?,?)";
            try (PreparedStatement ps = con.prepareStatement(val)) {
                ps.setString(1, "Name1");
                ps.setInt(2, 1111);
                ps.addBatch();

                ps.setString(1, "Name2");
                ps.setInt(2, 2222);
                ps.addBatch();

                ps.setString(1, "Name3");
                ps.setInt(2, 3333);
                ps.addBatch();

                ps.setString(1, "Name4");
                ps.setInt(2, 4444);
                ps.addBatch();

                int[] arr = ps.executeBatch();
                System.out.printf("Inserted rows: %d\n", (int) java.util.stream.IntStream.of(arr).filter(c -> c > 0).count());
            }

            String query = "SELECT * FROM student";
            try (ResultSet rs = st.executeQuery(query)) {
                while (rs.next()) {
                    String name = rs.getString(2);
                    int number = rs.getInt(3);
                    System.out.println("Name: " + name + ", Number: " + number);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



