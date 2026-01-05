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
	public Products(String name, double numProduce, int numProducts) {
		this.name = name;
		this.numProduce = numProduce;
		this.numProducts = numProducts;
	}

	//=== Methods ===\\
	@Override
	public String toString() {
		return this.name;
	}

	//=== Getters ===\\
	public String getName() {
		return name;
	}
	public int getNumProducts() {
		return numProducts;
	}
	public double getNumProduce() {
		return numProduce;
	}

	//=== Setters ===\\
	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}
}
