package lk.ijse.chatApplication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String clientUserName;

    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            clientHandlers.add(this);
            //sendMessageClientENter("Server: "+ clientUserName + "has entered the chat!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String messagesFromClient;
        while (socket.isConnected()){
            try {
                messagesFromClient = dataInputStream.readUTF();

            }catch (IOException e){

            }

        }
    }
}
