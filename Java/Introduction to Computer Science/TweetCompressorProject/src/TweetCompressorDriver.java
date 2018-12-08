/**
 * 
 * @author Rachel Pierstorff
 * Programming Project 3
 * October 27, 2016
 * Description: This program first shortens a Tweet entry by taking out random letters, then takes out only vowels.
 *
 */
import java.util.Scanner;
public class TweetCompressorDriver {

	public static void main(String[] args) {
		Scanner type = new Scanner(System.in);
		TweetCompressor test = new TweetCompressor();
		
		test.setTweet();
		test.displayLength();
		
		System.out.println("Enter if you would like to use the random compressor or the vowel compressor tool: ");
		String answer = type.next();
		switch (answer)
		{
			case "random":
				test.randomCompress();
				break;
			case "vowel":
				test.vowelCompress();
				break;
			default:
				System.out.println("Invalid input");
				break;
		}
	}
}
