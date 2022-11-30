package Quizkampen.Client.GUI;

import Quizkampen.Client.Client;
import Quizkampen.Server.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {

    Client client;
    JPanel headPanel = new JPanel();
    JLabel name = new JLabel();
    JButton startGameButton = new JButton("Starta nytt spel");

    public Home(String input, Client client) {
        this.client = client;
        headPanel.setLayout(new GridLayout(2, 1));
        name.setText("Välkommen " + input);
        headPanel.add(name);
        headPanel.add(startGameButton);
        add(headPanel);
        startGameButton.setOpaque(true);
        startGameButton.setBackground(Color.GREEN);
        startGameButton.addActionListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == startGameButton) {
                setVisible(false);
                client.sendData(new Response("continue to categories"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
