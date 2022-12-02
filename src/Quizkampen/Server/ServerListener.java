package Quizkampen.Server;

import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Serializable {

    private int port = 8888;

    public ServerListener() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket player1 = serverSocket.accept();
            Socket player2 = serverSocket.accept();
            Game game = new Game(player1, player2);
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServerListener();
    }
}
