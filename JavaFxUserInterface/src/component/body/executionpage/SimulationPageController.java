package component.body.executionpage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import component.mainapp.AppController;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.inputFields.LabelBooleanInputBox;
import utils.inputFields.LabelNumericInputBox;
import utils.inputFields.LabelTextBox;

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
        clearData();
    }

    private void clearData() {
        clearEnvironmentVariablesData();
        clearPopulationData();
    }

    private void clearPopulationData() {
        for(Node pair: leftVbox.getChildren()) {
            if (!(pair instanceof HBox)){
                continue;
            }
            HBox HBpair = (HBox) pair;
            if (HBpair instanceof LabelNumericInputBox) {
                LabelNumericInputBox LTBpair = (LabelNumericInputBox) HBpair;
                LTBpair.valueProperty().set(0);
            }
        }
    }

    private void clearEnvironmentVariablesData() {
        for(Node pair: rightVbox.getChildren()) {
            if (!(pair instanceof HBox)){
                continue;
            }
            HBox HBpair = (HBox) pair;
            if (HBpair instanceof LabelTextBox) {
                LabelTextBox LTBpair = (LabelTextBox) HBpair;
                LTBpair.textProperty().set("");
            }
            if (HBpair instanceof LabelNumericInputBox) {
                LabelNumericInputBox LNIpair = (LabelNumericInputBox) HBpair;
                LNIpair.valueProperty().set(0);
            }
            if (HBpair instanceof LabelBooleanInputBox) {
                LabelBooleanInputBox LBIpair = (LabelBooleanInputBox) HBpair;
                LBIpair.valueProperty().set(false);
            }
        }
    }

    public Parent getMainView() {
        return mainView;
    }
    public void organizeData() {
        organizeEnvironmentVariablesData();
        organizePopulationData();
    }
    private void organizePopulationData() {
        IntegerProperty maxPopulation = new SimpleIntegerProperty(world.getGridDTO().getArea());
        double initialValue = 3;
        double sumInitialValue = initialValue * entityDefinitionByName.values().size();
        DoubleProperty sumPopulation = new SimpleDoubleProperty(sumInitialValue); // Initialize sum to 0
        for (EntityDefinitionDTO entityDefinitionDTO : entityDefinitionByName.values()) {
            HBox pair = null;
            LabelNumericInputBox inputPlaceNumber = new LabelNumericInputBox(entityDefinitionDTO.getName(), 0, maxPopulation.doubleValue(), initialValue);
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
            } else if(newValue.intValue() == 0){
                alert.set("can't run simulation with no entities at all..");
                startSimulationButton.setDisable(true);
            }
                else {
                startSimulationButton.setDisable(false);
                alert.set(goodMessage);
            }
        });
    }

    private void organizeEnvironmentVariablesData() {
        for(PropertyDefinitionDTO env: environmentVariablesDefinition) {
            HBox pair = null;
            switch (env.getPropertyType()) {
                case "STRING":
                    LabelTextBox inputPlaceString = new LabelTextBox(env.getName(), "");
                    ObjectProperty<String> objectProperty =  new SimpleObjectProperty<>("");
                    resultEnvironment.put(env.getName(),objectProperty); // Initialize with an empty string
                    inputPlaceString.textProperty().addListener(((observable, oldValue, newValue) -> {
                        objectProperty.setValue(newValue);
                    }));
                    pair = inputPlaceString;
                    break;
                case "FLOAT":
                case "DECIMAL":
                    LabelNumericInputBox inputPlaceNumber = new LabelNumericInputBox(env.getName(), env.getFrom(), env.getTo(), (env.getTo() - env.getFrom()) / 2); // Initialize with a default value within the specified range
                    ObjectProperty<Double> objectPropertyD =  new SimpleObjectProperty<>(inputPlaceNumber.valueProperty().get());
                    resultEnvironment.put(env.getName(),objectPropertyD); // Initialize with an empty string
                    inputPlaceNumber.valueProperty().addListener(((observable, oldValue, newValue) -> {
                        objectPropertyD.setValue(newValue.doubleValue());
                    }));
                    pair = inputPlaceNumber;
                    break;
                case "BOOLEAN":
                    LabelBooleanInputBox inputPlaceBool = new LabelBooleanInputBox(env.getName(), false);
                    ObjectProperty<Boolean> objectPropertyB =  new SimpleObjectProperty<>(inputPlaceBool.valueProperty().get());
                    resultEnvironment.put(env.getName(),objectPropertyB); // Initialize with an empty string
                    inputPlaceBool.valueProperty().addListener(((observable, oldValue, newValue) -> {
                        objectPropertyB.setValue(newValue.booleanValue());
                    }));
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
    public void organizeData(Map<String, Object> resToEngine) {
        organizeEnvironmentVariablesData(resToEngine);
        organizePopulationData(resToEngine);
    }

    private void organizePopulationData(Map<String, Object> resToEngine) {
        for(Node pair: leftVbox.getChildren()) {
            if (!(pair instanceof HBox)){
                continue;
            }
            HBox HBpair = (HBox) pair;
            if (HBpair instanceof LabelNumericInputBox) {
                LabelNumericInputBox LTBpair = (LabelNumericInputBox) HBpair;
                for (Map.Entry<String, Object> nameObj : resToEngine.entrySet()) {
                    String manipulatedString =LTBpair.getLabelText() +"entity";
                    if (manipulatedString.equals(nameObj.getKey())) {
                        LTBpair.valueProperty().set((Double) nameObj.getValue());
                    }
                }
            }

        }
    }

    private void organizeEnvironmentVariablesData(Map<String, Object> resToEngine) {
        for(Node pair: rightVbox.getChildren()) {
            if (!(pair instanceof HBox)){
                continue;
            }
            HBox HBpair = (HBox) pair;
            if (HBpair instanceof LabelTextBox) {
                LabelTextBox LTBpair = (LabelTextBox) HBpair;
                for (Map.Entry<String, Object> nameObj : resToEngine.entrySet()) {
                    if (LTBpair.getLabelText().equals(nameObj.getKey())) {
                        LTBpair.textProperty().set((String) nameObj.getValue());
                        continue;
                    }
                }
            }
            if (HBpair instanceof LabelNumericInputBox) {
                LabelNumericInputBox LNIpair = (LabelNumericInputBox) HBpair;
                for (Map.Entry<String, Object> nameObj : resToEngine.entrySet()) {
                    if (LNIpair.getLabelText().equals(nameObj.getKey())) {
                        LNIpair.valueProperty().set((Double) nameObj.getValue());
                        continue;
                    }
                }
            }
            if (HBpair instanceof LabelBooleanInputBox) {
                LabelBooleanInputBox LBIpair = (LabelBooleanInputBox) HBpair;
                for (Map.Entry<String, Object> nameObj : resToEngine.entrySet()) {
                    if (LBIpair.getLabelText().equals(nameObj.getKey())) {
                        LBIpair.valueProperty().set((Boolean) nameObj.getValue());
                        continue;
                    }
                }
            }
        }
    }
}
