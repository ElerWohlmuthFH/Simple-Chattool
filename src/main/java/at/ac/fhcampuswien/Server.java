package at.ac.fhcampuswien;

import javax.sound.midi.SysexMessage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6666); //defines Port of ServerSocket
            System.out.println("Ready for connection");
            Socket socket = serverSocket.accept(); //listens to ServerSocket Port (6666) and connects

            System.out.println("Client connected");
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()); //forwards input on Client

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            System.out.println("Write a message and press Enter to send: ");
            while (true) {
                String message = (String) inputStream.readUTF();
                System.out.println(message);

                Scanner scanner = new Scanner(System.in);
                String serverInput = scanner.nextLine(); //scanns for User (Server) input

                String string = serverInput;
                outputStream.writeUTF(string);
                outputStream.flush();
            }

        } catch (IOException e) {
            System.out.println("Error 2");
        }

    }
}
