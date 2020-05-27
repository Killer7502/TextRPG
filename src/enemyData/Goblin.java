package enemyData;

/**
 * Represents the Goblin class which is a subclass of Enemy. Holds and sets data for the specific enemy.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.2
 */

import utilities.RanNumGen;

public class Goblin extends Enemy {
	
	/**
	 * Base constructor for goblin. Generates stats for the Goblin and initializes the stat and data arrays.
	 */
	public Goblin() {
		generateStats();
		initArrays();
	}
	
	/**
	 * Sets the stats for the current enemy object. Each stat is given a base number + a random number between 1 and x to determine that stat for the enemy.
	 * Enemy name is also set here. If the enemy needs to have a modified baseHitChance and maxDamage, that can be set here.
	 * Goblin has a baseHitChance of 10 instead of 50 and a maxDamage of 1. This is to ensure that the player can beat the first enemy most of the time.
	 */
	@Override void generateStats() {
		hitPoints = Integer.toString(5 + RanNumGen.genRandom(10));
		attack = Integer.toString(5 + RanNumGen.genRandom(10));
		strength = Integer.toString(5 + RanNumGen.genRandom(10));
		defense = Integer.toString(5 + RanNumGen.genRandom(10));
		
		cHitPoints = hitPoints;
		cAttack = attack;
		cStrength = strength;
		cDefense = defense;
		
		enemyName = "Goblin";
		
		baseHitChance = 10;
		maxDamage = 1;
	}
}
