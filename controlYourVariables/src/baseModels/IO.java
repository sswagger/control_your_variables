package baseModels;

import java.util.Scanner;

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
}
