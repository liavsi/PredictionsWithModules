package component.body.resultspage.statistic;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.EntityInstanceDTO;
import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import component.body.resultspage.ResultsPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticData {

    @FXML
    private AnchorPane entityHistogramPane;

    @FXML
    private ChoiceBox<String> entityChoiceBox;

    @FXML
    private ChoiceBox<String> propertyChoiceBox;

    @FXML
    private Button showHistogramButton;

    @FXML
    private Label histogramResultLabel;

    // Define your data and methods to populate the choice boxes here
    private List<EntityDefinitionDTO> entityDefinitions;
    private List<PropertyDefinitionDTO> selectedEntityProperties;
    private ResultsPageController resultsPageController;
    private Map<Integer, EntityInstanceManagerDTO> dataAroundTicks;


    @FXML
    private void initialize() {
        entityDefinitions = new ArrayList<>(); // Populate with your data
        selectedEntityProperties = new ArrayList<>(); // Populate with your data
    }

    private void populateEntityChoiceBox() {
        entityChoiceBox.getItems().clear();
        for (EntityDefinitionDTO entityDefinition : entityDefinitions) {
            entityChoiceBox.getItems().add(entityDefinition.getName());
        }
        entityChoiceBox.setValue(entityChoiceBox.getItems().get(0)); // Set default selection
    }

    private void populatePropertyChoiceBox() {
        propertyChoiceBox.getItems().clear();
        for (PropertyDefinitionDTO propertyDefinition : selectedEntityProperties) {
            propertyChoiceBox.getItems().add(propertyDefinition.getName());
        }
        propertyChoiceBox.setValue(propertyChoiceBox.getItems().get(0)); // Set default selection
    }
    public void setMainController(ResultsPageController resultsPageController) {
        this.resultsPageController = resultsPageController;
    }

    // Method to set and display entity statistic data
    public void setData(Map<Integer, EntityInstanceManagerDTO> dataAroundTicks) {
//        this.dataAroundTicks = dataAroundTicks;
//        entityDefinitions.(dataAroundTicks.get().stream()
//                .collect(Collectors.groupingBy(EntityInstanceDTO::getName, Collectors.counting())))
//        populateEntityChoiceBox();
//        populatePropertyChoiceBox();
    }

    @FXML
    public void onBackFromStatisticButton(ActionEvent event) {
        resultsPageController.removeStatisticView();
    }

    @FXML
    private void onShowHistogramButtonClicked(ActionEvent event) {
        String selectedEntityName = entityChoiceBox.getValue();
        String selectedPropertyName = propertyChoiceBox.getValue();

        // Implement your logic to display the histogram for the selected entity and property
        int histogramValue = getHistogramValue(selectedEntityName, selectedPropertyName);

        histogramResultLabel.setText(selectedPropertyName + " appears in " + histogramValue + " entities after the simulation");
    }

    // Implement your logic to calculate the histogram value
    private int getHistogramValue(String selectedEntityName, String selectedPropertyName) {
        // Replace this with your actual logic
        return 42; // Example value
    }

}

