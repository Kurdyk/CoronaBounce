package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
public class Vue extends JFrame{
	Board board =new Board(0,0);
	JPanel Graphique= new JPanel(); 
	
	Vue(int i,int j){
		this.setTitle("Corona");
		this.setSize(990, 530);
		this.getContentPane().setLayout(new GridLayout());
		this.board=new Board(i,j);
		this.Graphique.setLayout(new BorderLayout());
		this.Graphique.add(new CurveCanvas(1),BorderLayout.CENTER);
		this.getContentPane().add(board,BorderLayout.CENTER);
		this.getContentPane().add(this.Graphique, BorderLayout.EAST);		
	}
}
