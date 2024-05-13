
import java.sql.*;

public class Connection {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
}
