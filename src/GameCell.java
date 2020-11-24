import java.awt.Color;
import java.util.ArrayList;

public class GameCell {

	/**
	 * Red, Blue or Blank
	 */
	private Color color;

	private int id;

	private Boolean isOccupied;
	
	/** The game piece that sits in this cell*/
	private GameCell occupant;

	private int siblingUp;

	private int siblingRight;

	private int siblingDown;

	private int siblingLeft;
	
	/** A ArrayList of GameCell ids of the unoccupied siblings */
	private ArrayList<Integer> validMoves;

	private ArrayList<Mill> possibleMills;
	
	private Square boardSquare;
	
	private Boolean wasPlayed;

	public GameCell(Color c, int cellID, Boolean occupied) {	
		this.color = c;
		this.id = cellID;
		this.isOccupied = occupied;
		this.siblingUp = 0;
		this.siblingRight = 0;
		this.siblingDown = 0;
		this.siblingLeft = 0;
		this.possibleMills = new ArrayList<Mill>();
		this.occupant = null;
		this.validMoves = new ArrayList<Integer>();
		this.wasPlayed = false;
	}

	public Boolean getWasPlayed() {
		return this.wasPlayed;
	}

	public void setWasPlayed(Boolean wasPlayed) {
		this.wasPlayed = wasPlayed;
	}

	public int getID(){
		return this.id;
	}
	
	public Square getBoardSquare(){
		return this.boardSquare;
	}
	
	public void setBoardSquare(Square s){
		this.boardSquare = s;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Boolean isOccupied(){
		return this.isOccupied;
	}
	
	public void setOccupation(Boolean occupied){
		this.isOccupied = occupied;
	}


	public GameCell getOccupant() {
		return this.occupant;
	}

	public void setOccupant(GameCell occupant) {
		this.occupant = occupant;
	}

	public int getUpSibling() {
		return this.siblingUp;
	}

	public int getRightSibling() {
		return this.siblingRight;
	}

	public int getDownSibling() {
		return this.siblingDown;
	}

	public int getLeftSibling() {
		return this.siblingLeft;
	}

	public void setSiblings(int upID, int rightID, int downID, int leftID) {
		this.siblingUp = upID;
		this.siblingRight = rightID;
		this.siblingDown = downID;
		this.siblingLeft = leftID;
	}

	public ArrayList<Integer> getValidMoves() {
		return this.validMoves;
	}

	/**
	 * Loops through the siblings and adds the unoccupied siblings to validMoves
	 * ArrayList
	 * 
	 * Each sibling that is not occupied is valid
	 */
	public void setValidMoves(ArrayList<GameCell> allCells) {
		this.validMoves.clear();
		if (this.siblingUp > -1 && !allCells.get(this.siblingUp).isOccupied()){
			this.validMoves.add(this.siblingUp);
		}
		if(this.siblingRight > -1 && !allCells.get(this.siblingRight).isOccupied()){
			this.validMoves.add(this.siblingRight);
		}
		if(this.siblingDown > -1 && !allCells.get(this.siblingDown).isOccupied()){
			this.validMoves.add(this.siblingDown);
		}
		if(this.siblingLeft > -1 && !allCells.get(this.siblingLeft).isOccupied()){
			this.validMoves.add(this.siblingLeft);
		}
	}
	
	public ArrayList<Mill> getPossibleMills(){
		return this.possibleMills;
	}

}