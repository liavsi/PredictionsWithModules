package component.mainapp;

import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import component.body.detailspage.DetailsPageController;
import component.header.HeaderController;
import engine.api.Engine;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class AppController {
    private static final String DETAILS_FXML_LOCATION = "/component/body/detailspage/detailsPageView.fxml";
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


    public void onDetailsChosen()  {
        WorldDTO worldDTO = engine.getWorldDTO();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL manFXML = getClass().getResource(DETAILS_FXML_LOCATION);
        try {
            detailsPageComponent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        detailsPageComponentController = fxmlLoader.getController();
        detailsPageComponentController.showDetailsForWorld(worldDTO);
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
