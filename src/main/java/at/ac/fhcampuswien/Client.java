package at.ac.fhcampuswien;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 6666);
            System.out.println("Connected.");
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
