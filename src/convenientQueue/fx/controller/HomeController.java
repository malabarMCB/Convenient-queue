package convenientQueue.fx.controller;

import convenientQueue.fx.windowFactory.WindowFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {
    private WindowFactory windowFactory;

    public void inject (WindowFactory windowFactory){
        this.windowFactory = windowFactory;
    }

    @FXML
    private void viewMyQueue(ActionEvent actionEvent) {
        windowFactory.createMyQueueWindow();
    }

    @FXML
    private void createQueue(ActionEvent actionEvent) {
    }
}
