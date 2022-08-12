package lk.ijse.chatApplication.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lk.ijse.chatApplication.model.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {
    public JFXTextField txtMessage;

    final int PORT = 1234;
    public ScrollPane scrollPane;
    public VBox vboxMessages;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    static String username;
    String message = "";

    public void initialize() throws IOException {
        socket = new Socket("localhost",PORT);

        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(username);

        vboxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });
        new Thread(() -> {
            try {
                while (true){
                    message = dataInputStream.readUTF();
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    Text text = new Text(message);
                    TextFlow textFlow = new TextFlow(text);

                    hBox.getChildren().add(textFlow);
                    addText(hBox);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void addText(HBox hBox) {
        Platform.runLater(()->{
            vboxMessages.getChildren().add(hBox);
        });
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        String messageToSend = txtMessage.getText().trim();
        dataOutputStream.writeUTF(messageToSend);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        Text text = new Text("Me : "+messageToSend);
        TextFlow textFlow = new TextFlow(text);

        hBox.getChildren().add(textFlow);
        vboxMessages.getChildren().add(hBox);
        dataOutputStream.flush();
        txtMessage.clear();
    }
}
