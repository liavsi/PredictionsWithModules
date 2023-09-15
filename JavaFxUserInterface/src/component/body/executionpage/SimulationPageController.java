package component.body.executionpage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import com.sun.deploy.panel.IProperty;
import component.mainapp.AppController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.inputFields.LabelBooleanInputBox;
import utils.inputFields.LabelNumericInputBox;
import utils.inputFields.LabelTextBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationPageController {


    @FXML private Pane mainView;
    @FXML private Button clearButton;
    @FXML private Button startSimulationButton;
    @FXML private VBox rightVbox;
    @FXML private VBox leftVbox;
    private SimpleIntegerProperty gridArea;
    private WorldDTO world;
    private List<PropertyDefinitionDTO> environmentVariablesDefinition;
    private Map<String, EntityDefinitionDTO> entityDefinitionByName;

    private final Map<String, ObjectProperty> resultEnvironment = new HashMap<>();
    private AppController appController;


    public void setWorld(WorldDTO worldDTO) {
        this.world = worldDTO;
        this.environmentVariablesDefinition = world.getEnvPropertiesDefinitionDTO();
        this.entityDefinitionByName = world.getNameToEntityDefinitionDTO();
    }

    public void organizeData() {
        organizeEnvironmentVariablesData();
        organizePopulationData();
    }
    private void organizePopulationData() {
        IntegerProperty maxPopulation = new SimpleIntegerProperty(world.getGridDTO().getArea());
        DoubleProperty sumPopulation = new SimpleDoubleProperty(0.0); // Initialize sum to 0

        for (EntityDefinitionDTO entityDefinitionDTO : entityDefinitionByName.values()) {
            HBox pair = null;
            LabelNumericInputBox inputPlaceNumber = new LabelNumericInputBox(entityDefinitionDTO.getName(), 0, maxPopulation.doubleValue(), 0);

            // Bind inputPlaceNumber to sumPopulation
            inputPlaceNumber.valueProperty().addListener((observable, oldValue, newValue) -> {
                // Update the sumPopulation whenever an inputPlaceNumber changes
                sumPopulation.set(sumPopulation.get() - oldValue.doubleValue() + newValue.doubleValue());
            });

            resultEnvironment.put(entityDefinitionDTO.getName() + "entity", new SimpleObjectProperty<>(inputPlaceNumber.valueProperty().intValue()));
            inputPlaceNumber.valueProperty().bindBidirectional(resultEnvironment.get(entityDefinitionDTO.getName()+"entity"));
            pair = inputPlaceNumber;
            if (pair == null) {
                throw new RuntimeException("expected pair to have a value");
            }
            leftVbox.getChildren().add(pair);
        }

        String goodMessage = "Max Population in the simulation is: " + maxPopulation.intValue();
        StringProperty alert = new SimpleStringProperty(goodMessage);
        Label alertLabel = new Label("");
        alertLabel.textProperty().bind(alert);
        leftVbox.getChildren().add(alertLabel);

        // Bind the alert message and enable/disable button based on sumPopulation
        sumPopulation.addListener((observable, oldValue, newValue) -> {
            int tooMuch = -1 * (maxPopulation.intValue() - newValue.intValue());
            if (maxPopulation.intValue() - newValue.intValue() < 0) {
                alert.set("You need to lower your population by " + tooMuch);
                startSimulationButton.setDisable(true);
            } else {
                startSimulationButton.setDisable(false);
                alert.set(goodMessage);
            }
        });
    }

//    private void organizePopulationData() {
//        IntegerProperty maxPopulation = new SimpleIntegerProperty(world.getGridDTO().getArea());
//        List<DoubleProperty> sumPopulation = new ArrayList<>();
//        for (EntityDefinitionDTO entityDefinitionDTO : entityDefinitionByName.values()) {
//            HBox pair = null;
//            LabelNumericInputBox inputPlaceNumber = new LabelNumericInputBox(entityDefinitionDTO.getName(), 0, maxPopulation.doubleValue(), 0); // Initialize with a default value within the specified range
//            resultEnvironment.put(entityDefinitionDTO.getName() + "entity", new SimpleObjectProperty<>(inputPlaceNumber.valueProperty().intValue()));
//            inputPlaceNumber.valueProperty().bindBidirectional(resultEnvironment.get(entityDefinitionDTO.getName()+"entity"));
//            sumPopulation.add(inputPlaceNumber.valueProperty());
//            pair = inputPlaceNumber;
//            if (pair == null) {
//                throw new RuntimeException("expected pair to have a value");
//            }
//            leftVbox.getChildren().add(pair);
//        }
//        String goodMessage = "Max Population in the simulation is: " + maxPopulation.intValue();
//        StringProperty alert = new SimpleStringProperty(goodMessage);
//        Label alertLabel =new Label("");
//        alertLabel.textProperty().bind(alert);
//        leftVbox.getChildren().add(alertLabel);
//        DoubleBinding result = Bindings.createDoubleBinding(() -> sumPopulation.stream().mapToDouble(DoubleProperty::get).sum(), sumPopulation.toArray(new DoubleProperty[0]));
//        result.addListener(((observable, oldValue, newValue) -> {
//            int tooMuch =-1*(maxPopulation.intValue() - newValue.intValue());
//            if (maxPopulation.intValue() - newValue.intValue() < 0) {
//                alert.set("You need to lower your population by " + tooMuch);
//                startSimulationButton.setDisable(true);
//            }
//            else {
//                startSimulationButton.setDisable(false);
//                alert.set(goodMessage);
//            }
//        }));
//        result.get();
//    }

    private void organizeEnvironmentVariablesData() {
        for(PropertyDefinitionDTO env: environmentVariablesDefinition) {
            HBox pair = null;
            switch (env.getPropertyType()) {
                case "STRING":
                    LabelTextBox inputPlaceString = new LabelTextBox(env.getName(), "");
                    resultEnvironment.put(env.getName(), new SimpleObjectProperty<>("")); // Initialize with an empty string
                    inputPlaceString.textProperty().bindBidirectional(resultEnvironment.get(env.getName()));
                    pair = inputPlaceString;
                    break;
                case "FLOAT":
                case "DECIMAL":
                    LabelNumericInputBox inputPlaceNumber = new LabelNumericInputBox(env.getName(), env.getFrom(), env.getTo(), (env.getTo() - env.getFrom()) / 2); // Initialize with a default value within the specified range
                    resultEnvironment.put(env.getName(), new SimpleObjectProperty<>(inputPlaceNumber.valueProperty().get()));
                    inputPlaceNumber.valueProperty().bindBidirectional(resultEnvironment.get(env.getName()));
                    pair = inputPlaceNumber;
                    break;
                case "BOOLEAN":
                    LabelBooleanInputBox inputPlaceBool = new LabelBooleanInputBox(env.getName(), false);
                    resultEnvironment.put(env.getName(), new SimpleObjectProperty<>(false)); // Initialize with false
                    inputPlaceBool.valueProperty().bindBidirectional(resultEnvironment.get(env.getName()));
                    pair = inputPlaceBool;
                    break;
                default:
                    throw new RuntimeException("failed in organize data of SimulationPageController");
            }
            if(pair == null) {
                throw new RuntimeException("expected pair to have a value");
            }
            rightVbox.getChildren().add(pair);
        }
    }

    public void setMainController(AppController appController) {
        this.appController = appController;
    }
    public void onClickedStartSimulation(ActionEvent event) {
        Map<String, Object> resToEngine = resultEnvironment.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() == null ? null : entry.getValue().get()
                ));
        appController.startSimulationInEngine(resToEngine);
    }

    public void onClickedClearButton(ActionEvent event) {
    }

    public Parent getMainView() {
        return mainView;
    }



}
