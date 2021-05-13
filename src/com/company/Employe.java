package com.company;

public class Employe extends Individu {

    public String entrepriseName;
    private int tempsOut = 0;

    public int entrepriseX;
    public int entrepriseY;
    public int entrepriseHeight;
    public int entrepriseWidth;

    /**
     * Constucteur de la classe Employe
     * @param x Premier coordonnee initiale de l'Employe
     * @param y Seconde coordonnee initiale de l'Employe
     */
    public Employe (int x, int y) {
        super(x, y);
        InitEmploye();
    }

    /**
     * Initialise l'image de l'Employe
     */
    private void InitEmploye() {
        this.streamConstr();
        reloadImage();
    }

    /**
     * Initialise les limites de deplacement de l'Employe quand il est en Entreprise
     * @param x Premiere coordornee de l'Entreprise de l'Employe
     * @param y Seconde coordornee de l'Entreprise de l'Employe
     * @param height Hauteur de l'Entreprise de l'Employe
     * @param width Largeur coordornee de l'Entreprise de l'Employe
     */
    public void initEntrepriseDim(int x, int y, int height, int width){
         this.entrepriseX = x;
         this.entrepriseY = y;
         this.entrepriseHeight = height;
         this.entrepriseWidth = width;
    }

    /**
     * Construit le chemin d'acces pour l'image de l'Employe
     */
    @Override
    protected void streamConstr() {
        switch (size) {
            case 0 :
                this.stream = "src/resources/Employe" + entrepriseName + this.etat + ".png";
                break;
            case 1 :
                this.stream = "src/resources/Mini" + this.etat + ".png";
                break;
        }
    }

    /**
     * Gere les mouvements d'un Individu de classe Employe
     */
    @Override
    public void move() {
        int x_temp = x + X_SPEED;
        int y_temp = y + Y_SPEED;

        int limitSupX = x_temp + this.getBounds().width;
        int limitSupY = y_temp + this.getBounds().height;

        switch (insideLieu) {

            case 1 :
                if (limitSupX == entrepriseX + entrepriseWidth || limitSupX == entrepriseX + entrepriseWidth - 1 || x_temp == entrepriseX || x_temp == entrepriseX + 1) {
                    this.X_SPEED *= -1;
                    this.cmptRebond = 1;
                }

                if (limitSupY == entrepriseY + entrepriseHeight || limitSupY == entrepriseY + entrepriseHeight -  1 || y_temp == entrepriseY || y_temp == entrepriseY + 1) {
                    this.Y_SPEED *= -1;
                    this.cmptRebond = 1;
                }

                x += X_SPEED;
                y += Y_SPEED;
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
                x += X_SPEED;
                y += Y_SPEED;

                break;

            default :
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
                break;
        }
    }

    /**
     * Permet d'obtenir le nom de l'Entreprise de l'Employe
     * @return Le nom de l'Entreprise de l'Employe
     */
    public String getSpecification() {
        return entrepriseName;
    }

    /**
     * Si l'Employe passe trop de temps dehors sans retourner chez lui, lui permet un retour en Entreprise
     */
    public void updateTempsOut() {
        if (this.insideLieu == 0) {
            tempsOut++;
            if (tempsOut > 1500) {
                this.objective = 1;
                tempsOut = 0;
            }
        }
    }
}
