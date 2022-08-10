package lk.ijse.chatApplication;

import lk.ijse.chatApplication.model.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String clientUserName;
    private Socket socket;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());

            clientUserName = dataInputStream.readUTF();
            sendMessageClientEnter(this," has entered the chat!");
            clientHandlers.add(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        IncomingMessage();
    }

    private void IncomingMessage() {
        while (socket.isConnected()) {
            try {
                sendMessageClientEnter(this, dataInputStream.readUTF());
            } catch (IOException e) {

            }
        }
    }

    private void sendMessageClientEnter(ClientHandler client, String messageTo) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (clientHandler != client) {
                    clientHandler.dataOutputStream.writeUTF(clientUserName + " : " + messageTo);
                    clientHandler.dataOutputStream.flush();
                }

            } catch (IOException e) {

            }
        }
    }
}
