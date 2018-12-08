/**
 *
 * Created: Sat Mar 15 2009
 *
 * @author Kenrick Mock
 * @version 1
 * 
 *          Modified Sat Jan 9, 2016
 * @author Susanne Sherba
 * @version 1.1
 */

public class PizzaOrder {

	private static final int MAX_NUM_PIZZAS = 10;
	private Pizza[] pizza;
	private int numPizzas;

	public PizzaOrder() {
		pizza = new Pizza[MAX_NUM_PIZZAS];
		numPizzas = 0;
	}
	
	public PizzaOrder(PizzaOrder copy){
		this.pizza = new Pizza[copy.pizza.length];
		for (int i = 0; i < numPizzas; i++){
			this.pizza[i] = new Pizza(copy.pizza[i]);
		this.numPizzas = copy.numPizzas;
		}
	}
	
	/*
	 * Adds a pizza to the order
	 */
	public void addPizza(Pizza p) {
		if (numPizzas >= MAX_NUM_PIZZAS) {
			System.out.println("Too many pizzas for this order");
		} else {
			pizza[numPizzas] = p;
			numPizzas++;
		}

	}

	/*
	 * returns the number of pizzas in the order
	 * 
	 */

	public int getNumPizzas() {
		return numPizzas;
	}

	/*
	 * Changes the toppings on a specific pizza precondition: 0 <= pizzaNumber <
	 * numPizzas
	 * 
	 */
	public void changePizzaToppings(int pizzaNumber, int numCheeseToppings, int numHamToppings,
			int numPepperoniToppings) {
		if (pizzaNumber < 0 || pizzaNumber > numPizzas) {
			System.out.println("Invalid pizza number");
		} else {
			pizza[pizzaNumber].setNumCheeseToppings(numCheeseToppings);
			pizza[pizzaNumber].setNumHamToppings(numHamToppings);
			pizza[pizzaNumber].setNumPepperoniToppings(numPepperoniToppings);
		}
	}

	/*
	 * Prints the entire pizza order
	 */
	public void printOrder() {
		for (int i = 0; i < numPizzas; i++) {
			System.out.println(pizza[i]);
		}
		System.out.println("Order Total: $" + calcTotal());
	}

	/*
	 * Calculates the total cost of an order
	 */
	public double calcTotal() {
		double total = 0;
		for (int i = 0; i < numPizzas; i++) {
			total += pizza[i].calcCost();
		}
		return total;
	}

}
