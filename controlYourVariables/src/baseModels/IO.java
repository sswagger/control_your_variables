package baseModels;

import myFarm.Models.Crops;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IO {
	//=== Variables ===\\
	public static final String neutral = "\33[0m";
	protected static String input;
	protected static Scanner sc = new Scanner(System.in);

	//=== Functions ===\\
	protected static int inputInt(String prompt) {
		while (true) {
			System.out.print(prompt + ":  ");
			input = sc.findInLine("(?<=: {2}).*");
			try {
				return Integer.parseInt(input);
			}
			catch (NumberFormatException e) {
				if (input.equalsIgnoreCase("h")) {
					// Todo: add help
					continue;
				}
				System.out.println("Invalid Number, Try Again.");
			}
		}
	}
	protected static Double inputDouble(String prompt) {
		while (true) {
			System.out.print(prompt + ":  ");
			input = sc.findInLine("(?<=: {2}).*");
			try {
				return Double.parseDouble(input);
			}
			catch (NumberFormatException e) {
				if (input.equalsIgnoreCase("h")) {
					// Todo: add help
					continue;
				}
				System.out.println("Invalid Decimal, Try Again.");
			}
		}
	}
	protected static String inputString(String prompt) {
		System.out.print(prompt + ":  ");
		input = sc.findInLine("(?<=: {2}).*");
		if  (input.equalsIgnoreCase("h")) {
			// Todo: add help
			;
		}
		return input;
	}
	protected static String inputString(String prompt, String[] options) {
		while (true) {
			System.out.print(prompt + ":  ");
			input = sc.findInLine("(?<=: {2}).*");

			if (input.equalsIgnoreCase("h")) {
				// Todo: add help
				continue;
			}
			for (String option : options) {
				if (option.equalsIgnoreCase(input)) {
					return input;
				}
			}

			System.out.println("Invalid Option, Try Again.");
		}
	}
	protected static boolean inputStringBool(String prompt, String[] options) {
		System.out.print(prompt + ":  ");
		input = sc.findInLine("(?<=: {2}).*");

		if (input.equalsIgnoreCase("h")) {
			// Todo: add help
			;
		}

		for (String option : options) {
			if (option.equalsIgnoreCase(input)) {
				return true;
			}
		}
		return false;
	}
	// based on a rgb input, return the ansi code for various colors
	public static String rgbText(int red, int green, int blue) {
		return String.format("\33[38;2;%d;%d;%dm", red, green, blue);
	}
	public static String rgbBackground(int red, int green, int blue) {
		return String.format("\33[48;2;%d;%d;%dm", red, green, blue);
	}
	// read from data.txt
	public static ArrayList<ArrayList<String>> readData(String path, String key, ArrayList<String> subKeys) {
		try (BufferedReader data = new BufferedReader(new FileReader(path + "/data.txt"))) {
			String firstIndentLine = data.readLine();
			while (firstIndentLine != null) {
				Pattern varPat = Pattern.compile("(?<=\\$).*?(?==)", Pattern.CASE_INSENSITIVE);
				Matcher varMatcher = varPat.matcher(firstIndentLine);

				if (varMatcher.find()) {
					if (varMatcher.group().equals(key)) {
						String secondIndentLine = data.readLine();
						ArrayList<ArrayList<String>> foundObj = new ArrayList<>();
						while (!secondIndentLine.isEmpty()) {
							ArrayList<String> foundVals = new ArrayList<>();
							for (String subKey : subKeys) {
								Pattern pat = Pattern.compile("(?<=\\$" + subKey + "=).*?(?=;)", Pattern.CASE_INSENSITIVE);
								Matcher match = pat.matcher(secondIndentLine);
								if (match.find()) {
									foundVals.add(match.group());
								}
							}
							foundObj.add(foundVals);
							secondIndentLine = data.readLine();
						}
						return foundObj;
					}
				}
				firstIndentLine = data.readLine();
			}
			return null;
		}
		catch (IOException e) {
			System.out.println(e.getMessage() + Arrays.toString(e.getStackTrace()));
			return null;
		}
	}
}
