import java.io.*;

public class Initial {
	public static void main(String[] args) {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				processBuilder.command("cmd", "/C", "start cmd.exe -ArgumentList \"/k java .\\controlYourVariables\\src\\Main.java\""); // Windows
			}
			processBuilder.start();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
