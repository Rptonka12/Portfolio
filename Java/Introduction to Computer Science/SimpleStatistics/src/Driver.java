import java.util.Random;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		//make an array of doubles of a user defined size
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("How big of an array do you want?");
		int N = keyboard.nextInt();
		
		double[] numbers = createRandomArray(N); //don't have to put class name because it's in the same class
		printArray(numbers);
		
		//sum and average
		arraySum(numbers);
		System.out.println("Average is: " + arraySum(numbers)/numbers.length);
		
		//max and min
		double max = numbers[0];
		int maxPos = 0;
		for (int i = 1; i < numbers.length; i++){
			if (numbers[i]>max) {
				max = numbers[i];
				maxPos = i;
			}
		}
		System.out.println("Maximum is: " + max + " at position " + maxPos);
		
	}

	public static double[] createRandomArray(int N){ //static because you don't need a driver object to run the method
		double[] numbers = new double[N];
		Random rand = new Random();
		
		for (int i = 0; i < numbers.length; i++){
			numbers[i] = rand.nextDouble()*100;	
		}
		return numbers;
	}
	
	public static void printArray(double[] numbers){
		for (int i = 0; i < numbers.length; i++){
			System.out.print(numbers[i] + "  ");
		}
		System.out.println();
	}
	
	public static double arraySum(double[] numbers){
		double sum = 0;
		for(int i = 0; i < numbers.length; i++){
			sum += numbers[i];
		}
		return sum;
	}
}
