package com.wojtek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class TetrisGui extends JFrame {

    final static int windowScale = 30;
    final static int fieldNumberVertical = 23;
    final static int fieldNumberHorizontal = 12;
    final static int marginVertical = 5;
    final static int marginHorizontal = 14;
    public static int fieldSize;
    public static int score = 0;
    public int timerDelay = 1000;


    public TetrisGui(int level) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        fieldSize = screenSize.height / windowScale;
        int frameHeight = fieldSize * fieldNumberVertical - marginVertical;
        int frameWidth = fieldSize * fieldNumberHorizontal + marginHorizontal;
        setVisible(true);
        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        Tetris tetris = new Tetris(timerDelay);
        add(tetris);
        tetris.resetTable();

        KeyListener keyListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch ( e.getKeyCode() ) {
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

        addKeyListener(keyListener);


    }
}


