import java.awt.Color;
import java.awt.Graphics;


public class StandardBrick extends Brick
{
	
	private static final long serialVersionUID = 1L;
	private Color color = Color.red;
	
	public StandardBrick(int Xpos, int Ypos)
	{
		super(Xpos, Xpos);
		height = 10;
		width = 30;
		xVelocity = 0;
		yVelocity = 0;
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(x - (width/2), y - (height/2), width, height);
	}

	@Override
	public void impact()
	{
		//switch colors for now
		//later change this to delete brick
		if (color == Color.RED)
			color = Color.black;
		else
			color = Color.RED;
		
		GUI.ball.yVelocity = -GUI.ball.yVelocity;
	}

}
