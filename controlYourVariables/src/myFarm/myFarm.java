package myFarm;

import baseModels.IO;
import myFarm.Models.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class myFarm extends IO {
	//=== Variables ===\\
	private static String[] months =  new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private static int monthI;
	private static int year;
	private static String infoColor;
	private static String inputColor;
	private static String dangerColor;

	//=== Functions ===\\
	private static void printEquip() {
		// fixme: loop through crops, animals, and products
		System.out.println("your equipment here");
	}
	private static void year() {
		boolean hasQuit = false;

		if (inputStringBool("Do you want instructions?", new String[]{"y"})) {
			// Todo: add instructions
		}
		while (!hasQuit) {
			while (monthI < 4) {
				if (inputStringBool("Do you want to plant your crops?", new String[]{"y"})) {
					// todo: plant crops
				}
			}
		}
		// Todo: finish the year
	}

	//=== Main Function ===\\
	public static void main(String[] args) {
		ArrayList<Crops> crops = new ArrayList<>();
		ArrayList<ArrayList<String>> newCrops = readData(System.getProperty("user.dir") + "/controlYourVariables/src/myFarm/", "crops", new ArrayList<>(Arrays.asList("name", "profit")));
		if (newCrops != null) {
			for (ArrayList<String> crop : newCrops) {
				crops.add(new Crops(crop.get(0), Integer.parseInt(crop.get(1))));
			}
		}

		ArrayList<Products> products = new ArrayList<>();
		ArrayList<ArrayList<String>> newProducts = readData(System.getProperty("user.dir") + "/controlYourVariables/src/myFarm/", "products", new ArrayList<>(Arrays.asList("name", "numProduce")));
		if (newProducts != null) {
			for (ArrayList<String> product : newProducts) {
				products.add(new Products(product.get(0), Integer.parseInt(product.get(1))));
			}
		}

		ArrayList<Animals> animals = new ArrayList<>();
//		animals.put(
//			"Horses",
//			new Animals(
//				"Horse",
//				new Crops[]{crops.get("Wheat"), crops.get("Oats")},
//				new Products[]{products.get("Leather")},
//				5
//			)
//		);

		monthI = 0;
		year = 0;
		infoColor = rgbText(255, 255, 0) + rgbBackground(60, 140, 0);
		inputColor = rgbText(250, 250, 250) + rgbBackground(55, 150, 0);
		dangerColor = rgbText(255, 255, 0) + rgbBackground(210, 0, 0);
	}
}
