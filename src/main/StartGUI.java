package main;

/**
 * Handles creating the initial GUI for the game. Is used during initial startup and if the player loses a game.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.1
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.LayoutStyle.ComponentPlacement;

import userData.Player;
import utilities.Regex;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class StartGUI {

	private JFrame frameStart;
	private JTextField txtRandomTextBox;
	private JButton btnStart;
	private JButton btnLoad;
	private JFileChooser fileChooser;

	private static StartGUI start;
	private MainGUI main;
	private LoginGUI login;
	private Player player; //Initialize player
	private ArrayList<String> array; //Initialize ArrayList to be used later
	private ArrayList<String> loginArray; //Initialize Arraylist to store login data for player if they have it
	private StartGUI reStart;
	/**
	 * Launch the application.
	 */
	
	//Main method. Creates the StartGUI and goes from there
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGUI window = new StartGUI();
					start = window;
					window.frameStart.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	/**
	 * @wbp.parser.constructor
	 */
	public StartGUI() {
		initialize();
		createEvents();
	}
	
	/*
	 * Constructor only used if player dies
	 */
	public StartGUI(StartGUI start) {
		reStart = this;
		initialize();
		createEvents();
		frameStart.setVisible(true);
	}
	
	/*
	 * Method used to start game if LoginGUI was called before starting
	 */
	public void startGame(Boolean isLogin) {
		frameStart.setVisible(true);
		//If there is a login from a loaded save game, then start a loginGUI before starting the game.
		if (isLogin == true) {
			player = new Player(array, loginArray);
			JOptionPane.showMessageDialog(frameStart, "Game data loaded.\nStarting game now.");
		}
		//If there is no login from a loaded save game or if the user exits the loginGUI window, load the game with default player data.
		else if (isLogin == false) {
			player = new Player();
			JOptionPane.showMessageDialog(frameStart, "New game data loaded. Starting game now.");
		}
		frameStart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameStart.dispose();
		login = null;
		main = new MainGUI(player);
		if (reStart != null) {
			reStart = null;
		}
	}
	
	//Stores events for StartGUI
	private void createEvents() {
		
		//Start button pressed. Starts new game with default character data
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Create new player object with default stats
				JOptionPane.showMessageDialog(frameStart, "Starting new game.");
				Player player = new Player();
				
				//Close current frame and start the MainGUI/Game
				frameStart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frameStart.dispose();
				main = new MainGUI(player); //FIXME: Memory leak: Due to leaving old object open. Find way to set object to null
				if (reStart != null) {
					reStart = null;
				}
			}
		});
		
		//Load button pressed
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fileChooserVal = fileChooser.showOpenDialog(frameStart);
				File readFile = null; //Variable to hold selected file
				Scanner inFS = null; //Create scanner
				FileInputStream fileInput = null; //FileInputStream initialize
				String readLine = null; ////String to hold current line from save data file
				array = new ArrayList<String>(); //Array list to store loaded data before sending to player class
				loginArray = new ArrayList<String>();
				Regex regex = new Regex();
				
				//Opens a File Chooser window and then tries to open and read a given file. FIXME: Create a new LoadPlayerData class and put this in there (Good Luck)
				if (fileChooserVal == JFileChooser.APPROVE_OPTION) { //FIXME: Detect if close was selected once FileChooser was opened.
					readFile = fileChooser.getSelectedFile();
					
					//Checks if file can be read or not
					if (readFile.canRead()) {
						try {
								fileInput = new FileInputStream(readFile);
								inFS = new Scanner(fileInput);
								
								while (inFS.hasNext()) { //Read the save file and add the data inside to an ArrayList
									readLine = inFS.nextLine();
									
									//If readLine is either a Username or password, add it to the loginArray array. Else add to the array array (used for player stats)
									if (regex.stringCheck("Username:", readLine) == true || regex.stringCheck("Password:", readLine) == true) {
										loginArray.add(readLine);
									}
									else {
										String[] tempArray = readLine.split(":", 2);
										array.add(tempArray[1]); //Add only the integer element from the loaded data. 0 holds the stat name which isn't needed here. FIXME: Load stat name in stats array.
										Arrays.fill(tempArray, ""); 
									}
								}
						}
						catch (IOException e) { //File can't be read or InputStream died. Display error to user
							JOptionPane.showMessageDialog(frameStart, e);
						}
					}
					else { //InputStream can open the file, but it can be read. Inform user to try a different file
						JOptionPane.showMessageDialog(frameStart, "File can't be opened or read from. Please try a different file");
					}
				}
				
				//try {fileInput.close(); inFS.close();} catch (IOException e) {System.out.println("Error closing file: " + e);}
				
				
				//If array is empty, create player with default stats. Else create player with data located inside the array
				if (array.isEmpty()) {
					JOptionPane.showMessageDialog(frameStart, "Starting new game.");
					player = new Player(); 
					frameStart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frameStart.dispose();
					main = new MainGUI(player);
					if (reStart != null) {
						reStart = null;
					}
				}
				else {
					//If Username and Password exists in the file, then open the LoginGUI
					if (regex.stringCheck("Username", loginArray.get(0)) == true && regex.stringCheck("Password", loginArray.get(1)) == true) {
						frameStart.setVisible(false);
						String username = loginArray.get(0).replaceFirst("Username:", "");
						String password = loginArray.get(1).replaceFirst("Password:", "");
						loginArray.set(0, username);
						loginArray.set(1, password);
						login = new LoginGUI(username, password, start);
					}
					else { 
						player = new Player(array);
						
						//Close frame and start the MainGUI/Game
						frameStart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frameStart.dispose();
						main = new MainGUI(player);
						if (reStart != null) {
							reStart = null;
						}
					}
				}
			}
		});
	}

	/**
	 * Initialize the contents of the StartGUI frame.
	 */
	private void initialize() {
		frameStart = new JFrame();
		frameStart.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 24));
		frameStart.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameStart.setTitle("TextRPG: Start Menu");
		frameStart.setBounds(100, 100, 450, 300);
		frameStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameStart.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("TextRPG");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		
		JLabel lblNewLabel_1 = new JLabel("By: Sawyer Kole");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnStart = new JButton("Start New Game");
		
		btnLoad = new JButton("Load Saved Game");
		
		fileChooser = new JFileChooser();
		
		txtRandomTextBox = new JTextField();
		txtRandomTextBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtRandomTextBox.setHorizontalAlignment(SwingConstants.CENTER);
		txtRandomTextBox.setText("v0.3 - The Unbalanced Update");
		txtRandomTextBox.setEditable(false);
		txtRandomTextBox.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frameStart.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(125)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnStart, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnLoad, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txtRandomTextBox, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addGap(32)
					.addComponent(btnStart)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLoad)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtRandomTextBox, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(33, Short.MAX_VALUE))
		);
		frameStart.getContentPane().setLayout(groupLayout);
	}
}
