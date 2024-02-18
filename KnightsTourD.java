package files;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KnightsTourD {
    private static final int N = 8;
    private static final int[] X_MOVES = { 2, 1, -1, -2, -2, -1, 1, 2 };
    private static final int[] Y_MOVES = { 1, 2, 2, 1, -1, -2, -2, -1 };
    private int[][] board;
    private int[][] accessibility;

    public KnightsTourD() {
        board = new int[N][N];
        accessibility = new int[][] {
                { 2, 3, 4, 4, 4, 4, 3, 2 },
                { 3, 4, 6, 6, 6, 6, 4, 3 },
                { 4, 6, 8, 8, 8, 8, 6, 4 },
                { 4, 6, 8, 8, 8, 8, 6, 4 },
                { 4, 6, 8, 8, 8, 8, 6, 4 },
                { 4, 6, 8, 8, 8, 8, 6, 4 },
                { 3, 4, 6, 6, 6, 6, 4, 3 },
                { 2, 3, 4, 4, 4, 4, 3, 2 }
        };
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = -1;
            }
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < N && board[x][y] == -1;
    }

    private List<int[]> getValidMoves(int x, int y) {
        List<int[]> validMoves = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int nextX = x + X_MOVES[i];
            int nextY = y + Y_MOVES[i];
            if (isValidMove(nextX, nextY)) {
                int nextMoveAccessibility = getNextMoveAccessibility(nextX, nextY);
                validMoves.add(new int[] { nextX, nextY, accessibility[nextX][nextY], nextMoveAccessibility });

            }
        }
        return validMoves;
    }

    private int getNextMoveAccessibility(int x, int y) {
        int minAccessibility = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int nextX = x + X_MOVES[i];
            int nextY = y + Y_MOVES[i];
            if (isValidMove(nextX, nextY)) {
                minAccessibility = Math.min(minAccessibility, accessibility[nextX][nextY]);
            }
        }
        return minAccessibility;
    }

    private boolean solve(int moveCount, int x, int y) {
        board[x][y] = moveCount;
        if (moveCount == N * N - 1) {
            return true;
        }

        List<int[]> validMoves = getValidMoves(x, y);
        validMoves.sort(Comparator.comparingInt(a -> a[2]));

        for (int[] move : validMoves) {
            decreaseAccessibility(move[0], move[1]);
            if (solve(moveCount + 1, move[0], move[1])) {
                return true;
            }
            increaseAccessibility(move[0], move[1]);
        }
        board[x][y] = -1; // Backtrack
        return false;
    }

    private void decreaseAccessibility(int x, int y) {
        for (int i = 0; i < N; i++) {
            int nextX = x + X_MOVES[i];
            int nextY = y + Y_MOVES[i];
            if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) {
                accessibility[nextX][nextY]--;
            }
        }
    }

    private void increaseAccessibility(int x, int y) {
        for (int i = 0; i < N; i++) {
            int nextX = x + X_MOVES[i];
            int nextY = y + Y_MOVES[i];
            if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) {
                accessibility[nextX][nextY]++;
            }
        }
    }

    public void startTour() {
        if (!solve(0, 0, 0)) {
            System.out.println("No solution exists.");
        } else {
            printSolution();
        }
    }

    private void printSolution() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%2d ", board[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        KnightsTourD tour = new KnightsTourD();
        tour.startTour(); // You can call this method multiple times with different starting positions if
                          // needed
    }
}
