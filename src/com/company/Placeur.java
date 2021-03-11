package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Placeur extends Sprite {

    private List<Individu> individu;

    public Placeur(int x, int y) {
        super(x, y);

        initPlaceur();
    }

    private void initPlaceur() {
        individu = new ArrayList<>();
    }

    public List<Individu> getIndividus() {
        return individu;
    }

    public void spawn(int x, int y) {
        //Fait apparaitre un missile sur une coordonnee.
        individu.add(new Individu(x, y));
        individu.get(individu.size() - 1).etat = "Sain";

    }

    public void spawnInfected(int x, int y) {
        //Fait apparaitre un infecté
        individu.add(new Individu(x, y));
        individu.get(individu.size() - 1).etat = "Infected";
        individu.get(individu.size() - 1).reloadImage();
    }

    private int[][] genereCoordonnees(int nbr, int max_x, int max_y) {
        //Genere un tableau de coordonnees aleatoire entre le min et le max, peut en generer au meme endroit = pas bien
        int [][] tabCoordinates = new int[nbr][2];
        Set<int[]> SetCoordonnesPrises = new HashSet<>();
        int randomNum_x = -1;
        int randomNum_y = -1 ;
        Random rand = new Random();
        int [] a = {randomNum_x, randomNum_y};

        for (int i = 0; i < nbr; i++) {
            do {
                randomNum_x = rand.nextInt(max_x - 3) + 2; //Faut pas générer 0 ou 1;
                randomNum_y = rand.nextInt(max_y - 3) + 2; //Idem
                a[0] = randomNum_x;
                a[1] = randomNum_y;
            } while (SetCoordonnesPrises.contains(a));

            tabCoordinates[i][0] = randomNum_x;
            tabCoordinates[i][1] = randomNum_y;

            for (int j = -32; j <= 32; j++) {
                for (int k = -32; k <= 32; k++) {
                    int [] b = {randomNum_x + j, randomNum_y + k};
                    SetCoordonnesPrises.add(b);
                    b = null;
                }
            }
        }
        //printList(tabCoordinates);
        return tabCoordinates;
    }

    private int[][] coordonneesBrutes(int nbr, int max_x, int max_y) {
        int[][] res = new int[nbr][2];

        int i = 1;
        int j = 1;
        int cmpt = 0;

        while(cmpt < nbr) {
            res[cmpt][0] = i * 32;
            res[cmpt][1] = j * 32;
            i+= 2;
            if ((i - 1) * 32 >= max_x) {
                i = 0;
                j+=2;
            }
            if ((j - 1) * 32 >= max_y) {
                System.out.println("Trop d'élements");
                return res;
            }
            cmpt++;
        }

        return res;
    }

    public void placementMissiles(int nbr, int nbrI, boolean alea){
        //place nbr missiles/cercles dans la fenetre et nbrI infectés
        Individu m = new Individu(x,y);
        int[][] coordonneesInit;
        if (alea) {
            coordonneesInit = genereCoordonnees(nbr + nbrI, m.getBOARD_WIDTH() - m.getBounds().width, m.getBOARD_HEIGHT() - m.getBounds().height);
        }
        else {
            coordonneesInit = coordonneesBrutes(nbr + nbrI, m.getBOARD_WIDTH() - m.getBounds().width, m.getBOARD_HEIGHT() - m.getBounds().height);
        }
        int i = 0;
        while (i < nbr) {
            spawn(coordonneesInit[i][0], coordonneesInit[i][1]);
            i++;
        }
        for (int j = i; j < nbr + nbrI; j++) {
            spawnInfected(coordonneesInit[j][0], coordonneesInit[j][1]);
        }
        m = null;
    }

    //debug

    /*private void printList(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(tab[i][0] + " et " + tab[i][1] + "\n");
        }
    }*/
}