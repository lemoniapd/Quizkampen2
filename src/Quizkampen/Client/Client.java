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

public class Client {
    private int port = 44444;
    Object fromServer;
    ObjectOutputStream out;

    public Client(InetAddress ip) {

        try (Socket socket = new Socket(ip, port);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject("starta spel");
            System.out.println("client first obj sent");

            while ((fromServer = in.readObject()) != null) {
                    System.out.println(fromServer);
                if (fromServer instanceof String) {
                    String temp = fromServer.toString();
                    if (temp.startsWith("Starta spel")) {
                        // Enter data using BufferReader
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        String playerName = reader.readLine();
                        new Home(playerName, this);
                        sendData(playerName);
                    } else if (temp.startsWith("continue to categories")) {
                        String[] category1 = temp.split(":");
                        new CategoryPick(category1, this);
                    }else if (temp.startsWith("spelet avslutat")) {
                        new ScoreBoard(this);
                    }
                } else if (fromServer instanceof Response) {
                    Response tempResponse = (Response) fromServer;

                     if (tempResponse.getOperation().equals("QuestionSent")) {
                         new QuestionMode(tempResponse.getqList(), 0, this);

                         /*
                        switch (category) {
                            case "Math" -> new ScoreBoard(this);
                            case "Geography" -> new ScoreBoard(this);
                            case "Swedish" -> new ScoreBoard(this);
                        }
                    } else if (fromServer.startsWith("continue to questions")) {
                        String category = fromServer.split(":")[1];
                        System.out.println(category);

                    switch (category) {
                        case "Math" -> new QuestionMode(qdatabase.getMqResponse(), 0, this);
                        case "Geography" -> new QuestionMode(qdatabase.getGqResponse(), 0, this);
                        case "Swedish" -> new QuestionMode(qdatabase.getSqList(), 0, this);
                    }
                     */

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data) throws IOException {
        out.writeObject(data);
    }

    public static void main(String[] args) throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        new Client(ip);
    }
}
