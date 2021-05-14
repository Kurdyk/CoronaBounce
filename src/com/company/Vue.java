package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * classe qui creer la fenetre contenant la simulation
 */
public class Vue extends JFrame{
	/**
	 * board contenue dans la fenetre
	 */
	Board board ;
	/**
	 * constructeur qui va creer la fenetre
	 * @param act tableau de booleen qui donne les options desirees
	 * @param val tableau de valeur associees au options
	 */
	Vue(boolean[] act, int[] val){
		/**
		 * fixe le titre de la fenetre
		 */
		this.setTitle("Simulation");
		/**
		 * fixe les dimensions de la fenetre
		 */
		this.setSize(700, 800);
		/**
		 * fixe la position d'apparition par defaut de la fenetre
		 */
		this.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 400 );
		/**
		 * creer la board avec les parametre que l'utilisateurs a donne
		 */
		this.board = new Board(act, val);
		/**
		 * ajoute la board a la fenetre
		 */
		this.getContentPane().add(board);

	}
}
