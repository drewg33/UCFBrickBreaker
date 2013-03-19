package com.ucfbrickbreaker.brickbreak;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class HUD extends JPanel{
	

	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		drawLeftHUD(g);
		drawRightHUD(g);		

	}
	
	private static void drawLeftHUD(Graphics g){
		
		// Draw the background
		g.setColor(new Color(240, 240, 240));
		g.fillRect(0 , 0 , GUI.GAME_LEFT, GUI.SCREEN_HEIGHT);
		
		// Separator line
		g.setColor(Color.black);
		g.drawLine(GUI.GAME_LEFT, 0, GUI.GAME_LEFT, GUI.SCREEN_HEIGHT);
		

		//ENVIRONMENT
		g.drawLine(0, 17, GUI.GAME_LEFT, 17);
		g.drawString("ENVIRONMENT", 18, 14);
		g.drawString("Planet: Mars", 10, 38);
		g.drawString("Gravity:", 10, 74);
		g.drawLine(70, 55, 70, 75);
		g.drawPolygon(new int[]{60, 80, 70}, new int[]{75, 75, 85}, 3);
		g.drawString(""+GUI.GRAVITY, 90, 74);

		g.drawString("Solar Wind:", 10, 110);
		g.drawLine(30, 130, 85, 130);
		
		if(GUI.WIND > 0){
			g.drawPolygon(new int[]{85, 85, 95}, new int[]{120, 140, 130}, 3);
		}
		else{
			g.drawPolygon(new int[]{30, 30, 20}, new int[]{120, 140, 130}, 3);
		}
		g.drawString(String.format("%.2f MPH", GUI.WIND*100), 50, 160);
		
	}
	
	private static void drawRightHUD(Graphics g){
		
		// Draw the background
		g.setColor(new Color(240, 240, 240));
		g.fillRect(GUI.GAME_RIGHT+1, 0, GUI.SCREEN_WIDTH-GUI.GAME_RIGHT-1, GUI.SCREEN_HEIGHT);
		
		// Separator line
		g.setColor(Color.black);
		g.drawLine(GUI.GAME_RIGHT, 0, GUI.GAME_RIGHT, GUI.SCREEN_HEIGHT);
		
		// Power meter
		g.setColor(Color.black);
		g.drawString("POWER", GUI.GAME_RIGHT+43, 14);
		g.drawLine(GUI.GAME_RIGHT, 17, GUI.SCREEN_WIDTH, 17);
		g.fillRect(GUI.GAME_RIGHT+21, 25, 34, 64);
		g.setColor(Color.white);
		g.fillRect(GUI.GAME_RIGHT+23, 27, 30, 60);
		g.setColor(Color.red);
		g.fillRect(GUI.GAME_RIGHT+23, 27 + (6 * (10 - GUI.powerLevel)), 30, 60 - (6 * (10 - GUI.powerLevel)));
		g.setColor(Color.black);
		g.drawLine(GUI.GAME_RIGHT+21, 56, GUI.GAME_RIGHT+53, 56);
		g.drawString("Boosters", GUI.GAME_RIGHT+12, 102);
		g.drawString("" + (GUI.powerLevel - 5), GUI.GAME_RIGHT+34, 120);
		
		// Energy Meter
		g.setColor(Color.black);
		g.fillRect(GUI.GAME_RIGHT+82, 25, 14, 64);
		g.setColor(Color.white);
		g.fillRect(GUI.GAME_RIGHT+84, 27, 10, 60);
		g.setColor(Color.red);
		if(GUI.energyLevel%5==0) g.fillRect(GUI.GAME_RIGHT+84, (int)(27 + (0.6 * (100 - GUI.energyLevel))), 10, (int)(60 - (0.6 * (100 - GUI.energyLevel))));
		else g.fillRect(GUI.GAME_RIGHT+84, (int)(27 + (0.6 * (100 - GUI.energyLevel))), 10, (int)(60 - (0.6 * (100 - GUI.energyLevel))+1));
		g.drawString("Energy", GUI.GAME_RIGHT+69, 102);
		g.drawString(""+GUI.energyLevel, GUI.GAME_RIGHT+88-(""+GUI.energyLevel).length()*3, 120);
		
		// Resources Gathered
		g.setColor(Color.black);
		g.drawLine(GUI.GAME_RIGHT, 138, GUI.SCREEN_WIDTH, 138);
		g.drawString("RESOURCES", GUI.GAME_RIGHT+22, 150);
		g.drawLine(GUI.GAME_RIGHT, 152, GUI.SCREEN_WIDTH, 152);	
		g.drawString(""+GUI.AIR_REQUIRED, GUI.GAME_RIGHT+4, 180);
		g.drawString(""+GUI.WATER_REQUIRED, GUI.GAME_RIGHT+34, 180);
		g.drawString(""+GUI.FUEL_REQUIRED, GUI.GAME_RIGHT+68, 180);
		g.drawString(""+GUI.FOOD_REQUIRED, GUI.GAME_RIGHT+98, 180);	
		g.fillRect(GUI.GAME_RIGHT+4, 185, 14, 150);
		g.fillRect(GUI.GAME_RIGHT+34, 185, 14, 150);
		g.fillRect(GUI.GAME_RIGHT+68, 185, 14, 150);
		g.fillRect(GUI.GAME_RIGHT+98, 185, 14, 150);
		g.setFont(GUI.small);
		g.setColor(Resource.AIR_COLOR);
		g.drawString("AIR", GUI.GAME_RIGHT+1, 350);
		g.drawString("AIR", GUI.GAME_RIGHT+2, 350);
		g.setColor(Resource.WATER_COLOR);
		g.drawString("WATER", GUI.GAME_RIGHT+21, 350);
		g.drawString("WATER", GUI.GAME_RIGHT+22, 350);
		g.setColor(Resource.FUEL_COLOR);
		g.drawString("FUEL", GUI.GAME_RIGHT+61, 350);
		g.drawString("FUEL", GUI.GAME_RIGHT+62, 350);
		g.setColor(Resource.FOOD_COLOR);
		g.drawString("FOOD", GUI.GAME_RIGHT+90, 350);
		g.drawString("FOOD", GUI.GAME_RIGHT+91, 350);
		g.setColor(Color.black);
		g.drawString(""+GUI.airGathered, GUI.GAME_RIGHT+11-(""+GUI.airGathered).length()*3, 360);
		g.drawString(""+GUI.waterGathered, GUI.GAME_RIGHT+42-(""+GUI.waterGathered).length()*3, 360);
		g.drawString(""+GUI.fuelGathered, GUI.GAME_RIGHT+75-(""+GUI.fuelGathered).length()*3, 360);
		g.drawString(""+GUI.foodGathered, GUI.GAME_RIGHT+105-(""+GUI.foodGathered).length()*3, 360);
		
		g.setColor(Color.white);
		g.fillRect(GUI.GAME_RIGHT+6, 187, 10, 146);
		g.fillRect(GUI.GAME_RIGHT+36, 187, 10, 146);
		g.fillRect(GUI.GAME_RIGHT+70, 187, 10, 146);
		g.fillRect(GUI.GAME_RIGHT+100, 187, 10, 146);
		
		if(GUI.airGathered >0){
			g.setColor(Resource.AIR_COLOR);
			if(GUI.curAir > (int)(187 + ((double)(146/GUI.AIR_REQUIRED) * (GUI.AIR_REQUIRED - GUI.airGathered)))) {
				GUI.curAir--;
				g.fillRect(GUI.GAME_RIGHT+6, GUI.curAir, 10,  (333-GUI.curAir));
			}
			else g.fillRect(GUI.GAME_RIGHT+6, (int)(187 + ((double)(146/GUI.AIR_REQUIRED) * (GUI.AIR_REQUIRED - GUI.airGathered))), 10, (int)(146 - ((double)(146/GUI.AIR_REQUIRED) * (GUI.AIR_REQUIRED - GUI.airGathered))));
		}
		if(GUI.waterGathered>0){
			g.setColor(Resource.WATER_COLOR);
			if(GUI.curWater > (int)(187 + ((double)(146/GUI.WATER_REQUIRED) * (GUI.WATER_REQUIRED - GUI.waterGathered)))) {
				GUI.curWater--;
				g.fillRect(GUI.GAME_RIGHT+36, GUI.curWater, 10,  (333-GUI.curWater));
			}
			else g.fillRect(GUI.GAME_RIGHT+36, (int)(187 + ((double)(146/GUI.WATER_REQUIRED) * (GUI.WATER_REQUIRED - GUI.waterGathered))), 10, (int)(146 - ((double)(146/GUI.WATER_REQUIRED) * (GUI.WATER_REQUIRED - GUI.waterGathered))));
		}
		if(GUI.fuelGathered>0){
			g.setColor(Resource.FUEL_COLOR);
			if(GUI.curFuel > (int)(187 + ((double)(146/GUI.FUEL_REQUIRED) * (GUI.FUEL_REQUIRED - GUI.fuelGathered)))) {
				GUI.curFuel--;
				g.fillRect(GUI.GAME_RIGHT+70, GUI.curFuel, 10,  (333-GUI.curFuel));
			}
			else g.fillRect(GUI.GAME_RIGHT+70, (int)(187 + ((double)(146/GUI.FUEL_REQUIRED) * (GUI.FUEL_REQUIRED - GUI.fuelGathered))), 10, (int)(146 - ((double)(146/GUI.FUEL_REQUIRED) * (GUI.FUEL_REQUIRED - GUI.fuelGathered))));
		}
		if(GUI.foodGathered>0){
			g.setColor(Resource.FOOD_COLOR);
			if(GUI.curFood > (int)(187 + ((double)(146/GUI.FOOD_REQUIRED) * (GUI.FOOD_REQUIRED - GUI.foodGathered)))) {
				GUI.curFood--;
				g.fillRect(GUI.GAME_RIGHT+100, GUI.curFood, 10,  (333-GUI.curFood));
			}
			else g.fillRect(GUI.GAME_RIGHT+100, (int)(187 + ((double)(146/GUI.FOOD_REQUIRED) * (GUI.FOOD_REQUIRED - GUI.foodGathered))), 10, (int)(146 - ((double)(146/GUI.FOOD_REQUIRED) * (GUI.FOOD_REQUIRED - GUI.foodGathered))));
		}
		
		g.setColor(Color.black);
		g.drawString("Liquid Assets:", GUI.GAME_RIGHT+2, 380);
		g.setFont(GUI.original);
		g.drawString("$"+GUI.money, GUI.GAME_RIGHT+70, 381);
		g.drawString("$"+GUI.money, GUI.GAME_RIGHT+71, 381);
		
		
		g.drawLine(GUI.GAME_RIGHT, 400, GUI.SCREEN_WIDTH, 400);
		g.drawString("AMMUNITION", GUI.GAME_RIGHT+22, 412);
		g.drawLine(GUI.GAME_RIGHT, 417, GUI.SCREEN_WIDTH, 417);
		
	}

}
