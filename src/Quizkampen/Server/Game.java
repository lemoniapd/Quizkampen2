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
        Response inputLine1;
        Response inputLine2;
        Response outputLine;

        try {
            ObjectOutputStream output1 = new ObjectOutputStream(socket1.getOutputStream());
            ObjectOutputStream output2 = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream input1 = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream input2 = new ObjectInputStream(socket2.getInputStream());

            while (input1.readObject() != null || input2.readObject() != null) {
                //inputLine1 = ((Response) input1.readObject());
                inputLine2 = ((Response) input2.readObject());
                // System.out.println(inputLine1);
                System.out.println("Klient " + inputLine2.getOperation());
                Response protocol = quizProtocol.processInput(inputLine2);
                if (protocol.getOperation().equalsIgnoreCase("starta spel")) {
                    outputLine = new Response("starta spel");
                    output1.writeObject(outputLine);
                    output1.reset();
                    output2.writeObject(outputLine);
                    output2.reset();
                } else if (protocol.getOperation().equalsIgnoreCase("välj kategori")) {
                    //System.out.println(inputLine2.operation);
                    outputLine = new Response("välj kategori", categories());
                    System.out.println(outputLine.getOperation() + outputLine.getMessage());
                    output2.writeObject(outputLine);
                    output1.reset();
                    output2.reset();
                } else if (protocol.getOperation().equalsIgnoreCase("svara")) {
                    System.out.println("game svara på frågor");
                    //TODO ta emot kategorin
                    //TODO kontroll av kategori
                    if (((Response) input2.readObject()).getMessage().equalsIgnoreCase("Math")) {
                        outputLine = new Response("QuestionSent", qDatabase.getMqList());
                        output1.writeObject(outputLine);
                        output2.writeObject(outputLine);
                    }
                    if (((Response) input2.readObject()).getMessage().equalsIgnoreCase("Geography")) {
                        outputLine = new Response("QuestionSent", qDatabase.getGqList());
                        output1.writeObject(outputLine);
                        output2.writeObject(outputLine);
                    }
                    if (((Response) input2.readObject()).getMessage().equalsIgnoreCase("Swedish")) {
                        outputLine = new Response("QuestionSent", qDatabase.getSqList());
                        output1.writeObject(outputLine);
                        output2.writeObject(outputLine);
                    }
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
