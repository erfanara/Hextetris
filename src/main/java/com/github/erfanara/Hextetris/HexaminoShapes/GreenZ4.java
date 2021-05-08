package com.github.erfanara.Hextetris.HexaminoShapes;

import javafx.scene.paint.Color;

public class GreenZ4 extends Hexamino {
    public GreenZ4() {
        // Center of Shape is on index 0
        super(1, 1, 1, 0, 0, 2, 0, 3);

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.GREEN);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}