import java.util.ArrayList;
import java.util.Iterator;

public class ArraySet<T> implements Set<T>{

	private ArrayList<T> data;
	
	public ArraySet(){
		data = new ArrayList<T>();
	}
	
	private int indexOf(T val){
		//data.indexOf(val);
		for(int i = 0; i < data.size(); i++){
			if (data.get(i).equals(val)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public Iterator<T> iterator() {
		//arraylist is iterable
		return data.iterator();
	}

	@Override
	public boolean insert(T val) {
		if (contains(val)){
			return false; //val is already in set so you don't need to insert
		}
		data.add(val);
		return true;
	}

	@Override
	public boolean remove(T val) {
		int index = indexOf(val);
		if(index == -1) return false; //nothing to do since the value isn't in the set
		else {
			data.set(index, data.get(data.size()-1)); //copy last element to val's spot
			data.remove(data.size()-1); //and remove last element
			return true;
		}
	}

	@Override
	public boolean contains(T val) {
		//data.contains(val);
		//contained if index isn't -1
		return indexOf(val) != -1;
	}

	@Override public int size() { return data.size(); }
	@Override public boolean empty() { return data.isEmpty(); }
}