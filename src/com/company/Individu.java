package com.company;


import java.util.Random;

public class Individu extends Sprite {

    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 400;
    public int X_SPEED = 2;
    public int Y_SPEED = 2;
    public int saveXSpeed;
    public int saveYSpeed;
    public int cmptRebond = 0;
    public int insideLieu = 0; //0 si hors d'un lieu, 1 sinon;
    public int cmptInside = 0; //Utliser temps systeme;

    public String etat; //Infected, Recovered ou Neutral;
    public String stream;
    public int size = 0; //défaut : 0 ; petit sprite : 1;

    public int compteurGuerison = 0;
    public int dureeContamination = 500;
    public int compteurReinfection = 0;
    public int dureeImmunite = 1000;


    public Individu(int x, int y) {
        super(x, y);
        initIndividu();
    }

    public int getBOARD_WIDTH(){
        return BOARD_WIDTH;
    }

    public int getBOARD_HEIGHT(){
        return BOARD_HEIGHT;
    }

    private void initIndividu() {

        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

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

    //methode pour switch taille
    private void switchTaille(){

    }

    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    public void move() {
        //Gere les rebonds sur le bord du tableau
        int x_temp = x + X_SPEED;
        int y_temp = y + Y_SPEED;

        int limitSupX =  x_temp + this.getBounds().width;
        int limitSupY =  y_temp + this.getBounds().height;


        if (limitSupX == BOARD_WIDTH || limitSupX == BOARD_WIDTH - 1 || x_temp == 0 || x_temp == 1) {
            this.X_SPEED *= -1;
            this.cmptRebond = 1;
        }

        if (limitSupY == BOARD_HEIGHT || limitSupY == BOARD_HEIGHT - 1 || y_temp == 0 || y_temp == 1) {
            this.Y_SPEED *= -1;
            this.cmptRebond = 1;
        }

        x += X_SPEED;
        y += Y_SPEED;
    }

    public void rebound() {
        if (cmptRebond == 0) {
            this.X_SPEED *= -1;
            this.Y_SPEED *= -1;
            this.cmptRebond = 1;
        }
    }

    public void forcedRebound() {
        this.X_SPEED *= -1;
        this.Y_SPEED *= -1;
        this.cmptRebond = 1;
    }

    public void contamine() {
        this.etat = "Infected";
        reloadImage();
        getImageDimensions();
    }

    private void gueri() {
        this.etat = "Recovered";
        reloadImage();
        getImageDimensions();
        this.compteurGuerison = 0;
    }

    private void finImmunite(){
        this.etat = "Neutral";
        reloadImage();
        getImageDimensions();
        this.compteurReinfection = 0;
    }

    public void refresh(){
        this.cmptRebond = 0;
    }

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

    public String getEtat(){
        return etat;
    }

    public void setEtat(String newEtat) {
        this.etat = newEtat;
    }

    public void stop() {
        this.saveXSpeed = X_SPEED;
        this.saveYSpeed = Y_SPEED;
        this.X_SPEED = 0;
        this.Y_SPEED = 0;
    }

    public void go() {
        this.X_SPEED = 1;
        this.Y_SPEED = saveYSpeed;
        saveXSpeed = 0;
        saveYSpeed = 0;
    }

    public void goAlea() {
        Random rand = new Random();
        int tirage = rand.nextInt(4);
        switch (tirage) {
            case 0 :
                this.X_SPEED = - saveXSpeed;
                this.Y_SPEED = - saveYSpeed;
                saveXSpeed = 0;
                saveYSpeed = 0;
                break;
            case 1 :
                this.X_SPEED = saveXSpeed;
                this.Y_SPEED = - saveYSpeed;
                saveXSpeed = 0;
                saveYSpeed = 0;
                break;
            case 2 :
                this.X_SPEED = - saveXSpeed;
                this.Y_SPEED = saveYSpeed;
                saveXSpeed = 0;
                saveYSpeed = 0;
                break;
            case 3 :
                this.X_SPEED = saveXSpeed;
                this.Y_SPEED = saveYSpeed;
                saveXSpeed = 0;
                saveYSpeed = 0;
                break;
            default :
                this.go();
        }
    }

    public String getSpecification(){
        return "Specification manquante";
    }
}
