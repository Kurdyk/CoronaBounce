package com.company;

public class Home extends Lieu {

    public int numero;

    /**
     * Constucteur de la classe Home
     * @param x La premiere coordonnee du Home a placer
     * @param y La seconde coordonnee du Home a placer
     */
    public Home(int x, int y) {
        super(x, y);

        InitLieu();
        tempsIn = 600;
    }

    /**
     * Initialise le numero du Home
     * @param i
     */
    protected void setNumero(int i) {
        this.numero = i;
    }


    /**
     * Construit le chemin d'acces a l'image d'un Home
     */
    @Override
    protected void streamConstr() {
        this.stream = "src/resources/Home.png";
    }

    /**
     * Gere l'entree d'Individu en parametre dans sa maison
     * @param individu L'Individu a faire rentrer dans le lieu
     */
    @Override
    public void accueil(Individu individu) {
        if (individu.houseNumber == numero && individu.objective == 2) {
            inside(individu);
            individu.objective = 1;
        } else {
            individu.forcedRebound();
        }
    }
}
