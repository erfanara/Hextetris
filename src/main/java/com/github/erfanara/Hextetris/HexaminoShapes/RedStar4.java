package com.github.erfanara.Hextetris.HexaminoShapes;

import javafx.scene.paint.Color;

public class RedStar4 extends Hexamino {
    public RedStar4() {
        // Center of Shape is on index 0
        super(1, 1, 1, 0, 2, 2, 0, 2);

        for (int i = 0; i < super.shape.length; i++) {
            super.shape[i].setFill(Color.rgb(210, 0, 0));
            super.shape[i].setStroke(Color.WHITE);
        }
    }
}