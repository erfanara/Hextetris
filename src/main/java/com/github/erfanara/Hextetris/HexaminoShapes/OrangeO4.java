package com.github.erfanara.Hextetris.HexaminoShapes;

import javafx.scene.paint.Color;

public class OrangeO4 extends Hexamino {
    public OrangeO4() {
        // Center of Shape is on index 0
        super(1, 1, 1, 0, 0, 1, 2, 1);

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.ORANGE);
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}