package com.github.erfanara.Hextetris;

import java.util.ArrayList;

final class GameBoard {
    final static int X_SIZE = 15;
    final static int Y_SIZE = 21;
    final static double HEXAGON_RADIUS = 20;
    final static double HEXAGON_HEIGHT = Math.sqrt(3) * HEXAGON_RADIUS;
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

    static boolean isValidAndEmpty(int column, int row) {
        return verticRangeValidation(row) && horizonRangeValidation(column) && isEmpty(column, row);
    }

    static double[] convertToCoord(int[] columnRow) {
        return new double[] { HEXAGON_RADIUS + (HEXAGON_RADIUS * 3) / 2.0 * columnRow[0],
                (((columnRow[0] % 2 == 0) ? HEXAGON_HEIGHT / 2.0 : HEXAGON_HEIGHT) + HEXAGON_HEIGHT * columnRow[1]) };
    }

    static int[] convertToColumnRow(double[] coord) {
        int column = (int) ((coord[0] / HEXAGON_RADIUS - 1) * 2 / 3.0);
        return new int[] { column, (int) ((coord[1] - (column % 2 + 1) / 2.0) / HEXAGON_HEIGHT) };
    }

    static void submit(HexaMino a) {
        for (RegHexagon i : a.shape) {
            gameBoard[i.columnRow[0]][i.columnRow[1]] = i;
        }
    }

    static boolean isRowCompleted(int row) {
        for (int column = 0; column < X_SIZE; column++) {
            if (isEmpty(column, row))
                return false;
        }
        return true;
    }

    static ArrayList<Integer> getCompletedRows() {
        ArrayList<Integer> completedRows = new ArrayList<Integer>();
        for (int row = 0; row < Y_SIZE; row++) {
            if (isRowCompleted(row))
                completedRows.add(row);
        }

        return completedRows;
    }

    static void clearCompletedRows() {
        ArrayList<Integer> completedRows = getCompletedRows();
        for (int row : completedRows) {
            for (int column=0; column < X_SIZE; column++) {
                gameBoard[column][row].remove();
            }
            // TODO : now all hexagons above , should move down until they can't
            // mabe we need to redefine a new movedown method for hexagons
        }
    }

}