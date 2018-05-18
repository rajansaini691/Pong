import java.awt.Color;
import java.awt.Graphics2D;

public class EvilPaddle extends Paddle {

	private boolean isIntersecting = false;
	Color c1;
	String winningPaddle;
	
	public EvilPaddle(int x, int y, String winningPaddle) {
		super(x, y, 0, 0);
		this.c1 = new Color(255, 76, 76);
		this.winningPaddle = winningPaddle;
	}
	
	public void moveAndDraw(Graphics2D win, Ball b1, Paddle left, Paddle right) {
		win.setColor(c1);
		win.fill(this);
		if(winningPaddle == "Left") {
			dy = -left.dy;
		} else if(winningPaddle == "Right") {
			dy = -right.dy;
		}
		
		if(this.intersects(b1)) {
			if(b1.lastHit == "right" && !isIntersecting && b1.dx > 0) {
				b1.dx = -Math.abs(b1.dx);
				isIntersecting = true;
			} else if(b1.lastHit == "left" && !isIntersecting && b1.dx < 0) {
				b1.dx = Math.abs(b1.dx);
				isIntersecting = true;
			}
			
			if(b1.lastHit == "right" && !isIntersecting && b1.dx < 0) {
				b1.dx = Math.abs(b1.dx);
				right.height = (int) ((double) right.height * 3/4);
			} else if(b1.lastHit == "left" && !isIntersecting && b1.dx > 0) {
				b1.dx = -Math.abs(b1.dx);
				left.height = (int) ((double) left.height * 3/4);
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
