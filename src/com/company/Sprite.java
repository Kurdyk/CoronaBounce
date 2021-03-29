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

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        ii.getImage().flush();
        this.image = ii.getImage();
    }


    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public int[][] caseOccupees() {
        int taille = this.height * this.width;
        int res[][] = new int[taille][2];

        for (int i = 0; i < taille; i++) {
            for (int j = this.y; j < this.y + this.height; j++) {
                for (int k = this.x; k < this.x + this.height; k++) {
                    res[i] = new int[]{k, j};
                }
            }
        }

        return res;
    }

}



