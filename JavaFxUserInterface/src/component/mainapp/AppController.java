package component.mainapp;

import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import component.body.detailspage.DetailsPageController;
import component.header.HeaderController;
import engine.api.Engine;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import java.util.List;


public class AppController {
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;

    @FXML private DetailsPageController detailsPageComponentController;

    @FXML private ScrollPane detailsPageComponent;

    @FXML private BorderPane BorderPaneMain;

    private Engine engine;



    @FXML public void initialize() {
        if (headerComponentController != null) {
           headerComponentController.setMainController(this);
           // add all controllers here..
        }
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public void setFileNameToEngine(SimpleStringProperty filePath) {
        engine.fileNameProperty().bind(filePath);
    }


    public void onDetailsChosen() {
        WorldDTO worldDTO = engine.getWorldDTO();
        detailsPageComponentController.showDetailsForWorld();
        BorderPaneMain.setCenter(detailsPageComponent);
        // TODO: 28/08/2023 implement the World details screen
    }

    public void readWorld() {
        engine.readWorldFromXml();
    }

    public void moveToNewExecutionScreen() {
        WorldDTO worldDTO = engine.getWorldDTO();
        List<PropertyDefinitionDTO> environmentVarsAskToFill = worldDTO.getEnvPropertiesDefinitionDTO();
        // TODO: 28/08/2023 implement the new execution screen
    }
}
