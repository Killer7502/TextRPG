package userData;

/**
 * Represents the player class. Holds player variables and takes care of editing and sending anything player related.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.1
 */

import java.util.ArrayList;

import enemyData.Enemy;
import main.BattleGUI;
import utilities.RanNumGen;

public class Player {
	//Variables to hold the level for all player stats
	private String hitPoints;
	private String attack;
	private String strength;
	private String defense;
	private String mining;
	private String smithing;
	private String fishing;
	private String cooking;
	
	//Variables to hold the current level for all player stats
	private String cHitPoints;
	private String cAttack;
	private String cStrength;
	private String cDefense;
	private String cMining;
	private String cSmithing;
	private String cFishing;
	private String cCooking;
	
	//variables to hold the current experience for all player stats
	private String eHitPoints;
	private String eAttack;
	private String eStrength;
	private String eDefense;
	private String eMining;
	private String eSmithing;
	private String eFishing;
	private String eCooking;
	
	private ArrayList<String> statNames = new ArrayList<String>(); //Array to store stat names.
	private ArrayList<String> playerData = new ArrayList<String>(); //Array to store user data. Used to pass player data.
	private ArrayList<String> playerLogin = null; //Array to store user login data.
	
	/**
	 * Default Constructor. Sets the players stats to default values if no save data was loaded.
	 */
	public Player() {
		hitPoints = "10";
		cHitPoints = hitPoints;
		eHitPoints = "0";
		
		attack = "1";
		cAttack = attack;
		eAttack = "0";
		
		strength = "1";
		cStrength = strength;
		eStrength = "0";
		
		defense = "1";
		cDefense = defense;
		eDefense = "0";
		
		mining = "1";
		cMining = mining;
		eMining = "0";
		
		smithing = "1";
		cSmithing = "1";
		eSmithing = "0";
		
		fishing = "1";
		cFishing = "1";
		eFishing = "0";
		
		cooking = "1";
		cCooking = "1";
		eCooking = "0";
		
		initArrays();
	}
	
	/**
	 * Constructor. Used if player loads stats at the beginning of game and if player has a login associated with the save file.
	 * @param Array - Takes an array from the data loading service. Is used to set all player stats to a previous save game's stats.
	 * @param Login - Array that holds a users login data. Used to set populate the playerLogin array.
	 */
	public Player (ArrayList<String> Array, ArrayList<String> Login) {
		updatePlayer(Array);
		
		playerLogin = new ArrayList<String>();
		playerLogin.add(Login.get(0));
		playerLogin.add(Login.get(1));
		
		initArrays();
	}
	
	/**
	 * Constructor. Used if player loads stats at the beginning of the game.
	 * @param Array - Takes an array from the data loading service. Is used to set all player stats to a previous save game's stats.
	 */
	public Player (ArrayList<String> Array) {
		updatePlayer(Array);
		initArrays();
	}
	
	/**
	 * Adds data to player and statName arrays. Used at the end of constructors only.
	 */
	private void initArrays() {
		playerData.add(hitPoints);
		playerData.add(cHitPoints);
		playerData.add(eHitPoints);
		
		playerData.add(attack);
		playerData.add(cAttack);
		playerData.add(eAttack);
		
		playerData.add(strength);
		playerData.add(cStrength);
		playerData.add(eStrength);
		
		playerData.add(defense);
		playerData.add(cDefense);
		playerData.add(eDefense);
		
		playerData.add(mining);
		playerData.add(cMining);
		playerData.add(eMining);
		
		playerData.add(smithing);
		playerData.add(cSmithing);
		playerData.add(eSmithing);
		
		playerData.add(fishing);
		playerData.add(cFishing);
		playerData.add(eFishing);
		
		playerData.add(cooking);
		playerData.add(cCooking);
		playerData.add(eCooking);
		
		statNames.add("HitPoints");
		statNames.add("cHitPoints");
		statNames.add("eHitPoints");
		
		statNames.add("Attack");
		statNames.add("cAttack");
		statNames.add("eAttack");
		
		statNames.add("Strength");
		statNames.add("cStrength");
		statNames.add("eStrength");
		
		statNames.add("Defense");
		statNames.add("cDefense");
		statNames.add("eDefense");
		
		statNames.add("Mining");
		statNames.add("cMining");
		statNames.add("eMining");
		
		statNames.add("Smithing");
		statNames.add("cSmithing");
		statNames.add("eSmithing");
		
		statNames.add("Fishing");
		statNames.add("cFishing");
		statNames.add("eFishing");
		
		statNames.add("Cooking");
		statNames.add("cCooking");
		statNames.add("eCooking");
	}
	
	/**
	 * Used to return playerData to caller
	 * @return playerData - Returns playerData arrayList to the caller
	 */
	public ArrayList<String> getPlayerData() {
		
		playerData.set(0, hitPoints);
		playerData.set(1, cHitPoints);
		playerData.set(2, eHitPoints);
		
		playerData.set(3, attack);
		playerData.set(4, cAttack);
		playerData.set(5, eAttack);
		
		playerData.set(6, strength);
		playerData.set(7, cStrength);
		playerData.set(8, eStrength);
		
		playerData.set(9, defense);
		playerData.set(10, cDefense);
		playerData.set(11, eDefense);
		
		playerData.set(12, mining);
		playerData.set(13, cMining);
		playerData.set(14, eMining);
		
		playerData.set(15, smithing);
		playerData.set(16, cSmithing);
		playerData.set(17, eSmithing);
		
		playerData.set(18, fishing);
		playerData.set(19, cFishing);
		playerData.set(20, eFishing);
		
		playerData.set(21, cooking);
		playerData.set(22, cCooking);
		playerData.set(23, eCooking);
		
		return playerData;
	}
	
	/**
	 * Method to return array of player stat names
	 * @return statNames - Returns statNames arrayList to the caller
	 */
	public ArrayList<String> getStatNames() {
		return statNames;
	}
	
	/**
	 * Method to return array of player login data
	 * @return playerLogin - Returns playerLogin arrayList to the caller
	 */
	public ArrayList<String> getPlayerLogin() {
		return playerLogin;
	}
	
	/**
	 * Updates player stat variables from an given array
	 * @param Array - Takes an arrayList of Strings to update the player stat variables for the object
	 */
	private void updatePlayer(ArrayList<String> Array) {
		hitPoints = Array.get(0);
		cHitPoints = Array.get(1);
		eHitPoints = Array.get(2);
		
		attack = Array.get(3);
		cAttack = Array.get(4);
		eAttack = Array.get(5);
		
		strength = Array.get(6);
		cStrength = Array.get(7);
		eStrength = Array.get(8);
		
		defense = Array.get(9);
		cDefense = Array.get(10);
		eDefense = Array.get(11);
		
		mining = Array.get(12);
		cMining = Array.get(13);
		eMining = Array.get(14);
		
		smithing = Array.get(15);
		cSmithing = Array.get(16);
		eSmithing = Array.get(17);
		
		fishing = Array.get(18);
		cFishing = Array.get(19);
		eFishing = Array.get(20);
		
		cooking = Array.get(21);
		cCooking = Array.get(22);
		eCooking = Array.get(23);
	}
	
	/**
	 * Checks to see if any skills have enough experience to level up
	 * Each skill require 100 experience per level up. If a skill has more than 100 experience, one level is added to the skills primary level and current level and 100 experience is deducted from the skills experience variable
	 */
	public void checkLevel() {
		ArrayList<String> tempArray = new ArrayList<String>();
		tempArray = getPlayerData(); //Temp array for storing any new level ups
		//Iterates through the array for each unique skill and checks to see if a given skill has 100 or more experience in it
		for (int i = 0; i < tempArray.size(); i = i + 3) {
			if (Integer.parseInt(tempArray.get(i + 2)) >= 100) {
				int tempLevel = Integer.parseInt(tempArray.get(i));
				int tempExp = Integer.parseInt(tempArray.get(i + 2));
				
				//If a skill has atleast 100 experience, the system will loop through and add a level per 100 experience untilt eh experience level drops below 100.
				while (tempExp >= 100) {
					tempExp = tempExp - 100;
					tempLevel++;
				}
				//Set the temp array to reflect level ups and removed experience
				tempArray.set(i, Integer.toString(tempLevel));
				tempArray.set(i + 1, Integer.toString(tempLevel));
				tempArray.set(i + 2, Integer.toString(tempExp));
			}
		}
		//Update playerData array and player variables to reflect the changes in tempArray. Afterwards restore all current Stats to the main level of each stat.
		updatePlayer(tempArray);
		restoreAllStats();
	}
	
	/**
	 * Get skill value and return to caller
	 * @param skill - Takes a string from the caller. Uses that string to find amatching string in the statNames arrayList.
	 * @return temp - Returns the integer value associated with the skill name that was passed. 
	 */
	public String getSkill(String skill) {
		int i = 0;
		String temp = "";
		//Goes through the statNames arrayList and attempts to find a matching string to the skill string
		for (i = 0; i < statNames.size(); i++) {
			if (statNames.get(i).equals(skill)) {
				temp = playerData.get(i);
				break;
			}
		}
		return temp;
	}
	
	/**
	 * Add experience to a given skill.
	 * Searches statNames for the correct skill name and it's index. Uses this index to add the exp to the given skill in the playerData arrayList.
	 * @param skill - String of a given skill name to add experience to. Used to search statnames array for the correct skill.
	 * @param exp - Integer used to determine the amount of experience to be added to a particular skill.
	 */
	public void setExp(String skill, int exp) {
		int i = 0;
		//Goes through the statNames arrayList and attempts to find a matching string to the skill string
		for (i = 0; i < statNames.size(); i++) {
			if (statNames.get(i).equals(skill)) {
				break;
			}
		}
		//Adds exp to the corresponding skill in playerData. Proceeds to update the playerData arrayList to reflect this change
		exp = exp + Integer.parseInt(playerData.get(i + 2));
		playerData.set(i + 2, Integer.toString(exp));
		updatePlayer(playerData);
	}
	
	/**
	 * Determine attack roll and if damage should be dealt to the enemy.
	 * @param attackStyle - String used to determine attack style used by player. Attack style determines which stat will be used to determine chance to hit
	 * @param enemy - passed enemy object. Used to acccess the enemy and to send damageTaken
	 * @param battle - passed battleGUI object. Used to call back to current GUI and print to textOutput
	 */
	public void attack(String attackStyle, Enemy enemy, BattleGUI battle) {
		int enemyDefense = Integer.parseInt(enemy.getSkill("cDefense")); //Enemies current defense level
		int baseHitChance = 50; //Base hit chance to be added to calcHitChance
		int calcHitChance = 0;
		//Calc hitChance based off of attack style and related stats
		//calcHitChance is determined by adding the baseHitChance plus the current stat level for the given attack style. The enemies' current defense level is then subtracted from this number.
		if (attackStyle.equals("Accurate")) {
			calcHitChance = baseHitChance + Integer.parseInt(cAttack) - enemyDefense;
		}
		else if (attackStyle.equals("Aggressive")) {
			calcHitChance = baseHitChance + Integer.parseInt(cStrength) - enemyDefense;
		}
		else if (attackStyle.equals("Controlled")) {
			calcHitChance = baseHitChance + ((Integer.parseInt(cAttack) + Integer.parseInt(cStrength) + Integer.parseInt(cDefense)) / 3) - enemyDefense;
			if (((Integer.parseInt(cAttack) + Integer.parseInt(cStrength) + Integer.parseInt(cDefense)) % 3) != 0) {
				calcHitChance++;
			}
		}
		else if (attackStyle.equals("Defensive")) {
			calcHitChance = baseHitChance + Integer.parseInt(cDefense) - enemyDefense;
		}
		
		//calcHitChance is then checked to see if it is higher than a random number rolled between 1 and 100.
		//If calcHitChance is higher than or equal to the random number, the player hits the enemy and damage calculation is called. If lower than the random number, the player deals no damage to the enemy.
		int randNum = RanNumGen.genRandom(100);
		if (calcHitChance >= randNum) {
			int damage = calcDamageDealt();
			battle.printText("\nYou hit the " + enemy.getName() + " and deal " + damage + " damage\n");
			enemy.takeDamage(damage);
		}
		else {
			battle.printText("\nYou attack the enemy, but fail to deal any damage\n");
			enemy.takeDamage(0);
		}
	}
	
	/**
	 * Calculate damage to deal to enemy based on base damage of 1 + current Strength divided by 2
	 * @return damageDealt - Returns integer for damageDealt back to the caller
	 */
	private int calcDamageDealt() {
		int damageDealt = 0;
		damageDealt = 1 + (RanNumGen.genRandomZero(Integer.parseInt(cStrength) / 2));
		return damageDealt;
	}
	
	/**
	 * The player takes damage from an enemy attack. Takes the passed variable and subtracts it from the players current health pool.
	 * @param damage - Integer for how much damage the player is supposed to take.
	 */
	public void takeDamage(int damage) {
		cHitPoints = Integer.toString(Integer.parseInt(cHitPoints) - damage); //Subtract damage from current health
		
		//Checks to make sure that cHitPoints is set back to 0 if below 0
		if (Integer.parseInt(cHitPoints) < 0) {
			cHitPoints = "0";
		}
		//Update player data in the playerData arrayList
		playerData.set(1, cHitPoints);
	}
	
	//Heals the player by setting cHitPoints to hitPoints
	public void healPlayer() {
		cHitPoints = hitPoints;
		playerData.set(1, playerData.get(0));
	}
	
	//Restore all current player stats back to full
	private void restoreAllStats() {
		cHitPoints = hitPoints;
		cAttack = attack;
		cStrength = strength;
		cDefense = defense;
		cMining = mining;
		cSmithing = smithing;
		cFishing = fishing;
		cCooking = cooking;
	}
}