import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class HumanPlayer implements Player {

	private Color color;
	private Game g;
	private ArrayList<GameCell> gamePieces;
	private Boolean inFlight;
	private int cellCount;

	public HumanPlayer(Game game) {
		this.color = Color.BLUE;
		this.g = game;
		this.gamePieces = new ArrayList<GameCell>();
		this.inFlight = false;
		setGamePieces();
		setCellCount(9);
	}

	public Color getColor() {
		return this.color;
	}
	
	@Override
	public Boolean isInFlight() {
		return this.inFlight;
	}
	
	@Override
	public void setFlightStatus(Boolean inFlight){
		this.inFlight = inFlight;
	}

	public ArrayList<GameCell> getGamePieces() {
		return this.gamePieces;
	}

	public void setGamePieces() {
		this.gamePieces.clear();
		GameCell cell;
		
		for (int i = 24; i < 33; i++) {
			cell = new GameCell(this.color, i, false);
			this.gamePieces.add((i % 24), cell);
		}
	}

	public int getCellCount() {
		return this.cellCount;
	}

	public void setCellCount(int count) {
		this.cellCount = count;
	}

	public GameState playMove(Move m, Boolean reDrawBoard, Boolean showMillMessage) {
		
		//System.out.println("Human Player is trying to play move id = " + m.getId());
		
		GameCell startCell = null;
		GameCell destCell = this.g.getGameBoard().getAllCells().get(m.getDestCell().getID());
		
		if (this.g.getGameState().getPhase().equals(this.g.PHASE_POPULATE)) {
			
			startCell = this.gamePieces.get(m.getStartCell().getID() % 24);
			startCell.setWasPlayed(true);
			startCell.setColor(Color.WHITE);
			
			// Update the Destination Cell
			destCell.setColor(this.color);
			destCell.setOccupation(true);
			destCell.setOccupant(startCell);

			// Reset the sibling Cell's valid moves based on the new occupation
			if(destCell.getUpSibling() > -1){
				this.g.getGameBoard().getAllCells().get(destCell.getUpSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
			
			if(destCell.getRightSibling() > -1){
				this.g.getGameBoard().getAllCells().get(destCell.getRightSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
			
			if(destCell.getDownSibling() > -1){
				this.g.getGameBoard().getAllCells().get(destCell.getDownSibling()).setValidMoves(this.g.getGameBoard().getAllCells());
			}
			
			if(destCell.getLeftSibling() > -1){
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
			
			// Reset the sibling Cell's valid moves based on the new occupation
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

		}
		
		// Check for Mill
		if (this.g.getGameBoard().isMill(m, this.color) && this.g.getGameState().getP2Occupations().size() > 0) {
			
			//Remove player 2's 1st occupation on the board
			//located at position 0 in the player's ArrayList of occupations
			int cellID = this.g.getGameState().getP2Occupations().get(0).getID();
			
			
			//Remove a the selected cell from the board
			this.g.getGameBoard().clearCell(m, cellID, this.g.getPlayer2());
			
			//update the game state
			this.g.getGameState().updateState(reDrawBoard, 0, 0);
			
			if(showMillMessage){
				//this.g.getGameView().displayMillMessage("The id of the cell to remove is " + Integer.toString(cellID));
				this.g.getGameView().displayMillMessage("Player 1 played a mill!");
			}
			
		}
		
		// Reset current player
		this.g.setCurrentPlayer(this.g.getPlayer2());
		
		//update the game state
		this.g.getGameState().updateState(reDrawBoard, 0, 0);

		return this.g.getGameState();
	}
	
	/** Undo a move & resets the cells occupations and valid moves */
	public GameState undoMove(Move m, Boolean reDrawBoard) {
		
		GameCell startCell = null;
		GameCell destCell = this.g.getGameBoard().getAllCells().get(m.getDestCell().getID());
		
		//First check if the last move resulted in a mill.
		// If so, reset the last occupation for Player 2
		if (this.g.getGameBoard().isMill(m, this.color)) {
			
			//if the human player's last move resulted in a Mill, reset player 2's last occupation
			//GameCell lastOccupiedCell = this.g.getGameBoard().getP2LastOccupation();
			GameCell lastOccupiedCell = m.getCellRemoved();
			lastOccupiedCell.setColor(Color.RED);
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
		
		if(m.getStartCell().getID() >= 24){
			
			startCell = this.gamePieces.get(m.getStartCell().getID() % 24);
			
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
		this.g.getGameState().updateState(reDrawBoard, 0, 0);
		
		return this.g.getGameState();
	}

	@Override
	public void playBestMove() {
		//Never going to be called
		
	}

}
