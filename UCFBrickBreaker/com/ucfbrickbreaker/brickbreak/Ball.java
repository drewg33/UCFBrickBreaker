package com.ucfbrickbreaker.brickbreak;
import java.lang.Math;

public class Ball{
	
	
	protected static final int defaultRadius = 5;
	protected static int currentRadius = 7;
	
	protected static final double maxYvel = -(8.7426* Math.pow(10*GUI.GRAVITY, 0.5287))-3;

	protected static final double minYvel = -15 * GUI.GRAVITY;
	
	
	protected int x, y;
	protected double xVelocity, yVelocity;
	protected boolean destroy = false;
	protected boolean launched;
	
	
	public Ball(){
		this.x = GUI.paddleX;
		this.y = GUI.PADDLE_HEIGHT - currentRadius;
		this.xVelocity = 0;
		this.yVelocity = 0;
		launched = false;
	}
	
	
	public Ball(int x, int y, double xVelocity, double yVelocity){
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity= yVelocity;
		launched = true;
	}
	
	
	public void updateMetrics(){
		
		if(launched){		
			
			// Refresh ball position
			x += xVelocity;
			y += yVelocity;
	
			// Check vertical boundaries
			if (x - currentRadius < GUI.GAME_LEFT){
				xVelocity = -xVelocity;
				x = GUI.GAME_LEFT + currentRadius; 
			} 
			else if (x + currentRadius > GUI.GAME_RIGHT){
				xVelocity = -xVelocity;
				x = GUI.GAME_RIGHT - currentRadius;
			}
	
			// Check horizontal boundaries
			if (y - currentRadius < 0){
				yVelocity = -yVelocity;
				y = currentRadius;
			} 
			else if (y + currentRadius > GUI.SCREEN_HEIGHT){
				destroy = true;
				return;
			}
	
			// Check if hit paddle
			if (y + currentRadius > GUI.PADDLE_HEIGHT
					&& x < GUI.paddleX + GUI.PADDLE_WIDTH / 2
					&& x > GUI.paddleX - GUI.PADDLE_WIDTH / 2
					&& yVelocity > 0)
			{
				yVelocity = (-yVelocity)
						- ((GUI.powerLevel - 5) * GUI.POWER_MULTIPLIER)
						+ GUI.PADDLE_POWER_DAMPER;
				
				consumePower();
				
				if (yVelocity < maxYvel) yVelocity = maxYvel;
				if (yVelocity > minYvel)  yVelocity = minYvel;
				
				y = GUI.PADDLE_HEIGHT - currentRadius;
	
				xVelocity = (x - GUI.paddleX) * GUI.PADDLE_ANGLE_DAMPER;
			}
	
			// Account for acceleration (gravity)
			yVelocity += GUI.GRAVITY;
			xVelocity += GUI.WIND;
		}
		
		else {
			x = GUI.paddleX;
			y = GUI.PADDLE_HEIGHT - currentRadius;
		}
		
	}
	
	public void launch(){
		yVelocity = (maxYvel/2) - ((GUI.powerLevel - 5) * GUI.POWER_MULTIPLIER);
		if (yVelocity < maxYvel) yVelocity = maxYvel;
		if (yVelocity > minYvel)  yVelocity = minYvel;
		consumePower();
		launched = true;
	}
	
	public static void consumePower(){
		GUI.energyLevel -= Math.abs(GUI.powerLevel-5)*10;
		GUI.powerLevel = 5;
	}
	
}
