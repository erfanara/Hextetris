package com.github.erfanara.Hextetris;

import com.github.erfanara.Hextetris.HexaminoShapes.Hexamino;
import com.github.erfanara.Hextetris.HexaminoShapes.RegHexagon;

/*
    This class contains the main logic of the game
*/
public final class GameBoard {
    public final static int X_SIZE = 15;
    public final static int Y_SIZE = 21;
    public final static double HEXAGON_RADIUS = 20;
    public final static double HEXAGON_HEIGHT = Math.sqrt(3) * HEXAGON_RADIUS;
    static RegHexagon[][] gameBoard = new RegHexagon[X_SIZE][Y_SIZE];

    static boolean verticRangeValidation(int row) {
        return (row >= 0 && row < Y_SIZE);
    }

    static boolean horizonRangeValidation(int column) {
        return (column >= 0 && column < X_SIZE);
    }

    static boolean isEmpty(int column, int row) {
        return gameBoard[column][row] == null;
    }

    public static boolean isValidAndEmpty(int column, int row) {
        return verticRangeValidation(row) && horizonRangeValidation(column) && isEmpty(column, row);
    }

    public static double[] convertToCoord(int[] columnRow) {
        return new double[] { HEXAGON_RADIUS + (HEXAGON_RADIUS * 3) / 2.0 * columnRow[0],
                (((columnRow[0] % 2 == 0) ? HEXAGON_HEIGHT / 2.0 : HEXAGON_HEIGHT) + HEXAGON_HEIGHT * columnRow[1]) };
    }

    static boolean submit(Hexamino a) {
        for (RegHexagon k : a.shape) {
            if (isEmpty(k.getColumn(), k.getRow()))
                gameBoard[k.getColumn()][k.getRow()] = k;
            else
                return false;
        }
        return true;
    }

    static boolean isRowCompleted(int row) {
        for (int column = 0; column < X_SIZE; column++) {
            if (isEmpty(column, row))
                return false;
        }
        return true;
    }

    static int getFirstCompletedRow() {
        for (int row = Y_SIZE - 1; row >= 0; row--) {
            if (isRowCompleted(row)) {
                return row;
            }
        }
        return -1;
    }

    static void moveHexagonDown(int column, int row) {
        gameBoard[column][row].moveDown();
        gameBoard[column][row + 1] = gameBoard[column][row];
        gameBoard[column][row] = null;
    }

    static void removeHexagon(int column, int row) {
        gameBoard[column][row].remove();
        gameBoard[column][row] = null;
    }

    static boolean clearCompletedRows() {
        while (true) {
            int row = getFirstCompletedRow();
            if (row != -1) {
                for (int column = 0; column < X_SIZE; column++) {
                    removeHexagon(column, row);
                }
                // now all hexagons above , should moved down
                for (int upperRow = row - 1; upperRow >= 0; upperRow--) {
                    for (int column = 0; column < X_SIZE; column++) {
                        if (!isEmpty(column, upperRow))
                            moveHexagonDown(column, upperRow);
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

}