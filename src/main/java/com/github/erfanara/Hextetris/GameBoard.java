package com.github.erfanara.Hextetris;

class GameBoard {
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

    enum Movement {
        DOWN, RIGHT, LEFT, ROTATE;
    }

    static void clearBoard() {

    }

    static void completedHorizon() {

    }

}