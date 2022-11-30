package Quizkampen.Server;

import Quizkampen.Server.Questions.Qdatabase;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Game extends Thread implements Serializable{
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

    public String categories() {
        return "Matematik:Geografi:Svenska seder";
    }


    @Override
    public void run() {
        Response inputLine;
        Response outputLine;

        try {
            ObjectOutputStream output1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectOutputStream output2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream input1 = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream input2 = new ObjectInputStream(socket2.getInputStream());

            while (input1.readObject() != null || input2.readObject() != null) {
                //inputLine = ((Response) input1.readObject());
                inputLine = ((Response) input2.readObject());

                Response protocol = quizProtocol.processInput(inputLine);
                if (protocol.getOperation().equalsIgnoreCase("starta spel")) {
                    output1.writeObject(new Response("continue to categories", categories()));
                    output2.writeObject(new Response("Starta spel"));
                }
                else if (protocol.getOperation().equalsIgnoreCase("svara på frågor")) {

                    if (((Response) input1.readObject()).getMessage().equalsIgnoreCase("Math")) {
                        output1.writeObject(new Response("QuestionSent", qDatabase.getMqList()));
                        output2.writeObject(new Response("QuestionSent", qDatabase.getMqList()));

                    }
                    if (((Response) input1.readObject()).getMessage().equalsIgnoreCase("Geography")) {
                        output1.writeObject(new Response("QuestionSent", qDatabase.getGqList()));
                        output2.writeObject(new Response("QuestionSent", qDatabase.getGqList()));
                    }
                    if (((Response) input1.readObject()).getMessage().equalsIgnoreCase("Swedish")) {
                        output1.writeObject(new Response("QuestionSent", qDatabase.getSqList()));
                        output2.writeObject(new Response("QuestionSent", qDatabase.getSqList()));
                    }

                    //TODO ta emot kategorin
                    //TODO kontroll av kategori
                    //TODO skicka respons
                    output2.writeObject(new Response("continue to questions"));
                } else if (protocol.getOperation().equalsIgnoreCase("visa scoreboard")) {
                    //TODO, kolla båda spelare
                    output1.writeObject(new Response("spelet avslutat"));
                    output2.writeObject(new Response("spelet avslutat"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
