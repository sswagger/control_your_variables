import java.io.*;
import java.nio.file.FileSystems;

// opens console app on Windows OS
public class Initial {
	public static void main(String[] args) {
		try {
			String currPath = System.getProperty("user.dir");
			String separator = FileSystems.getDefault().getSeparator();
			ProcessBuilder processBuilder = new ProcessBuilder();
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				String classPath = currPath + separator + "out" + separator + "production" + separator + "controlYourVariables";
				String javaFile = "." + separator + "controlYourVariables" + separator + "src" + separator + "Main.java";
				processBuilder.command("cmd", "/C", "start cmd.exe -ArgumentList \"/k java -cp \"" + classPath + ";lib" + separator + "*\" \"" + javaFile + "\""); // Windows
			}
			processBuilder.start();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
