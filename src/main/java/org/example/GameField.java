package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    private final int BLOCK_SIZE = 16;
    private final int ALL_BLOCKS = 400;
    private Image body;
    private Image block;
    private int blockX;
    private int blockY;
    private int[] x = new int[ALL_BLOCKS];
    private int[] y = new int[ALL_BLOCKS];
    private int blocks;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.darkGray);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);

    }

    public void initGame(){
        blocks = 3;
        for (int i = 0; i < blocks; i++) {
            x[i] = 48 - i * BLOCK_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250,this);
        timer.start();
        createblock();
    }

    public void createblock(){
        blockX = new Random().nextInt(20) * BLOCK_SIZE;
        blockY = new Random().nextInt(20) * BLOCK_SIZE;
    }

    public void loadImages(){
        ImageIcon blockImage = new ImageIcon("block.png");
        block = blockImage.getImage();
        ImageIcon bodyImage = new ImageIcon("body.png");
        body = bodyImage.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(block, blockX, blockY,this);
            for (int i = 0; i < blocks; i++) {
                g.drawImage(body,x[i],y[i],this);
            }
        } else {
            String str = "Game Over";
            g.setColor(Color.white);
            g.drawString(str,125, SIZE/2);
        }
    }

    public void move() {
        for (int i = blocks; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(left) {
            x[0] -= BLOCK_SIZE;
        }
        if(right) {
            x[0] += BLOCK_SIZE;
        } if(up) {
            y[0] -= BLOCK_SIZE;
        } if(down) {
            y[0] += BLOCK_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == blockX && y[0] == blockY){
            blocks++;
            createblock();
        }
    }

    public void checkCollisions(){
        for (int i = blocks; i > 0 ; i--) {
            if(i > 4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down){
                left = false;
                up = true;
                right = false;
            }

            if (key == KeyEvent.VK_DOWN && !up){
                left = false;
                down = true;
                right = false;
            }

        }
    }

}
