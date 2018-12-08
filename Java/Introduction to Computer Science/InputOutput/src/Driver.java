import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class Driver {

	public static void main(String[] args) {
		Widget w = new Widget("w1", 3);
		
		try{
			PrintWriter pw = new PrintWriter(new FileOutputStream("widget.txt", true)); //sending to a file without exception throws a file not found
			pw.println(w);
			pw.flush();
			pw.close(); //have to do this to ensure data is written to file
			
			ObjectOutputStream oos = 
				new ObjectOutputStream(new FileOutputStream("widget.bin"));
			
			oos.writeObject(w);
			pw.close();
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("widget.txt"));
			
			Widget wIn = (Widget) ois.readObject();
			System.out.println(wIn);
			ois.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't open file" + e.getMessage());
		} catch (IOException e) {
			System.out.println("couldn't make the OOS for some reason" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("uknown class: " + e.getMessage());
			e.printStackTrace();
		} 
	}
}