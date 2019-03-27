package main;

import controller.FilterController;
import controller.GradeController;
import controller.MainMenuController;
import domain.Nota;
import domain.Student;
import domain.Tema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import repository.CrudRepository;
import repository.InFileGradeRepository;
import repository.InFileHomeworkRepository;
import repository.InFileStudentRepository;
import service.GradeService;
import service.HomeworkService;
import service.StudentService;
import utils.Pair;
import validator.ValidatorNota;
import validator.ValidatorStudent;
import validator.ValidatorTema;
import view.HomeworkController;
import view.HomeworkView;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

//        CrudRepository<Integer, Tema> repo = new InFileHomeworkRepository(new ValidatorTema(), "D:\\Adrian\\3.FACULTATE - ANUL II (Sem I)\\MAP\\Projects\\Tema7 (GUI)\\src\\txtFiles\\homework");
//        HomeworkService service = new HomeworkService(repo);
//        HomeworkController ctrl = new HomeworkController(service);
//        HomeworkView view = new HomeworkView(ctrl);
//
//        ctrl.setView(view);

        /*primaryStage.setTitle("Homework operations");
        Scene scene = new Scene(view.getView(),300,300);
        scene.setFill(Paint.valueOf("#052f52"));
        primaryStage.setScene(scene);
        primaryStage.show();*/
       init(primaryStage);
    }


    private void init(Stage primaryStage) throws Exception{

        //init repositories and services
        CrudRepository<Integer, Tema> homeworkRepository = new InFileHomeworkRepository(new ValidatorTema(), "D:\\Adrian\\3.FACULTATE - ANUL II (Sem I)\\MAP\\Projects\\Tema7 (GUI)\\src\\txtFiles\\homework");
        HomeworkService homeworkService = new HomeworkService(homeworkRepository);

        CrudRepository<Integer, Student> studentRepository = new InFileStudentRepository(new ValidatorStudent(),"D:\\Adrian\\3.FACULTATE - ANUL II (Sem I)\\MAP\\Projects\\Tema7 (GUI)\\src\\txtFiles\\students");
        StudentService studentService = new StudentService(studentRepository);

        CrudRepository<Pair<Integer, Integer>, Nota> gradeRepository = new InFileGradeRepository(new ValidatorNota(), "D:\\Adrian\\3.FACULTATE - ANUL II (Sem I)\\MAP\\Projects\\Tema7 (GUI)\\src\\txtFiles\\catalog");
        GradeService gradeService = new GradeService(homeworkService, studentService, gradeRepository);

        //main menu
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/MainMenuView.fxml"));
        Parent rootLayout = loader.load();
        MainMenuController controller = loader.getController();
        Scene mainMenuScene = new Scene(rootLayout);
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("MENIU");

        //homework
        HomeworkController homeworkController = new HomeworkController(homeworkService);
        HomeworkView homeworkView = new HomeworkView(homeworkController);

        //Scene homeworkScene = new Scene(homeworkView.getView(), 1300,1920);
        Scene homeworkScene = new Scene(homeworkView.getView(), 1200,588);
        homeworkScene.setFill(Paint.valueOf("#052f52"));
        homeworkController.setView(homeworkView);
        homeworkController.setPrimaryStage(primaryStage);
        homeworkController.setMainMenuScene(mainMenuScene);

        //grade
        FXMLLoader gradeLoader = new FXMLLoader();
        gradeLoader.setLocation(getClass().getResource("/view/GradeView.fxml"));
        Parent rootLayout1 = gradeLoader.load();
        GradeController gradeController = gradeLoader.getController();
        gradeController.setGradeService(gradeService);
        Scene gradeScene = new Scene(rootLayout1);
        gradeController.setPrimaryStage(primaryStage);
        gradeController.setMainMenuStage(mainMenuScene);


        //filter
        FXMLLoader filterLoader = new FXMLLoader();
        filterLoader.setLocation(getClass().getResource("/view/FilterView.fxml"));
        Parent rootLayout2 = filterLoader.load();
        FilterController filterController = filterLoader.getController();
        filterController.setGradeService(gradeService);
        Scene filterScene = new Scene(rootLayout2);
        filterController.setPrimaryStage(primaryStage);
        filterController.setMainMenuScene(mainMenuScene);

        controller.setPrimaryStage(primaryStage);
        controller.setHomeworkScene(homeworkScene);
        controller.setGradeScene(gradeScene);
        controller.setFilterScene(filterScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
