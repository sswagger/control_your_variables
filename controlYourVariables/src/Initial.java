/**
 * Opens terminal and executes Main.java based on the current file path
 * Operates on Windows only
**/

//=== IMPORTED MODULES ===\\
import java.io.*;
import java.nio.file.FileSystems;

//=== CLASS ===\\
public class Initial {
	//=== MAIN FUNCTION ===\\
	public static void main(String[] args) {
		try {
			// get the file path and system's folder separator
			String currPath = System.getProperty("user.dir");
			String separator = FileSystems.getDefault().getSeparator();

			// create processBuilder and double check that it is a Windows OS
			ProcessBuilder processBuilder = new ProcessBuilder();
			if (System.getProperty("os.name").toLowerCase().contains("win")) {
				// create "folder path" Strings
				String classPath = currPath + separator + "out" + separator + "production" + separator + "controlYourVariables";
				String javaFile = "." + separator + "controlYourVariables" + separator + "src" + separator + "Main.java";
				// create command with arguments
				processBuilder.command("cmd", "/C", "start cmd.exe -ArgumentList \"/k java -cp \"" + classPath + ";lib" + separator + "*\" \"" + javaFile + "\""); // Windows
			}
			// execute commands in terminal
			processBuilder.start();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());  // todo: create better error handling, since this will be executed without a "System"
		}
	}
}
