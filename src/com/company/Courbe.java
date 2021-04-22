package com.company;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Courbe extends JFrame{ //classe qui permettre de créer une fenêtre contenant le graphique résumé de la simulation
	JPanel Graphique= new JPanel();  //créer l'objet qui va contenir le graphique
	
	Courbe(){
		this.setTitle("Graphique"); //fixe le titre de la fenêtre
		this.setSize(400, 250); //fixe les dimensions de la fenêtre

		this.getContentPane().setLayout(new GridLayout()); //définit  différentes zones dans la fenêtre où l'on placera des objets
		this.Graphique.setLayout(new BorderLayout()); //de même pour l'objet graphique

		this.Graphique.add(new CurveCanvas(1)); //ajoute une courbe a l'objet graphique
		this.getContentPane().add(this.Graphique, BorderLayout.CENTER); //ajoute l'objet graphique à la fenêtre
	}
}
