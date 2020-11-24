import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class PlayerView extends JPanel {
	
	/** Sets the section header for the player */
	JLabel header;
	
	/** The individual views representing the squares on the board. */
	private SquareView[][] squareViews;
	
	/** The view that holds the player's game pieces */
	JPanel gamePiecePanel;
	
	GameState gState;
	
	private static final int PLAYERVIEW_WIDTH = 252;
	private static final int PLAYERVIEW_HEIGHT = 234;
	private static final int SQUARE_HEIGHT = 39;
	
	public PlayerView(GameState gState, String title, Color color){

		setMinimumSize(new Dimension(PLAYERVIEW_WIDTH, PLAYERVIEW_HEIGHT));
		setMaximumSize(new Dimension(PLAYERVIEW_WIDTH, PLAYERVIEW_HEIGHT));
		setPreferredSize(new Dimension(PLAYERVIEW_WIDTH, PLAYERVIEW_HEIGHT));
		setOpaque(false);
		
		this.gState = gState;
		this.header = new JLabel();
		this.header.setText(title);
		this.header.setHorizontalAlignment(SwingConstants.CENTER);
		this.header.setMinimumSize(new Dimension(PLAYERVIEW_WIDTH, SQUARE_HEIGHT));
		this.header.setMaximumSize(new Dimension(PLAYERVIEW_WIDTH, SQUARE_HEIGHT));
		this.header.setPreferredSize(new Dimension(PLAYERVIEW_WIDTH, SQUARE_HEIGHT));
		this.header.setOpaque(true);
		
		//This code is used to indicate the current player visually
		if(this.gState.getGame().getCurrentPlayer() == this.gState.getGame().getPlayer1() && color == Color.BLUE){
			this.header.setBackground(Color.BLUE);
		}
		else if(this.gState.getGame().getCurrentPlayer() == this.gState.getGame().getPlayer2() && color == Color.RED){
			this.header.setBackground(Color.RED);
		}
		
		add(this.header);
		
		this.gamePiecePanel = new JPanel();
		this.gamePiecePanel.setMinimumSize(new Dimension(PLAYERVIEW_WIDTH, PLAYERVIEW_HEIGHT - SQUARE_HEIGHT));
		this.gamePiecePanel.setMaximumSize(new Dimension(PLAYERVIEW_WIDTH, PLAYERVIEW_HEIGHT - SQUARE_HEIGHT));
		this.gamePiecePanel.setPreferredSize(new Dimension(PLAYERVIEW_WIDTH, PLAYERVIEW_HEIGHT - SQUARE_HEIGHT));
		this.gamePiecePanel.setLayout(new GridLayout(3, 3, 0, 0));
		this.gamePiecePanel.setOpaque(false);
		add(this.gamePiecePanel);
		
		this.squareViews = new SquareView[3][3];
		setGamePiecePanel(color);
	}
	
	/** Sets the game pieces for each player within the view */
	public void setGamePiecePanel(Color color){
		CellView cv;
		int gamePiecePosition = 0;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				
				this.squareViews[i][j] = new SquareView(new Square(this.gState.getGame().getGameBoard(), new Point(j, i)));
				
				if(color == Color.BLUE){
					cv = new CellView(gState.getGame().getPlayer1().getGamePieces().get(gamePiecePosition).getColor(), j, i);	
				}
				else{
					cv = new CellView(gState.getGame().getPlayer2().getGamePieces().get(gamePiecePosition).getColor(), j, i);	
				}
				this.squareViews[i][j].setCellView(cv);
				this.gamePiecePanel.add(this.squareViews[i][j]);
				
				gamePiecePosition ++;
			}
		}		
	}
	
	/** Associates the player's game pieces with the proper squares within the Game piece panel */
	public void setCellViews(Color color){
		if(color == Color.BLUE){
			ArrayList<GameCell> player1Pieces = this.gState.getGame().getPlayer1().getGamePieces();
			
			player1Pieces.get(0).setBoardSquare(this.squareViews[0][0].getSquare());
			player1Pieces.get(0).getBoardSquare().setGameCell(player1Pieces.get(0));
			this.squareViews[0][0].getCellView().setCellViewLabel(player1Pieces.get(0).getID());
			
			player1Pieces.get(1).setBoardSquare(this.squareViews[0][1].getSquare());
			player1Pieces.get(1).getBoardSquare().setGameCell(player1Pieces.get(1));
			this.squareViews[0][1].getCellView().setCellViewLabel(player1Pieces.get(1).getID());
			
			player1Pieces.get(2).setBoardSquare(this.squareViews[0][2].getSquare());
			player1Pieces.get(2).getBoardSquare().setGameCell(player1Pieces.get(2));
			this.squareViews[0][2].getCellView().setCellViewLabel(player1Pieces.get(2).getID());
			
			player1Pieces.get(3).setBoardSquare(this.squareViews[1][0].getSquare());
			player1Pieces.get(3).getBoardSquare().setGameCell(player1Pieces.get(3));
			this.squareViews[1][0].getCellView().setCellViewLabel(player1Pieces.get(3).getID());
			
			player1Pieces.get(4).setBoardSquare(this.squareViews[1][1].getSquare());
			player1Pieces.get(4).getBoardSquare().setGameCell(player1Pieces.get(4));
			this.squareViews[1][1].getCellView().setCellViewLabel(player1Pieces.get(4).getID());
			
			player1Pieces.get(5).setBoardSquare(this.squareViews[1][2].getSquare());
			player1Pieces.get(5).getBoardSquare().setGameCell(player1Pieces.get(5));
			this.squareViews[1][2].getCellView().setCellViewLabel(player1Pieces.get(5).getID());
			
			player1Pieces.get(6).setBoardSquare(this.squareViews[2][0].getSquare());
			player1Pieces.get(6).getBoardSquare().setGameCell(player1Pieces.get(6));
			this.squareViews[2][0].getCellView().setCellViewLabel(player1Pieces.get(6).getID());
			
			player1Pieces.get(7).setBoardSquare(this.squareViews[2][1].getSquare());
			player1Pieces.get(7).getBoardSquare().setGameCell(player1Pieces.get(7));
			this.squareViews[2][1].getCellView().setCellViewLabel(player1Pieces.get(7).getID());
			
			player1Pieces.get(8).setBoardSquare(this.squareViews[2][2].getSquare());
			player1Pieces.get(8).getBoardSquare().setGameCell(player1Pieces.get(8));
			this.squareViews[2][2].getCellView().setCellViewLabel(player1Pieces.get(8).getID());
			
		}
		else{
			ArrayList<GameCell> player2Pieces = this.gState.getGame().getPlayer2().getGamePieces();
			
			player2Pieces.get(0).setBoardSquare(this.squareViews[0][0].getSquare());
			player2Pieces.get(0).getBoardSquare().setGameCell(player2Pieces.get(0));
			this.squareViews[0][0].getCellView().setCellViewLabel(player2Pieces.get(0).getID());
			
			player2Pieces.get(1).setBoardSquare(this.squareViews[0][1].getSquare());
			player2Pieces.get(1).getBoardSquare().setGameCell(player2Pieces.get(1));
			this.squareViews[0][1].getCellView().setCellViewLabel(player2Pieces.get(1).getID());
			
			player2Pieces.get(2).setBoardSquare(this.squareViews[0][2].getSquare());
			player2Pieces.get(2).getBoardSquare().setGameCell(player2Pieces.get(2));
			this.squareViews[0][2].getCellView().setCellViewLabel(player2Pieces.get(2).getID());
			
			player2Pieces.get(3).setBoardSquare(this.squareViews[1][0].getSquare());
			player2Pieces.get(3).getBoardSquare().setGameCell(player2Pieces.get(3));
			this.squareViews[1][0].getCellView().setCellViewLabel(player2Pieces.get(3).getID());
			
			player2Pieces.get(4).setBoardSquare(this.squareViews[1][1].getSquare());
			player2Pieces.get(4).getBoardSquare().setGameCell(player2Pieces.get(4));
			this.squareViews[1][1].getCellView().setCellViewLabel(player2Pieces.get(4).getID());
			
			player2Pieces.get(5).setBoardSquare(this.squareViews[1][2].getSquare());
			player2Pieces.get(5).getBoardSquare().setGameCell(player2Pieces.get(5));
			this.squareViews[1][2].getCellView().setCellViewLabel(player2Pieces.get(5).getID());
			
			player2Pieces.get(6).setBoardSquare(this.squareViews[2][0].getSquare());
			player2Pieces.get(6).getBoardSquare().setGameCell(player2Pieces.get(6));
			this.squareViews[2][0].getCellView().setCellViewLabel(player2Pieces.get(6).getID());
			
			player2Pieces.get(7).setBoardSquare(this.squareViews[2][1].getSquare());
			player2Pieces.get(7).getBoardSquare().setGameCell(player2Pieces.get(7));
			this.squareViews[2][1].getCellView().setCellViewLabel(player2Pieces.get(7).getID());
			
			player2Pieces.get(8).setBoardSquare(this.squareViews[2][2].getSquare());
			player2Pieces.get(8).getBoardSquare().setGameCell(player2Pieces.get(8));
			this.squareViews[2][2].getCellView().setCellViewLabel(player2Pieces.get(8).getID());
		}
	}
	
	
	@Override
	/** 
	 * Draws the player view after calling super.paintChildren().
	 */
	public void paintChildren(Graphics g) {
		super.paintChildren(g);
	}

}
