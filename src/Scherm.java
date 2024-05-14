import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scherm extends JFrame {
    private int breedte = 800;
    private int hoogte = 800;
    private String databaseurl;
    private boolean connectie;

    public void setDatabaseurl(String databaseurl) {
        this.databaseurl = databaseurl;
    }

    public Scherm(String databasenaam) throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(breedte, hoogte);
        databaseurl = "jdbc:mysql://localhost:3306/"+databasenaam;
        try {
            JDBC dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the database.");
        }
        Inlog inlog = new Inlog(this);
        if (inlog.isLoginSuccessful()) {
            PanelRoute p = new PanelRoute();
            p.connectie = connectie;
            add(p);
            setVisible(true);
        } else {
            System.exit(0);
        }
    }
}
