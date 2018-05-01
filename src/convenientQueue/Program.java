package convenientQueue;

import convenientQueue.fx.windowFactory.WindowFactory;
import convenientQueue.logic.LoginService;
import convenientQueue.logic.repository.IUserRepository;
import javafx.stage.Stage;

import javafx.application.Application;

public class Program extends Application {
    @Override
    public void start(Stage stage){
        //dependency injections
        IUserRepository userRepository = null;
        LoginService loginService = new LoginService(userRepository);

        WindowFactory windowFactory = new WindowFactory(loginService);
        windowFactory.createLoginWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
