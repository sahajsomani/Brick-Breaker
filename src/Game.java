import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener{
	
	private boolean start = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 7; // update time for the timer.
	
	private int paddlePosX = 350;
	
	// ball coordinates
	private int ballPosX = 385;
	private int ballPosY = 530;
	
	// ball velocity
	private int ballDirX = -1; 
	private int ballDirY = -2;
	
	private Bricks brick;
	private Sound audio;  // object of the sound class.
	
	
	public Game() {
		brick = new Bricks(3,7); // to draw 3 rows and 7 columns of bricks
		audio = new Sound();   
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		audio.backgroundMusic();    // to start background music
		
		
	}
	
	

	
	public void paint(Graphics g) {

		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, getWidth(), 592);
		
		// Drawing bricks using array
		brick.draw((Graphics2D) g);
		
		
		//BORDERS
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 700, 3); //top
		g.fillRect(0, 0, 3, 600); //left
		g.fillRect(697, 0, 3, 600); //right
		
		// Printing scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: " + score, 550, 30);
		
		//PADDLE
		g.setColor(Color.white);
		g.fillRect(paddlePosX, 550, 100, 8);
		
		//BALL
		g.setColor(Color.green);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if (totalBricks == 0) { // when the user wins the round
			audio.stopBackgroundMusic();   // added
			start = false;
			ballDirX = 0;
			ballDirY = 0;

			audio.sparkleMusic();   // to start winning sound
			totalBricks = -1;   // this condition has been created so that the sound file does not update when the game updates.
			

		}
		
		if (totalBricks == -1) {   // to draw the string when the user wins the game
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won!! " + score, 260, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press enter to restart ", 260, 350);
		}
		
		if(ballPosY > 570) {
			audio.stopBackgroundMusic();   // to stop background music
			start = false;
			ballDirX = 0;
			ballDirY = 0;
			
			audio.failureMusic(); // to start failure sound
			ballPosY = 570;  // this condition has been created so that the sound file does not update when the game updates.
			
		}
		
		if (ballPosY == 570) {  // to draw the string when the user loses the game
			g.setColor(Color.red);
			g.setFont(new Font("american typewriter", Font.BOLD, 30));
			g.drawString("Game Over. Your score is: " + score, 190, 300);
			
			g.setFont(new Font("american typewriter", Font.BOLD, 20));
			g.drawString("Press enter to restart ", 190, 350);
		}
		
		g.dispose();
		
			
		}
	
	public void update() { // this method contains everything that the actionListener should. 
		timer.start(); // timer starts when the first action is performed i.e. pressing the right or left key
		if (start) {
			
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(paddlePosX, 550, 100, 8))) {
				ballDirY = -ballDirY;
			}
			
			OUTER: for (int i = 0; i < brick.map.length; i++) { 
				for (int j = 0; j < brick.map[0].length; j++) { // for justifying the bricks into rows and columns 
					if(brick.map[i][j] > 0) {
						int brickX = j * brick.brickWidth + 80; // for x coordinate of every brick
						int brickY = i * brick.brickHeight + 50; // for y coordinate of every brick
						int brickWidth = brick.brickWidth; // width of the brick from brick class
						int brickHeight = brick.brickHeight; // height of the brick from brick class
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20); // to create an imaginary rectangle around the ball which detects intersection with other rectangular objects
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) { // for changing the numerical in the array when the ball hits the brick
							brick.setBrickValue(i, j);
							audio.playSound(); // sound whenever the ball hits the brick
							if(brick.map[i][j] == 0) { // the brick will disappear only when its white 
								totalBricks --;
								score += 5;
							}
							if (brick.map[i][j] == 1) { // to add to the score when the user hits a persistent brick.
								score += 5;
							}
							
							
							if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) { // to change velocity according to which side of the brick is hit
								ballDirX = -ballDirX;
							}else {
								ballDirY = -ballDirY;
							}
							break OUTER;
						}
					}
				}
			}
			ballPosX += ballDirX; // to move the ball if there is no intersection 
			ballPosY += ballDirY;
			
			if (ballPosX < 3) { // changing velocity when the ball hits the left side of the screen
				ballDirX = -ballDirX;
			}
			if (ballPosY < 3) { // changing velocity when the ball hits the top of the screen
				ballDirY = - ballDirY;
			}
			if (ballPosX > 677) { // changing velocity when the ball hits the right side of the screen
				ballDirX = -ballDirX;
			}
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(paddlePosX >= 600) { // to stop paddle to go out of the screen
				paddlePosX = 600;
			} else {
				moveRight();
			}
		} 
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(paddlePosX < 10) { // to stop paddle to go out of the screen
				paddlePosX = 10;
			} else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) { // to pause the game
			if(totalBricks != 0) {
				start = false;
			}

		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) { // to restart after winning or losing
			if(!start) {
				start = true;
				ballPosX = 120;
				ballPosY = 350;
				ballDirX = -1;
				ballDirY = -2;
				paddlePosX = 350;
				score = 0;
				totalBricks = 21;
				brick = new Bricks(3,7);
				audio.backgroundMusic();
				
				repaint();
						
			}
		}
	}
	
	public void moveRight() { // to move the paddle right
		start = true;
		paddlePosX += 20;
	}
	
	public void moveLeft() { // to move the paddle left
		start = true;
		paddlePosX -= 20;
	
	}
	
	
	
	
}// end
