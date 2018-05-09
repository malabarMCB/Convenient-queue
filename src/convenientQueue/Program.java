package convenientQueue;

import convenientQueue.fx.windowFactory.WindowFactory;
import convenientQueue.logic.LoginService;
import convenientQueue.logic.repository.*;
import convenientQueue.sqlDataAccess.repository.*;
import javafx.stage.Stage;

import javafx.application.Application;

public class Program extends Application {
    private final String connectionString="jdbc:sqlserver://localhost:52733;databaseName=Convenient_queue; integratedSecurity=true";

    @Override
    public void start(Stage stage){
        //dependency injections
        IUserRepository userRepository = new SqlUserRepository(connectionString);
        IDoctorRepository doctorRepository = new SqlDoctorRepository(connectionString);
        LoginService loginService = new LoginService(userRepository);

        WindowFactory windowFactory = new WindowFactory(stage, loginService, doctorRepository);
        windowFactory.createLoginWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
