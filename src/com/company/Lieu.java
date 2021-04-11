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


    private List<Individu> contenu;
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

    public int []getSortie() {
        int [] res = new int[2];
        return res;
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

    private void sortie(Individu individu) {
        individu.size = 0;
        individu.reloadImage();
        individu.insideLieu = 0;
        contenu.remove(individu);
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
            individu.insideLieu = 1;
            contenu.add(individu);
            individu.x = entree[0];
            individu.y = entree[1];
        }
        else {
            System.out.println("ici");
            enAttenteEntree(individu);
        }
    }

    public void updateAttente() {
        if (attenteIn.size() != 0) {
            Individu individu = attenteIn.get(0);
            if (placeDispo(individu,entree[0],entree[1])) {
                placeAttente();
            }
        }
    }

    public void outside(Individu individu) {
        //Trouver une place proche dans le bord et le sortir.
    }

    public void checkOut(){
        for (Individu individu : contenu) {
            if (individu.cmptInside >= tempsIn) {
                outside(individu);
            }
        }
    }


   /* public int[][] removeCase(int[][] array, int index) {
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
