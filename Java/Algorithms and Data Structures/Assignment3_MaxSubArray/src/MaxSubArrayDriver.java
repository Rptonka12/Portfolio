import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxSubArrayDriver {

	public static void main(String[] args) {
		//reading file
		String filename = args[0];
		Scanner inputStream = null;
		
		try { inputStream = new Scanner(new FileInputStream(filename)); }
		catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename);
			System.exit(0);
		}
		ArrayList<Long> list = new ArrayList<Long>();
		while (inputStream.hasNext()) { list.add(inputStream.nextLong()); }
		
		//loop for recursive divide and conquer
		int iter1 = Math.max(1, (4096/list.size()));
		CpuTimer recurTimer = new CpuTimer();
		long recur_sum = 0;
		for (int a = 0; a < iter1; a++){
			recur_sum = setup(list, 0, list.size()-1);
		}
		double recurElapsed = recurTimer.getElapsedCpuTime();
		float recurTime = (float) recurElapsed/iter1;
		
		System.out.println(list.size() + ", \"R\"," + recurTime + ", " + recur_sum);
		
		//loop for kadane's algorithm
		int iter2 = Math.max(1, 131072/list.size());
		CpuTimer kadaneTimer = new CpuTimer();
		long kadanes_sum = 0;
		for (int b = 0; b < iter2; b++){
			kadanes_sum = kadanes(list);
		}
		double kadaneElapsed = kadaneTimer.getElapsedCpuTime();
		float kadaneTime = (float) kadaneElapsed/iter2;
		
		System.out.println(list.size() + ", \"K\"," + kadaneTime + ", " + kadanes_sum);
	}
	
	static long maxCrossingSub(ArrayList<Long> array, int low, int mid, int high){
		//crossing sub method implemented from textbook pseudocode
		//left sum
		long sum1 = 0;
		long left_sum = array.get(mid);
		long max_left = -1;
		for (int i = mid; i >= low; i--){
			sum1 = sum1 + array.get(i);
			if (sum1 > left_sum) {
				left_sum = sum1;
				max_left = i;
			}
		}
		
		//right sum
		long sum2 = 0;
		long right_sum = array.get(mid+1); 
		long max_right = -1;
		for (int j = mid + 1; j <= high; j++){
			sum2 = sum2 + array.get(j);
			if (sum2 > right_sum) {
				right_sum = sum2;
				max_right = j;
			}
		}
		return left_sum + right_sum;
	}

	static long setup(ArrayList<Long> a, int low, int high) {
		//solution if noncircular
		long first_max = maxSubarray(a, low, high); 
		
		//solution if circular
		int mid = (low + high) / 2;
		ArrayList<Long> b = new ArrayList<Long>();
		int i = 0;
		//creates a new array offset (starts at middle)
		for(int k = mid+1; k <= (mid+a.size()); k++){
			b.add(i, a.get(k%a.size()));
			i++;
		}
		long second_max = maxSubarray(b, low, high);
		
		return Math.max(first_max, second_max);
	}
	
	static long maxSubarray(ArrayList<Long> array, int low, int high){
		//maxSubarray as implemented from textbook pseudocode
		long leftSum;
		long rightSum;
		long crossSum;
		
		if (low==high)
			return array.get(low);
		else {
			int mid = (low + high) / 2;
			leftSum = maxSubarray(array, low, mid);
			rightSum = maxSubarray(array, mid+1, high);
			crossSum = maxCrossingSub(array, low, mid, high);
		}
		
		if ((leftSum >= rightSum) && (leftSum >= crossSum))
			return leftSum;
		else if ((rightSum >= leftSum) && (rightSum >= crossSum))
			return rightSum;
		else
			return crossSum;
	}
	
	static long kadanes(ArrayList<Long> array){ 
		//need to find smallest (most negative) subarray; remaining elements = circular sum
		long totalSum = 0;
		long maxSumSoFar = 0;
		long maxSumToK = 0;
		long minSumSoFar = 0;
		long minSumToK = 0;
		
		for (int k = 0; k < array.size(); k++){ 
			totalSum = totalSum + array.get(k);
			maxSumToK = maxSumToK + array.get(k);
			minSumToK = minSumToK + array.get(k);
			
			//keeps track of both maximum and minimums
			if (maxSumToK < 0)
				maxSumToK = 0;
			if (maxSumSoFar < maxSumToK)
				maxSumSoFar = maxSumToK;
			if (minSumToK >= 0)
				minSumToK = 0;
			if (minSumSoFar > minSumToK)
				minSumSoFar = minSumToK;
		}
		
		//checking if circular array sum is more than normal sum
		long circularSum = totalSum - minSumSoFar;
		if (circularSum > maxSumSoFar)
			maxSumSoFar = circularSum;
		
		return maxSumSoFar;
	}
}