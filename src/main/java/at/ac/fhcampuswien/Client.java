package at.ac.fhcampuswien;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {


        try {
            Socket socket = new Socket("localhost", 6666);
            System.out.println("Connected.");


            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            System.out.println("Write a message and press Enter to send: ");

//            Threads x = new Threads();
//            x.run();


            while (true) {

                Scanner scanner = new Scanner(System.in);
                String clientInput = scanner.nextLine(); //scanns for User (Server) input

                String string = clientInput;
                outputStream.writeUTF(string);
                outputStream.flush();

                String message = (String) inputStream.readUTF();
                System.out.println(message);
            }

        } catch (
                IOException e) {
            System.out.println("Error 1");
        }
    }
}
