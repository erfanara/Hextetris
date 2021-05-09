package com.github.erfanara.Hextetris;

import com.github.erfanara.Hextetris.HexaminoShapes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

public final class MainGui {
    private Scene mainScene;
    private HBox root;
    private Pane leftPane;
    private Pane rightPane;
    private VBox rightVBox;
    private Hexamino currentHexamino;
    private Timeline tl;
    private byte countMoveDownNotWorked;
    private Label scoreLabel;
    private static boolean pauseAndPlaySwitch = true;
    private int score;

    private void moveDownNotWorked() {
        this.countMoveDownNotWorked++;
    }

    private void setMoveDownNotWorked(byte moveDownNotWorked) {
        this.countMoveDownNotWorked = moveDownNotWorked;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    private Hexamino respawnHexamino() {
        Hexamino a = new PurpuleL4();

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
        leftPane.getChildren().add(a);
        return a;
    }

    private void createHexagonSheet() {
        for (int i = 0; i < GameBoard.X_SIZE; i++) {
            for (int j = 0; j < GameBoard.Y_SIZE; j++) {
                RegHexagon tmp = new RegHexagon(new int[] { i, j });
                tmp.setFill(Color.WHITE);
                tmp.setStroke(Color.BLACK);
                leftPane.getChildren().add(tmp);
            }
        }
    }

    private void gameOver() {
        Label gameOver = new Label("GAME OVER");
        gameOver.setFont(new Font(27));
        gameOver.setTranslateX(25);
        gameOver.setEffect(new Glow(1));
        gameOver.setTextFill(Color.RED);
        rightVBox.getChildren().add(gameOver);
    }

    private void mainTimeLine() {
        currentHexamino = respawnHexamino();
        tl = new Timeline(new KeyFrame(new Duration(700), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!currentHexamino.moveDown()) {
                    moveDownNotWorked();
                }
                if (countMoveDownNotWorked == 2) {
                    if (!GameBoard.submit(currentHexamino)) {
                        tl.stop();
                        leftPane.setEffect(new GaussianBlur());
                        mainScene.setOnKeyPressed(null);
                        gameOver();
                    } else {
                        if (GameBoard.clearCompletedRows()) {
                            setScore(getScore() + 10);
                            scoreLabel.setText("SCORE: " + score);
                        }
                        currentHexamino = respawnHexamino();
                        setMoveDownNotWorked((byte) 0);
                    }
                }
            };
        }));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    private void pauseAndPlay() {
        if (pauseAndPlaySwitch) {
            tl.pause();
            leftPane.setEffect(new GaussianBlur());
            pauseAndPlaySwitch = false;
        } else {
            tl.play();
            leftPane.setEffect(null);
            pauseAndPlaySwitch = true;
        }
    }

    private void setOnKeyPressed(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
            case W:
                currentHexamino.rotateClockWise();
                break;
            case D:
                currentHexamino.moveRight();
                break;
            case A:
                currentHexamino.moveLeft();
                break;
            case S:
                currentHexamino.moveDown();
                break;
            case P:
                pauseAndPlay();
                break;
            default:
                break;
            }
        });
    }

    MainGui(Stage stage) {
        leftPane = new Pane();
        rightPane = new Pane();
        createHexagonSheet();

        score = 0;
        scoreLabel = new Label("SCORE: " + score);
        scoreLabel.setMinSize(250, 50);
        scoreLabel.setEffect(new Glow());
        scoreLabel.setFont(new Font(25));
        scoreLabel.setTranslateX(10);

        Separator sep0 = new Separator(Orientation.HORIZONTAL);
        rightVBox = new VBox(scoreLabel, sep0);
        rightPane.getChildren().add(rightVBox);

        HBox.setHgrow(leftPane, Priority.NEVER);
        HBox.setHgrow(rightPane, Priority.NEVER);

        Separator sep1 = new Separator(Orientation.VERTICAL);
        root = new HBox(leftPane, sep1, rightPane);

        mainTimeLine();
        mainScene = new Scene(root);
        setOnKeyPressed(mainScene);
        stage.setScene(mainScene);
    }
}
