
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class GameView extends JPanel {

	/** Reference to game */
	private Game g;
	
	/** Represents the entire view of the game */
	private JPanel gamePanel;
	
	/** Used in creating a new view of the board when the game starts or resets */
	private BoardView boardView;
	
	/** Used in creating a new view for Player 1 when the game starts or resets */
	private PlayerView p1View;

	/** Used in creating a new view for Player 2 when the game starts or resets */
	private PlayerView p2View;
	
	/** Frame that contains the view of the game */
	private JFrame frame;
	
	public static final int BOARD_HEIGHT = 546;
	public static final int BOARD_WIDTH = 546;
	public static final int GAME_WIDTH = 1050;

	public GameView(Game g){
		this.g = g;
		setLayout(new BorderLayout());
		setOpaque(false);
		
		this.frame = new JFrame();
		this.frame.setTitle("Nine Men's Morris");
		
		this.gamePanel = new JPanel(new BorderLayout());
		final int insetSize = 20;
		this.gamePanel.add(this, BorderLayout.CENTER);
		this.gamePanel.setBorder(BorderFactory.createEmptyBorder(insetSize,
				insetSize, insetSize, insetSize));
		this.gamePanel.setMinimumSize(new Dimension(GAME_WIDTH, BOARD_HEIGHT));
		this.gamePanel.setMaximumSize(new Dimension(GAME_WIDTH, BOARD_HEIGHT));
		this.gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, BOARD_HEIGHT));
		this.gamePanel.setOpaque(false);
		this.frame.add(gamePanel);
		
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		
		reset(0, 0);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}
	
	public PlayerView getP1View() {
		return this.p1View;
	}

	public PlayerView getP2View() {
		return this.p2View;
	}

	public BoardView getBoardView() {
		return this.boardView;
	}

	public void setBoardView(BoardView boardView) {
		this.boardView = boardView;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public void displayWinner(String winner){
		
		JOptionPane.showMessageDialog(null, winner + " is the winner!", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void displayMillMessage(String message){
		JOptionPane.showMessageDialog(null, message, "Mill on the board!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void displayInvalidMoveMessage(String message){
		JOptionPane.showMessageDialog(null, message, "Invalid Move", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void displayPhase(String phase){
		JOptionPane.showMessageDialog(null, "Current Phase: " + phase, "New Phase", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Called when the game has ended and a new game begins or when the game is updated. Creates a new
	 * BoardView and 2 PlayerViews
	 */
	public void reset(int nodesCreated, int nodesExpanded) {
		
		//reset player 1's view
		if(this.p1View != null){
			remove(this.p1View);
			this.p1View = new PlayerView(this.g.getGameState(), "PLAYER 1", Color.BLUE);
			add(this.p1View, BorderLayout.WEST);
			this.p1View.setCellViews(Color.BLUE);
		}
		else{
			this.p1View = new PlayerView(this.g.getGameState(), "PLAYER 1", Color.BLUE);
			add(this.p1View, BorderLayout.WEST);
			this.p1View.setCellViews(Color.BLUE);
		}
		
		//reset the boardview
		if (this.boardView != null) {
			remove(this.boardView);
			this.boardView = new BoardView(this.g);
			add(this.boardView, BorderLayout.CENTER);
		} 
		else {
			this.boardView = new BoardView(this.g);
			add(this.boardView, BorderLayout.CENTER);
		}
		
		//reset player 2's view
		if(this.p2View != null){
			remove(this.p2View);
			this.p2View = new PlayerView(this.g.getGameState(), "PLAYER 2", Color.RED);
			add(this.p2View, BorderLayout.EAST);
			this.p2View.setCellViews(Color.RED);
		}
		else{
			this.p2View = new PlayerView(this.g.getGameState(), "PLAYER 2", Color.RED);
			add(this.p2View, BorderLayout.EAST);
			this.p2View.setCellViews(Color.RED);
		}
		
		JLabel nodeLabel1 = new JLabel();
		nodeLabel1.setText("Nodes Created: " + nodesCreated);
		nodeLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		nodeLabel1.setMinimumSize(new Dimension(200, 100));
		nodeLabel1.setMaximumSize(new Dimension(200, 100));
		nodeLabel1.setPreferredSize(new Dimension(200, 100));
		nodeLabel1.setOpaque(true);
		nodeLabel1.setBackground(Color.RED);
		
		JLabel nodeLabel2 = new JLabel();
		nodeLabel2.setText("Nodes Expanded: " + nodesExpanded);
		nodeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		nodeLabel2.setMinimumSize(new Dimension(200, 100));
		nodeLabel2.setMaximumSize(new Dimension(200, 100));
		nodeLabel2.setPreferredSize(new Dimension(200, 100));
		nodeLabel2.setOpaque(true);
		nodeLabel2.setBackground(Color.RED);
		
		this.p2View.add(nodeLabel1);
		this.p2View.add(nodeLabel2);
	}
	
	
}
