package com.company;


import java.util.Random;

public class Individu extends Sprite {

    public static  int BOARD_WIDTH;
    public static  int BOARD_HEIGHT;
    public int X_SPEED = 2;
    public int Y_SPEED = 2;
    public int cmptRebond;

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
    public Individu(int x, int y, int h, int w) {
        super(x, y);

        initIndividu(h, w);
    
    }
    public static int getBOARD_WIDTH(){
        return BOARD_WIDTH;
    }

    public static int getBOARD_HEIGHT(){
        return BOARD_HEIGHT;
    }

    private void initIndividu() {
    	Individu.BOARD_HEIGHT=500;
    	Individu.BOARD_WIDTH=500;
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }
    private void initIndividu(int h, int w) {
    	Individu.BOARD_HEIGHT=h;
    	Individu.BOARD_WIDTH=w;
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


}
