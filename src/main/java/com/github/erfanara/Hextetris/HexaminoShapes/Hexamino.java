package com.github.erfanara.Hextetris.HexaminoShapes;

import com.github.erfanara.Hextetris.GameBoard;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public abstract class Hexamino extends Group {
    public RegHexagon[] shape;
    protected double[] firstCoordOfCenter;

    // For temporary uses in moving methods
    private int[][] newColumnRows;

    public Hexamino(int... hexagonArr) {
        this.shape = new RegHexagon[hexagonArr.length / 2];
        for (int i = 0; i < hexagonArr.length / 2; i++) {
            this.shape[i] = new RegHexagon(new int[] { hexagonArr[i * 2], hexagonArr[i * 2 + 1] });
            this.getChildren().add(this.shape[i]);
        }

        newColumnRows = new int[this.shape.length][2];

        this.firstCoordOfCenter = this.shape[0].getCoordInScene();
    }

    public void remove(RegHexagon a) {
        this.getChildren().remove(a);
    }

    public boolean moveDown() {
        // Checking is Move Allowed or not
        for (RegHexagon i : this.shape) {
            if (!GameBoard.isValidAndEmpty(i.columnRow[0], i.columnRow[1] + 1))
                return false;
        }

        this.setLayoutY(this.getLayoutY() + GameBoard.HEXAGON_HEIGHT);
        for (RegHexagon i : this.shape) {
            i.columnRow[1]++;
        }
        return true;
    }

    public void moveRight() {
        // Copy current columnRows to newColumnRows arr
        for (int i = 0; i < this.shape.length; i++) {
            this.newColumnRows[i][0] = this.shape[i].columnRow[0];
            this.newColumnRows[i][1] = this.shape[i].columnRow[1];
        }
        // finding the newColumnRows of RegHexagons for new move
        byte parityOf1 = (byte) (this.shape[1].columnRow[0] % 2);
        for (int[] columnRow : this.newColumnRows) {
            if (columnRow[0] % 2 != parityOf1) {
                columnRow[1] += 1 - 2 * parityOf1;
            }
            columnRow[0]++;

            // Now checks the move is allowed or not
            if (!GameBoard.isValidAndEmpty(columnRow[0], columnRow[1]))
                return;
        }
        // copy newColumnRows to current columnRows of RegHexagons
        for (int i = 0; i < this.shape.length; i++) {
            this.shape[i].columnRow[0] = this.newColumnRows[i][0];
            this.shape[i].columnRow[1] = this.newColumnRows[i][1];
        }

        double[] x1 = this.shape[1].getCoordInScene();
        double[] x2 = GameBoard.convertToCoord(new int[] { this.shape[1].columnRow[0], this.shape[1].columnRow[1] });
        this.setLayoutX(this.getLayoutX() + (x2[0] - x1[0]));
        this.setLayoutY(this.getLayoutY() + (x2[1] - x1[1]));
    }

    public void moveLeft() {
        // Copy current columnRows to newColumnRows arr
        for (int i = 0; i < this.shape.length; i++) {
            this.newColumnRows[i][0] = this.shape[i].columnRow[0];
            this.newColumnRows[i][1] = this.shape[i].columnRow[1];
        }
        // finding the newColumnRows of RegHexagons for new move
        byte parityOf1 = (byte) (this.shape[1].columnRow[0] % 2);
        for (int[] columnRow : this.newColumnRows) {
            if (columnRow[0] % 2 != parityOf1) {
                columnRow[1] += 1 - 2 * parityOf1;
            }
            columnRow[0]--;

            // Now checks the move is allowed or not
            if (!GameBoard.isValidAndEmpty(columnRow[0], columnRow[1]))
                return;

        }
        // copy newColumnRows to current columnRows of RegHexagons
        for (int i = 0; i < this.shape.length; i++) {
            this.shape[i].columnRow[0] = this.newColumnRows[i][0];
            this.shape[i].columnRow[1] = this.newColumnRows[i][1];
        }

        double[] x1 = this.shape[1].getCoordInScene();
        double[] x2 = GameBoard.convertToCoord(new int[] { this.shape[1].columnRow[0], this.shape[1].columnRow[1] });
        this.setLayoutX(this.getLayoutX() + (x2[0] - x1[0]));
        this.setLayoutY(this.getLayoutY() + (x2[1] - x1[1]));
    }

    /*
     * This function has a special formula to calculate the new column Row of the
     * hexagons
     */
    public void rotateClockWise() {
        for (int i = 1; i < this.shape.length; i++) {
            this.newColumnRows[i][0] = (int) Math.round((this.shape[0].columnRow[0] + this.shape[i].columnRow[0]) / 2.0
                    + (this.shape[0].columnRow[1] - this.shape[i].columnRow[1])
                    + (this.shape[0].columnRow[0] % 2 - this.shape[i].columnRow[0] % 2) / 2.0);

            this.newColumnRows[i][1] = (int) Math.round((this.shape[0].columnRow[1] - this.shape[i].columnRow[1]) / 2.0
                    + (this.shape[0].columnRow[0] % 2 - this.shape[i].columnRow[0] % 2) / 4.0
                    - (this.newColumnRows[i][0] % 2 - this.shape[i].columnRow[0] % 2) / 2.0
                    - 3 * (this.shape[0].columnRow[0] - this.shape[i].columnRow[0]) / 4.0 + this.shape[i].columnRow[1]);

            // Now checks the move is allowed or not
            if (!GameBoard.isValidAndEmpty(this.newColumnRows[i][0], this.newColumnRows[i][1]))
                return;
        }

        for (int i = 1; i < this.shape.length; i++) {
            this.shape[i].columnRow[0] = this.newColumnRows[i][0];
            this.shape[i].columnRow[1] = this.newColumnRows[i][1];
        }

        for (RegHexagon i : this.shape) {
            Rotate x = new Rotate(60, firstCoordOfCenter[0], firstCoordOfCenter[1]);
            i.getTransforms().add(x);
        }
    }

}