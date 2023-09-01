package component.body.detailspage;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import component.body.detailspage.component.PropertyDetailsController;
import component.mainapp.AppController;
import engine.api.Engine;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
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
    private static final String Property_Details_FXML_RESOURCE = "/component/body/detailspage/component/propertyDetailsView.fxml";
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
    }


    public void setWorld(WorldDTO worldDTO) {
        this.world = worldDTO;
    }

//    public void showItem(MouseEvent mouseEvent) {
//        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
//        if (item != null && item.getValue() != null){
//            loadItem(item);
//        }
//
//    }


    private void loadItem(TreeItem<String> item) {
        try {
            if (item.getParent().getParent() != null && item.getParent().getParent().getValue().equals("Entities")) {
                loadProperty(item);
            } else if (item.getParent() != null && item.getParent().getValue().equals("Entities")) {
                //entity
            }
        } catch (Exception e) {
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
