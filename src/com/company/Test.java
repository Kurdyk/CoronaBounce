package com.company;

/**
 * classe qui va permettre l'éxécution du programme
 */
public class Test {
	private Reglage view; // fenêtre qui avec l'utilisateur va agir

	/**
	 * constructeur de l'objet
	 */
	public Test() {
		view= new Reglage(); //créer une nouvelle fenêtre réglage
		view.setVisible(true); //rend la fenêtre visible
	}

	/**
	 * la fonction main du programme
	 * @param args parametre par defaut de la fonction
	 */
	public static void main (String[]args) {
		new Test(); //créer un nouveau test
	}
}
