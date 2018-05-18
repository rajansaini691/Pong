import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Smasher extends Rectangle { //When the ball collides with this object, the paddle that last hit it turns red and smashes on its next hit
	private Random r1 = new Random();
	Color c1 = new Color(102, 204, 255);
	boolean isVisible = true;
	public Smasher() {
		super();
		this.height = 25;
		this.width = 25;
		this.x = r1.nextInt(550) + 100;
		this.y = r1.nextInt(350) + 100;
		this.isVisible = true;
	}
	public void draw(Graphics2D win, Ball b1, PongComp comp, Paddle left, Paddle right, ComputerPaddle computer) {
		win.setColor(c1);
		if(isVisible) 
		{
			win.fill(this);
			
			if(this.intersects(b1)) {
				isVisible = false;
				if(b1.lastHit == "right") {
					right.smashable = true;
				}
				if(b1.lastHit == "left") {
					left.smashable = true;
					computer.smashable = true;
					
				}
			}
		}
	}
}
