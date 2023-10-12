package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuValidator {
    public static void main(String[] args) {
        // Ensure a filename is provided as an argument
        if (args.length != 1) {
            System.out.println("Please provide the Sudoku file name as an argument.");
            return;
        }

        String filename = args[0];

        try {
            int[][] sudokuGrid = readSudokuFromFile(filename);
            if (isValidSudoku(sudokuGrid)) {
                System.out.println("The Sudoku puzzle is valid.");
            } else {
                System.out.println("The Sudoku puzzle is not valid.");
            }
        } catch (IOException e) {
            System.out.println("Error reading Sudoku from file: " + e.getMessage());
        }
    }

    public static int[][] readSudokuFromFile(String filename) throws IOException {
        int[][] grid = new int[9][9];

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int rowIndex = 0;

            while ((line = br.readLine()) != null && rowIndex < 9) {
                String[] numbers = line.split(",");
                for (int colIndex = 0; colIndex < 9; colIndex++) {
                    grid[rowIndex][colIndex] = Integer.parseInt(numbers[colIndex]);
                }
                rowIndex++;
            }
        }

        return grid;
    }

    public static boolean isValidSudoku(int[][] board) {
        for (int i = 0; i < 9; i++) {
            boolean[] rowCheck = new boolean[9];
            boolean[] colCheck = new boolean[9];
            boolean[] boxCheck = new boolean[9];

            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    if (rowCheck[board[i][j] - 1]) {
                        return false;
                    }
                    rowCheck[board[i][j] - 1] = true;
                }

                if (board[j][i] != 0) {
                    if (colCheck[board[j][i] - 1]) {
                        return false;
                    }
                    colCheck[board[j][i] - 1] = true;
                }

                int boxRow = 3 * (i / 3);
                int boxCol = 3 * (i % 3);
                int value = board[boxRow + j / 3][boxCol + j % 3];

                if (value != 0) {
                    if (boxCheck[value - 1]) {
                        return false;
                    }
                    boxCheck[value - 1] = true;
                }
            }
        }
        return true;
    }
}