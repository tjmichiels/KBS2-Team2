import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    private JButton initiateDialogButton;
    public Frame() {
        setTitle("KBS Team 2 Routebepaling");
        setSize(600, 425);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        initiateDialogButton = new JButton("dialog openen");
        add(initiateDialogButton);
        initiateDialogButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == initiateDialogButton) {
            Dialog diaog = new Dialog(this);
        }
    }
}
