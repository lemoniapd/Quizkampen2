package Quizkampen.Server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port = 22222;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket1 = serverSocket.accept();
            Socket socket2 = serverSocket.accept();
            Game game = new Game(socket1, socket2);
            /*
            player1.setOpponent(player2);
            player2.setOpponent(player1);
            player1.start();
            player2.start();

             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
