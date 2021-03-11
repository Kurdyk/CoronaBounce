package com.company;

public class Test {

	
		private Vue view;
		private Modele model;
		private Controleur control;
		
		public Test() {
			view= new Vue();
			view.setVisible(true);
			model= new Modele();
			control= new Controleur(model, view);
		}
		
		public static void main (String[]args) {
			new Test();

		}
	

}
