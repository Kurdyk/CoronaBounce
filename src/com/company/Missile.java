package com.company;

public class Missile extends Sprite {

    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 400;
    public int X_MISSILE_SPEED = 2;
    public int Y_MISSILE_SPEED = 2;
    public String etat;

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

        loadImage("src/resources/Neutral.png");
        getImageDimensions();
    }

    public void move() {
        //Gere les rebonds sur le bord du tableau (considere quelques pixels vides du png)
        x += X_MISSILE_SPEED;
        y += Y_MISSILE_SPEED;

        int limitSupX =  x + this.getBounds().width;
        int limitSupY =  y + this.getBounds().height;

        if (x > BOARD_WIDTH || y > BOARD_WIDTH) {
            visible = false;
        }

        if (limitSupX == BOARD_WIDTH || limitSupX == BOARD_WIDTH - 1 || x == -1 || x == 0 || x == 1) {
            this.X_MISSILE_SPEED *= -1;
        }

        if (limitSupY == BOARD_HEIGHT || limitSupY == BOARD_HEIGHT - 1 || y == -1 || y == 0 || y == 1) {
            this.Y_MISSILE_SPEED *= -1;
        }
    }

    public void rebound() {
        this.X_MISSILE_SPEED *= -1;
        this.Y_MISSILE_SPEED *= -1;
    }
}
