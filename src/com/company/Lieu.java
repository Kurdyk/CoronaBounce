package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Lieu extends Sprite {

    public String stream;
    public String typeLieu;
    public String nom;
    private int[] entree;
    public int tempsIn;
    private int[] sortie;
    private int nombreEntree = 0;

    public List<Individu> contenu;
    private List<Individu> attenteIn;
    private List<Individu> attenteOut;

    protected void streamConstr() {
        this.stream = "src/resources/" + typeLieu + nom + ".png";
    }

    public Lieu(int x, int y) {
        super(x, y);

        InitLieu();
    }

    public void InitLieu() {
        contenu = new ArrayList<>();
        attenteIn = new ArrayList<>();
        attenteOut = new ArrayList<>();

        System.out.println("taille de contenu : " + contenu.size());
        System.out.println("taille de attenteIn : " + attenteIn.size());
        System.out.println("taille de attenteOut : " + attenteOut.size());


        streamConstr();
        loadImage(stream);
        getImageDimensions();
        this.entree = this.getCentre();

    }


    protected void reloadImage() {
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    public void setTempsIn(int temps) {
        this.tempsIn = temps;
    }

    public int[] getCentre() {
        int[] res = new int[2];
        res[0] = x + width / 2;
        res[1] = y + height / 2;
        return res;
    }


    protected void setSortie(int bWidth, int bHeight) {
        int[] res = new int[2];
        Rectangle NorthWest = new Rectangle(0, 0, bWidth / 2, bHeight / 2);
        Rectangle NorthEast = new Rectangle(bWidth / 2, 0, bWidth / 2, bHeight / 2);
        Rectangle SouthWest = new Rectangle(0, bHeight / 2, bWidth / 2, bHeight / 2);
        Rectangle SouthEast = new Rectangle(bWidth / 2, bHeight / 2, bWidth / 2, bHeight / 2);

        if (this.getBounds().intersects(NorthWest)) {
            int milieuY = (int) NorthWest.getCenterY();
            res[0] = this.x + this.width / 2;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 5;
            } else {
                res[1] = this.y - 40; //pour assez de place pour indiv
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(NorthEast)) {
            int milieuX = (int) NorthEast.getCenterX();
            int milieuY = (int) NorthEast.getCenterY();
            res[0] = this.x + this.width / 2;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 5;
            } else {
                res[1] = this.y - 40;
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(SouthWest)) {
            int milieuY = (int) SouthWest.getCenterY();
            res[0] = this.x + this.width / 2;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 5;
            } else {
                res[1] = this.y - 40;
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(SouthEast)) {
            int milieuX = (int) SouthEast.getCenterX();
            int milieuY = (int) SouthEast.getCenterY();
            res[0] = this.x + this.width / 2;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 5;
            } else {
                res[1] = this.y - 40;
            }
            this.sortie = res;
            return;
        }

    }

    public List<Individu> getContenu() {
        return contenu;
    }

    public void accueil(Individu individu) {
    }

    public void enAttenteEntree(Individu individu) {
        attenteIn.add(individu);
        individu.stop();
    }

    public void placeAttente() {
        inside(attenteIn.get(0));
        attenteIn.remove(0);
    }

    public void sortAttente(List<Individu> individus) {
        Individu individu = attenteOut.get(0);
        if (placeDispoOut(individu, sortie[0], sortie[1], individus)) {
            individu.size = 0;
            individu.insideLieu = 0;
            individu.cmptInside = 0;
            contenu.remove(individu);
            attenteOut.remove(0);
            individu.x = sortie[0];
            individu.y = sortie[1];
            individu.goAlea();
            individu.reloadImage();
        }
    }

    private void sortie(Individu individu) {
        attenteOut.add(individu);
    }

    boolean placeDispoOut(Sprite sprite, int x, int y, List<Individu> individus) {
        Rectangle potentiel = new Rectangle(x - 16, y - 16, 48, 48);
        for (Individu individu : individus) {
            if (potentiel.intersects(individu.getBounds())) {
                return false;
            }
        }
        return true;
    }

    boolean placeDispo(int x, int y) {
        if (contenu == null) {
            return true;
        }
        Rectangle potentiel = new Rectangle(x, y,8, 8);
        for (Individu individu : contenu) {
            Rectangle r = individu.getBounds();
            if (r.intersects(potentiel)) {
                return false;
            }
        }
        return true;
    }

    public void inside(Individu individu) {
        if (placeDispo(entree[0], entree[1]) && nombreEntree == 0) {
            individu.size = 1;
            individu.reloadImage();
            individu.insideLieu = individu.objective;
            individu.cmptInside = 0;
            contenu.add(individu);
            individu.x = entree[0];
            individu.y = entree[1];
            individu.goAlea();

            nombreEntree = 1;
        } else {
            enAttenteEntree(individu);
        }
    }

    public void updateAttente(List<Individu> individus) {
        if (attenteIn.size() != 0) {
            if (placeDispo(entree[0], entree[1])) {
                placeAttente();
            }
        }
        if (attenteOut.size() != 0) {
            sortAttente(individus);
        }
        nombreEntree = 0;
    }

    public void checkout() {
        if (contenu.size() != 0) {
            for (Individu individu : contenu) {
                individu.cmptInside++;
                if (individu.cmptInside >= this.tempsIn && !attenteOut.contains(individu)) {
                    sortie(individu);
                }
            }
        }
    }
}