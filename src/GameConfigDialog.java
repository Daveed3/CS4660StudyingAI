import static java.awt.GridBagConstraints.BOTH;
import static java.awt.GridBagConstraints.CENTER;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GameConfigDialog extends JDialog implements ActionListener,
ItemListener{

	/** Game to which the configuration will apply.*/
	private Game g;
	
	/** Name identifier for normal MiniMax Computer opponent. */
	private static final String COMPUTER_MiniMax = "MiniMax";

	/** Name identifier for Alpha-Beta pruning Computer opponent. */
	private static final String COMPUTER_Alpha_Beta = "MiniMax w/ Alpha Beta";
	
	/** Checkbox for whether the Computer opponent plays moves randomly */
	private JCheckBox Play_Random;
	
	/** Checkbox for whether the valid move generator takes advantage of symmetry */
	private JCheckBox Use_Symmetry;
	
	/** Checkbox for whether the game is re-drawn during AI searching for best move */
	private JCheckBox ReDraw;
	
	/** Dropdown to select the type for the AI player */
	private JComboBox Computer_PlayerType;
	
	/** Dropdown to select the depth limit for the AI player */
	private JComboBox DepthLimit;
	
	/** Makes a play button.*/
	JButton play;
	
	
	public GameConfigDialog(Game g){
		this.g = g;
		
		setSize(600, 330);
		setLayout(new GridBagLayout());
		setTitle("Game Configuration");
		setModal(true);
		setResizable(false);
		
		// string of instructions/welcome at top of box
		add(new JLabel("Please customize your game."), makeConstraints(1, 0, 0, 8, CENTER));
		
		JLabel playerType_label = new JLabel("Select Computer Player:");
		add(playerType_label,makeConstraints(1, 0, 2, 1, BOTH));
		
		Computer_PlayerType = new JComboBox();
		Computer_PlayerType.addItem(COMPUTER_MiniMax);
		Computer_PlayerType.addItem(COMPUTER_Alpha_Beta);
		Computer_PlayerType.setSelectedIndex(0); // sets default to normal MiniMax
		Computer_PlayerType.addItemListener(this);
		add(Computer_PlayerType, makeConstraints(1, 2, 2, 1, BOTH));
		
		JLabel playRandom_label = new JLabel("Computer Plays Random:");
		add(playRandom_label,makeConstraints(1, 0, 3, 1, BOTH));
		
		Play_Random = new JCheckBox();
		Play_Random.setSelected(false);
		add(Play_Random, makeConstraints(1, 2, 3, 1, CENTER));
		
		JLabel useSymmetry_label = new JLabel("Move Generator Uses Symmetry:");
		add(useSymmetry_label,makeConstraints(1, 0, 4, 1, BOTH));
		
		Use_Symmetry = new JCheckBox();
		Use_Symmetry.setSelected(false);
		add(Use_Symmetry, makeConstraints(1, 2, 4, 1, CENTER));
		
		
		JLabel reDraw_label = new JLabel("Show Board Re-Draws:");
		add(reDraw_label,makeConstraints(1, 0, 5, 1, BOTH));
		
		ReDraw = new JCheckBox();
		ReDraw.setSelected(false);
		add(ReDraw, makeConstraints(1, 2, 5, 1, CENTER));
		
		JLabel limit_label = new JLabel("Select Depth Limit:");
		add(limit_label,makeConstraints(1, 0, 6, 1, BOTH));
		
		DepthLimit = new JComboBox();
		DepthLimit.addItem(2);
		DepthLimit.addItem(4);
		DepthLimit.addItem(6);
		DepthLimit.addItem(8);
		DepthLimit.addItem(10);
		DepthLimit.setSelectedIndex(0); // sets default to normal MiniMax
		DepthLimit.addItemListener(this);
		add(DepthLimit, makeConstraints(1, 2, 6, 1, BOTH));
		
		play = new JButton("Play!");
		play.addActionListener(this);

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		add(play, makeConstraints(0, 7, 7, 1, BOTH));
		add(quit, makeConstraints(0, 6, 7, 1, BOTH));
		
		getRootPane().setDefaultButton(play);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Helper method to create GridBagConstraints objects.
	 */
	protected GridBagConstraints makeConstraints(int weight, int gridx,
			int gridy, int gridwidth, int fill) {
		GridBagConstraints result = new GridBagConstraints();
		result.weightx = weight;
		result.weighty = weight;
		result.gridx = gridx;
		result.gridy = gridy;
		result.gridwidth = gridwidth;
		result.gridheight = 1;
		result.fill = fill;
		return result;
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(play)) {
			if(Computer_PlayerType.getSelectedItem() == COMPUTER_MiniMax){
				//set player 2 to new minimax player
				this.g.setPlayer2(new MiniMaxPlayer(this.g));
			}
			else if(Computer_PlayerType.getSelectedItem() == COMPUTER_Alpha_Beta){
				//set player 2 to new alpha-beta player
				this.g.setPlayer2(new AlphaBetaPlayer(this.g));
			}
			
			if(Play_Random.isSelected()){
				this.g.setShuffleMoves(true);
			}
			else{
				this.g.setShuffleMoves(false);
			}
			
			if(Use_Symmetry.isSelected()){
				this.g.setUseSymmetry(true);
			}
			else{
				this.g.setUseSymmetry(false);
			}
			
			if(ReDraw.isSelected()){
				this.g.setReDraw(true);
			}
			else{
				this.g.setReDraw(false);
			}
			
			this.g.setDepthLimit((Integer) DepthLimit.getSelectedItem() + 1);
		}
		
		// close window.
		this.dispose();
		return;
		
	}

}
