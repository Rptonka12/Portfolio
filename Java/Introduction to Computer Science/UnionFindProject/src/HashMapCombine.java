/*
 * Rachel Pierstorff
 * Project 6
 * June 2, 2017
 * Part 2: Generic class that implements ADT with strategy described for part 1
 */
import java.util.ArrayList;
import java.util.HashMap;

public class HashMapCombine<T> implements GroupCombine<T> {
	
	private HashMap<T, ArrayList<T>> tree;
	
	public HashMapCombine(){
		tree = new HashMap<T, ArrayList<T>>();
	}
	
	@Override
	public void createGroup(T data) {
		ArrayList<T> newGroup = new ArrayList<>();
		newGroup.add(data);
		tree.put(data, newGroup);
	}

	@Override
	public T find(T data) {
		return tree.get(data).get(0);
	}
	
	@Override
	public void combineSet(T a, T b) {
		ArrayList<T> group1 = tree.get(find(a));
		ArrayList<T> group2 = tree.get(find(b));
		
		if (group1.size() > group2.size()){
			for (int i = 0; i < group2.size(); i++){
				tree.remove(group2.get(i)); 
				group1.add(group2.get(i));
				tree.put(group2.get(i), group1); 
			}
		} else {
			for (int i = 0; i < group1.size(); i++){
				tree.remove(group1.get(i));
				group2.add(group1.get(i));
				tree.put(group1.get(i), group2);
			}
		}
	}
}