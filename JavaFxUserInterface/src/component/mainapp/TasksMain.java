package component.mainapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TasksMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        // CSSFX.start();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/component/header/headerView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root, 800, 600);

        // Set the Scene and show the Stage
        primaryStage.setTitle("JavaFX App with Scene Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
