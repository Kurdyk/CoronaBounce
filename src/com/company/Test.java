package com.company;

public class Test { //classe qui va permettre l'éxécution du programme
	private Reglage view; // fenêtre qui avec l'utilisateur va agir

	public Test() {
		view= new Reglage(); //créer une nouvelle fenêtre réglage
		view.setVisible(true); //rend la fenêtre visible
	}
		
	public static void main (String[]args) {
		new Test(); //créer un nouveau test
	}

}
