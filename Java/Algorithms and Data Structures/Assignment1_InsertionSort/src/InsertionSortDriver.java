import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class InsertionSortDriver {

	public static void main(String[] args) {
		//reading file
		String filename = args[0];
		Scanner inputStream = null;
		
		try { 
			inputStream = new Scanner(new FileInputStream(filename));
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename);
			System.exit(0);
		}
		
		ArrayList<String> words = new ArrayList<String>();
		while (inputStream.hasNext()) {
			words.add(inputStream.next());
		}
		
		//sorting
		for (int j = 1; j < words.size(); j++){
			String key = words.get(j);
			int i = j-1;
			while ((i >= 0) && ((words.get(i).compareTo(key)) > 0 )) {
				words.set(i+1, words.get(i));
				i = i-1;
			}
			words.set(i+1, key);
		}
		
		for (int i = 0; i < words.size(); i++){ System.out.println(words.get(i)); }
	}
}