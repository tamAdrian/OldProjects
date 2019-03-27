package main;

import controller.LoginController;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Distanta;
import model.Proba;
import model.Stil;
import repository.*;
import service.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
//        Label label = new Label("Hello world !");
//        Scene scene = new Scene(label);
//        primaryStage.setScene(scene);
//        primaryStage.show();
        init(primaryStage);
    }

    public void populate(ProbaRepository probaRepository){
        ArrayList<Distanta> distante = new ArrayList<Distanta>();
        distante.add(Distanta.dist50m);
        distante.add(Distanta.dist200m);
        distante.add(Distanta.dist800m);
        distante.add(Distanta.dist1500m);

        ArrayList<Stil> stiluri = new ArrayList<Stil>();
        stiluri.add(Stil.fluture);
        stiluri.add(Stil.liber);
        stiluri.add(Stil.mixt);
        stiluri.add(Stil.spate);

        int c = 1;
        for(int i = 0; i < 4 ;i++){
            for(int j = 0; j < 4; j++){
                probaRepository.save(new Proba(c, distante.get(i), stiluri.get(j)));
                c++ ;
            }
        }
    }

    private void init(Stage primaryStage) throws IOException {

        //init repo and service
        Properties props = new Properties();
        props.load(new FileInputStream("bd.config"));
        InscriereRepository inscriereRepository = new InscriereRepository(props);
        ParticipantRepository participantRepository = new ParticipantRepository(props);
        PersoanaOficiuRepository persoanaOficiuRepository = new PersoanaOficiuRepository(props);
        ProbaRepository probaRepository = new ProbaRepository(props);

        Service service = new Service(inscriereRepository, participantRepository, persoanaOficiuRepository, probaRepository);

        //login
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/LoginView.fxml"));
        Parent rootLayout = loader.load();
        LoginController loginController = loader.getController();
        Scene loginScene = new Scene(rootLayout);
        loginController.setPrimaryStage(primaryStage);
        loginController.setService(service);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("MENIU");
        primaryStage.show();

        //Main menu
        FXMLLoader menuLoader = new FXMLLoader();
        menuLoader.setLocation(getClass().getResource("/MainMenuView.fxml"));
        Parent menuRoot = menuLoader.load();
        Scene menuScene = new Scene(menuRoot);
        MainMenuController menuController = menuLoader.getController();
        menuController.setService(service);
        menuController.setPrimaryStage(primaryStage);
        menuController.setLoginScene(loginScene);

        loginController.setMenuScene(menuScene);
        primaryStage.show();

    }
}
