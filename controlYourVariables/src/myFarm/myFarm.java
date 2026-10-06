//=== PACKAGE ===\\
package myFarm;

//=== IMPORTED MODULES ===\\
import baseModels.jsonReader;
import mods.myFarm.myFarmMod;
import myFarm.Models.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
		inputString("Press Enter to Continue", "");
		clearScreen();
		printEquip();
	}
	private static void endMonth() {
		endMonthMod();
		if (!endMonthOverride) {
			// Player
			if (crops.get(0).getNumCrops() > 3) { //magicnumber
				crops.get(0).setNumCrops(crops.get(0).getNumCrops() - 3); //magicnumber
			}

			// Animals
			for (Animals a : animals) {
				int numAnimalsFeed = a.getNumAnimals();
				int feedNeeded = a.getSize() * a.getNumAnimals();
				// feed the animals
				for (Crops c : a.getEatableCrops()) {
					if (c.getNumCrops() > feedNeeded) {
						c.setNumCrops(c.getNumCrops() - feedNeeded);
						numAnimalsFeed = 0;
					}
					else {
						numAnimalsFeed -= (int)(c.getNumCrops() / a.getSize());
						c.setNumCrops(0);
					}
				}
				// check if there are more animals than there is food
				if (numAnimalsFeed > 0) {
					a.setNumAnimals(a.getNumAnimals() - numAnimalsFeed);

					System.out.println(dangerColor + "Your " + a + " are starving!" + neutral);
				}

				for (Products p : a.getProducible()) {
					if (!p.getRequireKill()) {
						p.setNumProducts((int)(p.getNumProducts() + (p.getNumProduce() * a.getSize())));
					}
				}
			}

			// todo: add traveling salesman
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
		System.out.println("Year: " + year);
		System.out.println("Month: " + months.get(monthI));
		System.out.println("Fields: " + fields);
		System.out.println("Plows: " + plows);
		System.out.println("Barns: " + barns);

		String format = "%s%-10s%10s%s";
		for (Crops crop : crops) {
			System.out.printf(String.format(format, infoColor, crop.getNumCrops() + " " + crop, crop.getNumPlanted() + " planted", neutral));
			System.out.println();
		}

		format = "%s%-20s%s";
		for (Animals animal : animals) {
			System.out.printf(String.format(format, infoColor, animal.getNumAnimals() + " " + animal,  neutral));
			System.out.println();
		}

		for (Products product : products) {
			System.out.printf(String.format(format, infoColor, product.getNumProducts() + " " + product, neutral));
			System.out.println();
		}
	}

	private static void year() {
		if (inputStringBool("Do you want instructions (y/n)", new String[]{"y"})) {
			System.out.println(infoColor + "You said yes!  // fixme" + neutral);  // Todo: add instructions
			continueGame();
		}

		while (!hasQuit) {
			monthI = 0; //magicnumber
			year += 1;

			// get number of available fields
			ArrayList<Integer> fieldRestrictions = new ArrayList<>();
			fieldRestrictions.add(plows);
			fieldRestrictions.add(fields);
			// find out how many animals can pull a plow
			int numBigAnimals = 0;
			for (Animals animal : animals) {
				if (animal.getSize() > 5) { //magicnumber
					numBigAnimals += 1; //magicnumber
				}
			}
			fieldRestrictions.add(numBigAnimals);
			Collections.sort(fieldRestrictions);
			int availFields = fieldRestrictions.getFirst();

			// plant crops
			while (monthI < 4) {
				// find out if the user wants to plant crops this month
				if (inputStringBool("Do you want to plant your crops (y/n)", new String[]{"y"})) {
					// find out if there are any fields that are "plowable"; if not, then fail
					if (availFields > 0) {
						// loop through crops
						for (Crops crop : crops) {
							// plant each one if user has any
							int numPlant = 0;
							if (crop.getNumCrops() > 0) {
								numPlant = inputIntMax("How many " + crop + " do you want to plant (you have " + availFields + " available fields)?", availFields, "");
							}
							crop.plantCrops(numPlant);
							availFields -= numPlant;
							// if they used all available fields then break
							if (availFields == 0) {
								break;
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
			// crops need to grow for 6 months
			while (monthI < currMonth+6) {
				water += inputInt("How much water do you want to give to your crops (you have " + fields + " fields)?", "");
				endMonth();

				if (water <= (monthI - currMonth) * 75) { //magicnumber
					System.out.println(dangerColor + "Your crops are starting to dry up!" + neutral);
				}
				else if (water >= (monthI - currMonth) * 125) { //magicnumber
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
				// see if user wants to kill animals
				boolean killFood = inputStringBool("Do you want to kill any animals for food?", new String[]{"y"});
				if (killFood) {
					// loop through animals
					for (Animals a : animals) {
						int killAnimal = inputIntMax("How many " + a + " do you want to kill?", a.getNumAnimals(), ""); // fixme: add help
						if (killAnimal > 0) {
							a.setNumAnimals(a.getNumAnimals() - killAnimal);
							for (Products p : a.getProducible()) {
								p.setNumProducts((int)(p.getNumProducts() + (p.getNumProduce() * a.getNumAnimals())));
							}
						}
					}
				}
				if (inputStringBool("Do you want to go to the market?", new String[]{"y"})) {
					market();
				}
				else {
					break;
				}
				endMonth();
			}

			// end year
			while (monthI < 12) {
				int foodEaten = 0;
				while (foodEaten < 5) {
					if (crops.get(0).getNumCrops() > 0) { // magicnumber
						int numEat = inputIntMax("How much " + crops.get(0) + " do you want to eat?", crops.get(0).getNumCrops(), "");
						crops.get(0).setNumCrops(numEat); // magicnumber
						foodEaten += numEat;
					}

					for (Products p : products) {
						if (p.getEatable()) {
							int numEat = inputIntMax("How many " + p + " do you want to eat?", p.getNumProducts(), "");
							p.setNumProducts(p.getNumProducts() - numEat);
							foodEaten += numEat;
						}
					}
				}
				endMonth();
			}

			hasQuit = inputStringBool("Do you want to quit and report to the king? (y/n)", new String[]{"y"});
		}
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
		ArrayList<ArrayList<String>> newProducts = data.readData(dataPath, "products", new ArrayList<>(Arrays.asList("name", "numProduce", "requireKill", "editable")));
		for (ArrayList<String> product : newProducts) {
			products.add(new Products(product.get(0), Double.parseDouble(product.get(1)), Boolean.parseBoolean(product.get(2)), Boolean.parseBoolean(product.get(3))));
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

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Good Bye");  // fixme: save data when user exits
		}));
	}
}
