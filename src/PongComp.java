import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongComp extends GameDriverV3 implements KeyListener {
	Rectangle background = new Rectangle(0, 0, 800, 600);
	Paddle left, right;
	PowerPaddle power;
	EvilPaddle evil;
	Ball b1, b2, b3; // b1 is visible, b2 is invisible
	ComputerPaddle computer;
	Smasher smash;

	double DIFFICULTY = 2; // If difficulty is whole, the computer will get it every time. If not, rounding
						   // errors will make it more likely to miss.

	/* The gameState integer controls which screen the game is in:
	 *  0 - Intro 
	 *  1 - Multiplayer
	 *  2 - Winner Screen 
	 *  3 - Rules 
	 *  4 - Singleplayer 
	 *  5 - Difficulty Selector
	 */
	
	int gameState = 0;
	PongScore score = new PongScore();
	PongIntro intro = new PongIntro(this);
	PongRules rules = new PongRules(this);
	PongDifficulty diff = new PongDifficulty(this);

	public PongComp() {
		super();
		reset();
	}

	public void reset() {
		// Gets executed whenever the reset function is called
		// Reinitializes all of the dynamic objects
		computer = new ComputerPaddle(10, this.getHeight() / 2 - 50);
		left = new Paddle(10, this.getHeight() / 2 - 50, KeyEvent.VK_W, KeyEvent.VK_S);
		right = new Paddle(770, this.getHeight() / 2 - 50, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
		b1 = new Ball(370, 270, 20, 20, Color.WHITE, true, score);
		b2 = new Ball(370, 270, 10, 10, Color.RED, false);
		b2.dx = b1.dx * DIFFICULTY;
		b2.dy = b1.dy * DIFFICULTY;
		b2.c1 = new Color(255, 0, 0);
		
		if(score.scoreLeft < score.scoreRight) {
			evil = new EvilPaddle(500, 460 - left.y, "Left");
		} else if (score.scoreLeft > score.scoreRight) {
			evil = new EvilPaddle(300, 460 - right.y, "Right");
		} else {
			evil = new EvilPaddle(500, 900, "");
		}
		
		power = new PowerPaddle(380, 150);
		smash = new Smasher();

		this.addKeyListener(left);
		this.addKeyListener(right);
		if (gameState == 0)
			this.addKeyListener(intro);
		if (gameState == 5)
			this.addKeyListener(diff);
		this.addKeyListener(rules);
		this.addKeyListener(this);
	}

	@Override
	public void draw(Graphics2D win) {
		// The gameState controls the objects on the screen:

		if (gameState == 0) {
			// SPLASH SCREEN (handled by the intro class)
			intro.draw(win, this);

		} else if (gameState == 1) {
			// MAIN GAME SCREEN - Multiplayer

			// Executes the draw functions of each object
			win.setColor(Color.BLACK);
			win.fill(background);
			left.moveAndDraw(win);
			right.moveAndDraw(win);
			score.draw(win, b1, left, right, this);
			smash.draw(win, b1, this, left, right, computer);
			b1.moveAndDraw(win);
			power.moveAndDraw(win, b1, left, right);
			evil.moveAndDraw(win, b1, left, right);

			// Handles collisions - makes the direction change with the location it hits the
			// paddle
			if (b1.collisionWithPaddle(right)) {
				if (right.smashable) {
					b1.speed += 10;
				}
				b1.dx = -b1.speed * Math.abs(Math.cos((2 * Math.PI / 4) / (b1.height + right.height) * (right.y - b1.y + right.height) - Math.PI / 4));
				b1.dy = -b1.speed * Math.sin(
						(2 * Math.PI / 3) / (b1.height + right.height) * (right.y - b1.y + right.height) - Math.PI / 3);
				b1.lastHit = "right";
				right.smashable = false;
			}
			if (b1.collisionWithPaddle(left)) {
				if (left.smashable) {
					b1.speed += 10;
				}
				b1.dx = b1.speed * Math.abs(Math.cos(
						(2 * Math.PI / 4) / (b1.height + left.height) * (left.y - b1.y + left.height) - Math.PI / 4));
				b1.dy = -b1.speed * Math.sin(
						(2 * Math.PI / 3) / (b1.height + left.height) * (left.y - b1.y + left.height) - Math.PI / 3);
				b1.lastHit = "left";
				left.smashable = false;
			}

			// Draws Player Names
			win.setColor(Color.WHITE);
			win.setFont(new Font("Yu Gothic", Font.BOLD, 20));
			win.drawString("Player 1", 50, 50);
			win.drawString("Player 2", 650, 50);

		} else if (gameState == 2) {
			// WINNER SCREEN
			win.setColor(Color.BLACK);
			win.fill(background);

			// Controls the fonts of the text on the winner screen
			win.setColor(Color.WHITE);
			win.setFont(new Font("Yu Gothic", Font.PLAIN, 100));
			win.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			// Writes the text, which varies depending on who wins
			if (intro.isMultiplayer) {
				if (score.winner == "Left") {
					win.drawString("Player 1 Wins!", 60, 210);
					win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
					win.drawString("Press enter to restart", 210, 380);
				} else if (score.winner == "Right") {
					win.drawString("Player 2 Wins!", 60, 210);
					win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
					win.drawString("Press enter to restart", 210, 380);
				} else {
					win.drawString("error", 0, 0); // Just in case the unexpected happens
				}
			} else {
				if (score.winner == "Left") {
					win.drawString("Computer wins", 40, 210);
					win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
					win.drawString("Press enter to restart", 210, 380);
				} else if (score.winner == "Right") {
					win.drawString("You win!", 200, 210);
					win.setFont(new Font("Yu Gothic", Font.PLAIN, 40));
					win.drawString("Press enter to restart", 210, 380);
				}
			}

		} else if (gameState == 3) {
			// RULES SCREEN
			rules.draw(win, this);

		} else if (gameState == 4) {
			// MAIN GAME SCREEN - Singleplayer

			// Executes the draw functions of each object
			win.setColor(Color.BLACK);
			win.fill(background);
			right.moveAndDraw(win);
			computer.moveAndDraw(win, b2, diff, b1);
			score.draw(win, b1, computer, right, this);
			smash.draw(win, b1, this, left, right, computer);
			b1.moveAndDraw(win);
			b2.moveAndDraw(win);
			power.moveAndDraw(win, b1, computer, right);

			// Handles collisions - makes the direction change with the location it hits the
			// paddle. Also, the ball moves faster upon collision
			if (b1.collisionWithPaddle(right)) {
				if (right.smashable) {
					b1.speed += 10;
				}
				b1.speed += 1 / b1.speed;
				b1.dx = -b1.speed * Math
						.abs(Math.cos((2 * Math.PI / 3) / (b1.height + right.height) * (right.y - b1.y + right.height)
								- Math.PI / 3));
				b1.dy = -b1.speed * Math.sin(
						(2 * Math.PI / 3) / (b1.height + right.height) * (right.y - b1.y + right.height) - Math.PI / 3);
				b1.lastHit = "right";
				b2 = new Ball(b1.x, b1.y, b1.width, b1.height, Color.RED, false);
				b2.dx = Math.round(b1.dx) * DIFFICULTY;
				b2.dy = Math.round(b1.dy) * DIFFICULTY;
				right.smashable = false;
			}

			// Creates a new invisible ball when colliding with the power paddle
			if (b1.collisionWithPaddle(power)) {
				b2 = new Ball(b1.x, b1.y, b1.width, b1.height, Color.RED, false);
				b2.dx = b1.dx * DIFFICULTY;
				b2.dy = b1.dy * DIFFICULTY;
			}

			if (b1.collisionWithPaddle(computer)) {
				if (computer.smashable) {
					b1.speed += 10;
				}

				b1.speed += 1 / b1.speed;
				b1.dx = b1.speed * Math.abs(Math.cos(
						(2 * Math.PI / 3) / (b1.height + left.height) * (left.y - b1.y + left.height) - Math.PI / 3));
				b1.dy = -b1.speed * Math.sin(
						(2 * Math.PI / 3) / (b1.height + left.height) * (left.y - b1.y + left.height) - Math.PI / 3);
				b1.lastHit = "left";
				b2.dx = Math.round(b1.dx) * DIFFICULTY;
				b2.dy = Math.round(b1.dy) * DIFFICULTY;
				computer.smashable = false;
			}

			// Draws Player Names
			win.setColor(Color.WHITE);
			win.setFont(new Font("Yu Gothic", Font.BOLD, 20));
			win.drawString("Computer", 50, 50);
			win.drawString("Human", 650, 50);

		} else if (gameState == 5) {
			diff.draw(win, this, computer);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (gameState == 2) {
			if (e.getKeyCode() == e.VK_ENTER) {
				if (intro.isMultiplayer) {
					gameState = 1;

				} else {
					gameState = 4;
				}
				score.scoreLeft = 0;
				score.scoreRight = 0;
				reset();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
