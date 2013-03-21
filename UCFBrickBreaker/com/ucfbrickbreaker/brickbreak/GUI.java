package com.ucfbrickbreaker.brickbreak;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
//import java.util.Random;

import java.io.*;


import javax.sound.sampled.*;

import javax.swing.*;


public class GUI extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener{

	static final long serialVersionUID = 1L;
	static Random generator = new Random();
	
	protected static final int SCREEN_WIDTH = 880;
	protected static final int SCREEN_HEIGHT = 480;
	protected static final int GAME_AREA_WIDTH = 640;
	protected static final int GAME_LEFT = (SCREEN_WIDTH/2)-(GAME_AREA_WIDTH/2);
	protected static final int GAME_RIGHT = (SCREEN_WIDTH/2)+(GAME_AREA_WIDTH/2);

	
	protected static final int PADDLE_HEIGHT = 450;
	protected static final int PADDLE_WIDTH = 100;
	protected static final double PADDLE_ANGLE_DAMPER = 0.15;
	protected static final double PADDLE_POWER_DAMPER = 0.75;
	protected static final double POWER_MULTIPLIER = 2;
	protected static final int REFRESH_RATE = 60;
	protected static final double GRAVITY = 0.2; // Higher is stronger gravity
	protected static double WIND = 0;
	
	protected static int paddleX = SCREEN_WIDTH/2;
	protected static int powerLevel = 5;
	protected static int energyLevel = 100;

	
	protected static int AIR_REQUIRED = 5;
	protected static int WATER_REQUIRED = 5;
	protected static int FUEL_REQUIRED = 5;
	protected static int FOOD_REQUIRED = 5;
	protected static int airGathered = 0;
	protected static int waterGathered = 0;
	protected static int fuelGathered = 0;
	protected static int foodGathered = 0;
	
	protected static int curFood = 333;
	protected static int curAir = 333;
	protected static int curWater = 333;
	protected static int curFuel = 333;
	
	protected static int money = 0;
	
	protected static Font original;
	protected static Font small = new Font("small", 0, 10);
	
	protected static ArrayList <Brick> bricks = new ArrayList <Brick>();
	protected static ArrayList <Ball> balls = new ArrayList<Ball>();
	protected static ArrayList <Powerup> powerups = new ArrayList <Powerup>();
	protected static ArrayList <Resource> resources = new ArrayList <Resource>();
	
	protected HUD hud = new HUD();
	int time_remaining = 18000;

	public GUI() {
		
		Dimension dim = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setPreferredSize(dim);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addMouseListener(this);
		
		int height = 10;
		
		bricks.add(new Brick(170,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(220,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(270,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(320,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(370,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(420,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(470,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(520,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(570,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(620,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(670,height, Brick.BrickType.getRandom()));
		bricks.add(new Brick(720,height, Brick.BrickType.getRandom()));
		
		bricks.add(new Brick(170,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(220,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(270,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(320,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(370,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(420,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(470,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(520,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(570,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(620,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(670,height+50, Brick.BrickType.getRandom()));
		bricks.add(new Brick(720,height+50, Brick.BrickType.getRandom()));
		
		bricks.add(new Brick(170,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(220,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(270,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(320,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(370,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(420,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(470,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(520,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(570,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(620,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(670,height+110, Brick.BrickType.getRandom()));
		bricks.add(new Brick(720,height+110, Brick.BrickType.getRandom()));
		
		balls.add(new Ball());
		//Random gen = new Random();
		//for(int i=0; i<10; i++) bricks.add(new StandardBrick(gen.nextInt(SCREEN_WIDTH), gen.nextInt(SCREEN_HEIGHT)));
		
		//playSound("kink", true);
		
		Thread gameThread = new Thread(){

			public void run(){
				
				int framecounter = 0;
				
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
					
					for(Resource r:resources){
						r.updateMetrics();
						if(r.destroy){
							toRemove = resources.indexOf(r);
							removing = true;
						}
					}
					
					if(removing) resources.remove(toRemove);	
					removing = false;
					
					for(Brick b:bricks){
						if(b.destroy){
							toRemove = bricks.indexOf(b);
							removing = true;
							b.destroyBrick();
						}
						else if(b.invert){
							b.destroy = true;
						}
						if(b.detectImpact()){
							//playSound("brick", false);
							b.invert = true;
						}
					}
					
					if(removing) {
						//Brick temp = bricks.get(toRemove);
						//bricks.add(new Brick(temp.x, temp.y, Brick.BrickType.getRandom()));
						//bricks.add(new Brick(temp.x, temp.y, temp.type));
						bricks.remove(toRemove);
					}
					
					
					if(framecounter == 119){
						//updateWind();
						framecounter = 0;
					}
					else framecounter ++;
					
					if(framecounter%15==0) if(energyLevel < 100) energyLevel++;
					if(framecounter%4==0) for(Brick b:bricks)	b.updateMetrics();

					
					
					repaint();
					if(time_remaining>=1)time_remaining--;
					
					try{
						Thread.sleep(1000 / REFRESH_RATE);
					} catch (Exception e) {}
				}
			}
		};

		gameThread.start();
	}
	
	
	
	public static void main(String[] args){

		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frame = new JFrame("Brick Breaker in SPACE!");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setContentPane(new GUI());
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	
	public static void playSound(String s, boolean loop){
		try {
		    File yourFile = new File("C:/Users/Hosam/Desktop/brickbreak/jar/audio/"+s+".wav");
		    AudioInputStream stream;
		    AudioFormat format;
		    DataLine.Info info;
		    Clip clip;

		    stream = AudioSystem.getAudioInputStream(yourFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
		    else clip.start();
		    
			
			
		}
		catch (Exception e) {;}
	}
	
	public static void updateWind(){
		double absRange = GRAVITY/2;
		double newRange = GRAVITY/2;
		double temp = generator.nextDouble() * newRange;
		double tempwind = (WIND - (newRange*((WIND+absRange)/(2*absRange)))) + temp;
		if(tempwind > absRange) tempwind = absRange;
		else if(tempwind < -absRange) tempwind = -absRange;
		WIND = tempwind;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		original = g.getFont();
		
		// Draw the background
		g.setColor(Color.white);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		//ImageIcon i = new ImageIcon("C:/Users/Hosam/Desktop/saturn1.jpg");
		//i.paintIcon(this, g, GAME_LEFT, 0);
	
		
		// Draw the paddle
		g.setColor(Color.black);
		g.fillRoundRect(paddleX - (PADDLE_WIDTH / 2), PADDLE_HEIGHT, PADDLE_WIDTH, 10, 500, 500);
		//i = new ImageIcon("C:/Users/Hosam/Desktop/paddle.png");
		//i.paintIcon(this, g, paddleX - (PADDLE_WIDTH / 2), PADDLE_HEIGHT);
		
		
		for(Ball b:balls) b.paintComponent(g);
		
		for (Brick b:bricks) b.paintComponent(g);
			
		for (Powerup p:powerups) p.paintComponent(g);
		
		for (Resource r:resources) r.paintComponent(g);	
		
		hud.paintComponent(g);
		
		g.drawString("TIME: "+time_remaining/(REFRESH_RATE*REFRESH_RATE)+":"+String.format("%02d",(time_remaining/REFRESH_RATE)%REFRESH_RATE), 20, 400);
		
		
	}
	

	@Override
	public void mouseMoved(MouseEvent e){
		int newx = e.getX();
		if(newx > GAME_RIGHT) paddleX = GAME_RIGHT;
		else if(newx < GAME_LEFT) paddleX = GAME_LEFT;
		else paddleX =newx;
	}
	
	@Override
	public void mouseDragged(MouseEvent e){
		int newx = e.getX();
		if(newx > GAME_RIGHT) paddleX = GAME_RIGHT;
		else if(newx < GAME_LEFT) paddleX = GAME_LEFT;
		else paddleX =newx;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e){
		
		int wheelMovement = -e.getWheelRotation();	
		
		if (wheelMovement > 0){
			if (powerLevel + wheelMovement <= 10){
				if(energyLevel>=(Math.abs(powerLevel+wheelMovement-5)*10)) powerLevel += wheelMovement;
			} 
			else powerLevel = 10;
			
		} 
		else {
			if (powerLevel + wheelMovement >= 0){
				if(energyLevel>=(Math.abs(powerLevel+wheelMovement-5)*10)) powerLevel += wheelMovement;
			} 
			else powerLevel = 0;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!balls.get(0).launched) balls.get(0).launch();
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
	public void mouseReleased(MouseEvent e) {		
	}
	

}
