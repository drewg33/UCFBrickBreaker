import java.awt.Color;
import java.awt.Graphics;


public class StandardBrick extends Brick
{
	
	private static final long serialVersionUID = 1L;
	public StandardBrick(int Xpos, int Ypos)
	{
		super(Xpos, Xpos);
		height = 10;
		width = 30;
		xVelocity = 0;
		yVelocity = 0;
		
	}

	@Override
	public void detectImpact()
	{
		
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(x - (width/2), y - (height/2), width, height);
	}

	@Override
	public void impact()
	{
		//standard brick only has one hit, delete the item here
	}

}
