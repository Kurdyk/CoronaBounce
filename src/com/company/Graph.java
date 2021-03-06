package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * Classe du graphique
 */
public class Graph extends JPanel {
        /**
         * Nombre de "rembourage"
         */
        private int padding = 25;
        /**
         * Nombre de "rembourage" des étiquettes
         */
        private int labelPad = 25;
        /**
         * Donne un contour a la fenetre de dessin
         */
        private static final Stroke Graph_Stroke = new BasicStroke(2f);
        /**
         * Taille diametre d'un point
         */
        private int pointWidth = 4;
        /**
         * Nombre de division de la hauteur Y
         */
        private int numberYDiv = 10;

        /**
         * Couleur de ligne
         */
        private Color lineColor = new Color(44, 102, 230, 180);
        /**
         * Couleur des points
         */
        private Color pointColor = new Color(100, 100, 100, 180);

        /**
         * Variable liee a la bord en cours
         */
        private Board board;
        /**
         * Nombre de population
         */
        private int population;

        /**
         * Liste d'individu sain
         */
        private List<Double> listSain;

        /**
         * Constructeur de la classe graphique
         * @param view classe vue pour pouvoir recuperer la board courante
         */
        public Graph(Vue view) {
                board = view.board;
                this.population = board.getPlaceur().getIndividus().size();
                this.listSain = board.getListSain();
        }

        /**
         * Dessine la courbe et les axes du graphique des individus contamines
         * @param g le g graphique de base de Java
         */
        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                double xScale = ((double) getWidth() - (2 * padding) - labelPad) / (listSain.size() - 1);
                double yScale = ((double) getHeight() - (2 * padding)- labelPad) / (population);



                List<Point> graphPoints = new ArrayList<>();
                for (int i = 0; i < listSain.size(); i++) {
                        int x1 = (int) (i * xScale + padding + labelPad);
                        int inf = (int) (population - listSain.get(i));
                        int y = getHeight() - 2 * padding;
                        int y1 = (y - inf * (int)yScale) ;


                        graphPoints.add(new Point(x1, y1));
                        
                }


                g2.setColor(Color.WHITE);
                g2.fillRect(padding + labelPad, padding, getWidth() - (2 * padding) - labelPad, getHeight() - 2 * padding - labelPad);
                g2.setColor(Color.BLACK);


                for (int i = 0; i < numberYDiv + 1; i++) {
                        int x0 = padding + labelPad;
                        int x1 = pointWidth + padding + labelPad;
                        int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPad)) / numberYDiv + padding + labelPad);
                        int y1 = y0;


                        if (listSain.size() > 0) {

                                g2.drawLine(padding + labelPad + 1 + pointWidth, y0, getWidth() - padding, y1);
                                g2.setColor(Color.BLACK);
                               // String yLabel = ((int) ((getMinSain() + (getMaxSain() - getMinSain()) * ((i * 1.0) / numberYDiv)) * 100)) / 100.0 + "";
                                String yLabel = (population/10)*i +"";
                                FontMetrics metrics = g2.getFontMetrics();
                                int labelWidth = metrics.stringWidth(yLabel);
                                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
                        }
                        g2.drawLine(x0, y0, x1, y1);}

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
                repaint();
        }

}