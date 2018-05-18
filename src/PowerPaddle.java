import java.awt.Color;
import java.awt.Graphics2D;

public class PowerPaddle extends Paddle {
	private boolean isIntersecting = false;
	public PowerPaddle(int x, int y) {
		super(x, y, 0, 0);
		// TODO Auto-generated constructor stub
		speed = 3;
		dy = speed;
		this.c1 = new Color(152,251,152);
	}
	
	@Override
	public void moveAndDraw(Graphics2D win, Ball b1, Paddle left, Paddle right) {
		win.setColor(c1);
		win.fill(this);
		
		if(this.y < 0) {
			dy = Math.abs(speed);
		} else if(this.y > 561 - this.height) {
			dy = -Math.abs(speed);
		}
		
		if(this.intersects(b1)) {
			if(b1.lastHit == "right" && !isIntersecting && b1.dx > 0) {
				b1.dx = -Math.abs(b1.dx);
				isIntersecting = true;
			} else if(b1.lastHit == "left" && !isIntersecting && b1.dx < 0) {
				b1.dx = Math.abs(b1.dx);
				isIntersecting = true;
			} else {
				System.out.println("?");
			}
			
			if(b1.lastHit == "right" && !isIntersecting && b1.dx < 0) {
				b1.dx = Math.abs(b1.dx);
				right.height += 30;
			} else if(b1.lastHit == "left" && !isIntersecting && b1.dx > 0) {
				b1.dx = -Math.abs(b1.dx);
				left.height += 30;
			} else {
				System.out.println("error");
			}
			isIntersecting = true;
		} else {
			isIntersecting = false;
		}
		
		this.translate(0, (int) dy);
	}

}
