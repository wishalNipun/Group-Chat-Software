package lk.ijse.chatApplication;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    final int PORT = 1234;
    private ServerSocket serverSocket;
    public ServerApp(ServerSocket serverSocket){
        this.serverSocket =serverSocket;
    }
    public void startServer(){
        try {
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (IOException e){

        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket !=null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server is Running");
        ServerApp serverApp = new ServerApp(serverSocket);
        serverApp.startServer();
    }

}
