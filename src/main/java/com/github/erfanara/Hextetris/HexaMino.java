package com.github.erfanara.Hextetris;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

class RegHexagon extends Polygon {
    protected final static double radius = GameBoard.HEXAGON_RADIUS;

    private void addPoints(double[] center) {
        // the amount to rotate for each vertex (in radians)
        double theta = 2.0 * Math.PI / 6;

        // compute x and y coordinates, centered at the origin
        for (int i = 0; i < 6; i++) {
            this.getPoints().add(center[0] + Math.round(radius * Math.cos(i * theta)));
            this.getPoints().add(center[1] + Math.round(radius * Math.sin(i * theta)));
        }
    }

    RegHexagon(int[] columnRow) {
        this.addPoints(GameBoard.convertToCoord(columnRow));
    }

    // TODO : this function is faster than GameBoard.ConvertToCoord() way?
    double[] getCoordInScene() {
        Bounds boundInScene = this.localToScene(this.getBoundsInLocal());
        return new double[] { boundInScene.getCenterX(), boundInScene.getCenterY() };
    }

}

// TODO : we doubt about the Group , maybe we should use pane or layout or ?
abstract class HexaMino extends Group {
    protected RegHexagon[] shape;
    protected double[] firstCoordOfCenter;

    HexaMino() {

    }

    void moveDown() {
        this.setTranslateY(this.getTranslateY() + GameBoard.HEXAGON_HEIGHT);
        // this.getTransforms().add(new Translate(0,GameBoard.HEXAGON_HEIGHT));

    }

    void moveRight() {
        double[] x1 = this.shape[1].getCoordInScene();
        int[] columnRow = GameBoard.convertToColumnRow(x1);
        double[] x2 = GameBoard
                .convertToCoord(new int[] { columnRow[0] + 1, columnRow[1] });
        this.setTranslateX(this.getTranslateX() + (x2[0] - x1[0]));
        this.setTranslateY(this.getTranslateY() + (x2[1] - x1[1]));

    }

    void moveLeft() {
        double[] x1 = this.shape[1].getCoordInScene();
        int[] columnRow = GameBoard.convertToColumnRow(x1);
        double[] x2 = GameBoard
                .convertToCoord(new int[] { columnRow[0] - 1, columnRow[1] });
        this.setTranslateX(this.getTranslateX() + (x2[0] - x1[0]));
        this.setTranslateY(this.getTranslateY() + (x2[1] - x1[1]));

    }

    void rotateClockWise() {
        this.getTransforms().add(new Rotate(60, firstCoordOfCenter[0], firstCoordOfCenter[1]));
    }

}

class PurpuleL4 extends HexaMino {

    PurpuleL4() {
        super.shape = new RegHexagon[4];

        // Center of Shape is on index 0
        // Top Hexagon of Shape is on index 1
        // We will save this rule in other methods too
        super.shape[0] = new RegHexagon(new int[] { 1, 1 });
        super.shape[1] = new RegHexagon(new int[] { 1, 0 });
        super.shape[2] = new RegHexagon(new int[] { 1, 2 });
        super.shape[3] = new RegHexagon(new int[] { 0, 1 });

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        // TODO
        // super.centerCoord = GameBoard
        // .getLayout(new int[] { super.shape[centerIndex].getColumn(),
        // super.shape[centerIndex].getRow() });

        for (int i = 0; i < 4; i++) {
            super.shape[i].setFill(Color.PURPLE);
            super.shape[i].setStroke(Color.WHITE);
            this.getChildren().add(super.shape[i]);
        }

    }

}