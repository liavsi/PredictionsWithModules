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
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AppController {
    private static final String Details_FXML_RESOURCE = "/component/body/detailspage/detailsPageView.fxml";
    private static final String NEW_EXECUTION_FXML_RESOURCE = "/component/body/executionpage/newExecutionView.fxml";
    private static final String RESULTS_FXML_RESOURCE = "/component/body/resultspage/resultView.fxml" ;


    @FXML private VBox dynamicVBox;

    @FXML private GridPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private DetailsPageController detailsPageComponentController;

    @FXML private SimulationPageController newExecutionPageComponentController;

    @FXML private ResultsPageController resultsPageController;

    @FXML private BorderPane BorderPaneMain;


    private ExecutorService executor = Executors.newFixedThreadPool(4); // You can adjust the number of threads as needed


    private Engine engine;
    private ObservableList<SimulationOutcomeDTO> recentSimulations = FXCollections.observableArrayList();

    public AppController() {
    }
    @FXML public void initialize() {
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

    private void changeDynamicDetailsScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL mainFxml = getClass().getResource("body/detailspage/detailsPageView.fxml");
        fxmlLoader.setLocation(mainFxml);
        VBox detailsBox = fxmlLoader.load();
        DetailsPageController detailsPageController = fxmlLoader.getController();
        dynamicVBox.getChildren().clear();
        dynamicVBox.getChildren().add(detailsBox);
    }
    public void onDetailsChosen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Details_FXML_RESOURCE));
            SplitPane detailsBox = loader.load();
            detailsPageComponentController = loader.getController();
            detailsPageComponentController.setWorld(engine.getWorldDTO());
            detailsPageComponentController.worldMenu();
            dynamicVBox.getChildren().clear();
            dynamicVBox.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //detailsPageComponentController.organizeData();
    }

    public void loadResultsPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(RESULTS_FXML_RESOURCE));
            Parent resultsPage = loader.load();
            resultsPageController = loader.getController();
            // Customize any data or logic you want to pass to the ResultsPageController
            // For example, you can set recent simulations:
            resultsPageController.setMainController(this);
            // Set the ResultsPage as the center of the BorderPane
            dynamicVBox.getChildren().clear();
            dynamicVBox.getChildren().add(resultsPage);
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

        Task<SimulationOutcomeDTO> simulationTask = new Task<SimulationOutcomeDTO>() {
            @Override
            protected SimulationOutcomeDTO call() throws Exception {
                // Run your simulation here
                SimulationOutcomeDTO simulationOutcomeDTO = engine.runNewSimulation(resToEngine);

                // You can update the progress here as needed
                updateProgress(1, 1); // Example: Completed

                return simulationOutcomeDTO;
            }
        };

        executor.submit(simulationTask);
        simulationTask.setOnSucceeded(event -> {
            SimulationOutcomeDTO simulationOutcomeDTO = simulationTask.getValue();
            recentSimulations.add(simulationOutcomeDTO);
            headerComponentController.setIsIsThereSimulationOutCome(true);
            switchToResultsPage();
        });
    }

    public void switchToNewExecutionPage() {
        if(newExecutionPageComponentController != null) {
            dynamicVBox.getChildren().clear();
            dynamicVBox.getChildren().add(newExecutionPageComponentController.getMainView());
        }
        else {
            loadNewExecutionPage();
        }
    }

    private void loadNewExecutionPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(NEW_EXECUTION_FXML_RESOURCE));
            Pane newExecutionPane = loader.load();
            newExecutionPageComponentController = loader.getController();
            newExecutionPageComponentController.setMainController(this);
            newExecutionPageComponentController.setWorld(engine.getWorldDTO());
            newExecutionPageComponentController.organizeData();
            dynamicVBox.getChildren().clear();
            dynamicVBox.getChildren().add(newExecutionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToResultsPage() {
        if(resultsPageController != null) {
            dynamicVBox.getChildren().clear();
            dynamicVBox.getChildren().add(resultsPageController.getMainView());
        }
        else {
            loadResultsPage();
        }
    }


    public ObservableList<SimulationOutcomeDTO> getRecentSimulations() {
        return recentSimulations;
    }
}
