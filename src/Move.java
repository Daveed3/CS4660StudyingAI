/**
 * - start and end will be game cell IDs
 */
public class Move {

	private GameCell startCell;

	private GameCell destCell;
	
	private GameCell cellRemoved;
	
	private GameCell cellRemovedOccupant;
	
	private double value;

	/** Concatenate the startCell ID with destCell ID */
	private String id;

	public Move() {
		this.startCell = null;
		this.destCell = null;
		this.cellRemoved = null;
		this.cellRemovedOccupant = null;
		this.id = null;
		this.value = 0;
	}
	
	public GameCell getCellRemovedOccupant() {
		return this.cellRemovedOccupant;
	}

	public void setCellRemovedOccupant(GameCell cellRemovedOccupant) {
		this.cellRemovedOccupant = cellRemovedOccupant;
	}

	public GameCell getCellRemoved() {
		return this.cellRemoved;
	}

	public void setCellRemoved(GameCell cellRemoved) {
		this.cellRemoved = cellRemoved;
	}

	public String getID() {
		return id;
	}

	public Move(GameCell start, GameCell destination, String moveID) {
		this.startCell = start;
		this.destCell = destination;
		this.id = moveID;
	}
	
	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public GameCell getStartCell(){
		return this.startCell;
	}
	
	public GameCell getDestCell(){
		return this.destCell;
	}

}