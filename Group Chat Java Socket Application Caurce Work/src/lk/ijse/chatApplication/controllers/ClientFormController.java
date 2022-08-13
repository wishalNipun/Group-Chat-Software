package lk.ijse.chatApplication.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import lk.ijse.chatApplication.model.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClientFormController {
    public JFXTextField txtMessage;

    final int PORT = 1234;
    public ScrollPane scrollPane;
    public VBox vboxMessages;
    public AnchorPane mainAnchorPane;
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
                    hBox.setPadding(new Insets(5,5,5,10));
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    Text text = new Text(message);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-background-color: #E5E5EB;"+"-fx-background-radius : 20px;");
                    textFlow.setPadding(new Insets(5,10,5,10));

                    hBox.getChildren().add(textFlow);
                    addText(hBox);
                }



            } catch (IOException e) {
                closeEverything(socket,dataInputStream,dataOutputStream);
                e.printStackTrace();
            }
        }).start();
    }

    private void addText(HBox hBox) {
        Platform.runLater(()->{
            vboxMessages.getChildren().add(hBox);
        });
    }
    public void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream){
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

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        String messageToSend = txtMessage.getText().trim();
        dataOutputStream.writeUTF(messageToSend);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5,5,5,10));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        Text text = new Text("Me : "+messageToSend);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: #2980b9;"+"-fx-background-radius : 20px;");
        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.WHITE);


        hBox.getChildren().add(textFlow);

        vboxMessages.getChildren().add(hBox);
        dataOutputStream.flush();
        txtMessage.clear();
    }

    public void btnImageSendOnAction(ActionEvent actionEvent) {
        FileChooser filechooser = new FileChooser();
        File file = filechooser.showOpenDialog(mainAnchorPane.getScene().getWindow());
        System.out.println(file.getPath());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5,5,5,10));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        ImageView imageView = new ImageView("file:" + file.getPath());
        imageView.preserveRatioProperty().set(true);
        imageView.setFitHeight(250);
        imageView.setFitWidth(250);
        TextFlow textFlow = new TextFlow(imageView);
        hBox.getChildren().add(textFlow);
        vboxMessages.getChildren().add(hBox);
    }
}
