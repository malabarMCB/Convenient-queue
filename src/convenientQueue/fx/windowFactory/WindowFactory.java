package convenientQueue.fx.windowFactory;

import convenientQueue.fx.controller.HomeController;
import convenientQueue.fx.controller.LoginController;
import convenientQueue.logic.LoginService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowFactory {
    private final String  BASE_PATH = "../view/";

    private LoginService loginService;

    public WindowFactory(LoginService loginService){
        this.loginService = loginService;
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
