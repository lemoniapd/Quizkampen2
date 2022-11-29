package Quizkampen.Client.GUI;

import Quizkampen.Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoard extends JFrame implements ActionListener {

    Client client;
    JPanel headPanel = new JPanel();
    JLabel title = new JLabel("Poängställning");
    JPanel scoreInfo = new JPanel();

    JPanel scoreBoard = new JPanel();
    JPanel middlePanel = new JPanel();
    JButton continueButton = new JButton("Fortsätt");

    public ScoreBoard(Client client) {
        this.client = client;
        headPanel.setLayout(new BorderLayout());
        scoreInfo.setLayout(new GridLayout(1, 3)); //namn + poäng (format 0-0) + namn
        scoreBoard.setLayout(new GridLayout(2, 3));
        middlePanel.setLayout(new GridLayout(2, 1));
        middlePanel.add(scoreInfo, scoreBoard);
        headPanel.add(title, BorderLayout.NORTH);
        headPanel.add(scoreBoard, BorderLayout.CENTER);
        headPanel.add(continueButton, BorderLayout.SOUTH);
        add(headPanel);
        continueButton.setOpaque(true);
        continueButton.setBackground(Color.GREEN);
        continueButton.addActionListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton) {
            setVisible(false);
            client.sendData("continue to questions");
        }
    }
}
