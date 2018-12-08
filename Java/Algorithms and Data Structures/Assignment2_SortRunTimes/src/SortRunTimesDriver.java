import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class SortRunTimesDriver {

	public static void main(String[] args) {
		//reading file
		String filename = args[0];
		Scanner inputStream = null;
		
		try { inputStream = new Scanner(new FileInputStream(filename)); }
		catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename);
			System.exit(0);
		}
		
		ArrayList<String> words = new ArrayList<String>();
		while (inputStream.hasNext()) { words.add(inputStream.next()); }

		for (int n = 8; n < 8193; n=n*2){
			int iter = Math.max(4, 8192/n);
			
			//loop for insertion sort
			ArrayList<String> insertOutput = new ArrayList<>();
			CpuTimer insertTimer = new CpuTimer();
			for (int a = 0; a < iter; a++){
				insertOutput = InsertionSort(words, n);
			}
			double insertElapsed = insertTimer.getElapsedCpuTime();
			float insertionTime = (float) insertElapsed/iter;
		
			//insert validations
			if (AlphaSorted(words)) { 
				System.out.println("Input array returned true for alpha sorted");
				break;
			}
			if (!AlphaSorted(insertOutput)) { 
				System.out.println("Output array returned false for alpha sorted");
				break;
			}
			int inHashI = CheckHash(words, n); int outHashI = CheckHash(insertOutput, n);
			if (inHashI != outHashI) { 
				System.out.println("CheckHash gives different results");
				break;
			}
			
			//loop for merge sort
			ArrayList<String> mergeOutput = new ArrayList<>();
			CpuTimer mergeTimer = new CpuTimer();
			for (int b = 0; b < iter; b++){
				mergeOutput = new ArrayList<>(words.subList(0, n));
				MergeSort(mergeOutput, 0, n-1);
			}
			double mergeElapsed = mergeTimer.getElapsedCpuTime();
			float mergeTime = (float) mergeElapsed/iter;
			
			//merge validations
			if (AlphaSorted(words)) { 
				System.out.println("Input array returned true for alpha sorted");
				break;
			}
			if (!AlphaSorted(mergeOutput)) { 
				System.out.println("Output array returned false for alpha sorted");
				break;
			}
			int inHashM = CheckHash(words, n); int outHashM = CheckHash(mergeOutput, n);
			if (inHashM != outHashM) { 
				System.out.println("CheckHash gives different results");
				break;
			}
			
			System.out.println("Avg time for n = " + n + ": Insertion Sort " + insertionTime + 
					", Merge Sort " + mergeTime);
		}
	}
	
	//program components
	static boolean AlphaSorted(ArrayList<String> list){
		for (int i = 0; i < list.size()-1; i++){
			if (list.get(i).compareTo(list.get(i+1)) > 0) { return false; }
		}
		return true;
	}
	
	static int CheckHash(ArrayList<String> words, int count){
		int xor = words.get(0).hashCode();
		for (int i = 1; i < count; i++){
			xor = xor ^ words.get(i).hashCode();
		}
		return xor;
	}
	
	static ArrayList<String> InsertionSort(ArrayList<String> words, int length){
	    ArrayList<String> sublist = new ArrayList<>(words.subList(0, length));
		
		for (int j = 1; j < sublist.size(); j++){
			String key = sublist.get(j);
			int i = j-1;
			while ((i >= 0) && ((sublist.get(i).compareTo(key)) > 0 )) {
				sublist.set(i+1, sublist.get(i));
				i = i-1;
			}
			sublist.set(i+1, key);
		}
		return sublist;
	}
	
	static void MergeSort(ArrayList<String> list, int p, int r){
		if (p < r) {
			int q = (p+r)/2;
			MergeSort(list, p, q);
			MergeSort(list, q+1, r);
			Merge(list, p, q, r); 	
		}
	}
	static void Merge(ArrayList<String> list, int p, int q, int r) {
		int n1 = q - p + 1; 
		int n2 = r - q;
		ArrayList<String> left = new ArrayList<String>(); 
		ArrayList<String> right = new ArrayList<String>();
		
		for (int i = 0; i < n1; i++) { left.add(list.get(p + i)); }
		for (int j = 0; j < n2; j++) { right.add(list.get(q + 1 + j)); }
		
		int i = 0; int j = 0;
		for (int k = p; k <= r; k++) {
			if (i < left.size() && j < right.size()) {
				if (left.get(i).compareTo(right.get(j)) < 0) {
					list.set(k, left.get(i));
					i = i + 1;
				} else {
					list.set(k, right.get(j));
					j = j + 1;
				}
			} else if ((i >= left.size()) && j < right.size()) {
				list.set(k, right.get(j));
				j = j + 1;
			} else if ((j >= right.size()) && i < left.size()) {
				list.set(k, left.get(i));
				i = i + 1;
			} else {
				System.out.println("both indices out");
			}
		}
	}
}