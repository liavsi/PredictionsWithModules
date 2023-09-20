package component.mainapp;

import engine.api.Engine;
import engine.impl.EngineImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utils.errors.AlertToScreen;

import java.io.IOException;
import java.net.URL;

public class TasksMain extends Application {

    private static final String PRIMARY_STAGE_NAME ="Prediction app";
    private static String APP_FXML_INCLUDE_RESOURCE = "mainApp.fxml";
    @Override
    public void start(Stage primaryStage) {
        // CSSFX.start();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(APP_FXML_INCLUDE_RESOURCE);
        fxmlLoader.setLocation(url);
        Parent root = null;
        // give the controllers access to the engine
        try {
            root = fxmlLoader.load(url.openStream());//
        } catch (IOException e) {
            AlertToScreen.showErrorDialog(e);
        }
        AppController controller = fxmlLoader.getController();
        //engine needs to know the controller
        //setPrimeryStage?
        Scene scene = new Scene(root, 600, 600);//
        // Set the Scene and show the Stage
        primaryStage.setTitle(PRIMARY_STAGE_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
        //engine needs to know the controllers?
    }

    public static void main(String[] args) {
        launch(args);
    }
}
