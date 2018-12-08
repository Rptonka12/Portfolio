import java.util.Random;
import java.util.Scanner;

public class TweetCompressor {
	private String contents;
	private int length;
	
	public void setTweet()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter a Tweet: ");
		contents = keyboard.nextLine();
		System.out.println("You entered: " + contents);
		length = contents.length();
	}
	
	public void displayLength()
	{
		System.out.println("The length of your tweet is: " + length);
	}
	
	public void randomCompress()
	{
		while (length > 140)
		{	
			Random r = new Random();
			int indx = r.nextInt(length);
			while (contents.substring(indx, indx + 1).equals(" "))
			{
				indx = r.nextInt(length);
			}
			contents = contents.substring(0,indx) + contents.substring(indx + 1);
			length = contents.length();
		}
		System.out.println(contents);
		System.out.println("The compressed length of your Tweet is " + length + " characters.");
	}	
	
	public void vowelCompress()
	{
		while ((length > 140) && ((contents.contains( "a")) || (contents.contains("e")) || (contents.contains("i")) 
				|| (contents.contains("o")) || (contents.contains("u")) || (contents.contains( "A")) || (contents.contains("E"))
				|| (contents.contains("I")) || (contents.contains("O")) || (contents.contains("U"))))
		{
			Random r = new Random();
			int indx = r.nextInt(length);
			String letter = contents.substring(indx, indx+1);
			while (!((letter.equalsIgnoreCase("a")) || (letter.equalsIgnoreCase("e")) || (letter.equalsIgnoreCase("i")) || (letter.equalsIgnoreCase("o")) || (letter.equalsIgnoreCase("u"))))
			{
				indx = r.nextInt(length);
				letter = contents.substring(indx, indx+1);
			}
			contents = contents.substring(0,indx) + contents.substring(indx + 1);
			length = contents.length();
		}
		this.randomCompress();
	}
	
}