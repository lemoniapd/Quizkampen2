package Quizkampen.Client.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassicMode extends JFrame implements ActionListener {

    JPanel headPanel = new JPanel();
    JButton randomPlayer = new JButton("Slumpad spelare");
    JButton goBack = new JButton("Gå tillbaka");


    public ClassicMode() {
        headPanel.setLayout(new GridLayout(2, 1));
        headPanel.add(randomPlayer);
        headPanel.add(goBack);
        add(headPanel);
        randomPlayer.setOpaque(true);
        goBack.setOpaque(true);
        randomPlayer.setBackground(Color.WHITE);
        goBack.setBackground(Color.RED);
        randomPlayer.addActionListener(this);
        goBack.addActionListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == randomPlayer) {
            setVisible(false);
            //new CategoryPick();
        } else if (e.getSource() == goBack) {
            setVisible(false);
            //new Home();
        }
    }
}
