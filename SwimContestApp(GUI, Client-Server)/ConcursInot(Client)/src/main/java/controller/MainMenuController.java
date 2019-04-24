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
import proxy.Proxy;
import utils.WarningBox;
import java.util.List;
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

    private Proxy proxy;

    private Stage primaryStage;
    private Scene loginScene;
    private ObservableList<ProbaDTO> model  = FXCollections.observableArrayList();
    private ObservableList<ParticipantDTO> listModel = FXCollections.observableArrayList();

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;

        //load table
        List<ProbaDTO> probe = proxy.numarParticipantiProbe();
        model.setAll(probe);
    }

    // reload table
    public void update(List<ProbaDTO> list){
        model.setAll(list);
    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public void setLoginScene(Scene loginScene){
        this.loginScene = loginScene;
    }


    public void createTableView(){
        tableColumnDistanta.setCellValueFactory(new PropertyValueFactory<ProbaDTO, String>("distanta"));
        tableColumnStil.setCellValueFactory(new PropertyValueFactory<ProbaDTO, String>("stil"));
        tableColumnNumar.setCellValueFactory(new PropertyValueFactory<ProbaDTO, String>("numarInscrisi"));
    }

    @FXML
    public void initialize(){
        createTableView();
        tableView.setItems(model);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setItems(listModel);
    }

    public void handleBack(ActionEvent actionEvent) {
        this.primaryStage.setScene(loginScene);
        numeTextField.clear();
        varstaTextField.clear();
        proxy.logout();
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
                proxy.inscrieParticipant(nume, Integer.valueOf(varsta) , list);
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
            listModel.setAll(proxy.cautaParticipantiProbe(probaDTO.getId())) ;
        }

    }
}
