package com.company;

import java.awt.event.KeyEvent;
import java.util.*;

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
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void spawn(int x, int y) {
        //Fait apparaitre un missile sur une coordonnee.
        missiles.add(new Missile(x + width, y + height / 2));
    }

    private int[][] genereCoordonnees(int nbr, int max_x, int max_y) {
        //Genere un tableau de coordonnees aleatoire entre le min et le max, peut en generer au meme endroit = pas bien
        int [][] tabCoordinates = new int[nbr][2];
        Set<int[]> SetCoordonnesPrises = new HashSet<>();
        Missile m = new Missile(x, y);
        int randomNum_x = -1;
        int randomNum_y = -1 ;
        Random rand = new Random();
        int [] a = {randomNum_x, randomNum_y};

        for (int i = 0; i < nbr; i++ ) {
            do {
                randomNum_x = rand.nextInt(max_x + 1);
                randomNum_y = rand.nextInt(max_y + 1);
                a[0] = randomNum_x;
                a[1] = randomNum_y;
            } while (SetCoordonnesPrises.contains(a));

            tabCoordinates[i][0] = randomNum_x;
            tabCoordinates[i][1] = randomNum_y;

            for (int j = 0; j <= m.getBounds().width; j++) {
                for (int k = 0; k <= m.getBounds().height; k++) {
                    int [] b = {randomNum_x + j, randomNum_y + k};
                    SetCoordonnesPrises.add(b);
                    b = null;
                }
            }
        }
        m = null;
        return tabCoordinates;
    }

    public void placementMissiles(int nbr){
        //place nbr missiles/cercles dans la fenetre
        Missile m = new Missile(x,y);
        int [][] coordonneesInit = genereCoordonnees(nbr, m.getBOARD_WIDTH() - m.getBounds().width - 10, m.getBOARD_HEIGHT() - m.getBounds().height - 10);
        for (int i = 0; i < nbr; i++) {
            spawn(coordonneesInit[i][0], coordonneesInit[i][1]);
        }
        m = null;
    }

}