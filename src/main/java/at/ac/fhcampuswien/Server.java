package at.ac.fhcampuswien;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6666); // defines Port of ServerSocket
            System.out.println("Ready for connection");
            Socket socket = serverSocket.accept(); // listens to ServerSocket Port (6666) and connects

            System.out.println("Client connected successfully.");
            System.out.println("Type your messages and press enter to send: ");

            new ReceiveMessageThread(socket, Server.class.getSimpleName()).start(); //starts ReceiveMessageThread

            Scanner scanner = new Scanner(System.in);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            try {
                String messageToSend = scanner.nextLine();
                do {
                    outputStream.writeUTF(messageToSend);
                    outputStream.flush();
                    messageToSend = scanner.nextLine();

                } while (true);

            } finally {
                outputStream.close();
            }

        } catch (
                IOException e) {
            System.out.println("Error 1");
        }
    }

}
