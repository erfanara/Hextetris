package com.github.erfanara.Hextetris;

final class GameBoard {
    final static int X_SIZE = 15;
    final static int Y_SIZE = 21;
    final static double HEXAGON_RADIUS = 20;
    final static double HEXAGON_HEIGHT = Math.sqrt(3) * HEXAGON_RADIUS;
    static RegHexagon[][] gameBoard = new RegHexagon[X_SIZE][Y_SIZE];

    static void print() {
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                System.out.print((gameBoard[j][i] == null) ? "0" : "1");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------");
    }

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

    static void clearCompletedRows() {
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
            } else {
                return;
            }
        }
    }

}