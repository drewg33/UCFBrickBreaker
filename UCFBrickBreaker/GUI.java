import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener{

	static final long serialVersionUID = 1L;
	protected static final int SCREEN_WIDTH = 640;
	protected static final int SCREEN_HEIGHT = 480;
	protected static final int PADDLE_HEIGHT = 440;
	protected static final int PADDLE_WIDTH = 100;
	protected static final double PADDLE_ANGLE_DAMPER = 0.15;
	protected static final double PADDLE_POWER_DAMPER = 1;
	protected static final double POWER_MULTIPLIER = 2;
	protected static final int REFRESH_RATE = 60;
	protected static final double GRAVITY = 0.2; // Higher is stronger gravity
	protected static double Xaccel = 0.2; 
	protected static int paddleX = SCREEN_WIDTH/2;
	protected static int powerLevel = 5;

	
	protected static ArrayList <Brick> bricks = new ArrayList <Brick>();
	protected static ArrayList <Ball> balls = new ArrayList<Ball>();
	protected static ArrayList <Powerup> powerups = new ArrayList <Powerup>();
	protected static ArrayList <Score> scores = new ArrayList <Score>();
	
	protected static int score = 0;

	public GUI() {

		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addMouseListener(this);
		
		//bricks.add(new StandardBrick(300,150));
		balls.add(new Ball());
		//Random gen = new Random();
		//for(int i=0; i<10; i++) bricks.add(new StandardBrick(gen.nextInt(SCREEN_WIDTH), gen.nextInt(SCREEN_HEIGHT)));
		
		
		Thread gameThread = new Thread(){
			
			public void run(){
				
				while (true){
					
					int toRemove = 0;
					boolean removing = false;
					
					for(Ball b:balls){
						b.updateMetrics();
						if(b.destroy){
							toRemove = balls.indexOf(b);
							removing = true;
						}
					}
					
					if(removing) balls.remove(toRemove);
					if(balls.size()==0) balls.add(new Ball());
					
					removing = false;
					
					for(Powerup p:powerups){
						p.updateMetrics();
						if(p.destroy){
							toRemove = powerups.indexOf(p);
							removing = true;
						}
					}
					
					if(removing) powerups.remove(toRemove);
					
					removing = false;
					
					for(Score s:scores){
						s.updateMetrics();
						if(s.destroy){
							toRemove = scores.indexOf(s);
							removing = true;
						}
					}
					
					if(removing) scores.remove(toRemove);
					
					repaint();

					try{
						Thread.sleep(1000 / REFRESH_RATE);
					} catch (Exception e) {}
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
		//Image img;
		//try {
		//	img = ImageIO.read(new File("C:/Users/Hosam/Desktop/sun.jpg"));
		//	g.drawImage(img, 0, 0, null);
		//} catch (IOException e) {}
		
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
		g.drawString("Score: "+score, 20, 20);

		// Draw the ball
		g.setColor(Color.black);
		for(Ball ball:balls){
			g.fillOval((int) (ball.x - Ball.currentRadius), (int) (ball.y - Ball.currentRadius),
					(int) (2 * Ball.currentRadius), (int) (2 * Ball.currentRadius));
		}
		// Draw the paddle
		g.setColor(Color.black);
		g.fillRoundRect(paddleX - (PADDLE_WIDTH / 2), PADDLE_HEIGHT,
				PADDLE_WIDTH, 10, 5, 500);
		
		for (Brick b:bricks){
			//b.detectImpact();
			b.paintComponent(g);
		}
		
		for (Powerup p:powerups){
			p.paintComponent(g);
		}
		
		for (Score s:scores){
			s.paintComponent(g);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e){
		paddleX = e.getX();
		int test = Powerup.generator.nextInt(400);
		if(test==0){
			boolean score = Powerup.generator.nextBoolean();
			if(score) scores.add(new Score(e.getX(), 20, 100));
			else powerups.add(new Powerup(e.getX(), 20));
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		paddleX = e.getX();
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {		
	}

	@Override
	public void mouseExited(MouseEvent e) {		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!balls.get(0).launched) balls.get(0).launch();
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}
	

}
