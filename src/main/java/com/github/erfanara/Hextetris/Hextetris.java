// HAZARD :::> TODO : we should split the project to many packages

package com.github.erfanara.Hextetris;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
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

    byte moveDownNotWorked;

    void moveDownNotWorked() {
        this.moveDownNotWorked++;
    }

    public void setMoveDownNotWorked(byte moveDownNotWorked) {
        this.moveDownNotWorked = moveDownNotWorked;
    }

    HexaMino newHexaMino() {
        HexaMino a = new PurpuleL4();

        Random r = new Random();
        switch (r.nextInt(7)) {
        case 0:
            // a = new PurpuleL4();
            break;
        case 1:
            a = new RedStar4();
            break;
        case 2:
            a = new Yellowl4();
            break;
        case 3:
            a = new PinkL4();
            break;
        case 4:
            a = new OrangeO4();
            break;
        case 5:
            a = new GreenZ4();
            break;
        case 6:
            a = new BlueJ4();
            break;
        default:
            break;
        }
        pane.getChildren().add(a);
        return a;
    }

    Timeline tl;
    Pane pane;
    HexaMino b;
    static boolean switch1 = true;

    void pp() {
        if (switch1) {
            tl.pause();
            switch1 = false;
        } else {
            tl.play();
            switch1 = true;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Group root = new Group();
        pane = new Pane();
        Separator sep = new Separator(Orientation.VERTICAL);
        Pane rightPane = new Pane();

        HBox.setHgrow(pane, Priority.NEVER);
        HBox.setHgrow(rightPane, Priority.NEVER);

        HBox root = new HBox(pane, sep, rightPane);

        for (int i = 0; i < GameBoard.X_SIZE; i++) {
            for (int j = 0; j < GameBoard.Y_SIZE; j++) {
                RegHexagon tmp = new RegHexagon(new int[] { i, j });
                tmp.setFill(Color.WHITE);
                tmp.setStroke(Color.BLACK);
                pane.getChildren().add(tmp);
            }
        }

        b = newHexaMino();
        tl = new Timeline(new KeyFrame(new Duration(700), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!b.moveDown()) {
                    moveDownNotWorked();
                }
                if (moveDownNotWorked == 2) {
                    GameBoard.submit(b);
                    // GameBoard.print();
                    GameBoard.clearCompletedRows();
                    b = newHexaMino();
                    setMoveDownNotWorked((byte) 0);
                }
            };
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        Scene x = new Scene(root);
        x.setOnKeyPressed(e -> {
            switch (e.getCode()) {
            case W:
                b.rotateClockWise();
                break;
            case D:
                b.moveRight();
                break;
            case A:
                b.moveLeft();
                break;
            case S:
                b.moveDown();
                break;
            case P:
                pp();
                break;
            case K:
                GameBoard.print();
                break;
            default:
                break;
            }
        });
        primaryStage.setScene(x);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.show();
        primaryStage.requestFocus();
    }
}