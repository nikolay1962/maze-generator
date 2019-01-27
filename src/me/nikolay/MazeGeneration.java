package me.nikolay;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;

public class MazeGeneration {

    static int size = 20;
//    static char[][] maze = new char[size][size];

    public static void main(String[] args) {

        Maze maze = new Maze(MazeGeneration.size);
        
        int[] pointOnPath = maze.randomPathPoint();
        System.out.println("Point on the path = " + Arrays.toString(pointOnPath));

        maze.setStartPoint();

        maze.setEndPoint();

        maze.print2d();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View(maze);
                view.setVisible(true);
            }
        });

    }

}
