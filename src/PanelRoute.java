import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;


public class PanelRoute extends JPanel implements ActionListener {
    private JLabel aap = new JLabel("Aap");
    private int breedte = 800;
    private int hoogte = 800;

    private int cirkelWH = breedte / 12;
    private int menulineY = (int) (getHoogte() * 0.1);
    private int profileY = (int) (getHoogte() * 0.035);
    private JButton menu;
    private JPanel menu1;
    private boolean drawSquare = false;
    private ArrayList<JButton> menuButtons = new ArrayList<>();

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PanelRoute(String databasenaam) {
        String databaseurl = "jdbc:mysql://localhost:3306/"+databasenaam;
        dbnaam = databasenaam;
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
            System.out.println("Panel connected to database");
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the panel database.");
        }
        setSize(breedte, getHoogte());
        menu = new JButton("Menu");
        this.setBackground(Color.white);
        add(menu);
        addMenuButton("Pakketten");
        addMenuButton("Opmerking");
        addMenuButton("Bevestigen");
        addMenuButton("Uitloggen");
        setVisible(true);
        menu.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                System.exit(0);
            }
        });
    }
    public PanelRoute(String databasenaam, boolean manager) {
        String databaseurl = "jdbc:mysql://localhost:3306/"+databasenaam;
        dbnaam = databasenaam;
        try {
            dbconn = new JDBC(databaseurl, "root", "");
            connectie = true;
            System.out.println("Panel connected to database");
        } catch (Exception e) {
            connectie = false;
            System.out.println("Failed to connect to the panel database.");
        }
        setSize(breedte, getHoogte());
        menu = new JButton("Menu");
        this.setBackground(Color.white);
        add(menu);
        addMenuButton("Pakketten");
        addMenuButton("Opmerking");
        addMenuButton("Bevestigen");
        addMenuButton("Registreren");
        addMenuButton("Uitloggen");
        setVisible(true);
        menu.addActionListener(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbconn.closeConnection();
                System.out.println("Database connection closed");
                System.exit(0);
            }
        });
    }
    public void addMenuButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        menuButtons.add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval((int) (getBreedte() * 0.9), (int) ((getHoogte() * 0.02)-4), cirkelWH, cirkelWH);
        g.fillRect(0, (int) (getHoogte() * 0.1), getBreedte(), hoogte / 300);

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menu){
            drawSquare = !drawSquare;
            repaint();
        } else if (menuButtons.contains(e.getSource())) {
            String buttonText = ((JButton)e.getSource()).getText();
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
            }
            else if (buttonText.equals("Registreren")) {
                Popup p = new Popup(4, true, dbnaam);
                p.setUsername(username);
                p.setVisible(true);
            }
            else if (buttonText.equals("Uitloggen")) {
                dbconn.closeConnection();
                System.out.println("Panel closed connection");
                System.exit(0);
            }
        }
    }
}

