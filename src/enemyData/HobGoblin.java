package enemyData;

/**
 * Represents the HobGoblin class which is a subclass of Enemy. Holds and sets data for the specific enemy.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.2
 */

import utilities.RanNumGen;

public class HobGoblin extends Enemy {
	
	/**
	 * Default constructor for the HobGoblin subclass. generatesStats and initializes the arrayLists for statNames and enemyData.
	 */
	public HobGoblin() {
		generateStats();
		initArrays();
	}
	
	/**
	 * Sets the stats for the current enemy object. Each stat is given a base number + a random number between 1 and x to determine that stat for the enemy.
	 * Enemy name is also set here. If the enemy needs to have a modified baseHitChance and maxDamage, that can be set here.
	 */
	@Override
	void generateStats() {
		hitPoints = Integer.toString(10 + RanNumGen.genRandom(15));
		attack = Integer.toString(10 + RanNumGen.genRandom(15));
		strength = Integer.toString(10 + RanNumGen.genRandom(15));
		defense = Integer.toString(10 + RanNumGen.genRandom(15));
		
		cHitPoints = hitPoints;
		cAttack = attack;
		cStrength = strength;
		cDefense = defense;
		
		enemyName = "HobGoblin";
	}
}
