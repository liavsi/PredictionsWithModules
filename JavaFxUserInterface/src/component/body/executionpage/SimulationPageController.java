package component.body.executionpage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.EntityInstanceManagerDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import component.mainapp.AppController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

    private final Map<String, ObjectProperty> resultEnvironment = new HashMap<>();
    private AppController appController;


    public void setWorld(WorldDTO worldDTO) {
        this.world = worldDTO;
        this.environmentVariablesDefinition = world.getEnvPropertiesDefinitionDTO();
    }

    public void organizeData() {
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
