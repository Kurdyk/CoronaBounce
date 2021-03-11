package com.company;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Placeur placeur;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 400;
    private final int DELAY = 15;


    public Board() {

        initBoard();
    }

    private void initBoard() {

        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        placeur = new Placeur(ICRAFT_X, ICRAFT_Y);


        timer = new Timer(DELAY, this);
        timer.start();
        placeur.placementMissiles(10, 1, false);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawObjects(g);


        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {


        List<Individu> ls = placeur.getIndividus();

        for (Individu individu : ls) {
            if (individu.isVisible()) {
                g.drawImage(individu.getImage(), individu.getX(),
                        individu.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateIndividus();
        CheckCollisionsFutures();
        refreshAll();

        repaint();
    }


    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }


    private void updateIndividus() {

        List<Individu> ls = placeur.getIndividus();

        for (int i = 0; i < ls.size(); i++) {

            Individu individu = ls.get(i);

            if (individu.isVisible()) {
                individu.move();
                individu.compteurUpdate();
            } else {
                ls.remove(i);
            }
        }
    }

    private void CheckCollisionsFutures() {
        List<Individu> ls = placeur.getIndividus();
        for (Individu individu1 : ls) {

            Rectangle r1 = individu1.getBounds();
            r1.x += individu1.X_SPEED;
            r1.y += individu1.Y_SPEED;

            for (Individu individu2 : ls) {
                Rectangle r2 = individu2.getBounds();

                if (individu1 != individu2 && r1.intersects(r2)) {
                    individu1.rebound();
                    CheckContamination(individu1, individu2);
                }
            }
        }
    }

    public void CheckCollisions() {
        List<Individu> ls = placeur.getIndividus();

        for (Individu m : ls) {

            Rectangle r1 = m.getBounds();

            for (Individu m1 : ls) {
                Rectangle r2 = m1.getBounds();

                if (m != m1 && r1.intersects(r2)) {
                    m.rebound();
                    CheckContamination(m,m1);
                }
            }
        }
    }

    protected void CheckContamination (Individu m, Individu m1) {
        if (m.etat.equals("Infected") && m1.etat.equals("Sain")) {
            m1.contamine();
        }
        if (m1.etat.equals("Infected") && m.etat.equals("Sain")) {
            m.contamine();
        }
    }

    private void refreshAll(){
        List<Individu> ls = placeur.getIndividus();
        for (int i = 0; i < ls.size(); i++) {
            Individu individu = ls.get(i);
            individu.refresh();
        }
    }

}