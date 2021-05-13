package com.company;

import java.awt.*;
import javax.swing.ImageIcon;



public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    /**
     * Constructeur d'un objet de classe Sprite
     * @param x Première coordonnee initiale
     * @param y Seconde coordonnee initiale
     */
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    /**
     * Permet le chargement initial de chaque image liee à un objet de classe Sprite
     * @param imageName Nom de l'image a charger
     */
    protected void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        ii.getImage().flush();
        this.image = ii.getImage();
    }

    /**
     * Initialise les dimensions d'un objet de type Sprite à partir des dimensions de son image
     */
    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    /**
     * @return Un Rectangle qui correspond au dimension du Sprite
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * @return L'image liee au Sprite
     */
    public Image getImage() {
        return image;
    }

    /**
     * @return La premiere coordonnee d'un Sprite
     */
    public int getX() {
        return x;
    }

    /**
     * @return La seconde coordonnee d'un Sprite
     */
    public int getY() {
        return y;
    }

    /**
     * @return Vrai si le Sprite est visible, faux sinon
     */
    public boolean isVisible() {
        return visible;
    }



}



