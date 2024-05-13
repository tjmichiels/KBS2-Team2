import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scherm extends JFrame {
    private int breedte = 800;
    private int hoogte = 800;


    public Scherm() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(breedte, hoogte);
        JDBC route = new JDBC("jdbc:mysql://localhost:3306/routebepaling", "root", "");
        Inlog inlog = new Inlog(this);
        if (inlog.isLoginSuccessful()) {
            PanelRoute p = new PanelRoute();
            add(p);
            setVisible(true);
        } else {
            System.exit(0);
        }
    }
}
