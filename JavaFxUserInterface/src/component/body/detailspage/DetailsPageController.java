package component.body.detailspage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.RuleDTO;
import DTOManager.impl.WorldDTO;
import component.mainapp.AppController;
import engine.api.Engine;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsPageController implements Initializable {
    
    @FXML private AppController mainAppController;
    @FXML private FlowPane details;
    @FXML private TreeView<String> treeView;

    private Engine engine;

    public void setMainController(AppController appController) {
        this.mainAppController = appController;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void showDetailsForWorld() {
        WorldDTO worldDTO = engine.getWorldDTO();
        // TODO: 28/08/2023 implement this method 
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //bindings
        this.engine = (Engine) resources.getObject("Engine");
    }
    public void worldMenu(){
        WorldDTO worldDTO = engine.getWorldDTO();
        TreeItem<String> world = new TreeItem<>("World");
        TreeItem<String> entities = new TreeItem<>("Entities");
        TreeItem<String> envVars = new TreeItem<>("Environment Variables");
        TreeItem<String> rules = new TreeItem<>("Rules");
        TreeItem<String> termination = new TreeItem<>("Terminations");
        treeView.setRoot(world);
        world.getChildren().addAll(entities,envVars,rules,termination);
        for (EntityDefinitionDTO entityDefinitionDTO: worldDTO.getNameToEntityDefinitionDTO().values()){
            TreeItem<String> entity = new TreeItem<>(entityDefinitionDTO.getName());
            entities.getChildren().add(entity);
        }
        for (PropertyDefinitionDTO propertyDefinitionDTO: worldDTO.getEnvPropertiesDefinitionDTO()){
            TreeItem<String> envVar = new TreeItem<>(propertyDefinitionDTO.getName());
            envVars.getChildren().add(envVar);
        }
        for (RuleDTO ruleDTO:worldDTO.getRulesDTO()){
            TreeItem<String> rule = new TreeItem<>(ruleDTO.getName());
            rules.getChildren().add(rule);
        }

        //
    }
}
