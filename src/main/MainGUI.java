package main;

import utilities.SavePlayerData; //Imports utilities package

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import userData.*;

import java.awt.Color;

public class MainGUI {

	private JFrame frameTextrpg;
	private JTextArea textOutput;
	private JScrollPane scrollPane;
	private JButton btnShop;
	private JButton btnCharStats;
	private JButton btnBattle;
	private JButton btnCharInventory;
	private JButton btnSave;
	private JButton btnClearOutput;
	private JButton btnQuit;
	private JFileChooser fileChooser;
	
	private Player player;
	private MainGUI main;
	private EnemySelectorGUI eSelect;
	
	/**
	 * Default constructor. Shouldn't be used under normal circumstances.
	 * @wbp.parser.constructor
	 */
	public MainGUI() {
		initialize();
		createEvents();
		frameTextrpg.setVisible(true);
	}
	
	/**
	 * Constructor: used when Player object
	 * sets player and main variables to their associated local variables.
	 * Initializes GUI and creates events and then sets the frame to visible
	 * @param p - player object passed from caller
	 */
	public MainGUI(Player p) {
		player = p;
		main = this;
		initialize();
		createEvents();
		frameTextrpg.setVisible(true);
	}
	
	//Code for all events for GUI
	private void createEvents() {
		
		//Open shop
		btnShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textOutput.append("Welcome to the shop! What are you looking for today?\n");
			}
		});
		
		//Open player Inventory FIXME: Fix inventory to check ArrayList of items
		btnCharInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textOutput.append("Inventory: \nEMPTY\n");
			}
		});
		
		//Check Character Stats and print to textOutputArea
		btnCharStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textOutput.append("Character Stats:\n");
				ArrayList<String> statNames = new ArrayList<String>(); //Temporary arrayList
				statNames = player.getStatNames(); //Get statNames from the player object.
				ArrayList<String> playerStats = new ArrayList<String>(); //Temporaray arrayList
				playerStats = player.getPlayerData(); //Get playerData from the playerObject
				
				int i = 0;
				
				//Go through the statNames and playerData and print out the stat names and their values in the textOuput
				for (i = 0; i < statNames.size(); i = i + 3) {
					textOutput.append(statNames.get(i) + ": " + playerStats.get(i + 1) + "/" + playerStats.get(i) + "\n");
				}
			}
		});
		
		//dispose of current mainGUI and create battleGUI
		btnBattle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameTextrpg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frameTextrpg.dispose();
				eSelect = new EnemySelectorGUI(player); //FIXME: Memory leak: Find way to set old object to null
				if (main != null) {
					main = null;
				}
			}
		});
			
		//Clear text output for textOuput
		btnClearOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textOutput.setText("");
			}
		});
		
		//Save button pressed. Used to save player data
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Tell user to select a file and warn about overwriting that file
				SavePlayerData save;
				JOptionPane.showMessageDialog(frameTextrpg, "Please select a text file.\nWARNING: DATA WILL BE OVERWRITTEN");
				
				//Open file chooser
				int fileChooserVal = fileChooser.showOpenDialog(frameTextrpg);
				File readFile = null; //File variable to hold file name
				String isSaved = ""; //String to store if String return from SavePlayerData. Will contain text telling user if save completed or failed.
				
				if (fileChooserVal == JFileChooser.APPROVE_OPTION) {
					readFile = fileChooser.getSelectedFile();
				}
				
				if (readFile == null) {
					JOptionPane.showMessageDialog(frameTextrpg, "File menu closed or no file choosen\nData will not be saved");
				}
				else {
					save = new SavePlayerData();
					isSaved = save.saveData(readFile, player);
				}
				
				//Prints output from SavePlayerData. Tells user if save completed or failed.
				save = null;
				JOptionPane.showMessageDialog(frameTextrpg, isSaved);
			}
		});
		
		//Button to quit game
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameTextrpg.dispose();
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameTextrpg = new JFrame();
		frameTextrpg.setTitle("TextRPG: Town");
		frameTextrpg.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameTextrpg.setResizable(false);
		frameTextrpg.setBounds(100, 100, 530, 320);
		frameTextrpg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTextrpg.setLocationRelativeTo(null);
		
		scrollPane = new JScrollPane();
		
		btnShop = new JButton("Enter Shop");
		
		btnCharInventory = new JButton("Check Inventory");
		
		btnCharStats = new JButton("Check Stats");
		
		btnBattle = new JButton("Leave for Battle");
		
		btnSave = new JButton("Save");
		
		btnClearOutput = new JButton("Clear Output");
		
		fileChooser = new JFileChooser();
		
		btnQuit = new JButton("Quit");
		GroupLayout groupLayout = new GroupLayout(frameTextrpg.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnShop, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnCharStats, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(12))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnClearOutput)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCharInventory, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBattle, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnQuit, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(24, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnShop)
								.addComponent(btnCharInventory))
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCharStats)
								.addComponent(btnBattle))
							.addGap(183)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnClearOutput)
								.addComponent(btnSave)
								.addComponent(btnQuit))))
					.addContainerGap())
		);
		
		textOutput = new JTextArea();
		textOutput.setWrapStyleWord(true);
		textOutput.setLineWrap(true);
		textOutput.setEditable(false);
		scrollPane.setViewportView(textOutput);
		frameTextrpg.getContentPane().setLayout(groupLayout);
	}
}
