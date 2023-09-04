package component.body.detailspage.component.property;

import DTOManager.impl.EntityDefinitionDTO;
import DTOManager.impl.PropertyDefinitionDTO;
import DTOManager.impl.WorldDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import sun.font.TrueTypeFont;

import java.awt.*;

public class PropertyDetailsController {


    @FXML public Label LabelPropertyTitle;
    @FXML public Label LabelPropertyName;
    @FXML public Label LabelPropertyType;
    @FXML public Label LabelPropertyRange;
    @FXML public Label LabelIsRandom;

    private SimpleStringProperty propertyTitle;
    private SimpleStringProperty propertyName;
    private SimpleStringProperty propertyType;
    private SimpleStringProperty propertyRange;

    private SimpleStringProperty isRandom;
    private WorldDTO world;

    public PropertyDetailsController(){
        this.propertyTitle = new SimpleStringProperty("");
        this.propertyName = new SimpleStringProperty("");
        this.propertyType = new SimpleStringProperty("");
        this.propertyRange = new SimpleStringProperty("");
        this.isRandom = new SimpleStringProperty("");
    }
    @FXML
    private void initialize(){
        LabelPropertyTitle.textProperty().bind(propertyTitle);
        LabelPropertyName.textProperty().bind(propertyName);
        LabelPropertyType.textProperty().bind(propertyType);
        LabelPropertyRange.textProperty().bind(propertyRange);
        LabelIsRandom.textProperty().bind(isRandom);
    }
    public void setPropertyDetails(TreeItem<String> item) {
        EntityDefinitionDTO entityDefinitionDTO;
        PropertyDefinitionDTO propertyDefinitionDTO;
        if (item.getParent().getParent() != null && item.getParent().getParent().getValue().equals("Entities")) {
            entityDefinitionDTO = world.getEntityDefinitionDTOByName(item.getParent().getValue());
            propertyDefinitionDTO = entityDefinitionDTO.getPropertyDefinitionDTOByName(item.getValue());
            propertyTitle.set("Entity Property");
            isRandom.set(propertyDefinitionDTO.getRandomInitializer().toString().equals("true")? "Property is random initialize" : "Property isn't random initialize");
        }
        else{
            propertyDefinitionDTO = world.getEnvVarDTOByName(item.getValue());
            propertyTitle.set("Environment Variable");
        }
        propertyName.set(propertyDefinitionDTO.getName());
        propertyType.set(propertyDefinitionDTO.getPropertyType());
        propertyRange.set(propertyDefinitionDTO.getFrom() + " - " + propertyDefinitionDTO.getTo());
    }
    public void setWorld(WorldDTO worldDTO){
        this.world = worldDTO;
    }
}
