// HAZARD :::> TODO : we should split the project to many packages

package com.github.erfanara.Hextetris;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Hextris
 */
public class Hextetris extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PurpuleL4 a = new PurpuleL4();

        // Group root = new Group();
        Pane root = new Pane();
        Pane rightPane = new Pane();

        HBox.setHgrow(root, Priority.NEVER);
        HBox.setHgrow(rightPane, Priority.NEVER);

        HBox hBox = new HBox(root, rightPane);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 21; j++) {
                root.getChildren().add(new RegHexagon(new int[] { i, j }));
            }
        }

        root.getChildren().add(a);

        // for (int i = 0; i < GameBoard.Y_SIZE - 3; i++) {
        // a.moveDown();
        // }

        // for (RegHexagon i : a.shape) {
        // System.out.println(i.getColumnRow()[0] + " " + i.getColumnRow()[1]);
        // System.out.println(i.getCoordInScene()[0] + " " + i.getCoordInScene()[1]);
        // }
        Timeline tl = new Timeline(new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (RegHexagon i : a.shape) {
                    // System.out.println(i.getCoordInScene()[0] + " " + i.getCoordInScene()[1]);
                }
                System.out.println("------------------------");
                a.moveDown();
            };
        }));
        tl.setCycleCount(2);
        // tl.play();
        // System.out.println("H= " + GameBoard.HEXAGON_HEIGHT);
        // System.out.println("R= " + GameBoard.HEXAGON_RADIUS);

        Scene x = new Scene(hBox);

        x.setOnKeyPressed(e -> {
            switch (e.getCode()) {
            case W:
                // if (GameBoard.isMoveAllowed(a, Movement.ROTATE))
                    a.rotateClockWise();
                break;
            case D:
                // if (GameBoard.isMoveAllowed(a, Movement.RIGHT))
                    a.moveRight();
                break;
            case A:
                // if (GameBoard.isMoveAllowed(a, Movement.LEFT))
                    a.moveLeft();
                break;
            case S:
                // if (GameBoard.isMoveAllowed(a, Movement.DOWN))
                    a.moveDown();
                break;

            default:
                break;
            }
        });
        primaryStage.setScene(x);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
    }
}