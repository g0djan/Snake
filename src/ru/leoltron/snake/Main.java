package ru.leoltron.snake;

import lombok.val;
import ru.leoltron.snake.game.ClassicSnakeController;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.generators.ClassicAppleGenerator;
import ru.leoltron.snake.game.generators.ClassicGameFieldGenerator;
import ru.leoltron.snake.gui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static ru.leoltron.snake.game.Direction.*;

public class Main {
    private static void setFrame(JFrame frame,
                                 GUI gui,
                                 int widthSize,
                                 int heightSize,
                                 KeyListener listener){
        frame.add(gui);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setPreferredSize(new Dimension(widthSize, heightSize));
        frame.getContentPane().add(gui, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(listener);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws InterruptedException {
        val fieldWidth = 16;
        val fieldHeight = 16;
        val widthSize = fieldWidth * 64;
        val heightSize = fieldHeight * 64;
        val game = new Game(
                new ClassicAppleGenerator(),
                new ClassicGameFieldGenerator(),
                new ClassicSnakeController(),
                fieldWidth,
                fieldHeight);
        game.startNewGame();
        val gui = new GUI(fieldWidth, fieldHeight, game);
        val frame = new JFrame();

        val listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        game.setCurrentDirection(UP);
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        game.setCurrentDirection(DOWN);
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        game.setCurrentDirection(RIGHT);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        game.setCurrentDirection(LEFT);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        };
        setFrame(frame, gui, widthSize, heightSize, listener);
        while(true) {
            game.tick();
            SwingUtilities.updateComponentTreeUI(frame);
            Thread.sleep(300);
        }
    }
}