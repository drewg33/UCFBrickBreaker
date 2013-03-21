package com.ucfbrickbreaker.brickbreak;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Missile extends Powerup
{
	private static final long serialVersionUID = 1L;
	private boolean falling = true, detonate = false;

	public Missile(int x, int y)
	{
		super(x, y);
		positiveEffect = true;
		color = new Color(70, 150, 30);
	}

	public void paintComponent(Graphics g)
	{
		// super.paintComponent(g);
		if (falling)
		{
			g.setColor(color);
			g.fillRoundRect(x - (width / 2), y - (height / 2), width, height, 5, 10000);
		} 
		else // paint after it has been caught
		{
			//TODO draw rocket image
			try
			{
				g.drawImage(ImageIO.read(new File("C:\\Users\\Drew\\Downloads\\rocket.png")), x - (width / 2), y - (height / 2), 20, 50, null);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		
	}

	public void acquireObject()
	{
		falling = false;
	}

	public void updateMetrics()
	{
		// Refresh position
		y += yVelocity;
		x += xVelocity;

		// Check horizontal boundary
		if (y > GUI.SCREEN_HEIGHT || y < 0)
		{
			destroy = true;
			return;
		}

		// Check vertical boundaries
		if (x < GUI.GAME_LEFT || x > GUI.GAME_RIGHT)
		{
			destroy = true;
			return;
		}
		
		if (falling)
		{

			// Check if hit paddle
			if (y > GUI.PADDLE_HEIGHT && x < GUI.paddleX + GUI.PADDLE_WIDTH / 2
					&& x > GUI.paddleX - GUI.PADDLE_WIDTH / 2 && yVelocity > 0)
			{
				acquireObject();
			}

			if (yVelocity > -Ball.maxYvel)
				yVelocity = -Ball.maxYvel;

			// Account for acceleration (gravity)
			yVelocity += GUI.GRAVITY / 3;
			xVelocity += (GUI.WIND / 3);
		} 
		else // already hit the paddle, coming back up as a missile now..
		{
			xVelocity = 0;
			yVelocity = -5;
			
			//TODO check collision with bricks
			/*if (<collision>)
				detonate = true;
			*/
			
			//detonate
			if (detonate)
			{
				for (Brick b:GUI.bricks)
				{
					//if brick is within w and h of missile on detonation, destroy it
					int w = b.width * 2;
					int h = b.height * 2;
					if (x + w < b.x && x - w > b.x && y + h < b.y && y - h > b.y)
					{
						b.destroy = true;
						//TODO draw smoke clouds where brick was
						//TODO play sound effect
					}
				}
			}
		}

	}
}
