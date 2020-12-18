package at.ac.fhcampuswien;

import javax.sound.midi.SysexMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666); //defines Port of ServerSocket
            System.out.println("Ready for connection");
            Socket socket = serverSocket.accept(); //listens to ServerSocket Port (6666) and connects

            System.out.println("Client connected");
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()); //forwards input on Client

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String message = (String) inputStream.readUTF();

            String string = message;
            outputStream.writeUTF(string);
            outputStream.flush();
            outputStream.close();


        } catch (IOException e) {
            System.out.println("Error 2");
        }

    }
}
