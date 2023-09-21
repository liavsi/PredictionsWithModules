package component.header;

import component.mainapp.AppController;
import engine.impl.EngineImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HeaderController {


    @FXML
    public ChoiceBox CBView;
    @FXML
    public GridPane gridPaneHeader;
    @FXML private AppController appController;
    @FXML public Button ButtonResults;
    @FXML public Label LabelFilePath;
    @FXML public Button ButtonNewExec;
    @FXML
    public Button ButtonDetails;
    @FXML
    public Button ButtonLoadFile;
    @FXML
    public Button ButtonQueueManage;

    private SimpleBooleanProperty isThereSimulationOutCome;
    private SimpleStringProperty filePath;
    private SimpleBooleanProperty isNewExecutionPressed;
    private SimpleBooleanProperty isDetails;
    private SimpleBooleanProperty isQueueManage;
    private SimpleBooleanProperty isFileSelected;
    private ObservableList<String> viewList = FXCollections.observableArrayList("Blue","Pink","white");
    private Stage primaryStage;



    public HeaderController() {
        this.filePath = new SimpleStringProperty("");
        this.isNewExecutionPressed = new SimpleBooleanProperty(false);
        this.isDetails = new SimpleBooleanProperty(false);
        this.isQueueManage = new SimpleBooleanProperty(false);
        this.isFileSelected = new SimpleBooleanProperty(false);
        this.isThereSimulationOutCome = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        LabelFilePath.textProperty().bind(filePath);
        ButtonDetails.disableProperty().bind(isFileSelected.not());
        ButtonNewExec.disableProperty().bind(isFileSelected.not());
        ButtonQueueManage.disableProperty().bind(isFileSelected.not());
        ButtonResults.disableProperty().bind(isThereSimulationOutCome.not());
        CBView.setValue("Blue");
        CBView.setItems(viewList);
        CBView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> applyStyles((String) newValue));

    }

    public void applyStyles(String selectedColor) {
        String cssFileName;
        switch (selectedColor) {
            case "Blue":
                cssFileName = "blueStyle.css";
                break;
            case "Pink":
                cssFileName = "pinkStyle.css";
                break;
            case "Green":
                cssFileName = "greenStyle.css";
                break;
            default:
                cssFileName = "defaultStyle.css"; // Provide a default style
                break;

        }
        appController.changeDesign(cssFileName);
        gridPaneHeader.getStylesheets().clear();
        gridPaneHeader.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
    }
    @FXML
    private void onClickLoadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Flows XML File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null)
            return;
        String absolutePath = selectedFile.getAbsolutePath();
        try {
            if (absolutePath != filePath.getValue()) {
                filePath.set(absolutePath);
                isFileSelected.set(true);
                appController.reLoadEveryThing();
                appController.setEngine(new EngineImpl());
                appController.readWorld();
            }
        }
        catch (IllegalArgumentException e) {
            filePath.set(e.getMessage());
            isFileSelected.set(false);
        }
    }

    @FXML
    private void onNewExecutionClicked(ActionEvent event) throws IOException {
        appController.onNewExecutionChosen();
        isNewExecutionPressed.set(true);
    }



    public void setMainController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    public void onDetailsClicked(ActionEvent actionEvent) {
        appController.onDetailsChosen();
    }

    public SimpleStringProperty getFilePath() {
        return filePath;
    }

    public void setIsIsThereSimulationOutCome(boolean bool) {
        isThereSimulationOutCome.set(bool);
    }

    public void onResultsClicked(ActionEvent event) {
        appController.switchToResultsPage();
    }

    public void onQueueManagementClicked(ActionEvent event) {
        appController.showQueueManagement();
    }
}
