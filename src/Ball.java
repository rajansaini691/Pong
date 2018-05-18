import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Ball extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double dx, dy, speed;
	Color c1;
	private Random r1 = new Random();
	String lastHit;
	private Boolean isVisible;
	private double startingAngle;

	public Ball(int x, int y, int width, int height, Color color, Boolean isVisible, PongScore score) {
		// Put ball in middle of screen
		// Give it a Random dx and dy
		super(x, y, width, height);
		speed = 5;
		startingAngle = (2*r1.nextDouble()*Math.PI/3 - Math.PI/3);
		dx = speed*Math.cos(startingAngle);
		if(score.scored == "left") {
			dx = Math.abs(dx);
		} else if(score.scored == "right") {
			dx = -Math.abs(dx);
		}
		
		dy = Math.sin(startingAngle);
		c1 = color;
		this.isVisible = isVisible;
	}

	public Ball(int x, int y, int width, int height, Color color, boolean isVisible) {
		
		super(x, y, width, height);
		speed = 5;
		startingAngle = (2*r1.nextDouble()*Math.PI/3 - Math.PI/3);
		dx = speed*(2*r1.nextInt(2) - 1)*Math.cos(startingAngle);
		dy = Math.sin(startingAngle);
		c1 = color;
		this.isVisible = isVisible;
	}

	public void moveAndDraw(Graphics2D win) {
		if(isVisible) {
			// 1. Determine collision with top or bottom (dy *= -1)
			// 2. Draw it
			win.setColor(c1);
			//win.fill(this);
			win.fillArc(x, y, this.width, this.height, 0, 360);
		}
		this.translate((int)Math.round(dx),(int)Math.round(dy));
		if (this.y < 0) {
			dy = Math.abs(dy);
		}
		if (this.y > 561 - this.height) {
			this.dy = -Math.abs(dy);
		}

		if(this.x < 0) {
			this.x = -1;
			this.dx = 0;
			this.dy = 0;
		}
		
	}

	public boolean collisionWithPaddle(Paddle p1) {
		if (this.intersects(p1)) {
			return true;
		}
		return false;
	}

	public boolean pastBoundary(Paddle p1) {
		if (this.x < p1.x && p1.x < 100) {
			return true;
		}
		if (this.x > p1.x && p1.x > 400) {
			return true;
		}
		return false;
	}
}
