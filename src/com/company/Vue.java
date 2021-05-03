package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
public class Vue extends JFrame{ //classe qui créer la fenêtre contenant la simulation
	Board board ;


	Vue(boolean[] act, int[] val){ //constructeur qui va créer la fenêtre
		this.setTitle("Simulation"); //fixe le titre de la fenêtre
		this.setSize(700, 800); //fixe les dimensions de la fenêtre
		this.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 400 );

		this.board=new Board(act, val); //créer la board avec les paramètre que l'utilisateurs a donné

		this.getContentPane().add(board); //ajoute la board à la fenêtre

	}
}
