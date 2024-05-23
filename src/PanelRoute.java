import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;


public class PanelRoute extends JPanel implements ActionListener {
    private JLabel aap = new JLabel("Aap");
    private int breedte = 800;
    private int hoogte = 1000;

    private int cirkelWH = breedte / 12;
    private int menulineY = (int) (getHoogte() * 0.1);
    private int menulineY2 = (int) (getHoogte() * 0.8);
    private int profileY = (int) (getHoogte() * 0.035);
    private JButton menu;
    private JButton routeOpvragen;
    private JPanel menu1;
    private boolean drawSquare = false;
    private ArrayList<JButton> menuButtons = new ArrayList<>();
    private ArrayList<String> ingelogdBezorgers = new ArrayList<>();

    public int getBreedte() {
        return breedte;
    }

    public int getHoogte() {
        return hoogte;
    }

    public boolean connectie;
    private JDBC dbconn;
    private String dbnaam;
    private String username;
    private String databaseurl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void logOut(String username) {
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

    public PanelRoute(String databasenaam) {
        databaseurl = "jdbc:mysql://localhost:3306/" + databasenaam;
        dbnaam = databasenaam;
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
            System.out.println("Panel connected to database");
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the panel database.");
        }
        setLayout(null);
        setSize(breedte, getHoogte());
        menu = new JButton("Menu");
        this.setBackground(Color.white);
        add(menu);
        menu.setBounds(getBreedte() / 80, menulineY - 40, 100, 30);
        routeOpvragen = new JButton("Route opvragen");
        routeOpvragen.addActionListener(this);
        add(routeOpvragen);
        routeOpvragen.setBounds((getBreedte() / 2) - (getBreedte() / 8), menulineY2 + 10, getBreedte() / 4, getHoogte() / 20);
        addMenuButton("Pakketten");
        addMenuButton("Opmerking");
        addMenuButton("Bevestigen");
        addMenuButton("Uitloggen");
        setVisible(true);
        menu.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logOut(username);
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                System.exit(0);
            }
        });
    }

    public PanelRoute(String databasenaam, boolean manager) {
        String databaseurl = "jdbc:mysql://localhost:3306/" + databasenaam;
        dbnaam = databasenaam;
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
            System.out.println("Panel (manager) connected to database");
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the panel database.");
        }
        setSize(breedte, getHoogte());
        menu = new JButton("Menu");
        this.setBackground(Color.white);
        add(menu);
        addMenuButton("Pakketten");
        addMenuButton("Bezorger status");
        addMenuButton("Route toewijzen");
        addMenuButton("Registreren");
        addMenuButton("Uitloggen");
        setVisible(true);
        menu.addActionListener(this);
        if (manager) {
            if (manager) {
                printLoggedBezorgers();
            }
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logOut(username);
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                System.exit(0);
            }
        });
    }

    private void printLoggedBezorgers() {
        try {
            ResultSet rs = JDBC.checkBezorgers(dbconn.getConn());
            while (rs.next()) {
                ingelogdBezorgers.add(rs.getString("naam"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMenuButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        menuButtons.add(button);
    }

    private void drawBezorgers(Graphics g) {
        int vierkantBreedte = getBreedte() / 8;
        int vierkantHoogte = getHoogte() / 8;
        int margin = getBreedte() / 40;
        int maxSquaresInColumn = (menulineY2 - menulineY) / (vierkantHoogte + margin);
        int x = getWidth() - vierkantBreedte - margin;
        int y = menulineY;

        for (int i = 0; i < ingelogdBezorgers.size(); i++) {
            g.drawRect(x, y, vierkantBreedte, vierkantHoogte);
            g.drawString(ingelogdBezorgers.get(i), x + 10, y + vierkantHoogte / 2);

            y += vierkantHoogte + margin;
            if ((i + 1) % maxSquaresInColumn == 0) {
                x -= vierkantBreedte + margin;
                y = menulineY;
            }
        }
    }

    private int convertLonToX(double lon) {
        lon -= 3.5;
        lon *= (800 / 3.7);
        return (int) lon;
    }

    private int convertLatToY(double lat) {
        lat -= 50.7;
        lat *= (1000 / 2.7);
        if (lat > 292) {
            lat = 0 + (1000 - lat);
        } else if (lat < 292) {
            lat = 1000 - (lat);
        }
        return (int) lat;
    }

    private void drawCity(Graphics g, String naam, double lat, double lon, int size) {
        if (naam.equals("Groningen")) {
            System.out.println(convertLonToX(lon));
            System.out.println(convertLatToY(lat));
        }
        g.fillOval(convertLonToX(lon), convertLatToY(lat), size, size);
        g.drawString(naam, convertLonToX(lon), convertLatToY(lat));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.blue);
        g.setFont(new Font("default", Font.BOLD, 12));
        int size = 10;
        // een paar standaard steden tekenen
        // coördinaten afkomstig van: https://latitudelongitude.org/nl/
        drawCity(g, "Utrecht",  52.09083,5.12222, size);
        drawCity(g, "Amsterdam",  52.37403,4.88969, size);
        drawCity(g, "Eindhoven",  51.44083,5.47778, size);
        drawCity(g, "Rotterdam",  51.9225, 4.47917,size);
        drawCity(g, "Den Haag",  52.07667, 4.29861,size);
        drawCity(g, "Groningen", 53.21917, 6.56667, size);
        drawCity(g, "Born", 51.03167, 5.80972, size);
        drawCity(g, "Middelburg", 51.5, 3.61389, size);
        drawCity(g, "Alkmaar", 52.63167, 4.74861, size);
        drawCity(g, "Zwolle", 52.5125, 6.09444, size);
        drawCity(g, "Almere", 52.37025, 5.21413, size);

        g.setColor(Color.black);

        g.drawOval((int) (getBreedte() * 0.9), (int) ((getHoogte() * 0.02) - 4), cirkelWH, cirkelWH);
        g.fillRect(0, menulineY, getBreedte(), hoogte / 300);
        g.fillRect(0, menulineY2, getBreedte(), hoogte / 300);

        if (drawSquare) {
            int buttonY = (int) (getHoogte() * 0.1);
            int buttonWidth = getBreedte() / 2;
            for (JButton button : menuButtons) {
                button.setBounds(0, buttonY, buttonWidth, 50);
                add(button);
                buttonY += 50;
            }
        } else {
            for (JButton button : menuButtons) {
                remove(button);
            }
        }
        drawBezorgers(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            drawSquare = !drawSquare;
            repaint();
        } else if (e.getSource() == routeOpvragen) {


        } else if (menuButtons.contains(e.getSource())) {
            String buttonText = ((JButton) e.getSource()).getText();
            if (buttonText.equals("Pakketten")) {
                Popup p = new Popup(1, true, dbnaam);
                p.setUsername(username);
                p.setVisible(true);
            } else if (buttonText.equals("Opmerking")) {
                Popup p = new Popup(2, true, dbnaam);
                p.setUsername(username);
                p.setVisible(true);
            } else if (buttonText.equals("Bevestigen")) {
                Popup p = new Popup(3, true, dbnaam);
                p.setUsername(username);
                p.setVisible(true);
            } else if (buttonText.equals("Registreren")) {
                Popup p = new Popup(4, true, dbnaam);
                p.setUsername(username);
                p.setVisible(true);
            } else if (buttonText.equals("Route toewijzen")) {
                Popup p = new Popup(5, true, dbnaam);
                p.setUsername(username);
                p.setVisible(true);
            } else if (buttonText.equals("Bezorger status")) {
                Popup p = new Popup(6, true, dbnaam);
                p.setUsername(username);
                p.setVisible(true);
            } else if (buttonText.equals("Uitloggen")) {
                logOut(username);
                dbconn.closeConnection();
                System.out.println("Panel closed connection");
                System.exit(0);
            }
        }
    }
}

