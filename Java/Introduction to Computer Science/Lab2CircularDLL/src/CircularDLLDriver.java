import java.util.Random;

public class CircularDLLDriver {

	public static void main(String[] args) {
		CircularDoublyLinkedList<Integer> l = new CircularDoublyLinkedList<>();
		Random r = new Random();
		
		for (int i = 1; i <= 10; i++){
			l.addAfterCursor(i);
			l.advanceCursor(1);
			System.out.print(" " + l.getValue());
		}
		
		System.out.println();
		
		while(l.getSize()>0){
			int tick = r.nextInt(5);
			l.advanceCursor(tick);
			System.out.println("removed: " + l.getValue() + " list size now: " + l.getSize());
			l.deleteCursor();
		}
	}
}