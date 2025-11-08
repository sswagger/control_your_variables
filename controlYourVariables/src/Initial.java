import java.io.*;
import java.nio.file.FileSystems;

public class Initial {
	public static void main(String[] args) {
		try {
			String currDirectory = System.getProperty("user.dir");
			String separator = FileSystems.getDefault().getSeparator();

			ProcessBuilder processBuilder = new ProcessBuilder();
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				processBuilder.command("cmd", "/C", "start cmd.exe -ArgumentList \"/k java .\\controlYourVariables\\src\\Main.java\""); // Windows
			}
			Process process = processBuilder.start();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
