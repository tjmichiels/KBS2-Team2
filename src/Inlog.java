import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inlog extends JDialog implements ActionListener {

private JLabel jluser = new JLabel("Username:");
private JTextField jtuser = new JTextField(8);
private JLabel jlww = new JLabel("Wachtwoord:");
private JTextField jtww = new JTextField(8);
private JButton login = new JButton("LOGIN");
private JButton cancel = new JButton("CANCEL");
private JDBC dbconn;
public boolean connectie;
private String username = "";
private boolean loginSuccessful = false;
    public Inlog(JFrame parent, String databasenaam) {
        super(parent, "Inloggen", true);
        String databaseurl = "jdbc:mysql://localhost:3306/"+databasenaam;
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
            System.out.println("Login connected to database");
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the login database.");
        }
        setSize(400, 600);
        setLayout(new GridLayout(3, 2));
        add(jluser);
        add(jtuser);
        add(jlww);
        add(jtww);
        add(cancel);
        add(login);
        login.addActionListener(this);
        cancel.addActionListener(this);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                System.exit(0);
            }
        });
    }
    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==login){
            String enteredUsername = jtuser.getText();
            String enteredPassword = jtww.getText();
            if (!enteredUsername.isEmpty() && !enteredPassword.isEmpty()) {
                try {
                    String query = "SELECT * FROM `routebepaling`.`User` WHERE `naam` = ? AND `wachtwoord` = ?";
                    ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query, enteredUsername, enteredPassword);
                    if (rs != null && rs.next()) {
                        loginSuccessful = true;
                        username = rs.getString("naam");
                        System.out.println("Welkom: "+username);
                        dbconn.closeConnection();
                        System.out.println("Login closed connection");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if (rs != null) rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancel) {
            dbconn.closeConnection();
            System.exit(0);
        }
    }
}
