package component.body.detailspage;

import DTOManager.impl.WorldDTO;
import component.mainapp.AppController;
import javafx.fxml.FXML;

public class DetailsPageController {
    
    @FXML private AppController mainAppController;

    public void setMainController(AppController appController) {
        this.mainAppController = appController;
    }

    public void showDetailsForWorld(WorldDTO worldDTO) {
        // TODO: 28/08/2023 implement this method 
    }
}
