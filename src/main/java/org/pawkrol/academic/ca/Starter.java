package org.pawkrol.academic.ca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by pawkrol on 4/28/17.
 */
public class Starter extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();

        URL resource = getClass().getClassLoader()
                .getResource("layout.fxml");
        if (resource == null)
            throw new Exception("Cannot load a layout");

        Parent root = fxmlLoader.load(resource.openStream());

        primaryStage.setTitle("Cellular Automata - Paweł Król");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
