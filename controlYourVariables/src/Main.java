import java.nio.file.FileSystems;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("Welcome to Control Your Variables");
		String input = new Scanner(System.in).nextLine();

		String currDirectory = System.getProperty("user.dir");
		String separator = FileSystems.getDefault().getSeparator();
		System.out.println(currDirectory);
		System.out.println(separator);
	}
}
