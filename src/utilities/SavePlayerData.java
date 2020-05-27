package utilities;

/**
 * Handles saving the player data and stats to a text file. Takes the playerData statNames array from the Player class.
 * @author Sawyer Kole
 * @version 0.2
 * @since 0.1
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import userData.Player;

public class SavePlayerData {
	private Player p; //Create player variable. Used when player object is passed to method
	private ArrayList<String> playerData = new ArrayList<String>(); //Array list to store player data while saving
	private ArrayList<String> playerLogin = new ArrayList<String>(); //Array list to store player login data while saving
	private ArrayList<String> statNames = new ArrayList<String>(); //ArrayList to store stat names
	
	/**
	 * Default constructor. Used to save data from a given player object to a given file on the system
	 * @param file - File object that's passed to the constructor. Used to determine where to save the player data to on the system
	 * @param player - player object that's passed to the constructor. Used to retrieve the player data that is to be saved to a file
	 * @return output - Returns a string telling the user whether or not the game data saved or not.
	 */
	public String saveData(File file, Player player) {
		
		FileOutputStream fileStream = null; //File output stream variable
		PrintWriter outFS = null; //Print writer variable
		String output = ""; //String to store text on whether or nor the save failed or succeeded.
		p = player; //Sets p to the player object
		playerData = p.getPlayerData(); //Gets player data array from player object'
		playerLogin = p.getPlayerLogin();
		statNames = p.getStatNames(); //Gets stat names from Player object
		
		//Tries to open file and save player data to it
		try { //If data saves, send confirmation message back to user.
			fileStream = new FileOutputStream(file);
			outFS = new PrintWriter(fileStream);
			
			if (playerLogin != null) {
				outFS.println("Username:" + playerLogin.get(0));
				outFS.println("Password:" + playerLogin.get(1));
			}
			
			//For statement to go through array and save each value to file
			for (int i = 0; i < playerData.size(); i++) {
				outFS.println(statNames.get(i) +  ":" + playerData.get(i));
			}
			outFS.flush();
			//Set output string to successful essentially
			output = ("Game saved successfully\nFeel free to close the game now");
		}
		catch (IOException e) { //Catch if system fails to open file. Sends error back to user.
			//Set output string to failed essentially
			output = (e + "\nFAILURE: GAME FAILED TO SAVE!\nPlease try again");
		}
		
		//Return output string to caller
		return output;
	}
}
