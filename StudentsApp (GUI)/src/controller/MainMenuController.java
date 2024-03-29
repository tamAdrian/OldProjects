package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {


    public Button gradeOperationsButton;
    public Button homeworkOperationsButton;
    public Button filterOperationsButton;

    private Stage primaryStage;
    private Scene homeworkScene;
    private Scene gradeScene;
    private Scene filterScene;

    public void setFilterScene(Scene filterScene) {
        this.filterScene = filterScene;
    }

    public void setGradeScene(Scene gradeScene) {
        this.gradeScene = gradeScene;
    }

    public void setHomeworkScene(Scene homeworkScene) {
        this.homeworkScene = homeworkScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void handleHomeworkOperations(ActionEvent actionEvent) {

        primaryStage.setScene(homeworkScene);
       // primaryStage.setTitle("Homework operations");

    }

    public void handleGradeOperations(ActionEvent actionEvent) {
        primaryStage.setScene(gradeScene);
    }

    public void handleFilterOperations(ActionEvent actionEvent) {
        primaryStage.setScene(filterScene);
    }
}
