package com.github.erfanara.Hextetris;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

class RegHexagon extends Polygon {
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

    RegHexagon(int[] columnRow) {
        this.addPoints(GameBoard.convertToCoord(columnRow));
        this.columnRow = columnRow;
    }

    // TODO : this function is faster than GameBoard.ConvertToCoord() way?
    double[] getCoordInScene() {
        Bounds boundInScene = this.localToScene(this.getBoundsInLocal());
        return new double[] { boundInScene.getCenterX(), boundInScene.getCenterY() };
    }

    void remove() {
        ((HexaMino) (this.getParent())).remove(this);
    }

    void moveDown() {
        this.setLayoutY(this.getLayoutY() + GameBoard.HEXAGON_HEIGHT);
        this.columnRow[1]++;
    }

}

abstract class HexaMino extends Group {
    protected RegHexagon[] shape;
    protected double[] firstCoordOfCenter;

    // For temporary uses in moving methods
    private int[][] newColumnRows;

    HexaMino(int... hexagonArr) {
        this.shape = new RegHexagon[hexagonArr.length / 2];
        for (int i = 0; i < hexagonArr.length / 2; i++) {
            this.shape[i] = new RegHexagon(new int[] { hexagonArr[i * 2], hexagonArr[i * 2 + 1] });
            this.getChildren().add(this.shape[i]);
        }

        newColumnRows = new int[this.shape.length][2];
    }

    void remove(RegHexagon a) {
        this.getChildren().remove(a);
    }

    boolean moveDown() {
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

    // TODO : their is todo for moveRight and moveLeft that their is a exception
    // that should be implemented
    // but it was not tidy...
    void moveRight() {
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

    void moveLeft() {
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

    void rotateClockWise() {
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

class PurpuleL4 extends HexaMino {
    PurpuleL4() {
        // Center of Shape is on index 0
        //////////////////////////// // Top Hexagon of Shape is on index 1
        ////////////////////// // Later we'll change this to the bottom automatically in
        // rotate method
        super(1, 1, 1, 0, 1, 2, 0, 1);

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.PURPLE);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}

class RedStar4 extends HexaMino {
    RedStar4() {
        // Center of Shape is on index 0
        //////////////////////////// // Top Hexagon of Shape is on index 1
        ////////////////////// // Later we'll change this to the bottom automatically in
        // rotate method
        super(1, 1, 1, 0, 2, 2, 0, 2);

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.rgb(210, 0, 0));
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}

class Yellowl4 extends HexaMino {
    Yellowl4() {
        // Center of Shape is on index 0
        //////////////////////////// // Top Hexagon of Shape is on index 1
        ////////////////////// // Later we'll change this to the bottom automatically in
        // rotate method
        super(0, 1, 0, 0, 0, 2, 0, 3);

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.rgb(230, 230, 0));
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}

class PinkL4 extends HexaMino {
    PinkL4() {
        // Center of Shape is on index 0
        //////////////////////////// // Top Hexagon of Shape is on index 1
        ////////////////////// // Later we'll change this to the bottom automatically in
        // rotate method
        super(0, 1, 0, 0, 1, 0, 0, 2);

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.PINK);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}

class OrangeO4 extends HexaMino {
    OrangeO4() {
        // Center of Shape is on index 0
        //////////////////////////// // Top Hexagon of Shape is on index 1
        ////////////////////// // Later we'll change this to the bottom automatically in
        // rotate method
        super(1, 1, 1, 0, 0, 1, 2, 1);

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.ORANGE);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}

class GreenZ4 extends HexaMino {
    GreenZ4() {
        // Center of Shape is on index 0
        //////////////////////////// // Top Hexagon of Shape is on index 1
        ////////////////////// // Later we'll change this to the bottom automatically in
        // rotate method
        super(1, 1, 1, 0, 0, 2, 0, 3);

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.GREEN);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}

class BlueJ4 extends HexaMino {
    BlueJ4() {
        // Center of Shape is on index 0
        //////////////////////////// // Top Hexagon of Shape is on index 1
        ////////////////////// // Later we'll change this to the bottom automatically in
        // rotate method
        super(0, 2, 0, 0, 0, 1, 1, 2);

        super.firstCoordOfCenter = super.shape[0].getCoordInScene();

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.NAVY);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}