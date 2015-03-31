import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Contains methods to play the game of life. Use class "Driver" to take in
 * command line arguments and run the actual game.
 * 
 * @author Brandon Fowler
 */
public class Life {
	private MySparseArray currentGen; // stores current generation of live nodes
	private MySparseArray neighbors; // used to keep track of number of
										// neighbors
	private MySparseArray nextGen; // stores next generation of nodes
	private int numGens; // number of generations to play the game of life
	private String inputFile; // String of path to input file
	private String outputFile; // String of path to output file

	/**
	 * Constructor for creating the game of life. Use method play() in order to
	 * play the game of life. Final outcome will be output to the specified
	 * file.
	 * 
	 * @param numGens
	 *            - Specifies the total number of generations to play the game
	 * @param inputFile
	 *            - Path to file containing initial start conditions.
	 * @param outputFile
	 *            - Path to file to write final outcome of game of life.
	 */
	public Life(int numGens, String inputFile, String outputFile) {
		this.numGens = numGens;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		currentGen = new MySparseArray(false); // alive holds a value of true
												// and dead holds false
		neighbors = new MySparseArray(new Integer(0)); // numbers of neighbors
														// holds value > 0
		nextGen = new MySparseArray(false); // alive holds value of true and
											// dead holds false
	}

	/**
	 * Used to read starting conditions from the file.
	 */
	private void readFromFile() {
		Path filePath = Paths.get(inputFile);

		try (BufferedReader reader = Files.newBufferedReader(filePath,
				Charset.forName("UTF-8"));) {
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split(",");
				currentGen.setValue(Integer.parseInt(splitLine[0]),
						Integer.parseInt(splitLine[1]), true);
			}
		} catch (IOException e) {
			System.out.println("Could not find file provided.");
		}
	}

	/**
	 * Used to output final outcome of game of life to specified file.
	 */
	private void outputToFile() {
		Path filePath = Paths.get(outputFile);

		try (BufferedWriter writer = Files.newBufferedWriter(filePath,
				Charset.forName("UTF-8"));) {
			RowIterator iterator = currentGen.iterateRows();
			while (iterator.hasNext()) {
				ElemIterator elem = iterator.next();
				while (elem.hasNext()) {
					MatrixElem mElem = elem.next();
					writer.write(mElem.rowIndex() + "," + mElem.columnIndex());
					writer.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("Unable to write to the provided file");
		}

	}

	/**
	 * Used to determine each nodes number of neighbors.
	 */
	private void getNeighbors() {
		RowIterator iterator = currentGen.iterateRows();
		while (iterator.hasNext()) {
			ElemIterator elem = iterator.next();
			while (elem.hasNext()) {
				MatrixElem mElem = elem.next();
				int row = mElem.rowIndex();
				int col = mElem.columnIndex();
				int numNeighbors = 0;
				for (int i = -1; i < 2; i++) { // loops through row above,
												// actual row, and row below
					for (int j = -1; j < 2; j++) { // loops through column left,
													// actual column, coloumn
													// right
						if (!currentGen.elementAt(row + i, col + j).equals(
								false)) // element is alive, add 1 to neighbor
										// total
							numNeighbors += 1;
						else { // element is not alive, add 1 to its total
								// number of neighbors
							Integer currentNeighbors = (Integer) neighbors
									.elementAt(row + i, col + j);
							neighbors.setValue(row + i, col + j,
									currentNeighbors + 1);
						}
					}
				}
				neighbors.setValue(row, col, numNeighbors - 1); // -1 to account
																// for counting
																// itself as a
																// neighbor
			}
		}
	}

	/**
	 * Used to determine the next generation of nodes that are alive or dead.
	 */
	private void setNextGeneration() {
		RowIterator iterator = neighbors.iterateRows();
		while (iterator.hasNext()) {
			ElemIterator elem = iterator.next();
			while (elem.hasNext()) {
				MatrixElem mElem = elem.next();
				int row = mElem.rowIndex();
				int col = mElem.columnIndex();
				Integer numNeighbors = (Integer) neighbors.elementAt(row, col);
				if (numNeighbors == 0 || numNeighbors == 1) { // dies if
																// neighbors are
																// 0 or 1
					nextGen.setValue(row, col, false);
					continue;
				}
				if (numNeighbors >= 4) { // dies if neighbors are 4 or more
					nextGen.setValue(row, col, false);
					continue;
				}
				if (numNeighbors == 3) { // lives if neighbors are 3
					nextGen.setValue(row, col, true);
					continue;
				}
				if (numNeighbors == 2) { // if neighbors are 2, lives or dies
					if (!currentGen.elementAt(row, col).equals(false)) { // currently
																			// alive
																			// so
																			// lives
						nextGen.setValue(row, col, true);
					} else { // currently dead so stays dead
						nextGen.setValue(row, col, false);
					}
				}
			}
		}
		currentGen = nextGen; // set current to next
		nextGen = new MySparseArray(false); // reset next
		neighbors = new MySparseArray(0); // reset neighbors
	}

	/**
	 * Public function used to play the game of life.
	 */
	public void playGame() {
		int generations = 1;
		readFromFile();
		while (generations <= numGens) {
			getNeighbors();
			setNextGeneration();
			generations += 1;
		}
		outputToFile();
	}

}
