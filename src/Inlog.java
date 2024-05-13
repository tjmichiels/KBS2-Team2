import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inlog extends JDialog implements ActionListener {

private JLabel jluser = new JLabel("Username:");
private JTextField jtuser = new JTextField(8);
private JLabel jlww = new JLabel("Wachtwoord:");
private JTextField jtww = new JTextField(8);
private JButton login = new JButton("LOGIN");
private JButton cancel = new JButton("CANCEL");
    private boolean loginSuccessful = false;
    public Inlog(JFrame parent) {
        super(parent, "Inloggen", true);
        setSize(400, 600);
        setLayout(new GridLayout(3, 2));
        add(jluser);
        add(jtuser);
        add(jlww);
        add(jtww);
        int keuze;
        add(cancel);
        add(login);
        login.addActionListener(this);
        cancel.addActionListener(this);
        setVisible(true);
    }
    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==login){
            loginSuccessful = true;
            dispose();
        }
        if(e.getSource()==cancel){
            System.exit(0);
        }
    }
}
