package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class Reglage extends JFrame{ //class qui va creer la feneªtre menu pour l'utilisateur

	JPanel panneau= new JPanel(); //objet qui va contenir les differents objets avec lesquels l'utilisateur va agir

	Vue view;  //feneªtre qui va contenir la simulation
	Courbe courbe; //feneªtre qui va contenir le graphique resume de la simulation

	JPanel Spinner =new JPanel(); //objet qui va contenir
	JPanel Button=new JPanel(); //objet qui contient des JButton lae§ant simulation et reneatialiser les parametres de la simulation
	JPanel checkBox = new JPanel();

	JSpinnerText population= new JSpinnerText("Population totale : ", 0,40, 1); //objet cree par classe interne pour eªtre utilise
	JSliderText taux= new JSliderText(0,100,25,5,"Taux sain :"); //objet cree par classe interne pour eªtre utilise
	

	boolean act[]; //tableau qui indiquera quelles options l'utilisateur a voulu dans sa simulation
	int []val; //tableau indiquant la valeur des options voulues si besoin est

	JButton b1= new JButton("REinitialiser");
	JButton b2= new JButton("GO");
	
	JCheckBox entreprise = new JCheckBox("entreprise");
	JCheckBox tempsentreprise = new JCheckBox("Temps de travail");
	JCheckBox tauxmortalité = new JCheckBox("mortalité");
	JCheckBox tauxImun = new JCheckBox("Immunité");
	JCheckBox tauxguéri = new JCheckBox("guéri");
	JCheckBox vitesseSimu = new JCheckBox("Vitesse");
	JCheckBox Tempstotale = new JCheckBox("Totale");
	
	
	JSpinnerText  nbentreprise = new JSpinnerText("Nombre D'Entreprise",0,2,1);
	JSpinnerText tpsentreprise= new JSpinnerText("Temps en entreprise",500,1500,100);
	JSpinnerText  Immun  = new JSpinnerText("Taux Immunité",500,5000,100);
	JSpinnerText TempsGuérison  = new JSpinnerText("Temps de guérison",500,5000,100);
	JSpinnerText Vitessesimulation  = new JSpinnerText("Vitesse de la simulation",1,3,1);
	JSpinnerText TempsTotale = new JSpinnerText("Temps totale",1000,10000,1000);

	
	JSliderText morta= new JSliderText(0,100,25,5,"Taux morta :");
	
	

	Reglage(){ //constructeur de la feneªtre

		this.setTitle("Corona"); //definit nom de la feneªtre
		this.setSize(700, 500); //definition taille feneªtre

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //arreªte le programme quand la feneªtre est ferme

		this.getContentPane().setLayout(new GridLayout()); //definition de differentes zones de la feneªtre oe¹ on pourra ajouter des objets
		this.getContentPane().add(panneau, BorderLayout.CENTER);//ajoute l'objet panneau au centre de la feneªtre


		this.panneau.setLayout(new BorderLayout()); //definit differente zones dans l'objet panneau oe¹ on pourra integrer d'autres objets


		this.Button.add(b1);//ajout de JButton e  l'objet Button
		this.Button.add(b2);
		
		//this.checkBox.setLayout(new BorderLayout());
		//this.checkBox.setAlignmentX(CENTER_ALIGNMENT);
		
		this.checkBox.add(entreprise); // sa marche
		this.checkBox.add(tempsentreprise);//sa marche
		this.checkBox.add(tauxmortalité); // sa marche
		this.checkBox.add(tauxImun);// .???
		this.checkBox.add(tauxguéri);//sa marche
		this.checkBox.add(vitesseSimu);//sa marche
		this.checkBox.add(Tempstotale);// ??
		
		
		
		
		this.checkBox.add(nbentreprise);
		this.checkBox.add(tpsentreprise);
		this.checkBox.add(morta);
		this.checkBox.add(Immun);
		this.checkBox.add(TempsGuérison);
		this.checkBox.add(Vitessesimulation);
		this.checkBox.add(TempsTotale);
		
		
		
		
		tauxmortalité.addActionListener((event)->{slidermorta();});
		

		this.panneau.add(Spinner,BorderLayout.NORTH); //ajoute l'objet qui va contenir les objets indiquants la population totale et le taux d'individu sain souhaite par l'utilisateur
		
		this.panneau.add(checkBox,BorderLayout.CENTER);
		this.panneau.add(Button,BorderLayout.SOUTH);  //ajoute l'objet qui va contenir les objets pour lancer et reinitialiser les parametres de la simulation


		this.Spinner.setLayout(new BorderLayout());
		this.Spinner.add(population, BorderLayout.NORTH); //ajoute le spinnnerText indiquant le nombre d'individu totale souhaite par l'utilisateur
		this.Spinner.add(taux,BorderLayout.CENTER);//ajoute le sliderText indiquant le taux d'individu sain desire par l'utilisateur au panneau


		b1.addActionListener((event)->{reinitialise();}); //permet de lancer la fonction reinitialise quand le JButton 1 est clique
		b2.addActionListener((event)->{go();}); //permet de lancer la fonction go quand le JButton 2 est clique
		
		
		
	}


	public void slidermorta() {
		this.Spinner.add(morta);
	}

	public void reinitialise() { //reinitialise tous les parametres de la simulation e  des valeurs par defaut
		this.population.restart(); //remet la valeur du spinnerText a une valeur par defaut
		this.taux.restart();  //fixe la valeur du sliderText e  50
		this.morta.restart();
		this.nbentreprise.restart();
		this.tauxmortalité.setSelected(false);
		this.entreprise.setSelected(false);
	}


	public void go() { //fonction qui va lancer la simulation
		int p= population.getValue(); //recuperation et traitement des valeurs de population et du taux de sain
		int s= taux.getValue();
		int i=100-taux.getValue();
		int x=p*s/100;
		int y=p*i/100;
		
		

		
		//

		act = new boolean[9]; //initialision des tableaux contenant les options et valeurs souhaite par l'utilisateur
		

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

		if(this.tauxmortalité.isSelected()== true) {
			this.act[4] = true;
			this.val[4] = morta.getValue();
		}
	
		if(this.tauxImun.isSelected()==true) {
			this.act[5] = true;
			this.val[5] = Immun.getValue();
		}
		if(this.tauxguéri.isSelected()==true) {
			this.act[6]= true;
			this.val[6] = TempsGuérison.getValue();
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


		Vue view= new Vue(act,val); //definition de la feneªtre contenant la simulation avec options desirees
		view.setVisible(true); //rend la feneªtre de la simulation visible
		Courbe courbe= new Courbe(); //definition de la feneªtre contenant le graphique resume de la simulation
		courbe.setVisible(true); //rend la feneªtre graphique visible

	}
	


	public class JSpinnerText extends JPanel { //classe interne permettant de creer des objets spinnerText
	    JSpinner spinner;
	    int i; //permet de garder le maximum voulu
		public JSpinnerText(String s, int i, int j, int k) { //constructeur qui va permettre de construire un spinner avec text associe et un maximum definit
	        super();
	        this.i=i;
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

	    public JFormattedTextField getTextField(JSpinner spinner) { //permet au spinner d'avoir un zone de texte
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

	     protected JSpinner addLabeledSpinner(Container c, String label, SpinnerModel model) { //associe un spinner et un texte
	        JLabel l = new JLabel(label);
	        c.add(l);

	        JSpinner spinner = new JSpinner(model);
	        l.setLabelFor(spinner);
	        c.add(spinner);

	        return spinner;
	    }

	      int getValue() {
	    	 return (int)this.spinner.getValue();
	     } //renvoie la valeur courante de l'attribut spinner
	      void restart() {
	    	  this.spinner.setValue(i/2);
	      } //fixe la valeur courante de l'attribut spinner

		
	}
	


	public class JSliderText extends JPanel{ //classe interne qui va permettre la creation de slider avec du texte
		JLabel sliderLabel; //texte desire
		JSlider slider; //slider auquel on va associe le texte
		int réni;


		JSliderText(int min, int max,int M, int m,String s){ //constructeur de l'objet
			this.sliderLabel= new JLabel(s, JLabel.CENTER); //creation du label avec texte voulu
			slider= new JSlider(); //creation du slider
			this.réni=max;
			this.slider.setMinimum(min);//parametrage du slider
			this.slider.setMaximum(max);
			this.slider.setValue(max/2);
			this.slider.setMajorTickSpacing(M);
			this.slider.setMinorTickSpacing(m);
			this.slider.setPaintTicks(true);
			this.slider.setPaintLabels(true);
			this.sliderLabel.setAlignmentY(Component.TOP_ALIGNMENT);
			this.add(sliderLabel); //ajout du label e  l'objet
			this.add(slider); //ajout du slider e  l'objet
		}

		 void setValue(int n) {
			 this.slider.setValue(n);
		 } //fixe la valeur courante du slider de l'objet
		 int getValue() {
			 return this.slider.getValue();
		 } //recupere la valeur  courante du slider
		 void restart() {
			 this.setValue(réni/2);
		 }
	}

}
