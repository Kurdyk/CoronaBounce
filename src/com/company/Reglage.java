package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe qui va creer la fenetre menu pour l'utilisateur
 */
public class Reglage extends JFrame{
	/**
	 * Objet qui va contenir les differents objets avec lesquels l'utilisateur va agir
	 */
	JPanel panneau= new JPanel();
	/**
	 * Fenetre qui va contenir la simulation
	 */
	Vue view;
	/**
	 * Objet qui va contenir
	 */
	JPanel Spinner =new JPanel();
	/**
	 * Objet qui contient des JButton lancer simulation et reneatialiser les parametres de la simulation
	 */
	JPanel Button=new JPanel();
	/**
	 * Objet qui contient des JCheckBox pour parametrer la simulation
	 */
	JPanel checkBox = new JPanel();
	/**
	 * Objet cree par classe interne pour etre utilise
	 */
	JSpinnerText population= new JSpinnerText("Population totale : ", 0,40, 1);
	/**
	 * objet cree par classe interne pour etre utilise
	 */
	JSliderText taux= new JSliderText(0,100,25,5,"Taux sain :");
	/**
	 * Tableau qui indiquera quelles options l'utilisateur a voulu dans sa simulation
	 */
	boolean act[];
	/**
	 * Tableau indiquant la valeur des options voulues si besoin est
	 */
	int []val;
	/**
	 * Creer le bouton reinitialiser
	 */
	JButton b1= new JButton("Reinitialiser");
	/**
	 * Creer le bouton GO
	 */
	JButton b2= new JButton("GO");
	/**
	 * Creer le bouton Courbe
	 */
	JButton b3 = new JButton("Courbe");
	/**
	 * Creer la checkboxe Entreprise(s)
	 */
	JCheckBox entreprise = new JCheckBox("Entreprise(s)");
	/**
	 * Creer la checkboxe Mortalite
	 */
	JCheckBox mortalite = new JCheckBox("Mortalite");
	/**
	 * Creer la checkboxe Limiteur temporel
	 */
	JCheckBox tempsTotale = new JCheckBox("Limiteur temporel");
	/**
	 * Creer la checkbox qui determine s'il ont veut plusieurs potentielles vagues
	 */
	JCheckBox reinfection = new JCheckBox("Reinfection");
	/**
	 * Creer  un SpinnerText Nombre d'entrepise(s)
	 */
	JSpinnerText nbEntreprise = new JSpinnerText("Nombre d'entreprise(s)",0,2,1);
	/**
	 * Creer  un SpinnerText Temps en entreprise
	 */
	JSpinnerText tpsEntreprise= new JSpinnerText("Temps en entreprise",500,1500,100);
	/**
	 * Creer  un SpinnerText Taux mortalite
	 */
	JSliderText tauxMortalite= new JSliderText(0,100,25,5,"Taux mortalite :");
	/**
	 * Creer  un SpinnerText Taux immunite
	 */
	JSpinnerText tauxImmunite = new JSpinnerText("Temps d'immunite",500,5000,100);
	/**
	 * Creer  un SpinnerText Temps en guerison
	 */
	JSpinnerText tempsGuerison  = new JSpinnerText("Temps de guerison",500,5000,100);
	/**
	 * Creer  un SpinnerText Vitesse de la simulation
	 */
	JSpinnerText VitesseSimulation  = new JSpinnerText("Vitesse de la simulation",1,3,1);
	/**
	 * Creer  un SpinnerText Temps maximum
	 */
	JSpinnerText tempsMax = new JSpinnerText("Temps maximum",1000,10000,1000);
	/**
	 * Variable qui indique la population totale a T0
	 */
	private int p;
	/**
	 * Variable qui indique la population saine a T0
	 */
	private int x;
	/**
	 * Variable qui indique la population infecte a T0
	 */
	private int y;
	/**
	 * JPanel qui va contenir des boites pour ameliorer l'ergonomie
	 */
	/***
	 * Variable qui sera transfere au graphique (valeur de population de depart)
	 */
	private int valDeb;

	JPanel Gbox= new JPanel();
	/**
	 * Boite pour ergonie numero 1
	 */
	JPanel box1= new JPanel();
	/**
	 * Boite pour ergonie numero 1
	 */
	JPanel box2= new JPanel();
	/**
	 * Boite pour ergonie numero 1
	 */
	JPanel box3= new JPanel();

	/**
	 * Constructeur de la fenetre
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

		this.Gbox.setLayout(new BorderLayout());//fixe un Layout a Gbox
		this.box1.add(entreprise);//ajout des elements a box1
		this.box1.add(nbEntreprise);
		this.box2.add(mortalite);//ajout des elements a box2
		this.box2.add(tauxMortalite);
		this.box3.add(tempsTotale);//ajout des elements a box3
		this.box3.add(tempsMax);

		this.Gbox.add(box1, BorderLayout.NORTH);// ajout de box1 en haut de Gbox
		this.Gbox.add(box2, BorderLayout.CENTER);// ajout de box2 au centre de Gbox
		this.Gbox.add(box3, BorderLayout.SOUTH);// ajout de box2 en bas de Gbox
		this.checkBox.add(Gbox);// ajout des differents elements a checkBox
		this.checkBox.add(tpsEntreprise);
		this.checkBox.add(tauxImmunite);
		this.checkBox.add(tempsGuerison);
		this.checkBox.add(VitesseSimulation);
		this.checkBox.add(reinfection);



		this.panneau.add(Spinner,BorderLayout.NORTH); //ajoute l'objet qui va contenir les objets indiquants la population totale et le taux d'individu sain souhaite par l'utilisateur
		
		this.panneau.add(checkBox,BorderLayout.CENTER);
		this.panneau.add(Button,BorderLayout.SOUTH);  //ajoute l'objet qui va contenir les objets pour lancer et reinitialiser les parametres de la simulation


		this.Spinner.setLayout(new BorderLayout()); //fixe un Layout a Spinner
		this.Spinner.add(population, BorderLayout.NORTH); //ajoute le spinnnerText indiquant le nombre d'individu totale souhaite par l'utilisateur
		this.Spinner.add(taux,BorderLayout.CENTER);//ajoute le sliderText indiquant le taux d'individu sain desire par l'utilisateur au panneau

		b1.addActionListener((event)->{reinitialise();}); //permet de lancer la fonction reinitialise quand le JButton 1 est clique
		b2.addActionListener((event)->{go();}); //permet de lancer la fonction go quand le JButton 2 est clique
		b3.addActionListener((event)->{afficheCourbe();}); //permet de lancer la fonction afficheCourbe quand le JButton 3 est clique

		/*entreprise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JCheckBox tmp= (JCheckBox) actionEvent.getSource();

				if(tmp.isSelected()){this.box1.add(entreprise);}
				else{
					this.box1.remove(entreprise);
				}
			}
		});*/

	}



	/**
	 * Reinitialise tous les parametres de la simulation a des valeurs par defaut
	 */
	public void reinitialise() {
		this.population.restart(); //remet la valeur du spinnerText a une valeur par defaut
		this.taux.restart();  //fixe la valeur du sliderText a 50
		this.nbEntreprise.restart();
		this.tpsEntreprise.restart();
		this.tauxMortalite.restart();
		this.tauxImmunite.restart();
		this.tempsGuerison.restart();
		this.VitesseSimulation.restart();
		this.tempsMax.restart();

		this.entreprise.setSelected(false);//deselctionne toutes les checkboxes
		this.mortalite.setSelected(false);
		this.tempsTotale.setSelected(false);
		this.reinfection.setSelected(false);

	}

	/**
	 * Fonction qui va lancer la simulation
	 */
	public void go() {
		p= population.getValue(); //recuperation et traitement des valeurs de population et du taux de sain
		int s= taux.getValue();
		int i=100-taux.getValue();
		this.x=p*s/100;
		this.y=p*i/100;
		this.valDeb=x+y;

		
		

		act = new boolean[] {true, true, false, true, false, true, true, true, false, false};//initialision des tableaux contenant les options et valeurs souhaite par l'utilisateur
		val = new int[] {x, y, 0, 900, 1000, 500, 3000, 1, 10000, 0};
		if(this.entreprise.isSelected()== true) {
			this.act[2] = true;
			this.val[2] = nbEntreprise.getValue();
		}

		this.val[3] = tpsEntreprise.getValue();

		if(this.mortalite.isSelected()== true) {
			this.act[4] = true;
			this.val[4] = tauxMortalite.getValue();
		}

		this.val[5] = tauxImmunite.getValue();
		this.val[6] = tempsGuerison.getValue();
		this.val[7] =  VitesseSimulation.getValue();

		if(this.tempsTotale.isSelected()==true) {
			this.act[8]=true;
			this.val[8] = tempsMax.getValue();
		}

		this.act[9] = reinfection.isSelected();

		
        //attribution des valeurs de population et taux de sain dans les tablaux
		// les deux premiers elements du tableau d'option sont toujours consideres comme vrai car "option" par defauts de la simulation


		view = new Vue(act,val); //definition de la fenetre contenant la simulation avec options desirees
		view.setVisible(true); //rend la fenetre de la simulation visible

	}

	public void afficheCourbe(){
		Courbe courbe= new Courbe(view,valDeb); //définition de la fenêtre contenant le graphique résumé de la simulation
		courbe.setVisible(true); //rend la fenêtre graphique visible
	}


	/**
	 * Classe interne permettant de creer des objets spinnerText
	 */
	public class JSpinnerText extends JPanel {
	    JSpinner spinner;
	    int reni; //permet de garder la valeur pour reinitialiser la valeur numerique de l'objet

		/**
		 * Constructeur qui va permettre de construire un spinner avec text associe et un maximum definit
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
		 * Permet au spinner d'avoir un zone de texte
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
		 * Associe un spinnermodel et un label
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
		 * Renvoie la valeur courante de l'attribut spinner
		 * @return la valeur
		 */
		int getValue() {
			return (int) this.spinner.getValue();
		}

		/**
		 * Fixe la valeur courante de l'attribut spinner
		 */
		void restart() {
			this.spinner.setValue(reni/2);
		}
	}

	/**
	 * Classe interne qui va permettre la creation de slider avec du texte
	 */
	public class JSliderText extends JPanel{
		JLabel sliderLabel; //texte desire
		JSlider slider; //slider auquel on va associe le texte
		int reni;

		/**
		 * Constructeur de l'objet
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
		 * Fixe la valeur courante du slider de l'objet
		 * @param n valeur qui deviendra courante
		 */
		void setValue(int n) {
			 this.slider.setValue(n);
		}

		/**
		 * Recupere la valeur  courante du slider
 		 * @return valeur numerique courante
		 */
		int getValue() {
			return this.slider.getValue();
		}

		/**
		 * Fixe la valeur du slider a une valeur par defaut
		 */
		void restart() {
			 this.setValue(reni/2);
		}
	}
}
