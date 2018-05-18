import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongRules implements KeyListener {
	// Controls the height of the descriptions
	private int instructionHeight = 190;

	// Controls the font size of the descriptions
	private int instructionSize = 20;

	// Controls the top padding between lines in the descriptions
	private int padding = 10;
	
	private boolean enter = false;
	private boolean release = false;
	
	public int state = 0;

	public PongRules(PongComp comp) {
		this.state = comp.gameState;
	}
	
	public void draw(Graphics2D win, PongComp comp) {
		state = comp.gameState;
		
		// Draws background
		win.setColor(Color.WHITE);
		win.fillRect(0, 0, 800, 600);

		// Draws "How to Play" as the heading on the top
		win.setColor(Color.BLACK);
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 100));
		win.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		win.drawString("How to Play", 100, 110);

		// Draws "Player 1:" and "Player 2:"
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
		win.drawString("Player 1:                      Player 2:  ", 40, instructionHeight);

		// Draws the instructions for Player 1
		win.setFont(new Font("Yu Gothic", Font.PLAIN, instructionSize));
		win.drawString("Use the W and S keys", 40, instructionHeight + instructionSize + padding);
		win.drawString("to move the left paddle", 40, instructionHeight + 2 * (instructionSize + padding));
		win.drawString("up and down, respectively.", 40, instructionHeight + 3 * (instructionSize + padding));
		win.drawString("In singleplayer, it is the computer.",40, instructionHeight + 4 * (instructionSize + padding));
		
		// Draws the instructions for Player 2
		win.setFont(new Font("Yu Gothic", Font.PLAIN, instructionSize));
		win.drawString("Use the UP and DOWN keys", 450, instructionHeight + instructionSize + padding);
		win.drawString("to move the right paddle", 450, instructionHeight + 2 * (instructionSize + padding));
		win.drawString("up and down, respectively.", 450, instructionHeight + 3 * (instructionSize + padding));
		win.drawString("In singleplayer, you are the human.", 450, instructionHeight + 4 * (instructionSize + padding));
		
		// Draws the "Objective" heading
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
		win.drawString("Objective:", 280, 370);
		
		//Draws the text detailing the objective of the game
		win.setFont(new Font("Yu Gothic", Font.PLAIN, instructionSize));
		win.drawString("Hit the ball to your opponent using the paddle. If you miss, your opponent",
				25, 370 + instructionSize + padding);
		win.drawString("gets a point. First player to 7 wins! The green power paddle increases the paddle ",
				25, 370 + 2*(instructionSize + padding));
		win.drawString("size, while the red evil paddle (controllable by the losing player) decreases it.", 25, 370 + 3*(instructionSize + padding));
		win.drawString("Finally, the blue smasher gives a smashing ability to the player who hits it.", 25, 370 + 4*(instructionSize + padding));

		//Draws "Press ENTER to go back"
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
		win.drawString("Press SPACEBAR to go back", 165, 540);		
		
		//Changes the GameState back to 0 if the ENTER key is pressed
		if(release && state == 3) {
			comp.gameState = 0;
			enter = false;
			release = false;
		}
		
				
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_SPACE && release == false && state == 3) {
			enter = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_SPACE && enter && state == 3) {
			release = true;
			enter = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
