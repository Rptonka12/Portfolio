/**
 * 
 * Pizza class that stores information about a single pizza.
 *
 * Created: Sat Mar 15 2009
 *
 * @author Kenrick Mock
 * @version 1 Modified Sat Jan 9, 2016
 * @author Susanne Sherba
 * @version 1.1
 */

public class Pizza {

	public static final String SMALL = "Small";
	public static final String MEDIUM = "Medium";
	public static final String LARGE = "Large";

	private String size;
	private int numCheeseToppings;
	private int numHamToppings;
	private int numPepperoniToppings;

	// no argument constructor
	public Pizza() {
		size = "Large";
		numCheeseToppings = 1;
		numHamToppings = 0;
		numPepperoniToppings = 0;
	}

	/*
	 * Constructor takes 4 arguments: size of pizza, num of cheese toppings, 
	 * num of ham toppings, num of pepperoni toppings
	 */
	public Pizza(String pizzaSize, int cheese, int ham, int pepperoni) {
		if (!(pizzaSize == SMALL || pizzaSize == MEDIUM || pizzaSize == LARGE)) {
			// if size is invalid, set size to SMALL
			size = SMALL;
		} else {
			size = pizzaSize;
		}
		numCheeseToppings = cheese;
		numHamToppings = ham;
		numPepperoniToppings = pepperoni;
	}
	
	//copy constructor
	public Pizza(Pizza copy){
		this.size = copy.getSize();
		this.numCheeseToppings = copy.getNumCheeseToppings();
		this.numHamToppings = copy.getNumHamToppings();
		this.numPepperoniToppings = copy.getNumPepperoniToppings();
	}

	public String getSize() {
		return size;
	}

	public int getNumCheeseToppings() {
		return numCheeseToppings;
	}

	public int getNumHamToppings() {
		return numHamToppings;
	}

	public int getNumPepperoniToppings() {
		return numPepperoniToppings;
	}

	public void setNumCheeseToppings(int cheese) {
		numCheeseToppings = cheese;
	}

	public void setNumHamToppings(int ham) {
		numHamToppings = ham;
	}

	public void setNumPepperoniToppings(int pepperoni) {
		numPepperoniToppings = pepperoni;
	}

	/* 
	 * Calculates the cost of the pizza
	 */
	public double calcCost() {
		double baseCost = 10;
		if (size.equals(SMALL)) {
			baseCost = 10;
		} else if (size.equals(MEDIUM)) {
			baseCost = 12;
		} else if (size.equals(LARGE)) {
			baseCost = 14;
		} else {
			System.out.println("Error, unknown size.");
			return 0;
		}
		return baseCost + (numHamToppings + numCheeseToppings + numPepperoniToppings) * 2;
	}

	public String toString() {
		return "Size: " + size + ", Cheese Toppings: " + numCheeseToppings + " Pepperoni Toppings: "
				+ numPepperoniToppings + " Ham Toppings: " + numHamToppings + ". Cost: " + calcCost();
	}

}
