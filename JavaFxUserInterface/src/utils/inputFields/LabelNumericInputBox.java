package utils.inputFields;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;


public class LabelNumericInputBox extends HBox {
    private Label label;
    private Spinner<Integer> spinner;
    private SimpleIntegerProperty valueProperty;

    public LabelNumericInputBox(String labelText, int minValue, int maxValue, int initialValue) {
        // Create label
        label = new Label(labelText);

        // Create spinner with specified range and initial value
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, initialValue);
        spinner = new Spinner<>(valueFactory);

        // Bind the value property of the spinner to a SimpleIntegerProperty
        valueProperty = new SimpleIntegerProperty(initialValue);
        spinner.getValueFactory().valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            valueProperty.set(newValue);
        });
        // Add label and spinner to the HBox
        getChildren().addAll(label, spinner);

        // Set spacing and other styling as needed
        setSpacing(10); // Adjust spacing between label and spinner
    }

    public IntegerProperty valueProperty() {
        return valueProperty;
    }

    // You can add more methods to customize the behavior of this controller
}
