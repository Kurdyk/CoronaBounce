package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
public class Vue extends JFrame{ //classe qui créer la fenêtre contenant la simulation
	Board board ;

	
	Vue(boolean[] act, int[] val){ //constructeur qui va créer la fenêtre
		this.setTitle("Simulation"); //fixe le titre de la fenêtre
		this.setSize(600, 520); //fixe les dimensions de la fenêtre

		this.board=new Board(val[0],val[1]); //créer la board avec les paramètre que l'utilisateurs a donné

		this.getContentPane().add(board); //ajoute la board à la fenêtre

	}
}
