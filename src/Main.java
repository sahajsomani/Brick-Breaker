import javax.swing.JFrame;

public class Main {
	
	public static void main (String [] args) {
		JFrame frame = new JFrame("Brick Breaker");
		Game game = new Game();
		frame.setBounds(0, 0, 700, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.setVisible(true);
	
		
	}

}
