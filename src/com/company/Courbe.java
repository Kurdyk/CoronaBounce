package com.company;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * classe qui permettre de créer une fenetre contenant le graphique résumé de la simulation
 */
public class Courbe extends JFrame{
	/**
	 * creer l'objet qui va contenir le graphique
	 */
	JPanel Graphique= new JPanel();

	Courbe(Vue view,int x, int y, int p){
		/**
		 * fixe le titre de la fenetre
		 */
		this.setTitle("Graphique");
		/**
		 * fixe les dimensions de la fenetre
		 */
		this.setSize(800, 400);
		/**
		 * fixe la position par defaut de la fenetre
		 */
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-400, Toolkit.getDefaultToolkit().getScreenSize().height - 400);
		/**
		 * definit  differentes zones dans la fenetre ou l'on placera des objets
		 */
		this.getContentPane().setLayout(new GridLayout());
		/**
		 * de meme pour l'objet graphique
		 */
		this.Graphique.setLayout(new BorderLayout());
		/**
		 * ajoute une courbe a l'objet graphique
		 */
		this.Graphique.add(new Graph(view,p));
		/**
		 * ajoute l'objet graphique a la fenetre
		 */
		this.getContentPane().add(this.Graphique, BorderLayout.CENTER);
	}
}
