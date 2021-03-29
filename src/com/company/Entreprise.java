package com.company;

public class Entreprise extends Lieu {
    public Entreprise(int x, int y) {
        super(x, y);
    }

    public String nom; // A ou B pour le moment;
    public int tempsDeTravail = 800;
    public int capaciteAccueil = 5;

    public void accueilEmploye(Employe employe) {
        if (employe.entrepriseName.equals(nom)) {
            this.inside(employe);
        }
        else {
            employe.forcedRebound();
        }
    }

}
