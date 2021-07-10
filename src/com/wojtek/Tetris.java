package com.wojtek;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.Random;

import static com.wojtek.TetrisGui.fieldSize;
import static com.wojtek.TetrisGui.score;


public class Tetris extends JPanel {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    private final Timer timer;
    public Point blockPlace;
    int orientation = 0;
    private int[][] table;
    private int block;
    public static int level = 1;
    private long scoreCombo = 0;
    private static boolean game = true;

    public Tetris(int timerDelay) {

        timer = new Timer(timerDelay, evt -> moveDown());


    }

    public static void main(String[] args) {
        TetrisGui tetrisGui = new TetrisGui(level);


    }


    public void moveToSide(int i) {
        if (game) {
            for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
            }
            if (collisionTest(blockPlace.x + i, blockPlace.y, orientation)) {
                blockPlace.x += i;

            }
            for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
            }
            repaint();
        }
    }


    public void moveDown() {


        if (game) {


            for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
            }

            if (collisionTest(blockPlace.x, blockPlace.y + 1, orientation)) {
                blockPlace.y += 1;
                score += 1;
                for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
                    table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
                }

            } else {
                placeOntoBottom();
            }

            repaint();
        }
    }


    public void resetTable() {
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
        game = true;

    }

    @Override
    public void paintComponent(Graphics g) {

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 22; j++) {
                switch ( table[i][j] ) {
                    case 0 -> {
                        g.setColor(Color.GRAY);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 1 -> {
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 2 -> {
                        g.setColor(Color.GREEN);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 3 -> {
                        g.setColor(Color.YELLOW);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 4 -> {
                        g.setColor(Color.RED);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 5 -> {
                        g.setColor(Color.ORANGE);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 6 -> {
                        g.setColor(Color.CYAN);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 7 -> {
                        g.setColor(Color.MAGENTA);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                    case 8 -> {
                        g.setColor(Color.BLUE);
                        g.fillRect(i * fieldSize, j * fieldSize, fieldSize, fieldSize);
                    }
                }
            }
        }
        g.setColor(Color.RED);
        g.drawString("SCORE: " + score + "      lvl:" + level, fieldSize * 4, fieldSize / 2);


        if (!game) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", fieldSize * 5, fieldSize * 10);
        }
    }

    public void blockNew() {
        if (game) {
            gameLevelCheck();
            blockPlace = new Point(5, 1);
            Random generator = new Random();
            block = generator.nextInt(6);
            orientation = generator.nextInt(3);

            if (!collisionTest(blockPlace.x, blockPlace.y, orientation)) {
                gameOver();
            }
        }
    }

    public void blockDraw() {
        if (game) {
            for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
            }
            repaint();
            scoreCombo = 50;
        }
    }

    public void gameOver() {
        timer.stop();
        game = false;


    }


    public void gameLevelCheck() {
        if (score > 500) {
            level = 2;
            timer.setDelay(750);
        }
        if (score > 1000) {
            level = 3;
            timer.setDelay(500);
        }
        if (score > 2000) {
            level = 4;
            timer.setDelay(300);
        }
        if (score > 2500) {
            level = 5;
            timer.setDelay(200);
        }
    }

    public boolean collisionTest(int x, int y, int orientation) {

        for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
            if (table[p.x + x][p.y + y] != 0) {
                return false;
            }
        }
        return true;
    }

    public void rotate() {
        if (game) {
            for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
            }
            int newOrientation = orientation + 1;
            if (newOrientation > 3) {
                newOrientation = 0;
            }
            if (collisionTest(blockPlace.x, blockPlace.y, newOrientation)) {
                orientation = newOrientation;
            }

            blockDraw();
            repaint();
        }

    }

    public void placeOntoBottom() {
        for (Point p : Tetraminos.tetrisBlocks[block][orientation]) {
            table[blockPlace.x + p.x][blockPlace.y + p.y] = block + 2;
        }
        score += 5;
        checkForFullRows();

        blockNew();
        blockDraw();

    }

    public void checkForFullRows() {
        for (int h = 20; h > 0; h--) {
            for (int g = 1; g < 11; g++) {
                if (table[g][h] != 0) {

                    if (g == 10) {
                        deleteRow(h);
                    }

                } else {
                    break;
                }
            }
        }

    }

    public void deleteRow(int row) {
        for (int i = row; i > 1; i--) {
            for (int g = 1; g < 11; g++) {
                table[g][i] = table[g][i - 1];
            }
        }
        score += scoreCombo;
        scoreCombo *= 2;
        checkForFullRows();
    }


}
