package com.juliankominiak.chess;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static com.juliankominiak.chess.Board.*;

public class Main extends Application {
    public static Stage stage;
    public static Scene scene;
    public static Board board;

    //Initializes the game.
    private static Parent initialize() throws FileNotFoundException {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        board = new Board();
        board.createBoard();
        root.getChildren().addAll(tileGroup, pieceGroup);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        scene = new Scene(initialize());
        primaryStage.setTitle("Chess");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Initializes the game again, in order to play the next game.
    public static void playAgain() throws FileNotFoundException {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        board = new Board();
        board.createBoard();
        root.getChildren().addAll(tileGroup, pieceGroup);
        Scene scene = new Scene(root);
        stage.setTitle("Chess");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

