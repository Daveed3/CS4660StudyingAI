import java.awt.Point;


public class Square {
	
	/** An instance of the board. */
	private final GameBoard board;

	/** The (x,y) coordinate of this square. */
	private Point point;
	
	/** The GameCell associated with this square*/
	private GameCell gameCell;
	
	/** The graphical view associated with this square. */
	@SuppressWarnings("unused")
	private SquareView squareView;
	
	/**
	 * Model of a square.
	 * 
	 * @param b Instance of the board that this square is associated with.
	 * @param p (x,y) location of this square within the board.
	 */
	public Square(GameBoard gBoard, Point p) {
		this.board = gBoard;
		this.point = p;
		this.gameCell = null;
	}
	
	/** Return the game cell associated with this Square */
	public GameCell getGameCell(){
		return this.gameCell;
	}
	
	/** Sets the game cell associated with this Square */
	public void setGameCell(int cellID){
		this.gameCell = this.board.getAllCells().get(cellID);
	}
	

	/** Sets the game cell associated with this Square */
	public void setGameCell(GameCell gc){
		this.gameCell = gc;
	}
	
	public GameBoard getBoard() {
		return this.board;
	}

	/**
	 * Tells the square which SquareView it is associated with.
	 */
	public void setSquareView(SquareView s) {
		this.squareView = s;
	}
	
	public double getPointX(){
		return this.point.getX();
	}
	
	public double getPointY(){
		return this.point.getY();
	}

}
