package com.celluarautomata;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private GridPane pane = new GridPane();
    public List<Pair<Integer, Integer>> positions = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {

        Button button1 = new Button("hi");
        int[][] test = new int[8][8]; // board to store context of scene
        test[3][2] = 1;
        test[1][2] = 1;
        test[1][3] = 1;
        Rectangle rect = new Rectangle(30, 30);

        AtomicInteger i = new AtomicInteger(3);
        AtomicInteger j = new AtomicInteger(2);

        // pane.add(rect, 1, 0);
        pane.setGridLinesVisible(true);
        genRects(test, pane); // it is possible to draw rectangles based on array

        Scene scene = new Scene(pane, 240, 240);

        stage.setScene(scene);
        // AtomicInteger i = new AtomicInteger(0); // numbers that will be increased
        // must be handled concurrently
        System.out.println(neighbors(positions.get(0), test));
        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {

                KeyCode key = arg0.getCode();

                if (key == KeyCode.W) {
                    test[i.get()][j.get()] = 0;
                    test[i.get()][j.decrementAndGet()] = 1;
                    System.out.println(test[i.get()][j.get()]);
                    genRects(test, pane);
                    System.out.println(positions);
                }
            }

        });

        rect.setOnMouseClicked(new EventHandler<MouseEvent>() { // how to handle click events

            @Override
            public void handle(MouseEvent event) {

                test(event, rect, i);
            }
        });
    }

    public int neighbors(Pair<Integer, Integer> p, int[][] board) {
        AtomicInteger neighbors = new AtomicInteger(0);
        // for testing purposes we will allow cell to live if >2 neighbors

        int x = p.getKey();
        int y = p.getValue();

        if (board[x - 1][y] == 1) { // if square above
            neighbors.incrementAndGet();
        }
        if (board[x][y + 1] == 1) { // if square right
            neighbors.incrementAndGet();
        }
        if (board[x + 1][y] == 1) { // square down
            neighbors.incrementAndGet();
        }
        if (board[x][y - 1] == 1) {
            neighbors.incrementAndGet();
        }

        return neighbors.get();
    }

    public void genRects(int[][] board, GridPane pane) {
        // first loop rows
        positions.clear();
        for (int i = 0; i < 8; i++) {
            // then columns
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) {
                    pane.add(new Rectangle(30, 30), i, j);
                    System.out.println("Column: " + i + " Row: " + j);
                    Pair<Integer, Integer> p = new Pair<Integer, Integer>(i, j);
                    positions.add(p);
                } else {
                    Rectangle r = new Rectangle(30, 30);
                    r.setFill(Color.WHITESMOKE);
                    pane.add(r, i, j);
                }
            }
        }

    }

    public void test(MouseEvent e, Rectangle rect, AtomicInteger i) {
        // Remove the rectangle from its current position
        i.incrementAndGet();
        int x = i.get();
        pane.getChildren().remove(rect);
        System.out.println(i);
        // Add the rectangle to the new position (e.g., position (1, 1))
        pane.add(rect, 1, x); // Change these coordinates as needed

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