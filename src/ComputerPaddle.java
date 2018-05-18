import java.awt.Color;
import java.awt.Graphics2D;

public class ComputerPaddle extends Paddle {
	boolean smashable;
	double reactionRadius;
	
	public ComputerPaddle(int x, int y) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
	}
	
	
	//How it works: it compares its position to an invisible "ball", and it then tries to approximate the ball's position, where the
	//accuracy depends on the difficulty. However, if the isCheating variable is true, then the paddle's dy is the ball's. 
	@Override
	public void moveAndDraw(Graphics2D win, Ball b2, PongDifficulty diff, Ball b1) {
		win.setColor(c1);
		win.fill(this);
		
		if(smashable) {
			c1 = new Color(102, 204, 255);
		} else {
			c1 = Color.WHITE;
		}
		
		if(!diff.cheating) {
			if(b2.y > this.y + this.height/2 + this.height * diff.reactionRadius) {
				this.dy = Math.abs(this.speed);
			} else if(b2.y < this.y + this.height/2 - this.height * diff.reactionRadius) {
				this.dy = -Math.abs(this.speed);
			} else {
				this.dy = 0;
			}
			if(smashable) {
				c1 = new Color(102, 204, 255);
			} else {
				c1 = Color.WHITE;
			}
		} else {
			this.y = b1.y - this.height / 2;
			this.dy = 0;
		}
		this.translate(0, (int) dy);
	}
	
}
