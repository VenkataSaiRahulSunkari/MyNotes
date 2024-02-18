package files;

import java.util.*;

public class KnightsTourHeuristicAdv{
    private static final int N = 8;
    private static final int[] xMoves = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int[] yMoves = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int[][] accessibility = {
        {2, 3, 4, 4, 4, 4, 3, 2},
        {3, 4, 6, 6, 6, 6, 4, 3},
        {4, 6, 8, 8, 8, 8, 6, 4},
        {4, 6, 8, 8, 8, 8, 6, 4},
        {4, 6, 8, 8, 8, 8, 6, 4},
        {4, 6, 8, 8, 8, 8, 6, 4},
        {3, 4, 6, 6, 6, 6, 4, 3},
        {2, 3, 4, 4, 4, 4, 3, 2}
    };

    private int[][] board;
    private int totalTours = 0;

    public KnightsTourHeuristicAdv() {
        board = new int[N][N];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int x = 0; x < N; x++) {
            Arrays.fill(board[x], Integer.MIN_VALUE);
        }
    }

    private boolean isMoveValid(int x, int y) {
        return (x >= 0 && x < N) && (y >= 0 && y < N) && board[x][y] == Integer.MIN_VALUE;
    }

    private boolean solve(int x, int y, int moveCount) {
        if (moveCount == N * N) {
            return true;
        }

        List<int[]> candidates = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int nextX = x + xMoves[i];
            int nextY = y + yMoves[i];

            if (isMoveValid(nextX, nextY)) {
                candidates.add(new int[]{nextX, nextY, accessibility[nextX][nextY]});
            }
        }

        candidates.sort((a, b) -> {
            if (a[2] == b[2]) {
                return Integer.compare(getNextMoveAccessibility(a[0], a[1]), getNextMoveAccessibility(b[0], b[1]));
            }
            return Integer.compare(a[2], b[2]);
        });

        for (int[] move : candidates) {
            int nextX = move[0];
            int nextY = move[1];
            board[nextX][nextY] = moveCount;
            if (solve(nextX, nextY, moveCount + 1)) {
                return true;
            } else {
                board[nextX][nextY] = Integer.MIN_VALUE;
            }
        }
        return false;
    }

    private int getNextMoveAccessibility(int x, int y) {
        int minAccessibility = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int nextX = x + xMoves[i];
            int nextY = y + yMoves[i];
            if (isMoveValid(nextX, nextY)) {
                minAccessibility = Math.min(minAccessibility, accessibility[nextX][nextY]);
            }
        }
        return minAccessibility;
    }

    public void solveKnightsTourFrom(int startX, int startY) {
        initializeBoard();
        board[startX][startY] = 0;
        if (solve(startX, startY, 1)) {
            totalTours++;
            printSolution();
        } else {
            System.out.println("No solution exists starting from (" + startX + ", " + startY + ").");
        }
    }

   

        private void printSolution() {
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    System.out.printf("%2d ", board[x][y]);
                }
                System.out.println();
            }
            System.out.println();
        }

        public static void main(String[] args) {
            KnightsTourHeuristicAdv knight = new KnightsTourHeuristicAdv();
            int fullTours = 0;
            for (int x = 0; x < N; x++) {
                for (int y = 0; y < N; y++) {
                    knight.solveKnightsTourFrom(x, y);
                    if (knight.totalTours > fullTours) {
                        fullTours++;
                    }
                }
            }
            System.out.println("Full tours completed: " + fullTours);
        }
    }


