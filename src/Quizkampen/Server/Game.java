package Quizkampen.Server;

import Quizkampen.Server.Questions.Qdatabase;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Game extends Thread {
    Properties properties = new Properties();
    Qdatabase qDatabase = new Qdatabase();
    QuizProtocol quizProtocol = new QuizProtocol();
    private final Socket socket1;
    private final Socket socket2;
    private int amountOfQuestions;
    private int amountOfRounds;

    public Game(Socket socket1, Socket socket2) {
        this.socket1 = socket1;
        this.socket2 = socket2;
        try {
            properties.load(new FileInputStream("src/Quizkampen/GameSettings.properties"));
            amountOfRounds = Integer.parseInt(properties.getProperty("rounds", "2"));
            amountOfQuestions = Integer.parseInt(properties.getProperty("questions", "2"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public String categories() {  //test
        //return categories;
        return "Matematik:Geografi:Svenska seder";
    }

    @Override
    public void run() {
        String inputLine, outputLine;

        try {
            ObjectOutputStream output1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectOutputStream output2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream input1 = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream input2 = new ObjectInputStream(socket2.getInputStream());
            while (true) {
                //inputLine = String.valueOf(input1.readObject());
                //inputLine = String.valueOf(input2.readObject());

                Object o = input1.readObject();
                System.out.println(o.getClass().getSimpleName());

                String protocol = quizProtocol.processInput(String.valueOf(o));
                //if (o instanceof String) {
                if (protocol == "starta spel") {
                    output1.writeObject("continue to categories" + categories());
                    output2.writeObject("Starta spel");
                    //outputLine = quizProtocol.processInput(inputLine);
                    //output1.writeObject(outputLine);
                }
                else if (protocol == "svara på frågor") {

                    if (input1.readObject().toString() == "Math") {
                        output1.writeObject(new Response(qDatabase.getMqList()));
                    }
                    if (input1.readObject().toString() == "Geography") {
                        output1.writeObject(new Response(qDatabase.getGqList()));
                    }
                    if (input1.readObject().toString() == "Swedish") {
                        output1.writeObject(new Response(qDatabase.getSqList()));
                    }

                    //TODO ta emot kategorin
                    //TODO kontroll av kategori
                    //TODO skicka respons
                    output2.writeObject("continue to questions");
                }
                else if (protocol == "visa scoreboard") {
                    output1.writeObject("");
                    output2.writeObject("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
