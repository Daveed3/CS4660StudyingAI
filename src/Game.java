import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Game {

	private GameBoard gBoard;

	private GameView gView;

	private GameState gState;

	private HumanPlayer p1;

	private Player p2;

	private Player currentPlayer;
	
	private Player winner;

	private ArrayList<GameCell> cellsInMove;
	
	private int cellToClear;
	
	private Boolean shuffleMoves;
	private int depthLimit;
	private Boolean useSymmetry;
	private Boolean reDraw;
	
	public final String PHASE_POPULATE = "Populate";
	public final String PHASE_FIGHT = "Fight";
	public final String PHASE_FLIGHT = "Flight";
	public final String PHASE_CLEAR_CELL = "Remove Cell";
	public final String PHASE_GAME_OVER = "GAME OVER";

	public Game() {
		
		GameConfigDialog config = new GameConfigDialog(this);
		
		setGameState(new GameState(this, "main", PHASE_POPULATE));
		this.gBoard = new GameBoard(this);
		
		this.p1 = new HumanPlayer(this);
		//this.p2 = new MiniMaxPlayer(this);
		this.currentPlayer = this.p1; // the human player goes first
		this.gState.updateState(false, 0, 0);
		this.cellsInMove = new ArrayList<GameCell>();
		this.cellToClear = -1;
		
		this.gView = new GameView(this);
		
		this.gView.displayPhase(this.gState.getPhase());
	}

	public Boolean getUseSymmetry() {
		return this.useSymmetry;
	}

	public void setUseSymmetry(Boolean useSymmetry) {
		this.useSymmetry = useSymmetry;
	}

	public Boolean doShuffleMoves() {
		return this.shuffleMoves;
	}

	public void setShuffleMoves(Boolean shuffleMoves) {
		this.shuffleMoves = shuffleMoves;
	}

	public int getDepthLimit() {
		return this.depthLimit;
	}

	public void setDepthLimit(int depthLimit) {
		this.depthLimit = depthLimit;
	}

	public Boolean doReDraw() {
		return this.reDraw;
	}

	public void setReDraw(Boolean reDraw) {
		this.reDraw = reDraw;
	}

	public Player getWinner() {
		return this.winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public ArrayList<GameCell> getCellsInMove() {
		return this.cellsInMove;
	}
	
	public int getCellToClear() {		
		return this.cellToClear;
	}
	
	public void setCellToClear(int cellToClear) {
		this.cellToClear = cellToClear;
	}

	/*public void setSelectedCells(ArrayList<GameCell> selectedCells) {
		this.cellsInMove = selectedCells;
	}*/

	public GameBoard getGameBoard() {
		return gBoard;
	}

	public void setGameBoard(GameBoard gBoard) {
		this.gBoard = gBoard;
	}

	public GameState getGameState() {
		return this.gState;
	}

	public void setGameState(GameState gState) {
		this.gState = gState;
	}

	public GameView getGameView() {
		return gView;
	}

	public void setGameView(GameView gView) {
		this.gView = gView;
	}

	public HumanPlayer getPlayer1() {
		return this.p1;
	}

	public Player getPlayer2() {
		return this.p2;
	}
	
	public void setPlayer2(Player p){
		this.p2 = p;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void setCurrentPlayer(Player p) {
		this.currentPlayer = p;
	}
	
	private void start() {
		while (!isGameOver(this.gState, true)) {
			play();
		}
		
		System.exit(0);
		
	}

	public Boolean isGameOver(GameState gState, Boolean showWinner) {
		
		if(gState.getPhase().equals(this.PHASE_FIGHT) || gState.getPhase().equals(this.PHASE_FLIGHT)){
			// Check if Player 1 wins
			if (this.gState.generateP2ValidMoves().size() == 0 || this.gState.getP2Occupations().size() == 2) {
				gState.setPhase(this.PHASE_GAME_OVER);
				this.winner = this.p1;
				if(showWinner){
					this.gView.displayWinner("PLAYER 1");
				}
				return true;
			}
			// check if Player 2 wins
			else if (this.gState.generateP1ValidMoves().size() == 0 || this.gState.getP1Occupations().size() == 2) {
				gState.setPhase(this.PHASE_GAME_OVER);
				this.winner = this.p2;
				if(showWinner){
					this.gView.displayWinner("PLAYER 2");
				}
				return true;
			}
		}
		
		return false;
	}

	/** Play the game */
	public void play() {
		if (this.currentPlayer == this.p1) {
			if (this.getCellsInMove().size() == 2) {
				Move m = getMoveToPlay();
				
				if (isValidMove(m)) {
					
					this.p1.playMove(m, true, true);
					this.cellsInMove.clear();
				}
				// Get here when the move is invalid
				else {
					this.cellsInMove.clear();
				}
			}
		}

		else if (this.currentPlayer == this.p2) {
			
			this.p2.playBestMove();
		}
	}

	private Move getMoveToPlay() {
		GameCell start;
		GameCell dest;
		
		//During Populate phase, need to grab the start cell from the user's game pieces
		if(this.gState.getPhase().equals(PHASE_POPULATE)){
			
			if(isLegalMove(this.getCellsInMove().get(0).getID(), this.getCellsInMove().get(1).getID())){
				start = this.p1.getGamePieces().get(this.getCellsInMove().get(0).getID() % 24);
				dest = this.gBoard.getAllCells().get(this.getCellsInMove().get(1).getID());
			}
			else{
				GameCell cell1 = new GameCell(Color.BLUE, -1, null);
				GameCell cell2 = new GameCell(Color.BLUE, -1, null);
				start = cell1;
				dest = cell2;
			}
		}
		else{
			
			if(isLegalMove(this.getCellsInMove().get(0).getID(), this.getCellsInMove().get(1).getID())){
				start = this.gBoard.getAllCells().get(this.getCellsInMove().get(0).getID());
				dest = this.gBoard.getAllCells().get(this.getCellsInMove().get(1).getID());
			}
			else{
				GameCell cell1 = new GameCell(Color.BLUE, -1, null);
				GameCell cell2 = new GameCell(Color.BLUE, -1, null);
				start = cell1;
				dest = cell2;
			}
		}
		
		Move m = new Move(start, dest, Integer.toString(start.getID()) + "-" + Integer.toString(dest.getID()));
		return m;
	}
	
	/** Only called during populate to check the cell ids */
	private boolean isLegalMove(int start, int end){
		
		if(this.gState.getPhase().equals(PHASE_POPULATE)){
			if((start >= 24 && start <= 32) && (end >= 0 && end <= 23)){
				return true;
			}
			else{
				return false;
			}
		}
		else {
			if((start >= 0 && start <= 23) && (end >= 0 && end <= 23)){
				return true;
			}
			else{
				return false;
			}
		}
		
	}
	
	private boolean isValidMove(Move m) {
		for (Move mv : this.gBoard.generateValidMoves(Color.BLUE)) {
			if (mv.getID().equals(m.getID())) {
				return true;
			}
		}
		
		this.gView.displayInvalidMoveMessage("Oops, that was an invalid move!");
		return false;
	}

	public static void main(String[] args) {
		final Game g = new Game();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				g.gView.validate();
				g.gView.repaint();
			}
		});
		g.start();
	}

	

}