// HAZARD :::> TODO : we should split the project to many packages

package com.github.erfanara.Hextetris;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
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
        // PurpuleL4 a = new PurpuleL4();
        // RedStar4 a = new RedStar4();
        // Yellowl4 a = new Yellowl4();
        // PinkL4 a = new PinkL4();
        // OrangeO4 a = new OrangeO4();
        // GreenZ4 a = new GreenZ4();
        BlueJ4 a = new BlueJ4();

        // Group root = new Group();
        Pane root = new Pane();
        Separator sep = new Separator(Orientation.VERTICAL);
        Pane rightPane = new Pane();

        HBox.setHgrow(root, Priority.NEVER);
        HBox.setHgrow(rightPane, Priority.NEVER);

        HBox hBox = new HBox(root, sep, rightPane);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 21; j++) {
                root.getChildren().add(new RegHexagon(new int[] { i, j }));
            }
        }

        root.getChildren().add(a);

        Timeline tl = new Timeline(new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                a.moveDown();
            };
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        Scene x = new Scene(hBox);

        x.setOnKeyPressed(e -> {
            switch (e.getCode()) {
            case W:
                a.rotateClockWise();
                break;
            case D:
                a.moveRight();
                break;
            case A:
                a.moveLeft();
                break;
            case S:
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