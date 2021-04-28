package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class Reglage extends JFrame{ //class qui va créer la fenêtre menu pour l'utilisateur

	JPanel panneau= new JPanel(); //objet qui va contenir les différents objets avec lesquels l'utilisateur va agir

	Vue view;  //fenêtre qui va contenir la simulation
	Courbe courbe; //fenêtre qui va contenir le graphique résumé de la simulation

	JPanel Spinner =new JPanel(); //objet qui va contenir
	JPanel Button=new JPanel(); //objet qui contient des JButton laçant simulation et rénéatialiser les paramètres de la simulation
	JPanel checkBox = new JPanel();

	JSpinnerText population= new JSpinnerText("Population totale : ",40, 1); //objet crée par classe interne pour être utilisé
	JSliderText taux= new JSliderText(0,100,25,5,"Taux sain :"); //objet crée par classe interne pour être utilisé
	

	boolean act[]; //tableau qui indiquera quelles options l'utilisateur a voulu dans sa simulation
	int []val; //tableau indiquant la valeur des options voulues si besoin est

	JButton b1= new JButton("Réinitialiser");
	JButton b2= new JButton("GO");
	
	JCheckBox entreprise = new JCheckBox("entreprise");
	JCheckBox tauxmortalit� = new JCheckBox("mortalit�");
	
	JSliderText morta= new JSliderText(0,100,25,5,"Taux morta :");
	JSpinnerText  nbentreprise = new JSpinnerText("Entreprise",4,1);
	
	
	


	Reglage(){ //constructeur de la fenêtre

		this.setTitle("Corona"); //définit nom de la fenêtre
		this.setSize(500, 300); //définition taille fenêtre

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //arrête le programme quand la fenêtre est fermé

		this.getContentPane().setLayout(new GridLayout()); //définition de différentes zones de la fenêtre où on pourra ajouter des objets
		this.getContentPane().add(panneau, BorderLayout.CENTER);//ajoute l'objet panneau au centre de la fenêtre


		this.panneau.setLayout(new BorderLayout()); //définit différente zones dans l'objet panneau où on pourra intégrer d'autres objets


		this.Button.add(b1);//ajout de JButton à l'objet Button
		this.Button.add(b2);
		
		//this.checkBox.setLayout(new BorderLayout());
		//this.checkBox.setAlignmentX(CENTER_ALIGNMENT);
		
		this.checkBox.add(entreprise);
		this.checkBox.add(tauxmortalit�);
		this.checkBox.add(nbentreprise);
		this.checkBox.add(morta);
		
		
		//tauxmortalit�.addActionListener((event)->{slidermorta();});
		

		this.panneau.add(Spinner,BorderLayout.NORTH); //ajoute l'objet qui va contenir les objets indiquants la population totale et le taux d'individu sain souhaité par l'utilisateur
		
		this.panneau.add(checkBox,BorderLayout.CENTER);
		this.panneau.add(Button,BorderLayout.SOUTH);  //ajoute l'objet qui va contenir les objets pour lancer et réinitialiser les paramètres de la simulation


		this.Spinner.setLayout(new BorderLayout());
		this.Spinner.add(population, BorderLayout.NORTH); //ajoute le spinnnerText indiquant le nombre d'individu totale souhaité par l'utilisateur
		this.Spinner.add(taux,BorderLayout.CENTER);//ajoute le sliderText indiquant le taux d'individu sain désiré par l'utilisateur au panneau


		b1.addActionListener((event)->{reinitialise();}); //permet de lancer la fonction reinitialise quand le JButton 1 est cliqué
		b2.addActionListener((event)->{go();}); //permet de lancer la fonction go quand le JButton 2 est cliqué
		
		
		
	}


	public void slidermorta() {
		this.tauxmortalit�.add(morta);
	}

	public void reinitialise() { //réinitialise tous les paramètres de la simulation à des valeurs par défaut
		this.population.restart(); //remet la valeur du spinnerText a une valeur par défaut
		this.taux.restart();  //fixe la valeur du sliderText à 50
		this.morta.restart();
		this.nbentreprise.restart();
		this.tauxmortalit�.setSelected(false);
		this.entreprise.setSelected(false);
	}


	public void go() { //fonction qui va lancer la simulation
		int p= population.getValue(); //récupération et traitement des valeurs de population et du taux de sain
		int s= taux.getValue();
		int i=100-taux.getValue();
		int x=p*s/100;
		int y=p*i/100;
		
		

		
		//

		act = new boolean[9]; //initialision des tableaux contenant les options et valeurs souhaité par l'utilisateur
		

		act = new boolean[] {true, true, false, true, false, true, true, true, false};//initialision des tableaux contenant les options et valeurs souhaité par l'utilisateur
		val = new int[] {x, y, 0, 900, 1000, 500, 3000, 3, 10000};
		if(this.tauxmortalit�.isSelected()== true) {
			this.act[4] = true;
			this.val[4] = morta.getValue();
		}
		if(this.entreprise.isSelected()== true) {
			this.act[2] = true;
			this.val[2] = nbentreprise.getValue();
		}

        //attribution des valeurs de population et taux de sain dans les tablaux
		// les deux premiers éléments du tableau d'option sont toujours considérés comme vrai car "option" par défauts de la simulation


		Vue view= new Vue(act,val); //définition de la fenêtre contenant la simulation avec options désirées
		view.setVisible(true); //rend la fenêtre de la simulation visible
		Courbe courbe= new Courbe(); //définition de la fenêtre contenant le graphique résumé de la simulation
		courbe.setVisible(true); //rend la fenêtre graphique visible

	}
	


	public class JSpinnerText extends JPanel { //classe interne permettant de créer des objets spinnerText
	    JSpinner spinner;
	    int i; //permet de garder le maximum voulu
		public JSpinnerText(String s, int i, int j) { //constructeur qui va permettre de construire un spinner avec text associé et un maximum définit
	        super();
	        this.i=i;
	        JFormattedTextField ftf = null; //va permettre de ajouter du texte au spinner associé

	        SpinnerModel chiffre = new SpinnerNumberModel(i/2, 0, i, j); //création du spinner avec les valeurs numérique en fonction du maximum donner
	         spinner = addLabeledSpinner(this, s, chiffre);
	        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));


	        ftf = getTextField(spinner); //définition de la zone de text
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
	


	public class JSliderText extends JPanel{ //classe interne qui va permettre la création de slider avec du texte
		JLabel sliderLabel; //texte désiré
		JSlider slider; //slider auquel on va associé le texte
		int r�ni;


		JSliderText(int min, int max,int M, int m,String s){ //constructeur de l'objet
			this.sliderLabel= new JLabel(s, JLabel.CENTER); //création du label avec texte voulu
			slider= new JSlider(); //création du slider
			this.r�ni=max;
			this.slider.setMinimum(min);//paramétrage du slider
			this.slider.setMaximum(max);
			this.slider.setValue(max/2);
			this.slider.setMajorTickSpacing(M);
			this.slider.setMinorTickSpacing(m);
			this.slider.setPaintTicks(true);
			this.slider.setPaintLabels(true);
			this.sliderLabel.setAlignmentY(Component.TOP_ALIGNMENT);
			this.add(sliderLabel); //ajout du label à l'objet
			this.add(slider); //ajout du slider à l'objet
		}

		 void setValue(int n) {
			 this.slider.setValue(n);
		 } //fixe la valeur courante du slider de l'objet
		 int getValue() {
			 return this.slider.getValue();
		 } //récupère la valeur  courante du slider
		 void restart() {
			 this.setValue(r�ni/2);
		 }
	}

}
