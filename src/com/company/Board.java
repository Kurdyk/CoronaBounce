package com.company;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private Placeur placeur;
	private boolean ingame;
	int maxtemps;
	int temps = 0;

	private int B_WIDTH = 800;
	private int B_HEIGHT = 775;
	private int DELAY = 12; //Accelerateur potentiel
	public int killRate = 0; //Determiner la mortalité du virus = killRate * 1 / 10000;
	public int infect;
	public int sain;
	private List<Double> listSain = new ArrayList<Double>();



	public Board(boolean[] tabBoolean, int[] tabVal) {
		initBoard(tabBoolean, tabVal);
	}

	public int getWidth() {
		return B_WIDTH;
	}

	public int getHeight() {
		return B_HEIGHT;
	}

	public Placeur getPlaceur(){
		return this.placeur;
	}

	public List<Double> getListSain() {
		return listSain;
	}

	private void initBoard(boolean[] tabBoolean, int[] tabVal) {


		setFocusable(true);
		setBackground(Color.BLACK);
		ingame = true;

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		placeur = new Placeur(this.getWidth(), this.getHeight());

		int parcours = 0;

		int popSaine = tabVal[parcours];
		int popInf = tabVal[parcours + 1];
		placeur.placementIndividus(popSaine, popInf, false);
		parcours += 2;

		int n = placeur.placeHome(500, this.getWidth(), this.getHeight());
		placeur.choixEmployes(popInf + popSaine);
		placeur.placeInHome(popInf + popSaine, n);

		if(tabBoolean[parcours]) {
			int nbrEntreprises = tabVal[parcours];
			placeur.placeEntreprises(nbrEntreprises);
			int tempsEntreprise = tabVal[parcours + 1];
			for (Entreprise entreprise : placeur.getEntreprise()) {
				entreprise.setTempsIn(tempsEntreprise);
			}
		}
		parcours += 2;

		if(tabBoolean[parcours]) {
			this.killRate = tabVal[parcours];
		}
		parcours++;

		int tempsImmu = tabVal[parcours];
		int tempsConta = tabVal[parcours + 1];
		for(Individu individu : placeur.getIndividus()) {
			individu.setDuree(tempsImmu, tempsConta);
		}
		parcours += 2;


		this.DELAY /= tabVal[parcours];
		parcours++;

		if(tabBoolean[parcours]) {
			this.maxtemps = tabVal[parcours];
		} else {
			this.maxtemps = -1;
		}

		timer = new Timer(DELAY, this);
		timer.start();


		initLimiteEmploye();
		initLimiteHome();
		initSorties();
	}


	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();

	    }

	private void drawObjects(Graphics g) {

		List<Individu> ls = placeur.getIndividus();
		for (Individu individu : ls) {
			if (individu.isVisible()) {
				g.drawImage(individu.getImage(), individu.getX(), individu.getY(), this);
			}
		}
		g.setColor(Color.WHITE);

		List<Lieu> lieux = placeur.getLieu();
		for (Lieu lieu : lieux) {
			g.drawImage(lieu.getImage(), lieu.getX(), lieu.getY(), this);
		}
		g.setColor(Color.WHITE);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		temps++;
		inGame();

		CheckCollisionsFutures();
		updateIndividus();
		refreshAll();

		updateLieux();

		repaint();
	}

	private void updateIndividus() {


		List<Individu> ls = placeur.getIndividus();

		for (int i = 0; i < ls.size(); i++) {

			Individu individu = ls.get(i);

			if (individu.isVisible()) {
				individu.move();
				individu.compteurUpdate();
			} else {
				ls.remove(i);
			}
		}

		dieCheck();
		updateEmployes();

		if(temps%100==0){
			double nb = (double) nbSain();
			listSain.add(nb);
		}

		if (temps % 500 == 0) {
			reShuffle();

		}

	}

	/*private void inGame() {

		if (!ingame || temps == maxtemps) {
			timer.stop();
		}
	}*/

	public boolean inGame() {

		if (!ingame || temps == maxtemps) {
			timer.stop();
			return false;
		}
		return true;
	}

	private void CheckCollisionsFutures() {

		List<Individu> ls = placeur.getIndividus();
		List<Home> homes = placeur.getHomes();
		List<Entreprise> entreprises = placeur.getEntreprise();

		for (Individu individu1 : ls) {

			Rectangle r1 = individu1.getBounds();
			r1.x += individu1.X_SPEED;
			r1.y += individu1.Y_SPEED;

			for (Individu individu2 : ls) {
				Rectangle r2 = individu2.getBounds();

				if (individu1 != individu2 && r1.intersects(r2)) {
					individu1.rebound();
					CheckContamination(individu1, individu2);
					break;
				}
			}


			for (Home home : homes) {
				Rectangle r2 = home.getBounds();
				if (individu1.insideLieu != 2 && r1.intersects(r2)) {
					home.accueil(individu1);
					break;
				}
			}

			for (Entreprise entreprise : entreprises) {
				Rectangle r2 = entreprise.getBounds();
				if (individu1.insideLieu != 1 && r1.intersects(r2)) {
					entreprise.accueil(individu1);
					break;
				}
			}


		}
	}


    protected void CheckContamination (Individu m, Individu m1) {
        if (m.etat.equals("Infected") && m1.etat.equals("Neutral")) {
            m1.contamine();
			sain--;
			infect++;
        }
        if (m1.etat.equals("Infected") && m.etat.equals("Neutral")) {
            m.contamine();
			sain--;
			infect++;
        }
    }


    protected void dieCheck() {
        for (Individu indiv : placeur.getIndividus()) {
            if (indiv.etat == "Infected") {
                Random rand = new Random();
                int tirage = rand.nextInt(10000);
                if (tirage < killRate) {
                    indiv.visible = false;
                }
            }
        }
    }

    private void refreshAll(){
    	List<Individu> ls = placeur.getIndividus();
    	for (int i = 0; i < ls.size(); i++) {
    		Individu individu = ls.get(i);
    		individu.refresh();
    	}
    }

    public void initLimiteEmploye() {
		List<Employe> ls = placeur.getEmployes();
		List<Entreprise> lsEntreprise = placeur.getEntreprise();

		if (ls != null && lsEntreprise != null) {

			for (Employe employe : ls) {
				for (Entreprise entreprise : lsEntreprise) {

					if (employe.entrepriseName.equals(entreprise.nom)) {
						employe.initEntrepriseDim(entreprise.x, entreprise.y, entreprise.height, entreprise.width);
					}

				}
			}
		}
	}

	public void initLimiteHome() {
		List<Individu> individus = placeur.getIndividus();
		List<Home> lsHome = placeur.getHomes();
		if (individus != null && lsHome != null) {
			for (Individu individu : individus) {
				for (Home home : lsHome) {
					if(individu.houseNumber == home.numero) {
						individu.setHomeLimits(home);
					}
				}
			}
		}
	}

	private void initSorties() {
		List<Lieu> lieux = placeur.getLieu();
		for (Lieu lieu : lieux) {
			lieu.setSortie(B_WIDTH, B_HEIGHT);
		}
	}

	private void updateLieux() {
		List<Lieu> lieux = placeur.getLieu();
		List<Individu> individus = placeur.getIndividus();
		for (Lieu lieu : lieux) {
			lieu.checkout();
			lieu.updateAttente(individus);
		}
	}

	private void reShuffle() {
		List<Individu> individus = placeur.getIndividus();
		for (Individu individu : individus) {
			if(individu.X_SPEED != 0) {
				individu.goAlea();
			}
		}
	}

	private void updateEmployes() {
		List<Employe> employes = placeur.getEmployes();
		for (Employe employe : employes) {
			employe.updateTempsOut();
		}
	}

	private int nbSain(){
		int nb=0;
		for (Individu indiv : placeur.getIndividus()) {
			if (indiv.etat == "Neutral") {
				nb=nb+1;
			}
		}
		return nb;
	}
}