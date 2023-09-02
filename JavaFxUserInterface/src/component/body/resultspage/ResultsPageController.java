package component.body.resultspage;

import DTOManager.impl.SimulationOutcomeDTO;
import com.sun.javafx.collections.ObservableListWrapper;
import component.mainapp.AppController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import utils.results.SimulationOutcomeListCell;

import java.util.ArrayList;
import java.util.List;

public class ResultsPageController {

    @FXML private VBox vBoxWithSimulations;
    @FXML
    private AnchorPane resultsPane;

    @FXML
    private ListView<SimulationOutcomeDTO> simulationList; // Assuming SimulationOutcomeDTO is the class for simulation outcomes

    @FXML
    private VBox simulationDetailsPane;

    @FXML
    private Button rerunButton;

    @FXML
    private Parent mainView;

    private AppController mainController;
    private ObservableList<SimulationOutcomeDTO> recentSimulations;
    @FXML
    private void initialize() {
        // Initialize your controller
        simulationList.setCellFactory(param -> new SimulationOutcomeListCell());
        // Handle item selection in the list
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
        simulationList.setItems(recentSimulations);
    }

    public Parent getMainView() {
        return mainView;
    }
}

// Add more methods and logic for your results page as needed