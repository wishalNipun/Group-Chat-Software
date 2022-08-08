package lk.ijse.chatApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        final int PORT = 5000;

        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                System.out.println("Server is running in port: " + PORT);

                Socket localSocket = serverSocket.accept();
                System.out.println("Client accepted..!");

                DataOutputStream dataOutputStream = new DataOutputStream(localSocket.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                String message = "", reply = "";

                while (true) {
                    message = dataInputStream.readUTF();
                    System.out.println("Client says , :" + message);
                    reply = bufferedReader.readLine();
                    dataOutputStream.writeUTF(reply);
                    dataOutputStream.flush();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
