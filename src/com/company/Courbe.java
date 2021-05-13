package com.company;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Courbe extends JFrame{ //classe qui permettre de créer une fenêtre contenant le graphique résumé de la simulation
	JPanel Graphique= new JPanel();  //créer l'objet qui va contenir le graphique

	Courbe(Vue view,int x, int y){
		this.setTitle("Graphique"); //fixe le titre de la fenêtre
		this.setSize(800, 400); //fixe les dimensions de la fenêtre
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-400, Toolkit.getDefaultToolkit().getScreenSize().height - 400);
		this.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 400);
		this.getContentPane().setLayout(new GridLayout()); //définit  différentes zones dans la fenêtre où l'on placera des objets
		this.Graphique.setLayout(new BorderLayout()); //de même pour l'objet graphique

		this.Graphique.add(new Graph(view,x,y)); //ajoute une courbe a l'objet graphique
		this.getContentPane().add(this.Graphique, BorderLayout.CENTER); //ajoute l'objet graphique à la fenêtre
	}
}