package convenientQueue.fx.windowFactory;

import convenientQueue.fx.controller.HomeController;
import convenientQueue.fx.controller.LoginController;
import convenientQueue.fx.controller.MyQueueController;
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

    public WindowFactory(LoginService loginService, IDoctorRepository doctorRepository){
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
            ((MyQueueController)controller).inject(doctorRepository,this);
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
        Stage stage= new Stage();
        stage.setTitle(sceneTitle);
        stage.setScene(scene);

        injection.run(loader.getController());

        stage.show();
    }
}
