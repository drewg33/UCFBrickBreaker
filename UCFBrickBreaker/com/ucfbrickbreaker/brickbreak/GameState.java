/**
 * 
 */
package com.ucfbrickbreaker.brickbreak;

import java.awt.Graphics2D;

/**
 * @author Thomas
 *
 */
public abstract class GameState {
	//public enum GameStateName
	public GameState(){
		
	}
	
	/**
	 * Checks input structures any changes any internal data structures in response to
	 * events received during updateLogic() and updateScreen().
	 */
	public abstract void updateInput();
	
	/**
	 * Updates the game logic based on the time that has passed and the input determined
	 * by updateInput().
	 */
	public abstract void updateLogic(long time);
	
	/**
	 * Updates (draws/renders) the screen based on the internal data determined by 
	 * updateLogic().
	 */
	public abstract void updateScreen(Graphics2D g2d);
	
}
