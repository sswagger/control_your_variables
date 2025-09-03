package myFarm.Models;

public class Crops {
	//=== Variables ===\\
	private final String name;
	private int NumCrops;
	private int NumPlanted;

	//=== Constructors ===\\
	public Crops(String name) {
		this.name = name;
	}

	//=== Getters and Setters ===\\
	public int getNumCrops() {
		return NumCrops;
	}
	public void setNumCrops(int numCrops) {
		NumCrops = numCrops;
	}
	public int getNumPlanted() {
		return NumPlanted;
	}
	public void setNumPlanted(int numPlanted) {
		NumPlanted = numPlanted;
	}
	public String getName() {
		return name;
	}
}
