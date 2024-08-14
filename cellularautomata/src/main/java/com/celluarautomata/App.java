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
    private boolean cellDestroyed = false; // keep track of if cell is alive

    @Override
    public void start(Stage stage) throws IOException {

        Button button1 = new Button("hi");
        int[][] test = new int[8][8]; // board to store context of scene
        test[3][2] = 1;
        test[3][4] = 1;

        Rectangle rect = new Rectangle(30, 30);

        AtomicInteger i = new AtomicInteger(3);
        AtomicInteger j = new AtomicInteger(2);

        // pane.add(rect, 1, 0);
        pane.setGridLinesVisible(true);
        genRects(test, pane); // it is possible to draw rectangles based on array

        Scene scene = new Scene(pane, 240, 240);
        int l = positions.size();

        stage.setScene(scene);
        // AtomicInteger i = new AtomicInteger(0); // numbers that will be increased
        // must be handled concurrently

        stage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {

                KeyCode key = arg0.getCode();
                if (key == KeyCode.CAPS) {
                    System.out.println(positions);
                }
                if (!cellDestroyed) { // if our cell is killed it can no longer move
                    if (key == KeyCode.W) {
                        test[i.get()][j.get()] = 0;
                        test[i.get()][j.decrementAndGet()] = 1;

                        genRects(test, pane);
                        System.out.println(positions);
                    }

                    if (key == KeyCode.A) {
                        test[i.get()][j.get()] = 0;
                        test[i.decrementAndGet()][j.get()] = 1;

                        genRects(test, pane);
                        System.out.println(positions);
                    }

                    if (key == KeyCode.S) {
                        test[i.get()][j.get()] = 0;
                        test[i.get()][j.incrementAndGet()] = 1;

                        genRects(test, pane);
                        System.out.println(positions);
                    }

                    if (key == KeyCode.D) {
                        test[i.get()][j.get()] = 0;
                        test[i.incrementAndGet()][j.get()] = 1;

                        genRects(test, pane);
                        System.out.println(positions);
                    }
                    Iterator<Pair<Integer, Integer>> iter = positions.iterator();
                    while (iter.hasNext()) {
                        Pair<Integer, Integer> position = iter.next();
                        System.out.println("NEW Pos: " + position);
                        int neighbors = neighbors(position, test); // generate neighbors for a position
                        int x = position.getKey(); // get x position
                        int y = position.getValue(); // get y position

                        // System.out.println(neighbors);
                        if (neighbors >= 1) { // check if the neighbors is in a threshold (e.g = 2 move cell, = 0 kill
                                              // cell)
                            // but in this case we're just killing
                            System.out.println("Neighbors: " + neighbors + " x: " + x + "y: " + y);
                            test[x][y] = 0; // set it to 0, killing it
                            System.out.println(test[x][y]);
                            genRects(test, pane); // redraw board
                            cellDestroyed = true; // kill the cell
                            System.out.println("Deleting Cell: " + x + y);
                        } else {
                            System.out.println(neighbors);
                        }
                    }
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
        int neighbors = 0;
        int x = p.getKey();
        int y = p.getValue();

        // Check for boundaries and count live neighbors around the cell
        if (x > 0 && board[x - 1][y] == 1) { // above
            neighbors++;
        }
        if (y < board[0].length - 1 && board[x][y + 1] == 1) { // right
            neighbors++;
        }
        if (x < board.length - 1 && board[x + 1][y] == 1) { // below
            neighbors++;
        }
        if (y > 0 && board[x][y - 1] == 1) { // left
            neighbors++;
        }

        return neighbors;
    }

    public void genRects(int[][] board, GridPane pane) {
        // first loop rows
        positions.clear(); // clear positions at start to avoid duplicates
        for (int i = 0; i < 8; i++) {
            // then columns
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) { // if board = 1, draw a black rectangle(cell)
                    pane.add(new Rectangle(30, 30), i, j);

                    Pair<Integer, Integer> p = new Pair<Integer, Integer>(i, j); // create position
                    positions.add(p); // store its position
                } else { // draw a white one to maintain grid layout
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