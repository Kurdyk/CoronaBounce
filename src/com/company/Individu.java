package com.company;


import java.util.Random;

/**
 * La classe de base de notre simulation, les Individus representent des personnes
 */
public class Individu extends Sprite {

    /**
     * La largeur de la Board pour verifier les collisions avec les murs
     */
    public static final int BOARD_WIDTH = 800;
    /**
     * La longueur de la Board pour verifier les collisions avec les murs
     */
    public static final int BOARD_HEIGHT = 760;
    /**
     * La vitesse horizontale de l'Individu
     */
    public int X_SPEED;
    /**
     * La vitesse verticale de l'Individu
     */
    public int Y_SPEED;
    /**
     * Compte le nombre de rebonds par tour de boucle de l'Individu, l'evite de faire plusieurs rebonds qui s'annulent
     */
    public int cmptRebond = 0;
    /**
     * Donne la position de l'Individu : 0 si hors d'un Lieu, 1 Entreprise, 2 si Maison
     */
    public int insideLieu = 0;
    /**
     * Si l'Individu est dans un Lieu, compte depuis combien de temps
     */
    public int cmptInside = 0;
    /**
     * Donne le Lieu que l'Individu cherche a atteindre : 1 si cherche un lieu de travail, 2 pour sa maison
     */
    public int objective = 1;
    /**
     * Le numero du Home de l'Individu
     */
    public int houseNumber;
    /**
     * Premiere coordonnee du Home de l'Individu
     */
    public int homeX;
    /**
     * Premiere coordonnee du Home de l'Individu
     */
    public int homeY;
    /**
     * Largeur du Home de l'Individu
     */
    public int homeHeight;
    /**
     * Hauteur du Home de l'Individu
     */
    public int homeWidth;
    /**
     * Determine l'etat de contamination de l'Individu : Infected, Recovered ou Neutral
     */
    public String etat;
    /**
     * Determine le chemin d'acces a l'image correspondante a l'Individu
     */
    public String stream;
    /**
     * Determine la taille de l'image : defaut : 0 ; petit sprite : 1
     */
    public int size = 0;
    /**
     * Compte le temps que l'Individu a passe infecte pour le guerir a terme
     */
    public int compteurGuerison = 0;
    /**
     * Duree qu'un Individu passe a l'etat contamine
     */
    public int dureeContamination = 1000;
    /**
     * Compte le temps que l'Individu a passe immunise pour le rendre reinfectable a terme
     */
    public int compteurReinfection = 0;
    /**
     * Duree qu'un Individu passe a l'etat immunise
     */
    public int dureeImmunite = 1200;

    /**
     * Constructeur de la classe Individu
     * @param x Premiere coordonnee initiale de l'Individu
     * @param y Seconde coordonnee initiale de l'Individu
     */
    public Individu(int x, int y) {
        super(x, y);
        initIndividu();
    }

    /**
     * @return La largeur de la Board
     */
    public int getBOARD_WIDTH(){
        return BOARD_WIDTH;
    }

    /**
     *
     * @return La hauteur de la Board
     */
    public int getBOARD_HEIGHT(){
        return BOARD_HEIGHT;
    }

    /**
     * Initialise les variables de l'Individu
     */
    private void initIndividu() {

        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
        goAlea();
    }

    /**
     * Contruit le chemin d'acces a l'image correspondante
     */
    protected void streamConstr() {
        switch (size) {
            case 0 :
                this.stream = "src/resources/" + etat + ".png";
                break;
            case 1 :
                this.stream = "src/resources/Mini" + etat + ".png";
                break;
        }
    }

    /**
     * Recharge l'image de l'Individu
     */
    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    /**
     * Gere les rebonds sur le bord du tableau et dans les maisons, incremente les coordonnees de la vitesse
     */
    public void move() {

        int x_temp = x + X_SPEED;
        int y_temp = y + Y_SPEED;

        int limitSupX =  x_temp + this.getBounds().width;
        int limitSupY =  y_temp + this.getBounds().height;

        switch (insideLieu) {
            case 0 :
                if (limitSupX == BOARD_WIDTH || limitSupX == BOARD_WIDTH - 1 || x_temp == 0 || x_temp == 1) {
                    this.X_SPEED *= -1;
                    this.cmptRebond = 1;
                }

                if (limitSupY == BOARD_HEIGHT || limitSupY == BOARD_HEIGHT - 1 || y_temp == 0 || y_temp == 1) {
                    this.Y_SPEED *= -1;
                    this.cmptRebond = 1;
                }
                break;

            case 2 :
                if (limitSupX == homeX + homeWidth || limitSupX == homeX + homeWidth - 1 || x_temp == homeX || x_temp == homeX + 1) {
                    this.X_SPEED *= -1;
                    this.cmptRebond = 1;
                }

                if (limitSupY == homeY + homeHeight || limitSupY == homeY + homeHeight - 1 || y_temp == homeY || y_temp == homeY + 1) {
                    this.Y_SPEED *= -1;
                    this.cmptRebond = 1;
                }
                break;
        }

        x += X_SPEED;
        y += Y_SPEED;

    }

    /**
     * Fait rebondir l'Individu si il ne l'a pas deja fait dans le tour de boucle
     */
    public void rebound() {
        if (cmptRebond == 0) {
            this.X_SPEED *= -1;
            this.Y_SPEED *= -1;
            this.cmptRebond = 1;
        }
    }

    /**
     * Force un rebond pour l'Individu
     */
    public void forcedRebound() {
        this.X_SPEED *= -1;
        this.Y_SPEED *= -1;
        this.cmptRebond = 1;
    }

    /**
     * Contamine l'Individu
     */
    public void contamine() {
        this.etat = "Infected";
        reloadImage();
        getImageDimensions();
    }
    /**
     * Guerit l'Individu
     */
    private void gueri() {
        this.etat = "Recovered";
        reloadImage();
        getImageDimensions();
        this.compteurGuerison = 0;
    }
    /**
     * Rend l'Individu a nouveau contaminable
     */
    private void finImmunite(){
        this.etat = "Neutral";
        reloadImage();
        getImageDimensions();
        this.compteurReinfection = 0;
    }
    /**
     * Reinitialise le compteur de rebond
     */
    public void refresh(){
        this.cmptRebond = 0;
    }

    /**
     * Fait avancer les compteurs lies a la maladie
     */
    public void compteurUpdate() {
        switch (etat) {
            case "Infected" :
                this.compteurGuerison++;
                if (compteurGuerison >= dureeContamination) {
                    this.gueri();
                }
                break;
            case "Recovered" :
                this.compteurReinfection++;
                if (compteurReinfection >= dureeImmunite) {
                    this.finImmunite();
                }
                break;
        }
    }

    /**
     * Arrete l'Individu
     */
    public void stop() {
        this.X_SPEED = 0;
        this.Y_SPEED = 0;
    }

    /**
     * Donne une vitesse aleatoire a l'individu
     */
    public void goAlea() {
        Random rand = new Random();
        int tirageXSigne = rand.nextInt(2);
        int tirageXval = rand.nextInt(2) + 1;
        int tirageYSigne = rand.nextInt(2);
        int tirageYval = rand.nextInt(2) + 1;
        this.X_SPEED = (int) (Math.pow(-1, tirageXSigne) * tirageXval);
        this.Y_SPEED = (int) (Math.pow(-1, tirageYSigne) * tirageYval);
    }

    /**
     * Permet d'obtenir le nom d'Entreprise de l'Individu s'il est aussi un employe
     * @return le nom d'Entreprise de l'Individu, une erreur sinon
     */
    public String getSpecification(){
        return "Specification manquante";
    }

    /**
     * Fait correspondre le numero du Home de l'Individu a ce dernier
     * @param i
     */
    public void setHouse(int i) {
        this.houseNumber = i;
    }

    /**
     * Initialise les limites de deplacement a l'interieur du Home de l'Individu
     * @param home
     */
    public void setHomeLimits(Home home) {
        this.homeX = home.x;
        this.homeY = home.y;
        this.homeHeight = home.height;
        this.homeWidth = home.width;
    }

    /**
     * Donne de nouvelle valeur aux durees liees a la maladie
     * @param i
     * @param j
     */
    public void setDuree(int i, int j) {
        this.dureeImmunite = i;
        this.dureeContamination = j;
    }

}
