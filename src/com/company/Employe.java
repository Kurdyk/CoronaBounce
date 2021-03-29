package com.company;

public class Employe extends Individu{

    public String entrepriseName;
    private String stream;

    public Employe (int x, int y) {
        super(x, y);

        InitEmploye();
    }

    private void InitEmploye() {
        reloadImage();
        getImageDimensions();
    }

    protected void streamConstr() {
        switch (size) {
            case 0 :
                this.stream = "src/ressources/Employe" + etat + ".png";
                break;
            case 1 :
                this.stream = "src/ressources/Mini" + etat + ".png";
                break;
        }
    }


}
