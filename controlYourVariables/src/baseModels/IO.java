package baseModels;
import java.util.Scanner;


public class IO {
	protected String input;
	protected Scanner sc = new Scanner(System.in);

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
}
