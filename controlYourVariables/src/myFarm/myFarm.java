//=== PACKAGE ===\\
package myFarm;

//=== IMPORTED MODULES ===\\
import baseModels.jsonReader;
import mods.myFarm.myFarmMod;
import myFarm.Models.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//=== CLASS ===\\
public class myFarm extends myFarmMod {
	//=== VARIABLES ===\\
	private static final ArrayList<String> months = new ArrayList<>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
	private static int monthI;
	private static int year;
	private static int fields;
	private static int plows;
	private static int barns;
	private static double money;
	private static boolean hasQuit = false;
	private static final Random rand = new Random();
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
						numAnimalsFeed -= (c.getNumCrops() / a.getSize());
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
						p.setNumProducts((int)(p.getNumProducts() + (p.getNumProduce() * a.getSize() * a.getNumAnimals())));
					}
				}

				if (monthI == 2) { // magicnumber
					int randBirth = rand.nextInt(a.getSize()) + 1;
					if (a.getNumAnimals() > 2 && a.getNumAnimals() % randBirth == 0) {
						if (a.getSize() >= 3) { // magicnumber
							a.setNumAnimals(a.getNumAnimals() + 1); // magicnumber
						}
						else {
							a.setNumAnimals(a.getNumAnimals() + ((1 / a.getSize()) * 10)); // magicnumber
						}
						System.out.println(infoColor + "Your " + a + " had a birth!" + neutral);
						continueGame();
					}
				}
			}

			// todo: add traveling salesman
			// todo: add wizard spells
			monthI++;
			printEquip();
		}
	}
	private static Integer bargain(String name, int num, double price) {
		int numCustomers = rand.nextInt(10) + 5; // magicnumber
		int numSold = 0;

		while (num > 0 && numCustomers > 0) {
			int randTrade = rand.nextInt(3);
			if (randTrade == 0) {
				int numBuy = rand.nextInt(num);
				if (inputStringBool("A customer wants to buy " + numBuy + " " + name + ". Do you accept (y/n)", new String[]{"y"})) {
					num -= numBuy;
					numSold += numBuy;
					money += numBuy * price;
					numCustomers--;
				}
			}
			else if (randTrade == 1) {
				double numPrice = (double) rand.nextInt((int) (price * 100)) / 100;
				if (inputStringBool("A customer offers $" + numPrice + ".00 for the rest of your " + name + ". Do you accept (y/n)", new String[]{"y"})) {
					numSold += num;
					money += num * numPrice;
					num = 0;
					numCustomers--;
				}
			}
			else {
				Animals randAnimal = animals.get(rand.nextInt(animals.size()));
				if (inputStringBool("A customer offers to trade one " + randAnimal + " for the rest of your " + name + ". Do you accept (y/n)", new String[]{"y"})) {
					numSold += num;
					randAnimal.setNumAnimals(randAnimal.getNumAnimals() + 1);
					num = 0;
					numCustomers--;
				}
			}
		}
		return numSold;
	}
	private static void market() {
		// sell
		for (Crops c : crops) {
			if (c.getNumCrops() > 0) {
				c.setNumCrops(c.getNumCrops() - bargain(
					c.getName(),
					inputIntMax("How many bushels of " + c + " do you want to sell?", c.getNumCrops(),"You have " + c.getNumCrops() + " " + c + "."),
					inputDoubleMax("How much do you want to charge for one bushel of " + c + "?", 15, "Customers will not buy if you charge more than $15") //magicnumber
				));
			}
		}
		for (Products p : products) {
			if (p.getNumProducts() > 0) {
				p.setNumProducts(p.getNumProducts() - bargain(
					p.getName(),
					inputIntMax("How many " + p + " do you want to sell?", p.getNumProducts(),"You have " + p.getNumProducts() + " " + p + "."),
					inputDoubleMax("How much do you want to charge for one " + p + "?", p.getCost(), "Customers will not buy if you charge more than $" + p.getCost())
				));
			}
		}
		for (Animals a : animals) {
			if (a.getNumAnimals() > 0) {
				a.setNumAnimals(a.getNumAnimals() - bargain(
					a.getName(),
					inputIntMax("How many " + a + " do you want to sell?", a.getNumAnimals(),"You have " + a.getNumAnimals() + " " + a + "."),
					inputDoubleMax("How much do you want to charge for one " + a + "?", 60, "Customers will not buy if you charge more than $60") //magicnumber
				));
			}
		}

		// buy
		for (int i = 0; i < crops.size(); i++) {
			if (i <= year) {
				Crops c = crops.get(i);
				int cropPrice = 15 + year; // magicnumber
				c.setNumCrops(c.getNumCrops() + inputInt(c + " are $" + cropPrice + " each. How many " + c + " do you want to buy?", ""));
			}
		}
		for (int i = 0; i < animals.size(); i++) {
			if (i <= year) {
				Animals a = animals.get(i);
				int cropPrice = 15 + year; // magicnumber
				a.setNumAnimals(a.getNumAnimals() + inputInt(a + " are $" + cropPrice + " each. How many " + a + " do you want to buy?", ""));
			}
		}

		// todo: hire wizards
		// todo: hire mechanics
		// todo: hire knights
		// todo: get new fields (from the duke)
		// todo: get new barns (carpenter)
		// todo: get new plows (smith)
	}
	private static void fail(String message) {
		clearScreen();
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
		clearScreen();
		System.out.println(infoColor + "Year: " + year + neutral);
		System.out.println(infoColor + "Month: " + months.get(monthI) + neutral);
		System.out.println(infoColor + "Money: " + money + neutral);
		System.out.println(infoColor + "Fields: " + fields + neutral);
		System.out.println(infoColor + "Plows: " + plows + neutral);
		System.out.println(infoColor + "Barns: " + barns + neutral);

		String format = "%s%-10s%10s%s";
		for (Crops crop : crops) {
			if (crop.getNumCrops() > 0) {
				System.out.printf(String.format(format, infoColor, crop.getNumCrops() + " " + crop, crop.getNumPlanted() + " planted", neutral));
				System.out.println();
			}
		}

		System.out.println();
		format = "%s%-15s%s";
		for (Animals animal : animals) {
			if (animal.getNumAnimals() > 0) {
				System.out.printf(String.format(format, infoColor, animal + ": " + animal.getNumAnimals(), neutral));
				System.out.println();
			}
		}

		System.out.println();
		for (Products product : products) {
			if (product.getNumProducts() > 0) {
				System.out.printf(String.format(format, infoColor, product + ": " + product.getNumProducts(), neutral));
				System.out.println();
			}
		}
	}
	private static String[] getWeather() {
		ArrayList<String> cold = new ArrayList<>(Arrays.asList("Gelid", "Cold", "Frosty", "Freezing", "Snowing"));
		ArrayList<String> warm = new ArrayList<>(Arrays.asList("Temperate", "Warm", "Ambient", "Brisk", "Windy"));
		ArrayList<String> rain = new ArrayList<>(Arrays.asList("Rainy", "Down-pouring", "Foggy", "Humid", "Flooding"));
		ArrayList<String> hot = new ArrayList<>(Arrays.asList("Hot", "Torrid", "Sweltering", "Blisteringly Hot", "Freezing cold - wait...That can't be right!"));

		int randWeather = rand.nextInt(4);
		if (monthI < 2 || monthI > 9) { //magicnumber
			return new String[]{cold.get(randWeather), "-5"}; //magicnumber
		}
		else if (monthI < 5 || monthI > 7) { //magicnumber
			int isRain = rand.nextInt(2);
			if (isRain == 0) {
				return new String[]{rain.get(randWeather), "50"}; //magicnumber
			}
			return new String[]{warm.get(randWeather), "5"}; //magicnumber
		}
		else  {
			int isRain = rand.nextInt(2);
			if (isRain == 0) {
				return new String[]{rain.get(randWeather), "30"}; //magicnumber
			}
			return new String[]{hot.get(randWeather), "-15"}; //magicnumber
		}
	}

	//=== MAIN FUNCTION ===\\
	private static void year() {
		if (inputStringBool("Do you want instructions (y/n)", new String[]{"y"})) {
			System.out.println(infoColor + "You said yes!  // fixme" + neutral);  // Todo: add instructions
			continueGame();
		}
		clearScreen();

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
				if (animal.getSize() >= 5) { //magicnumber
					numBigAnimals += animal.getNumAnimals();
				}
			}
			fieldRestrictions.add(numBigAnimals);
			Collections.sort(fieldRestrictions);
			int availFields = fieldRestrictions.getFirst();
			int plantedFields = 0;
			int water = 0;

			// plant crops
			printEquip();
			while (monthI < 4) {
				// find out if the user wants to plant crops this month
				if (inputStringBool("It is " + months.get(monthI) + " and the weather is " + getWeather()[0] + ". Do you want to plant your crops (y/n)", new String[]{"y"})) {
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
			// fail if user does not plant anything
			if (availFields == fieldRestrictions.getFirst()) {
				fail("You didn't plant any crops this year!");
			}
			else {
				plantedFields = fieldRestrictions.getFirst() - availFields;
			}

			// water crops
			if (!hasQuit) {
				clearScreen();
				printEquip();
				int currMonth = monthI;
				// crops need to grow for 6 months
				while (monthI < currMonth + 6) {
					String[] weather = getWeather();
					water += inputInt("It is " + months.get(monthI) + ", and the weather is " + weather[0] + ". How much water do you want to give to your crops (per field)?", "Usually fields take about 100 gallons of water, but this may fluctuate with the weather!");
					water += Integer.parseInt(weather[1]);
					endMonth();

					if (water <= (monthI - currMonth) * 75) { //magicnumber
						System.out.println(dangerColor + "Your crops are starting to dry up!" + neutral);
					}
					else if (water >= (monthI - currMonth) * 125) { //magicnumber
						System.out.println(dangerColor + "Your crops are starting to drown!" + neutral);
					}
				}

				// fail if the water is not right
				if (water < (monthI - currMonth) * 75) { //magicnumber
					fail("Your crops dried up!");
				}
				else if (water > (monthI - currMonth) * 125) { //magicnumber
					fail("Your crops drowned!");
				}
			}

			// harvest crops
			if (!hasQuit) {
				while (monthI < 11) {
					if (inputStringBool("Do you want to harvest your crops? (y/n)", new String[]{"y"})) {
						for (Crops crop : crops) {
							crop.harvestCrops();
						}
						endMonth();
						break;
					}
					endMonth();
				}
				if (monthI == 11) {
					fail("You did not harvest your crops in time!");
				}
			}

			// go to market
			if (!hasQuit) {
				while (monthI < 11) {
					// see if user wants to kill animals
					if (inputStringBool("Do you want to kill any animals for food (y/n)", new String[]{"y"})) {
						// loop through animals
						for (Animals a : animals) {
							if (a.getNumAnimals() > 0) {
								int killAnimal = inputIntMax("How many " + a + " do you want to kill?", a.getNumAnimals(), "This animal produces " + String.join(", ", a.getProducible().toString()));
								a.setNumAnimals(a.getNumAnimals() - killAnimal);
								for (Products p : a.getProducible()) {
									p.setNumProducts((int) (p.getNumProducts() + (p.getNumProduce() * killAnimal)));
								}
							}
						}
					}
					printEquip();
					if (inputStringBool("Do you want to go to the market?", new String[]{"y"})) {
						market();
						break;
					}
					endMonth();
				}
			}

			// end year
			if (!hasQuit) {
				while (monthI < 11) {
					endMonth();
					int foodEaten = 0;
					while (foodEaten < 5) {
						if (crops.get(0).getNumCrops() > 0) { // magicnumber
							int numEat = inputIntMax(months.get(monthI) + ": How much " + crops.get(0) + " do you want to eat?", crops.get(0).getNumCrops(), "");
							crops.get(0).setNumCrops(crops.get(0).getNumCrops() - numEat); // magicnumber
							foodEaten += numEat;
						}

						for (Products p : products) {
							if (p.getEatable() && p.getNumProducts() > 0) {
								int numEat = inputIntMax(months.get(monthI) + ": How many " + p + " do you want to eat?", p.getNumProducts(), "You have " + p.getNumProducts() + " " + p +".");
								p.setNumProducts(p.getNumProducts() - numEat);
								foodEaten += numEat;
							}
						}
					}
				}
			}

			// ask to continue to next year
			if (!hasQuit) {
				// taxes
				double tot_tax = (water * plantedFields * 0.01) + (barns * 10) + (fields * 20); // magicnumber
				System.out.println(
					"╔═══ Taxes Receipt" +
					"║ Water:   $" + (water * plantedFields * 0.01) +
					"║ Barns:   $" + (barns * 10) +
					"║ Fields:  $" + (fields * 20) +
					"╠═══" +
					"║ Total:   $" + tot_tax +
					"╚═══"
				); // magicnumber
				money -= tot_tax;

				hasQuit = inputStringBool("Do you want to quit and report to the king? (y/n)", new String[]{"y"});
			}
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
			money = data.readData(data.getPath(), "money");
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
		ArrayList<ArrayList<String>> newProducts = data.readData(dataPath, "products", new ArrayList<>(Arrays.asList("name", "numProduce", "requireKill", "eatable", "cost")));
		for (ArrayList<String> product : newProducts) {
			products.add(new Products(product.get(0), Double.parseDouble(product.get(1)), Boolean.parseBoolean(product.get(2)), Boolean.parseBoolean(product.get(3)), Double.parseDouble(product.get(4))));
		}

		animals = new ArrayList<>();
		ArrayList<ArrayList<String>> newAnimals = data.readData(dataPath, "animals", new ArrayList<>(Arrays.asList("name", "crops", "products", "size", "number")));
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
			animals.add(new Animals(animal.get(0), cropList, productList, Integer.parseInt(animal.get(3)), Integer.parseInt(animal.get(4))));
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

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Good Bye");  // fixme: save data when user exits
		}));

		year();
	}
}
