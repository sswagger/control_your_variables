package myFarm;

import baseModels.IO;
import mods.myFarm.myFarmMod;
import myFarm.Models.*;
import java.util.ArrayList;
import java.util.Arrays;

public class myFarm extends IO {
	//=== Variables ===\\
	private static ArrayList<String> months =  new ArrayList<>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
	private static int monthI;
	private static int year;
	private static int fields;
	private static String infoColor;
	private static String inputColor;
	private static String dangerColor;
	private static ArrayList<Crops> crops;
	private static ArrayList<Products> products;
	protected static ArrayList<Animals> animals;

	//=== Functions ===\\
	private static void printEquip() {
		// fixme: loop through crops, animals, and products
		System.out.println("your equipment here");
	}
	private static void year() {
		boolean hasQuit = false;

		if (inputStringBool("Do you want instructions?", new String[]{"y"})) {
			System.out.println(infoColor + "You said yes!" + neutral);  // Todo: add instructions
		}
		while (!hasQuit) {
			while (monthI < 4) {
				if (inputStringBool("Do you want to plant your crops?", new String[]{"y"})) {
					for (Crops crop : crops) {
						if (crop.getNumCrops() > 0) {
							crop.plantCrops(inputInt("How many " + crop + " do you want to plant (you have " + crop.getNumCrops() + ")?"));
						}
					}
				}
				break;  //testing purposes
			}
			break;  //testing purposes
		}
		System.out.println(crops.getFirst().getNumCrops());  // test
		// Todo: finish the year
	}

	//=== Main Function ===\\
	public static void main(String[] args) {
		String dataPath = System.getProperty("user.dir") + "\\controlYourVariables\\src\\myFarm\\";
		if (myFarmMod.dataPath != null && !myFarmMod.dataPath.isEmpty()) {
			dataPath = myFarmMod.dataPath;
		}

		try {
			monthI = Integer.parseInt(readData(dataPath, "monthI"));
			year = Integer.parseInt(readData(dataPath, "yearI"));
			fields = Integer.parseInt(readData(dataPath, "fields"));
		}
		catch (Exception e) {
			System.out.println("Cannot read simple data, check file format");
			System.out.println(e.getClass() + ": " + e.getMessage());
			return;
		}

		crops = new ArrayList<>();
		ArrayList<ArrayList<String>> newCrops = readData(dataPath, "crops", new ArrayList<>(Arrays.asList("name", "profit")));
		if (newCrops != null) {
			for (ArrayList<String> crop : newCrops) {
				crops.add(new Crops(crop.get(0), Integer.parseInt(crop.get(1))));
			}
		}

		products = new ArrayList<>();
		ArrayList<ArrayList<String>> newProducts = readData(dataPath, "products", new ArrayList<>(Arrays.asList("name", "numProduce")));
		if (newProducts != null) {
			for (ArrayList<String> product : newProducts) {
				products.add(new Products(product.get(0), Integer.parseInt(product.get(1))));
			}
		}

		animals = new ArrayList<>();
		ArrayList<ArrayList<String>> newAnimals = readData(dataPath, "animals", new ArrayList<>(Arrays.asList("name", "crops", "products", "size")));
		if (newAnimals != null) {
			for (ArrayList<String> animal : newAnimals) {
				ArrayList<Crops> cropList = new ArrayList<>();
				ArrayList<Products> productList = new ArrayList<>();
				for (int i = 0; i < animal.get(1).length(); i++) {
					char cropI = animal.get(1).charAt(i);
					try {
						cropList.add(crops.get(Integer.parseInt(cropI + "")));
					}
					catch (NumberFormatException e) {
						System.out.println(dangerColor + "Cannot read crops! " + cropI + neutral);
					}
				}
				for (int i = 0; i < animal.get(2).length(); i++) {
					char productI = animal.get(2).charAt(i);
					try {
						productList.add(products.get(Integer.parseInt(productI + "")));
					}
					catch (NumberFormatException e) {
						System.out.println(dangerColor + "Cannot read products! " + productI + neutral);
					}
				}
				animals.add(new Animals(animal.get(0), cropList, productList, Integer.parseInt(animal.get(3))));
			}
		}

		infoColor = rgbText(255, 255, 0) + rgbBackground(60, 140, 0);
		inputColor = rgbText(250, 250, 250) + rgbBackground(55, 150, 0);
		dangerColor = rgbText(255, 255, 0) + rgbBackground(210, 0, 0);
		crops.getFirst().setNumCrops(80);

		year();
	}
}
