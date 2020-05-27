package main;

/**
 * GUI that handles a battle between the player and an enemy. Allows the player to fight/run away from an enemy and handles giving the player experience/loot if they win.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.1
 */

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import enemyData.*;
import userData.Player;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BattleGUI {

	private JFrame frameBattleGUI;
	private JButton btnQuit;
	private JButton btnReturn;
	private JButton btnClearOutput;
	private JTextArea textOutput;
	private Player player;
	private BattleGUI battle;
	private MainGUI main;
	private Enemy enemy;
	private StartGUI start;
	private JProgressBar playerHealthBar;
	private JProgressBar playerPrayerBar;
	private JLabel lblEnemy;
	private JLabel label_1;
	private JProgressBar enemyHealthBar;
	private JButton btnAccurate;
	private JButton btnAggressive;
	private JButton btnControlled;
	private JButton btnDefensive;

	//Constructor: Default
	//Shouldn't be used under normal circumstances
	/**
	 * @wbp.parser.constructor
	 */
	public BattleGUI() {
		initialize();
		createEvents();
		frameBattleGUI.setVisible(true);
	}
	
	/**
	 * Constructor: used when the Player and enemy object are passed
	 * Sets the player, battle, and enemy variable to their respective objects. Initializes the creates events for the GUI
	 * GUI is then set to visible and the initial line for the fight is displayed in the textOutput
	 * @param p - passed player object. Main object for the player.
	 * @param e - passed enemy object. Main object for the current enemy.
	 */
	public BattleGUI(Player p, Enemy e) {
		player = p;
		battle = this;
		enemy = e;
		initialize();
		createEvents();
		frameBattleGUI.setVisible(true);
		textOutput.append("You find a " + enemy.getName() + " and prepare for combat.\nHow do you want to attack?\n");
	}

	//All events are located here
	private void createEvents() {
		
		//Player Accurate Attack
		btnAccurate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				player.attack("Accurate", enemy, battle);
				gameLoop();
			}
		});
		
		//PLayer Aggressive Attack
		btnAggressive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				player.attack("Aggressive", enemy, battle);
				gameLoop();
			}
		});
		
		//Player Controlled Attack
		btnControlled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				player.attack("Controlled", enemy, battle);
				gameLoop();
			}
		});
		
		//Player Defensive Attack
		btnDefensive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				player.attack("Defensive", enemy, battle);
				gameLoop();
			}
		});
		
		//Return to town. Disposes of battleGUI frame
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameBattleGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frameBattleGUI.dispose();
				main = new MainGUI(player); //FIXME: Memory leak: Find way to set old object to null
				if (battle != null) {
					battle = null;
					enemy = null;
				}
			}
		});
		
		//Clear textOutput area
		btnClearOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textOutput.setText("");
			}
		});
		
		//Button to quit game
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameBattleGUI.dispose();
			}
		});
	}
	
	/**
	 * Update player and enemy health/prayer bars. Run after player and enemy attacks to ensure that the bars are up to date
	 */
	private void updateBars() {
		playerHealthBar.setString(player.getSkill("cHitPoints") + "/" + player.getSkill("HitPoints"));
		playerHealthBar.setValue(Integer.parseInt(player.getSkill("cHitPoints")));
		enemyHealthBar.setString(enemy.getSkill("cHitPoints") + "/" + enemy.getSkill("HitPoints"));
		enemyHealthBar.setValue(Integer.parseInt(enemy.getSkill("cHitPoints")));
	}
	
	//Enemy action
	private void enemyAction() {
		enemy.attack(player, battle);
	}
	
	/**
	 * Runs a game loop after the player decides what to do. Updates health bars, checks if player or enemy is at 0 HitPoints and takes care of the enemyAction
	 */
	private void gameLoop() {
		while (true) {
			updateBars();
			//Check if enemy is dead or not. IF the enemy is dead, tell the player that they won and run playerWin()
			if (Integer.parseInt(enemy.getSkill("cHitPoints")) == 0) {
				JOptionPane.showMessageDialog(frameBattleGUI, "The enemy has been defeated!\nExperience has been given for your victory.\nWith that you head back to town.");
				playerWin();
				frameBattleGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frameBattleGUI.dispose();
				main = new MainGUI(player); //FIXME: Memory leak: Find way to set old object to null
				if (battle != null) {
					battle = null;
					enemy = null;
				}
				break;
			}
			enemyAction();
			updateBars();
			//Check if player is dead or not. If player is dead, tell them that they died and restart the game at the StartGUI
			if (Integer.parseInt(player.getSkill("cHitPoints")) == 0) {
				JOptionPane.showMessageDialog(frameBattleGUI, "You have been defeated!\nAs you fall to the ground you hear the faint echoing of laughter.\nGAME OVER");
				frameBattleGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frameBattleGUI.dispose();
				start = new StartGUI(start);
				if (battle != null) {
					battle = null;
					enemy = null;
				}
				break;
			}
			break;
		}
	}
	
	/**
	 * Print text to the textOutput JTextField
	 * @param text - text to be print to the textOutput
	 */
	public void printText(String text) { 
		textOutput.append(text);
	}
	
	/**
	 * Run this if player wins a fight. Add experience to skills based off a multiplier and enemy health. Finish by doing a level check and healing the player.
	 * As of now the experience for combat skills (Attack, Strength, Defense) is determined by multiplying the enemyHP value by 8.
	 * As of now the experience for HitPoints is determined by multiplying the enemyHP value by 4.
	 *FIXME: switch static numbers with a variable that can be found and updated easier
	 */
	private void playerWin() {
		//Temporary variable to store enemy max HP value
		int enemyHP = Integer.parseInt(enemy.getSkill("HitPoints"));
		//Set experience for each skill that needs experience
		player.setExp("Attack", enemyHP * 8);
		player.setExp("Strength", enemyHP * 8);
		player.setExp("Defense", enemyHP * 8);
		player.setExp("HitPoints", enemyHP * 4);
		
		//Run a check level for the player to see if any skills leveled up form gained experience. Afterwards, run a healPlayer to restore the player health to full.
		player.checkLevel();
		player.healPlayer();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameBattleGUI = new JFrame();
		frameBattleGUI.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameBattleGUI.setResizable(false);
		frameBattleGUI.setTitle("TextRPG: Battle Menu");
		frameBattleGUI.setBounds(100, 100, 450, 350);
		frameBattleGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameBattleGUI.setLocationRelativeTo(null);
		
		btnAggressive = new JButton("Aggressive");
		
		btnAccurate = new JButton("Accurate");
		
		btnDefensive = new JButton("Defensive");
		
		btnControlled = new JButton("Controlled");
		
		btnQuit = new JButton("Quit");
		
		btnReturn = new JButton("Return to town");
		
		btnClearOutput = new JButton("Clear Output");
		
		JScrollPane scrollPane = new JScrollPane();
		
		playerHealthBar = new JProgressBar();
		playerHealthBar.setMaximum(Integer.parseInt(player.getSkill("HitPoints")));
		playerHealthBar.setForeground(new Color(128, 0, 0));
		playerHealthBar.setBackground(Color.BLACK);
		playerHealthBar.setStringPainted(true);
		playerHealthBar.setString(player.getSkill("cHitPoints") + "/" + player.getSkill("HitPoints"));
		playerHealthBar.setValue(Integer.parseInt(player.getSkill("cHitPoints")));
		
		//FIXME: Set to dynamic once prayer is added
		playerPrayerBar = new JProgressBar();
		playerPrayerBar.setValue(1);
		playerPrayerBar.setStringPainted(true);
		playerPrayerBar.setString("1/1");
		playerPrayerBar.setMaximum(1);
		playerPrayerBar.setForeground(new Color(0, 191, 255));
		playerPrayerBar.setBackground(Color.BLACK);
		
		JLabel lblPlayer = new JLabel("PLAYER");
		
		lblEnemy = new JLabel("ENEMY");
		
		label_1 = new JLabel("---------------------------------------------");
		
		enemyHealthBar = new JProgressBar();
		enemyHealthBar.setBackground(new Color(0, 0, 0));
		enemyHealthBar.setForeground(new Color(128, 0, 0));
		enemyHealthBar.setMaximum(Integer.parseInt(enemy.getSkill("HitPoints")));
		enemyHealthBar.setMinimum(0);
		enemyHealthBar.setStringPainted(true);
		enemyHealthBar.setString(enemy.getSkill("cHitPoints") + "/" + enemy.getSkill("HitPoints"));
		enemyHealthBar.setValue(Integer.parseInt(enemy.getSkill("cHitPoints")));
		
		GroupLayout groupLayout = new GroupLayout(frameBattleGUI.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(6)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnClearOutput)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnQuit, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
									.addComponent(playerHealthBar, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
									.addComponent(playerPrayerBar, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(label_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addComponent(enemyHealthBar, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
									.addComponent(btnReturn, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
											.addComponent(btnControlled, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnAccurate, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(btnAggressive, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(btnDefensive, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
								.addGap(434))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(81)
								.addComponent(lblPlayer)
								.addContainerGap()))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(83)
							.addComponent(lblEnemy, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAggressive)
								.addComponent(btnAccurate))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnDefensive)
								.addComponent(btnControlled))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblPlayer)
							.addGap(4)
							.addComponent(playerHealthBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(playerPrayerBar, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEnemy)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(enemyHealthBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
							.addComponent(btnReturn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnQuit)
								.addComponent(btnClearOutput))))
					.addContainerGap())
		);
		
		textOutput = new JTextArea();
		textOutput.setWrapStyleWord(true);
		textOutput.setLineWrap(true);
		textOutput.setEditable(false);
		scrollPane.setViewportView(textOutput);
		frameBattleGUI.getContentPane().setLayout(groupLayout);
	}
}
