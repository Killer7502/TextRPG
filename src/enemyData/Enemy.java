package enemyData;

/**
 * Represents the enemy class. Holds the enemy variables and handles anything enemy related such as attacking, dealing damage, taking damage, etc.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.2
 */

import java.util.ArrayList;

import main.BattleGUI;
import userData.Player;
import utilities.RanNumGen;

public abstract class Enemy {

	//protected String combatLevel;
	
	//Base stats for a given enemy
	protected String hitPoints;
	protected String attack;
	protected String strength;
	protected String defense;
	
	//Current stats for a given enemy
	protected String cHitPoints;
	protected String cAttack;
	protected String cStrength;
	protected String cDefense;
	
	//String for enemyName. Set by subclasses
	protected String enemyName;
	
	//Base chance to hit for all enemies. Can be changed by an enemy subclass to either make them hit or miss more often.
	protected int baseHitChance = 50;
	//Max damage for a given enemy class. If the max damage is left at 0, then there is no max damage ceiling set for a given enemy.
	protected int maxDamage = 0;
	
	//Arraylists that hold enemy stat names (String) and the actual enemyStats (integer)
	protected ArrayList<String> statNames = new ArrayList<String>();
	protected ArrayList<String> enemyStats = new ArrayList<String>();
	
	/**
	 * Set stats for an enemy subclass. Each subclass is required to define this.
	 */
	abstract void generateStats();
	
	/**
	 * Enemy attacks the player. Used to determine if an enemy hits the player and how much damage they'll deal.
	 * @param player - passed player object. Is used to determine player defense and to send a takeDamage command to the player
	 * @param battle - passed battleGUI object. Is used to reference back to the GUI on screen at the time so that text may be appended to the textOutput there.
	 */
	public void attack(Player player, BattleGUI battle) {
		int playerDefense = Integer.parseInt(player.getSkill("cDefense"));
		int calcHitChance = 0;
		
		int attackStyle = RanNumGen.genRandom(4);
		
		//Switch to determine attack style that the given enemy will use. Attack style only determines what stat (Attack, Strength, Defense) will be used to determine the hit chance
		//calcHitChance is determined by adding baseHitChance and the current level of the choosen attack style together and then subtracting from the player's current defense level
		switch (attackStyle) {
		case 1: //Accurate
			calcHitChance = baseHitChance + Integer.parseInt(cAttack) - playerDefense;
			break;
		case 2: //Aggressive
			calcHitChance = baseHitChance + Integer.parseInt(cStrength) - playerDefense;
			break;
		case 3: //Controlled
			calcHitChance = baseHitChance + ((Integer.parseInt(cAttack) + Integer.parseInt(cStrength) + Integer.parseInt(cDefense)) / 3) - playerDefense;
			if (((Integer.parseInt(cAttack) + Integer.parseInt(cStrength) + Integer.parseInt(cDefense)) % 3) != 0) {
				calcHitChance++;
			}
			break;
		case 4: //Defensive
			calcHitChance = baseHitChance + Integer.parseInt(cDefense) - playerDefense;
			break;
		}
		
		//Check if calculated hit chance is greater than a random number roll between 1 and 100
		//If calculated hit chance is greater than the random number, then the enemy hits the player. If not the enemy misses the player
		if (calcHitChance >= RanNumGen.genRandom(100)) {
			int damage = calcDamageDealt();
			battle.printText("\nThe " + enemyName + " hits you and deals " + damage + " damage\n");
			player.takeDamage(damage);
		}
		else {
			battle.printText("\nThe " + enemyName + " attacks you, but fails to deal any damage\n");
			player.takeDamage(0);
		}
	}
	
	/**
	 * Calculate damage to be dealt to the player
	 * @return damageDealt - Returns the damage to be dealt based off of base 1 + random number between 0 and cStrength/2
	 */
	private int calcDamageDealt() {
		int damageDealt = 0;
		
		damageDealt = 1 + (RanNumGen.genRandomZero(Integer.parseInt(cStrength) / 2)); //Damage dealt is base 1 + a random number between 0 and current Strength divided by 2 (rounded down)
		
		//If the enemy has a max damage set, make sure that the damageDealt doesn't exceed the maxDamage
		if (maxDamage != 0) {
			if (damageDealt > maxDamage) {
				damageDealt = maxDamage;
			}
		}
		return damageDealt;
	}
	
	/**Enemy takes damage from player. Updates enemyData array with the new health value after calculation
	 * @param damage - amount of damage that the enemy is going to take to their current hitpoints
	 */
	public void takeDamage(int damage) {
		cHitPoints = Integer.toString(Integer.parseInt(cHitPoints) - damage);
		
		//Checks to make sure that cHitPoints is set back to 0 if below 0
		if (Integer.parseInt(cHitPoints) < 0) {
			cHitPoints = "0";
		}
		
		enemyStats.set(1, cHitPoints);
	}
	
	/**
	 * Initialize/Add data to statsNames and enemyStats arrayLists
	 */
	protected void initArrays() {
		enemyStats.add(hitPoints);
		enemyStats.add(cHitPoints);
		enemyStats.add(attack);
		enemyStats.add(cAttack);
		enemyStats.add(strength);
		enemyStats.add(cStrength);
		enemyStats.add(defense);
		enemyStats.add(cDefense);
		
		statNames.add("HitPoints");
		statNames.add("cHitPoints");
		statNames.add("Attack");
		statNames.add("cAttack");
		statNames.add("Strength");
		statNames.add("cStrength");
		statNames.add("Defense");
		statNames.add("cDefense");
	}
	
	/**
	 * Return enemy stats names ArrayList to caller
	 * @return statNames - Returns the enemy statNames arrayList to caller
	 */
	public ArrayList<String> getStatNames() {
		return statNames;
	}
	
	/**
	 * Return updated enemy stats ArrayList to caller
	 * @return enemyStats - Returns the enemyStats arrayList to the caller
	 */
	public ArrayList<String> getEnemyStats() {
		enemyStats.set(0, hitPoints);
		enemyStats.set(1, cHitPoints);
		enemyStats.set(2, attack);
		enemyStats.set(3, cAttack);
		enemyStats.set(4, strength);
		enemyStats.set(5, cStrength);
		enemyStats.set(6, defense);
		enemyStats.set(7, cDefense);
		
		return enemyStats;
	}
	
	/**
	 * 
	 * @param skill - Takes a string from caller which is used to find a skill in the statNames arrayList
	 * @return temp - Returns the skill level of the called skill to the caller (int)
	 */
	public String getSkill(String skill) {
		int i = 0;
		String temp = "";
		for (i = 0; i < statNames.size(); i++) {
			if (statNames.get(i).equals(skill)) {
				temp = enemyStats.get(i);
				break;
			}
		}
		return temp;
	}
	
	/**
	 * Used to get the enemyName from a given enemy object
	 * @return enemyName - Returns the enemyName variable to caller
	 */
	public String getName() {
		return enemyName;
	}
}
