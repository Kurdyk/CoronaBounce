package com.company;

public class Home extends Lieu {

    public int numero;

    public Home(int x, int y) {
        super(x, y);

        InitLieu();

    }

    protected void setNumero(int i) {
        this.numero = i;
    }

    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    protected void streamConstr() {
        this.stream = "src/resources/Home.png";
    }

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
