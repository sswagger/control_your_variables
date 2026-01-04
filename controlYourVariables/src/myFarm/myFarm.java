//=== PACKAGE ===\\
package myFarm;

//=== IMPORTED MODULES ===\\
import baseModels.jsonReader;
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
	private static boolean hasQuit = false;
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
		endMonthMod();
		if (!endMonthOverride) {
			// todo: end the month normally
			monthI++;
		}
	}
	private static void market() {

	}
	private static void fail(String message) {
		System.out.println(dangerColor + "Oops! " + message + neutral);
		System.out.println(infoColor + "You can choose any of the following:" + neutral);
		System.out.println(infoColor + "1. Continue anyway" + neutral);
		System.out.println(infoColor + "2. Go to the market" + neutral);
		System.out.println(infoColor + "3. Quit and report to king" + neutral + "\n");

		switch (inputString("Your Choice", new String[] {"1", "2", "3"})) {
			case "1":
				return;
			case "2":
				market();
			case "3":
				hasQuit = true;
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
		if (inputStringBool("Do you want instructions (y/n)", new String[]{"y"})) {
			System.out.println(infoColor + "You said yes!  // fixme" + neutral);  // Todo: add instructions
			continueGame();
		}

		while (!hasQuit) {
			monthI = 0;
			year += 1;
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

			// plant crops
			while (monthI < 4) {
				if (inputStringBool("Do you want to plant your crops (y/n)", new String[]{"y"})) {
					clearScreen();
					if (availFields > 0) {
						for (Crops crop : crops) {
							if (crop.getNumCrops() > 0) {
								crop.plantCrops(inputInt("How many " + crop + " do you want to plant (you have " + availFields + " available fields)?"));
							}
						}
					}
					else {
						fail("You don't have any available fields!");
					}
					break;
				}
				endMonth();
			}

			// water crops
			int currMonth = monthI;
			int water = 0;
			while (monthI < currMonth+6) {
				for (Crops crop : crops) {
					if (crop.getNumPlanted() > 0) {
						water += inputInt("How much water do you want to give to your crops (you have " + fields + " fields)?");
					}
				}
				endMonth();

				if (water <= (monthI - currMonth) * 75) {
					System.out.println(dangerColor + "Your crops are starting to dry up!" + neutral);
				}
				else if (water >= (monthI - currMonth) * 125) {
					System.out.println(dangerColor + "Your crops are starting to drown!" + neutral);
				}
			}

			// harvest crops
			while (monthI < 11) {
				if (inputStringBool("Do you want to harvest your crops? (y/n)", new String[]{"y"})) {
					for (Crops crop : crops) {
						crop.harvestCrops();
					}
					break;
				}
				endMonth();
			}

			// go to market
			while (monthI < 12) {
				if (inputStringBool("Do you want to go to the market?", new String[]{"y"})) {
					market();
				}
				else {
					break;
				}
				endMonth();
			}

			// end month
			while (monthI < 12) {
				// todo: eat food...
				endMonth();
			}
		}
		// Todo: finish the year
	}

	//=== MAIN FUNCTION ===\\
	public static void main(String[] args) {
		String dataPath = System.getProperty("user.dir") + "\\controlYourVariables\\src\\myFarm\\Data\\";
		if (myFarmMod.dataPath != null && !myFarmMod.dataPath.isEmpty()) {
			dataPath = myFarmMod.dataPath;
		}
		jsonReader data = new jsonReader(dataPath);

		try {
			monthI = data.readData(data.getPath(), "monthI");
			year = data.readData(data.getPath(), "yearI");
			fields = data.readData(data.getPath(), "fields");
			plows = data.readData(data.getPath(), "plows");
			barns = data.readData(data.getPath(), "barns");
		}
		catch (Exception e) {
			System.out.println("Cannot read simple data, check file format");
			System.out.println(e.getClass() + ": " + e.getMessage());
			return;
		}

		crops = new ArrayList<>();
		ArrayList<ArrayList<String>> newCrops = data.readData(dataPath, "crops", new ArrayList<>(Arrays.asList("name", "profit")));
		for (ArrayList<String> crop : newCrops) {
			crops.add(new Crops(crop.get(0), Integer.parseInt(crop.get(1))));
		}

		products = new ArrayList<>();
		ArrayList<ArrayList<String>> newProducts = data.readData(dataPath, "products", new ArrayList<>(Arrays.asList("name", "numProduce")));
		for (ArrayList<String> product : newProducts) {
			products.add(new Products(product.get(0), Integer.parseInt(product.get(1))));
		}

		animals = new ArrayList<>();
		ArrayList<ArrayList<String>> newAnimals = data.readData(dataPath, "animals", new ArrayList<>(Arrays.asList("name", "crops", "products", "size")));
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
