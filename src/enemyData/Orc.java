package enemyData;

/**
 * Represents the Orc class which is a subclass of Enemy. Holds and sets data for the specific enemy.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.2
 */

import utilities.RanNumGen;

public class Orc extends Enemy {
	
	/**
	 * Default constructor for the Orc subclass. generatesStats and initializes arrayLists for statNames and enemyData
	 */
	public Orc() {
		generateStats();
		initArrays();
	}
	
	/**
	 * Sets the stats for the current enemy object. Each stat is given a base number + a random number between 1 and x to determine that stat for the enemy.
	 * Enemy name is also set here. If the enemy needs to have a modified baseHitChance and maxDamage, that can be set here.
	 */
	@Override
	void generateStats() {
		hitPoints = Integer.toString(20 + RanNumGen.genRandom(20));
		attack = Integer.toString(20 + RanNumGen.genRandom(20));
		strength = Integer.toString(20 + RanNumGen.genRandom(20));
		defense = Integer.toString(20 + RanNumGen.genRandom(20));
		
		cHitPoints = hitPoints;
		cAttack = attack;
		cStrength = strength;
		cDefense = defense;
		
		enemyName = "Orc";
	}
}
