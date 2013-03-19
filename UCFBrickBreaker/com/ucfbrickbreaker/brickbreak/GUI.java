package com.ucfbrickbreaker.brickbreak;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//import java.util.Random;

import java.io.*;


import javax.sound.sampled.*;

import javax.swing.*;


public class GUI extends JPanel implements MouseMotionListener, MouseWheelListener, MouseListener{

	static final long serialVersionUID = 1L;
	
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
	
	protected Font original;
	protected Font small = new Font("small", 0, 10);
	
	protected static ArrayList <Brick> bricks = new ArrayList <Brick>();
	protected static ArrayList <Ball> balls = new ArrayList<Ball>();
	protected static ArrayList <Powerup> powerups = new ArrayList <Powerup>();
	protected static ArrayList <Resource> resources = new ArrayList <Resource>();
	
	

	public GUI() {
		
		Dimension dim = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setPreferredSize(dim);
		//this.setMaximumSize(dim);
		//this.setMinimumSize(dim);
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
		double temp = Powerup.generator.nextDouble() * newRange;
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

		// Draw the balls
		g.setColor(Color.black);
		for(Ball b:balls){
			b.paintComponent(g);
		}
		
		
		// Draw the paddle
		g.setColor(Color.black);
		g.fillRoundRect(paddleX - (PADDLE_WIDTH / 2), PADDLE_HEIGHT, PADDLE_WIDTH, 10, 500, 500);
		//i = new ImageIcon("C:/Users/Hosam/Desktop/paddle.png");
		//i.paintIcon(this, g, paddleX - (PADDLE_WIDTH / 2), PADDLE_HEIGHT);
		
		for (Brick b:bricks){
			//b.detectImpact();
			b.paintComponent(g);
		}
		
		for (Powerup p:powerups){
			p.paintComponent(g);
		}
		
		for (Resource r:resources){
			r.paintComponent(g);
		}
		
		// Draw HUD Elements
		g.setColor(Color.black);
		g.drawLine(GAME_LEFT, 0, GAME_LEFT, SCREEN_HEIGHT);
		g.drawLine(GAME_RIGHT, 0, GAME_RIGHT, SCREEN_HEIGHT);
		
		g.setColor(new Color(240, 240, 240));
		g.fillRect(0 , 0 , GAME_LEFT, SCREEN_HEIGHT);
		g.fillRect(GAME_RIGHT+1, 0, SCREEN_WIDTH-GAME_RIGHT-1, SCREEN_HEIGHT);
		
		
		
		// Power meter
		g.setColor(Color.black);
		g.drawString("POWER", GAME_RIGHT+43, 14);
		g.drawLine(GAME_RIGHT, 17, SCREEN_WIDTH, 17);
		g.fillRect(GAME_RIGHT+21, 25, 34, 64);
		g.setColor(Color.white);
		g.fillRect(GAME_RIGHT+23, 27, 30, 60);
		g.setColor(Color.red);
		g.fillRect(GAME_RIGHT+23, 27 + (6 * (10 - powerLevel)), 30, 60 - (6 * (10 - powerLevel)));
		g.setColor(Color.black);
		g.drawLine(GAME_RIGHT+21, 56, GAME_RIGHT+53, 56);
		g.drawString("Boosters", GAME_RIGHT+12, 102);
		g.drawString("" + (powerLevel - 5), GAME_RIGHT+34, 120);


		// Energy Meter
		g.setColor(Color.black);
		g.fillRect(GAME_RIGHT+82, 25, 14, 64);
		g.setColor(Color.white);
		g.fillRect(GAME_RIGHT+84, 27, 10, 60);
		g.setColor(Color.red);
		if(energyLevel%5==0) g.fillRect(GAME_RIGHT+84, (int)(27 + (0.6 * (100 - energyLevel))), 10, (int)(60 - (0.6 * (100 - energyLevel))));
		else g.fillRect(GAME_RIGHT+84, (int)(27 + (0.6 * (100 - energyLevel))), 10, (int)(60 - (0.6 * (100 - energyLevel))+1));
		g.drawString("Energy", GAME_RIGHT+69, 102);
		g.drawString(""+energyLevel, GAME_RIGHT+88-(""+energyLevel).length()*3, 120);

		
		
		// Resources Gathered
		g.setColor(Color.black);
		g.drawLine(GAME_RIGHT, 138, SCREEN_WIDTH, 138);
		g.drawString("RESOURCES", GAME_RIGHT+22, 150);
		g.drawLine(GAME_RIGHT, 152, SCREEN_WIDTH, 152);

		
		g.drawString(""+AIR_REQUIRED, GAME_RIGHT+4, 180);
		g.drawString(""+WATER_REQUIRED, GAME_RIGHT+34, 180);
		g.drawString(""+FUEL_REQUIRED, GAME_RIGHT+68, 180);
		g.drawString(""+FOOD_REQUIRED, GAME_RIGHT+98, 180);
		
		g.fillRect(GAME_RIGHT+4, 185, 14, 150);
		g.fillRect(GAME_RIGHT+34, 185, 14, 150);
		g.fillRect(GAME_RIGHT+68, 185, 14, 150);
		g.fillRect(GAME_RIGHT+98, 185, 14, 150);
		g.setFont(small);
		//g.drawString("AIR WATER FUEL FOOD", GAME_RIGHT+2, 350);
		g.setColor(Resource.AIR_COLOR);
		g.drawString("AIR", GAME_RIGHT+1, 350);
		g.drawString("AIR", GAME_RIGHT+2, 350);
		g.setColor(Resource.WATER_COLOR);
		g.drawString("WATER", GAME_RIGHT+21, 350);
		g.drawString("WATER", GAME_RIGHT+22, 350);
		g.setColor(Resource.FUEL_COLOR);
		g.drawString("FUEL", GAME_RIGHT+61, 350);
		g.drawString("FUEL", GAME_RIGHT+62, 350);
		g.setColor(Resource.FOOD_COLOR);
		g.drawString("FOOD", GAME_RIGHT+90, 350);
		g.drawString("FOOD", GAME_RIGHT+91, 350);
		g.setColor(Color.black);
		
		
		g.drawString(""+airGathered, GAME_RIGHT+11-(""+airGathered).length()*3, 360);
		g.drawString(""+waterGathered, GAME_RIGHT+42-(""+waterGathered).length()*3, 360);
		g.drawString(""+fuelGathered, GAME_RIGHT+75-(""+fuelGathered).length()*3, 360);
		g.drawString(""+foodGathered, GAME_RIGHT+105-(""+foodGathered).length()*3, 360);
		
		g.setColor(Color.white);
		g.fillRect(GAME_RIGHT+6, 187, 10, 146);
		g.fillRect(GAME_RIGHT+36, 187, 10, 146);
		g.fillRect(GAME_RIGHT+70, 187, 10, 146);
		g.fillRect(GAME_RIGHT+100, 187, 10, 146);
		
		if(airGathered >0){
			g.setColor(Resource.AIR_COLOR);
			if(curAir > (int)(187 + ((double)(146/AIR_REQUIRED) * (AIR_REQUIRED - airGathered)))) {
				curAir--;
				g.fillRect(GAME_RIGHT+6, curAir, 10,  (333-curAir));
			}
			else g.fillRect(GAME_RIGHT+6, (int)(187 + ((double)(146/AIR_REQUIRED) * (AIR_REQUIRED - airGathered))), 10, (int)(146 - ((double)(146/AIR_REQUIRED) * (AIR_REQUIRED - airGathered))));
		}
		if(waterGathered>0){
			g.setColor(Resource.WATER_COLOR);
			if(curWater > (int)(187 + ((double)(146/WATER_REQUIRED) * (WATER_REQUIRED - waterGathered)))) {
				curWater--;
				g.fillRect(GAME_RIGHT+36, curWater, 10,  (333-curWater));
			}
			else g.fillRect(GAME_RIGHT+36, (int)(187 + ((double)(146/WATER_REQUIRED) * (WATER_REQUIRED - waterGathered))), 10, (int)(146 - ((double)(146/WATER_REQUIRED) * (WATER_REQUIRED - waterGathered))));
		}
		if(fuelGathered>0){
			g.setColor(Resource.FUEL_COLOR);
			if(curFuel > (int)(187 + ((double)(146/FUEL_REQUIRED) * (FUEL_REQUIRED - fuelGathered)))) {
				curFuel--;
				g.fillRect(GAME_RIGHT+70, curFuel, 10,  (333-curFuel));
			}
			else g.fillRect(GAME_RIGHT+70, (int)(187 + ((double)(146/FUEL_REQUIRED) * (FUEL_REQUIRED - fuelGathered))), 10, (int)(146 - ((double)(146/FUEL_REQUIRED) * (FUEL_REQUIRED - fuelGathered))));
		}
		if(foodGathered>0){
			g.setColor(Resource.FOOD_COLOR);
			if(curFood > (int)(187 + ((double)(146/FOOD_REQUIRED) * (FOOD_REQUIRED - foodGathered)))) {
				curFood--;
				g.fillRect(GAME_RIGHT+100, curFood, 10,  (333-curFood));
			}
			else g.fillRect(GAME_RIGHT+100, (int)(187 + ((double)(146/FOOD_REQUIRED) * (FOOD_REQUIRED - foodGathered))), 10, (int)(146 - ((double)(146/FOOD_REQUIRED) * (FOOD_REQUIRED - foodGathered))));
		}
		//g.setFont(original);
		g.setColor(Color.black);
		g.drawString("Liquid Assets:", GAME_RIGHT+2, 380);
		g.setFont(original);
		g.drawString("$"+money, GAME_RIGHT+70, 381);
		g.drawString("$"+money, GAME_RIGHT+71, 381);
		
		
		g.drawLine(GAME_RIGHT, 400, SCREEN_WIDTH, 400);
		g.drawString("AMMUNITION", GAME_RIGHT+22, 412);
		g.drawLine(GAME_RIGHT, 417, SCREEN_WIDTH, 417);
		
		
		//ENVIRONMENT
		g.drawLine(0, 17, GAME_LEFT, 17);
		g.drawString("ENVIRONMENT", 18, 14);
		g.drawString("Planet: Mars", 10, 38);
		g.drawString("Gravity:", 10, 74);
		g.drawLine(70, 55, 70, 75);
		g.drawPolygon(new int[]{60, 80, 70}, new int[]{75, 75, 85}, 3);
		g.drawString(""+GRAVITY, 90, 74);

		g.drawString("Solar Wind:", 10, 110);
		g.drawLine(30, 130, 85, 130);
		
		if(WIND > 0){
			g.drawPolygon(new int[]{85, 85, 95}, new int[]{120, 140, 130}, 3);
		}
		else{
			g.drawPolygon(new int[]{30, 30, 20}, new int[]{120, 140, 130}, 3);
		}
		g.drawString(String.format("%.2f MPH", WIND*100), 50, 160);
		
		
		
		
		//g.setColor(Color.red);
		//if(energyLevel%5==0) g.fillRect(GAME_RIGHT+14, (int)(167 + (0.6 * (100 - energyLevel))), 10, (int)(60 - (0.6 * (100 - energyLevel))));
		//else g.fillRect(GAME_RIGHT+14, (int)(167 + (0.6 * (100 - energyLevel))), 10, (int)(60 - (0.6 * (100 - energyLevel))+1));

		
		//g.drawString("Score: "+score, 20, 20);
		
	}
	

	@Override
	public void mouseMoved(MouseEvent e){
		int newx = e.getX();
		if(newx > GAME_RIGHT) paddleX = GAME_RIGHT;
		else if(newx < GAME_LEFT) paddleX = GAME_LEFT;
		else paddleX =newx;
		
		/*int test = Powerup.generator.nextInt(100);
		if(test==0){
			boolean score = Powerup.generator.nextBoolean();
			//boolean score = true;
			if(score) resources.add(new Resource(e.getX(), 20, Resource.ResourceType.getRandom()));
			else powerups.add(new Powerup(e.getX(), 20));
		}*/
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
			else{
				powerLevel = 10;
			}
		} 
		
		else {
			if (powerLevel + wheelMovement >= 0){
				if(energyLevel>=(Math.abs(powerLevel+wheelMovement-5)*10)) powerLevel += wheelMovement;
			} 
			else{
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
