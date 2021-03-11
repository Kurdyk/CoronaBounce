package com.company;
import javax.swing.*;
import java.awt.*;
public class Vue extends JFrame{
	Board board= new Board();
	JPanel panneau= new JPanel();
	JPanel Spinner =new JPanel();
	JPanel Button=new JPanel();
	
	Modele modele=new Modele();
	Controleur controle= new Controleur(modele, this); 
	
	JLabel label1= new JLabel();
	JLabel label2= new JLabel();
	SpinnerModel model= new SpinnerNumberModel(0,0,20,1);
	JSpinner individu= new JSpinner(model);
	JSpinner infecte= new JSpinner(model);
	JButton b1= new JButton("Réinitialiser");
	JButton b2= new JButton("GO");
	JButton b3= new JButton("STOP");
	
	Vue(){
		
		this.setTitle("Corona");
		this.setSize(1000, 500);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.getContentPane().setLayout(new GridLayout());
		this.getContentPane().add(panneau, BorderLayout.EAST);
		this.getContentPane().add(board,BorderLayout.WEST);
		
		this.panneau.setLayout(new BorderLayout());
	
		this.Spinner.setPreferredSize(new Dimension(100,100));
		this.Button.setPreferredSize(new Dimension(100,100));
		
		this.individu.add(label1);
		this.panneau.add(Button,BorderLayout.SOUTH);
		this.panneau.add(Spinner, BorderLayout.CENTER);
		this.Spinner.add(individu);
		this.Spinner.add(infecte);
		this.Button.add(b1);
		this.Button.add(b2);
		this.Button.add(b3);
		
		
		individu.addChangeListener((event)->{controle.spinnerMoved();});
		infecte.addChangeListener((event)->{controle.spinnerMoved();});
		
		b1.addActionListener((event)->{reinitialise();});
		b2.addActionListener((event)->{go();});
		b3.addActionListener((event)->{stop();});
		
	}
	
	public void reinitialise() {
		
	}
	
	public void go() {
		
	}
	
	public void stop() {
		
	}
}
