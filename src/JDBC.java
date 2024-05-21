import java.sql.*;

public class JDBC {
    private String url;
    private String username;
    private String password;
    private Connection conn;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public JDBC(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        try {
            setConn(DriverManager.getConnection(url, username, password));
//            System.out.println("Connected to database");
        }catch (SQLException e){
            System.out.println("Failed to connect to the database.");
            System.out.println("Check the database name to see if you have the correct one");
        }

    }
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
//                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the connection.");
            e.printStackTrace();
        }
    }
    public static ResultSet checkBezorgers(Connection conn) throws SQLException {
        String query = "SELECT naam FROM user WHERE rol = 'bezorger' AND ingelogd = 'Yes'";
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }
    public void voegBezorgerToe() throws SQLException {
        JDBC.executeSQL(getConn(), "INSERT INTO `bezorger` (`naam`, `route_id`)\n" +
                "SELECT DISTINCT u.naam, NULL\n" +
                "FROM `user` u\n" +
                "LEFT JOIN `bezorger` b ON u.naam = b.naam\n" +
                "WHERE u.rol = 'bezorger' AND b.naam IS NULL;");
        closeConnection();
    }
    public static ResultSet executeSQL(Connection conn, String query, String... params) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            pstmt.setString(i + 1, params[i]);
        }

        if (query.trim().toUpperCase().startsWith("SELECT")) {
            return pstmt.executeQuery();
        } else {
            pstmt.executeUpdate();
            return null;
        }
    }
}
