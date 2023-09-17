package component.body.resultspage;

import DTOManager.impl.SimulationOutcomeDTO;
import com.sun.corba.se.spi.activation.Server;
import com.sun.javafx.collections.ObservableListWrapper;
import component.mainapp.AppController;
import engine.api.Engine;
import engine.world.design.expression.ExpressionType;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
    private ObservableList<SimulationOutcomeDTO> recentSimulations;

    private SimpleIntegerProperty ticks;
    private SimpleIntegerProperty seconds;
    private Engine engine;
    public GridPane getMainView() {
        return mainView;
    }

    public ResultsPageController() {
        this.ticks = new SimpleIntegerProperty();
        this.seconds = new SimpleIntegerProperty();
        recentSimulations = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        TickNumLabel.textProperty().bind(ticks.asString());
        SecondsLabel.textProperty().bind(seconds.asString());
        // Initialize your controller
        simulationList.setCellFactory(param -> new SimulationOutcomeListCell());
        // Handle item selection in the list
        simulationList.getSelectionModel().selectLast();
        simulationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showSimulationDetails(newValue); // Display details of the selected simulation outcome
        });
    }

    private void showSimulationDetails(SimulationOutcomeDTO selectedSimulation) {
        Service backgroundService = new Service() {
            @Override
            protected Task createTask() {

                return new Task() {
                    @Override
                    protected SimulationOutcomeDTO call() throws Exception {
                        // Run your simulation here
                        int simulationId = selectedSimulation.getId();
                        SimulationOutcomeDTO currSimulationDTO = engine.getPastSimulationDTO(simulationId);
                        boolean isSimulationRunning = true;
                        isSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
                        while (isSimulationRunning) {
                            currSimulationDTO = engine.getPastSimulationDTO(simulationId);
                            SimulationOutcomeDTO finalCurrSimulationDTO = currSimulationDTO;
                            Platform.runLater(() -> {
                                ticks.set(selectedSimulation.getTerminationDTO().getTicks());
                                seconds.set(selectedSimulation.getTerminationDTO().getSecondsToPast());
//                        updateProgress(currSimulationDTO.getTerminationDTO().getTicks(), engine.getWorldDTO().getTerminationDTO().getTicks());
                            });
                            Thread.sleep(200);
                            isSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
                        }
                        return engine.getPastSimulationDTO(simulationId);
                    }
                };
            }
        };
        backgroundService.start();
        backgroundService.setOnSucceeded(event -> {
            // simulation finished make buttons disabled..
        });


    }



    public void setMainController(AppController appController) {
        this.mainController = appController;
        recentSimulations = appController.getRecentSimulations();
        simulationList.setItems(recentSimulations);
    }

    public void onStopButton(ActionEvent actionEvent) {
        if (simulationList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int id = simulationList.getSelectionModel().getSelectedItem().getId();
        engine.stopSimulationByID(id);
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