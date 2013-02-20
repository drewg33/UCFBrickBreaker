import java.awt.Graphics;

import javax.swing.JPanel;


public abstract class Brick extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected int Xpos, Ypos,Xvel,Yvel, height, width;
	Graphics g;
	
	public Brick(int Xpos, int Ypos)
	{
		this.Xpos = Xpos;
		this.Ypos = Ypos;
	}
	public abstract void impact();
	public abstract void detectImpact();
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
	
}