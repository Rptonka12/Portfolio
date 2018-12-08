import java.util.Random;

public class HashTableDriver {

	public static void main(String[] args) {
		HashTableSet<Integer> hts = new HashTableSet<>();
		
		Random r = new Random();
		for (int i = 0; i < 20; i++){
			hts.insert(r.nextInt(1000000));
			for (int j : hts){
				System.out.print(j + " ");
			}
			System.out.println();
		}
		
		HashTableSet<DumbClass> hsd = new HashTableSet<>();
		hsd.insert(new DumbClass(10,3.14f, new String("hello"), false));
		hsd.insert(new DumbClass(10,3.14f, new String("hello"), false));
		System.out.println(hsd.size());
		
		
	}
}