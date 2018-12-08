
public class ShuffleCipher implements MessageEncoder {

	private int shuffles;
	
	public ShuffleCipher(int n){
		shuffles = n;
	}
	
	@Override
	public void encode(String plaintext) {
		String completed = plaintext;
		for (int i = 0; i < shuffles; i++){
			completed = new String (shuffle(plaintext));
		}
		System.out.println(completed);
	}
	
	private char[] shuffle(String message){
		String firstHalf = message.substring(0, (message.length()/2+1));
		String secondHalf = message.substring((message.length()/2+1), message.length());
		char[] shuffled = new char[message.length()];
		
		for (int whole = 0, half = 0; half < firstHalf.length(); whole+=2, half++){
			shuffled[whole] = firstHalf.charAt(half);
		}
		for (int whole = 1, half = 0; half < secondHalf.length(); whole+=2, half++){
			shuffled[whole] = secondHalf.charAt(half);
		}
		return shuffled;
	}
}