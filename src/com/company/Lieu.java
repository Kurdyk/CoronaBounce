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

    /**
     * Construit le chemin d'acces pour l'image correspondante
     */
    protected void streamConstr() {
        this.stream = "src/resources/" + typeLieu + nom + ".png";
    }

    /**
     * Constructeur de la classe
     * @param x
     * @param y
     */
    public Lieu(int x, int y) {
        super(x, y);

        InitLieu();
    }

    /**
     * Initialise le lieu
     */
    public void InitLieu() {
        contenu = new ArrayList<>();
        attenteIn = new ArrayList<>();
        attenteOut = new ArrayList<>();

        streamConstr();
        loadImage(stream);
        getImageDimensions();
        this.entree = this.getCentre();

    }

    /**
     * Recharge l'image du Lieu si necessaire
     */
    protected void reloadImage() {
        this.streamConstr();
        this.loadImage(stream);
        getImageDimensions();
    }

    /**
     * Initialise la duree où les individus restent dans le lieu
     * @param temps Le tempsIn voulu
     */
    public void setTempsIn(int temps) {
        this.tempsIn = temps;
    }

    /**
     * Retourne les coordonnees du centre du lieu
     * @return Les coordonnees du centre sous forme de tableau
     */
    public int[] getCentre() {
        int[] res = new int[2];
        res[0] = x + width / 2;
        res[1] = y + height / 2;
        return res;
    }


    /**
     * Initialise la sortie du lieu
     * @param bWidth La largeur de la Board
     * @param bHeight La longueur de la Board
     */
    protected void setSortie(int bWidth, int bHeight) {
        int[] res = new int[2];
        Rectangle NorthWest = new Rectangle(0, 0, bWidth / 2, bHeight / 2);
        Rectangle NorthEast = new Rectangle(bWidth / 2, 0, bWidth / 2, bHeight / 2);
        Rectangle SouthWest = new Rectangle(0, bHeight / 2, bWidth / 2, bHeight / 2);
        Rectangle SouthEast = new Rectangle(bWidth / 2, bHeight / 2, bWidth / 2, bHeight / 2);

        res[0] = this.x + this.width / 2;

        if (this.getBounds().intersects(NorthWest)) {
            int milieuY = (int) NorthWest.getCenterY();

            if (this.y < milieuY) {
                res[1] = this.y + this.height + 5;
            } else {
                res[1] = this.y - 40; //pour assez de place pour indiv
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(NorthEast)) {
            int milieuY = (int) NorthEast.getCenterY();
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
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 5;
            } else {
                res[1] = this.y - 40;
            }
            this.sortie = res;
            return;
        }

        if (this.getBounds().intersects(SouthEast)) {
            int milieuY = (int) SouthEast.getCenterY();
            if (this.y < milieuY) {
                res[1] = this.y + this.height + 5;
            } else {
                res[1] = this.y - 40;
            }
            this.sortie = res;
            return;
        }

    }

    /**
     * Gère l'accueil de l'Individu en paramètre dans le Lieu
     * @param individu L'Individu a faire rentrer dans le lieu
     */
    public void accueil(Individu individu) {
    }

    /**
     * La place d'entree est prise, met l'Individu en paramètre en attente
     * @param individu L'Individu a faire rentrer dans le Lieu
     */
    public void enAttenteEntree(Individu individu) {
        attenteIn.add(individu);
        individu.stop();
    }

    /**
     * Place le premier Individu de la liste d'attente dans le Lieu
     */
    public void placeAttente() {
        inside(attenteIn.get(0));
        attenteIn.remove(0);
    }

    /**
     * Sort le premier Individu de la liste d'attente de sortie en dehors du Lieu
     * @param individus L'Individu a faire sortir du Lieu
     */
    public void sortAttente(List<Individu> individus) {
        Individu individu = attenteOut.get(0);
        if (placeDispoOut(sortie[0], sortie[1], individus)) {
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

    /**
     * Fait sortir un Individu present depuis assez longtemps, le met en liste d'attente si la place de sortie est occupee.
     * @param individu L'Individu a faire sortir du Lieu
     */
    private void sortie(Individu individu) {
        attenteOut.add(individu);
    }

    /**
     * Verifie si la sortie est libre
     * @param x Premiere coordonee de la sortie
     * @param y Seconde coordonne de la sortie
     * @param individus La liste des Individus de la Board
     * @return true si la place est libre sinon false
     */
    boolean placeDispoOut(int x, int y, List<Individu> individus) {
        Rectangle potentiel = new Rectangle(x - 16, y - 16, 48, 48);
        for (Individu individu : individus) {
            if (potentiel.intersects(individu.getBounds())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifie si l'entree est libre
     * @param x Premiere coordonnee de l'entree
     * @param y Seconde coordonnee de la sortie
     * @return true si la place est libre sinon false
     */
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

    /**
     * Prend l'Individu en parametre et le place a l'entree du Lieu avec modification de ses caracteristiques
     * @param individu L'Individu a faire rentrer dans le Lieu
     */
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

    /**
     * Update les listes d'attente de sortie et d'entree
     * @param individus La liste de tous les Individus de la Board
     */
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

    /**
     * Update les individus du contenu du Lieu et entame le processus de sortie si besoin
     */
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