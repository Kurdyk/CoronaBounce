package com.company;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * classe qui permettre de créer une fenêtre contenant le graphique résumé de la simulation
 */
public class Courbe extends JFrame{
	JPanel Graphique= new JPanel();  //créer l'objet qui va contenir le graphique

	/**
	 * constructeur de l'objet
	 */
	Courbe(){
		this.setTitle("Graphique"); //fixe le titre de la fenêtre
		this.setSize(400, 250); //fixe les dimensions de la fenêtre
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-400, Toolkit.getDefaultToolkit().getScreenSize().height - 400);// fixe la position d'apparition de la fenetre par defaut
		this.getContentPane().setLayout(new GridLayout()); //définit  différentes zones dans la fenêtre où l'on placera des objets
		this.Graphique.setLayout(new BorderLayout()); //de même pour l'objet graphique

		this.Graphique.add(new CurveCanvas(1)); //ajoute une courbe a l'objet graphique
		this.getContentPane().add(this.Graphique, BorderLayout.CENTER); //ajoute l'objet graphique à la fenêtre
	}
}
