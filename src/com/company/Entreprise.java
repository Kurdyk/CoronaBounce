package com.company;

public class Entreprise extends Lieu {

    public String nom; // A ou B pour le moment;


    public Entreprise(int x, int y, String name) {
        super(x, y);
        this.nom = name;
        this.tempsIn = 800;
        InitLieu();
    }


    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    protected void streamConstr() {
        this.stream = "src/resources/Entreprise" + nom + "V2.png";
    }

    public void accueil(Individu individu) {
        //regarde si l'indivi du peut entrer sinon .rebound();
        if (individu instanceof Employe && individu.getSpecification().equals(nom) && individu.objective == 1) {
            this.inside(individu);
            individu.objective = 2;
        }
        else {
            individu.forcedRebound();
        }
    }

}
