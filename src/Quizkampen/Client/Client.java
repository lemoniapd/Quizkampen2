package Quizkampen.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {

    private int port = 22222;

    public Client(InetAddress ip) {
    }

    public static void main(String[] args) throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        new Client(ip);
    }
}

