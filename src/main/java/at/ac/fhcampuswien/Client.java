package at.ac.fhcampuswien;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {


        try {
            Socket socket = new Socket("localhost", 6666);
            System.out.println("Connected.");


            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            String message = "Hello Gefaehrten";
            outputStream.writeUTF(message);
            outputStream.flush();

            String string = (String) inputStream.readUTF();
            System.out.println(string);

        } catch (IOException e) {
            System.out.println("Error 1");
        }


    }
}
