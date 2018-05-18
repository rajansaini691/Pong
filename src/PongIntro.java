import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongIntro implements KeyListener {
	//Controls the background and text colors to make changing the color schematic easy
	private Color backgroundColor = Color.WHITE;
	private Color textColor = Color.BLACK;
	
	//Controls the dimensions of the triangle
	private int triangleHeight = 40, triangleWidth = 40, triangleX = 200, triangleY = 400;
	public boolean isMultiplayer = false;
	
	/* The "selected" integer tells you what button is selected:
	 * 0 - MULTIPLAYER
	 * 1 - SINGLEPLAYER
	 * 2 - RULES
	 * The numberOfButtons just makes it easier to add buttons later on
	 */
	private int selected = 0;
	private int numberOfButtons = 3;
	private boolean entered = false;
	private int state = 0;
	private boolean released = false;
	
	public PongIntro(PongComp comp) {
		this.state = comp.gameState;
	}
	
	public void draw(Graphics2D win, PongComp comp) {
		this.state = comp.gameState;
		
		//Draws the background
		win.setColor(backgroundColor);
		win.fillRect(0, 0, 800, 600);
		
		//Draws the PONG text
		win.setColor(textColor);
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 150));
		win.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		win.drawString("PONG", 180, 190);
		
		//Draws "By Rajan Saini" in a smaller font size below PONG
		win.setFont(new Font("Yu Gothic", Font.ITALIC, 40));
		win.drawString("By Rajan Saini", 250, 250);
		
		//Draws MULTIPLAYER
		win.setFont(new Font("Yu Gothic", Font.BOLD, 60));
		win.drawString("MULTIPLAYER", 180, 320);
		
		//Draws SINGLEPLAYER
		win.setFont(new Font("Yu Gothic", Font.BOLD, 60));
		win.drawString("SINGLEPLAYER", 160, 390);
		
		//Draws RULES
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 50));
		win.drawString("RULES", 310, 450);
		
		//Draws "Use the arrow keys to change selections and press ENTER to select"
		win.setFont(new Font("Yu Gothic", Font.PLAIN, 30));
		win.drawString("Use the arrow keys to change selections and", 80, 500);
		win.drawString("press ENTER to select", 230, 530);
		
		//Controls the selector's position based on the selector number
		if(selected == 0) {
			triangleX = 120;
			triangleY = 275;
		} else if(selected == 1) {
			triangleX = 90;
			triangleY = 355;
		} else if(selected == 2) {
			triangleX = 250;
			triangleY = 405;
		}
		
		//Draws the selector (a triangle)
		win.fillPolygon(new int[] {0 + triangleX, 0 + triangleX, triangleWidth + triangleX},
						new int[] {0 + triangleY, triangleHeight + triangleY, triangleHeight/2 + triangleY}, 
						3);
		
		//Changes the GameState depending on what is selected
		if(released && comp.gameState == 0) {
			if(selected == 0) {
				comp.gameState = 1;  //Multiplayer
				this.isMultiplayer = true;
			}
			if(selected == 1) {
				comp.gameState = 5;  //Singleplayer - Goes to difficulty page
				this.isMultiplayer = false;
				comp.reset();
			}
			if(selected == 2) {
				comp.gameState = 3;  //Rules
			}
			entered = false;
			released = false;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_DOWN && state == 0) {
			selected += 1;
			selected %= numberOfButtons;
		}
		
		if(e.getKeyCode() == e.VK_UP && state == 0) {
			selected -= 1;
			selected %= numberOfButtons;
			if(selected < 0) {
				selected = numberOfButtons - 1;
			}
		}
		
		if(e.getKeyCode() == e.VK_ENTER && state == 0 && entered == false) {
			entered = true;
		}
		
	}
	
	private int getState() {
		return this.state;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == e.VK_ENTER && state == 0 && entered) {
			released = true;
			entered = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
