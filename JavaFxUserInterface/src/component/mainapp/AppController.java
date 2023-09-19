package component.mainapp;

import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.WorldDTO;
import com.sun.javafx.collections.ObservableListWrapper;
import component.body.detailspage.DetailsPageController;
import component.body.executionpage.SimulationPageController;
import component.body.resultspage.ResultsPageController;
import component.header.HeaderController;
import engine.api.Engine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppController {
    private static final String Details_FXML_RESOURCE = "/component/body/detailspage/detailsPageView.fxml";
    private static final String NEW_EXECUTION_FXML_RESOURCE = "/component/body/executionpage/newExecutionView.fxml";
    private static final String RESULTS_FXML_RESOURCE = "/component/body/resultspage/resultView.fxml";
    private static final int MILISEC_TASK_SEND_UPDATES = 100;
    @FXML
    public GridPane dynamicAnchorPane;
    @FXML
    private VBox dynamicVBox;

    @FXML
    private GridPane headerComponent;
    @FXML
    private HeaderController headerComponentController;
    @FXML
    private DetailsPageController detailsPageComponentController;

    @FXML
    private SimulationPageController newExecutionPageComponentController;

    @FXML
    private ResultsPageController resultsPageController;

    @FXML
    private BorderPane BorderPaneMain;
    private final ExecutorService executor = Executors.newFixedThreadPool(4); // You can adjust the number of threads as needed
    private Engine engine;
    private ObservableList<SimulationOutcomeDTO> recentSimulations = FXCollections.observableArrayList();
    private Map<Integer, Map<String,Object>> resToEngineForSimulationId = new HashMap<>();

    public AppController() {
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null) {
            headerComponentController.setMainController(this);
        }
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        this.engine.fileNameProperty().bind(headerComponentController.getFilePath());
    }

    public void setFileNameToEngine(SimpleStringProperty filePath) {
        engine.fileNameProperty().bind(filePath);
    }

    public void onDetailsChosen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            detailsPageComponentController = loader.getController();
            detailsPageComponentController.setWorld(engine.getWorldDTO());
            detailsPageComponentController.worldMenu();
            dynamicAnchorPane.getChildren().clear();
            dynamicAnchorPane.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //detailsPageComponentController.organizeData();
    }

    public void loadResultsPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(RESULTS_FXML_RESOURCE));
            GridPane resultsPage = loader.load();
            resultsPageController = loader.getController();
            // Customize any data or logic you want to pass to the ResultsPageController
            // For example, you can set recent simulations:
            resultsPageController.setMainController(this);
            resultsPageController.setEngine(engine);
            // Set the ResultsPage as the center of the BorderPane
            //dynamicVBox.getChildren().clear();
            dynamicAnchorPane.getChildren().clear(); // Now clear the VBox
            dynamicAnchorPane.getChildren().add(resultsPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onNewExecutionChosen() {
        switchToNewExecutionPage();
    }

    public void readWorld() {
        engine.readWorldFromXml();
    }

    public void startSimulationInEngine(Map<String, Object> resToEngine) {
        switchToResultsPage();
        SimulationOutcomeDTO simulationOutcomeDTO = engine.runNewSimulation(resToEngine);
        int simulationId = simulationOutcomeDTO.getId();
        //  keep to rerun simulation by id Number
        resToEngineForSimulationId.put(simulationId,resToEngine);
        recentSimulations.add(simulationOutcomeDTO);
        headerComponentController.setIsIsThereSimulationOutCome(true);
    }

//        SimulationOutcomeDTO simulationOutcomeDTO = engine.runNewSimulation(resToEngine);
//        int simulationId = simulationOutcomeDTO.getId();
//        recentSimulations.put(simulationId, simulationOutcomeDTO);
//        headerComponentController.setIsIsThereSimulationOutCome(true);
//        Thread thread = new Thread(()->{
//            boolean isSimulationRunning = true;
//            isSimulationRunning = !(simulationOutcomeDTO.getTerminationDTO().isSecondsTerminate() || simulationOutcomeDTO.getTerminationDTO().isTicksTerminate());
//            while (isSimulationRunning) {
//                SimulationOutcomeDTO currSimulationDTO = engine.getPastSimulationDTO(simulationId);
//                Platform.runLater(() -> {
//                    recentSimulations.put(simulationId, currSimulationDTO);
//                });
//                isSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
//                try {
//                    Thread.sleep(100000000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        thread.start();
//        switchToResultsPage();


//        SimulationOutcomeDTO simulationOutcomeDTO = engine.runNewSimulation(resToEngine);
//        int simulationId = simulationOutcomeDTO.getId();
//        recentSimulations.put(simulationId, simulationOutcomeDTO);
//        headerComponentController.setIsIsThereSimulationOutCome(true);
//        switchToResultsPage();
//        boolean isSimulationRunning = true;
//        isSimulationRunning = !(simulationOutcomeDTO.getTerminationDTO().isSecondsTerminate() || simulationOutcomeDTO.getTerminationDTO().isTicksTerminate());
//        while (isSimulationRunning) {
//            SimulationOutcomeDTO currSimulationDTO = engine.getPastSimulationDTO(simulationId);
//            Platform.runLater(() -> {
//                recentSimulations.put(simulationId, currSimulationDTO);
//            });
//            isSimulationRunning = !(currSimulationDTO.getTerminationDTO().isSecondsTerminate() || currSimulationDTO.getTerminationDTO().isTicksTerminate());
//        }
//    }

    public void switchToNewExecutionPage() {
        if (newExecutionPageComponentController != null) {
            dynamicAnchorPane.getChildren().clear();
            dynamicAnchorPane.getChildren().add(newExecutionPageComponentController.getMainView());
        } else {
            loadNewExecutionPage();
        }
    }

    private void loadNewExecutionPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(NEW_EXECUTION_FXML_RESOURCE));
            GridPane newExecutionPane = loader.load();
            newExecutionPageComponentController = loader.getController();
            newExecutionPageComponentController.setMainController(this);
            newExecutionPageComponentController.setWorld(engine.getWorldDTO());
            newExecutionPageComponentController.organizeData();
            dynamicAnchorPane.getChildren().clear();
            dynamicAnchorPane.getChildren().add(newExecutionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToResultsPage() {
        if (resultsPageController != null) {
            dynamicAnchorPane.getChildren().clear();
            dynamicAnchorPane.getChildren().add(resultsPageController.getMainView());
        } else {
            loadResultsPage();
        }
    }


    public ObservableList<SimulationOutcomeDTO> getRecentSimulations() {
        return recentSimulations;
    }

    public void startSimulationInEngine(int id) {
        if(!resToEngineForSimulationId.containsKey(id))
        {
            throw new RuntimeException("got an id that doesnt have resToEngine");
        }
        Map<String,Object> resToEngine = resToEngineForSimulationId.get(id);
        SimulationOutcomeDTO simulationOutcomeDTO = engine.runNewSimulation(resToEngine);
        int simulationId = simulationOutcomeDTO.getId();
        //  keep to rerun simulation by id Number
        resToEngineForSimulationId.put(simulationId,resToEngine);
        recentSimulations.add(simulationOutcomeDTO);
        headerComponentController.setIsIsThereSimulationOutCome(true);
        switchToResultsPage();
    }

    public void reRunFromId(int id) {
        Map<String,Object> resToEngine = resToEngineForSimulationId.get(id);
        newExecutionPageComponentController.organizeData(resToEngine);
        switchToNewExecutionPage();
    }

}
