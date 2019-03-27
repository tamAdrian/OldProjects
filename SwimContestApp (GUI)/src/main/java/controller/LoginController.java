package controller;


import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.Service;
import utils.WarningBox;

public class LoginController {
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button backButton;

    private Stage primaryStage;
    private Scene mainScene;
    private Scene menuScene;
    private Service service;

    public void setService(Service service){
        this.service = service;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setMainScene(Scene mainScene){
        this.mainScene = mainScene;
    }
    public void setMenuScene(Scene menuScene){
        this.menuScene = menuScene;
    }

    public void handleLogin(ActionEvent actionEvent) {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            if(service.login(username, password)){
                this.primaryStage.setScene(menuScene);
                usernameTextField.clear();
                passwordTextField.clear();
            }
            else{
                WarningBox warningBox = new WarningBox();
                warningBox.warningMessage(Alert.AlertType.WARNING,"WARNING", "DATE DE AUTENTIFICARE INCORECTE ! VA RUGAM INTRODUCETI DIN NOU DATELE.");
            }
    }
}
