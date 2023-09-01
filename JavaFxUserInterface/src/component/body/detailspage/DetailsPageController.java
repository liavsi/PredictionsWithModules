package component.body.detailspage;

import DTOManager.impl.WorldDTO;
import component.mainapp.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;

public class DetailsPageController {
    
    @FXML private AppController mainAppController;
    @FXML private FlowPane details;
    @FXML private TreeView<String> treeView;

    private WorldDTO world;

    public void setMainController(AppController appController) {
        this.mainAppController = appController;
    }




    @FXML
    public void initialize() {
    }


    public void organizeData(){
        TreeItem<String> worldTreeView = new TreeItem<>("World");
        TreeItem<String> entitiesTreeView = new TreeItem<>("Entities");
        TreeItem<String> envVarsTreeView = new TreeItem<>("Environment Variables");
        TreeItem<String> rulesTreeView = new TreeItem<>("Rules");
        TreeItem<String> terminationTreeView = new TreeItem<>("Terminations");
        treeView.setRoot(worldTreeView);
        worldTreeView.getChildren().addAll(entitiesTreeView,envVarsTreeView,rulesTreeView,terminationTreeView);

//        for (EntityDefinitionDTO entityDefinitionDTO: world.getNameToEntityDefinitionDTO().values()){
//            TreeItem<String> entity = new TreeItem<>(entityDefinitionDTO.getName());
//            entities.getChildren().add(entity);
//        }
        //////
    }


    public void setWorld(WorldDTO worldDTO) {
        this.world = worldDTO;
    }
}
