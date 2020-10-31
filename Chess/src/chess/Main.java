package chess;

/**
 * @author Ashish Kedia and Adarsh Mohata
 *
 */

/**
 * This is the Main Class of our project. All GUI Elements are declared,
 * initialized and used in this class itself. It is inherited from the JFrame
 * Class of Java's Swing Library.
 * 
 */

public class Main {
	public static Controller Mainboard;
	public static void main(String[] args) {
		// Setting up the board
		Mainboard = new Controller();
		Mainboard.setVisible(true);
		Mainboard.setResizable(false);
	}
}