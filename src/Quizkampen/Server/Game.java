package Quizkampen.Server;

import Quizkampen.Server.Questions.Qdatabase;

import java.net.Socket;
import java.util.Properties;

public class Game extends Thread {

    Properties properties = new Properties();
    Qdatabase qDatabase = new Qdatabase();
    QuizProtocol quizProtocol = new QuizProtocol();

    public Game (Socket socket1, Socket socket2){

    }

    @Override
    public void run(){

    }
}
