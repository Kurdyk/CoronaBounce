package com.company;

public class Controleur {
	Modele modele;
	Vue vue;
	
	Controleur(Modele m, Vue v){
		this.modele=m;
		this.vue=v;
	}
	
	void spinnerMoved() {
		int people= (int) vue.individu.getValue();
		int sick= (int) vue.infecte.getValue();
		modele.setSain(people);
		modele.setMalade(sick);
	}
}
