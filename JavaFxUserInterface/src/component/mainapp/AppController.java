package component.mainapp;

import component.header.HeaderController;
import engine.api.Engine;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;


public class AppController {
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;

    private Engine engine;

    @FXML
    public void initialize() {}

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public void setFileNameToEngine(SimpleStringProperty filePath) {
        engine.fileNameProperty().bind(filePath);
    }



}
