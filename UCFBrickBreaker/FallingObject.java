import java.awt.Graphics;
import javax.swing.JPanel;


public abstract class FallingObject extends JPanel{
	

	private static final long serialVersionUID = 1L;
	protected int x, y;
	protected double yVelocity = 0;
	protected boolean destroy = false;
	
	public FallingObject(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public abstract void acquireObject();
	
	public void updateMetrics(){
		
		// Refresh ball position
		y += yVelocity;

		// Check horizontal boundary
		if (y > GUI.SCREEN_HEIGHT){
			destroy = true;
			return;
		}

		// Check if hit paddle
		if (y  > GUI.PADDLE_HEIGHT
				&& x < GUI.paddleX + GUI.PADDLE_WIDTH / 2
				&& x > GUI.paddleX - GUI.PADDLE_WIDTH / 2
				&& yVelocity > 0)
		{
			acquireObject();
			destroy = true;
			return;
		}

		if (yVelocity > -Ball.maxYvel) yVelocity = -Ball.maxYvel;
		
		// Account for acceleration (gravity)
		yVelocity += GUI.GRAVITY/3;
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	
}