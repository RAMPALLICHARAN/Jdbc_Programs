package JDBC_Programs;


import java.sql.*;

public class Jdbc_AddDataProgram {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/Students";
        String username = "root";
        String password = "Charan@99666";
        String tableName = "Students";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, username, password);
                 Statement st = con.createStatement()) {

                String tableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                        + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(30) NOT NULL, "
                        + "value INT NOT NULL)";
                st.executeUpdate(tableQuery);

                String insertQuery = "INSERT INTO " + tableName + " (name, value) VALUES (?, ?)";
                try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                    ps.setString(1, "Item1");
                    ps.setInt(2, 100);
                    ps.addBatch();

                    ps.setString(1, "Item2");
                    ps.setInt(2, 200);
                    ps.addBatch();

                    ps.setString(1, "Item3");
                    ps.setInt(2, 300);
                    ps.addBatch();

                    int[] rowsInserted = ps.executeBatch();
                    System.out.println(rowsInserted.length + " rows inserted successfully.");
                }

                String query = "SELECT * FROM " + tableName;
                try (ResultSet rs = st.executeQuery(query)) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        int value = rs.getInt("value");
                        System.out.println("ID: " + id + " | Name: " + name + " | Value: " + value);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


