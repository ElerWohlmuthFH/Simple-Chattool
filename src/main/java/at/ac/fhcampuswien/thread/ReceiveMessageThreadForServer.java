package at.ac.fhcampuswien.thread;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveMessageThreadForServer extends Thread {
    public static String message;
    private final Socket socket;
    private final String name;

    public ReceiveMessageThreadForServer(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            DataInputStream inputStream;
            try {
                inputStream = new DataInputStream(socket.getInputStream());
                try {
                    message = inputStream.readUTF();
                    System.out.println(name + ": " + message);

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}