package com.company;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Lieu extends Sprite {

    public String stream;
    public String typeLieu;
    public String nom;
    private int[] entree;
    public int tempsIn = 100;
    private int[] sortie;

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

        streamConstr();
        loadImage(stream);
        getImageDimensions();
        this.entree = this.getCentre();

    }

    protected void reloadImage(){
        this.streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    public int[] getCentre(){
        int [] res = new int[2];
        res[0] = x + width / 2;
        res[1] = y + height / 2;
        return res;
    }


    protected void setSortie(int bWidth, int bHeight) {
        int[] res = new int[2];
        Rectangle NorthWest = new Rectangle(0, 0, bWidth / 2, bHeight /2);
        Rectangle NorthEast = new Rectangle(bWidth / 2, 0, bWidth / 2, bHeight /2);
        Rectangle SouthWest = new Rectangle(0, bHeight / 2, bWidth / 2, bHeight /2);
        Rectangle SouthEast = new Rectangle(bWidth / 2, bHeight / 2, bWidth / 2, bHeight /2);

        if (this.getBounds().intersects(NorthWest)) {
            int milieuY = (int) NorthWest.getCenterY();
            res[0] = this.x + this.width / 2;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 1;
            } else {
                res[1] = this.y - 33; //pour assez de place pour indiv
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(NorthEast)) {
            int milieuX = (int) NorthEast.getCenterX();
            int milieuY = (int) NorthEast.getCenterY();
            res[0] = this.x + this.width / 2 ;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 1;
            } else {
                res[1] = this.y - 33;
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(SouthWest)) {
            int milieuX = (int) SouthWest.getCenterX();
            int milieuY = (int) SouthWest.getCenterY();
            res[0] = this.x + this.width / 2;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 1;
            } else {
                res[1] = this.y - 33;
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(SouthEast)) {
            int milieuX = (int) SouthEast.getCenterX();
            int milieuY = (int) SouthEast.getCenterY();
            res[0] = this.x + this.width / 2;
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 1;
            } else {
                res[1] = this.y - 33;
            }
            this.sortie = res;
            return;
        }

    }

    public List<Individu> getContenu(){
        return contenu;
    }

    public void accueil(Individu individu) {
    }

    public void enAttenteEntree(Individu individu) {
        attenteIn.add(individu);
        individu.stop();
    }

    public void placeAttente() {
        Individu individu = attenteIn.get(0);
        attenteIn.remove(0);
        inside(individu);
        individu.go();
    }

    public void sortAttente(List<Individu> individus) {
        Individu individu = attenteOut.get(0);
        if (placeDispoOut(individu, sortie[0], sortie[1], individus)) {
            individu.size = 0;
            individu.insideLieu = 0;
            contenu.remove(individu);
            attenteOut.remove(0);
            individu.x = sortie[0];
            individu.y = sortie[1];
            individu.X_SPEED = 2;
            individu.Y_SPEED = - 2;
            individu.reloadImage();
        }
    }

    private void sortie(Individu individu) {
        attenteOut.add(individu);
    }

    boolean placeDispoOut(Sprite sprite, int x, int y, List<Individu> individus) {
        Rectangle potentiel = new Rectangle(x, y, sprite.width, sprite.height);
            for (Individu individu : individus) {
                if (potentiel.intersects(individu.getBounds())) {
                    return false;
                }
            }
        return true;
    }

    boolean placeDispo(Sprite sprite, int x, int y) {
        if (contenu == null) {
            return true;
        }
        Rectangle potentiel = new Rectangle(x, y, sprite.width, sprite.height);
        for (Sprite spriteIn : contenu) {
            Rectangle r = spriteIn.getBounds();
            if (r.intersects(potentiel)) {
                return false;
            }
        }
        return true;
    }

    public void inside(Individu individu) {
        if (placeDispo(individu, entree[0], entree[1])) {
            individu.size = 1;
            individu.reloadImage();
            individu.insideLieu = individu.objective;
            individu.cmptInside = 0;
            contenu.add(individu);
            individu.x = entree[0];
            individu.y = entree[1];
            individu.X_SPEED = 1;
            individu.Y_SPEED = 2;
        }
        else {
            enAttenteEntree(individu);
        }
    }

    public void updateAttente(List<Individu> individus) {
        if (attenteIn.size() != 0) {
            Individu individu = attenteIn.get(0);
            if (placeDispo(individu,entree[0],entree[1])) {
                placeAttente();
            }
        }
        if (attenteOut.size() != 0) {
            sortAttente(individus);
        }
    }

    public void checkout () {
        if (contenu.size() != 0) {
            for (Individu individu : contenu) {
                individu.cmptInside++;
                if (individu.cmptInside >= this.tempsIn) {
                    sortie(individu);
                }
            }
        }
    }


   /* POUBELLE


   public int[][] removeCase(int[][] array, int index) {
        int[][] copy = new int[array.length - 1][2];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }

    private void removeCaseParValeur(int [][] array, int []tab) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == tab) {
                removeCase(array, i);
            }
        }
    }

        private int[][] findEmptySpace() {
        int[][] res = this.caseOccupees();

        for (int i = x; i < width; i++) {
            removeCaseParValeur(res, new int[]{x + i, y});
        }

        for (int j = y; j < height; j++) {
            removeCaseParValeur(res, new int[]{x, y + j});
        }

        if (contenu != null) {
            for (Sprite spriteIn : contenu) {

                int[][] espaceOcc = spriteIn.caseOccupees();
                int tailleSprite = spriteIn.width * spriteIn.height;

                for (int i = 0; i < tailleSprite; i++) {
                    for (int j = 0; j < res.length; j++) {

                        if (res[j] == espaceOcc[i]) {
                            res = removeCase(res, j);
                        }

                    }
                }
            }
        }
        return res;
    }

    public HashSet<int[]> convertToSet(int [][] array) {
        HashSet<int[]> res = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            res.add(array[i]);
        }
        return res;
    }

    public int[] findSpaceToSpawn(Sprite sprite) {
        int width = sprite.width;
        int height = sprite.height;

        int[][] emptySpace = findEmptySpace();
        HashSet<int[]> emptySpaceSet = convertToSet(emptySpace);

        for (int k = 0; k < emptySpace.length; k++) {
                int[] coordinates = emptySpace[k];
                boolean okaySpace = true;
                for (int i = coordinates[0]; i <= width ; i++) {
                    if (!okaySpace) {
                        break;
                    }
                    for (int j = coordinates[1] ; i <= height ; j++) {
                        if(!okaySpace) {
                            break;
                        }
                        if (!emptySpaceSet.contains(coordinates)) {
                            okaySpace = false;
                            break;
                        }
                    }
                }
                if (okaySpace) {
                    return coordinates;
                }
        }
        return null;
    }
   */

}
