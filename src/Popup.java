
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Popup extends JDialog implements ActionListener {
    private JButton confirm = new JButton("CONFIRM");
    private JTextField jtfield = new JTextField();
    private JButton cancel = new JButton("CANCEL");
    private JLabel pakketl = new JLabel("Pakket:--");
    private int aantalpakket;
    private int knop;

    public Popup(int knop, boolean modal) {
        super();
        this.knop = knop;
        setSize(400, 600);
        if (modal) {
            setModalityType(ModalityType.APPLICATION_MODAL);
        }
        if(knop==3){
            setLayout(new GridLayout(2, 2));
            setTitle("Bezorging bevestigen");
            add(new JLabel("Welke order wil je bevestigen:"));
            add(jtfield);
            add(cancel);
            add(confirm);
        }
        if(knop==2){
            setLayout(new GridLayout(2, 2));
            setTitle("Opmerking maken");
            add(new JLabel("Wat is je opmerking:"));
            add(jtfield);
            add(cancel);
            add(confirm);
        }
        if(knop==1){
            aantalpakket = 5;
            setLayout(new GridLayout(aantalpakket+2, 1));
            setTitle("Paketten");
            for (int i = 0; i < aantalpakket; i++) {
                JLabel pakketLabel = new JLabel("Pakket " + (i + 1));
                add(pakketLabel);
            }
            add(cancel);
            add(confirm);
        }
        cancel.addActionListener(this);
        confirm.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==confirm){
            if(knop==1){
//Pakketten

            }
            if(knop==2){
//Opmerking

            }
            if(knop==3){
//Bevestiging

            }if(knop==4){

            }if(knop==5){

            }


            dispose();
        }
        if(e.getSource()==cancel){
            dispose();
        }
    }
}
