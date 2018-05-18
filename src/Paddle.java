import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends Rectangle implements KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3254798794448647166L;
	double dy;
	Color c1;
	boolean isUp, isDown, smashable;
	int keyUp, keyDown, speed;
	
	public Paddle(int x, int y, int aKeyUp, int aKeyDown) {
		super(x, y, 10, 100);
		dy = 0;
		c1 = Color.WHITE;
		isUp = false;
		keyUp = aKeyUp;
		keyDown = aKeyDown;
		speed = 7;
		this.y = y;
	}
	
	public void moveAndDraw(Graphics2D win) {
		win.setColor(c1);
		win.fill(this);
		if(isUp && this.y > 0) {
			dy = 0 - speed;
		} else if(isDown && this.y < 561 - this.height) {
			dy = speed;
		} else {
			dy = 0;
		}
		
		if(smashable) {
			c1 = new Color(102, 204, 255);
		} else {
			c1 = Color.WHITE;
		}
		
		this.translate(0, (int) dy);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == this.keyUp) {
			isUp = true;
		} else if(e.getKeyCode() == this.keyDown) {
			isDown = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == this.keyUp) {
			isUp = false;
		} 
		if(e.getKeyCode() == this.keyDown) {
			isDown = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//Used for the powerPaddle
	public void moveAndDraw(Graphics2D win, Ball b1, Paddle left, Paddle right) {
		// TODO Auto-generated method stub
		
	}

	//Used for the computerPaddle
	public void moveAndDraw(Graphics2D win, Ball b2, PongDifficulty diff, Ball b1) {
		// TODO Auto-generated method stub
		
	}

}
