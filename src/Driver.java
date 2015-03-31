/**
 * Driver class used to run the game of life. Takes in command line arguments as
 * follows:
 * 
 * Argument 1: Input file containing initial start conditions
 * 
 * Argument 2: Output file where final results of game will be written to
 * 
 * Argument 3: Number of generations to play the game
 *
 *
 */
public class Driver {

	public static void main(String[] args) {
		String inputFile = args[0];
		String outputFile = args[1];
		Integer generations = Integer.parseInt(args[2]);
		Life game = new Life(generations, inputFile, outputFile);
		game.playGame();
	}
}
