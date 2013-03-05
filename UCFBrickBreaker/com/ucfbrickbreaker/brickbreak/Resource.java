package com.ucfbrickbreaker.brickbreak;
import java.awt.Color;
import java.awt.Graphics;


public class Resource extends FallingObject{

	private static final long serialVersionUID = 1L;

	protected static final Color AIR_COLOR = new Color(175, 215, 235);
	protected static final Color WATER_COLOR = new Color(10, 130, 190); 
	protected static final Color FUEL_COLOR = new Color(80, 45, 20);
	protected static final Color FOOD_COLOR = new Color(170, 80, 20);
	protected static final Color SCRAP_COLOR = new Color(70, 60, 50);
	protected static final Color SILVER_COLOR  = new Color(140, 130, 120);
	protected static final Color GOLD_COLOR = new Color(200, 150, 40); 
	
	protected enum ResourceType{
		AIR, WATER, FUEL, FOOD, SCRAP, SILVER, GOLD;
		
	    public static ResourceType getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }
	}
	
	private int value;
	private Color color;
	private ResourceType type;
	
	
	public Resource(int x, int y, ResourceType t) {
		super(x, y);
		this.type = t;
		if(t == ResourceType.AIR){
			this.value = 10;
			color = AIR_COLOR;
		}
		else if(t == ResourceType.WATER){
			this.value = 10;
			color = WATER_COLOR;
		}
		else if(t == ResourceType.FUEL){
			this.value = 10;
			color = FUEL_COLOR;
		}
		else if(t == ResourceType.FOOD){
			this.value = 10;
			color = FOOD_COLOR;
		}
		else if(t == ResourceType.SCRAP){
			this.value = 100;
			color = SCRAP_COLOR;
		}
		else if(t == ResourceType.SILVER){
			this.value = 200;
			color = SILVER_COLOR;
		}
		else if(t == ResourceType.GOLD){
			this.value = 500;
			color = GOLD_COLOR;
		}
			
	}

	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(this.color);
		g.drawString(""+type, x-((""+type).length()*4), y);
		g.drawString(""+type, x-((""+type).length()*4)+1, y+1);
	}
	
	@Override
	public void acquireObject() {
		if(type == ResourceType.AIR){
			if(GUI.airGathered < GUI.AIR_REQUIRED) GUI.airGathered++;
			else GUI.money += this.value;
		}
		else if(type == ResourceType.WATER){
			if(GUI.waterGathered < GUI.WATER_REQUIRED) GUI.waterGathered++;
			else GUI.money += this.value;
		}
		else if(type == ResourceType.FUEL){
			if(GUI.fuelGathered < GUI.FUEL_REQUIRED) GUI.fuelGathered++;
			else GUI.money += this.value;
		}
		else if(type == ResourceType.FOOD){
			if(GUI.foodGathered < GUI.FOOD_REQUIRED) GUI.foodGathered++;
			else GUI.money += this.value;
		}
		else if(type == ResourceType.SCRAP){
			GUI.money += this.value;
		}
		else if(type == ResourceType.SILVER){
			GUI.money += this.value;
		}
		else if(type == ResourceType.GOLD){
			GUI.money += this.value;
		}
	}

}
