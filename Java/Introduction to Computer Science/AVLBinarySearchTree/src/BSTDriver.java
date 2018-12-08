import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class BSTDriver {

	public static void main(String[] args) {
		/*
		Random r = new Random();
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		int n = 80000;
		
		//test 1
		long start1 = System.currentTimeMillis();
		for (int i = 0; i < n; i++){
			bst.insert(i);
		}
		long end1 = System.currentTimeMillis();
		long time1 = end1 - start1;
		System.out.println("Test one with " + n + " elements took " + time1 + " milliseconds to complete");
		
		//test 2
		BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
		long start2 = System.currentTimeMillis();
		for (int i = 0; i < n; i++){
			int j = r.nextInt(n);
			bst2.insert(j);
		}
		long end2 = System.currentTimeMillis();
		long time2 = end2 - start2;
		System.out.println("Test two with " + n + " random elements took " + time2 + " milliseconds to complete");
		*/
		/*
		 * Test 1 takes longer to perform because with each addition, 
		 * the code must cycle through to the end of the list since it always adds a larger element.
		 * Test 2 on the other hand, it more often adding an element to the beginning of the tree 
		 * (higher up on the tree) as opposed to test 1 which always adds a leaf.
		 */
		
		//War and Peace input
		/*
		String filename1 = "WarandPeace.txt";
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new FileInputStream(filename1));
		} catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename1);
			System.exit(0);
		}
		BinarySearchTree<String> bst3 = new BinarySearchTree<>();
		
		long start3 = System.currentTimeMillis();
		while (inputStream.hasNextLine()){
			String word = inputStream.nextLine();
			bst3.insert(word);
		}
		long end3 = System.currentTimeMillis();
		long time3 = end3 - start3;
		System.out.println("Test 3 with text of War and Peace took " + time3 + " milliseconds to complete");
		//took 1239 milliseconds
		
		//English words input
		String filename2 = "wordlist.txt";
		Scanner inputStream2 = null;
		try {
			inputStream2 = new Scanner(new FileInputStream(filename2));
		} catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename2);
			System.exit(0);
		}
		BinarySearchTree<String> bst4 = new BinarySearchTree<>();
		
		long start4 = System.currentTimeMillis();
		while (inputStream2.hasNextLine()){
			String word = inputStream2.nextLine();
			bst4.insert(word);
		}
		long end4 = System.currentTimeMillis();
		long time4 = end4 - start4;
		System.out.println("Test 3 with text of War and Peace took " + time4 + " milliseconds to complete");
		//took 227332 milliseconds
		 * 
		 */
		
		AVLTree<Integer> avl = new AVLTree<>();
		avl.insert(1);
		avl.insert(2);
		avl.insert(3);
		avl.insert(6);
		avl.insert(5);
		avl.insert(8);
		avl.insert(7);
		avl.insert(9);
		System.out.println(avl);
		avl.remove(6);
		avl.remove(2);
		avl.remove(1);
		avl.remove(3);
		avl.remove(8);
		avl.remove(7);
		System.out.println(avl);
		
	}
}