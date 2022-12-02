package Quizkampen.Client;

import Quizkampen.Client.GUI.CategoryPick;
import Quizkampen.Client.GUI.Home;
import Quizkampen.Client.GUI.QuestionMode;
import Quizkampen.Client.GUI.ScoreBoard;
import Quizkampen.Server.Response;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Serializable{
    private int port = 8888;
    Response fromServer;
    ObjectOutputStream out;
    ArrayList<Boolean> round = new ArrayList<Boolean>();
    public int currentRound = 0;

    public Client(InetAddress ip) throws IOException {

        try (Socket socket = new Socket(ip, port);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(new Response("starta spel"));
            System.out.println("client first obj sent");

            while ((fromServer = (Response) in.readObject()) != null) {
                System.out.println(fromServer);
                if (fromServer.getOperation().equalsIgnoreCase("Starta spel")) {
                    // Enter name using BufferReader
                    String playerName = JOptionPane.showInputDialog("Skriv in ditt namn");
                    new Home(playerName, this);
                } else if (fromServer.getOperation().equalsIgnoreCase("continue to categories")) {
                    currentRound++;
                    String[] categories = fromServer.getMessage().split(":");
                    new CategoryPick(categories, this);
                } else if (fromServer.getOperation().equalsIgnoreCase("QuestionSent")) {
                    new QuestionMode(fromServer.getqList(), 0, this);
                } else if (fromServer.getOperation().equalsIgnoreCase("spelet avslutat")) {
                    new ScoreBoard(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(Response data) throws IOException {
        out.writeObject(data);
    }
    public void addRoundData(boolean correctAnswer)  {
        round.add(correctAnswer);
    }
    public void sendRoundDone() {
        try {
            sendData(new Response("Send Points", round));
            round.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        new Client(ip);
    }
}
