package com.ucfbrickbreaker.brickbreak;
import java.awt.Graphics;

import javax.swing.JPanel;


public abstract class Brick extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected int x, y, height, width;
	protected double xVelocity, yVelocity;
	
	public Brick(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public abstract void vert_impact();
	public abstract void horiz_impact();
	
	/*public boolean detectImpact()
	{
		if (GUI.ball.x > x - width/2 && GUI.ball.x < x + width/2
			&& ((GUI.ball.y - (y+height/2) < GUI.ball.radius
					&& GUI.ball.yVelocity < 0
					&& GUI.ball.y > y+height/2) || 
				((y-height/2) - GUI.ball.y < GUI.ball.radius
					&& GUI.ball.yVelocity > 0))

			)
		{
			horiz_impact();
			return true;
		}
		/*else if(GUI.ball.y > y - height/2 
				&& GUI.ball.y < y + height/2
				&& ((GUI.ball.x - (x+width/2) < GUI.ball.radius
						&& GUI.ball.yVelocity < 0
						&& GUI.ball.y > y+height/2) || 
					((y-height/2) - GUI.ball.y < GUI.ball.radius
						&& GUI.ball.yVelocity > 0))
				)
		{
			vert_impact();
			return true;
		}
		return false;
	}*/
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
}