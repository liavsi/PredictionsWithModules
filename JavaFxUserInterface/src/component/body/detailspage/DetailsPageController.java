package component.body.detailspage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.RuleDTO;
import DTOManager.impl.WorldDTO;
import DTOManager.impl.actionDTO.*;
import component.body.detailspage.component.action.calculation.CalculationDetailsController;
import component.body.detailspage.component.action.condition.ConditionDetailsController;
import component.body.detailspage.component.action.increasedecrease.IncreaseDecreaseDetailsController;
import component.body.detailspage.component.action.setkill.SetKillDetailsController;
import component.body.detailspage.component.entity.EntityDetailsController;
import component.body.detailspage.component.property.PropertyDetailsController;
import component.body.detailspage.component.rule.RuleDetailsController;
import component.mainapp.AppController;
import javafx.collections.ObservableList;
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
    private static final String IncreaseDecrease_Details_FXML_RESOURCE = "/component/body/detailspage/component/action/increasedecrease/increaseDecreaseDetailsView.fxml";
    private static final String Calculation_Details_FXML_RESOURCE = "/component/body/detailspage/component/action/calculation/calculationDetailsView.fxml";
    private static final String SetKill_Details_FXML_RESOURCE = "/component/body/detailspage/component/action/setkill/setKillDetailsView.fxml";
    private static final String Condition_Details_FXML_RESOURCE = "/component/body/detailspage/component/action/condition/conditionDetailsView.fxml";
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
        }else if(item.getParent() != null && item.getParent().getParent() != null && item.getParent().getParent().getValue().equals("Rules")){
            loadAction(item);
        }
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAction(TreeItem<String> item) {
        ObservableList<TreeItem<String>> children = item.getParent().getChildren();
        int index = children.indexOf(item);
        RuleDTO ruleDTO = world.getRuleDTOByName(item.getParent().getValue());
        ActionDTO actionDTO = ruleDTO.getActionByIndex(index);
        if(actionDTO instanceof IncreaseDTO || actionDTO instanceof DecreaseDTO) {
            loadIncreaseDecrease(actionDTO);
        } else if (actionDTO instanceof CalculationDTO) {
            loadCalculation((CalculationDTO) actionDTO);
        }else if (actionDTO instanceof KillDTO || actionDTO instanceof SetDTO) {
            loadSetKill(actionDTO);
        } else if (actionDTO instanceof ConditionActionDTO) {
            loadCondition((ConditionActionDTO)actionDTO);
        }

    }

    private void loadCondition(ConditionActionDTO conditionActionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Condition_Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            ConditionDetailsController conditionDetailsController = loader.getController();
            conditionDetailsController.setConditionDetails(conditionActionDTO);
            details.getChildren().clear();
            details.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSetKill(ActionDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(SetKill_Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            SetKillDetailsController setKillDetailsController = loader.getController();
            setKillDetailsController.setSetKillDetails(actionDTO);
            details.getChildren().clear();
            details.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCalculation(CalculationDTO calculationDTO) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Calculation_Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            CalculationDetailsController calculationDetailsController = loader.getController();
            calculationDetailsController.setCalculationDetails(calculationDTO);
            details.getChildren().clear();
            details.getChildren().add(detailsBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadIncreaseDecrease(ActionDTO actionDTO) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(IncreaseDecrease_Details_FXML_RESOURCE));
            GridPane detailsBox = loader.load();
            IncreaseDecreaseDetailsController increaseDecreaseDetailsController = loader.getController();
            increaseDecreaseDetailsController.setIncreaseDecreaseDetails(actionDTO);
            details.getChildren().clear();
            details.getChildren().add(detailsBox);
        } catch (IOException e) {
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
