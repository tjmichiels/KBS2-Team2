import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private String dbnaam;
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
        dbnaam = databasenaam;
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
        if(knop==6){
            setLayout(new GridLayout(3, 2));
            setTitle("Bezorger status");
            add(new JLabel("Welke bezorger wil je bekijken:"));
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
            if (knop == 1) {
                setSize(800, 600);
                try {
                    JDBC dbconn = new JDBC(databaseurl, "root", "");
                    String query = "SELECT order_id, naam, postcode, huisnummer FROM test.order JOIN klanten ON test.order.klant_id = klanten.klant_id";
                    ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query);
                    String countQuery = "SELECT COUNT(order_id) AS order_count FROM test.order";
                    ResultSet countResult = JDBC.executeSQL(dbconn.getConn(), countQuery);
                    int aantalpakket = 0;
                    if (countResult.next()) {
                        aantalpakket = countResult.getInt("order_count");
                    }
                    setLayout(new GridLayout(aantalpakket + 2, 1));
                    setTitle("Pakketten");

                    int i = 0;
                    while (rs.next() && i < aantalpakket) {
                        int order_id = rs.getInt("order_id");
                        String naam = rs.getString("naam");
                        String postcode = rs.getString("postcode");
                        int huisnr = rs.getInt("huisnummer");

                        ArrayList<String> adresInfo = PostcodeAPI.getAddressInfo(postcode, huisnr);
                        JLabel pakketLabel = new JLabel("Order ID: " + order_id + ", Naam: " + naam + ", Adres: "
                        + adresInfo.get(2) +" "+ huisnr);
                        add(pakketLabel);
                        i++;
                    }
                    dbconn.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                add(error);
                add(filler);
                add(cancel);
                add(confirm);
            }
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
        if(knop==5){
            setLayout(new GridLayout(4, 2));
            setTitle("Route toewijzen");
            add(new JLabel("Aan welke bezorger wil je de route toewijzen"));
            add(jtfield);
            add(new JLabel("Welke route wil je toewijzen"));
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
    public Popup(boolean modal, String databasenaam) {
        super();
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
                    getParent().repaint();
                    dispose();
                } else {
                    error.setText("Please enter a valid order number.");
                }
            }
            if(knop==4){
//                Registratie
                String naam = jtfield.getText();
                String rol = jtinput.getText();
                String ww = jtww.getText();
                rol.toLowerCase();
                System.out.println(rol);
                if(!naam.isEmpty() && !ww.isEmpty()){
                    if(rol.equals("bezorger")||rol.equals("manager")){
                        String insert = "INSERT INTO User (naam, wachtwoord, rol) VALUES (?, ?, ?)";
                        String bezorg = "INSERT INTO Bezorger (naam) VALUES ()";
                        String select= "SELECT * FROM User WHERE naam = ? AND rol = ?";
                        try {
                            JDBC.executeSQL(dbconn.getConn(), insert, naam, ww, rol);
                            ResultSet rs = JDBC.executeSQL(dbconn.getConn(), select, naam, rol);
                            dbconn.voegBezorgerToe();
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
            }
            if(knop==5){
//                Toewijzen
                String route = jtinput.getText();
                String naam = jtfield.getText();
                if(onlyDigits(route, route.length())){
                    String routeCheckQuery = "SELECT COUNT(*) AS route_count FROM route WHERE id = ?";
                    try {
                        ResultSet resultSet = JDBC.executeSQL(dbconn.getConn(), routeCheckQuery, route);
                        if (resultSet.next() && resultSet.getInt("route_count") > 0) {
                            String updateQuery = "UPDATE bezorger SET route_id = ? WHERE naam = ?";
                            JDBC.executeSQL(dbconn.getConn(), updateQuery, route, naam);
                            dispose();
                        } else {
                            error.setText("Deze route bestaat niet");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    error.setText("Voer een correct route id in.");
                }
            }
            if (knop == 6) {
                // Bekijken
                String bezorger = jtfield.getText();
                String query = "SELECT * FROM bezorger WHERE naam = ?";
                try {
                    ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query, bezorger);
                    if (rs.next() && rs.getObject("route_id") != null) {
                        Popup p = new Popup(true, dbnaam);
                        dispose();
                    } else {
                        error.setText("Bezorger bestaat niet of heeft geen route");
                    }
                    rs.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
