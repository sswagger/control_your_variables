package myFarm.Models;

import java.util.ArrayList;


public class Animals {
	//=== Attributes ===\\
	private final String name;
	private final ArrayList<Crops> eatableCrops;
	private final ArrayList<Products> producible;
	private final int size;
	private int numAnimals;

	//=== Constructors ===\\
	public Animals(String name, ArrayList<Crops> eatableCrops, ArrayList<Products> producible, int size) {
		this.name = name;
		this.eatableCrops = eatableCrops;
		this.producible = producible;
		this.size = size;
	}

	//=== Methods ===\\
	public void eat() {
		int numToEat = this.numAnimals * this.size;

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
			numAnimals -= numToEat / this.numAnimals;
		}
	}
	public void produceProduct(Products product) {
		product.setNumProducts((int) (product.getNumProducts() + (this.numAnimals * product.getNumProduce() * this.size)));
	}
	@Override
	public String toString() {
		return this.name;
	}

	//=== Getters ===\\
	public String getName() {
		return name;
	}
	public ArrayList<Crops> getEatableCrops() {
		return eatableCrops;
	}
	public int getNumAnimals() {
		return numAnimals;
	}
	public ArrayList<Products> getProducible() {
		return producible;
	}
	public int getSize() {
		return size;
	}

	//=== Setters ===\\
	public void setNumAnimals(int numAnimals) {
		this.numAnimals = numAnimals;
	}
}
