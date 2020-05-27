package main;

/**
 * GUI that handles logging in if a save file has a username and password associated with it.
 * @author Sawyer Kole
 * @version 0.2
 * @since 0.2
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;

import utilities.Regex;
import javax.swing.JPasswordField;

public class LoginGUI {

	private JFrame frameLogin;
	
	private String username;
	private String password;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField txtUsername;
	private JButton btnEnter;
	private int counter;
	private StartGUI start;
	private JButton btnExit;
	private JPasswordField passPassword;
	
	/**
	 * @wbp.parser.constructor
	 */
	public LoginGUI() {
		initialize();
		createEvents();
	}
	
	//Constructor
	public LoginGUI(String user, String pass, StartGUI startGUI) {
		username = user;
		password = pass;
		start = startGUI;
		initialize();
		createEvents();
		frameLogin.setVisible(true);
	}
	
	//Create and execute events for the GUI
	private void createEvents() {
		
		//Exit program if pressed
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameLogin.dispose();
			}
		});
		
		/**
		 * Enter button pressed. Check entered username and password against data stored in file
		 */
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Regex regex = new Regex(); //Init and create Regex object
				String userInput = txtUsername.getText(); //String to hold entered username
				char[] charInput = passPassword.getPassword(); //String to hold entered password
				String passInput = "";
				for (int i = 0; i < charInput.length; i++) {
					String temp = Character.toString(charInput[i]);
					passInput = passInput.concat(temp);
				}
				
				//Check if username matches correct format
				if (regex.usernameCheck(userInput) == false) {
					JOptionPane.showMessageDialog(frameLogin,  "Username is not in the correct format or does not exist.\nPlease try again.");
					counter++;
				}
				//Check if password matches correct format
				else if (regex.passwordCheck(passInput) == false) {
					JOptionPane.showMessageDialog(frameLogin, "Password is not in the correct format or username password combonation does not exist.\nPlease try again.");
					counter++;
				}
				//Check if username and password match the username and password that are on file
				else if (userInput.equals(username) && passInput.contentEquals(password)) {
					JOptionPane.showMessageDialog(frameLogin, "Login successful.\nLoading game data now.");
					frameLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frameLogin.dispose();
					start.startGame(true);
				}
				//If the entered username and password don't match anything on file, tell the user and ask them to try again.
				else {
					JOptionPane.showMessageDialog(frameLogin, "The entered Username and Password do not match anything that's on file.");
					counter++;
				}
				
				//If number of tries is at or exceeds 10, tell the user that the max number of attempts has been reached and exit
				if (counter >= 10) {
					JOptionPane.showMessageDialog(frameLogin, "Max number of attempts reached.\nExiting now and loading default game data.");
					frameLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frameLogin.dispose();
					start.startGame(false);
				}
				//Clear charInput array and passInput string.
				Arrays.fill(charInput, '0');
				passInput = "";
			}
		});
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameLogin = new JFrame();
		frameLogin.setResizable(false);
		frameLogin.setTitle("TextRPG: Login");
		frameLogin.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameLogin.setBounds(100, 100, 250, 140);
		frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLogin.getContentPane().setLayout(null);
		frameLogin.setLocationRelativeTo(null);
		
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 11, 79, 14);
		frameLogin.getContentPane().add(lblUsername);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 36, 79, 14);
		frameLogin.getContentPane().add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(99, 8, 125, 20);
		frameLogin.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		btnEnter = new JButton("Enter");
		btnEnter.setBounds(135, 77, 89, 23);
		frameLogin.getContentPane().add(btnEnter);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(36, 77, 89, 23);
		frameLogin.getContentPane().add(btnExit);
		
		passPassword = new JPasswordField();
		passPassword.setBounds(99, 33, 125, 20);
		frameLogin.getContentPane().add(passPassword);
	}
}
