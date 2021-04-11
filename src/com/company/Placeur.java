package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Placeur extends Sprite {

    private List<Individu> individu;
    private List<Lieu> lieu;

    public Placeur(int x, int y) {
        super(x, y);

        initPlaceur();
    }

    private void initPlaceur() {
        individu = new ArrayList<>();
        lieu = new ArrayList<>();
    }

    public List<Lieu> getLieu() {
        return lieu;
    }

    public List<Individu> getIndividus() {
        return individu;
    }

    public List<Employe> getEmployes() {
        List <Employe> res = new ArrayList<>();
        for (Individu indiv : individu) {
            if (indiv instanceof Employe) {
                res.add((Employe) indiv);
            }
        }
        return  res;
    }

    public List<Entreprise> getEntreprise() {
        List <Entreprise> res = new ArrayList<>();
        for (Lieu lie : lieu) {
            if (lie instanceof Entreprise) {
                res.add((Entreprise) lie);
            }
        }
        return  res;
    }

    public void spawn(int x, int y) {
        //Fait apparaitre un missile sur une coordonnee.
        individu.add(new Individu(x, y));
        individu.get(individu.size() - 1).etat = "Neutral";
        individu.get(individu.size() - 1).reloadImage();

    }

    public void spawnInfected(int x, int y) {
        //Fait apparaitre un infecté
        individu.add(new Individu(x, y));
        individu.get(individu.size() - 1).etat = "Infected";
        individu.get(individu.size() - 1).reloadImage();
    }

    public void spawnEmploye(int x, int y) {
        Employe employe = new Employe(x, y);
        employe.etat = "Neutral";
        employe.reloadImage();
        individu.add(employe);

    }

    public void spawnEmployeInfected(int x, int y) {
        //Fait apparaitre un infecté
        individu.add(new Employe(x, y));
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
            if ((i - 2) * 32 >= max_x) {
                i = 0;
                j+=2;
            }
            if ((j - 2) * 32 >= max_y) {
                System.out.println("Trop d'élements");
                return res;
            }
            cmpt++;
        }

        return res;
    }

    public void placementIndividus(int nbr, int nbrI, boolean alea){
        //place nbr cercles dans la fenetre et nbrI infectés
        Individu m = new Individu(x,y);
        int[][] coordonneesInit;
        if (alea) {
            coordonneesInit = genereCoordonnees(nbr + nbrI, m.getBOARD_WIDTH() - m.getBounds().width, m.getBOARD_HEIGHT() - m.getBounds().height);
        }
        else {
            coordonneesInit = coordonneesBrutes(nbr + nbrI, m.getBOARD_WIDTH() - m.getBounds().width, m.getBOARD_HEIGHT() - m.getBounds().height);
        }
        int a = 0;
        while (a < nbr) {
            spawnEmploye(coordonneesInit[a][0], coordonneesInit[a][1]);
            a++;
        }
        for (int b = a; b < nbr + nbrI; b++) {
            spawnInfected(coordonneesInit[b][0], coordonneesInit[b][1]);
        }
        m = null;

    }

    private int[][] coordonneesBrutesTot(int max_x, int max_y, Image image) {
        int[][] res = new int[0][2];

        int i = 1;
        int j = 1;
        int cmpt = 0;
        int height = image.getHeight(null);
        int width = image.getWidth(null);

        while(i * width < max_x && j * height < max_y) {
            res[cmpt][0] = i * width;
            res[cmpt][1] = j * height;
            i += 3;
            if ((i - 1) * width >= max_x - 5) {
                i = 0;
                j += 3;
            }
            if ((j - 1) * height >= max_y - 5) {
                return res;
            }
            cmpt++;
        }
        return res;
    }

    int [] randomList(int nbr, int bound) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i < bound ; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        int[] res = new int [nbr];
        for (int i = 0; i < nbr; i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    private int[][] chooseCoordinates(int nbrTot, int[][] choices) {
        int[][] res = new int[nbrTot][2];
        int [] randList = randomList(nbrTot, nbrTot);
        int cmpt = 0;
        while (cmpt < nbrTot) {
            res[cmpt] = choices[randList[cmpt]];
        }
        return res;
    }

    private int getIndice(Individu indiv) {
        for (int i = 0; i < individu.size(); i++) {
            if (individu.get(i) == indiv) {
                return i;
            }
        }
        return -1;
    }

    public void placeEntreprise(int x, int y, String name) {
        lieu.add(new Entreprise (x, y, name));
        lieu.get(lieu.size() - 1).reloadImage();
    }


    private int[][] coordinatesEnt(int n) {
        switch (n) {
            case 0:
                return null;
            case 1 :
                return new int[][] {{128, 128}};
            default : return new int[][] {{128, 128}, {256, 256}};
        }
    }

    //debug

    /*private void printList(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(tab[i][0] + " et " + tab[i][1] + "\n");
        }
    }*/
}