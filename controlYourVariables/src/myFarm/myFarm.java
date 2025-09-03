package myFarm;
import myFarm.Models.Animals;
import myFarm.Models.Crops;


public class myFarm {
	public static void main(String[] args) {
		Crops c = new Crops("firstCrop");
		Crops e = new Crops("secondCrop");
		Animals myTestAnimal = new Animals("firstAnimal", new Crops[]{c, e}, 2);
		c.setNumCrops(6);
		e.setNumCrops(5);
		myTestAnimal.setNumAnimals(20);

		myTestAnimal.eat();
	}
}
