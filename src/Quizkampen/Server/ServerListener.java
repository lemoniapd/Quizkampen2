package Quizkampen.Server;

import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener implements Serializable {

    private int port = 8888;

    public ServerListener() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket1 = serverSocket.accept();
            Socket socket2 = serverSocket.accept();
            Game game = new Game(socket1, socket2);
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServerListener();
    }
}
