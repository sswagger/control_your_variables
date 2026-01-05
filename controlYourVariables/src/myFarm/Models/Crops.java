package myFarm.Models;

public class Crops {
	//=== Attributes ===\\
	private final String name;
	private final int profit;
	private int numCrops;
	private int numPlanted;

	//=== Constructors ===\\
	public Crops(String name, int profit) {
		this.name = name;
		this.profit = profit;
	}
	public Crops(String name, int profit, int numCrops, int numPlanted) {
		this.name = name;
		this.profit = profit;
		this.numCrops = numCrops;
		this.numPlanted = numPlanted;
	}

	//=== Methods ===\\
	public void plantCrops(int numPlanted) {
		this.numCrops -= numPlanted;
		this.numPlanted = numPlanted;
	}
	public void harvestCrops() {
		this.numCrops += this.numPlanted * this.profit;
		this.numPlanted = 0;
	}
	@Override
	public String toString() {
		return this.name;
	}

	//=== Getters ===\\
	public int getNumCrops() {
		return this.numCrops;
	}
	public int getNumPlanted() {
		return this.numPlanted;
	}
	public String getName() {
		return this.name;
	}

	//=== Setters ===\\
	public void setNumCrops(int numCrops) {
		this.numCrops = numCrops;
	}
}
