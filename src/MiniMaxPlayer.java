import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class MiniMaxPlayer implements Player{

	private Color color;
	private Game g;
	private ArrayList<GameCell> gamePieces;
	private Boolean inFlight;
	private int cellCount;
	private int depthCount;
	private int nodesCreated;
	private int nodesExpanded;
	
	public MiniMaxPlayer(Game game) {
		this.color = Color.RED;
		this.g = game;
		this.gamePieces = new ArrayList<GameCell>();
		this.inFlight = false;
		this.nodesCreated = 0;
		this.nodesExpanded = 0;
		setGamePieces();
		setCellCount(9);
	}

	public Color getColor() {
		return this.color;
	}
	
	public Boolean isInFlight() {
		return this.inFlight;
	}
	
	public void setFlightStatus(Boolean inFlight){
		this.inFlight = inFlight;
	}
	
	public ArrayList<GameCell> getGamePieces() {
		return this.gamePieces;
	}

	public void setGamePieces() {
		this.gamePieces.clear();
		GameCell cell;
		
		for (int i = 33; i < 42; i++) {
			cell = new GameCell(this.color, i, false);
			this.gamePieces.add((i % 33), cell);
		}
	}
	
	public int getCellCount(){
		return this.cellCount;
	}
	
	public void setCellCount(int count){
		this.cellCount = count;
	}
	
	public GameState playMove(Move m, Boolean reDrawBoard, Boolean showMillMessage) {
		
		//System.out.println("MiniMax Player is trying to play move id = " + m.getId());
		
		GameCell startCell = null;
		GameCell destCell = this.g.getGameBoard().getAllCells().get(m.getDestCell().getID());
		
		if (this.g.getGameState().getPhase().equals(this.g.PHASE_POPULATE)) {
			
			startCell = this.gamePieces.get(m.getStartCell().getID() % 33);
			startCell.setWasPlayed(true);
			startCell.setColor(Color.WHITE);
			
			// Update the Destination Cell
			destCell.setColor(this.color);
			destCell.setOccupation(true);
			destCell.setOccupant(startCell);

			// Reset the sibling Cell's valid moves based on the new occupation
			if (destCell.getUpSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getRightSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getDownSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getLeftSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getLeftSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
			
			this.cellCount--;
			
		}

		else if (this.g.getGameState().getPhase().equals(this.g.PHASE_FIGHT) || this.g.getGameState().getPhase().equals(this.g.PHASE_FLIGHT)) {
			
			startCell = this.g.getGameBoard().getAllCells().get(m.getStartCell().getID());
			
			// Update the Destination Cell
			destCell.setColor(this.color);
			destCell.setOccupation(true);
			destCell.setOccupant(startCell.getOccupant());

			// Update the Start Cell
			startCell.setColor(Color.WHITE);
			startCell.setOccupation(false);
			startCell.setOccupant(null);
						
			// Reset the Start Cell's sibling Cell's valid moves based on the new occupation
			if (startCell.getUpSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (startCell.getRightSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (startCell.getDownSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (startCell.getLeftSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getLeftSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
			
			
			// Reset the Destination Cell's sibling cell's valid moves based on the new occupation
			if (destCell.getUpSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getRightSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getDownSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getLeftSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getLeftSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

		}
				
		// Check for Mill
		if (this.g.getGameBoard().isMill(m, this.color) && this.g.getGameState().getP1Occupations().size() > 0) {
		
			//Remove player 1's 1st occupation on the board
			//located at position 0 in the player's ArrayList of occupations
			int cellID = this.g.getGameState().getP1Occupations().get(0).getID();
			
			//Remove a the selected cell from the board
			this.g.getGameBoard().clearCell(m, cellID, this.g.getPlayer1());
			
			//update the game state
			this.g.getGameState().updateState(reDrawBoard, this.nodesCreated, this.nodesExpanded);
			
			if(showMillMessage){
				this.g.getGameView().displayMillMessage("Player 2 played a mill!");
			}
		}
		
		// Reset current player
		this.g.setCurrentPlayer(this.g.getPlayer1());
		
		//update the game state
		this.g.getGameState().updateState(reDrawBoard, this.nodesCreated, this.nodesExpanded);

		return this.g.getGameState();
	}
	
	/** Undo a move & resets the cells occupations and valid moves */
	public GameState undoMove(Move m, Boolean reDrawBoard) {
		
		GameCell startCell = null;
		GameCell destCell = this.g.getGameBoard().getAllCells().get(m.getDestCell().getID());
		
		//First check if the last move resulted in a mill.
		// If so, reset the last occupation for Player 1
		if (this.g.getGameBoard().isMill(m, this.color)) {
			
			//if the minimax player's last move resulted in a Mill, reset player 1's last occupation
			GameCell lastOccupiedCell = m.getCellRemoved();
			lastOccupiedCell.setColor(Color.BLUE);
			lastOccupiedCell.setOccupation(true);
			lastOccupiedCell.setOccupant(m.getCellRemovedOccupant());
			
			//Reset the valid moves of the siblings of the last occupied cell
			if (lastOccupiedCell.getUpSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(lastOccupiedCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (lastOccupiedCell.getRightSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(lastOccupiedCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (lastOccupiedCell.getDownSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(lastOccupiedCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (lastOccupiedCell.getLeftSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(lastOccupiedCell.getLeftSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
		}
		
		if(m.getStartCell().getID() >= 33){
			
			startCell = this.gamePieces.get(m.getStartCell().getID() % 33);
			
			// Update the Start Cell
			startCell.setWasPlayed(false);
			startCell.setColor(this.color);
			
			// Update the Destination Cell
			destCell.setColor(Color.WHITE);
			destCell.setOccupation(false);
			destCell.setOccupant(null);
			
			// Reset the sibling Cell's valid moves based on the new occupation
			if (destCell.getUpSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getRightSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getDownSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getLeftSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getLeftSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
			
			this.cellCount++;
			
		}
		else{
			startCell = this.g.getGameBoard().getAllCells().get(m.getStartCell().getID());

			// Update the Start Cell
			startCell.setColor(this.color);
			startCell.setOccupation(true);
			startCell.setOccupant(destCell.getOccupant());

			// Update the Destination Cell
			destCell.setColor(Color.WHITE);
			destCell.setOccupation(false);
			destCell.setOccupant(null);
			
			// Reset the Start Cell's sibling Cell's valid moves based on the new occupation
			if (startCell.getUpSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (startCell.getRightSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (startCell.getDownSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (startCell.getLeftSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(startCell.getLeftSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
			
			
			// Reset the Destination Cell's sibling cell's valid moves based on the new occupation
			if (destCell.getUpSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getRightSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getDownSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}

			if (destCell.getLeftSibling() > -1) {
				this.g.getGameBoard().getAllCells().get(destCell.getLeftSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
		}
		
		// Reset current player
		this.g.setCurrentPlayer(this);
		
		//update the game state
		this.g.getGameState().updateState(reDrawBoard, this.nodesCreated, this.nodesExpanded);
		
		return this.g.getGameState();
	}
	
	public void playBestMove(){
		//reset depth counter
		this.depthCount = 1;
		
		//reset nodes created
		this.nodesCreated = 0;
		
		//reset nodes expanded
		this.nodesExpanded = 0;
		
		//Find the best move
		Move m = getMaxMove(this.g.getGameState(), null);
		
		//Play the best move
		playMove(m, true, true);
		
		//update the game state & draw the board
		this.g.getGameState().updateState(true, this.nodesCreated, this.nodesExpanded);
		
		//continue the game
		this.g.setCurrentPlayer(this.g.getPlayer1());
	}
	
	private Move getMaxMove(GameState gState, Move lastMove_Player1){	
		
		if(this.g.isGameOver(gState, false) || this.depthCount == this.g.getDepthLimit()){
			return lastMove_Player1;
		}
		else{
			
			Move bestMove = new Move();
			bestMove.setValue(Double.NEGATIVE_INFINITY);
			
			GameState newState = null;
			
			CopyOnWriteArrayList<Move> p2ValidMoves = gState.generateP2ValidMoves();
			
			this.nodesCreated = this.nodesCreated + p2ValidMoves.size();
			
			if(this.g.doShuffleMoves()){
				//shuffle the valid moves
				Collections.shuffle(p2ValidMoves);
			}
			
			/*System.out.println("Player 2's valid moves are: ");
			for (Move m: p2ValidMoves){
				System.out.println(m.getID());
			}*/
			
			for (Move m: p2ValidMoves){
				
				this.nodesExpanded++;
				
				newState = playMove(m, this.g.doReDraw(), false);
				this.depthCount++;
				
				Move m2 = getMinMove(newState, m);
				
				m2.setValue(getMoveValue(m2));
				
				if(m2.getValue() > bestMove.getValue()){
					bestMove = m;
					bestMove.setValue(m2.getValue());
				}
				
				//undo move
				undoMove(m, this.g.doReDraw());
				
				//reduce depth
				this.depthCount--;
			}
			
			return bestMove;
		}
	}
	
	private Move getMinMove(GameState gState, Move lastMove_Player2){
		
		if(this.g.isGameOver(gState, false) || this.depthCount == this.g.getDepthLimit()){
			return lastMove_Player2;
		}
		else{
			
			Move bestMove = new Move();
			bestMove.setValue(Double.POSITIVE_INFINITY);
			
			GameState newState = null;
			
			CopyOnWriteArrayList<Move> p1ValidMoves = gState.generateP1ValidMoves();
			
			this.nodesCreated = this.nodesCreated + p1ValidMoves.size();
			
			if(this.g.doShuffleMoves()){
				//shuffle the valid moves
				Collections.shuffle(p1ValidMoves);
			}
			
			/*System.out.println("Player 1's valid moves are: ");
			for (Move m: p1ValidMoves){
				System.out.println(m.getId());
			}*/
			
			for (Move m: p1ValidMoves){
				
				this.nodesExpanded++;
				
				newState = this.g.getPlayer1().playMove(m, this.g.doReDraw(), false);
				this.depthCount++;
				
				Move m2 = getMaxMove(newState, m);
				
				m2.setValue(getMoveValue(m2));
				
				if(m2.getValue() < bestMove.getValue()){					
					bestMove = m;
					bestMove.setValue(m2.getValue());
				}
				
				//undo move
				this.g.getPlayer1().undoMove(m, this.g.doReDraw());
				
				//reduce depth
				this.depthCount--;
				
			}
			return bestMove;
		}
	}
	
	private int getMoveValue(Move mv){
		// m_red = whether the move resulted in a mill for AI player (1 for yes and 0 for no)
		// m_blue = whether the move resulted in a mill for Human player (1 for yes and 0 for no)

		// p_red = number of potential mills for the AIs next turn as a result of a particular move
		// where a potential mill is 3 sequential cells with 2 occupied by the opponent and one blank

		// p_blue = Number of potential mills on the next play for the opponent that would result from making a particular move
		// where a potential mill is 3 sequential cells with 2 occupied by the opponent and one blank
		
		int value  = 0;

		int m_blue = 0;
		int m_red = 0;
		int p_red = 0;
		int p_blue = 0;
		
		// Check for Mill for the Computer Player
		if (this.g.getGameBoard().isMill(mv, Color.RED)) {
			m_red = 1;
		}
		
		// Check for Mill for Human Player
		if (this.g.getGameBoard().isMill(mv, Color.BLUE)) {					
			m_blue = 1;
		}

		// check for potential Mills for the Computer Player
		p_red = this.g.getGameBoard().getPotentialMills(mv, Color.RED);
		
		// check for potential Mills for the Human Player
		p_blue = this.g.getGameBoard().getPotentialMills(mv, Color.BLUE);

		//value = (3 * m_red) - (6 * m_blue) + (2 * p_red) - (3 * p_blue);
		
		value = (3 * m_red) - (3 * m_blue) + (1 * p_red) - (1 * p_blue);
		
		return value;
	}

}
