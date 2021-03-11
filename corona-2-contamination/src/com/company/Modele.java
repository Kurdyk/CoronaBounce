package com.company;

public class Modele {
	private int sain, malade, immun;
	
	Modele(){
		sain=0;
		malade=0;
		immun=0;
	}
	
	public int getSain() {return sain;}
	public int getMalade() {return malade;}
	public int getImmun() {return immun;}
	
	public void setSain(int n) {sain=n;}
	public void setMalade(int n) {malade=n;}
	public void setImmun(int n) {immun=n;}
}
