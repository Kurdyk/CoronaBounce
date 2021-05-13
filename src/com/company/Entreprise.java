package com.company;

public class Entreprise extends Lieu {

    public String nom; // A ou B pour le moment;

    /**
     * Constructeur de la classe Entreprise
     * @param x Premiere coordonnee de l'Entreprise a placer
     * @param y Seconde coordonnee de l'Entreprise a placer
     * @param name Le nom de l'Entreprise a placer
     */
    public Entreprise(int x, int y, String name) {
        super(x, y);
        this.nom = name;
        this.tempsIn = 800;
        InitLieu();
    }

    /**
     * Recharge une image si necessaire
     */
    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    /**
     * Construit le chemin d'acces pour une image de type Entreprise
     */
    protected void streamConstr() {
        this.stream = "src/resources/Entreprise" + nom + "V2.png";
    }

    /**
     * Regarde si l'Individu en parametre est compatible avec l'entreprise, sinon le fait rebondir
     * @param individu L'Individu a faire rentrer dans le lieu
     */
    public void accueil(Individu individu) {
        if (individu instanceof Employe && individu.getSpecification().equals(nom) && individu.objective == 1) {
            inside(individu);
            individu.objective = 2;
        }
        else {
            individu.forcedRebound();
        }
    }

}
