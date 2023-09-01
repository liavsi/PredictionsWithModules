package component.mainapp;

import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.SimulationOutcomeDTO;
import DTOManager.impl.WorldDTO;
import component.body.detailspage.DetailsPageController;
import component.body.executionpage.SimulationPageController;
import component.header.HeaderController;
import engine.api.Engine;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public VBox dynamicVBox;

    @FXML private GridPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private DetailsPageController detailsPageComponentController;

    @FXML private SimulationPageController newExecutionPageComponentController;

    @FXML private BorderPane BorderPaneMain;

    private ExecutorService executor = Executors.newFixedThreadPool(4); // You can adjust the number of threads as needed


    private Engine engine;


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
            dynamicVBox.getChildren().clear();
            dynamicVBox.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
        detailsPageComponentController.organizeData();
    }

    public void onNewExecutionChosen() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(NEW_EXECUTION_FXML_RESOURCE));
            Pane newExecutionPane = loader.load();
            newExecutionPageComponentController = loader.getController();
            newExecutionPageComponentController.setMainController(this);
            newExecutionPageComponentController.setWorld(engine.getWorldDTO());
            dynamicVBox.getChildren().clear();
            dynamicVBox.getChildren().add(newExecutionPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newExecutionPageComponentController.organizeData();
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
        simulationTask.setOnSucceeded(event ->{
            SimulationOutcomeDTO simulationOutcomeDTO = simulationTask.getValue();
            showSimulationResults(simulationOutcomeDTO);
        });
    }

    private void showSimulationResults(SimulationOutcomeDTO simulationOutcomeDTO) {
        System.out.println("finished simulation");
    }
}
