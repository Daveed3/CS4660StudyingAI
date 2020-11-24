import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

public class GameBoard {

	private Game g;

	/** Array of squares that represents board */
	private Square[][] squares;

	/** A list of the valid moves for player 1 */
	private CopyOnWriteArrayList<Move> p1ValidMoves;
	
	/** A list of the valid moves for player 2 */
	private CopyOnWriteArrayList<Move> p2ValidMoves;

	/** A list of all valid moves for the game */
	private CopyOnWriteArrayList<Move> allValidMoves;

	/** A list of the occupied cells for a specific player */
	private ArrayList<GameCell> occupations;

	/** A collection of all the cells that are occupied */
	private ArrayList<GameCell> allOccupations;

	private ArrayList<GameCell> allCells;
	private ArrayList<Mill> allMills;

	public GameBoard(Game g) {
		this.g = g;
		this.p1ValidMoves = new CopyOnWriteArrayList<Move>();
		this.p2ValidMoves = new CopyOnWriteArrayList<Move>();
		this.allValidMoves = new CopyOnWriteArrayList<Move>();
		this.occupations = new ArrayList<GameCell>();
		this.allOccupations = new ArrayList<GameCell>();
		this.allCells = new ArrayList<GameCell>();
		this.allMills = new ArrayList<Mill>();

		createCells();
		setSiblings();
		setMills();
		setSquares();
		setCellViews();
	}

	// Sets all Squares on the board to their initial values.
	private void setSquares() {
		this.squares = new Square[7][7];
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				Square temp = new Square(this, new Point(j, i));
				this.squares[i][j] = temp;
			}
		}
	}

	public Game getGame() {
		return g;
	}

	// Associate the square with the appropriate cell views There are 24 cell
	// views associated with each of the 24 Game Cells
	private void setCellViews() {

		// Outer Rectangle
		this.allCells.get(0).setBoardSquare(this.squares[0][0]);
		this.allCells.get(0).getBoardSquare().setGameCell(0);
		this.allCells.get(1).setBoardSquare(this.squares[0][3]);
		this.allCells.get(1).getBoardSquare().setGameCell(1);
		this.allCells.get(2).setBoardSquare(this.squares[0][6]);
		this.allCells.get(2).getBoardSquare().setGameCell(2);
		this.allCells.get(3).setBoardSquare(this.squares[3][6]);
		this.allCells.get(3).getBoardSquare().setGameCell(3);
		this.allCells.get(4).setBoardSquare(this.squares[6][6]);
		this.allCells.get(4).getBoardSquare().setGameCell(4);
		this.allCells.get(5).setBoardSquare(this.squares[6][3]);
		this.allCells.get(5).getBoardSquare().setGameCell(5);
		this.allCells.get(6).setBoardSquare(this.squares[6][0]);
		this.allCells.get(6).getBoardSquare().setGameCell(6);
		this.allCells.get(7).setBoardSquare(this.squares[3][0]);
		this.allCells.get(7).getBoardSquare().setGameCell(7);

		// Outer Rectangle
		this.allCells.get(8).setBoardSquare(this.squares[1][1]);
		this.allCells.get(8).getBoardSquare().setGameCell(8);
		this.allCells.get(9).setBoardSquare(this.squares[1][3]);
		this.allCells.get(9).getBoardSquare().setGameCell(9);
		this.allCells.get(10).setBoardSquare(this.squares[1][5]);
		this.allCells.get(10).getBoardSquare().setGameCell(10);
		this.allCells.get(11).setBoardSquare(this.squares[3][5]);
		this.allCells.get(11).getBoardSquare().setGameCell(11);
		this.allCells.get(12).setBoardSquare(this.squares[5][5]);
		this.allCells.get(12).getBoardSquare().setGameCell(12);
		this.allCells.get(13).setBoardSquare(this.squares[5][3]);
		this.allCells.get(13).getBoardSquare().setGameCell(13);
		this.allCells.get(14).setBoardSquare(this.squares[5][1]);
		this.allCells.get(14).getBoardSquare().setGameCell(14);
		this.allCells.get(15).setBoardSquare(this.squares[3][1]);
		this.allCells.get(15).getBoardSquare().setGameCell(15);

		// Outer Rectangle
		this.allCells.get(16).setBoardSquare(this.squares[2][2]);
		this.allCells.get(16).getBoardSquare().setGameCell(16);
		this.allCells.get(17).setBoardSquare(this.squares[2][3]);
		this.allCells.get(17).getBoardSquare().setGameCell(17);
		this.allCells.get(18).setBoardSquare(this.squares[2][4]);
		this.allCells.get(18).getBoardSquare().setGameCell(18);
		this.allCells.get(19).setBoardSquare(this.squares[3][4]);
		this.allCells.get(19).getBoardSquare().setGameCell(19);
		this.allCells.get(20).setBoardSquare(this.squares[4][4]);
		this.allCells.get(20).getBoardSquare().setGameCell(20);
		this.allCells.get(21).setBoardSquare(this.squares[4][3]);
		this.allCells.get(21).getBoardSquare().setGameCell(21);
		this.allCells.get(22).setBoardSquare(this.squares[4][2]);
		this.allCells.get(22).getBoardSquare().setGameCell(22);
		this.allCells.get(23).setBoardSquare(this.squares[3][2]);
		this.allCells.get(23).getBoardSquare().setGameCell(23);
	}

	public Square[][] getSquares() {
		return this.squares;
	}

	// Returns the valid moves for the specified color
	public CopyOnWriteArrayList<Move> generateValidMoves(Color color) {
		
		if (g.getGameState().getPhase().equals(this.g.PHASE_POPULATE)) {
			
			if(this.g.getUseSymmetry()){
				GameCell firstUnPlayed = null;
				
				if(color == Color.BLUE){
					
					this.p1ValidMoves.clear();
					
					for (GameCell gp : this.g.getPlayer1().getGamePieces()) {
						
						if(!gp.getWasPlayed()){
							firstUnPlayed = gp;
							for (GameCell gc : this.allCells) {
								if (gc.getColor() == Color.WHITE) {
									Move m = new Move(firstUnPlayed, gc, Integer.toString(firstUnPlayed.getID()) + "-"+ Integer.toString(gc.getID()));
									this.p1ValidMoves.add(m);
								}
							}
							break;
						}
					}
				}
				
				else if(color == Color.RED){
					
					this.p2ValidMoves.clear();
						
					for (GameCell gp : this.g.getPlayer2().getGamePieces()) {
						
						if(!gp.getWasPlayed()){
							//System.out.println("The start id of the next move is " + gp.getID());
							firstUnPlayed = gp;
							for (GameCell gc : this.allCells) {
								if (gc.getColor() == Color.WHITE) {
									Move m = new Move(firstUnPlayed, gc, Integer.toString(firstUnPlayed.getID()) + "-"+ Integer.toString(gc.getID()));
									this.p2ValidMoves.add(m);
								}
							}
							break;
						}
					}
				}
			}
			
			else{
				if(color == Color.BLUE){
					
					this.p1ValidMoves.clear();
					
					for (GameCell gc : this.allCells) {
						if (gc.getColor() == Color.WHITE) {
							for (GameCell gp : this.g.getPlayer1().getGamePieces()) {
								if(gp != null && gp.getColor() != Color.WHITE){
									Move m = new Move(gp, gc, Integer.toString(gp.getID()) + "-"+ Integer.toString(gc.getID()));
									this.p1ValidMoves.add(m);
								}
							}
						}
					}
				}
				
				else if(color == Color.RED){
					
					this.p2ValidMoves.clear();
					
					for (GameCell gc : this.allCells) {
						if (gc.getColor() == Color.WHITE) {
							for (GameCell gp : this.g.getPlayer2().getGamePieces()) {
								if(gp != null && gp.getColor() != Color.WHITE){
									Move m = new Move(gp, gc, Integer.toString(gp.getID()) + "-"+ Integer.toString(gc.getID()));
									this.p2ValidMoves.add(m);
								}
							}
						}
					}
				}
			}			
		}
		
		else if (g.getGameState().getPhase().equals(this.g.PHASE_FIGHT)) {
			
			if(color == Color.BLUE){
				this.p1ValidMoves.clear();
			}
			else{
				this.p2ValidMoves.clear();
			}
			
			for (GameCell gc : this.allCells) {
				if (gc.getColor() == color) {
					for (int i : gc.getValidMoves()) {
						
						Move m = new Move(gc, this.allCells.get(i),Integer.toString(gc.getID()) + "-" + Integer.toString(this.allCells.get(i).getID()));
						
						if(color == Color.BLUE){
							this.p1ValidMoves.add(m);
						}
						else{
							this.p2ValidMoves.add(m);
						}
						
					}
				}
			}
		}

		else if (this.g.getGameState().getPhase().equals(this.g.PHASE_FLIGHT)) {

			if (this.g.getPlayer1().isInFlight()) {

				if (color == Color.BLUE) {
					this.p1ValidMoves.clear();

					for (GameCell gc : this.allCells) {
						if (!gc.isOccupied()) {
							for (GameCell gc2 : this.g.getGameState().getP1Occupations()) {
								Move m = new Move(gc2, gc, Integer.toString(gc2.getID())+ "-"
										+ Integer.toString(gc.getID()));
								this.p1ValidMoves.add(m);
							}
						}
					}
				} 
				else {

					this.p2ValidMoves.clear();

					for (GameCell gc : this.allCells) {
						if (gc.getColor() == color) {
							for (int i : gc.getValidMoves()) {
								Move m = new Move(gc,this.allCells.get(i),Integer.toString(gc.getID())+ "-"
												+ Integer.toString(this.allCells.get(i).getID()));
								this.p2ValidMoves.add(m);
							}
						}
					}
				}

			}

			else if (this.g.getPlayer2().isInFlight()){

				if (color == Color.BLUE) {
					
					this.p1ValidMoves.clear();
					
					for (GameCell gc : this.allCells) {
						if (gc.getColor() == color) {
							for (int i : gc.getValidMoves()) {
								Move m = new Move(gc,this.allCells.get(i),Integer.toString(gc.getID())+ "-"
												+ Integer.toString(this.allCells.get(i).getID()));
								this.p1ValidMoves.add(m);
							}
						}
					}					
				} 
				else {

					this.p2ValidMoves.clear();
					
					for (GameCell gc : this.allCells) {
						if (!gc.isOccupied()) {
							for (GameCell gc2 : this.g.getGameState().getP2Occupations()) {
								Move m = new Move(gc2, gc, Integer.toString(gc2.getID())+ "-"
										+ Integer.toString(gc.getID()));
								this.p2ValidMoves.add(m);
							}
						}
					}
				}
			}
		}

			if (color == Color.BLUE) {
				return this.p1ValidMoves;
			} else {
				return this.p2ValidMoves;
			}
	}

	public CopyOnWriteArrayList<Move> generateAllValidMoves() {
		this.allValidMoves.clear();
		for (GameCell gc : this.allCells) {
			for (int i : gc.getValidMoves()) {
				Move m = new Move(gc, allCells.get(i), Integer.toString(gc
						.getID())
						+ "-"
						+ Integer.toString(allCells.get(i).getID()));
				this.allValidMoves.add(m);
			}
		}
		return this.allValidMoves;
	}

	//Returns the occupations for the specified color.
	public ArrayList<GameCell> getOccupations(Color color) {
		this.occupations.clear();
		
		for (GameCell gc : this.allCells) {
			if (gc.getColor() == color && gc.isOccupied()) {
				this.occupations.add(gc);
			}
		}
		return this.occupations;
	}

	public ArrayList<GameCell> getAllOccupations() {
		this.allOccupations.clear();
		for (GameCell gc : this.allCells) {
			if (gc.isOccupied()) {
				this.allOccupations.add(gc);
			}
		}
		return this.allOccupations;
	}

	//Initializes all the game cells
	public void createCells() {
		GameCell cell;
		for (int i = 0; i < 24; i++) {
			cell = new GameCell(Color.WHITE, i, false);
			this.allCells.add(i, cell);
		}
	}

	public ArrayList<GameCell> getAllCells() {
		return this.allCells;
	}
	
	public void resetAllCells(ArrayList<GameCell> newCells) {
		this.allCells = newCells;
	}

	// Sets the up, right, down & left siblings (in that order) for each sell in the game
	public void setSiblings() {
		// Outer rectangle
		this.allCells.get(0).setSiblings(-1, 1, 7, -1);
		this.allCells.get(1).setSiblings(-1, 2, 9, 0);
		this.allCells.get(2).setSiblings(-1, -1, 3, 1);
		this.allCells.get(3).setSiblings(2, -1, 4, 11);
		this.allCells.get(4).setSiblings(3, -1, -1, 5);
		this.allCells.get(5).setSiblings(13, 4, -1, 6);
		this.allCells.get(6).setSiblings(7, 5, -1, -1);
		this.allCells.get(7).setSiblings(0, 15, 6, -1);

		// Middle rectangle
		this.allCells.get(8).setSiblings(-1, 9, 15, -1);
		this.allCells.get(9).setSiblings(1, 10, 17, 8);
		this.allCells.get(10).setSiblings(-1, -1, 11, 9);
		this.allCells.get(11).setSiblings(10, 3, 12, 19);
		this.allCells.get(12).setSiblings(11, -1, -1, 13);
		this.allCells.get(13).setSiblings(21, 12, 5, 14);
		this.allCells.get(14).setSiblings(15, 13, -1, -1);
		this.allCells.get(15).setSiblings(8, 23, 14, 7);

		// Inner rectangle
		this.allCells.get(16).setSiblings(-1, 17, 23, -1);
		this.allCells.get(17).setSiblings(9, 18, -1, 16);
		this.allCells.get(18).setSiblings(-1, -1, 19, 17);
		this.allCells.get(19).setSiblings(18, 11, 20, -1);
		this.allCells.get(20).setSiblings(19, -1, -1, 21);
		this.allCells.get(21).setSiblings(-1, 20, 13, 22);
		this.allCells.get(22).setSiblings(23, 21, -1, -1);
		this.allCells.get(23).setSiblings(16, -1, 22, 15);

		setValidMoves();
	}

	// Sets the initial set of valid moves for each cell on an empty board
	public void setValidMoves() {
		for (GameCell gc : this.allCells) {
			gc.setValidMoves(this.allCells);
		}
	}

	//Sets all the possible mills for the board and associates each cell with possible mills it could be involved in
	public void setMills() {
		this.allMills.add(new Mill("012", 0, 1, 2));
		this.allMills.add(new Mill("076", 0, 7, 6));
		this.allMills.add(new Mill("1917", 1, 9, 17));
		this.allMills.add(new Mill("234", 2, 3, 4));
		this.allMills.add(new Mill("31119", 3, 11, 19));
		this.allMills.add(new Mill("456", 4, 5, 6));
		this.allMills.add(new Mill("51321", 5, 13, 21));
		this.allMills.add(new Mill("71523", 7, 15, 23));
		this.allMills.add(new Mill("8910", 8, 9, 10));
		this.allMills.add(new Mill("81514", 8, 15, 14));
		this.allMills.add(new Mill("101112", 10, 11, 12));
		this.allMills.add(new Mill("121314", 12, 13, 14));
		this.allMills.add(new Mill("161718", 16, 17, 18));
		this.allMills.add(new Mill("162322", 16, 23, 22));
		this.allMills.add(new Mill("181920", 18, 19, 20));
		this.allMills.add(new Mill("202122", 20, 21, 22));

		// Associate the possible mills with each cell
		for (GameCell c : this.allCells) {
			for (Mill m : this.allMills) {
				if (m.getCell1() == c.getID() || m.getCell2() == c.getID()
						|| m.getCell3() == c.getID()) {
					c.getPossibleMills().add(m);
				}
			}
		}
	}

	public ArrayList<Mill> getAllMills() {
		return this.allMills;
	}

	public Boolean isMill(Move mv, Color color) {
		GameCell destCell = this.allCells.get(mv.getDestCell().getID());

		for (Mill m : destCell.getPossibleMills()) {
			if (this.allCells.get(m.getCell1()).getColor() == color
					&& this.allCells.get(m.getCell2()).getColor() == color
					&& this.allCells.get(m.getCell3()).getColor() == color) {
				
				return true;
			}
		}
		return false;
	}

	/** Gets the number of potential mills for a player */
	public int getPotentialMills(Move mv, Color color) {
		int count = 0;
		
		for(Mill m: this.allMills){
			
			if (this.allCells.get(m.getCell1()).getColor() == color
					&& this.allCells.get(m.getCell2()).getColor() == color
					&& this.allCells.get(m.getCell3()).getColor() == Color.WHITE) {
				count++;
			} 
			else if (this.allCells.get(m.getCell1()).getColor() == color
					&& this.allCells.get(m.getCell2()).getColor() == Color.WHITE
					&& this.allCells.get(m.getCell3()).getColor() == color) {
				count++;
			} 
			else if (this.allCells.get(m.getCell1()).getColor() == Color.WHITE
					&& this.allCells.get(m.getCell2()).getColor() == color
					&& this.allCells.get(m.getCell3()).getColor() == color) {
				count++;
			}
		}

		return count;
	}

	/** This will be called when a player has a mill & has selected a piece to clear from the board */
	public void clearCell(Move m, int cellToClear, Player player) {
		
		//associate cell being removed to the move that caused the removal
		m.setCellRemoved(this.allCells.get(cellToClear));
		m.setCellRemovedOccupant(this.allCells.get(cellToClear).getOccupant());
		
		// Clear the cell to remove
		GameCell cellToRemove = this.allCells.get(cellToClear);
		cellToRemove.setColor(Color.WHITE);
		cellToRemove.setOccupation(false);
		cellToRemove.setOccupant(null);
		
		if (cellToRemove.getUpSibling() > -1) {
			this.allCells.get(cellToRemove.getUpSibling()).setValidMoves(this.allCells);
		}

		if (cellToRemove.getRightSibling() > -1) {
			this.allCells.get(cellToRemove.getRightSibling()).setValidMoves(this.allCells);
		}

		if (cellToRemove.getDownSibling() > -1) {
			this.allCells.get(cellToRemove.getDownSibling()).setValidMoves(this.allCells);
		}

		if (cellToRemove.getLeftSibling() > -1) {
			this.allCells.get(cellToRemove.getLeftSibling()).setValidMoves(this.allCells);
		}

		// Reset the cell to null for the next player's turn
		this.g.setCellToClear(-1);
	}

}