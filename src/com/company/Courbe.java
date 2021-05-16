package com.company;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe qui permettre de creer une fenetre contenant le graphique résume de la simulation
 */
public class Courbe extends JFrame{
	/**
	 * Creer l'objet qui va contenir le graphique
	 */
	JPanel Graphique= new JPanel();

	Courbe(Vue view,int valDeb){
		/**
		 * Fixe le titre de la fenetre
		 */
		this.setTitle("Graphique");
		/**
		 * Fixe les dimensions de la fenetre
		 */
		this.setSize(800, 400);
		/**
		 * Fixe la position par defaut de la fenetre
		 */
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-400, Toolkit.getDefaultToolkit().getScreenSize().height - 400);
		/**
		 * Definit  differentes zones dans la fenetre ou l'on placera des objets
		 */
		this.getContentPane().setLayout(new GridLayout());
		/**
		 * De meme pour l'objet graphique
		 */
		this.Graphique.setLayout(new BorderLayout());
		/**
		 * Ajoute une courbe a l'objet graphique
		 */
		this.Graphique.add(new Graph(view, valDeb));
		/**
		 * Ajoute l'objet graphique a la fenetre
		 */
		this.getContentPane().add(this.Graphique, BorderLayout.CENTER);
	}
}
