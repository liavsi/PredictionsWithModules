package component.header;

import component.mainapp.AppController;
import engine.api.Engine;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HeaderController {


    @FXML private AppController appController;

    @FXML public Label LabelFilePath;
    @FXML
    public Button ButtonNewExec;
    @FXML
    public Button ButtonResults;
    @FXML
    public Button ButtonDetails;
    @FXML
    public Button ButtonLoadFile;
    @FXML
    public Button ButtonQueueManage;

    private SimpleStringProperty filePath;
    private SimpleBooleanProperty isNewExecutionPressed;
    private SimpleBooleanProperty isDetails;
    private SimpleBooleanProperty isQueueManage;
    private SimpleBooleanProperty isFileSelected;

    private Engine engine;
    private Stage primaryStage;



    public HeaderController() {
        this.filePath = new SimpleStringProperty("");
        this.isNewExecutionPressed = new SimpleBooleanProperty(false);
        this.isDetails = new SimpleBooleanProperty(false);
        this.isQueueManage = new SimpleBooleanProperty(false);
        this.isFileSelected = new SimpleBooleanProperty(false);
    }

    public void bindFileNameToEngine() {
        appController.setFileNameToEngine(filePath);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {

    }
}
