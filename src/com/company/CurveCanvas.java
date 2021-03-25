package com.company;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class CurveCanvas extends JComponent{
	
	private CurveFunction function = (x)->Math.sin(x);
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.RED);
		g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
		g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
		
		g.setColor(Color.BLACK);
		g.drawString("0, 0", (int)(getWidth()*0.51), (int)(getHeight()*0.54));
		
		double step = 0.1;
		g.setColor(new Color(125,0,255));
		
		int xDeb = xToPixel(-Math.PI);
		int yDeb = yToPixel(function.compute(-Math.PI));
		
		for(double lx = -Math.PI+step; lx<Math.PI+step; lx+=step) {
			int x = xToPixel(lx);
			int y = yToPixel(function.compute(lx));
			
			g.drawLine(x, y, xDeb, yDeb);
			
			xDeb=x;
			yDeb=y;
			
		}
		
	}
	
	public void setFunction(CurveFunction function) {
		this.function = function;
		this.repaint();
	}
	
	private int xToPixel(double x) {
		return (int)(getWidth()*(x + Math.PI)/ (2*Math.PI));
	}
	
	private int yToPixel(double y) {
		return (int)(getHeight()*(1-(y+1)/2.0));
	}
	
	public static interface CurveFunction{
		public double compute(double x);
	}
}
