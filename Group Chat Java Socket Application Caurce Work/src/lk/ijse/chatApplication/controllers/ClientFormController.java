package lk.ijse.chatApplication.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import lk.ijse.chatApplication.model.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {
    public JFXTextField txtMessage;
    public JFXTextArea textArea;

    final int PORT = 1234;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    static String username;
    String message = "";

    public void initialize() throws IOException {
        socket = new Socket("localhost",PORT);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        textArea.setEditable(false);
        dataOutputStream.writeUTF(username);
        new Thread(() -> {
            try {
                while (true){
                    message = dataInputStream.readUTF();
                    textArea.appendText("\n"+message);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtMessage.getText().trim());
        textArea.appendText("\n"+"Me : "+txtMessage.getText().trim());
        dataOutputStream.flush();
        txtMessage.clear();
    }
}
