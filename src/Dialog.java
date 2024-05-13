import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialog extends JDialog implements ActionListener {
    private JButton btnCancel;

    public Dialog(Frame owner) {
        super(owner, true);
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));
        setTitle("...");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        add(btnCancel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
}
