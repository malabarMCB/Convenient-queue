package convenientQueue.fx.windowFactory;

import convenientQueue.fx.controller.*;
import convenientQueue.logic.LoginService;
import convenientQueue.logic.repository.IDoctorRepository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowFactory {
    private final String  BASE_PATH = "../view/";

    private LoginService loginService;
    private IDoctorRepository doctorRepository;
    private Stage stage;

    public WindowFactory(Stage stage, LoginService loginService, IDoctorRepository doctorRepository){
        this.stage = stage;
        this.loginService = loginService;
        this.doctorRepository = doctorRepository;
    }

    public void createLoginWindow(){
        setUpWindow(BASE_PATH+"Login.fxml","Login",controller -> {
            ((LoginController)controller).inject(loginService, this);
        } );
    }

    public void createHomeWindow(){
        setUpWindow(BASE_PATH+"Home.fxml","Home",controller -> {
            ((HomeController)controller).inject(this);
        } );
    }

    public void createMyQueueWindow(){
        setUpWindow(BASE_PATH+"MyQueue.fxml","MyQueue",controller -> {
            MyQueueController myQueueController = (MyQueueController) controller;
            myQueueController.inject(doctorRepository,this);
            myQueueController.init();
        } );
    }

    public void createCreateQueueWindow(){
        setUpWindow(BASE_PATH+"CreateQueue.fxml","CreateQueue",controller -> {
            CreateQueueController myQueueController = (CreateQueueController) controller;
            myQueueController.inject(doctorRepository,this);
            myQueueController.init();
        } );
    }

    private void setUpWindow(String resoursePath, String sceneTitle, ControllerInjectionFunctionalInterface injection){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resoursePath));
        Parent root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene= new Scene(root);
        stage.setTitle(sceneTitle);
        stage.setScene(scene);

        injection.run(loader.getController());

        stage.show();
    }
}
