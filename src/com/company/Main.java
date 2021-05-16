package com.company;

/**
 * Classe qui va permettre l'execution du programme
 */
public class Main {
	/**
	 * Fenetre qui avec l'utilisateur va agir
	 */
	private Reglage view;

	/**
	 * Constructeur de l'objet
	 */
	public Main() {
		view= new Reglage(); //créer une nouvelle fenêtre réglage
		view.setVisible(true); //rend la fenêtre visible
	}

	/**
	 * La fonction main du programme
	 * @param args parametre par defaut de la fonction
	 */
	public static void main (String[]args) {
		/**
		 * Creer un nouveau Main
		 */
		new Main();
	}
}
