package Quizkampen.Client.GUI;

import Quizkampen.Client.Client;
import Quizkampen.Questions.Qdatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryPick extends JFrame implements ActionListener {
    Qdatabase qDatabase = new Qdatabase();
    Client client;
    JPanel headPanel = new JPanel();
    JLabel title = new JLabel("Välj kategori: ");
    JButton mathCategory = new JButton();
    JButton geographyCategory = new JButton();
    JButton swedishQuestions = new JButton();

    public CategoryPick(Client client) {
        this.client = client;
        mathCategory.setText(qDatabase.getMqList().get(0).getCategory());
        geographyCategory.setText(qDatabase.getGqList().get(0).getCategory());
        swedishQuestions.setText(qDatabase.getSqList().get(0).getCategory());

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
    }
}
