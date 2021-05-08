package com.github.erfanara.Hextetris.HexaminoShapes;

import com.github.erfanara.Hextetris.GameBoard;

import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;

public class RegHexagon extends Polygon {
    protected final static double radius = GameBoard.HEXAGON_RADIUS;
    protected int[] columnRow;

    private void addPoints(double[] center) {
        // the amount to rotate for each vertex (in radians)
        double theta = 2.0 * Math.PI / 6;

        // compute x and y coordinates, centered at the origin
        for (int i = 0; i < 6; i++) {
            this.getPoints().add(center[0] + Math.round(radius * Math.cos(i * theta)));
            this.getPoints().add(center[1] + Math.round(radius * Math.sin(i * theta)));
        }
    }

    public int getColumn() {
        return columnRow[0];
    }

    public int getRow() {
        return columnRow[1];
    }

    public RegHexagon(int[] columnRow) {
        this.addPoints(GameBoard.convertToCoord(columnRow));
        this.columnRow = columnRow;
    }

    // TODO : this function is faster than GameBoard.ConvertToCoord() way?
    public double[] getCoordInScene() {
        Bounds boundInScene = this.localToScene(this.getBoundsInLocal());
        return new double[] { boundInScene.getCenterX(), boundInScene.getCenterY() };
    }

    public void remove() {
        ((Hexamino) (this.getParent())).remove(this);
    }

    public void moveDown() {
        this.setLayoutY(this.getLayoutY() + GameBoard.HEXAGON_HEIGHT);
        this.columnRow[1]++;
    }

}