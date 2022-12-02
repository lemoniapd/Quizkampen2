package Quizkampen.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private Response data;
    ObjectOutputStream output;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
    }

    public Boolean hasData() {
        return data != null;
    }

    public Response readData() {
        Response response = data;
        data = null;
        return response;
    }

    public void sendData(Response data) throws IOException {
        output.writeObject(data);
    }

    @Override
    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            while (socket.isConnected()) {
                data = (Response) input.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
