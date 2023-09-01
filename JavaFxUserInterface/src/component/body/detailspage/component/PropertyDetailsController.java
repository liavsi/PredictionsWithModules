package component.body.detailspage.component;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;

import java.awt.*;

public class PropertyDetailsController {


    @FXML public Label LabelPropertyTitle;
    @FXML public Label LabelPropertyName;
    @FXML public Label LabelPropertyType;
    @FXML public Label LabelPropertyRange;

    private SimpleStringProperty propertyTitle;
    private SimpleStringProperty propertyName;
    private SimpleStringProperty propertyType;
    private SimpleStringProperty propertyRange;
    private WorldDTO world;

    public PropertyDetailsController(){
        this.propertyTitle = new SimpleStringProperty("");
        this.propertyName = new SimpleStringProperty("");
        this.propertyType = new SimpleStringProperty("");
        this.propertyRange = new SimpleStringProperty("");
    }
    @FXML
    private void initialize(){
        LabelPropertyTitle.textProperty().bind(propertyTitle);
        LabelPropertyName.textProperty().bind(propertyName);
        LabelPropertyType.textProperty().bind(propertyType);
        LabelPropertyRange.textProperty().bind(propertyRange);
    }
    public void setPropertyDetails(TreeItem<String> item) {
        propertyTitle.set("Entity Property");
        EntityDefinitionDTO entityDefinitionDTO = world.getEntityDefinitionDTOByName(item.getParent().getValue());
        PropertyDefinitionDTO propertyDefinitionDTO = entityDefinitionDTO.getPropertyDefinitionDTOByName(item.getValue());
        propertyName.set(propertyDefinitionDTO.getName());
        propertyType.set(propertyDefinitionDTO.getPropertyType());
        propertyRange.set(propertyDefinitionDTO.getFrom() + " - " + propertyDefinitionDTO.getTo());
    }
    public void setWorld(WorldDTO worldDTO){
        this.world = worldDTO;
    }
}
