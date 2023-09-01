package utils.results;

import DTOManager.impl.SimulationOutcomeDTO;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SimulationOutcomeListCell extends ListCell<SimulationOutcomeDTO> {
    private final VBox container;
    private final Text idText;
    private final Text runDateText;

    public SimulationOutcomeListCell() {
        container = new VBox();
        idText = new Text();
        runDateText = new Text();
        container.getChildren().addAll(idText, runDateText);
        container.setSpacing(5); // Adjust spacing as needed
    }

    @Override
    protected void updateItem(SimulationOutcomeDTO item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            // Set the text values for id and runDate
            idText.setText("ID: " + item.getId());
            runDateText.setText("Run Date: " + item.getRunDate());

            // Set the cell's graphic to the container
            setGraphic(container);
        }
    }
}