import baseModels.IO;
import myFarm.myFarm;
import mySmithy.mySmithy;
import myWeaver.myWeaver;
import myCarpenter.myCarpenter;

public class Main extends IO {
	public static void main(String[] args) {
		System.out.println("Welcome to Control Your Variables!\n");
		System.out.println("""
				The King of Wealthyland is concerned about Wealthyton (a major section of his country).
				For the past 6 months, Wealthyton has not paid its full taxes.
				And to make maters worse, there are rumors that Wealthyton is raising its own army.
				He is sending his top spy (you) disguised as a normal worker, to find out what is happening in Wealthyton.
				Previous reports suggest that the real issue is one of the following:
				\tInvaders
				\tPests
				\tMonsters
				\tor if there is no other possible solution: Duke is preparing to revolt
				
				You must report to the King at the end of every stage, and tell him what you think is wrong"""
		);
		inputString("Press Enter to Continue");
		clearScreen();

		myFarm.main(new String[]{});
		mySmithy.main(new String[]{});
		myWeaver.main(new String[]{});
		myCarpenter.main(new String[]{});
	}
}
