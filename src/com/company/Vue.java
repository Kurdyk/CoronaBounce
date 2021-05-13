package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * classe qui créer la fenêtre contenant la simulation
 */
public class Vue extends JFrame{
	Board board ;

	/**
	 * constructeur qui va créer la fenêtre
	 * @param act tableau de booleen qui donne les options desirees
	 * @param val tableau de valeur associees au options
	 */
	Vue(boolean[] act, int[] val){
		this.setTitle("Simulation"); //fixe le titre de la fenêtre
		this.setSize(700, 800); //fixe les dimensions de la fenêtre
		this.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 400 ); //fixe la position d'apparition par defaut de la fenetre

		this.board=new Board(act, val); //créer la board avec les paramètre que l'utilisateurs a donné

		this.getContentPane().add(board); //ajoute la board à la fenêtre

	}
}
