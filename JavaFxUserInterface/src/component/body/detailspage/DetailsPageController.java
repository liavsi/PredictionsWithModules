package component.body.detailspage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.RuleDTO;
import DTOManager.impl.WorldDTO;
import DTOManager.impl.actionDTO.ActionDTO;
import component.body.detailspage.component.entity.EntityDetailsController;
import component.body.detailspage.component.property.PropertyDetailsController;
import component.body.detailspage.component.rule.RuleDetailsController;
import component.mainapp.AppController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class DetailsPageController {

    @FXML
    private AppController mainAppController;
    @FXML
    private FlowPane details;
    @FXML
    private TreeView<String> treeView;

    private WorldDTO world;
    private static final String Property_Details_FXML_RESOURCE = "/component/body/detailspage/component/property/propertyDetailsView.fxml";
    private static final String Entity_Details_FXML_RESOURCE = "/component/body/detailspage/component/entity/entityDetailsView.fxml";
    private static final String Rule_Details_FXML_RESOURCE = "/component/body/detailspage/component/rule/ruleDetailsView.fxml";
    public void setMainController(AppController appController) {
        this.mainAppController = appController;
    }


    @FXML
    public void initialize() {
    }


    public void worldMenu() {
        TreeItem<String> worldRoot = new TreeItem<>("World");
        TreeItem<String> entitiesNode = new TreeItem<>("Entities");
        TreeItem<String> envVarsNode = new TreeItem<>("Environment Variables");
        TreeItem<String> rulesNode = new TreeItem<>("Rules");
        TreeItem<String> generalsNode = new TreeItem<>("Generals");
        treeView.setRoot(worldRoot);
        worldRoot.getChildren().addAll(entitiesNode, envVarsNode, rulesNode, generalsNode);
        for (EntityDefinitionDTO entityDefinitionDTO : world.getNameToEntityDefinitionDTO().values()) {
            TreeItem<String> entityNode = new TreeItem<>(entityDefinitionDTO.getName());
            entitiesNode.getChildren().add(entityNode);
            for (PropertyDefinitionDTO propertyDefinitionDTO : entityDefinitionDTO.getPropertiesDTO()) {
                TreeItem<String> propertyNode = new TreeItem<>(propertyDefinitionDTO.getName());
                entityNode.getChildren().add(propertyNode);
            }
        }
        for (PropertyDefinitionDTO envVarsDTO : world.getEnvPropertiesDefinitionDTO()) {
            TreeItem<String> envVarNode = new TreeItem<>(envVarsDTO.getName());
            envVarsNode.getChildren().add(envVarNode);
        }
        for (RuleDTO ruleDTO:world.getRulesDTO()){
            TreeItem<String> ruleNode = new TreeItem<>(ruleDTO.getName());
            rulesNode.getChildren().add(ruleNode);
            for (ActionDTO actionDTO: ruleDTO.getActions()){
                TreeItem<String> actionNode = new TreeItem<>(actionDTO.getActionType());
                ruleNode.getChildren().add(actionNode);
            }
        }
    }


    public void setWorld(WorldDTO worldDTO) {
        this.world = worldDTO;
    }
    private void loadItem(TreeItem<String> item) {
        if(item == null){
            return;
        }else if(item.getParent() != null && item.getParent().getValue().equals("Entities")){
            loadEntity(item);
        }else if (item.getParent()!= null && item.getParent().getParent() != null && item.getParent().getParent().getValue().equals("Entities")) {
            loadProperty(item);
        }else if(item.getParent() != null && item.getParent().getValue().equals("Rules")){
            loadRule(item);
        }else if(item.getParent() != null && item.getParent().getValue().equals("Environment Variables")){
            loadProperty(item);
        }
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEntity(TreeItem<String> item) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Entity_Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            EntityDetailsController entityDetailsController= loader.getController();
            entityDetailsController.setWorld(world);
            entityDetailsController.setEntityDetails(item);
            details.getChildren().clear();
            details.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRule(TreeItem<String> item) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Rule_Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            RuleDetailsController ruleDetailsController= loader.getController();
            ruleDetailsController.setWorld(world);
            ruleDetailsController.setRuleDetails(item);
            details.getChildren().clear();
            details.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProperty(TreeItem<String> item) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Property_Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            PropertyDetailsController propertyDetailsController= loader.getController();
            propertyDetailsController.setWorld(world);
            propertyDetailsController.setPropertyDetails(item);
            details.getChildren().clear();
            details.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void showItem(MouseEvent mouseEvent) {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null && item.getValue() != null) {
            loadItem(item);
        }
    }
}
