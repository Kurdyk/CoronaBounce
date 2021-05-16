package com.company;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * Classe du graphique
 */
public class Graph extends JPanel {
        /**
         * nombre de "rembourage"
         */
        private int padding = 25;
        /**
         * nombre de "rembourage" des étiquettes
         */
        private int labelPad = 25;
        /**
         * donne un contour a la fenetre de dessin
         */
        private static final Stroke Graph_Stroke = new BasicStroke(2f);
        /**
         * taille diametre d'un point
         */
        private int pointWidth = 4;
        /**
         * nombre de division de la hauteur Y
         */
        private int numberYDiv = 10;

        /**
         * couleur de ligne
         */
        private Color lineColor = new Color(44, 102, 230, 180);
        /**
         * couleur des points
         */
        private Color pointColor = new Color(100, 100, 100, 180);



        /**
         * variable liee a la bord en cours
         */
        private Board board;
        /**
         * nombre de population
         */
        private int population;

        /**
         * liste d'individu sain
         */
        private List<Double> listSain;

        /**
         * constructeur de la classe graphique
         * @param view classe vue pour pouvoir recuperer la board courante
         * @param population nombre de population total (pas encore pu servir)
         */
        public Graph(Vue view, int population) {
                board = view.board;
                this.population = population;
                this.listSain = board.getListSain();
        }

        /**
         * dessine la courbe et les axes du graphique
         * @param g le g graphique de base de Java
         */
        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                double xScale = ((double) getWidth() - (2 * padding) - labelPad) / (listSain.size() - 1);
                double yScale = ((double) getHeight() - (2 * padding) - labelPad) / (getMaxSain() - getMinSain());




                List<Point> graphPoints = new ArrayList<>();
                for (int i = 0; i < listSain.size(); i++) {
                        int x1 = (int) (i * xScale + padding + labelPad);
                        int y1 = (int) (getMaxSain() -(listSain.get(i) * yScale) + padding);

                        graphPoints.add(new Point(x1, y1));
                        
                }

                for (int i=0;i<graphPoints.size();i++) {
                }

                g2.setColor(Color.WHITE);
                g2.fillRect(padding + labelPad, padding, getWidth() - (2 * padding) - labelPad, getHeight() - 2 * padding - labelPad);
                g2.setColor(Color.BLACK);



                for (int i = 0; i < numberYDiv + 1; i++) {
                        int x0 = padding+labelPad;
                        int x1 = pointWidth+padding+labelPad;
                        int y0=getHeight() - ((i * (getHeight() - padding * 2 - labelPad)) / numberYDiv + padding + labelPad);
                        int y1 = y0;
                        g2.drawLine(x0, y0, x1, y1);
                }

                g2.drawLine(padding + labelPad, getHeight() - padding - labelPad, padding + labelPad, padding);
                g2.drawLine(padding + labelPad, getHeight() - padding - labelPad, getWidth() - padding, getHeight() - padding - labelPad);

                Stroke oldStroke = g2.getStroke();
                g2.setColor(lineColor);
                g2.setStroke(Graph_Stroke);
                for (int i = 0; i < graphPoints.size() - 1; i++) {
                        int x1 = graphPoints.get(i).x;
                        int y1 = graphPoints.get(i).y;
                        int x2 = graphPoints.get(i + 1).x;
                        int y2 = graphPoints.get(i + 1).y;
                        g2.drawLine(x1, y1, x2, y2);
                }

                g2.setStroke(oldStroke);
                g2.setColor(pointColor);


                for (int i = 0; i < graphPoints.size(); i++) {
                        int x = graphPoints.get(i).x - pointWidth / 2;
                        int y = graphPoints.get(i).y - pointWidth / 2;
                        int ovalW = pointWidth;
                        int ovalH = pointWidth;
                        g2.fillOval(x, y, ovalW, ovalH);
                }

        }

        /**
         * donne le nombre d'individu minimum de la liste sain
         * @return en double le nombre minimum d'individu sain de la liste
         */
       private double getMinSain() {
                double minSain = Double.MAX_VALUE;
                for (Double sain : listSain) {
                        minSain = Math.min(minSain, sain);
                }
                return minSain;
        }

        /**
         * donne le nombre d'individu maximum de la liste sain
         * @return en double le nombre maximum d'individu sain de la liste
         */
        private double getMaxSain() {
                double maxSain = Double.MIN_VALUE;
                for (Double sain : listSain) {
                        maxSain = Math.max(maxSain, sain);
                }
                return maxSain;
        }

}