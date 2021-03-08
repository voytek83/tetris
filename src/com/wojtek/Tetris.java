package com.wojtek;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JPanel {

    public static int pW;
    int rotation = 0;
    private int[][] tablica;
    private Point origin;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pW = screenSize.height / 30;
        int frameHeight = pW * 23 - 5;
        int frameWidth = pW * 12 + 14;

        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        final Tetris tetris = new Tetris();
        tetris.zerowanieTablicy();
        frame.add(tetris);
        tetris.nowyKlocek();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);

                        tetris.spadniecie();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();


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
    }

    @Override
    public void paintComponent(Graphics g) {

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 22; j++) {
                switch (tablica[i][j]) {
                    case 0:
                        g.setColor(Color.GRAY);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 1:
                        g.setColor(Color.DARK_GRAY);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 2:
                        g.setColor(Color.GREEN);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 3:
                        g.setColor(Color.YELLOW);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 4:
                        g.setColor(Color.RED);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 5:
                        g.setColor(Color.ORANGE);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 6:
                        g.setColor(Color.CYAN);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 7:
                        g.setColor(Color.MAGENTA);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                    case 8:
                        g.setColor(Color.BLUE);
                        g.fillRect(i * pW, j * pW, pW, pW);
                        break;
                }
            }
        }
    }

    private void nowyKlocek() {
        origin = new Point(5, 2);


    }

    private void spadniecie() {

        origin.y += 1;
        repaint();
    }

    private void rysowanieKlocka(klocki, rotation) {

    }

    private enum rotation {
        1,2,3,4;
    }

    private enum klocki {
        kwKloc, lKloc, odwLKloc, iKloc, zKloc, odwZKloc, tKloc;
    }


}