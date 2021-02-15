package com.company;

public class Missile extends Sprite {

    private static final int BOARD_WIDTH = 500;
    private static final int BOARD_HEIGHT = 400;
    private int X_MISSILE_SPEED = 2;
    private int Y_MISSILE_SPEED = 2;

    public Missile(int x, int y) {
        super(x, y);

        initMissile();
    }

    public static int getBOARD_WIDTH(){
        return BOARD_WIDTH;
    }

    public static int getBOARD_HEIGHT(){
        return BOARD_HEIGHT;
    }

    private void initMissile() {

        loadImage("src/resources/Blue_Circle.png");
        getImageDimensions();
    }

    public void move() {
        //Gere (mal) les rebonds sur le bord du tableau
        x += X_MISSILE_SPEED;
        y += Y_MISSILE_SPEED;

        if (x > BOARD_WIDTH || y > BOARD_WIDTH) {
            visible = false;
        }

        if (x == BOARD_WIDTH || x == BOARD_WIDTH - 1|| x == 0 || x == 1) {
            X_MISSILE_SPEED *= -1;
        }

        if (y == BOARD_HEIGHT || y == BOARD_HEIGHT - 1|| y == 0 || y == 1) {
            Y_MISSILE_SPEED *= -1;
        }
    }
}