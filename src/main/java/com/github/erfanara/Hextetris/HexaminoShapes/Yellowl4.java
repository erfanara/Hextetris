package com.github.erfanara.Hextetris.HexaminoShapes;

import javafx.scene.paint.Color;

public class Yellowl4 extends Hexamino {
    public Yellowl4() {
        // Center of Shape is on index 0
        super(0, 1, 0, 0, 0, 2, 0, 3);

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.rgb(230, 230, 0));
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}