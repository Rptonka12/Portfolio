/**
 * @author Rachel Pierstorff
 * Project 1
 * January 15, 2017
 */
public class EnvironmentDriver {

	public static void main(String[] args) throws Exception {
		
		String filename = "GameOfLife1.txt";
		Environment e1 = new Environment(filename);
		e1.runSimulation();
	}

}