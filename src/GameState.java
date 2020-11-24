import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameState {

	private String id;
	
	private Game g;
	
	private String phase;
	
	private CopyOnWriteArrayList<Move> player1ValidMoves;
	private CopyOnWriteArrayList<Move> player2ValidMoves;
	
	/*private ArrayList<GameCell> player1Occupations;
	private ArrayList<GameCell> player2Occupations;*/

	public GameState(Game g, String stateID, String phase) {
		this.id = stateID;
		this.g = g;
		
		this.player1ValidMoves = null;
		//this.player1Occupations = null;
		
		this.player2ValidMoves = null;
		//this.player2Occupations = null;
		
		setPhase(phase);
	}
	
	public Game getGame() {
		return this.g;
	}

	//Gets this list of valid moves for Player 1
	public CopyOnWriteArrayList<Move> generateP1ValidMoves(){
		return this.player1ValidMoves;
	}
	
	//Refreshes Player 1's valid moves
	public void setP1ValidMoves() {
		this.player1ValidMoves = this.g.getGameBoard().generateValidMoves(Color.BLUE);
	}
	
	//Gets this list of valid moves for Player 2
	public CopyOnWriteArrayList<Move> generateP2ValidMoves(){
		return this.player2ValidMoves;
	}

	//Refreshes Player 2's valid moves
	public void setP2ValidMoves() {
		this.player2ValidMoves = this.g.getGameBoard().generateValidMoves(Color.RED);
	}

	public String getID(){
		return this.id;
	}
	
	//Gets the list of all valid moves in the game
	/*public CopyOnWriteArrayList<Move> getAllValidMoves(){
		return this.g.getGameBoard().getAllValidMoves();
	}*/
	
	//Gets all the currently occupied cells on the board
	/*public ArrayList<GameCell> getAllOccupations(){
		return this.g.getGameBoard().getAllOccupations();
	}*/
	
	//Gets Player 1's occupations
	public ArrayList<GameCell> getP1Occupations(){
		return this.g.getGameBoard().getOccupations(Color.BLUE);
		//return this.player1Occupations;
	}
	
	//Refreshes Player 1's occupations
	/*public void setP1Occupations(){
		this.player1Occupations = this.g.getGameBoard().getOccupations(Color.BLUE);
	}*/
	
	//Gets Player 2's occupations
	public ArrayList<GameCell> getP2Occupations(){
		return this.g.getGameBoard().getOccupations(Color.RED);
		//return this.player2Occupations;
	}
	
	//Refreshes Player 2's occupations
	/*public void setP2Occupations(){
		this.player2Occupations = this.g.getGameBoard().getOccupations(Color.RED);
	}*/
	
	//Gets the current phase of the game
	public String getPhase(){
		return this.phase;
	}
	
	//Sets the phase of the game
	public void setPhase(String phase){
		this.phase = phase;
	}
	
	public void updatePhase(Boolean reDrawBoard) {
		
		if(this.g.getPlayer1().getCellCount() > 0 || this.g.getPlayer2().getCellCount() > 0){
			setPhase(this.g.PHASE_POPULATE);
		}
		
		// If both players have place played all 9 cells on the board, then move to fight phase
		else if (this.phase.equals(this.g.PHASE_POPULATE) && this.g.getPlayer1().getCellCount() == 0 && this.g.getPlayer2().getCellCount() == 0) {
			setPhase(this.g.PHASE_FIGHT);
			
			//this.g.getGameView().displayPhase("Fight!");
			//System.out.println("The game has made it to the fight phase");
			
			if(reDrawBoard){
				this.g.getGameView().displayPhase("Fight!");
			}
		}
		
		// The game over check is for the case when undoing moves during computer player search
		else if(this.phase.equals(this.g.PHASE_FIGHT) || this.phase.equals(this.g.PHASE_GAME_OVER)){
			
			//If one player has only 3 occupations remaining, then move to the Flight phase
			if(this.g.getGameBoard().getOccupations(Color.BLUE).size() == 3){
				this.g.getPlayer1().setFlightStatus(true);
				setPhase(this.g.PHASE_FLIGHT);
				
				if(reDrawBoard){
					this.g.getGameView().displayPhase("Flight!");
				}
			}
			
			else if(this.g.getGameBoard().getOccupations(Color.RED).size() == 3){
				this.g.getPlayer2().setFlightStatus(true);
				setPhase(this.g.PHASE_FLIGHT);
				
				if(reDrawBoard){
					this.g.getGameView().displayPhase("Flight!");
				}
			}
		}
		
		// This is for the case when undoing moves during computer player search
		else if(this.phase.equals(this.g.PHASE_FLIGHT)){
			//If one player has only 3 occupations remaining, then move to the Flight phase
			if(this.g.getGameBoard().getOccupations(Color.BLUE).size() == 3){
				this.g.getPlayer1().setFlightStatus(true);
				setPhase(this.g.PHASE_FLIGHT);
			}
			
			else if(this.g.getGameBoard().getOccupations(Color.RED).size() == 3){
				this.g.getPlayer2().setFlightStatus(true);
				setPhase(this.g.PHASE_FLIGHT);
			}
			
			else {
				setPhase(this.g.PHASE_FIGHT);
			}
		}
	}
	
	//Updates the state of the game
	public void updateState(Boolean reDrawBoard, int nodesCreated, int nodesExpanded){
		
		updatePhase(reDrawBoard);
		
		setP1ValidMoves();
		setP2ValidMoves();
		
		if(reDrawBoard){
			//re-draws the board with the updated positions
			this.g.getGameView().reset(nodesCreated, nodesExpanded);
			this.g.getGameView().revalidate();
			this.g.getGameView().repaint();
			
			/*try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
	}

}