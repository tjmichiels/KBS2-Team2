import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


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

    public PanelRoute() {
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
                Popup p = new Popup(1, true);
                p.setVisible(true);
            } else if (buttonText.equals("Opmerking")) {
                Popup p = new Popup(2, true);
                p.setVisible(true);
            } else if (buttonText.equals("Bevestigen")) {
                Popup p = new Popup(3, true);
                p.setVisible(true);
            }
            else if (buttonText.equals("Uitloggen")) {
                System.exit(0);
            }
        }
    }
}

