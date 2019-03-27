package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import service.Service;
import utils.WarningBox;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

public class MainMenuController {

    public Button backButton;
    public TableView tableView;
    public TableColumn tableColumnDistanta;
    public TableColumn tableColumnStil;
    public TableColumn tableColumnNumar;
    public TextField numeTextField;
    public TextField varstaTextField;
    public Button adaugaParticipantButton;
    public Button cautaParticipantiButton;
    public ListView listView;

    private Service service;
    private Stage primaryStage;
    private Scene loginScene;
    private ObservableList<ProbaDTO> model  = FXCollections.observableArrayList();
    private ObservableList<ParticipantDTO> listModel = FXCollections.observableArrayList();

    public void setService(Service service){
        this.service = service;

        List<ProbaDTO> probe = service.numarParticipantiProbe();
        model.setAll(probe);

    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setLoginScene(Scene loginScene){
        this.loginScene = loginScene;
    }

    @FXML
    public void initialize(){
        createTableView();
        tableView.setItems(model);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setItems(listModel);
    }

    public void createTableView(){
        tableColumnDistanta.setCellValueFactory(new PropertyValueFactory<ProbaDTO, String>("distanta"));
        tableColumnStil.setCellValueFactory(new PropertyValueFactory<ProbaDTO, String>("stil"));
        tableColumnNumar.setCellValueFactory(new PropertyValueFactory<ProbaDTO, String>("numarInscrisi"));
    }

    public void handleBack(ActionEvent actionEvent) {
        this.primaryStage.setScene(loginScene);
        numeTextField.clear();
        varstaTextField.clear();
    }

    public void handleAdaugaParticipant(ActionEvent actionEvent) {


        String nume = numeTextField.getText();
        String varsta = varstaTextField.getText();

        if(nume.equals("") || varsta.equals("")){
            WarningBox warningBox = new WarningBox();
            warningBox.warningMessage(Alert.AlertType.ERROR,"ERROR", "COMPLETATI TOATE CAMPURILE !");
        }
        else{
            List<ProbaDTO> list = (List<ProbaDTO>) tableView.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());

            if(list.size() == 0){
                WarningBox warningBox = new WarningBox();
                warningBox.warningMessage(Alert.AlertType.ERROR,"ERROR", "SELECTATI CEL PUTIN O PROBA DIN TABEL !");
            }
            else{
                service.inscrieParticipant(nume, Integer.valueOf(varsta) , list);
                model.setAll(service.numarParticipantiProbe()); //actualizare dupa adaugare participant
            }

        }

    }

    public void handleCautaParticipanti(ActionEvent actionEvent) {

        List<ProbaDTO> list = (List<ProbaDTO>) tableView.getSelectionModel().getSelectedItems().stream().collect(Collectors.toList());
        if(list.size() != 1){
            WarningBox warningBox = new WarningBox();
            warningBox.warningMessage(Alert.AlertType.ERROR,"ERROR", "SELECTATI O SINGURA PROBA !");
        }
        else{
            ProbaDTO probaDTO = list.get(0);
            listModel.setAll(service.cautaParticipantiProbe(probaDTO.getId())) ;
        }

    }
}
