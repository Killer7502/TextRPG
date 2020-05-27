package utilities;

/**
 * Handles regex patterns for calling classes. Primarily used for the login system. Used to check if a username and password exist in a save file and then checks to make sure that the user uses the correrct format when entering their username and password
 * @author Sawyer Kole
 * @version 0.2
 * @since 0.2
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	/**
	 * Checks to see if the provide username String is in the correct format or not defined by the variable Regex below.
	 * @param username - Takes a username string from caller to compare against the set Regex string.
	 * @return isCorrect (boolean) - returns if the given username string is in the correct format or not
	 */
	public Boolean usernameCheck(String username) {
		String Regex = ("([A-Z]{1})((\\S|\\w|\\d){4,6})([0-9]{1}$)"); //Regex string to check username against
		Boolean isCorrect = false; //Boolean for determining if username is in the correct format
		Pattern pattern; //Init Pattern object
		Matcher matcher; //Init Matcher object
			
		pattern = Pattern.compile(Regex);
		matcher = pattern.matcher(username);
		
		//Check if matcher can find a match for the given regex and input string
		if (matcher.find() == true) {
			isCorrect = true;
		}
		else {
			isCorrect = false;
		}
		
		//Set both opened objects to null once done
		pattern = null;
		matcher = null;
		
		//Return isCorrect (boolean)
		return isCorrect;
	}
	
	/**
	 * Checks to see if the provided password String is in the correct format or not defined by the variable Regex below
	 * @param password - Takes a password string from the caller to compare against the set Regex string.
	 * @return isCorrect (boolean) - returns if the given password string is in the correct format or not
	 */
	public Boolean passwordCheck(String password) {
		String Regex = ("((\\S|\\w|\\d){8,12}$)"); //Regex string to check password against
		Boolean isCorrect = false; //Boolean for determining if username is in the correct format
		Pattern pattern; //Init Pattern object
		Matcher matcher; //Init Matcher object
		
		pattern = Pattern.compile(Regex);
		matcher = pattern.matcher(password);
		
		//Check if matcher can find a match for the given regex and input string
		if (matcher.find() == true) {
			isCorrect = true;
		}
		else {
			isCorrect = false;
		}
		
		//Set both opened objects to null once done
		pattern = null;
		matcher = null;
		
		//Return isCorrect (boolean)
		return isCorrect;
	}
	
	/**
	 * Used to read a line of data from the save file
	 * @param regex - String passed from the caller to compare the loaded string from the text file against
	 * @param input - String loaded from the text file to be checked
	 * @return isMatch (boolean) - Returns if the loaded line from the text file has a match for the given regex string
	 */
	public Boolean stringCheck(String regex, String input) {
		Boolean isMatch = false;
		Pattern pattern; //Init pattern object
		Matcher matcher; //Init matcher object
		
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(input);
		
		//Check if there is a match with the given regex and input
		if (matcher.find() == true) {
			isMatch = true;
		}
		else {
			isMatch = false;
		}
		
		//Set both opened objects to null once done
		pattern = null;
		matcher = null;
		
		//Return isMatch (boolean)
		return isMatch;
	}
}
