package com.github.erfanara.Hextetris;

class GameBoard {
    final static int X_SIZE = 15;
    final static int Y_SIZE = 21;
    final static double HEXAGON_RADIUS = 20;
    final static double HEXAGON_HEIGHT = Math.sqrt(3) * HEXAGON_RADIUS;
    static RegHexagon[][] gameBoard = new RegHexagon[X_SIZE][Y_SIZE];

    /*
     * Checks the specified index of gameBoard that is in the range of gameBoard
     * size or not
     */
    static boolean isValid(int column, int row) {
        if (column < 0 || column >= X_SIZE)
            return false;

        if (row < 0 || row >= Y_SIZE)
            return false;

        return true;
    }

    /*
     * Checks the specified index of gameBoard that is empty or not ,this method
     * checks if that index is valid or not first.
     */
    static boolean isEmpty(int column, int row) {
        return isValid(column, row) && gameBoard[column][row] == null;
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

    // static boolean isMoveAllowed(HexaMino a, Movement x) {
    // // for (RegHexagon i : a.shape) {
    // // if(i.getRow()== Y_SIZE-1 || i.getColumn()==X_SIZE-1 )
    // // return false;
    // // }
    // switch (x) {
    // case DOWN:
    // for (RegHexagon i : a.shape) {
    // if (i.getRow() == Y_SIZE - 1)
    // return false;
    // }
    // break;
    // case RIGHT:
    // for (RegHexagon i : a.shape) {
    // if (i.getColumn() == X_SIZE - 1)
    // return false;
    // }
    // break;
    // case LEFT:
    // for (RegHexagon i : a.shape) {
    // if (i.getColumn() == 0)
    // return false;
    // }
    // break;
    // case ROTATE:
    // for (RegHexagon i : a.shape) {
    // if (i.getRow() == Y_SIZE - 1 || i.getColumn() == X_SIZE - 1 || i.getColumn()
    // == 0)
    // return false;
    // }
    // break;

    // default:
    // break;
    // }

    // return true;
    // }

    static void clearBoard() {

    }

    static void completedHorizon() {

    }

}