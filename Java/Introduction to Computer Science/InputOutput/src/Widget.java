import java.io.Serializable;

public class Widget implements Serializable { //compiler figures out how to rewrite the object as bytes
	private String name;
	private Gadget[] gadgets;
	
	public Widget(String name, int n){
		this.name = name;
		gadgets = new Gadget[n];
		for (int i = 0; i < n; i++) {
			gadgets[i] = new Gadget(i, i, "gadget: " + i);
		}
	}
	
	@Override
	public String toString(){
		String ret = "Widget: " + name;
		for (int i = 0; i < gadgets.length; i++){
			ret += gadgets[i];
		}
		return ret;
	}
}