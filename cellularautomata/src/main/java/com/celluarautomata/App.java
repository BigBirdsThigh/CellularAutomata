package com.celluarautomata;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private GridPane pane = new GridPane();

    @Override
    public void start(Stage stage) throws IOException {
        Button button1 = new Button("hi");
        Rectangle rect = new Rectangle(30, 30);
        pane.add(rect, 1, 0);
        pane.setGridLinesVisible(true);

        pane.setVgap(10);
        Scene scene = new Scene(pane, 240, 100);
        stage.setScene(scene);
        stage.show();
        // Set an event handler to move the rectangle when clicked
        rect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                test(event, rect);
            }
        });
    }

    public void test(MouseEvent e, Rectangle rect) {

        // Remove the rectangle from its current position
        pane.getChildren().remove(rect);

        // Add the rectangle to the new position (e.g., position (1, 1))
        pane.add(rect, 1, 1); // Change these coordinates as needed

    }

    // static void setRoot(String fxml) throws IOException {
    // scene.setRoot(loadFXML(fxml));
    // }

    // private static Parent loadFXML(String fxml) throws IOException {
    // FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml +
    // ".fxml"));
    // return fxmlLoader.load();
    // }

    public static void main(String[] args) {
        launch();
    }

}