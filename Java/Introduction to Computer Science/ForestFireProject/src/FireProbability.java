/**
 * @author Rachel Pierstorff
 * Project 3
 * April 21, 2017
 * This class calculates highest and fixed vegetation probabilities to calculate likelihood of forest burn
 * and includes the driver method.
 */
public class FireProbability {

	public static float fixedVegProbability(float probability){
		int fireBurnCount = 0;
		for (int i = 0; i < 10000; i++){
			if (new Forest(20,20, probability).depthFirstSearch()){
				fireBurnCount++;
			}
		}
		return (float) fireBurnCount/10000;
	}

	public static float highestVegProbability(){
		float desiredBurnProbability = 0.50f;
		float low = 0;
		float high = 1;
		float highestVegProbability = 0;
		
		for (int i = 0; i < 20; i++){
			highestVegProbability = (high + low)/2;
			if (fixedVegProbability(highestVegProbability) > desiredBurnProbability){
				high = highestVegProbability;
			} else
				low = highestVegProbability;
		}
		return highestVegProbability;
	}
	
	public static void main(String[] args) {
		//highestVegProbability test
		System.out.println("Highest vegetation probability that returns a 50% change of burn spreading: ");
		System.out.printf("%.3f", highestVegProbability());
		
		/*fixedVegProbability test
		float fp = 0.50f; //probability a cell is forest
		System.out.println("Burn probability at " + fp + " forest probability: ");
		System.out.printf("%.3f", fixedVegProbability(fp));	
		*/
		
		/* demonstrates depth and breadth algorithm implementations
		Forest f1 = new Forest(5, 5, 0.50f);
		
		System.out.println(f1.depthFirstSearch());
		System.out.println(f1.breadthFirstSearch());
		*/
	}
}