import java.awt.Graphics;

import javax.swing.JPanel;


public abstract class Brick extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected int x, y, xVelocity,yVelocity, height, width;
	
	
	public Brick(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public abstract void impact();
	public abstract void detectImpact();
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
}