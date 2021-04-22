package com.company;

public class Employe extends Individu {

    public String entrepriseName;

    public int entrepriseX;
    public int entrepriseY;
    public int entrepriseHeight;
    public int entrepriseWidth;

    public Employe (int x, int y) {
        super(x, y);
        InitEmploye();
    }

    private void InitEmploye() {
        this.streamConstr();
        reloadImage();
    }

    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    public void initEntrepriseDim(int x, int y, int height, int width){
         this.entrepriseX = x;
         this.entrepriseY = y;
         this.entrepriseHeight = height;
         this.entrepriseWidth = width;
    }

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


    public void move() {
        //Gere les rebonds sur le bord du tableau
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
                if (limitSupX == homeX + homeWidth || limitSupX == homeX + homeWidth - 1 || x_temp == homeX || x_temp == homeX) {
                    this.X_SPEED *= -1;
                    this.cmptRebond = 1;
                }

                if (limitSupY == homeY + homeHeight || limitSupY == homeY + homeHeight - 1 || y_temp == homeY || y_temp == homeY) {
                    this.Y_SPEED *= -1;
                    this.cmptRebond = 1;
                }
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

    public void setEtat(String newEtat){
        this.etat = newEtat;
    }

    public String getSpecification() {
        return entrepriseName;
    }

}
