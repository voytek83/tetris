package com.wojtek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class Tetris extends JPanel {

    public static int pW;
    int orientation = 0;
    private final Point[][][] tetrisBlocks = {

            {
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)},
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3)}
            },

            {
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2)},
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0)}
            },

            {
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)},
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0)},
                    {new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0)}
            },

            {
                    {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
                    {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
                    {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)},
                    {new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)}
            },

            {
                    {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
                    {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
                    {new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)},
                    {new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}
            },

            {
                    {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)},
                    {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)},
                    {new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
                    {new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}
            },

            {
                    {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
                    {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)},
                    {new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)},
                    {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2)}
            }
    };
    public Point blockPlace;
    private int[][] table;
    private int block;
    private Timer timer = new Timer(1000, evt -> moveDown());

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Tetris");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pW = screenSize.height / 30;
        int frameHeight = pW * 23 - 5;
        int frameWidth = pW * 12 + 14;

        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Tetris tetris = new Tetris();
        frame.add(tetris);
        tetris.resetTable();
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                        tetris.moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        tetris.moveToSide(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        tetris.moveToSide(+1);
                        break;
                    case KeyEvent.VK_SPACE:
                        tetris.rotate();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        frame.addKeyListener(keyListener);


    }

    private void moveToSide(int i) {
        for (Point p : tetrisBlocks[block][orientation]) {
            table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
        }
        if (collisionTest(blockPlace.x + i, blockPlace.y, orientation)) {
            blockPlace.x += i;

        }
        for (Point p : tetrisBlocks[block][orientation]) {
            table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
        }
        repaint();

    }


    private void moveDown() {

        for (Point p : tetrisBlocks[block][orientation]) {
            table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
        }

        if (collisionTest(blockPlace.x, blockPlace.y + 1, orientation)) {
            blockPlace.y += 1;
            for (Point p : tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
            }

        } else {
            placeOntoBottom();
        }

        repaint();

    }


    private void resetTable() {
        table = new int[12][22];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 21; j++) {
                table[i][j] = 0;
            }
        }

        for (int ii = 0; ii < 11; ii++) {
            table[ii][0] = 1;
            table[ii][21] = 1;
        }
        for (int ij = 0; ij < 22; ij++) {
            table[0][ij] = 1;
            table[11][ij] = 1;
        }

        blockNew();
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 22; j++) {
                switch (table[i][j]) {
                    case 0 -> {
                        g.setColor(Color.GRAY);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 1 -> {
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 2 -> {
                        g.setColor(Color.GREEN);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 3 -> {
                        g.setColor(Color.YELLOW);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 4 -> {
                        g.setColor(Color.RED);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 5 -> {
                        g.setColor(Color.ORANGE);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 6 -> {
                        g.setColor(Color.CYAN);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 7 -> {
                        g.setColor(Color.MAGENTA);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                    case 8 -> {
                        g.setColor(Color.BLUE);
                        g.fillRect(i * pW, j * pW, pW, pW);
                    }
                }
            }
        }


    }

    public void blockNew() {
        blockPlace = new Point(5, 1);

        Random generator = new Random();
        block = generator.nextInt(6);
        orientation = generator.nextInt(3);
    }

    private void blockDraw() {

        for (Point p : tetrisBlocks[block][orientation]) {
            table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
        }


        repaint();


    }

    private void gameOver() {


    }

    private boolean collisionTest(int x, int y, int orientation) {

        for (Point p : tetrisBlocks[block][orientation]) {
            if (table[p.x + x][p.y + y] != 0) {
                return false;
            }
        }
        return true;
    }

    public void rotate() {
        for (Point p : tetrisBlocks[block][orientation]) {
            table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
        }
        int newOrientation = orientation+1;
        if (newOrientation >3){
            newOrientation=0;
        }
        if (collisionTest(blockPlace.x, blockPlace.y, newOrientation)) {
            orientation = newOrientation;
        }

        blockDraw();
        repaint();
    }

    public void placeOntoBottom() {
        for (Point p : tetrisBlocks[block][orientation]) {
            table[blockPlace.x + p.x][blockPlace.y + p.y] = block + 2;
        }

        blockNew();
        if (collisionTest(blockPlace.x, blockPlace.y, orientation)) {
            blockDraw();
        }

    }


}




