package controller;


import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.PersoanaOficiu;
import proxy.Proxy;
import utils.WarningBox;

public class LoginController {

    public TextField usernameTextField;
    public PasswordField passwordTextField;

    private Stage primaryStage;
    private Scene mainScene;
    private Scene menuScene;

    private Proxy proxy;

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setMenuScene(Scene menuScene){
        this.menuScene = menuScene;
    }

    public void handleLogin(ActionEvent actionEvent) {

            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            PersoanaOficiu persoanaOficiu = new PersoanaOficiu(username, password);
            if(proxy.login(persoanaOficiu)){
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
