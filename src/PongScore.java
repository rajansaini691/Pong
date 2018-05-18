import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PongScore {

	public int scoreLeft;
	public int scoreRight;
	public String winner;
	private int heightBelow = 325;
	private int distanceFromLeft = 235;
	private int fontSize = 150;
	Color c1 = new Color(127, 127, 127);
	private int counter = 0;
	public String scored;
	
	public PongScore() {
		scoreLeft = 0;
		scoreRight = 0;
	}

	public void draw(Graphics2D win, Ball b1, Paddle left, Paddle right, PongComp comp) {

		// Changes the score and resets the board when someone scores
		if (b1.pastBoundary(left)) {
			scoreRight += 1;
			scored = "right";
			comp.reset();
		}
		if (b1.pastBoundary(right)) {
			scoreLeft += 1;
			scored = "left";
			comp.reset();
		}
		
		//Gives the text color and font, gives it antialiasing
		win.setColor(c1);
		win.setFont(new Font("Yu Gothic", Font.PLAIN, fontSize));
		win.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		
		// Handles winning, draws the score
		if (scoreLeft < 7 && scoreRight < 7) {
			win.drawString("" + scoreLeft + "   " + scoreRight, distanceFromLeft, heightBelow);
		} else if (scoreLeft > scoreRight) {
			winner = "Left";
			comp.gameState = 2;
		} else {
			winner = "Right";
			comp.gameState = 2;
		}
		
	}
}
