package main;

/**
 * GUI that handles enemy selection. Used when the player leaves for battle. Allowws the user to select the type of enemy that theyw ould like to fight.
 * @author Sawyer Kole
 * @version 0.3
 * @since 0.1
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import enemyData.*;
import userData.Player;

public class EnemySelectorGUI {

	private JFrame frameEnemySelector;
	private JLabel lblNewLabel;
	private JButton btnReturn;
	private JButton btnQuit;
	private JButton btnOrc;
	private JButton btnDragon;
	private JButton btnGoblin;
	private JButton btnHobGoblin;
	
	private Player player;
	private EnemySelectorGUI eSelect;
	private MainGUI main;
	private Enemy enemy;
	private BattleGUI battle;

	//Default Constructor. Shouldn't be used under normal circumstances
	/**
	 * @wbp.parser.constructor
	 */
	public EnemySelectorGUI() {
		initialize();
		createEvents();
		frameEnemySelector.setVisible(true);
	}

	/**
	 * Constructor for if Player object is passed.
	 * Sets both eSelect and player variables and then initializes frame and creates events. Finally sets frame to visible.
	 * @param p - player object that was passed from the caller. Main player object used to store player data.
	 */
	public EnemySelectorGUI(Player p) {
		eSelect = this;
		player = p;
		initialize();
		createEvents();
		frameEnemySelector.setVisible(true);
	}
	
	//Creates and contains code for all events
	private void createEvents() {
		//Return button pressed. Return user to town.
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameEnemySelector.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frameEnemySelector.dispose();
				main = new MainGUI(player);
				if (eSelect != null) {
					eSelect = null;
				}
			}
		});
		
		//Quit button pressed. Close program.
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameEnemySelector.dispose();
			}
		});
		
		//Goblin button pressed. Start battle with this enemy type.
		btnGoblin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Goblin goblin = new Goblin();
				enemy = goblin;
				startBattle();
			}
		});
		
		//HobGoblin button pressed. Start battle with this enemy type.
		btnHobGoblin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HobGoblin hobGoblin = new HobGoblin();
				enemy = hobGoblin;
				startBattle();
			}
		});
		
		//Orc button pressed. Start battle with this enemy type.
		btnOrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Orc orc = new Orc();
				enemy = orc;
				startBattle();
			}
		});
		
		//Dragon button pressed. Start battle with this enemy type.
		btnDragon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Dragon dragon = new Dragon();
				enemy = dragon;
				startBattle();
			}
		});
	}
	
	/**
	 * Create BattleGUI object and dispose EnemySelectorObjectGUI. Uses passed enemy object and player object to create the battle.
	 */
	private void startBattle() {
		frameEnemySelector.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameEnemySelector.dispose();
		battle = new BattleGUI(player, enemy);
		if (eSelect != null) {
			eSelect = null;
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameEnemySelector = new JFrame();
		frameEnemySelector.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameEnemySelector.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("Select an enemy to fight");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 274, 14);
		frameEnemySelector.getContentPane().add(lblNewLabel);
		
		btnGoblin = new JButton("Goblin");
		btnGoblin.setBounds(44, 36, 100, 23);
		frameEnemySelector.getContentPane().add(btnGoblin);
		
		btnHobGoblin = new JButton("HobGoblin");
		btnHobGoblin.setBounds(154, 36, 100, 23);
		frameEnemySelector.getContentPane().add(btnHobGoblin);
		
		btnOrc = new JButton("Orc");
		btnOrc.setBounds(44, 70, 100, 23);
		frameEnemySelector.getContentPane().add(btnOrc);
		
		btnDragon = new JButton("Dragon");
		btnDragon.setBounds(154, 70, 100, 23);
		frameEnemySelector.getContentPane().add(btnDragon);
		
		btnQuit = new JButton("Quit");
		btnQuit.setBounds(154, 137, 130, 23);
		frameEnemySelector.getContentPane().add(btnQuit);
		
		btnReturn = new JButton("Return to Town");
		btnReturn.setBounds(10, 137, 134, 23);
		frameEnemySelector.getContentPane().add(btnReturn);
		frameEnemySelector.setBackground(Color.BLACK);
		frameEnemySelector.setTitle("TextRPG: Enemy Selector");
		frameEnemySelector.setResizable(false);
		frameEnemySelector.setBounds(100, 100, 300, 200);
		frameEnemySelector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameEnemySelector.setLocationRelativeTo(null);
	}
}
