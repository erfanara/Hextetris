package com.github.erfanara.Hextetris.HexaminoShapes;

import javafx.scene.paint.Color;

public class PinkL4 extends Hexamino {
    public PinkL4() {
        // Center of Shape is on index 0
        super(0, 1, 0, 0, 1, 0, 0, 2);

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.PINK);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}