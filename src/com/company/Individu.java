package com.company;


import java.util.Random;

public class Individu extends Sprite {

    public static final int BOARD_WIDTH = 700;
    public static final int BOARD_HEIGHT = 800;
    public int X_SPEED;
    public int Y_SPEED;

    public int cmptRebond = 0;

    public int insideLieu = 0; //0 si hors d'un lieu, 1 entreprise, 2 si maison;
    public int cmptInside = 0;

    public int objective = 1; //1 si cherche un lieu de travail, 2 pour sa maison

    public int houseNumber;
    public int homeX;
    public int homeY;
    public int homeHeight;
    public int homeWidth;


    public String etat; //Infected, Recovered ou Neutral;
    public String stream;
    public int size = 0; //défaut : 0 ; petit sprite : 1;

    public int compteurGuerison = 0;
    public int dureeContamination = 1000;
    public int compteurReinfection = 0;
    public int dureeImmunite = 1200;


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
        goAlea();
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


    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    public void move() {
        //Gere les rebonds sur le bord du tableau et dans les maisons.
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
        this.X_SPEED = 0;
        this.Y_SPEED = 0;
    }


    public void goAlea() {
        Random rand = new Random();
        int tirageXSigne = rand.nextInt(2);
        int tirageXval = rand.nextInt(2) + 1;
        int tirageYSigne = rand.nextInt(2);
        int tirageYval = rand.nextInt(2) + 1;
        this.X_SPEED = (int) (Math.pow(-1, tirageXSigne) * tirageXval);
        this.Y_SPEED = (int) (Math.pow(-1, tirageYSigne) * tirageYval);
    }

    public String getSpecification(){
        return "Specification manquante";
    }

    public void setHouse(int i) {
        this.houseNumber = i;
    }

    public void setHomeLimits(Home home) {
        this.homeX = home.x;
        this.homeY = home.y;
        this.homeHeight = home.height;
        this.homeWidth = home.width;
    }

    public void setDuree(int i, int j) {
        this.dureeImmunite = i;
        this.dureeContamination = j;
    }



}
