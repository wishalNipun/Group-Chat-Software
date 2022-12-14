package lk.ijse.chatApplication;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    final int PORT = 1234;
    final int PORT2 = 1235;
    private ServerSocket serverSocket;
    private ServerSocket serverSocket2;
    public ServerApp(ServerSocket serverSocket,ServerSocket serverSocket2){

        this.serverSocket =serverSocket;
        this.serverSocket2 =serverSocket2;
    }
    public void startServer(){
        try {
            while (!serverSocket.isClosed()){

                ClientHandler clientHandler = new ClientHandler(serverSocket.accept(),serverSocket2.accept());

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (IOException e){
            closeServerSocket();
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
        ServerSocket serverSocket2 = new ServerSocket(1235);
        ServerApp serverApp = new ServerApp(serverSocket,serverSocket2);
        serverApp.startServer();
    }

}
