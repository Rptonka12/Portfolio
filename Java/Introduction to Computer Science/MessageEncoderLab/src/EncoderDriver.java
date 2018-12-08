
public class EncoderDriver {

	public static void main(String[] args) {
		SubstitutionCipher substitute = new SubstitutionCipher(3);
		substitute.encode("hello");
		
		ShuffleCipher shuffle = new ShuffleCipher(1);
		shuffle.encode("abcdefghi");
	}
}