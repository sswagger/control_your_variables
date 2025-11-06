package myFarm.Models;

public class Products {
	//=== Variables ===\\
	private final String name;
	private final double numProduce;
	private int numProducts;

	//=== Constructors ===\\
	public Products(String name, double numProduce) {
		this.name = name;
		this.numProduce = numProduce;
	}

	//=== Getters and Setters ===\\
	public String getName() {
		return name;
	}
	public int getNumProducts() {
		return numProducts;
	}
	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}
	public double getNumProduce() {
		return numProduce;
	}
}
