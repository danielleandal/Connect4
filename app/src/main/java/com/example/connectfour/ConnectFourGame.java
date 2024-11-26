package com.example.connectfour;

import android.util.Log;


public class ConnectFourGame {


    // Member variable: 2D integer array (7 rows, 6 columns)
    private int[][] board;
    private int currentPlayer;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int ROWS = 7;
    public static final int COLS = 6;
    private static int DISCS = 42;



    // Corrected constructor to instantiate the board array
    public ConnectFourGame() {
        board = new int[7][6]; // 7 rows and 6 columns
        currentPlayer = BLUE;
    }

    // Method newGame to initialize the board with zeros
    public void newGame() {
        for (int i = 0; i < ROWS; i++) { // iterate through rows
            for (int j = 0; j < COLS; j++) { // iterate through columns
                board[i][j] = 0; // initialize each element to 0
            }
        }

        currentPlayer = BLUE;
    }

    public void switchPlayer() {
        if (currentPlayer == BLUE) {
            currentPlayer = RED;
        } else {
            currentPlayer = BLUE;
        }
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setState(String gameState) {
        // Split the gameState string by newlines to get each row
        String[] lines = gameState.split("\n");

        // Loop through each line (except the last one which contains the current player)
        for (int i = 0; i < lines.length - 1; i++) {
            // Split each line by commas to get individual cell values
            String[] cells = lines[i].split(",");
            for (int j = 0; j < cells.length; j++) {
                // Populate the board with the saved data
                board[i][j] = Integer.parseInt(cells[j]);
            }
        }

        // The last line contains the current player information
        String[] playerInfo = lines[lines.length - 1].split("=");
        currentPlayer = Integer.parseInt(playerInfo[1]);
    }



    // Method getState to return the board's current state as a String
    public String getState() {
        StringBuilder boardState = new StringBuilder();

        for (int i = 0; i < board.length; i++) { // iterate through rows
            for (int j = 0; j < board[i].length; j++) { // iterate through columns
                boardState.append(board[i][j]); // append each element to StringBuilder
                if (j < board[i].length - 1) {
                    boardState.append(", "); // add comma for readability
                }
            }
            boardState.append("\n"); // add newline after each row
        }

        return boardState.toString(); // convert StringBuilder to String and return
    }

    public boolean selectDisc(int col) {
        // Check if the column index is within bounds
        if (col < 0 || col >= ConnectFourGame.COLS) {
            Log.e("ConnectFour", "Invalid column index: " + col);
            return false;
        }

        // Start from the bottom-most row and move upwards to find the first empty spot
        for (int row = ConnectFourGame.ROWS - 1; row >= 0; row--) {
            if (board[row][col] == ConnectFourGame.EMPTY) {
                board[row][col] = currentPlayer;
                switchPlayer(); // Switch to the other player after a successful move
                return true;
            }
        }
        return false; // Column is full
    }



    public int getDisc(int row, int col) {
        // Step c: Return the element stored at the specified row and column of the board
        return board[row][col];
    }
    public boolean isGameOver() {
        // Condition 1: Check if the board is full
        if (isBoardFull()) {
            return true;
        }

        // Condition 2: Check if there is a winning condition
        if (isWin()) {
            return true;
        }

        // Game is not over yet
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == 0) { // Empty cell found
                    return false;
                }
            }
        }
        return true; // No empty cells found
    }

    public boolean isWin() {
        // Check all possible win conditions
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    private boolean checkHorizontal() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                // Check if there are four consecutive discs of the same player
                if (board[row][col] != 0 &&
                        board[row][col] == board[row][col + 1] &&
                        board[row][col] == board[row][col + 2] &&
                        board[row][col] == board[row][col + 3]) {
                    return true; // Win condition met
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {
        // Loop through each column
        for (int col = 0; col < COLS; col++) {
            // Loop through rows, but stop at ROWS - 3 to prevent out-of-bounds errors
            for (int row = 0; row <= ROWS - 4; row++) {
                if (board[row][col] != EMPTY &&
                        board[row][col] == board[row + 1][col] &&
                        board[row][col] == board[row + 2][col] &&
                        board[row][col] == board[row + 3][col]) {
                    return true; // Four in a row vertically
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        // Check for diagonals from top-left to bottom-right
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 0; col <= board[row].length - 4; col++) {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row + 1][col + 1] &&
                        board[row][col] == board[row + 2][col + 2] &&
                        board[row][col] == board[row + 3][col + 3]) {
                    return true; // Win condition met
                }
            }
        }

        // Check for diagonals from top-right to bottom-left
        for (int row = 0; row <= board.length - 4; row++) {
            for (int col = 3; col < board[row].length; col++) {
                if (board[row][col] != 0 &&
                        board[row][col] == board[row + 1][col - 1] &&
                        board[row][col] == board[row + 2][col - 2] &&
                        board[row][col] == board[row + 3][col - 3]) {
                    return true; // Win condition met
                }
            }
        }
        return false;
    }

}