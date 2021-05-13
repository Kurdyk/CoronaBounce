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
	private int B_HEIGHT = 760;
	private int DELAY = 12;
	public int killRate = 0; //Determiner la mortalité du virus = killRate * 1 / 10000;
	public int infect;
	public int sain;
	private List<Double> listSain = new ArrayList<Double>();


	public List<Home> homes;
	public List<Entreprise> entreprises;


	/**
	 * Constructeur de la Board
	 * @param tabBoolean Tableau de valeurs booleennes pour l'initialisation des fonctions de la Board
	 * @param tabVal Tableau de valeurs numeriques pour l'initialisation des parametres de la Board
	 */
	public Board(boolean[] tabBoolean, int[] tabVal) {
		initBoard(tabBoolean, tabVal);
	}

	/**
	 * @return La largueur de la Board
	 */
	public int getWidth() {
		return B_WIDTH;
	}

	/**
	 * @return La hauteur de la Board
	 */
	public int getHeight() {
		return B_HEIGHT;
	}

	/**
	 * @return Le placeur lie a la Board
	 */
	public Placeur getPlaceur(){
		return this.placeur;
	}

	/**
	 * La listSain est actuallisee regulierement avec le nombre d'individus sains pour faire la courbe
	 * @return Cette listSain
	 */
	public List<Double> getListSain() {
		return listSain;
	}

	/**
	 * Initialise les fonctions et valeurs de la Board
	 * @param tabBoolean Tableau de valeurs booleennes pour l'initialisation des fonctions de la Board
	 * @param tabVal Tableau de valeurs numeriques pour l'initialisation des parametres de la Board
	 */
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

		homes = placeur.getHomes();
		entreprises = placeur.getEntreprise();
	}

	/**
	 * Dessine l'aspect general de la board
	 * @param g Le g graphique de base de Java
	 */
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		drawObjects(g);
		Toolkit.getDefaultToolkit().sync();

	    }

	/**
	 * Dessine chaque objet dans la Board
 	 * @param g Le g graphique de base de Java
	 */
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


	/**
	 * Actualise l'etat de la Board
	 * @param e Le e ActionEvent de Java
	 */
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

	/**
	 * Deplace les Individus, verifie les morts a effectuer, actualise le nombre d'individus sain, fait avancer les compteurs lies aux Individus
	 */
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

	/**
	 * Verifie que le temps max n'est pas ecoule, sinon arrete le timer
	 * @return true si la Board peur continuer, false sinon
	 */
	public boolean inGame() {

		if (!ingame || temps == maxtemps) {
			timer.stop();
			return false;
		}
		return true;
	}

	/**
	 * Verifie les collisions à venir entre Individus et avec les lieux, declanche les accueils
	 */
	private void CheckCollisionsFutures() {

		List<Individu> ls = placeur.getIndividus();

		for (Individu individu1 : ls) {

			Rectangle r1 = individu1.getBounds();
			r1.x += individu1.X_SPEED;
			r1.y += individu1.Y_SPEED;


			for (Home home : homes) {
				Rectangle r2 = home.getBounds();
				if (individu1.insideLieu == 2) {
					break;
				}
				if (r1.intersects(r2)) {
					home.accueil(individu1);
					break;
				}
			}

			for (Entreprise entreprise : entreprises) {
				Rectangle r2 = entreprise.getBounds();
				if (individu1.insideLieu == 1) {
					break;
				}
				if (r1.intersects(r2)) {
					entreprise.accueil(individu1);
					break;
				}
			}

			for (Individu individu2 : ls) {
				if (!individu1.equals(individu2)) {
					Rectangle r2 = individu2.getBounds();

					if (r1.intersects(r2)) {
						individu1.rebound();
						CheckContamination(individu1, individu2);
						break;
					}
				}
			}
		}
	}

	/**
	 * En cas de collisions entre deux Individus verifie si une contamination a lieu
	 * @param m Le premier Individu de la collision
	 * @param m1 Le second Individu de la collision
	 */
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

	/**
	 * Verifie si un Individu doit mourir, si oui, le tue
	 */
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

	/**
	 * Remet a 0 les compteurs de collision
	 */
	private void refreshAll(){
    	List<Individu> ls = placeur.getIndividus();
    	for (int i = 0; i < ls.size(); i++) {
    		Individu individu = ls.get(i);
    		individu.refresh();
    	}
    }

	/**
	 * Initialise les limites des Entreprises pour les Employes correspodants
	 */
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

	/**
	 * Initialise les limites des Homes pour les individus correpondants
	 */
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

	/**
	 * Initialise les sorties pour les Lieux
	 */
	private void initSorties() {
		List<Lieu> lieux = placeur.getLieu();
		for (Lieu lieu : lieux) {
			lieu.setSortie(B_WIDTH, B_HEIGHT);
		}
	}

	/**
	 * Pour chaque Lieu, update les listes d'attente et les compteurs de sortie pour les Individus dedans
	 */
	private void updateLieux() {
		List<Lieu> lieux = placeur.getLieu();
		List<Individu> individus = placeur.getIndividus();
		for (Lieu lieu : lieux) {
			lieu.checkout();
			lieu.updateAttente(individus);
		}
	}

	/**
	 * Donne une nouvelle vitesse aleatoire a chaque Individu
	 */
	private void reShuffle() {
		List<Individu> individus = placeur.getIndividus();
		for (Individu individu : individus) {
			if(individu.X_SPEED != 0) {
				individu.goAlea();
			}
		}
	}

	/**
	 * Incremente les compteurs pour les Employes hors d'un Lieu, pour leurs permettre d'y retourner si necessaire
	 */
	private void updateEmployes() {
		List<Employe> employes = placeur.getEmployes();
		for (Employe employe : employes) {
			employe.updateTempsOut();
		}
	}

	/**
	 * Compte le nombre d'Individus sains
	 * @return Le nombre d'Individus sains
	 */
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