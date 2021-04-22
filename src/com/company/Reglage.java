package com.company;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class Reglage extends JFrame{
	
	JPanel panneau= new JPanel();
	JPanel Spinner =new JPanel();
	Vue view;
	
	JPanel Button=new JPanel();
	JSpinnerText population= new JSpinnerText("Population totale : ",40);
	
	JSliderText taux= new JSliderText(0,100,25,5,"Taux infection :");
	
	JButton b1= new JButton("Réinitialiser");
	JButton b2= new JButton("GO");
	
	Reglage(){
		
		this.setTitle("Corona");
		this.setSize(500, 300);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.getContentPane().setLayout(new GridLayout());
		this.getContentPane().add(panneau, BorderLayout.WEST);
		
		
		this.panneau.setLayout(new BorderLayout());
		
		this.Spinner.setPreferredSize(new Dimension(100,100));
		this.Button.setPreferredSize(new Dimension(100,100));
		this.Button.add(b1);
		this.Button.add(b2);		
		
		this.panneau.add(Spinner,BorderLayout.NORTH);
		this.panneau.add(taux, BorderLayout.CENTER);
		this.panneau.add(Button,BorderLayout.SOUTH);
		
		this.Spinner.add(population);	
		
		b1.addActionListener((event)->{reinitialise();});
		b2.addActionListener((event)->{go();});
		
	}
	
	public void reinitialise() {
		this.population.restart();
		this.taux.setValue(50);
	
	}
	
	public void go() {
		int p= population.getValue();
		int s= taux.getValue();
		int i=100-taux.getValue();
		int x=p*s/100;
		int y=p*i/100;
		boolean[] tabBoolean = new boolean[] {true, true, true, true, false, true, true, true, false};
		int[] tabVal = new int[] {x, y, 2, 900, 1000, 500, 3000, 3, 10000};

		Vue view= new Vue(tabBoolean, tabVal);
		view.setVisible(true);
		
	}
	
	public class JSpinnerText extends JPanel {
	    JSpinner spinner;
	    
		public JSpinnerText(String s, int i) {
	        super();
	        
	        JFormattedTextField ftf = null;
	        	
	        SpinnerModel chiffre = new SpinnerNumberModel(i/2, 0, i, 1);
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
	    	  this.spinner.setValue(10);
	      }
		
	}
	
	public class JSliderText extends JPanel{
		JLabel sliderLabel;
		JSlider slider;
		
		JSliderText(int min, int max,int M, int m,String s){
			this.sliderLabel= new JLabel(s, JLabel.CENTER);
			slider= new JSlider();
			this.slider.setMinimum(min);
			this.slider.setMaximum(max);
			this.slider.setValue(max/2);	
			this.slider.setMajorTickSpacing(M);
			this.slider.setMinorTickSpacing(m);
			this.slider.setPaintTicks(true);
			this.slider.setPaintLabels(true);
			this.sliderLabel.setAlignmentY(Component.TOP_ALIGNMENT);
			this.add(sliderLabel);
			this.add(slider);	
		}
		
		 void setValue(int n) {
			 this.slider.setValue(n);
		 }
		 
		 int getValue() {
			 return this.slider.getValue();
		 }
	}
	
}
