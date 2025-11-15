package baseModels;

import java.util.Scanner;

public class IO {
	//=== Attributes ===\\
	public static final String neutral = "\33[0m";
	protected String input;
	protected Scanner sc = new Scanner(System.in);

	//=== Methods ===\\
	protected int inputInt(String prompt) {
		while (true) {
			System.out.print(prompt + ":  ");
			this.input = sc.findInLine("(?<=: {2}).*");
			try {
				return Integer.parseInt(this.input);
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
	protected Double inputDouble(String prompt) {
		while (true) {
			System.out.print(prompt + ":  ");
			this.input = sc.findInLine("(?<=: {2}).*");
			try {
				return Double.parseDouble(this.input);
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
	protected String inputString(String prompt) {
		System.out.print(prompt + ":  ");
		this.input = sc.findInLine("(?<=: {2}).*");
		if  (input.equalsIgnoreCase("h")) {
			// Todo: add help
			;
		}
		return this.input;
	}
	protected String inputString(String prompt, String[] options) {
		while (true) {
			System.out.print(prompt + ":  ");
			this.input = sc.findInLine("(?<=: {2}).*");

			if (input.equalsIgnoreCase("h")) {
				// Todo: add help
				continue;
			}
			for (String option : options) {
				if (option.equalsIgnoreCase(this.input)) {
					return this.input;
				}
			}

			System.out.println("Invalid Option, Try Again.");
		}
	}
	// based on a rgb input, return the ansi code for various colors
	public static String rgbText(int red, int green, int blue) {
		return String.format("\33[38;2;%d;%d;%dm", red, green, blue);
	}
	public static String rgbBackground(int red, int green, int blue) {
		return String.format("\33[48;2;%d;%d;%dm", red, green, blue);
	}
}
