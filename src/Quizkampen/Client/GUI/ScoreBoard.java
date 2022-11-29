package Quizkampen.Client.GUI;

import Quizkampen.Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoard extends JFrame implements ActionListener {

    Client client;
    JPanel headPanel = new JPanel();
    JLabel title = new JLabel("Poäng");
    JPanel nameInfo = new JPanel();
    JPanel scoreBoard = new JPanel();
    JPanel middlePanel = new JPanel();
    JButton closeButton = new JButton("Stäng");

    public ScoreBoard(Client client) {
        this.client = client;
        headPanel.setLayout(new BorderLayout());
        nameInfo.setLayout(new GridLayout(1, 2)); //namn + poäng (format 0-0) + namn
        scoreBoard.setLayout(new GridLayout(2, 3));
        middlePanel.setLayout(new GridLayout(2, 1));
        middlePanel.add(nameInfo, scoreBoard);
        headPanel.add(title, BorderLayout.NORTH);
        headPanel.add(scoreBoard, BorderLayout.CENTER);
        headPanel.add(closeButton, BorderLayout.SOUTH);
        add(headPanel);
        closeButton.setOpaque(true);
        closeButton.setBackground(Color.GREEN);
        closeButton.addActionListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == closeButton) {
                System.exit(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
