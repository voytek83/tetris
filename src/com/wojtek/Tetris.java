package com.wojtek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;
import java.util.Random;


public class Tetris extends JPanel {

    /**
     * WW
     */

    @Serial
    private static final long serialVersionUID = 1L;
    public static int poleWymiar;
    private final Timer timer;
    public Point blockPlace;
    int orientation = 0;
    private int[][] table;
    private int block;
    private boolean game = true;
    private long score = 1;
    private long scoreCombo = 0;
    private static final int fieldSizeModifier = 30;
    private static final int numberRows = 23;
    private static final int numberColumns = 12;
    int gameLevel = 0;


    public Tetris(int timerDelay) {
        //ciekawe by moglobyc jakby to przekazywac np jako parametr startowy, albo w jakiejs innej formie, wtedy mozna tym levele ograc
        timer = new Timer(timerDelay, evt -> moveDown());
    }

    public static void main(String[] args) {


    }


    private static void createAndShowGUI(int timerDelay) {
        JFrame frame = new JFrame("Tetris");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        poleWymiar = screenSize.height / fieldSizeModifier;//ta 30tka to zla praktyka, to tzw magic number czyli...liczba z dupy. powinienes miec pole w klasie np int sizeOfSth, analogicznych rzeczy tego typu jet wiecej tu
        int frameHeight = poleWymiar * (numberRows) - 5; // +2
        int frameWidth = poleWymiar * (numberColumns) + 14; // 12 pól + margines ramki

        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        Tetris tetris = new Tetris(timerDelay);
        frame.add(tetris);
        tetris.resetTable();


        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN -> tetris.moveDown();
                    case KeyEvent.VK_LEFT -> tetris.moveToSide(-1);
                    case KeyEvent.VK_RIGHT -> tetris.moveToSide(+1);
                    case KeyEvent.VK_SPACE -> tetris.rotate();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        frame.addKeyListener(keyListener);
    }

    private void moveToSide(int i) {
        if (game) {
            for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
            }
            if (collisionTest(blockPlace.x + i, blockPlace.y, orientation)) {
                blockPlace.x += i;

            }
            for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
            }
            repaint();
        }
    }


    private void moveDown() {
        if (game) {
            for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = 0;
            }

            if (collisionTest(blockPlace.x, blockPlace.y + 1, orientation)) {
                blockPlace.y += 1;
                score += 1;
                for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
                    table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
                }

            } else {
                placeOntoBottom();
            }
            repaint();
        }
    }


    private void resetTable() {
        table = new int[numberColumns][numberRows];
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
                switch (table[i][j]) {
                    case 0 -> {
                        g.setColor(Color.GRAY);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 1 -> {
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 2 -> {
                        g.setColor(Color.GREEN);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 3 -> {
                        g.setColor(Color.YELLOW);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 4 -> {
                        g.setColor(Color.RED);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 5 -> {
                        g.setColor(Color.ORANGE);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 6 -> {
                        g.setColor(Color.CYAN);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 7 -> {
                        g.setColor(Color.MAGENTA);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                    case 8 -> {
                        g.setColor(Color.BLUE);
                        g.fillRect(i * poleWymiar, j * poleWymiar, poleWymiar, poleWymiar);
                    }
                }
            }
        }
        g.setColor(Color.RED);
        g.drawString("SCORE: " + score, poleWymiar * 4, poleWymiar / 2);

        if (score > 1000) {
            timer.stop();
            gameLevel = 1;
        }

        if (!game) {
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", poleWymiar * 5, poleWymiar * 10);
        }
    }

    private void blockNew() {
        if (game) {
            blockPlace = new Point(5, 1);
            Random generator = new Random();
            block = generator.nextInt(6);
            orientation = generator.nextInt(3);

            if (!collisionTest(blockPlace.x, blockPlace.y, orientation)) {
                gameOver();
            }
        }
    }

    private void blockDraw() {
        if (game) {
            for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
                table[p.x + blockPlace.x][p.y + blockPlace.y] = block + 2;
            }
            repaint();
            scoreCombo = 50;
        }
    }

    private void gameOver() {
        timer.stop();
        game = false;
    }

    private boolean collisionTest(int x, int y, int orientation) {

        for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
            if (table[p.x + x][p.y + y] != 0) {
                return false;
            }
        }
        return true;
    }

    private void rotate() {
        if (game) {
            for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
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

    private void placeOntoBottom() {
        for (Point p : tetrisBlocks.tetrisBlocks[block][orientation]) {
            table[blockPlace.x + p.x][blockPlace.y + p.y] = block + 2;
        }
        score += 5;
        checkForFullRows();

        blockNew();
        blockDraw();
    }

    private void checkForFullRows() {
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


    private void deleteRow(int row) {
        for (int i = row; i > 1; i--) {
            for (int g = 1; g < 11; g++) {
                table[g][i] = table[g][i - 1];
            }
        }
        score += scoreCombo;
        scoreCombo *= 2;
        checkForFullRows();
    }

    private void timerRun(int timerDelay) {
        if (score < 1000) {
            SwingUtilities.invokeLater(() -> createAndShowGUI(1000));
        } else
    }


}
}