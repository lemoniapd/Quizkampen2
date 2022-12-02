package Quizkampen.Client.GUI;


import Quizkampen.Client.Client;
import Quizkampen.Server.Questions.Question;
import Quizkampen.Server.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class QuestionMode extends JFrame implements ActionListener {

    Client client;
    Properties p = new Properties();
    List<Question> questionsList;
    int questionIndex;
    int amountOfQuestions;
    protected char answerSelected;
    JPanel headPanel = new JPanel();
    JPanel titlePanel = new JPanel();
    JLabel player1 = new JLabel(/*getPlayerName*/"Spelare 1");
    JLabel player2 = new JLabel(/*getPlayerName*/"Spelare 2");
    JLabel category = new JLabel("");
    JLabel question = new JLabel();
    JButton answer1 = new JButton();
    JButton answer2 = new JButton();
    JButton answer3 = new JButton();
    JButton answer4 = new JButton();
    JPanel answerPanel = new JPanel();

    public QuestionMode(List<Question> q, int questionIndex, Client client) {
        this.client = client;
        this.questionsList = q;
        this.questionIndex = questionIndex;
        try {
            p.load(new FileInputStream("src/Quizkampen/GameSettings.properties"));
            amountOfQuestions = Integer.parseInt(p.getProperty("questionsPerRound", "2"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Question question = questionsList.get(questionIndex);

        this.question.setText(question.getQuestion());
        this.category.setText(question.getCategory());
        this.answer1.setText(question.getOptions()[0]);
        this.answer2.setText(question.getOptions()[1]);
        this.answer3.setText(question.getOptions()[2]);
        this.answer4.setText(question.getOptions()[3]);

        headPanel.setLayout(new BorderLayout());
        titlePanel.setLayout(new GridLayout(1, 3));
        answerPanel.setLayout(new GridLayout(2, 2));

        titlePanel.add(player1);
        titlePanel.add(category);
        titlePanel.add(player2);

        answerPanel.add(answer1);
        answerPanel.add(answer2);
        answerPanel.add(answer3);
        answerPanel.add(answer4);

        headPanel.add(titlePanel, BorderLayout.NORTH);
        headPanel.add(this.question, BorderLayout.CENTER);
        headPanel.add(answerPanel, BorderLayout.SOUTH);

        add(headPanel);
        answer1.setName("A");
        answer2.setName("B");
        answer3.setName("C");
        answer4.setName("D");
        answer1.setOpaque(true);
        answer2.setOpaque(true);
        answer3.setOpaque(true);
        answer4.setOpaque(true);
        answer1.setBackground(Color.GRAY);
        answer2.setBackground(Color.GRAY);
        answer3.setBackground(Color.GRAY);
        answer4.setBackground(Color.GRAY);
        answer1.addActionListener(this);
        answer2.addActionListener(this);
        answer3.addActionListener(this);
        answer4.addActionListener(this);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public int getAnswerSelected() {
        return answerSelected;
    }

    public void setAnswerSelected(char answerSelected) {
        this.answerSelected = answerSelected;
    }

    public void answerCorrector(JButton btn) {
        char answer = btn.getName().charAt(0);
        if (answer == questionsList.get(questionIndex).getRightAnswer()) {
            btn.setBackground(Color.GREEN);
            client.addRoundData(true);
            //skicka poäng till server
        } else {
            btn.setBackground(Color.RED);
            client.addRoundData(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ActionListener timerListener = evt -> {
            setVisible(false);
            if (questionIndex == amountOfQuestions-1) {
                client.sendRoundDone();
                System.out.println("done with questions");
                if (client.currentRound < 2) {
                    try {
                        client.sendData(new Response("välj kategori", "2"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {new ScoreBoard(client); }
            } else {
                new QuestionMode(questionsList, ++questionIndex, this.client);
            }
        };

        Timer timer = new Timer(1000, timerListener);
        timer.setRepeats(false);

        answerCorrector((JButton) e.getSource());
        timer.start();
    }
}
