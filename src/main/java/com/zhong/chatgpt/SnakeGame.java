package com.zhong.chatgpt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SnakeGame extends JFrame implements KeyListener {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int CELL_SIZE = 10;
    private static final int MAX_CELLS = (WIDTH * HEIGHT) / (CELL_SIZE * CELL_SIZE);

    private int[] x, y;
    private int cells;
    private int foodX, foodY;
    private boolean left, right, up, down;

    public SnakeGame() {
        setTitle("Java贪吃蛇");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setVisible(true);

        x = new int[MAX_CELLS];
        y = new int[MAX_CELLS];
        startGame();
    }

    private void startGame() {
        cells = 3;
        for (int i = 0; i < cells; i++) {
            x[i] = 50 - i * CELL_SIZE;
            y[i] = 50;
        }

        generateFood();

        Timer timer = new Timer(100, e -> {
            if (left) moveLeft();
            if (right) moveRight();
            if (up) moveUp();
            if (down) moveDown();
            move();
        });
        timer.start();
    }

    private void generateFood() {
        foodX = (int) (Math.random() * (WIDTH - CELL_SIZE));
        foodY = (int) (Math.random() * (HEIGHT - CELL_SIZE));
        foodX = foodX - (foodX % CELL_SIZE);
        foodY = foodY - (foodY % CELL_SIZE);
    }

    private void move() {
        for (int i = cells; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (x[0] == foodX && y[0] == foodY) {
            cells++;
            generateFood();
        }

        if (x[0] < 0) x[0] = WIDTH - CELL_SIZE;
        if (x[0] >= WIDTH) x[0] = 0;
        if (y[0] < 0) y[0] = HEIGHT - CELL_SIZE;
        if (y[0] >= HEIGHT) y[0] = 0;

        for (int i = 1; i < cells; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                JOptionPane.showMessageDialog(this, "Game Over!");
                System.exit(0);
            }
        }

        repaint();
    }

    private void moveLeft() {
        left = true;
        right = false;
        up = false;
        down = false;
        x[0] -= CELL_SIZE;
    }

    private void moveRight() {
        left = false;
        right = true;
        up = false;
        down = false;
        x[0] += CELL_SIZE;
    }

    private void moveUp() {
        left = false;
        right = false;
        up = true;
        down = false;
        y[0] -= CELL_SIZE;
    }

    private void moveDown() {
        left = false;
        right = false;
        up = false;
        down = true;
        y[0] += CELL_SIZE;
    }

    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < cells; i++) {
            g.setColor(Color.GREEN);
            g.fillRect(x[i], y[i], CELL_SIZE, CELL_SIZE);
        }

        g.setColor(Color.RED);
        g.fillRect(foodX, foodY, CELL_SIZE, CELL_SIZE);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT && !right) moveLeft();
        if (keyCode == KeyEvent.VK_RIGHT && !left) moveRight();
        if (keyCode == KeyEvent.VK_UP && !down) moveUp();
        if (keyCode == KeyEvent.VK_DOWN && !up) moveDown();
    }
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new SnakeGame();
    }
}