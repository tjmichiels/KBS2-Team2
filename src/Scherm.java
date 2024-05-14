import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Scherm extends JFrame {
//    JDBC.executeQuery(dbconn.getConn(),"");
    private int breedte = 800;
    private int hoogte = 800;
    private String databaseurl;
    private boolean connectie;
    private JDBC dbconn;
    private String usrnaam;
    private String rol;

    public void setDatabaseurl(String databaseurl) {
        this.databaseurl = databaseurl;
    }

    public Scherm(String databasenaam) throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(breedte, hoogte);
        databaseurl = "jdbc:mysql://localhost:3306/"+databasenaam;
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
            System.out.println("Frame connected to database");
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the database.");
        }
        Inlog inlog = new Inlog(this, databasenaam);
        if (inlog.isLoginSuccessful()) {
            this.usrnaam = inlog.getUsername();
            try {
                String query = "SELECT rol FROM User WHERE naam = ?";
                ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query, usrnaam);
                while (rs.next()) {
                    rol = rs.getString("rol");
                    System.out.println("Naam: "+usrnaam+", Rol: "+rol);
                }
                rs.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
            if(rol.equals("manager")){
                PanelRoute p = new PanelRoute(databasenaam, true);
                p.connectie = connectie;
                p.setUsername(inlog.getUsername());
                add(p);
            } else if (rol.equals("bezorger")) {
                PanelRoute p = new PanelRoute(databasenaam);
                p.connectie = connectie;
                p.setUsername(inlog.getUsername());
                add(p);
            }
            setVisible(true);
        } else {
            dbconn.closeConnection();
            System.exit(0);
        }

        setTitle("Hallo "+usrnaam);
        Tables table = new Tables(databasenaam);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                System.exit(0);
            }
        });
    }
}
