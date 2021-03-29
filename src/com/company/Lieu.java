package com.company;

import java.util.*;
import java.util.List;

public class Lieu extends Sprite {

    String stream;
    String typeLieu;
    String nom;

    private List<Individu> contenu;

    protected void streamConstr() {
        this.stream = "src/ressources/" + typeLieu + nom + ".png";
    }

    public Lieu(int x, int y) {
        super(x, y);

        InitLieu();
    }

    private void InitLieu() {
        streamConstr();
        loadImage(stream);
        getImageDimensions();
    }

    public int[] getCentre(){
        int [] res = new int[2];
        res[0] = width / 2;
        res[1] = height / 2;
        return res;
    }

    public List<Individu> getContenu(){
        return contenu;
    }

    private void accueil(Individu individu) {
        contenu.add(individu);
    }

    private void sortie(Individu individu) {
        contenu.remove(individu);
    }

    public int[][] removeCase(int[][] array, int index) {
        int[][] copy = new int[array.length - 1][2];

        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index) {
                copy[j++] = array[i];
            }
        }
        return copy;
    }

    private int[][] findEmptySpace() {
        int[][] res = this.caseOccupees();

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

    public void inside(Individu individu) {
        int[] place = findSpaceToSpawn(individu);
        if (place != null) {
            individu.x = place[0];
            individu.y = place[1];
        }
    }
}
