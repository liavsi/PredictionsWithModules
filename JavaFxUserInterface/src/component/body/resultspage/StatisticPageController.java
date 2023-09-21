package component.body.resultspage;

import DTOManager.impl.*;
import engine.api.Engine;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class StatisticPageController {

    @FXML
    public TreeView<String> TreeViewStatistic;
    @FXML
    public Label LabelHistogram;
    @FXML
    public Label LabelDontChange;
    @FXML
    public Label LabelAverage;
    @FXML
    public GridPane statisticGrid;
    private Engine engine;

    private SimulationOutcomeDTO simulationOutcomeDTO;
    private ResultsPageController resultPageController;

    //    public StatisticPageController(){
//        histogram = new SimpleStringProperty("");
//        average = new SimpleStringProperty("");
//        dontChange = new SimpleStringProperty("");
//    }
//    @FXML
//    private void initialize(){
//        LabelHistogram.textProperty().bind(histogram);
//        LabelAverage.textProperty().bind(average);
//        LabelDontChange.textProperty().bind(dontChange);
//    }
    public void statisticMenu(){
        TreeItem<String> root = new TreeItem<>("Entities");
        TreeViewStatistic.setRoot(root);
        for (EntityDefinitionDTO entityDefinitionDTO: engine.getWorldDTO().getNameToEntityDefinitionDTO().values()){
            TreeItem<String> entityNode = new TreeItem<>(entityDefinitionDTO.getName());
            root.getChildren().add(entityNode);
            for (PropertyDefinitionDTO propertyDefinitionDTO : entityDefinitionDTO.getPropertiesDTO()) {
                TreeItem<String> propertyNode = new TreeItem<>(propertyDefinitionDTO.getName());
                entityNode.getChildren().add(propertyNode);
            }
        }
    }

    public void setSimulationOutcomeDTO(SimulationOutcomeDTO simulationOutcomeDTO) {
        this.simulationOutcomeDTO = simulationOutcomeDTO;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void showStatisticData(MouseEvent mouseEvent) {
        TreeItem<String> item = TreeViewStatistic.getSelectionModel().getSelectedItem();
        if (item != null && item.getValue() != null && !item.getValue().equals("Entities") && !item.getParent().getValue().equals("Entities")){
            showPropertyStatistic(item);
        }
    }

    private void showPropertyStatistic(TreeItem<String> item) {
        LabelAverage.setText("");
        LabelHistogram.setText("");
        LabelDontChange.setText("");
        Map<String,Integer> entityCounts = new HashMap<>();
        float sum = 0;
        int totalCount = 0;
        float sumEnd = 0.0F;
        int totalEnd = 0;
        Float averageEnd = 0.0f;
        for (EntityInstanceDTO entityInstanceDTO : simulationOutcomeDTO.getEntityInstanceDTOS().getInstancesDTO().values()) {
            for (PropertyInstanceDTO propertyInstanceDTO : entityInstanceDTO.getProperties().values()) {
                String propertyName = propertyInstanceDTO.getPropertyDefinitionDTO().getName();

                // Check if the propertyName matches item.getValue()
                if (propertyName.equals(item.getValue())) {
                    // Increment the count for the entityDefinition
                    String entityDefinitionName = entityInstanceDTO.getEntityDefinitionDTO().getName();
                    entityCounts.put(entityDefinitionName, entityCounts.getOrDefault(entityDefinitionName, 0) + 1);

                    if (propertyInstanceDTO.getValue() instanceof Integer || propertyInstanceDTO.getValue() instanceof Float){
                        sumEnd += (float) propertyInstanceDTO.getValue();
                        totalEnd++;
                    }else {
                        averageEnd = null;
                    }
                    for (Integer value : propertyInstanceDTO.getSameValueCounts()) {
                        sum += value; // Add the value to the sum
                        totalCount++; // Increment the count
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> entry : entityCounts.entrySet()) {
            String entityDefinitionName = entry.getKey();
            int count = entry.getValue();
            String labelText = LabelHistogram.getText();
            // Concatenate the new text with the existing text
            labelText += "Entity: " + entityDefinitionName + ", Count: " + count + "\n"; // You can use "\n" for a new line
            // Set the updated text to the label
            LabelHistogram.setText(labelText);
        }
        float average = (float) sum / totalCount;
        LabelDontChange.setText("The average number of ticks in which\n the value of the property: " + item.getValue() + "\ndidn't change is " + average);
        if (averageEnd != null){
            averageEnd = sumEnd / totalEnd;
            LabelAverage.setText("The average of the values of\n the property:" + item.getValue() + "\nin the final population is: " + averageEnd);
        }else {
            LabelAverage.setText("The property isn't a numeric value");
        }
    }

    public void onBackFromStatisticButton(ActionEvent actionEvent) {
        this.resultPageController.removeStatisticView();
    }

    public void setMainController(ResultsPageController resultsPageController) {
        this.resultPageController = resultsPageController;
    }
}
