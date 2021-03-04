package com.company;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private SpaceShip spaceship;
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

        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);


        timer = new Timer(DELAY, this);
        timer.start();
        spaceship.placementMissiles(10, 3);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawObjects(g);


        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {

        /*if (spaceship.isVisible()) {
            g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(),
                    this);
        }*/

        List<Missile> ms = spaceship.getMissiles();

        for (Missile missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(),
                        missile.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateMissiles();
        CheckCollisions();

        repaint();
    }


    private void inGame() {

        if (!ingame) {
            timer.stop();
        }
    }


    private void updateMissiles() {

        List<Missile> ms = spaceship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Missile m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    public void CheckCollisions() {
        List<Missile> ms = spaceship.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (Missile m1 : ms) {
                Rectangle r2 = m1.getBounds();
                /*r2.x -= 1;
                r2.y -= 1;
                r2.width += 2;
                r2.height += 2;*/ //Essai pour eviter le bug de collision

                if (m != m1 && r1.intersects(r2)) {
                    m.rebound();
                    CheckContamination(m,m1);
                }
            }
        }
    }

    protected void CheckContamination (Missile m, Missile m1) {
        if (m.etat == "Infected" && m1.etat == "Sain") {
            m1.contamine();
        }
        if (m1.etat == "Infected" && m.etat == "Sain") {
            m.contamine();
        }
    }
}