package com.ucfbrickbreaker.brickbreak;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Brick extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	protected enum BrickType{
		AIR, WATER, FUEL, FOOD, SCRAP, SILVER, GOLD, POWERUP;
		
	    public static BrickType getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }
	}
	
	protected int x, y, height, width;
	protected double xVelocity;
	protected boolean invert = false;
	protected boolean destroy = false;
	protected BrickType type;
	private Color color;
	
	
	public Brick(int x, int y, BrickType t){
		this.x = x;
		this.y = y;
		this.type = t;
		this.height = 15;
		this.width = 50;
		
		if(t == BrickType.AIR) color = Resource.AIR_COLOR;	
		else if(t == BrickType.WATER) color = Resource.WATER_COLOR;
		else if(t == BrickType.FUEL)color = Resource.FUEL_COLOR;
		else if(t == BrickType.FOOD) color = Resource.FOOD_COLOR;
		else if(t == BrickType.SCRAP) color = Resource.SCRAP_COLOR;
		else if(t == BrickType.SILVER) color = Resource.SILVER_COLOR;	
		else if(t == BrickType.GOLD) color = Resource.GOLD_COLOR;
		else if(t == BrickType.POWERUP) color = Color.black;
	}
	
	
	public void updateMetrics(){		
		
		// Refresh brick position
		if(GUI.WIND > 0)x += 1;
		else if(GUI.WIND < 0) x+=-1;

	}
	
	
	public boolean detectImpact(){
		boolean ghostball = false;
		for(Ball b:GUI.balls){
			if(b.x>=this.x-width/2 && b.x<=this.x+width/2){	
				if (b.y - Ball.currentRadius < this.y+height/2 && b.yVelocity<0 && b.y>this.y){
					if(!ghostball){
						b.yVelocity = -b.yVelocity;
						b.y = this.y+height/2 + Ball.currentRadius;
					}
					invert = true;
					return true;
				} 
				else if (b.y + Ball.currentRadius > this.y-height/2 && b.yVelocity>0 && b.y<this.y){
					if(!ghostball){
						b.yVelocity = -b.yVelocity;
						b.y = this.y-height/2 - Ball.currentRadius;
					}
					invert = true;
					return true;
				} 
			}
			else if(b.y>=this.y-height/2  && b.y<=this.y+height/2){	
				if (b.x - Ball.currentRadius < this.x+width/2 && b.xVelocity<0 && b.x>this.x){
					if(!ghostball){
						b.xVelocity = -b.xVelocity;
						b.x = this.x+width/2 + Ball.currentRadius;
					}
					invert = true;
					return true;
				} 
				else if (b.x + Ball.currentRadius > this.x-width/2 && b.xVelocity>0 && b.x<this.x){
					if(!ghostball){
						b.xVelocity = -b.xVelocity;
						b.x = this.x-width/2 - Ball.currentRadius;
					}
					invert = true;
					return true;
				} 
			}
		}
		return false;
	}
	
	public void destroyBrick(){
		if(this.type == BrickType.AIR) GUI.resources.add(new Resource(this.x, this.y+height/2, Resource.ResourceType.AIR));	
		else if(this.type == BrickType.WATER) GUI.resources.add(new Resource(this.x, this.y+height/2, Resource.ResourceType.WATER));
		else if(this.type == BrickType.FUEL) GUI.resources.add(new Resource(this.x, this.y+height/2, Resource.ResourceType.FUEL));
		else if(this.type == BrickType.FOOD) GUI.resources.add(new Resource(this.x, this.y+height/2, Resource.ResourceType.FOOD));
		else if(this.type == BrickType.SCRAP) GUI.resources.add(new Resource(this.x, this.y+height/2, Resource.ResourceType.SCRAP));
		else if(this.type == BrickType.SILVER) GUI.resources.add(new Resource(this.x, this.y+height/2, Resource.ResourceType.SILVER));
		else if(this.type == BrickType.GOLD) GUI.resources.add(new Resource(this.x, this.y+height/2, Resource.ResourceType.GOLD));
		else if(this.type == BrickType.POWERUP) GUI.powerups.add(pickPowerup(this.x, this.y+height/2));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(color);
		if(invert) g.fill3DRect(x - (width/2), y - (height/2), width, height, false);
		else g.fill3DRect(x - (width/2), y - (height/2), width, height, true);
		if(this.type == BrickType.POWERUP){
			g.setColor(Color.WHITE);
			g.drawString("?", x-5, y+5);
			g.drawString("?", x-4, y+5);
		}
	}
	
	private Powerup pickPowerup(int x, int y) //method to select which type of powerup a brick will hold
	{
		int rand = GUI.generator.nextInt(8); //should pass nextInt the number of different powerups
		Powerup p;
		switch (rand) //make a new case here for each powerup type. Doesn't have to be a random num, just using that now..
		{
			default:
				p = new Missile(x,y);
				break;
				
		}
		
		return p;
	}
	
}