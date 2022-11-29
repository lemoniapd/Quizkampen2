package Quizkampen.Server;

import Quizkampen.Server.Questions.Qdatabase;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
public class Game extends Thread {

    Properties properties = new Properties();
    Qdatabase qDatabase = new Qdatabase();
    QuizProtocol quizProtocol = new QuizProtocol();
    private int amountOfQuestions;
    private int amountOfRounds;

    public Game(Socket socket1, Socket socket2) {

        try {
            properties.load(new FileInputStream("src/Quizkampen/GameSettings.properties"));
            amountOfRounds = Integer.parseInt(properties.getProperty("rounds", "2"));
            amountOfQuestions = Integer.parseInt(properties.getProperty("questions", 2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void run() {
        String inputLine, outputLine;
        try {
            inputLine = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputLine = new PrintWriter(socket.getOutputStream(), true);

            outputLine = quizProtocol.processInput(null);


            while (inputLine != null)  {
                outputLine = quizProtocol.processInput(inputLine);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
