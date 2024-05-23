import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Scherm extends JFrame {
//    JDBC.executeQuery(dbconn.getConn(),"");
    private int breedte = 800;
    private int hoogte = 1000;
    private String databaseurl;
    private boolean connectie;
    private JDBC dbconn;
    private String usernaam;
    private String rol;

    private PanelRoute p;

    public void setListOfCoordinates(ArrayList<double[]> list) {
        this.p.setListOfCoordinates(list);
        repaint();
    }


    public void setDatabaseurl(String databaseurl) {
        this.databaseurl = databaseurl;
    }
    public void logOut(String username){
        String insert = "UPDATE user\n" +
                "SET ingelogd = 'No'\n" +
                "WHERE naam = ?;";
        try {
            JDBC.executeSQL(dbconn.getConn(), insert, username);
        } catch (SQLException e) {
            dbconn.closeConnection();
            throw new RuntimeException(e);
        }
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
        Tables table = new Tables(databasenaam);
        Inlog inlog = new Inlog(this, databasenaam);
        if (inlog.isLoginSuccessful()) {
            this.usernaam = inlog.getUsername();
            try {
                String query = "SELECT rol FROM User WHERE naam = ?";
                ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query, usernaam);
                while (rs.next()) {
                    rol = rs.getString("rol");
                    System.out.println("Naam: "+ usernaam +", Rol: "+rol);

                }
                rs.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
            String roll = rol.toLowerCase();
            if(roll.equals("manager")){
                p = new PanelRoute(databasenaam, true);
                p.connectie = connectie;
                p.setUsername(inlog.getUsername());
                add(p);
            } else if (roll.equals("bezorger")) {
                p = new PanelRoute(databasenaam);
                p.connectie = connectie;
                p.setUsername(inlog.getUsername());
                add(p);
            }
            setVisible(true);
        } else {
            System.exit(0);
            dbconn.closeConnection();
            logOut(usernaam);
        }

        setTitle("Hallo "+ usernaam);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logOut(usernaam);
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                System.exit(0);
            }
        });
    }
}
