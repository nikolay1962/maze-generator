package me.nikolay;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Maze {

    public static final char WALL = '#';
    public static final char PASSAGE = '.';
    public static final char START = '_';
    public static final char END = '^';
    public static final char VISITED = ':';
    public static final char PATH = '*';

    private int size;
    private char[][] maze;

    int[] startPoint;
    int[] endPoint;

    public Maze(int size) {
        this.size = size;

        maze = new char[size][size];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = WALL;
            }
        }

        this.generateMaze(new int[]{0, 0});
    }

    public char[][] getMaze() {
        return maze;
    }

    public int getSize() {
        return size;
    }

    public void setPoint(int[] point, char value) {
        if (ifPointInsideMaze(point[0], point[1])) {
            maze[point[0]][point[1]] = value;
        }
    }

    private void generateMaze(int[] point) {
        int[][] dirs = {
                {0, -1},
                {1, 0},
                {0, 1},
                {-1, 0},
        };

        Collections.shuffle(Arrays.asList(dirs));
//        System.out.println(Arrays.toString(dirs[0]) + Arrays.toString(dirs[1]) +Arrays.toString(dirs[2]) +Arrays.toString(dirs[3]));

        for (int[] dir : dirs) {
            int[] newPoint = new int[2];
            newPoint[0] = point[0] + dir[0];
            newPoint[1] = point[1] + dir[1];
//            System.out.println("newPoint[0] = " + newPoint[0] + ", newPoint[1] = " + newPoint[1]);
            if (isGoodPath(newPoint)) {
                maze[newPoint[0]][newPoint[1]] = '.';
                generateMaze(newPoint);
            }
        }
//        return maze;
    }

    private boolean isGoodPath(int[] point) {
        if (!ifPointInsideMaze(point[0], point[1])) {
            return false;
        }

        int[][] dirs = {
                new int[]{0, -1},
                new int[]{1, 0},
                new int[]{0, 1},
                new int[]{-1, 0} };

        int countPath = 0;
        for (int[] dir : dirs) {
            int[] newPoint = new int[]{point[0] + dir[0], point[1] + dir[1]};
            if ( ifPointInsideMaze(newPoint[0], newPoint[1]) &&
                    maze[newPoint[0]][newPoint[1]] == '.') {
                countPath++;
            }
        }

        if (countPath > 1)
            return false;

        return true;
    }

    private boolean ifPointInsideMaze(int point0, int point1) {
        return (point0 < maze.length && point0 >= 0 &&
                point1 < maze[point0].length && point1 >= 0);
    }

    public void print2d() {
        for (char[] row : maze) {
            for (char col : row) {
                System.out.print(col + " ");
            }
            System.out.print('\n');
        }
        System.out.print("\n");
    }

    public int[] randomPathPoint() {
        int maxTries = 10;
        for (int i = 0; i < maxTries; i++) {
            int point0 = (int) (Math.random() * maze.length);
            int point1 = (int) (Math.random() * maze[0].length);
            if (isPath(point0, point1)) {
                return new int[]{point0, point1};
            }
        }
        System.out.println("No point was found after " + maxTries + " tries.");
        return null;
    }

    public boolean isPath(int point0, int point1) {
        return (point0 < maze.length && point0 >= 0 &&
                point1 < maze[point0].length && point1 >= 0 &&
                maze[point0][point1] == PASSAGE);
    }

    public void setStartPoint() {
        boolean found = false;
        outerloop:
        for (int row = 0; row < maze.length; row++) {
            for (int col = row - 1 ; col <= row + 1; col++) {
                for (int i = row - 1; i <= row + 1; i++) {
                    if (isPath(col, i)) {
                        found = true;
                        this.startPoint = new int[]{col, i};
                        setPoint(this.startPoint, Maze.START);
                        break outerloop;
                    }
                }
            }
        }
        System.out.println("Start point = " + Arrays.toString(startPoint));
    }

    public void setEndPoint() {
        boolean found = false;
        outerloop:
        for (int row = maze.length - 1; row >= 0; row--) {
            for (int col = row + 1; col >= row - 1; col--) {
                for (int i = row + 1; i >= row - 1; i--) {
                    if (isPath(col, i)) {
                        found = true;
                        this.endPoint = new int[]{col, i};
                        setPoint(this.endPoint, Maze.END);
                        break outerloop;
                    }
                }
            }
        }
        System.out.println("End point = " + Arrays.toString(endPoint));
    }

    public boolean searchPath(int row, int col, List<Integer> path) {
        // If path was found, this method will return true
        // and the path will be filled.
        // startPoint is a start position.
        // End point is marked in the maze as Maze.END.

        if (isValid(row, col) && maze[row][col] == Maze.END) {
            path.add(row);
            path.add(col);
            return true;
        }

        // when this is wall or visited,
        // return false.
        if (isValid(row, col) && (maze[row][col] == Maze.WALL || maze[row][col] == Maze.VISITED)) {
            return false;
        }

        // When a current position is PASSAGE,
        // then let's mark it as VISITED

        if (isValid(row, col) && maze[row][col] == Maze.PASSAGE) {
            maze[row][col] = Maze.VISITED;
        }

        // now let's visit all neighbor cells recursively.
        // If path was found, let's fill the path list
        // with current position.

        if (isValid(row - 1, col)) {
            if (searchPath(row - 1, col, path)) {
                setPath(row, col, Maze.PATH);
                path.add(row);
                path.add(col);
                return true;
            }
        }
        if (isValid(row + 1, col)) {
            if (searchPath(row + 1, col, path)) {
                setPath(row, col, Maze.PATH);
                path.add(row);
                path.add(col);
                return true;
            }
        }
        if (isValid(row, col - 1)) {
            if (searchPath(row, col - 1, path)) {
                setPath(row, col, Maze.PATH);
                path.add(row);
                path.add(col);
                return true;
            }
        }
        if (isValid(row, col + 1)) {
            if (searchPath(row, col + 1, path)) {
                setPath(row, col, Maze.PATH);
                path.add(row);
                path.add(col);
                return true;
            }
        }

        return false;
    }

    private void setPath(int row, int col, char path2) {
        if (maze[row][col] != Maze.START) {
            maze[row][col] = path2;
        }
    }

    private boolean isValid(int row, int col) {
        return (row < maze.length && row >= 0 &&
                col < maze[row].length && col >= 0);
    }
}
