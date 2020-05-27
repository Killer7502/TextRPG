package enemyData;

/**
 * Represents the Dragon class which is a subclass of Enemy. Holds and sets data for the specific enemy.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.2
 */

import utilities.RanNumGen;

public class Dragon extends Enemy {
	
	/**
	 * Default constructor for the Dragon subclass. generatesStats and initializes the statNames and data arrayLists for the enemy.
	 */
	public Dragon() {
		generateStats();
		initArrays();
	}
	
	/**
	 * Sets the stats for the current enemy object. Each stat is given a base number + a random number between 1 and x to determine that stat for the enemy.
	 * Enemy name is also set here. If the enemy needs to have a modified baseHitChance and maxDamage, that can be set here.
	 */
	@Override void generateStats() {
		hitPoints = Integer.toString(40 + RanNumGen.genRandom(40));
		attack = Integer.toString(40 + RanNumGen.genRandom(40));
		strength = Integer.toString(40 + RanNumGen.genRandom(40));
		defense = Integer.toString(40 + RanNumGen.genRandom(40));
		
		cHitPoints = hitPoints;
		cAttack = attack;
		cStrength = strength;
		cDefense = defense;
		
		enemyName = "Dragon";
	}
}
