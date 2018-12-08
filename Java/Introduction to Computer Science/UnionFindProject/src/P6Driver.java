/*
 * Rachel Pierstorff
 * Project 6
 * June 2, 2017
 * Part 3 and 4: static methods getting groups of students and testing pathological case; uses ArrayList of ArrayLists to print groups
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class P6Driver {
	
	public static void groupStudents(GroupCombine<String> mechanism){ 
		//inputting file
		String filename = "sampleInput2.txt";
		Scanner inputStream = null;
		try {
				inputStream = new Scanner(new FileInputStream(filename));
		} catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename);
			System.exit(0);
		}
		
		int reportedCount = inputStream.nextInt();
		inputStream.nextLine();
		String tempGroup = "";
		ArrayList<String> students = new ArrayList<>();
		
		//reading individual students
		while (true) {
			tempGroup = inputStream.nextLine();
			if (tempGroup.contains(",")) { break; }
			String name = tempGroup;
			students.add(name);
			mechanism.createGroup(name);	
		}
		
		assert(students.size()==reportedCount);
		
		//reading pairs of students
		String pair = tempGroup;
		while(inputStream.hasNextLine()) {
			System.out.println("Pair checking: " + pair);
			String name1 = pair.substring(0, pair.indexOf(","));
			String name2 = pair.substring(pair.indexOf(" ")+1); 
			mechanism.combineSet(name1, name2);
			
			pair = inputStream.nextLine();
		}
		//last pair
		System.out.println("Pair checking: " + pair);
		String name1 = pair.substring(0, pair.indexOf(","));
		String name2 = pair.substring(pair.indexOf(" ")+1); 
		mechanism.combineSet(name1, name2);
			
		//post-processing; arranging and printing groups
		ArrayList<ArrayList<String>> groups = new ArrayList<>();
		
		for(int i = 0; i < students.size(); i++){
			ArrayList<String> emptyGroup = new ArrayList<>();
			emptyGroup.add(mechanism.find(students.get(i)));
			if(!groups.contains(emptyGroup)){
				groups.add(emptyGroup); //adds all representatives to the groups list
			}
		}

		for(int i = 0; i < students.size(); i++){
			String repTarget = mechanism.find(students.get(i));
			if (!repTarget.equals(students.get(i))){
				for(int j = 0; j < groups.size(); j++){
					if (repTarget.equals(groups.get(j).get(0))){
						groups.get(j).add(students.get(i)); //adds the students to their respective representative's ArrayList
					}
				}
			}
		}
		
		for(int i = 0; i < groups.size(); i++){
			int groupIndex = i+1;
			System.out.println("Group " + groupIndex + ": " + groups.get(i).toString());
		}
	}
	
	public static void pathologicalCase(GroupCombine<Integer> mechanism, int n){
		ArrayList<Integer> numbers = new ArrayList<>();
		for (int i = 0; i < n; i++){
			numbers.add(i);
		}
		
		ArrayList<ArrayList<Integer>> groups = new ArrayList<>();
		
		//individual numbers
		for(int i = 0; i < numbers.size(); i++){
			mechanism.createGroup(numbers.get(i));
			
			ArrayList<Integer> reps = new ArrayList<Integer>();
			reps.add(numbers.get(i));
			groups.add(reps);
		}
				
		int roundCounter = 1;
		
		//pairs groups until all are in one group
		while (groups.size()>1){
			for(int i = 0; i < numbers.size(); i+=roundCounter*2){
				if(i+roundCounter < numbers.size()) {
					mechanism.combineSet(numbers.get(i), numbers.get(i+roundCounter));
				}
			}
			
			groups.clear();
			
			for(int i = 0; i < numbers.size(); i++){
				ArrayList<Integer> emptyGroup = new ArrayList<>();
				emptyGroup.add(mechanism.find(numbers.get(i)));
				if(!groups.contains(emptyGroup)){
					groups.add(emptyGroup);
				}
			}
			
			for(int i = 0; i < numbers.size(); i++){
				Integer repTarget = mechanism.find(numbers.get(i));
				if (!repTarget.equals(numbers.get(i))){
					for(int j = 0; j < groups.size(); j++){
						if (repTarget.equals(groups.get(j).get(0))){
							groups.get(j).add(numbers.get(i));
						}
					}
				}
			}
			roundCounter *= 2;
		}
	}

	public static void main(String[] args) {
		//Part 3 Student Group Analysis
//		groupStudents(new HashMapCombine<String>());
//		groupStudents(new UnionFind<String>());

		//Part 4 Pathological Case Analysis
		int[] ns = new int[5];
		ns[0] = 1000; ns[1] = 5000; ns[2] = 10000; ns[3] = 20000; ns[4] = 50000;
		
		//UnionFind
		for(int i = 0; i < ns.length; i++){
			long start = System.currentTimeMillis();
			pathologicalCase(new UnionFind<Integer>(), ns[i]);
			long end = System.currentTimeMillis();
			long time = end-start;
			System.out.println("UnionFind with " + ns[i] + " elements took " + time + " milliseconds");
		}
		
		//HashMapCombine
		for(int i = 0; i < ns.length; i++){
			long start = System.currentTimeMillis();
			pathologicalCase(new HashMapCombine<Integer>(), ns[i]);
			long end = System.currentTimeMillis();
			long time = end-start;
			System.out.println("HashMapCombine with " + ns[i] + " elements took " + time + " milliseconds");
		}
	}
}