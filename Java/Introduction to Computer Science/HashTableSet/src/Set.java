
public interface Set<T> extends Iterable<T> { //if something is a set, it is also iterable
	boolean insert(T val); //returns boolean to know whether or not the value was actually added
	boolean remove(T val); //return true if we actually removed val
	boolean contains(T val); 
	
	int size();
	boolean empty();
}
