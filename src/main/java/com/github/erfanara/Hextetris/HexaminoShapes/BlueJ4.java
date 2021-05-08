package com.github.erfanara.Hextetris.HexaminoShapes;

import javafx.scene.paint.Color;

public class BlueJ4 extends Hexamino {
    public BlueJ4() {
        // Center of Shape is on index 0
        super(0, 2, 0, 0, 0, 1, 1, 2);

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.NAVY);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}