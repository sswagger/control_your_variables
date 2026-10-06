package myFarm.Models;

public class Products {
	//=== Variables ===\\
	private final String name;
	private final double numProduce;
	private int numProducts;
	private final boolean requireKill;
	private final boolean eatable;

	//=== Constructors ===\\
	public Products(String name, double numProduce, boolean requireKill, boolean eatable) {
		this.name = name;
		this.numProduce = numProduce;
		this.numProducts = 0;
		this.requireKill = requireKill;
		this.eatable = eatable;
	}
	public Products(String name, double numProduce, int numProducts, boolean requireKill, boolean eatable) {
		this.name = name;
		this.numProduce = numProduce;
		this.numProducts = numProducts;
		this.requireKill = requireKill;
		this.eatable = eatable;
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
	public boolean getRequireKill() {
		return requireKill;
	}
	public boolean getEatable() {
		return eatable;
	}

	//=== Setters ===\\
	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}
}
