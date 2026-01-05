/**
 * This is a class that parses and reads data from a json file
 **/

//=== PACKAGE ===\\
package baseModels;

//=== IMPORTED MODULES ===\\
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

//=== CLASS ===\\
public class jsonReader {
	//=== ATTRIBUTES ===\\
	private String path;
	private String json;

	//=== CONSTRUCTORS ===\\
	public jsonReader(String path) {
		this.path = path;
	}

	//=== FUNCTIONS ===\\
	/**
	 * returns data from data.json
	 *
	 * @param path {@code String}: The path to data.json
	 * @param key {@code String}: The key for a list of objects
	 * @param subKeys {@code ArrayList<String>}: The list that holds the keys of each of the objects
	 * @return {@code ArrayList<ArrayList<Strings>>}: The list that holds each of the values needed to build an object
	 **/
	public ArrayList<ArrayList<String>> readData(String path, String key, ArrayList<String> subKeys) {
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
	public int readData(String path, String key) {
		// if data.json has not already been read, read it
		if (json == null) {
			readJSON(path);
		}

		// return integer value for key
		return new JSONObject(json).getInt(key);
	}

	//=== Private Functions ===\\
	// read data.json
	private void readJSON(String path) {
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


	//=== Getters ===\\
	public String getPath() {
		return path;
	}

	//=== Setters ===\\
	public void setPath(String path) {
		this.path = path;
	}
	public void setJson(String json) {
		this.json = json;
	}
}
