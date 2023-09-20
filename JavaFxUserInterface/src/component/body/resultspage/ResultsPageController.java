package component.body.resultspage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.EntityInstanceDTO;
import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import component.mainapp.AppController;
import engine.api.Engine;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import utils.results.EntityPopulation;
import utils.results.SimulationOutcomeListCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public TableView<EntityPopulation> TableView;
    @FXML
    public TableColumn<EntityPopulation,String> EntityNameColumn;
    @FXML
    public TableColumn<EntityPopulation,Integer> PopulationColumn;
    @FXML private StackPane analyticData;
    @FXML private Button ButtonStatistic;
    @FXML private Button ButtonGraph;

    @FXML private VBox vBoxWithSimulations;
    @FXML
    private AnchorPane resultsPane;

    @FXML
    private ListView<SimulationOutcomeDTO> simulationList; // Assuming SimulationOutcomeDTO is the class for simulation outcomes

    @FXML
    private VBox simulationDetailsPane;

    @FXML
    private Button rerunButton;

    private SimpleBooleanProperty isSimulationRunning;
    private SimpleBooleanProperty isSimulationOver;
    Task<SimulationOutcomeDTO> simulationUpdateTask;
    private AppController mainController;
    private ObservableList<SimulationOutcomeDTO> recentSimulations;
    private ObservableList<ShowEntity> entities;

    private SimpleIntegerProperty ticks;
    private SimpleLongProperty seconds;
    private Pane graphPane;

    private Engine engine;
    public GridPane getMainView() {
        return mainView;
    }

    public ResultsPageController() {
        this.ticks = new SimpleIntegerProperty();
        this.entities = FXCollections.observableArrayList();
        this.seconds = new SimpleLongProperty();
        recentSimulations = FXCollections.observableArrayList();
        this.isSimulationRunning = new SimpleBooleanProperty(true);
        this.isSimulationOver = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        TickNumLabel.textProperty().bind(ticks.asString());
        SecondsLabel.textProperty().bind(seconds.asString());
        ButtonPause.disableProperty().bind(isSimulationRunning.not());
        ButtonResume.disableProperty().bind(Bindings.createBooleanBinding(() ->
                        isSimulationOver.get() || isSimulationRunning.get(),
                isSimulationOver, isSimulationRunning
        ));
        ButtonStop.disableProperty().bind(isSimulationRunning.not());
        ButtonGraph.disableProperty().bind(isSimulationOver.not());
        ButtonStatistic.disableProperty().bind(isSimulationOver.not());
        EntityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEntityName()));
        PopulationColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPopulation()).asObject());
        // Add more entities as needed
        // Set the data in the TableView


        // Initialize your controller
        simulationList.setCellFactory(param -> new SimulationOutcomeListCell());
        // Handle item selection in the list
        simulationList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showSimulationDetails(newValue); // Display details of the selected simulation outcome
        });
    }



    private void showSimulationDetails(SimulationOutcomeDTO selectedSimulation) {
        removeGraph();
        if (simulationUpdateTask != null) {
            simulationUpdateTask.cancel();
        }
        simulationUpdateTask = new Task<SimulationOutcomeDTO>() {

            @Override
            protected SimulationOutcomeDTO call()  {
                // Run your simulation here
                int simulationId = selectedSimulation.getId();
                SimulationOutcomeDTO currSimulationDTO = engine.getPastSimulationDTO(simulationId);
                boolean isCurrSimulationRunning = true;
                isCurrSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
                System.out.println("running task on simulation id:" + simulationId);
                System.out.println("current seconds" + currSimulationDTO.getTerminationDTO().getCurrSecond());
                while (isCurrSimulationRunning) {
                    currSimulationDTO = engine.getPastSimulationDTO(simulationId);
                    SimulationOutcomeDTO finalCurrSimulationDTO = currSimulationDTO;
                    if(isCancelled()){
                        break;
                    }
                    Platform.runLater(() -> {
                        ticks.set(finalCurrSimulationDTO.getTerminationDTO().getCurrTick());
                        if (isSimulationRunning.get()) {
                            seconds.set(finalCurrSimulationDTO.getTerminationDTO().getCurrSecond());
                        }
                        isSimulationRunning.set(!(finalCurrSimulationDTO.isPause() || finalCurrSimulationDTO.isStop()));
                        isSimulationOver.set(finalCurrSimulationDTO.isStop());
                        updateTable(finalCurrSimulationDTO.getEntityInstanceDTOS().getInstancesDTO());
                    });
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException interrupted) {
                        if (isCancelled()) {
                            break;
                        }
                    }
                    isCurrSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
                    if(isSimulationOver.get()){
                        break;
                    }
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

    private void updateTable(Map<Integer, EntityInstanceDTO> instancesDTO) {
        List<EntityPopulation> entityPopulations = updateTableColumns(instancesDTO);
        TableView.getItems().clear();
        TableView.getItems().addAll(entityPopulations);
    }

    private List<EntityPopulation> updateTableColumns(Map<Integer, EntityInstanceDTO> instancesDTO) {
        List<EntityPopulation> entityPopulations = new ArrayList<>();
        Map<String, Long> countMap = instancesDTO.values().stream()
                .collect(Collectors.groupingBy(EntityInstanceDTO::getName, Collectors.counting()));
        // Print the counts
        countMap.forEach((name, count) -> entityPopulations.add(new EntityPopulation(name, count.intValue())));
        return entityPopulations;
    }


    public void setMainController(AppController appController) {
        this.mainController = appController;
        recentSimulations = appController.getRecentSimulations();
        simulationList.setItems(recentSimulations);
        recentSimulations.addListener((ListChangeListener<SimulationOutcomeDTO>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    // Select the last added item in the ListView
                    simulationList.getSelectionModel().selectLast();
                }
            }
        });
    }

    public void onStopButton(ActionEvent actionEvent) {
        if (simulationList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int id = simulationList.getSelectionModel().getSelectedItem().getId();
        engine.stopSimulationByID(id);
    }

    public void onResumeButton(ActionEvent actionEvent) {
        if (simulationList.getSelectionModel().getSelectedItem() == null ) {
            return;
        }
        int id = simulationList.getSelectionModel().getSelectedItem().getId();
        engine.resumeSimulationByID(id);
    }

    public void onPauseButton(ActionEvent actionEvent) {
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

    public void onShowGraphClicked(ActionEvent event) {
        SimulationOutcomeDTO simulationOutcomeDTO = simulationList.getSelectionModel().getSelectedItem();
        loadGraph(simulationOutcomeDTO.getDataAroundTicks());
    }

    private void loadGraph(Map<Integer, EntityInstanceManagerDTO> dataAroundTicks)  {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/component/body/resultspage/graphView.fxml"));
            graphPane = loader.load();
            GraphViewController graphViewController = loader.getController();
            // Customize any data or logic you want to pass to the ResultsPageController
            // For example, you can set recent simulations:
            graphViewController.setMainController(this);
            graphViewController.setData(dataAroundTicks);
            // Set the ResultsPage as the center of the BorderPane
            //dynamicVBox.getChildren().clear();
            analyticData.getChildren().add(graphPane);
            graphPane.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeGraph() {
        if (graphPane != null) {
            analyticData.getChildren().remove(graphPane);
            graphPane = null;
        }

    }
}

// Add more methods and logic for your results page as needed