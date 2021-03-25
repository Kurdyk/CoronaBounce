package com.company;

public class Test {

	
		private Vue view;
		private Modele model;
		private Controleur control;
		
		public Test() {
			view= new Vue();
			view.setVisible(true);
			
		}
		
		public static void main (String[]args) {
			new Test();

		}
	

}
