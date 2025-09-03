package myFarm.Models;

public class Animals {
	//=== Variables ===\\
	private final String name;
	private final Crops[] eatableCrops;
	private final Products[] producible;
	private final int size;
	private final int numEat;
	private int numAnimals;

	//=== Constructors ===\\
	public Animals(String name, Crops[] eatableCrops, Products[]producible, int size,  int numEat) {
		this.name = name;
		this.eatableCrops = eatableCrops;
		this.producible = producible;
		this.numEat = numEat;
		this.size = size;
	}

	//=== Functions ===\\
	public void eat() {
		int numToEat = this.numAnimals * this.numEat;

		for (Crops i: this.eatableCrops) {
			if (i.getNumCrops() >= numToEat) {
				i.setNumCrops(i.getNumCrops() - numToEat);
				numToEat = 0;
				break;
			}
			else {
				numToEat -= i.getNumCrops();
				i.setNumCrops(0);
			}
		}

		if (numToEat != 0) {
			numAnimals -= numToEat;
		}
	}
	public void produceProduct(Products product) {
		product.setNumProducts(product.getNumProducts() + (this.numAnimals * product.getNumProduce() * this.size));
	}

	//=== Getters and Setters ===\\
	public String getName() {
		return name;
	}
	public int getNumEat() {
		return numEat;
	}
	public Crops[] getEatableCrops() {
		return eatableCrops;
	}
	public int getNumAnimals() {
		return numAnimals;
	}
	public void setNumAnimals(int numAnimals) {
		this.numAnimals = numAnimals;
	}
	public Products[] getProducible() {
		return producible;
	}
	public int getSize() {
		return size;
	}
}
