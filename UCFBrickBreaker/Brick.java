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
	public boolean detectImpact()
	{
		if (GUI.ball.x - GUI.ball.radius < x + width / 2
			&& GUI.ball.x + GUI.ball.radius > x - width / 2
			&& GUI.ball.y  - GUI.ball.radius < y + height / 2
			&& GUI.ball.y  + GUI.ball.radius > y - height / 2
			/*&& GUI.ball.yVelocity > 0*/)
		{
			impact();
			return true;
		}
		return false;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
}