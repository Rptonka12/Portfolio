import java.io.Serializable;

public class Gadget implements Serializable {
	private int i;
	private double d;
	private String s;
	
	public Gadget (int i, double d, String s) {
		this.i = i;
		this.d = d;
		this.s = s;
	}
	
	@Override
	public String toString(){
		return " gadget: " + i + " " + d + " " + s;
	}
}