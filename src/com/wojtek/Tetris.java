package com.wojtek;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Tetris extends JPanel {

    public static int pW;
    static int rotation = 0;
    private Timer timer = new Timer(1000, evt -> dropPiece());
    private final Point[][][] Tetraminos = {

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
    public Point piecePlace;
    private int[][] tablica;
    private Integer currentPiece;

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
        tetris.zerowanieTablicy();


    }

    private void move(int i) {
        if (collisionTest(piecePlace.x + i, piecePlace.y, rotation)) {
            piecePlace.x += i;
        }
        repaint();

    }


    private void dropPiece() {
        //if (collisionTest(piecePlace.x, piecePlace.y + 1, rotation)) {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            tablica[p.x + piecePlace.x][p.y + piecePlace.y] = 0;
        }
        piecePlace.y += 1;

        for (Point p : Tetraminos[currentPiece][rotation]) {
            tablica[p.x + piecePlace.x][p.y + piecePlace.y] = 5;
        }

        // } else {
        //     fixToWell();
        // }

        repaint();

    }


    private void zerowanieTablicy() {
        tablica = new int[12][22];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 21; j++) {
                tablica[i][j] = 0;
            }
        }
        //ramka
        for (int ii = 0; ii < 11; ii++) {
            tablica[ii][0] = 1;
            tablica[ii][21] = 1;
        }
        for (int ij = 0; ij < 22; ij++) {
            tablica[0][ij] = 1;
            tablica[11][ij] = 1;
        }

        newPiece();
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 22; j++) {
                switch (tablica[i][j]) {
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
        drawPiece();

    }

    public void newPiece() {
        piecePlace = new Point(4, 2);
        rotation = 0;
        Random generator = new Random();
        currentPiece = generator.nextInt(6);

    }

    private void drawPiece() {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            tablica[p.x + piecePlace.x][p.y + piecePlace.y] = 5;
        }
        repaint();
    }

    private boolean collisionTest(int x, int y, int rotation) {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            if (tablica[p.x + x][p.y + y] == 0) {
                return false;
            }
        }
        return true;
    }

    public void rotate(int i) {
        int newRotation = (rotation + i) % 4;
        if (newRotation < 0) {
            newRotation = 3;
        }
        if (collisionTest(piecePlace.x, piecePlace.y, newRotation)) {
            rotation = newRotation;
        }
        repaint();
    }

    public void fixToWell() {
        for (Point p : Tetraminos[currentPiece][rotation]) {
            tablica[piecePlace.x + p.x][piecePlace.y + p.y] = 2;
        }
        clearRows();
        newPiece();
    }

    public void deleteRow(int row) {
        for (int j = row - 1; j > 0; j--) {
            for (int i = 1; i < 11; i++) {
                tablica[i][j + 1] = tablica[i][j];
            }
        }
    }

    public void clearRows() {
        boolean gap;


        for (int j = 21; j > 0; j--) {
            gap = false;
            for (int i = 1; i < 11; i++) {
                if (tablica[i][j] == 1) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                deleteRow(j);
                j += 1;

            }
        }


    }

}




