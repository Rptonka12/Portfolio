import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PriorityQueueDriver {

	public static void main(String[] args) {
		//reading file and setting up output file
		String filename = args[0];
		String outputname = args[1];
		Scanner inputStream = null;
		PrintWriter pw;
		
		try { 
			inputStream = new Scanner(new FileInputStream(filename)); 
			pw = new PrintWriter(new FileOutputStream(outputname));
			
			//creating array of incoming planes to add to heap during simulation
			ArrayList<Aircraft> planes = new ArrayList<Aircraft>();
			int numRunways = inputStream.nextInt();
			while (inputStream.hasNext()) { planes.add(new Aircraft(inputStream.next(), 
					inputStream.nextInt(), inputStream.nextInt())); }
			
			//beginning of simulation
			int time = 0;
			int planesLanded = 0; //counter to know when to end simulation
			Heap mpq = new Heap();
			
			while (planesLanded < planes.size()){
				//step 1 - determine planes that are arriving and insert them to queue, then build heap
				for (int i = 0; i < planes.size(); i++){
					if (planes.get(i).arrivalTime == time) {
						mpq.insert(planes.get(i));
					}
				}
				mpq.buildMinHeap();
				
				//step 2 - for the number of runways available, if the queue is not empty, land planes and rebuild heaps
				for (int j = 0; j < numRunways; j++){
					if (!mpq.isEmpty()){
						Aircraft landing = mpq.heapExtractMin();
						pw.println(landing.flightID + "\t" + landing.arrivalTime + "\t" + landing.landingPriority + "\t" + time);
						pw.flush();
					}
				}
				planesLanded = mpq.getLandingCounter();
				time++;
			}
			pw.close();	
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot open file: " + filename);
			System.exit(0);
		}
	}
}