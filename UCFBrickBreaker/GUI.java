// Hosam Bassiouni
// Messin' around with Brick Breaker

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GUI extends JPanel implements MouseMotionListener, MouseWheelListener
{

	private static final long serialVersionUID = 1L;
	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int PADDLE_HEIGHT = 420;
	private static final int PADDLE_WIDTH = 100;
	private static final double PADDLE_ANGLE_DAMPER = 0.1;
	private static final double PADDLE_POWER_DAMPER = 0.5;
	private static final double POWER_MULTIPLIER = 2;
	private static final int REFRESH_RATE = 60;
	private static final double GRAVITY = 0.4; // Higher is stronger gravity
	private int paddleX;
	private int powerLevel = 5;
	
	protected ArrayList <Brick> bricks = new ArrayList <Brick>();
	protected Ball ball = new Ball(50,20,3,2,5);

	public GUI()
	{

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		bricks.add(new StandardBrick(50,50)); //will want to add these from a config file for each level

		Thread gameThread = new Thread()
		{
			public void run()
			{
				while (true)
				{

					// Refresh ball position
					ball.x += ball.xVelocity;
					ball.y += ball.yVelocity;

					// Check vertical boundaries
					if (ball.x - ball.radius < 0)
					{
						ball.xVelocity = -ball.xVelocity; // Reflect along normal
						ball.x = ball.radius; // Re-position the ball at the edge
					} else if (ball.x + ball.radius > SCREEN_WIDTH)
					{
						ball.xVelocity = -ball.xVelocity;
						ball.x = SCREEN_WIDTH - ball.radius;
					}

					// Check horizontal boundaries
					if (ball.y - ball.radius < 0)
					{
						ball.yVelocity = -ball.yVelocity;
						ball.y = ball.radius;
					} else if (ball.y + ball.radius > SCREEN_HEIGHT)
					{
						ball.yVelocity = -ball.yVelocity;
						ball.y = SCREEN_HEIGHT - ball.radius;
					}

					// Check if hit paddle
					if (ball.y + ball.radius > PADDLE_HEIGHT
							&& ball.x < paddleX + PADDLE_WIDTH / 2
							&& ball.x > paddleX - PADDLE_WIDTH / 2
							&& ball.yVelocity > 0)
					{
						ball.yVelocity = (-ball.yVelocity)
								- ((powerLevel - 5) * POWER_MULTIPLIER)
								+ PADDLE_POWER_DAMPER;
						//powerLevel = 5;
						if (ball.yVelocity < -25)
							ball.yVelocity = -25;
						if (ball.yVelocity > -5)
							ball.yVelocity = -5;
						ball.y = PADDLE_HEIGHT - ball.radius;

						ball.xVelocity = (ball.x - paddleX) * PADDLE_ANGLE_DAMPER;
					}

					// Account for acceleration (gravity)
					ball.yVelocity += GRAVITY;

					repaint();

					try
					{
						Thread.sleep(1000 / REFRESH_RATE);
					} catch (Exception e)
					{
					}
				}
			}
		};

		gameThread.start();
	}

	public static void main(String[] args)
	{

		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new JFrame("Brick Breaker in SPACE!");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new GUI());
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Draw the background
		g.setColor(Color.white);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		// Draw the power meter
		g.setColor(Color.black);
		g.fillRect(580, 25, 34, 64);
		g.setColor(Color.white);
		g.fillRect(582, 27, 30, 60);
		g.setColor(Color.red);
		g.fillRect(582, 27 + (6 * (10 - powerLevel)), 30,
				60 - (6 * (10 - powerLevel)));
		g.setColor(Color.black);
		g.drawLine(580, 56, 613, 56);
		g.drawString("Power Level:", 563, 102);
		g.drawString("" + (powerLevel - 5), 594, 120);

		// Draw the ball
		g.setColor(Color.black);
		g.fillOval((int) (ball.x - ball.radius), (int) (ball.y - ball.radius),
				(int) (2 * ball.radius), (int) (2 * ball.radius));

		// Draw the paddle
		g.setColor(Color.black);
		g.fillRoundRect(paddleX - (PADDLE_WIDTH / 2), PADDLE_HEIGHT,
				PADDLE_WIDTH, 10, 5, 500);
		
		for (Brick b:bricks)
		{
			b.paintComponent(g);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		paddleX = e.getX();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		int wheelMovement = -e.getWheelRotation();
		if (wheelMovement > 0)
		{
			if (powerLevel + wheelMovement <= 10)
			{
				powerLevel += wheelMovement;
			} else
			{
				powerLevel = 10;
			}
		} else
		{
			if (powerLevel + wheelMovement >= 0)
			{
				powerLevel += wheelMovement;
			} else
			{
				powerLevel = 0;
			}
		}
	}

}
