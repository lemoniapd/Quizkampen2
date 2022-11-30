package Quizkampen.Client;

import Quizkampen.Client.GUI.CategoryPick;
import Quizkampen.Client.GUI.Home;
import Quizkampen.Client.GUI.QuestionMode;
import Quizkampen.Client.GUI.ScoreBoard;
import Quizkampen.Server.Questions.Question;
import Quizkampen.Server.Response;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Serializable{
    private int port = 8888;
    Response fromServer;
    ObjectOutputStream out;

    public Client(InetAddress ip) throws IOException {

        try (Socket socket = new Socket(ip, port);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(new Response("starta spel"));
            System.out.println("client first obj sent");

            while ((fromServer = (Response) in.readObject()) != null) {
                System.out.println(fromServer);
                if (fromServer.getOperation().equalsIgnoreCase("Starta spel")) {
                    // Enter data using BufferReader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String playerName = reader.readLine();
                    new Home(playerName, this);
                    //sendData(playerName);
                } else if (fromServer.getOperation().equalsIgnoreCase("continue to categories")) {
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

    public static void main(String[] args) throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        new Client(ip);
    }
}
