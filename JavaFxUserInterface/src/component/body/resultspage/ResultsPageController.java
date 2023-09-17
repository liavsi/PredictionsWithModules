package component.body.resultspage;

import DTOManager.impl.SimulationOutcomeDTO;
import com.sun.javafx.collections.ObservableListWrapper;
import component.mainapp.AppController;
import engine.api.Engine;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utils.results.SimulationOutcomeListCell;

import java.util.ArrayList;
import java.util.List;

public class ResultsPageController {

    @FXML
    public Label TickNumLabel;
    @FXML
    public Label SecondsLabel;
    @FXML
    public GridPane mainView;
    @FXML private VBox vBoxWithSimulations;
    @FXML
    private AnchorPane resultsPane;

    @FXML
    private ListView<SimulationOutcomeDTO> simulationList; // Assuming SimulationOutcomeDTO is the class for simulation outcomes

    @FXML
    private VBox simulationDetailsPane;

    @FXML
    private Button rerunButton;
    private AppController mainController;
    private ObservableMap<Integer, SimulationOutcomeDTO> recentSimulations;

    private SimpleIntegerProperty ticks;
    private SimpleIntegerProperty seconds;
    private Engine engine;
    public GridPane getMainView() {
        return mainView;
    }

    public ResultsPageController() {
        this.ticks = new SimpleIntegerProperty();
        this.seconds = new SimpleIntegerProperty();
        recentSimulations = FXCollections.observableHashMap();
    }

    @FXML
    private void initialize() {
        // Initialize your controller
        simulationList.setCellFactory(param -> new SimulationOutcomeListCell());
        // Handle item selection in the list
        simulationList.getSelectionModel().selectLast();
        simulationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showSimulationDetails(newValue); // Display details of the selected simulation outcome
        });

    }

    private void showSimulationDetails(SimulationOutcomeDTO selectedSimulation) {

        simulationDetailsPane.getChildren().clear();
        simulationDetailsPane.getChildren().add(new TextArea(selectedSimulation.getRunDate()));
        // Add labels, text, or other UI components to display details here
    }

    public void setMainController(AppController appController) {
        this.mainController = appController;
        recentSimulations = appController.getRecentSimulations();
        simulationList.setItems(FXCollections.observableArrayList(recentSimulations.values()));
    }

    public void onStopButton(ActionEvent actionEvent) {
        engine.stopSimulationByID(1);
    }

    public void onResumeButton(ActionEvent actionEvent) {
        engine.resumeSimulationByID(1);
    }

    public void onPauseButton(ActionEvent actionEvent) {
        engine.pauseSimulationByID(1);
    }

    public void onRerunButton(ActionEvent actionEvent) {
        
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}

// Add more methods and logic for your results page as needed