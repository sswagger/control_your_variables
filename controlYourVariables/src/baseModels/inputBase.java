/**
 * This is the parent class for all classes that contain a psvm
**/

//=== PACKAGE ===\\
package baseModels;

//=== IMPORTED MODULES ===\\
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

//=== CLASS ===\\
public class inputBase {
	//=== VARIABLES ===\\
	protected static final String neutral = "\033[0m";
	protected static String infoColor = "";
	protected static String inputColor = "";
	protected static String dangerColor = "";
	private static String input = "";
	private static final Scanner sc = new Scanner(System.in);
	private static String json;

	//=== FUNCTIONS ===\\
	/**
	 * Takes numerical input from System.in.
	 *
	 * @param prompt {@code String}: Question that prompts the user for a response
	 * @return {@code Int}: The users response parsed into an int
	**/
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

	/**
	 * Takes decimal input from System.in.
	 *
	 * @param prompt {@code String}: The String that prompts the user for a response
	 * @return {@code Double}: The users response parsed into a Double
	**/
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

	/**
	 * Takes any input from System.in.
	 *
	 * @param prompt {@code String}: The String that prompts the user for a response
	 * @return {@code String}: The users response
	**/
	protected static String inputString(String prompt) {
		getInput(prompt);
		if  (input.equalsIgnoreCase("h")) {
			// Todo: add help
			;
		}
		return input;
	}

	/**
	 * Takes input from System.in.
	 * Forces the user to enter one of the defined options.
	 *
	 * @param prompt {@code String}: The String that prompts the user for a response
	 * @param options {@code String[]}: A selection of options for the user to pick from
	 * @return {@code String}: The users response
	**/
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

	/**
	 * Takes input from System.in.
	 * Forces the user to enter one of the defined options.
	 * Returns true if the user picks one of the options.
	 *
	 * @param prompt {@code String}: The String that prompts the user for a response
	 * @param options {@code String[]}: A selection of correct options for the user
	 * @return {@code Bool}: True if the user picks one of the options; otherwise, False
	**/
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

	/**
	 * based on a rgb input, return the ANSI color code for foreground colors
	 *
	 * @param red {@code int}: The red value (1 - 255)
	 * @param green {@code int}: The green value (1 - 255)
	 * @param blue {@code int}: The blue value (1 - 255)
	 * @return {@code String}: The ANSI color code.
	**/
	protected static String rgbText(int red, int green, int blue) {
		return String.format("\033[38;2;%d;%d;%dm", red, green, blue);
	}

	/**
	 * based on a rgb input, return the ANSI color code for background colors
	 *
	 * @param red {@code int}: The red value (1 - 255)
	 * @param green {@code int}: The green value (1 - 255)
	 * @param blue {@code int}: The blue value (1 - 255)
	 * @return {@code String}: The ANSI color code.
	**/
	protected static String rgbBackground(int red, int green, int blue) {
		return String.format("\033[48;2;%d;%d;%dm", red, green, blue);
	}

	/**
	 * Prints ANSI codes for clearing console window
	**/
	protected static void clearScreen() {
		System.out.println("\033[H\033[2J");
	}

	/**
	 * returns data from data.json
	 *
	 * @param path {@code String}: The path to data.json
	 * @param key {@code String}: The key for a list of objects
	 * @param subKeys {@code ArrayList<String>}: The list that holds the keys of each of the objects
	 * @return {@code ArrayList<ArrayList<Strings>>}: The list that holds each of the values needed to build an object
	**/
	protected static ArrayList<ArrayList<String>> readData(String path, String key, ArrayList<String> subKeys) {
		ArrayList<ArrayList<String>> data = new ArrayList<>();

		// if data.json has not already been read, read it
		if (json == null) {
			readJSON(path);
		}

		// loop through key values
		JSONArray jsonKey = new JSONObject(json).getJSONArray(key);
		for (int i = 0; i < jsonKey.length(); i++) {
			ArrayList<String> dataSubKeys = new ArrayList<>();
			// loop through subkeys and add values
			for (String subKey : subKeys) {
				JSONObject subJson = jsonKey.getJSONObject(i);
				dataSubKeys.add(subJson.getString(subKey));
			}
			data.add(dataSubKeys);
		}
		return data;
	}

	/**
	 * returns data from data.json
	 *
	 * @param path {@code String}: The path to data.json
	 * @param key {@code String}: The key for the needed value
	 * @return {@code int}: The value for the key
	 **/
	protected static int readData(String path, String key) {
		// if data.json has not already been read, read it
		if (json == null) {
			readJSON(path);
		}

		// return integer value for key
		return new JSONObject(json).getInt(key);
	}

	//=== PRIVATE FUNCTIONS ===\\
	// read data.json
	private static void readJSON(String path) {
		// read JSON and create object
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
	// display input and ensure that the user said something
	private static void getInput(String prompt) {
		System.out.print(inputColor + prompt + ":" + neutral + "  ");
		input = sc.findInLine(".*");
		sc.nextLine();
		if (input == null) {
			input = "";
		}
	}
}
