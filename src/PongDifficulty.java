import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongDifficulty implements KeyListener {
	// Controls the background and text colors to make changing the color schematic
	// easy
	private Color textColor = Color.BLACK;
	private int selectHeight = 350;
	private int selected = 0; // Switches between 0, 1, and 2; 0 = Easy, 1 = Medium, 2 = Hard, 3 = Cheating mode
	private boolean pressed = false;
	double reactionRadius;
	private int cheatingCounter = 0;
	boolean cheating = false;

	public PongDifficulty(PongComp comp) {

	}

	public void draw(Graphics2D win, PongComp comp, ComputerPaddle computer) {
		win.setColor(Color.WHITE);
		win.fillRect(0, 0, 800, 600);

		// Draws the "EASY" text
		win.setColor(textColor);
		win.setFont(new Font("ISOCTEUR", Font.PLAIN, 50));
		win.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		win.drawString("Easy", 50, selectHeight);

		// Draws the "Medium" text
		win.drawString("Medium", 285, selectHeight);

		// Draws the "Hard" text
		win.drawString("Hard", 600, selectHeight);
	
		switch (selected) {
		case 0:
			win.setColor(Color.BLACK);
			win.fillRect(40, selectHeight - 45, 160, 60);
			win.setColor(Color.WHITE);
			win.drawString("Easy", 50, selectHeight);
			break;
		case 1:
			win.setColor(Color.BLACK);
			win.fillRect(270, selectHeight - 45, 240, 60);
			win.setColor(Color.WHITE);
			win.drawString("Medium", 285, selectHeight);
			break;
		case 2:
			win.setColor(Color.BLACK);
			win.fillRect(590, selectHeight - 45, 160, 60);
			win.setColor(Color.WHITE);
			win.drawString("Hard", 600, selectHeight);
			break;
		default:
		}
		
		// Draws the "Select Difficulty" text
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 75));
		win.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		win.setColor(Color.BLACK);
		win.drawString("Select Difficulty", 100, 100);

		//Draws the surprise text when cheating mode is true (Alright. Get ready for my lazy and impossible AI!)
		if(cheating) {
			win.setFont(new Font("Yu Gothic", Font.PLAIN, 50));
			win.setColor(new Color(0, 0, 102));
			win.drawString("Alright. Get ready for my", 100, 200);
			win.drawString("lazy and impossible AI!", 100, 250);
		}
		
		//Draws the text stating the keyboard commands
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
		win.setColor(Color.BLACK);

		//Draws "Use the arrow keys to change selections and press ENTER to select"
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 35));
		win.drawString("Use the LEFT and RIGHT arrow keys", 85, 420);
		win.drawString("to change selections and press", 130, 460);
		win.drawString("ENTER to start the game", 180, 500);
		
		
		//Starts the game and changes the computer's difficulty depending on the difficulty selected
		if (pressed) {
			switch (selected) {
			case 0:
				comp.DIFFICULTY = 1; //Lower reaction time
				reactionRadius = 0.8;
				cheating = false;
				break;
			case 1:
				comp.DIFFICULTY = 1.1232412; //To induce rounding errors
				reactionRadius = 0.3;
				cheating = false;
				break;
			case 2:
				comp.DIFFICULTY = 2;   //Inhibits all rounding errors
				reactionRadius = 0.08;
				cheating = false;
				break;
			case 3:
				cheating = true;
				break;
			default:
				System.out.println("Check PongDifficulty");
			}
			comp.gameState = 4;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_ENTER) {
			pressed = true;
		}
		if (e.getKeyCode() == e.VK_RIGHT && !cheating) {
			selected++;
			selected %= 3;
		}
		if (e.getKeyCode() == e.VK_LEFT && !cheating) {
			selected--;
			selected %= 3;
			if (selected < 0) {
				selected = 2;
			}
		}
		if(e.getKeyCode() == e.VK_C && !cheating) {
			cheatingCounter++;
			if(cheatingCounter > 5) {
				cheating = true;
				selected = 3;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
