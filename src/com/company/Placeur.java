package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * La classe Placeur permet de gerer l'initialisation des objets sur la Board, elle contient aussi la liste de tous les Individus et Lieux
 */
public class Placeur {

    int bWidth;
    int bHeight;

    private List<Individu> individu;
    private List<Lieu> lieu;

    /**
     * Constructeur de la classe Placeur
     * @param width Limite en largueur (en x) de la Board qui utilise ce placeur
     * @param height Limite en hauteur (en y) de la Board qui utilise ce placeur
     */
    public Placeur(int width, int height) {
        this.bWidth = width;
        this.bHeight = height;
        initPlaceur();
    }

    /**
     * Initialise les listes contentant les Individus et les Lieux
     */
    private void initPlaceur() {
        individu = new ArrayList<>();
        lieu = new ArrayList<>();
    }

    /**
     * @return La liste des Lieux places sur la Board
     */
    public List<Lieu> getLieu() {
        return lieu;
    }

    /**
     * @return La liste des Individus places sur la Board
     */
    public List<Individu> getIndividus() {
        return individu;
    }

    /**
     * @return La liste des Employes places sur la Board
     */
    public List<Employe> getEmployes() {
        List <Employe> res = new ArrayList<>();
        for (Individu indiv : individu) {
            if (indiv instanceof Employe) {
                res.add((Employe) indiv);
            }
        }
        return  res;
    }

    /**
     * @return La liste des Entreprises placees sur la Board
     */
    public List<Entreprise> getEntreprise() {
        List <Entreprise> res = new ArrayList<>();
        for (Lieu lie : lieu) {
            if (lie instanceof Entreprise) {
                res.add((Entreprise) lie);
            }
        }
        return  res;
    }

    /**
     * @return La liste des Homes places sur la Board
     */
    public List<Home> getHomes() {
        List <Home> res = new ArrayList<>();
        for (Lieu l : lieu) {
            if (l instanceof Home) {
                res.add((Home) l);
            }
        }
        return res;
    }

    /**
     * Initialise un Individu
     * @param x Première coordonnee initiale
     * @param y Seconde coordonnee initiale
     */
    public void spawn(int x, int y) {
        individu.add(new Individu(x, y));
        individu.get(individu.size() - 1).etat = "Neutral";
        individu.get(individu.size() - 1).reloadImage();

    }

    /**
     * Initialise un Individu infecte
     * @param x Première coordonnee initiale
     * @param y Seconde coordonnee initiale
     */
    public void spawnInfected(int x, int y) {
        individu.add(new Individu(x, y));
        individu.get(individu.size() - 1).etat = "Infected";
        individu.get(individu.size() - 1).reloadImage();
    }

    /**
     * Initialise un Employe
     * @param x Première coordonnee initiale
     * @param y Seconde coordonnee initiale
     */
    public void spawnEmploye(int x, int y) {
        Employe employe = new Employe(x, y);
        employe.etat = "Neutral";
        employe.reloadImage();
        individu.add(employe);

    }

    /**
     * Initialise un Employe infecte
     * @param x Première coordonnee initiale
     * @param y Seconde coordonnee initiale
     */
    public void spawnEmployeInfected(int x, int y) {
        //Fait apparaitre un infecte
        individu.add(new Employe(x, y));
        individu.get(individu.size() - 1).etat = "Infected";
        individu.get(individu.size() - 1).reloadImage();
    }

    /**
     * Genere un tableau de coordonnees aleatoires pour placer des individus
     * @param nbr Nombre d'Individus à placer
     * @param max_x Largeur de la board
     * @param max_y Hauteur de la Board
     * @return Le tableau fait pour initialiser les Individus sans maison
     */
    private int[][] genereCoordonnees(int nbr, int max_x, int max_y) {
        int [][] tabCoordinates = new int[nbr][2];
        Set<int[]> SetCoordonnesPrises = new HashSet<>();
        int randomNum_x = -1;
        int randomNum_y = -1 ;
        Random rand = new Random();
        int [] a = {randomNum_x, randomNum_y};

        for (int i = 0; i < nbr; i++) {
            do {
                randomNum_x = rand.nextInt(max_x - 3) + 2; //Il ne faut pas generer 0 ou 1;
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

        return tabCoordinates;
    }

    /**
     * Genere un tableau de coordonnees non aleatoire pour placer des individus
     * @param nbr Nombre d'Individus à placer
     * @param max_x Largeur de la board
     * @param max_y Hauteur de la Board
     * @return Le tableau fait pour initialiser les Individus sans maison
     */
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
                System.out.println("Trop d'elements");
                return res;
            }
            cmpt++;
        }

        return res;
    }

    /**
     * Place les Individus sur la Board
     * @param nbr Nombre voulu d'Individus
     * @param nbrI Nombre d'infectes voulu
     * @param alea Placement aleatoire (true) ou non (false)
     */
    public void placementIndividus(int nbr, int nbrI, boolean alea){
        Individu m = new Individu(-1,-1);
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

    /**
     * Generateur de sequence aleatoire d'entiers positifs sans doublon
     * @param nbr Nombre de nombres voulu
     * @param bound Limite superieure des entiers
     * @return La sequence aleatoire de taille nbr et majoree par bound
     */
    int [] randomTab(int nbr, int bound) {
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

    /**
     * Place une Entreprise sur la Board et lui donne son nom
     * @param x Première coordonnee du placement
     * @param y Seconde coordonnee du placement
     * @param name Nom à donner à l'Entreprise placee
     */
    public void placeEntreprise(int x, int y, String name) {
        lieu.add(new Entreprise (x, y, name));
        lieu.get(lieu.size() - 1).reloadImage();
    }

    /**
     * Place les Entreprises sur la Board
     * @param nbr Le nombre d'Entreprises voulu
     */
    public void placeEntreprises(int nbr) {
        ArrayList<int[]> coordonnees = coordinatesEnt(nbr);
        String[] names = new String[] {"A", "B"};
        for (int i = 0; i < nbr && i < 2; i++) {
            int [] coor = coordonnees.get(i);
            placeEntreprise(coor[0], coor[1], names[i]);
        }
    }

    /**
     * Cherche les coordonnees pour placer les Entreprises
     * @param n Le nombre d'Entreprises voulu
     * @return La liste des coordonees pour placer les Entreprises
     */
    private ArrayList<int[]> coordinatesEnt(int n) {
        ArrayList<int[]> res = new ArrayList<>();
        int midX = bWidth / 2;
        int midY = bHeight / 2;
        for (int i = 0; i < n && i < 2; i++) {
            int[] temp = new int[2];
            switch (i) {
                case 0:
                    temp[0] = midX - 128;
                    temp[1] = midY - 128;
                    res.add(temp);
                    break;
                case 1:
                    temp[0] = midX;
                    temp[1] = midY;
                    res.add(temp);
                    break;
            }
        }
        return res;
    }


    public void contaminationInitiale(int scale) {
        int nbrContamines = (int) individu.size() * scale / 100;
        int[] aContaminer = randomTab(nbrContamines, individu.size());
        for (int elt : aContaminer) {
            (individu.get(elt)).etat = "Infected";
        }
    }

    /**
     * Permet de transformer des Individus en Employes de l'entreprise A ou B
     * @param nbr Nombre d'Employes voulu
     */
    public void choixEmployes(int nbr) {
        int switcher = 1;
        int[] aEmployer = randomTab(nbr, individu.size());
        for (int i = 0; i < aEmployer.length; i++) {
            Individu temp = individu.get(i);
            individu.remove(i);
            Employe newEmploye = new Employe(temp.x, temp.y);
            newEmploye.etat = temp.etat;
            if (switcher == 1) {
                newEmploye.entrepriseName = "A";
                newEmploye.reloadImage();
                switcher *= -1;
            } else {
                newEmploye.entrepriseName = "B";
                newEmploye.reloadImage();
                switcher *= -1;
            }
            individu.add(i, newEmploye);
            temp = null;
        }
    }

    /**
     * Genere une liste de coordonnees pour placer les Homes avec un espacement entre chaque Home
     * @param width Largeur de la Board
     * @param height Hauteur de la Board
     * @return La liste des coordonnees pour placer les Homes
     */
    private ArrayList<int[]> coordonneesHome(int width, int height) {
        ArrayList<int[]> res = new ArrayList<>();
        int maxPerLigne = width / 160;

        for (int i = 0; i < maxPerLigne; i++) {
            res.add(new int[] {i * 160, 0});
        }
        for (int j = 0; j < maxPerLigne; j++) {
            res.add(new int[] {j * 160, height - 64});
        }
        return res;
    }

    /**
     * Compare deux int a et b
     * @param a Premier int
     * @param b Second int
     * @return Le plus petit des deux
     */
    private int min(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }

    /**
     * Place les Home sur la Board
     * @param nbr Nombre voulu
     * @param width Largeur de la Board
     * @param height Hauteur de la Board
     * @return Le nombre de Homes places au final
     */
    public int placeHome(int nbr, int width, int height) {

        ArrayList<int[]> coordonnees = coordonneesHome(width, height);
        int nbrEffectif = min(coordonnees.size(), nbr);
        for (int i = 0; i < nbrEffectif; i++) {
            int[] coor = coordonnees.get(i);
            Home newHome = new Home(coor[0], coor[1]);
            newHome.setNumero(i);
            newHome.reloadImage();
            lieu.add(newHome);
        }

        return nbrEffectif;
    }

    /**
     * On place les Individus dans les maisons et on les lie entre eux
     * @param nbrI Nombre d'Individus
     * @param nbrH Nombre de Homes
     */
    public void placeInHome(int nbrI, int nbrH) {
        int nbrPerHome = nbrI / nbrH;
        int reste = nbrI % nbrH;
        int i = 0;
        for (Home home : getHomes()) {
            int lX = home.x;
            int lY = home.y;
            int lWidth = home.width;
            int lHeight = home.height;

            Rectangle NorthWest = new Rectangle(lX, lY, lWidth / 2, lHeight /2);
            Rectangle NorthEast = new Rectangle(lX + lWidth / 2, lY, lWidth / 2, lHeight /2);
            Rectangle SouthWest = new Rectangle(lX, lY + lHeight / 2, lWidth / 2, lHeight /2);
            Rectangle SouthEast = new Rectangle(lX + lWidth / 2, lY + lHeight / 2, lWidth / 2, lHeight /2);

            for (int j = 0; j < nbrPerHome && j < 4; j++) {
                Individu indiv = individu.get(i);
                indiv.setHouse(home.numero);
                i++;
                indiv.size = 1;
                indiv.reloadImage();
                indiv.insideLieu = 2;
                indiv.goAlea();
                home.contenu.add(indiv);
                switch (j) {
                    case 0 :
                        indiv.x = NorthWest.x + 8;
                        indiv.y = NorthWest.y + 8;
                        indiv.cmptInside = 0;
                        break;
                    case 1 :
                        indiv.x = NorthEast.x + 8;
                        indiv.y = NorthEast.y + 8;
                        indiv.cmptInside = 100;
                        break;
                    case 2 :
                        indiv.x = SouthWest.x + 8;
                        indiv.y = SouthWest.y + 8;
                        indiv.cmptInside = 200;
                        break;
                    default :
                        indiv.x = SouthEast.x + 8;
                        indiv.y = SouthEast.y + 8;
                        indiv.cmptInside = 300;
                        break;
                    }
                }
            }
        placeReste(reste, nbrH, i);
    }

    /**
     * Place les Individus restans à placer après le premier passage de PlaceInHome
     * @param nbrR Le nombre d'Individus à placer
     * @param nbrH Le nombre de Homes total
     * @param start A quel Individu de la liste individus commencer
     */
    private void placeReste(int nbrR, int nbrH, int start) {
        int [] ordre = randomTab(nbrR, nbrH);
        for (int i = 0; i < nbrR; i++) {
            placeSoloInHome(individu.get(start + i), getHomes().get(ordre[i]));
        }
    }

    /**
     * Place un Individu donne dans un Home donne
     * @param individu L'individu à placer
     * @param home La maison dans laquelle le placer
     */
    private void placeSoloInHome(Individu individu, Home home) {
        int nbrIn = home.contenu.size();
        individu.size = 1;
        individu.setHouse(home.numero);
        individu.reloadImage();
        individu.insideLieu = 2;
        home.contenu.add(individu);
        switch (nbrIn) {
            case 0 :
                Rectangle NorthWest = new Rectangle(home.x, home.y, home.width / 2, home.height /2);
                individu.x = NorthWest.x + 8;
                individu.y = NorthWest.y + 8;
                individu.cmptInside = 0;
                break;
            case 1 :
                Rectangle NorthEast = new Rectangle(home.x + home.width / 2, home.y, home.width / 2, home.height /2);
                individu.x = NorthEast.x + 8;
                individu.y = NorthEast.y + 8;
                individu.cmptInside = 100;
                break;
            case 2 :
                Rectangle SouthWest = new Rectangle(home.x, home.y + home.height / 2, home.width / 2, home.height /2);
                individu.x = SouthWest.x + 8;
                individu.y = SouthWest.y + 8;
                individu.cmptInside = 200;
                break;
            default :
                Rectangle SouthEast = new Rectangle(home.x + home.width / 2, home.y + home.height / 2, home.width / 2, home.height /2);
                individu.x = SouthEast.x + 8;
                individu.y = SouthEast.y + 8;
                individu.cmptInside = 300;
                break;
        }
    }
}
