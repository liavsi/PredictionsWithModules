package component.body.resultspage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import com.sun.corba.se.spi.activation.Server;
import com.sun.javafx.collections.ObservableListWrapper;
import component.mainapp.AppController;
import engine.SimulationOutcome;
import engine.api.Engine;
import engine.world.design.definition.entity.api.EntityDefinition;
import engine.world.design.expression.ExpressionType;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import utils.results.SimulationOutcomeListCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultsPageController {

    @FXML
    public Label TickNumLabel;
    @FXML
    public Label SecondsLabel;
    @FXML
    public GridPane mainView;
    @FXML
    public Button ButtonStop;
    @FXML
    public Button ButtonResume;
    @FXML
    public Button ButtonPause;
    @FXML
    public TableView<ShowEntity> TableView;
    @FXML
    public TableColumn<ShowEntity,String> EntityNameColumn;
    @FXML
    public TableColumn<ShowEntity,Integer> PopulationColumn;

    @FXML private VBox vBoxWithSimulations;
    @FXML
    private AnchorPane resultsPane;

    @FXML
    private ListView<SimulationOutcomeDTO> simulationList; // Assuming SimulationOutcomeDTO is the class for simulation outcomes

    @FXML
    private VBox simulationDetailsPane;

    @FXML
    private Button rerunButton;
    Task<SimulationOutcomeDTO> simulationUpdateTask;
    private SimpleBooleanProperty isStopSelected;
    private SimpleBooleanProperty isPauseSelected;
    private AppController mainController;
    private ObservableList<SimulationOutcomeDTO> recentSimulations;

    private ObservableList<ShowEntity> entities;

    private SimpleIntegerProperty ticks;
    private SimpleLongProperty seconds;
    private Engine engine;
    public GridPane getMainView() {
        return mainView;
    }

    public ResultsPageController() {
        this.ticks = new SimpleIntegerProperty();
        this.entities = FXCollections.observableArrayList();
        this.seconds = new SimpleLongProperty();
        recentSimulations = FXCollections.observableArrayList();
        this.isStopSelected = new SimpleBooleanProperty(false);
        this.isPauseSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        TickNumLabel.textProperty().bind(ticks.asString());
        SecondsLabel.textProperty().bind(seconds.asString());
        ButtonPause.disableProperty().bind(isPauseSelected);
        ButtonResume.disableProperty().bind(isPauseSelected.not());
        ButtonStop.disableProperty().bind(isStopSelected);
        EntityNameColumn.setCellValueFactory(new PropertyValueFactory<ShowEntity,String>("EntityName"));
        PopulationColumn.setCellValueFactory(new PropertyValueFactory<ShowEntity,Integer>("Population"));
        TableView.setItems(entities);

        // Initialize your controller
        simulationList.setCellFactory(param -> new SimulationOutcomeListCell());
        // Handle item selection in the list
        simulationList.getSelectionModel().selectLast();
        simulationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showSimulationDetails(newValue); // Display details of the selected simulation outcome
        });
    }

    private void setEntitiesOnClick(SimulationOutcomeDTO currSimulation) {
        entities.clear();
        for (EntityDefinitionDTO entityDefinitionDTO: engine.getWorldDTO().getNameToEntityDefinitionDTO().values()){
            entities.add(new ShowEntity(entityDefinitionDTO));
        }
    }

    private void showSimulationDetails(SimulationOutcomeDTO selectedSimulation) {

        if (simulationUpdateTask != null) {
            simulationUpdateTask.cancel();
        }
        simulationUpdateTask = new Task<SimulationOutcomeDTO>() {

            @Override
            protected SimulationOutcomeDTO call()  {
                // Run your simulation here
                int simulationId = selectedSimulation.getId();
                SimulationOutcomeDTO currSimulationDTO = engine.getPastSimulationDTO(simulationId);
                boolean isSimulationRunning = true;
                isSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
                System.out.println("running task on simulation id:" + simulationId);
                System.out.println("current seconds" + currSimulationDTO.getTerminationDTO().getCurrSecond());
                while (isSimulationRunning) {
                    if (isCancelled()) {
                        break;
                    }
                    currSimulationDTO = engine.getPastSimulationDTO(simulationId);
                    SimulationOutcomeDTO finalCurrSimulationDTO = currSimulationDTO;
                    Platform.runLater(() -> {
                        ticks.set(finalCurrSimulationDTO.getTerminationDTO().getCurrTick());
                        if (!isPauseSelected.get()) {
                            seconds.set(finalCurrSimulationDTO.getTerminationDTO().getCurrSecond());
                        }
                        // TODO: 18/09/2023
                        //setEntitiesOnClick(currSimulationDTO);
//                        updateProgress(currSimulationDTO.getTerminationDTO().getTicks(), engine.getWorldDTO().getTerminationDTO().getTicks());
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException interrupted) {
                        if (isCancelled()) {
                            break;
                        }
                    }
                    isSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
                }
                return engine.getPastSimulationDTO(simulationId);
            }
        };
        Thread th = new Thread(simulationUpdateTask);
        th.setDaemon(true);
        th.start();
        simulationUpdateTask.setOnSucceeded((event -> {
            // simulation finished make buttons disabled..
        }));

    }



    public void setMainController(AppController appController) {
        this.mainController = appController;
        recentSimulations = appController.getRecentSimulations();
        simulationList.setItems(recentSimulations);
    }

    public void onStopButton(ActionEvent actionEvent) {
        isStopSelected.set(true);
        if (simulationList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int id = simulationList.getSelectionModel().getSelectedItem().getId();
        engine.stopSimulationByID(id);
    }

    public void onResumeButton(ActionEvent actionEvent) {
        if (simulationList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int id = simulationList.getSelectionModel().getSelectedItem().getId();
        engine.resumeSimulationByID(id);
    }

    public void onPauseButton(ActionEvent actionEvent) {
        isPauseSelected.set(true);
        if (simulationList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int id = simulationList.getSelectionModel().getSelectedItem().getId();
        engine.pauseSimulationByID(id);
    }

    public void onRerunButton(ActionEvent actionEvent) {
        if (simulationList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int id = simulationList.getSelectionModel().getSelectedItem().getId();
        mainController.reRunFromId(id);
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}

// Add more methods and logic for your results page as needed