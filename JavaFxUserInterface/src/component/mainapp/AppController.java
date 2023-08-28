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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;


public class AppController extends ResourceBundle {
    @FXML private VBox dynamicBox;
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private DetailsPageController detailsPageComponentController;
    @FXML private ScrollPane detailsPageComponent;

    @FXML private BorderPane BorderPaneMain;

    private Engine engine;



    @FXML public void initialize() {
        if (headerComponentController != null && detailsPageComponentController != null) {
           headerComponentController.setMainController(this);
           detailsPageComponentController.setMainController(this);
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
        detailsPageComponentController.showDetailsForWorld();
        BorderPaneMain.setCenter(detailsPageComponent);
        // TODO: 28/08/2023 implement the World details screen
    }

    public void readWorld() {
        engine.readWorldFromXml();
    }

    public void moveToNewExecutionScreen() throws IOException {
        WorldDTO worldDTO = engine.getWorldDTO();
        List<PropertyDefinitionDTO> environmentVarsAskToFill = worldDTO.getEnvPropertiesDefinitionDTO();
        changeDynamicDetailsScreen();
        // TODO: 28/08/2023 implement the new execution screen
    }
    private void changeDynamicDetailsScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFxml = getClass().getResource("body/detailspage/detailsPageView.fxml");
        fxmlLoader.setLocation(mainFxml);
        fxmlLoader.setResources(this);
        VBox detailsBox = fxmlLoader.load();
        DetailsPageController detailsPageController = fxmlLoader.getController();
        dynamicBox.getChildren().clear();
        detailsBox.getChildren().add(detailsBox);
    }
    @Override
    protected Object handleGetObject(String key) {
        switch (key){
            case "Engine":
                return engine;
            default:
                return null;
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
