package com.company;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceShip extends Sprite {

    private int dx;
    private int dy;
    private List<Missile> missiles;

    public SpaceShip(int x, int y) {
        super(x, y);

        initSpaceShip();
    }

    private void initSpaceShip() {

        missiles = new ArrayList<>();

        loadImage("src/resources/craft.png");
        getImageDimensions();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }


    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }

    public void spawn(int x, int y) {
        //Fait apparaitre un missile sur une coordonnee.
        missiles.add(new Missile(x + width, y + height / 2));
    }

    private int[][] genereCoordonnees(int nbr, int max_x, int max_y) {
        //Genere un tableau de coordonnees aleatoire entre le min et le max
        int [][] tabCoordinates = new int[nbr][2];
        int randomNum_x;
        int randomNum_y;
        Random rand = new Random();
        for (int i = 0; i < nbr; i++ ) {
            randomNum_x = rand.nextInt(max_x + 1);
            randomNum_y = rand.nextInt(max_y + 1);
            tabCoordinates[i][0] = randomNum_x;
            tabCoordinates[i][1] = randomNum_y;
        }
        return tabCoordinates;
    }

    public void placementMissiles(int nbr){
        //place nbr missiles/cercles dans la fenetre
        int [][] coordonneesInit = genereCoordonnees(nbr + 1, Missile.getBOARD_WIDTH(), Missile.getBOARD_HEIGHT());
        for (int i = 0; i < nbr + 1; i++) {
            spawn(coordonneesInit[i][0], coordonneesInit[i][1]);
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}