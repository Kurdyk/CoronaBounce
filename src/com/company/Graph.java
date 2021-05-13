package com.company;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Graph extends JPanel {
        private int width = 800;
        private int height = 400;
        private int padding = 25;
        private int labelPad = 25;
        private static final Stroke Graph_Stroke = new BasicStroke(2f);
        private int pointWidth = 4;
        private int numberYDiv = 10;

        private Color lineColor = new Color(44, 102, 230, 180);
        private Color pointColor = new Color(100, 100, 100, 180);
        private Color gridColor = new Color(200, 200, 200, 200);


        private Board board;
        private int sain;
        private int infect;
        private Placeur p;
        private List<Individu> individu;
        private List<Double> listSain;

        public Graph(Vue view, int sain, int infect) {
                board = view.board;
                this.sain = sain;
                this.infect = infect;
                p = board.getPlaceur();
                individu = p.getIndividus();
                this.listSain = board.getListSain();
        }

        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                double xScale = ((double) getWidth() - (2 * padding) - labelPad) / (listSain.size() - 1);
                double yScale = ((double) getHeight() - (2 * padding) - labelPad) / (infect+sain);

                for(int i = 0; i<listSain.size();i++){
                        System.out.println(listSain.get(i));
                }


                List<Point> graphPoints = new ArrayList<>();
                for (int i = 0; i < listSain.size(); i++) {
                        int x1 = (int) (i * xScale + padding + labelPad);
                        int y1 = (int) (getMaxSain()-(listSain.get(i) * yScale) + padding);
                        System.out.println(listSain.get(i));
                        graphPoints.add(new Point(x1, y1));
                }

                for (int i=0;i<graphPoints.size();i++) {
                        //System.out.println(graphPoints.get(i));

                }



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

        private double getMinSain() {
                double minSain = Double.MAX_VALUE;
                for (Double sain : listSain) {
                        minSain = Math.min(minSain, sain);
                }
                return minSain;
        }

        private double getMaxSain() {
                double maxSain = Double.MIN_VALUE;
                for (Double sain : listSain) {
                        maxSain = Math.max(maxSain, sain);
                }
                return maxSain;
        }




}
