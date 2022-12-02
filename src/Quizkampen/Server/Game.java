package Quizkampen.Server;

import Quizkampen.Server.Questions.Qdatabase;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Game extends Thread implements Serializable {
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

            int currentPlayer;


            while (true) {
                if (socket1.getInputStream().available() != 0) {
                    currentPlayer = 1;
                    inputLine = (Response) input1.readObject();
                }
                else if (socket2.getInputStream().available() != 0) {
                    currentPlayer = 2;
                    inputLine = (Response) input2.readObject();
                }
                else {
                    Thread.sleep(500);
                    continue;
                }

                System.out.println(currentPlayer);
                Response protocol = inputLine;
                System.out.println(protocol.getOperation());

                if (protocol.getOperation().equalsIgnoreCase("starta spel")) {
                    outputLine = new Response("starta spel");
                    if (currentPlayer == 1) {
                        output1.writeObject(outputLine);
                    } else {
                        output2.writeObject(outputLine);
                    }
                } else if (protocol.getOperation().equalsIgnoreCase("välj kategori")) {
                    if (currentPlayer != Integer.parseInt(protocol.getMessage())) {
                        continue;
                    }
                    outputLine = new Response("continue to categories", categories());
                    System.out.println("hallå");
                    output1.writeObject(outputLine);

                } else if (protocol.getOperation().equalsIgnoreCase("svara på frågor")) {
                    System.out.println("game svara på frågor");
                    //TODO ta emot kategorin
                    //TODO kontroll av kategori
                    if (protocol.getMessage().equalsIgnoreCase("Math")) {
                        outputLine = new Response("QuestionSent", qDatabase.getMqList());
                        output1.writeObject(outputLine);
                        output2.writeObject(outputLine);
                    }
                    if (protocol.getMessage().equalsIgnoreCase("Geography")) {
                        outputLine = new Response("QuestionSent", qDatabase.getGqList());
                        output1.writeObject(outputLine);
                        output2.writeObject(outputLine);
                    }
                    if (protocol.getMessage().equalsIgnoreCase("Swedish")) {
                        outputLine = new Response("QuestionSent", qDatabase.getSqList());
                        output1.writeObject(outputLine);
                        output2.writeObject(outputLine);
                    }

                    output2.writeObject(new Response("continue to questions"));
                } else if (protocol.getOperation().equalsIgnoreCase("visa scoreboard")) {
                    //TODO, kolla båda spelare
                    outputLine = new Response("spelet avslutat");
                    output1.writeObject(outputLine);
                    output2.writeObject(outputLine);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
