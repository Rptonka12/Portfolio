
public class SubstitutionCipher implements MessageEncoder {

	private int shift;
	
	public SubstitutionCipher(int shift){
		this.shift = shift;
	}
	
	@Override
	public void encode(String plaintext) {
		System.out.println(shift(plaintext));
	}
	
	private char[] shift(String message){
		char[] charMessage = message.toCharArray();
		for(int i = 0; i < charMessage.length; i++){
			charMessage[i] = (char) (charMessage[i] + shift);
		}
		return charMessage;	
	}
}