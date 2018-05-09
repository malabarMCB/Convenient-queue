package convenientQueue.fx.controller;

import convenientQueue.fx.FxTextExtension;
import convenientQueue.fx.windowFactory.WindowFactory;
import convenientQueue.logic.LoginService;
import convenientQueue.logic.exception.InvalidLoginRequestException;
import convenientQueue.logic.model.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {
    @FXML private Text dialogText;
    @FXML private TextField password;
    @FXML private TextField login;

    private LoginService loginService;
    private WindowFactory windowFactory;

    public void inject(LoginService loginService, WindowFactory windowFactory){
        this.loginService = loginService;
        this.windowFactory = windowFactory;
    }

    @FXML
    private void initialize(){
        login.setText("test1");
        password.setText("111");
    }

    @FXML
    private void login(ActionEvent actionEvent) {
        LoginRequest loginRequest = configureLoginRequest();
        try {
            loginService.Login(loginRequest);
            windowFactory.createHomeWindow();
        } catch (InvalidLoginRequestException e) {
            FxTextExtension.appendText(dialogText,"Incorrect login or password");
        }
    }

    private LoginRequest configureLoginRequest(){
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setLogin(login.getText());
        loginRequest.setPassword(password.getText());

        return loginRequest;
    }
}
