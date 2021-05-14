package com.company;

/**
 * classe qui va permettre l'execution du programme
 */
public class Main {
	/**
	 * fenetre qui avec l'utilisateur va agir
	 */
	private Reglage view;

	/**
	 * constructeur de l'objet
	 */
	public Main() {
		view= new Reglage(); //créer une nouvelle fenêtre réglage
		view.setVisible(true); //rend la fenêtre visible
	}

	/**
	 * la fonction main du programme
	 * @param args parametre par defaut de la fonction
	 */
	public static void main (String[]args) {
		/**
		 * creer un nouveau test
		 */
		new Main();
	}
}
