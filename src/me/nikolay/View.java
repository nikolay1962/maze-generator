package me.nikolay;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame {

    private char[][] maze;
    private int cellSize;

    private List<Integer> path;

    public View(Maze maze) throws HeadlessException {
        this.maze = maze.getMaze();
        setTitle("Simple Maze.");
        setSize(760, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cellSize = (700 / maze.getSize());
        this.getContentPane().setBackground(Color.GRAY);

        path = new ArrayList<>();

        maze.searchPath(maze.startPoint[0], maze.startPoint[1], path);
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.translate(cellSize, cellSize);

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                Color color;
                switch (maze[row][col]) {
                    case Maze.WALL:
                        color = Color.BLACK;
                        break;
                    case Maze.END:
                        color = Color.RED;
                        break;
                    case Maze.START:
                        color = Color.GREEN;
                        break;
                    case Maze.VISITED:
                        color = Color.cyan;
                        break;
                    case Maze.PATH:
                        color = Color.YELLOW;
                        break;
                    default:
                        color = Color.WHITE;
                        break;
                }
                g.setColor(color);
                g.fillRect(cellSize * col, cellSize * row, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(cellSize * col, cellSize * row, cellSize, cellSize);

            }
        }

    }
}
