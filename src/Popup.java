import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Popup extends JDialog implements ActionListener {
    private JButton confirm = new JButton("CONFIRM");
    private JTextField jtfield = new JTextField();
    private JTextField jtinput = new JTextField();
    private JButton cancel = new JButton("CANCEL");
    private JLabel pakketl = new JLabel("Pakket:--");
    private JLabel error = new JLabel();
    private JLabel filler = new JLabel();
    private JTextField jtww = new JTextField();
    private int aantalpakket;
    private int knop;
    private JDBC dbconn;
    private boolean connectie;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Popup(int knop, boolean modal, String databasenaam) {
        super();
        this.knop = knop;
        String databaseurl = "jdbc:mysql://localhost:3306/"+databasenaam;
        setSize(400, 600);
        if (modal) {
            setModalityType(ModalityType.APPLICATION_MODAL);
        }

        try {
            dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
            System.out.println("Popup connected to database");
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the panel database.");
        }
        if(knop==3){
            setLayout(new GridLayout(3, 2));
            setTitle("Bezorging bevestigen");
            add(new JLabel("Welke order wil je bevestigen:"));
            add(jtfield);
            add(error);
            add(filler);
            add(cancel);
            add(confirm);
        }
        if(knop==2){
            setLayout(new GridLayout(4, 2));
            setTitle("Opmerking maken");
            add(new JLabel("Over welk pakket gaat het:"));
            add(jtfield);
            add(new JLabel("Wat is je opmerking:"));
            add(jtinput);
            add(error);
            add(filler);
            add(cancel);
            add(confirm);
        }
        if(knop==1){
            aantalpakket = 5;
            setLayout(new GridLayout(aantalpakket+2, 2));
            setTitle("Paketten");
            for (int i = 0; i < aantalpakket; i++) {
                JLabel pakketLabel = new JLabel("Pakket " + (i + 1));
                add(pakketLabel);
            }
            add(error);
            add(filler);
            add(cancel);
            add(confirm);
        }
        if(knop==4){
            setLayout(new GridLayout(5, 2));
            setTitle("Registratie");
            add(new JLabel("Wie komt erbij:"));
            add(jtfield);
            add(new JLabel("Wat wordt je wachtwoord"));
            add(jtww);
            add(new JLabel("Welke rol::"));
            add(jtinput);
            add(error);
            add(filler);
            add(cancel);
            add(confirm);
        }
        cancel.addActionListener(this);
        confirm.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                dispose();
            }
        });
    }
    public static boolean onlyDigits(String str, int n) {
        for (int i = 0; i < n; i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    private void opmerkingMaken(){

    }
    private void orderBevestigen(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==confirm){
            if(knop==1){
                //Pakketten
                dispose();
            }
            if(knop==2){
                //Opmerking
                if (onlyDigits(jtfield.getText(), jtfield.getText().length())) {
                    String opmerking = jtinput.getText();
                    String query = "INSERT INTO opmerkingen (opmerking, order_id, bezorger) VALUES (?, ?, ?)";
                    String query2 = "SELECT COUNT(*) AS OrderAantal FROM `order`";
                    try {
                        if (countOrders(query2)) return;
                        JDBC.executeSQL(dbconn.getConn(), query, opmerking, jtfield.getText(), username);
                        String selectQuery = "SELECT * FROM opmerkingen WHERE bezorger = ?";
                        ResultSet rs = JDBC.executeSQL(dbconn.getConn(), selectQuery, username);
                        while (rs.next()) {
                            String id = rs.getString("id");
                            String merk = rs.getString("opmerking");
                            String usr = rs.getString("bezorger");
                            System.out.println("ID: " + id + ", Opmerking: " + merk+", Bezorger: "+usr);
                        }
                        rs.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                } else {
                    error.setText("Voer een getal in als order nr.");
                }
            }
            if(knop==3){
                //Bevestiging
                if (onlyDigits(jtfield.getText(), jtfield.getText().length())) {
                    int bevestiging = Integer.parseInt(jtfield.getText());
                    String query = "SELECT COUNT(*) AS OrderAantal FROM `order`";
                    String query2 = "UPDATE `order` SET delivered = 'YES' WHERE order_id = ?";
                    String query3 = "SELECT * FROM `order` WHERE order_id = ?";

                    try {
                        if (countOrders(query)) return;
                        JDBC.executeSQL(dbconn.getConn(), query2, String.valueOf(bevestiging));
                        ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query3, String.valueOf(bevestiging));
                        while (rs.next()) {
                            String orderId = rs.getString("order_id");
                            String delivered = rs.getString("delivered");
                            System.out.println("Order ID: " + orderId + ", Bezorgd: " + delivered);
                        }
                        rs.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                } else {
                    error.setText("Please enter a valid order number.");
                }

            }if(knop==4){
//                Registratie
                String naam = jtfield.getText();
                String rol = jtinput.getText();
                String ww = jtww.getText();
                rol.toLowerCase();
                System.out.println(rol);
                if(!naam.isEmpty() && !ww.isEmpty()){
                    if(rol.equals("bezorger")||rol.equals("manager")){
                        String query = "INSERT INTO User (naam, wachtwoord, rol) VALUES (?, ?, ?);";
                        String query2 = "SELECT * FROM User WHERE naam = ? AND rol = ?;";
                        try {
                            JDBC.executeSQL(dbconn.getConn(), query, naam, ww, rol);
                            ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query2, naam, rol);
                            while (rs.next()) {
                                naam = rs.getString("naam");
                                rol = rs.getString("rol");
                                System.out.println("Naam: " + naam + ", rol: " + rol);
                            }
                            rs.close();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                    } else {
                        error.setText("Voer de juiste rol in");
                    }
                } else {
                    error.setText("Voer een naam en wachtwoord in");
                }
            }if(knop==5){

            }
        }
        if(e.getSource()==cancel){
            dbconn.closeConnection();
            System.out.println("Popup closed connection");
            dispose();
        }
    }

    private boolean countOrders(String query) throws SQLException {
        ResultSet countResult = JDBC.executeSQL(dbconn.getConn(), query);
        int orderaant = 0;
        if (countResult.next()) {
            orderaant = countResult.getInt("OrderAantal");
        }
        int enteredOrder = Integer.parseInt(jtfield.getText());
        if (enteredOrder > orderaant) {
            error.setText("Order number is te hoog.");
            filler.setText("Er zijn maar "+orderaant+" orders");
            return true;
        }
        return false;
    }
}
