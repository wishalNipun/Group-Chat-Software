package lk.ijse.chatApplication.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.chatApplication.model.Log;

import java.io.IOException;
import java.net.URL;

public class LogFormController {
    public JFXTextField txtUserName;
    public AnchorPane loginFormContext;

    public void initialize(){

    }

    public void btnLogOnAction(ActionEvent actionEvent) throws IOException {
        ClientFormController.username=txtUserName.getText();
        Stage stage1= (Stage)loginFormContext.getScene().getWindow();
        stage1.close();
        URL resource = getClass().getResource("/lk/ijse/chatApplication/view/ClientForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage2= new Stage();
        stage2.setTitle("Group Chat Application");
        stage2.setScene(scene);
        stage2.centerOnScreen();
        stage2.show();

    }
}
