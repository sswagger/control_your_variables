package baseModels;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class IO {
	//=== Variables ===\\
	protected static final String neutral = "\33[0m";
	protected static String input;
	protected static Scanner sc = new Scanner(System.in);
	protected static String json;

	//=== Functions ===\\
	protected static int inputInt(String prompt) {
		while (true) {
			System.out.print(prompt + ":  ");
			input = sc.findInLine(".*");
			sc.nextLine();
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
			input = sc.findInLine(".*");
			sc.nextLine();

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
		input = sc.findInLine(".*");
		sc.nextLine();

		if  (input.equalsIgnoreCase("h")) {
			// Todo: add help
			;
		}
		return input;
	}
	protected static String inputString(String prompt, String[] options) {
		while (true) {
			System.out.print(prompt + ":  ");
			input = sc.findInLine(".*");
			sc.nextLine();

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
		input = sc.findInLine(".*");
		sc.nextLine();

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
		return String.format("\33[38;2;%d;%d;%dm", red, green, blue);
	}
	protected static String rgbBackground(int red, int green, int blue) {
		return String.format("\33[48;2;%d;%d;%dm", red, green, blue);
	}
	// read from data.json
	protected static ArrayList<ArrayList<String>> readData(String path, String key, ArrayList<String> subKeys) {
		ArrayList<ArrayList<String>> data = new ArrayList<>();

		if (json.isEmpty()) {
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
		if (json.isEmpty()) {
			readJSON(path);
		}
		return new JSONObject(json).getInt(key);
	}

	//=== Private Functions ===\\
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
}
