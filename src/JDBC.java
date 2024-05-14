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
            System.out.println("Connected to database");
        }catch (SQLException e){
            System.out.println("Failed to connect to the database or execute the query.");
            System.out.println("Check the database name to see if you have the correct one");
        }

    }
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the connection.");
            e.printStackTrace();
        }
    }
    public static void executeQuery(Connection conn, String query) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    String variableValue = value.toString();
                    System.out.println(columnName + " = " + variableValue);
                }
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to execute the query.");
            e.printStackTrace();
        }
    }
}
