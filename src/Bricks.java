import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bricks {
	
	 public int map[][]; // 2D array for making bricks
	 public int brickWidth;
	 public int brickHeight;
	 
	 public Bricks(int row, int col) {
		 map = new int[row][col];
		 for (int i = 0; i < map.length; i++) { // loop for entering values for type of brick
			 for(int j = 0; j < map[0].length; j++) {
				 if (i == 1) {
					 map[i][j] = 2; // so that the bottom row has all persistent bricks
				 }else {
					 map[i][j] = 1; // rest all bricks will be non persistent
				 }
				 
				
				 
			 }
		 }
		 
		 brickWidth = 540/col; // brick size according to panel
		 brickHeight = 150/row;
	 }
	 
	 public void draw (Graphics2D g) { // method to draw the bricks
		 for (int i = 0; i < map.length; i++) { // loop to draw the bricks
			 for(int j = 0; j < map[0].length; j++) {
				 if(map[i][j] > 0) { // to check whether the space contains a brick or not
					 if (map[i][j] == 1) { // to make white non persistent bricks
						 g.setColor(Color.white);
						 g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);

						 g.setStroke(new BasicStroke(3));
						 g.setColor(Color.black);
						 g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);

					 } 
					 
					 if (map[i][j] == 2) { // to make blue persistent bricks
						 g.setColor(Color.cyan);
						 g.fillRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);

						 g.setStroke(new BasicStroke(3));
						 g.setColor(Color.black);
						 g.drawRect(j*brickWidth + 80, i*brickHeight + 50, brickWidth, brickHeight);
					 }
				 }
			 }
		 }

	 }
	 
	 public void setBrickValue ( int row, int col) { // to change the value of the bricks in the array when they are hit
		 if (map[row][col] == 1) {
				map[row][col] = 0;
			} else if (map[row][col] == 2) {
				map[row][col] = 1;
			}
	 }
	 
}
