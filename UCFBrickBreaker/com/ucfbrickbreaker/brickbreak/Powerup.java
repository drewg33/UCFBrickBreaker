package com.ucfbrickbreaker.brickbreak;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Powerup extends FallingObject{
	
	private static final long serialVersionUID = 1L;
	
	private Color color;
	private boolean positiveEffect;
	protected static Random generator = new Random();
	
	protected int height, width;
	
	
	public Powerup(int x, int y){
		super(x,y);
		height = 16;
		width = 16;
		positiveEffect = generator.nextBoolean();
		if(positiveEffect) color = new Color(70, 150, 30);
		else color = new Color(220, 30, 50);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(color);
		g.fillRoundRect(x - (width/2), y - (height/2), width, height, 5, 5);
	}
	
	@Override
	public void acquireObject() {
		if(positiveEffect) {
			if(!GUI.balls.get(0).launched) GUI.balls.get(0).launch();
			int x = GUI.balls.get(0).x;
			int y = GUI.balls.get(0).y;
			GUI.balls.add(new Ball(x, y, -2, Ball.maxYvel/4));
			GUI.balls.add(new Ball(x, y, 2, Ball.maxYvel/4));
		}
	}
	
}
