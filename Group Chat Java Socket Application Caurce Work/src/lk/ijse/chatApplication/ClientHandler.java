package lk.ijse.chatApplication;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    String clientUserName;
    private Socket socket;
    private Socket socket2;

    public ClientHandler(Socket socket,Socket socket2) {
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());

            this.socket2 = socket2;
            this.dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());
            this.dataInputStream2 = new DataInputStream(socket2.getInputStream());

            clientUserName = dataInputStream.readUTF();
            sendMessageClientEnter(this," has entered the chat ! ");
            clientHandlers.add(this);

        } catch (IOException e) {
            closeEverything(socket,dataInputStream,dataOutputStream);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        IncomingImages();
        IncomingMessage();
    }

    private void IncomingImages() {
        new Thread(() -> {
            while (socket.isConnected()) {
                try {

                    int readInt = dataInputStream2.readInt();
                    byte[] bytes = new byte[readInt];
                    dataInputStream2.readFully(bytes,0,readInt);
                    sendMessageClientImage(this,readInt,bytes);

                } catch (IOException e) {

                }
            }
        }).start();
    }

    private void sendMessageClientImage(ClientHandler client, int i, byte[] bytes) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (clientHandler != client) {
                    //    clientHandler.dataOutputStream.writeUTF(clientUserName);
                    //  clientHandler.dataOutputStream.flush();
                    clientHandler.dataOutputStream2.writeInt(i);
                    clientHandler.dataOutputStream2.write(bytes);


                }

            } catch (IOException e) {

            }
        }
    }

    private void IncomingMessage() {
        while (socket.isConnected()) {
            try {
                sendMessageClientEnter(this, dataInputStream.readUTF());
            } catch (IOException e) {
                closeEverything(socket,dataInputStream,dataOutputStream);
                break;
            }
        }
    }
    public void removeClientHandler(){
        clientHandlers.remove(this);
        sendMessageClientEnter(this, " has left the chat ! ");

    }
    private void sendMessageClientEnter(ClientHandler client, String messageTo) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (clientHandler != client) {
                    clientHandler.dataOutputStream.writeUTF(clientUserName + " : " + messageTo);
                    clientHandler.dataOutputStream.flush();
                }

            } catch (IOException e) {
                closeEverything(socket,dataInputStream,dataOutputStream);
            }
        }
    }

    public void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream){
        removeClientHandler();
        try {
            if (dataInputStream !=null){
                dataInputStream.close();
            }
            if (dataOutputStream!=null){
                dataOutputStream.close();
            }
            if (socket !=null){
                socket.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
