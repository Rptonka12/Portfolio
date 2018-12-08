import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class HashTableSet<T> implements Set<T> {

	//buckets of hash table
	private ArraySet<T>[] data;
	private long scale, shift, bigPrime; //compression function elements
	private int size; //how many items stored in set (not same as length of the array)
	
	public HashTableSet(int tableSize){ //number of buckets
		bigPrime = 109345121;
		Random r = new Random();
		scale = 1 + (r.nextLong() % 100); //1 - bigPrime-1
		shift = r.nextLong() % bigPrime;
		size = 0;
		
		data = (ArraySet<T>[]) new ArraySet[tableSize];
	}
	
	public HashTableSet(){ this(17); } //default hash table with 17 buckets
	
	@Override
	public Iterator<T> iterator() {
		return new HSIterator();
	}
	
	private class HSIterator implements Iterator<T>{

		int bucket;
		Iterator<T> bucketIterator;
		
		public HSIterator(){
			//loops through buckets until finds a useful iterator
			for(bucket = 0; bucket<data.length; bucket++){
				if (data[bucket] != null){
					bucketIterator = data[bucket].iterator();
					if(bucketIterator.hasNext()){
						return;
					} 
				}
			}
			bucketIterator = null; //no buckets have items to iterate
		}
		
		@Override public boolean hasNext() { return bucketIterator != null; }

		@Override
		public T next() {
			T ret = bucketIterator.next();
			if(bucketIterator.hasNext()) { return ret; } //more in this bucket
			//just like constructor
			for (bucket = bucket+1; bucket < data.length; bucket++){
				if (data[bucket] != null){
					bucketIterator = data[bucket].iterator();
					if(bucketIterator.hasNext()){ return ret; }
				}
			}
			bucketIterator = null;
			return ret;
		}
	}
	
	private int whichBucket(T t){
		return (int)((t.hashCode() * scale + shift) % bigPrime) % data.length;
	}
	
	@Override
	public boolean insert(T val) {
		int bucket = whichBucket(val);
		if(data[bucket] == null){ //first entry in bucket
			data[bucket] = new ArraySet<T>();
		}
		boolean ret = data[bucket].insert(val);
		if(ret) { size++; } //actually added it
		
		float loadFactor = (float)(size)/data.length;
		if (loadFactor > 0.75) { resize(2*data.length + 1); } //make sure length is odd
		
		return ret;
	}

	@Override
	public boolean remove(T val) {
		int bucket = whichBucket(val);
		if(data[bucket]==null){
			return false; //no work done for removing if not there
		}
		boolean ret = data[bucket].remove(val);
		if (ret) { size--; } 
		return ret;
	}

	@Override
	public boolean contains(T val) {
		int bucket = whichBucket(val);
		if(data[bucket]==null){
			return false; //not in set
		}
		return data[bucket].contains(val);
	}
	
	private void resize(int newSize){
		ArrayList<T> oldData = new ArrayList<>();
		for(T val : this) { oldData.add(val); }
		data = (ArraySet<T>[]) new ArraySet[newSize];
		size = 0;
		for(T val : oldData) { insert(val); }
	}

	@Override public int size() { return size; }
	@Override public boolean empty() { return size==0; }
}