package com.company;

public class Entreprise extends Lieu {

    public String nom; // A ou B pour le moment;
    public int tempsDeTravail = 800;


    public Entreprise(int x, int y, String name) {
        super(x, y);
        this.nom = name;
        InitLieu();
        System.out.println(nom + " : milieu x : " + getCentre()[0] + ", milieu y : " + getCentre()[1]);
    }

    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    protected void streamConstr() {
        this.stream = "src/resources/Entreprise" + nom + "V2.png";
    }

    public void accueilEmploye(Employe employe) {
        if (employe.entrepriseName.equals(nom)) {
            this.inside(employe);
        }
        else {
            employe.forcedRebound();
        }
    }

    public void accueil(Individu individu) {
        //regarde si l'indivi du peut entrer sinon .rebound();
        if (individu instanceof Employe && individu.getSpecification().equals(nom)) {
            this.inside(individu);
        }
        else {
            individu.forcedRebound();
        }
    }

}
