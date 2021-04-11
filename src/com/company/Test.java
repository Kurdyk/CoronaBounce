package com.company;

public class Test {

	
		private Reglage view;
		
		
		public Test() {
			view= new Reglage();
			view.setVisible(true);
			
		}
		public Test(int i, int j) {
			view= new Reglage();
			view.setVisible(true);
			
		}
		
		public static void main (String[]args) {
			new Test();
			//new Test(10, 10);

		}
	

}
