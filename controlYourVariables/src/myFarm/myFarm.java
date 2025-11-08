package myFarm;
import baseModels.IO;
import myFarm.Models.*;
import java.util.HashMap;
import java.util.Map;


public class myFarm extends IO {
	public static void main(String[] args) {
		Map<String, Crops> crops = new HashMap<>();
		crops.put("Wheat", new Crops("Wheat", 10));
		crops.put("Oats", new Crops("Oat", 10));
		crops.put("Hay", new Crops("Hay", 10));
		crops.put("Corn", new Crops("Corn", 10));
		crops.put("Barley", new Crops("Barley", 10));

		Map<String, Products> products = new HashMap<>();
		products.put("Leather", new Products("Leather", 2));
		products.put("Milk", new Products("Milk", 2));
		products.put("Meat", new Products("Meat", 2));
		products.put("Wool", new Products("Wool", 2));

		Map<String, Animals> animals = new HashMap<>();
		animals.put(
			"Horses",
			new Animals(
				"Horse",
				new Crops[]{crops.get("Wheat"), crops.get("Oats")},
				new Products[]{products.get("Leather")},
				5
			)
		);
		animals.put(
			"Cattle",
			new Animals(
				"Cattle",
				new Crops[]{crops.get("Oats"), crops.get("Hay")},
				new Products[]{products.get("Leather"), products.get("Milk"), products.get("Meat")},
			5
			)
		);
		animals.put(
			"Sheep",
			new Animals(
				"Sheep",
				new Crops[]{crops.get("Corn")},
				new Products[]{products.get("Leather")},
				5
			)
		);
		animals.put(
			"Chickens",
			new Animals(
				"Chicken",
				new Crops[]{crops.get("Barley"), crops.get("Corn")},
				new Products[]{products.get("Leather")},
				5
			)
		);
		animals.put(
				"Turkeys",
				new Animals(
						"Turkey",
						new Crops[]{crops.get("Barley"), crops.get("Corn")},
						new Products[]{products.get("Leather")},
						5
				)
		);
		animals.put(
				"Pigs",
				new Animals(
						"Pig",
						new Crops[]{crops.get("Wheat"), crops.get("Oats"), crops.get("Hay"), crops.get("Corn"), crops.get("Barley")},
						new Products[]{products.get("Leather")},
						5
				)
		);
		animals.put(
				"Goats",
				new Animals(
						"Goat",
						new Crops[]{crops.get("Wheat"), crops.get("Oats"), crops.get("Hay"), crops.get("Corn"), crops.get("Barley")},
						new Products[]{products.get("Leather")},
						5
				)
		);
	}
}
