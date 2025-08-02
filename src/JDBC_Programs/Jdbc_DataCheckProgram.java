package JDBC_Programs;



import java.sql.*;

public class Jdbc_DataCheckProgram {
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

            String user = "CREATE TABLE IF NOT EXISTS user(user_id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), email VARCHAR(150), phone INT)";
            st.executeUpdate(user);

            String ticket = "CREATE TABLE IF NOT EXISTS ticket(ticket_id INT AUTO_INCREMENT PRIMARY KEY, delivery_method VARCHAR(50))";
            st.executeUpdate(ticket);

            String pointsTransaction = "CREATE TABLE IF NOT EXISTS pointstransaction(transaction_id INT AUTO_INCREMENT PRIMARY KEY, amount DECIMAL(10,2), transaction_type VARCHAR(20), user_id INT, FOREIGN KEY (user_id) REFERENCES user(user_id))";
            st.executeUpdate(pointsTransaction);

            String insertUser = "INSERT INTO user(name,email,phone) VALUES(?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(insertUser)) {
                ps.setString(1, "Name1");
                ps.setString(2, "email1@example.com");
                ps.setInt(3, 12345);
                ps.addBatch();

                ps.setString(1, "Name2");
                ps.setString(2, "email2@example.com");
                ps.setInt(3, 23456);
                ps.addBatch();

                ps.setString(1, "Name3");
                ps.setString(2, "email3@example.com");
                ps.setInt(3, 34567);
                ps.addBatch();

                ps.setString(1, "Name4");
                ps.setString(2, "email4@example.com");
                ps.setInt(3, 45678);
                ps.addBatch();

                int[] arr = ps.executeBatch();
                System.out.printf("Inserted rows: %d\n", (int) java.util.stream.IntStream.of(arr).filter(c -> c > 0).count());
            }

            String query1 = "SELECT * FROM user";
            try (ResultSet rs = st.executeQuery(query1)) {
                while (rs.next()) {
                    String name = rs.getString(2);
                    String email = rs.getString(3);
                    int phone = rs.getInt(4);
                    System.out.println("Name: " + name + ", Email: " + email + ", Phone: " + phone);
                }
            }

            String insertTicket = "INSERT INTO ticket(delivery_method) VALUES (?)";
            try (PreparedStatement ps = con.prepareStatement(insertTicket)) {
                ps.setString(1, "Method1");
                ps.addBatch();

                ps.setString(1, "Method2");
                ps.addBatch();

                ps.setString(1, "Method3");
                ps.addBatch();

                ps.executeBatch();
            }

            String query2 = "SELECT * FROM ticket";
            try (ResultSet rs = st.executeQuery(query2)) {
                while (rs.next()) {
                    String method = rs.getString("delivery_method");
                    System.out.println("Ticket Delivery Method: " + method);
                }
            }

            String insertTransaction = "INSERT INTO pointstransaction(amount, transaction_type, user_id) VALUES (?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(insertTransaction)) {
                ps.setBigDecimal(1, new java.math.BigDecimal("150.75"));
                ps.setString(2, "Credit");
                ps.setInt(3, 1);
                ps.addBatch();

                ps.setBigDecimal(1, new java.math.BigDecimal("50.25"));
                ps.setString(2, "Debit");
                ps.setInt(3, 2);
                ps.addBatch();

                ps.executeBatch();
            }

            String query = "SELECT u.name, SUM(pt.amount) AS total_amount FROM user u JOIN pointstransaction pt ON u.user_id = pt.user_id GROUP BY u.name";
            try (ResultSet rs = st.executeQuery(query)) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    double totalAmount = rs.getDouble("total_amount");
                    System.out.println("Name: " + name + ", Total Amount: " + totalAmount);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


