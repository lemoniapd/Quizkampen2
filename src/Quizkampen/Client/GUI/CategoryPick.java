package Quizkampen.Client.GUI;

import Quizkampen.Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryPick extends JFrame implements ActionListener {
    private String[] categories;
    Client client;
    JPanel headPanel = new JPanel();
    JLabel title = new JLabel("Välj kategori: ");
    JButton mathCategory = new JButton();
    JButton geographyCategory = new JButton();
    JButton swedishQuestions = new JButton();

    public CategoryPick(String[] categories, Client client) {
        this.client = client;
        this.categories = categories;
        mathCategory.setText(categories[1]);
        geographyCategory.setText(categories[2]);
        swedishQuestions.setText(categories[3]);

        headPanel.setLayout(new GridLayout(4, 1));
        headPanel.add(title);
        headPanel.add(mathCategory);
        headPanel.add(geographyCategory);
        headPanel.add(swedishQuestions);
        add(headPanel);
        mathCategory.addActionListener(this);
        geographyCategory.addActionListener(this);
        swedishQuestions.addActionListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == mathCategory) {
                setVisible(false);
                client.sendData("Math");
            } else if (e.getSource() == geographyCategory) {
                setVisible(false);
                client.sendData("Geography");
            } else if (e.getSource() == swedishQuestions) {
                setVisible(false);
                client.sendData("Swedish");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
