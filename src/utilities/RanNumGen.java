package utilities;

/**
 * Represents the Goblin class which is a subclass of Enemy. Holds and sets data for the specific enemy.
 * @author Sawyer Kole
 * @version 0.1
 * @since 0.1
 */

import java.util.Random;

public class RanNumGen {

	/**
	 * Returns a random number to caller between 1 and num1.
	 * @param num1 - maximum number that the random number generator can geneerate
	 * @return randNum - Returns the generated random number to the caller.
	 */
	public static int genRandom(int num1) {
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		
		//If the num1 is below zero, set num1 to the absolute of num1
		num1 = num1 + 1;
		if (num1 < 0) {
			num1 = Math.abs(num1);
		}
		
		int randNum = rand.nextInt(num1);
		return randNum;
	}
	
	/**
	 * Returns random number to caller between 0 and num - 1
	 * If num1 is equal to 0 then the program will return 0 to the caller. Else it will run normally and generate a random number using num1 as the input.
	 * @return randNum - Returns the generated random number to the caller
	 */
	public static int genRandomZero(int num1) {
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		
		//If the num1 is below zero, set num1 to the absolute of num1
		if (num1 < 0) {
			num1 = Math.abs(num1);
		}
		
		//If num1 is equal to 0, pass back 0.
		if (num1 == 0) {
			return 0;
		}
		else {
			int randNum = rand.nextInt(num1);
			return randNum;
		}
	}
}
