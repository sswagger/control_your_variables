//=== PACKAGE ===\\
package myFarm;

//=== IMPORTED MODULES ===\\
import mods.myFarm.myFarmMod;
import myFarm.Models.*;
import java.util.ArrayList;
import java.util.Arrays;

//=== CLASS ===\\
public class myFarm extends myFarmMod {
	//=== VARIABLES ===\\
	private static final ArrayList<String> months =  new ArrayList<>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
	private static int monthI;
	private static int year;
	private static int fields;
	private static int plows;
	private static int barns;
	private static ArrayList<Crops> crops;
	private static ArrayList<Products> products;
	protected static ArrayList<Animals> animals;

	//=== FUNCTIONS ===\\
	private static void continueGame() {
		inputString("Press Enter to Continue");
		clearScreen();
		printEquip();
	}
	private static void endMonth() {
		if (endMonthOverride) {
			endMonthMod();
		}
		else {
			endMonthMod();
			// todo: end the month normally
			monthI++;
		}
	}
	private static void printEquip() {
		String format = "%-50s%s";
		for (Crops crop : crops) {
			System.out.printf(String.format(format, infoColor + crop.getNumCrops() + " " + crop, crop.getNumPlanted() + " planted" + neutral + "\n"));
		}
		// todo: add products and animals
	}
	private static void year() {
		boolean hasQuit = false;

		if (inputStringBool("Do you want instructions (y/n)", new String[]{"y"})) {
			System.out.println(infoColor + "You said yes!" + neutral);  // Todo: add instructions
			continueGame();
		}
		while (!hasQuit) {
			int availFields = 0;
			for (Animals animal : animals) {
				if (animal.getSize() > 5) {
					if (animal.getNumAnimals() <= plows) {
						if (animal.getNumAnimals() <= fields) {
							availFields = animal.getNumAnimals();
						}
						else {
							availFields = plows;
						}
					}
					else {
						if (fields <= plows) {
							availFields = fields;
						}
					}
					break;
				}
			}

			while (monthI < 4) {
				if (inputStringBool("Do you want to plant your crops (y/n)", new String[]{"y"})) {
					clearScreen();
					for (Crops crop : crops) {
						if (crop.getNumCrops() > 0) {
							crop.plantCrops(inputInt("How many " + crop + " do you want to plant (you have " + availFields + " available fields)?"));
						}
					}
				}
				endMonth();
			}
			break;  // fixme: testing purposes
		}
		// Todo: finish the year
	}

	//=== MAIN FUNCTION ===\\
	public static void main(String[] args) {
		String dataPath = System.getProperty("user.dir") + "\\controlYourVariables\\src\\myFarm\\";
		if (myFarmMod.dataPath != null && !myFarmMod.dataPath.isEmpty()) {
			dataPath = myFarmMod.dataPath;
		}

		try {
			monthI = readData(dataPath, "monthI");
			year = readData(dataPath, "yearI");
			fields = readData(dataPath, "fields");
			plows = readData(dataPath, "plows");
			barns = readData(dataPath, "barns");
		}
		catch (Exception e) {
			System.out.println("Cannot read simple data, check file format");
			System.out.println(e.getClass() + ": " + e.getMessage());
			return;
		}

		crops = new ArrayList<>();
		ArrayList<ArrayList<String>> newCrops = readData(dataPath, "crops", new ArrayList<>(Arrays.asList("name", "profit")));
		for (ArrayList<String> crop : newCrops) {
			crops.add(new Crops(crop.get(0), Integer.parseInt(crop.get(1))));
		}

		products = new ArrayList<>();
		ArrayList<ArrayList<String>> newProducts = readData(dataPath, "products", new ArrayList<>(Arrays.asList("name", "numProduce")));
		for (ArrayList<String> product : newProducts) {
			products.add(new Products(product.get(0), Integer.parseInt(product.get(1))));
		}

		animals = new ArrayList<>();
		ArrayList<ArrayList<String>> newAnimals = readData(dataPath, "animals", new ArrayList<>(Arrays.asList("name", "crops", "products", "size")));
		for (ArrayList<String> animal : newAnimals) {
			ArrayList<Crops> cropList = new ArrayList<>();
			ArrayList<Products> productList = new ArrayList<>();
			for (int i = 0; i < animal.get(1).length(); i++) {
				char cropI = animal.get(1).charAt(i);
				try {
					if (cropI != ',') {
						cropList.add(crops.get(Integer.parseInt(cropI + "")));
					}
				}
				catch (NumberFormatException e) {
					System.out.println(dangerColor + "Cannot read crops! " + cropI + neutral);
				}
			}
			for (int i = 0; i < animal.get(2).length(); i++) {
				char productI = animal.get(2).charAt(i);
				try {
					if (productI != ',') {
						productList.add(products.get(Integer.parseInt(productI + "")));
					}
				}
				catch (NumberFormatException e) {
					System.out.println(dangerColor + "Cannot read products! " + productI + neutral);
				}
			}
			animals.add(new Animals(animal.get(0), cropList, productList, Integer.parseInt(animal.get(3))));
		}

		if (infoColor == null || infoColor.isEmpty()) {
			infoColor = rgbText(255, 255, 0) + rgbBackground(60, 140, 0);
		}
		if (inputColor == null || inputColor.isEmpty()) {
			inputColor = rgbText(250, 250, 250) + rgbBackground(55, 150, 0);
		}
		if (dangerColor == null || dangerColor.isEmpty()) {
			dangerColor = rgbText(255, 255, 0) + rgbBackground(210, 0, 0);
		}
		crops.getFirst().setNumCrops(80);

		year();
	}
}
