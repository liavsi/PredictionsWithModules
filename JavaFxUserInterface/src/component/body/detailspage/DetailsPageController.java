package component.body.detailspage;

import DTOManager.impl.WorldDTO;
import component.mainapp.AppController;
import engine.api.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsPageController implements Initializable {
    
    @FXML private AppController mainAppController;

    private Engine engine;

    public void setMainController(AppController appController) {
        this.mainAppController = appController;
    }

    public void showDetailsForWorld() {
        WorldDTO worldDTO = engine.getWorldDTO();
        // TODO: 28/08/2023 implement this method 
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //bindings
        this.engine = (Engine) resources.getObject("Engine");
    }
}
