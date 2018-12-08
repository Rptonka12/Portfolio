
public class Lab4Driver {

	public static void main(String[] args) {
		PizzaOrder order1 = new PizzaOrder();
		
		Pizza p1 = new Pizza(Pizza.LARGE, 1, 1, 0);
		Pizza p2 = new Pizza(Pizza.MEDIUM, 2, 0, 2);
				
		order1.addPizza(p1);
		order1.addPizza(p2);
		
		order1.printOrder();
		
		//copy
		PizzaOrder order2 = new PizzaOrder(order1);
		Pizza p3 = new Pizza(p1);
		Pizza p4 = new Pizza(p2);
		
		order2.addPizza(p3);
		order2.addPizza(p4);
		
		order2.changePizzaToppings(1, 6, 4, 0);
		
		order2.printOrder();
	}
}