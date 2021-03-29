package com.company;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
public class Vue extends JFrame{
	Board board= new Board(0,0);
	JPanel panneau= new JPanel();
	JPanel Spinner =new JPanel();
	JPanel Graphique= new JPanel(); 
	JLabel test= new JLabel(); 
	JPanel Button=new JPanel();
	JSpinnerText individu=new JSpinnerText("sain(s):");
	JSpinnerText infecte= new JSpinnerText("infecté(s):");
	Modele modele=new Modele();
	Controleur controle= new Controleur(modele, this); 
	
	
	
	JButton b1= new JButton("Réinitialiser");
	JButton b2= new JButton("GO");
	JButton b3= new JButton("STOP");
	
	Vue(){
		
		this.setTitle("Corona");
		this.setSize(1000, 500);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.getContentPane().setLayout(new GridLayout());
		this.getContentPane().add(panneau, BorderLayout.EAST);
		
		
		this.panneau.setLayout(new BorderLayout());
	
		this.Spinner.setPreferredSize(new Dimension(100,100));
		this.Button.setPreferredSize(new Dimension(100,100));
		
		
		this.panneau.add(Button,BorderLayout.SOUTH);
		this.panneau.add(Spinner,BorderLayout.NORTH);
		this.Spinner.add(individu);
		this.Spinner.add(infecte);
		
		this.Button.add(b1);
		this.Button.add(b2);
		this.Button.add(b3);
		this.test.setHorizontalAlignment(SwingConstants.CENTER);
		this.panneau.add(test, BorderLayout.CENTER);
		
		b3.setEnabled(false);
		b1.addActionListener((event)->{reinitialise();});
		b2.addActionListener((event)->{go();});
		b3.addActionListener((event)->{stop();});
		
	}
	
	public void reinitialise() {
		this.individu.restart();
		this.infecte.restart();
		this.b2.setEnabled(true);
	
	}
	
	public void go() {
		int i=individu.getValue();
		int j=infecte.getValue();
		this.test.setText("i:"+i+" j:"+j);
		this.panneau.add(test, BorderLayout.CENTER);
		this.Graphique.add(new CurveCanvas());
		this.Graphique.setSize(500,500);
		this.board=new Board(i,j);
		
		this.getContentPane().add(board,BorderLayout.CENTER);
		this.getContentPane().add(this.Graphique, BorderLayout.NORTH);
		this.b2.setEnabled(false);
		this.b1.setEnabled(false);
		this.b3.setEnabled(true);
	}
	
	public void stop() {
		this.getContentPane().remove(board);
		this.b1.setEnabled(true);
		this.b3.setEnabled(false);
	}
	

	
	public class JSpinnerText extends JPanel {
	    JSpinner spinner;
	    
		public JSpinnerText(String s) {
	        super();
	        
	        JFormattedTextField ftf = null;
	        	
	        SpinnerModel chiffre = new SpinnerNumberModel(0, 0, 25, 1);       
	         spinner = addLabeledSpinner(this, s, chiffre);
	        spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
	        
	        ftf = getTextField(spinner);
	        if (ftf != null ) {
	            ftf.setColumns(8); 
	            ftf.setHorizontalAlignment(JTextField.RIGHT);
	        	}
	      
	       
	        }
	    
	    	
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
	 
	   
	 
	     protected JSpinner addLabeledSpinner(Container c, String label, SpinnerModel model) {
	        JLabel l = new JLabel(label);
	        c.add(l);
	 
	        JSpinner spinner = new JSpinner(model);
	        l.setLabelFor(spinner);
	        c.add(spinner);
	 
	        return spinner;
	    }
	     
	      int getValue() {
	    	 return (int)this.spinner.getValue();
	     }

	      void restart() {
	    	  this.spinner.setValue(0);
	      }
		
	}
}
