package at.ac.fhcampuswien;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReceiveMessageThread extends Thread {

    private final Socket socket;
    private final String name;

    public ReceiveMessageThread(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        {

            while (true) {
                DataInputStream inputStream;
                try {
                    inputStream = new DataInputStream(socket.getInputStream());
                    try {
                        String message = inputStream.readUTF();
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
}
