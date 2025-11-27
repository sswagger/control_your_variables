package baseModels;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {
	//=== VARIABLES ===\\
	protected static final String neutral = "\033[0m";
	protected static String infoColor = "";
	protected static String inputColor = "";
	protected static String dangerColor = "";
	private static String input = "";
	private static final Scanner sc = new Scanner(System.in);
	private static String json;

	//=== FUNCTIONS ===\\
	// get input from System.in
	protected static int inputInt(String prompt) {
		while (true) {
			getInput(prompt);
			try {
				return Integer.parseInt(input);
			}
			catch (NumberFormatException e) {
				if (input.equalsIgnoreCase("h")) {
					// Todo: add help
					continue;
				}
				System.out.println(dangerColor + "Invalid Number, Try Again." + neutral);
			}
		}
	}
	protected static Double inputDouble(String prompt) {
		while (true) {
			getInput(prompt);

			try {
				return Double.parseDouble(input);
			}
			catch (NumberFormatException e) {
				if (input.equalsIgnoreCase("h")) {
					// Todo: add help
					continue;
				}
				System.out.println(dangerColor + "Invalid Decimal, Try Again." + neutral);
			}
		}
	}
	protected static String inputString(String prompt) {
		getInput(prompt);
		if  (input.equalsIgnoreCase("h")) {
			// Todo: add help
			;
		}
		return input;
	}
	protected static String inputString(String prompt, String[] options) {
		while (true) {
			getInput(prompt);

			if (input.equalsIgnoreCase("h")) {
				// Todo: add help
				continue;
			}
			for (String option : options) {
				if (option.equalsIgnoreCase(input)) {
					return input;
				}
			}

			System.out.println(dangerColor + "Invalid Option, Try Again." +  neutral);
		}
	}
	protected static boolean inputStringBool(String prompt, String[] options) {
		getInput(prompt);

//		if (input.equalsIgnoreCase("h")) {
//			// Todo: add help
//			;
//		}
		for (String option : options) {
			if (option.equalsIgnoreCase(input)) {
				return true;
			}
		}
		return false;
	}
	// based on a rgb input, return the ansi code for various colors
	protected static String rgbText(int red, int green, int blue) {
		return String.format("\033[38;2;%d;%d;%dm", red, green, blue);
	}
	protected static String rgbBackground(int red, int green, int blue) {
		return String.format("\033[48;2;%d;%d;%dm", red, green, blue);
	}
	// clear console window
	protected static void clearScreen() {
		System.out.println("\033[H\033[2J");
	}
	// return data from data.json
	protected static ArrayList<ArrayList<String>> readData(String path, String key, ArrayList<String> subKeys) {
		ArrayList<ArrayList<String>> data = new ArrayList<>();

		if (json == null) {
			readJSON(path);
		}

		JSONArray jsonKey = new JSONObject(json).getJSONArray(key);
		for (int i = 0; i < jsonKey.length(); i++) {
			ArrayList<String> dataSubKeys = new ArrayList<>();
			for (String subKey : subKeys) {
				JSONObject subJson = jsonKey.getJSONObject(i);
				dataSubKeys.add(subJson.getString(subKey));
			}
			data.add(dataSubKeys);
		}
		return data;
	}
	protected static int readData(String path, String key) {
		if (json == null) {
			readJSON(path);
		}
		return new JSONObject(json).getInt(key);
	}

	//=== PRIVATE FUNCTIONS ===\\
	// read data.json
	private static void readJSON(String path) {
		// read json and create object
		try (BufferedReader in = new BufferedReader(new FileReader(path + "data.json"))) {
			String line;
			StringBuilder sbJSON = new StringBuilder();

			while ((line = in.readLine()) != null) {
				sbJSON.append(line.trim());
			}
			json = sbJSON.toString();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private static void getInput(String prompt) {
		System.out.print(inputColor + prompt + ":" + neutral + "  ");
		input = sc.findInLine(".*");
		sc.nextLine();
		if (input == null) {
			input = "";
		}
	}
}
