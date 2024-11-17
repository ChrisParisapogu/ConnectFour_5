package com.example.connectfour;

public class ConnectFourGame {
    // Member variable for the board (7 rows x 6 columns)
    private int[][] board;
    private int player;
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;

    // Custom constructor to instantiate the board array
    public ConnectFourGame() {
        board = new int[ROW][COL];
        newGame();
    }

    public void newGame() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                board[i][j] = EMPTY;
            }
        }
        player = BLUE;
    }

    public int getDisc(int row, int col) {
        return board[row][col];
    }

    public boolean isGameOver() {
        return isWin() || isBoardFull();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (board[i][j] == EMPTY) return false;
            }
        }
        return true;
    }

    private boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    private boolean checkHorizontal() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for (int col = 0; col < COL; col++) {
            for (int row = 0; row <= ROW - 4; row++) {
                if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player &&
                        board[row + 3][col] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player) {
                    return true;
                }
            }
            for (int col = 3; col < COL; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col - 1] == player &&
                        board[row + 2][col - 2] == player &&
                        board[row + 3][col - 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    public void selectDisc(int row, int col) {
        for (int i = ROW - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = player;
                player = (player == BLUE) ? RED : BLUE;
                break;
            }
        }
    }

    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = Character.getNumericValue(gameState.charAt(index++));
            }
        }
    }

    public String getState() {
        StringBuilder state = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                state.append(cell);
            }
        }
        return state.toString();
    }


    //previous code
    // Method to start a new game by initializing all board elements to zero
//    public void newGame() {
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                board[i][j] = 0;
//            }
//        }
//    }

    // Method to return the current state of the board as a string
//    public String getState() {
//        StringBuilder state = new StringBuilder();
//        for (int[] row : board) {
//            for (int cell : row) {
//                state.append(cell);
//            }
//        }
//        return state.toString();
//    }
}
