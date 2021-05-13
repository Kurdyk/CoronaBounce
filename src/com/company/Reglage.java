package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * class qui va creer la fenetre menu pour l'utilisateur
 */
public class Reglage extends JFrame{

	JPanel panneau= new JPanel(); //objet qui va contenir les differents objets avec lesquels l'utilisateur va agir

	Vue view;  //fenetre qui va contenir la simulation
	Courbe courbe; //fenetre qui va contenir le graphique resume de la simulation

	JPanel Spinner =new JPanel(); //objet qui va contenir
	JPanel Button=new JPanel(); //objet qui contient des JButton lancer simulation et reneatialiser les parametres de la simulation
	JPanel checkBox = new JPanel();

	JSpinnerText population= new JSpinnerText("Population totale : ", 0,40, 1); //objet cree par classe interne pour etre utilise
	JSliderText taux= new JSliderText(0,100,25,5,"Taux sain :"); //objet cree par classe interne pour etre utilise

	boolean act[]; //tableau qui indiquera quelles options l'utilisateur a voulu dans sa simulation
	int []val; //tableau indiquant la valeur des options voulues si besoin est

	JButton b1= new JButton("Reinitialiser");
	JButton b2= new JButton("GO");
	JButton b3 = new JButton(("Courbe"));
	
	JCheckBox entreprise = new JCheckBox("entreprise");
	JCheckBox tempsentreprise = new JCheckBox("Temps de travail");
	JCheckBox tauxmortalite = new JCheckBox("mortalite");
	JCheckBox tauxImun = new JCheckBox("Immunite");
	JCheckBox tauxgueri = new JCheckBox("gueri");
	JCheckBox vitesseSimu = new JCheckBox("Vitesse");
	JCheckBox Tempstotale = new JCheckBox("Totale");

	JSpinnerText  nbentreprise = new JSpinnerText("Nombre D'Entreprise",0,2,1);
	JSpinnerText tpsentreprise= new JSpinnerText("Temps en entreprise",500,1500,100);
	JSpinnerText  Immun  = new JSpinnerText("Taux Immunite",500,5000,100);
	JSpinnerText TempsGuerison  = new JSpinnerText("Temps de guerison",500,5000,100);
	JSpinnerText Vitessesimulation  = new JSpinnerText("Vitesse de la simulation",1,3,1);
	JSpinnerText TempsTotale = new JSpinnerText("Temps totale",1000,10000,1000);
	JSliderText morta= new JSliderText(0,100,25,5,"Taux morta :");

	private int x;
	private int y;


	/**
	 * constructeur de la fenetre
	 */
	Reglage(){

		this.setTitle("Corona"); //definit nom de la fenetre
		this.setSize(700, 500); //definition taille fenetre
		this.setLocationRelativeTo(null); //positionne la fenetre au centre de l'ecran en position par defaut
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //arrete le programme quand la fenetre est ferme

		this.getContentPane().setLayout(new GridLayout()); //definition de differentes zones de la fenetre  on pourra ajouter des objets
		this.getContentPane().add(panneau, BorderLayout.CENTER);//ajoute l'objet panneau au centre de la fenetre


		this.panneau.setLayout(new BorderLayout()); //definit differente zones dans l'objet panneau ou on pourra integrer d'autres objets


		this.Button.add(b1);//ajout de JButton a l'objet Button
		this.Button.add(b2);
		this.Button.add(b3);
		
		this.checkBox.add(entreprise);
		this.checkBox.add(tempsentreprise);
		this.checkBox.add(tauxmortalite);
		this.checkBox.add(tauxImun);
		this.checkBox.add(tauxgueri);
		this.checkBox.add(vitesseSimu);
		this.checkBox.add(Tempstotale);
		
		this.checkBox.add(nbentreprise);
		this.checkBox.add(tpsentreprise);
		this.checkBox.add(morta);
		this.checkBox.add(Immun);
		this.checkBox.add(TempsGuerison);
		this.checkBox.add(Vitessesimulation);
		this.checkBox.add(TempsTotale);

		tauxmortalite.addActionListener((event)->{slidermorta();});

		this.panneau.add(Spinner,BorderLayout.NORTH); //ajoute l'objet qui va contenir les objets indiquants la population totale et le taux d'individu sain souhaite par l'utilisateur
		
		this.panneau.add(checkBox,BorderLayout.CENTER);
		this.panneau.add(Button,BorderLayout.SOUTH);  //ajoute l'objet qui va contenir les objets pour lancer et reinitialiser les parametres de la simulation


		this.Spinner.setLayout(new BorderLayout());
		this.Spinner.add(population, BorderLayout.NORTH); //ajoute le spinnnerText indiquant le nombre d'individu totale souhaite par l'utilisateur
		this.Spinner.add(taux,BorderLayout.CENTER);//ajoute le sliderText indiquant le taux d'individu sain desire par l'utilisateur au panneau

		b1.addActionListener((event)->{reinitialise();}); //permet de lancer la fonction reinitialise quand le JButton 1 est cliqué
		b2.addActionListener((event)->{go();}); //permet de lancer la fonction go quand le JButton 2 est cliqué
		b3.addActionListener((event)->{afficheCourbe();});

	}

	/**
	 *
	 */
	public void slidermorta() {
		this.Spinner.add(morta);
	}

	/**
	 * reinitialise tous les parametres de la simulation a des valeurs par defaut
	 */
	public void reinitialise() {
		this.population.restart(); //remet la valeur du spinnerText a une valeur par defaut
		this.taux.restart();  //fixe la valeur du sliderText a 50
		this.morta.restart();
		this.nbentreprise.restart();
		this.Immun.restart();
		this.TempsGuerison.restart();
		this.TempsTotale.restart();
		this.Vitessesimulation.restart();
		this.tpsentreprise.restart();

		this.tauxmortalite.setSelected(false);
		this.entreprise.setSelected(false);
		this.tempsentreprise.setSelected(false);
		this.tauxmortalite.setSelected(false);
		this.tauxImun.setSelected(false);
		this.tauxgueri.setSelected(false);
		this.vitesseSimu.setSelected(false);
		this.Tempstotale.setSelected(false);

	}

	/**
	 * fonction qui va lancer la simulation
	 */
	public void go() {
		int p= population.getValue(); //recuperation et traitement des valeurs de population et du taux de sain
		int s= taux.getValue();
		int i=100-taux.getValue();
		this.x=p*s/100;
		this.y=p*i/100;
		
		

		act = new boolean[] {true, true, false, false, false, true, true, true, false};//initialision des tableaux contenant les options et valeurs souhaite par l'utilisateur
		val = new int[] {x, y, 0, 900, 1000, 500, 3000, 1, 10000};
		if(this.entreprise.isSelected()== true) {
			this.act[2] = true;
			this.val[2] = nbentreprise.getValue();
		}
		if(this.tempsentreprise.isSelected()==true) {
			this.act[3] = true;
			this.val[3] = tpsentreprise.getValue();
		}

		if(this.tauxmortalite.isSelected()== true) {
			this.act[4] = true;
			this.val[4] = morta.getValue();
		}
	
		if(this.tauxImun.isSelected()==true) {
			this.act[5] = true;
			this.val[5] = Immun.getValue();
		}
		if(this.tauxgueri.isSelected()==true) {
			this.act[6]= true;
			this.val[6] = TempsGuerison.getValue();
		}
		if(this.vitesseSimu.isSelected()==true) {
			this.act[7]= true;
			this.val[7] =  Vitessesimulation.getValue();
		}
		if(this.Tempstotale.isSelected()==true) {
			this.act[8]=true;
			this.val[8] = TempsTotale.getValue();
		}
		
        //attribution des valeurs de population et taux de sain dans les tablaux
		// les deux premiers elements du tableau d'option sont toujours consideres comme vrai car "option" par defauts de la simulation


		view = new Vue(act,val); //definition de la fenetre contenant la simulation avec options desirees
		view.setVisible(true); //rend la fenetre de la simulation visible

	}

	public void afficheCourbe(){
		Courbe courbe= new Courbe(view, x,y); //définition de la fenêtre contenant le graphique résumé de la simulation
		courbe.setVisible(true); //rend la fenêtre graphique visible
	}


	/**
	 * classe interne permettant de creer des objets spinnerText
	 */
	public class JSpinnerText extends JPanel {
	    JSpinner spinner;
	    int reni; //permet de garder la valeur pour reinitialiser la valeur numerique de l'objet

		/**
		 * constructeur qui va permettre de construire un spinner avec text associe et un maximum definit
		 * @param s texte associé a l'objet
		 * @param i valeur minimum du spinner
		 * @param j valeur maximum du spinner
		 * @param k pas du spinner
		 */
		public JSpinnerText(String s, int i, int j, int k) {
	        super();
	        this.reni=j;
	        JFormattedTextField ftf = null; //va permettre de ajouter du texte au spinner associe

	        SpinnerModel chiffre = new SpinnerNumberModel(j/2, i, j, k); //creation du spinner avec les valeurs numerique en fonction du maximum donner
	         spinner = addLabeledSpinner(this, s, chiffre);
	        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));


	        ftf = getTextField(spinner); //definition de la zone de text
	        if (ftf != null ) {
	            ftf.setColumns(8);
	            ftf.setHorizontalAlignment(JTextField.RIGHT);
	        	}
	        }

		/**
		 * permet au spinner d'avoir un zone de texte
		 * @param spinner spinner auquel on veut ajoute une zone de texte
		 * @return spinner avec une zone de texte ou null si echoue
		 */
	    public JFormattedTextField getTextField(JSpinner spinner) {
	        JComponent editor = spinner.getEditor();
	        if (editor instanceof JSpinner.DefaultEditor) {
	            return ((JSpinner.DefaultEditor)editor).getTextField();
	        } else {
	            System.err.println("Unexpected editor type: "
	                               + spinner.getEditor().getClass()
	                               + " isn't a descendant of DefaultEditor");
	            return null;
	        }
	    }

		/**
		 * associe un spinnermodel et un label
		 * @param c ensemble qui va contenir un label et un spinner model
		 * @param label
		 * @param model
		 * @return retourne un spinner avec un label
		 */
	     protected JSpinner addLabeledSpinner(Container c, String label, SpinnerModel model) {
	        JLabel l = new JLabel(label);
	        c.add(l);

	        JSpinner spinner = new JSpinner(model);
	        l.setLabelFor(spinner);
	        c.add(spinner);

	        return spinner;
	    }

		/**
		 * renvoie la valeur courante de l'attribut spinner
		 * @return la valeur
		 */
		int getValue() {
			return (int) this.spinner.getValue();
		}

		/**
		 * fixe la valeur courante de l'attribut spinner
		 */
		void restart() {
			this.spinner.setValue(reni/2);
		}
	}

	/**
	 * classe interne qui va permettre la creation de slider avec du texte
	 */
	public class JSliderText extends JPanel{
		JLabel sliderLabel; //texte desire
		JSlider slider; //slider auquel on va associe le texte
		int reni;

		/**
		 * constructeur de l'objet
		 * @param min valeur minimale du slider
		 * @param max valeur maximale du slider
		 * @param M	valeur de l'espacement du tick majeur
		 * @param m valeur de l'espancement du tick mineur
		 * @param s texte associe au slider
		 */
		JSliderText(int min, int max,int M, int m,String s){
			this.sliderLabel= new JLabel(s, JLabel.CENTER); //creation du label avec texte voulu
			slider= new JSlider(); //creation du slider
			this.reni=max;
			this.slider.setMinimum(min);//parametrage du slider
			this.slider.setMaximum(max);
			this.slider.setValue(max/2);
			this.slider.setMajorTickSpacing(M);
			this.slider.setMinorTickSpacing(m);
			this.slider.setPaintTicks(true);
			this.slider.setPaintLabels(true);
			this.sliderLabel.setAlignmentY(Component.TOP_ALIGNMENT);
			this.add(sliderLabel); //ajout du label a l'objet
			this.add(slider); //ajout du slider a l'objet
		}

		/**
		 * //fixe la valeur courante du slider de l'objet
		 * @param n valeur qui deviendra courante
		 */
		void setValue(int n) {
			 this.slider.setValue(n);
		}

		/**
		 * recupere la valeur  courante du slider
 		 * @return valeur numerique courante
		 */
		int getValue() {
			return this.slider.getValue();
		}

		/**
		 * fixe la valeur du slider a une valeur par defaut
		 */
		void restart() {
			 this.setValue(reni/2);
		}
	}
}
