package convenientQueue.fx.controller;

import convenientQueue.fx.windowFactory.WindowFactory;

public class HomeController {
    private WindowFactory windowFactory;

    public void inject (WindowFactory windowFactory){
        this.windowFactory = windowFactory;
    }
}
