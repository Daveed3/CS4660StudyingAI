import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



public interface Player {
	
	public Color getColor();
	
	public ArrayList<GameCell> getGamePieces();
	
	public int getCellCount();
	
	public Boolean isInFlight();
	
	public GameState playMove(Move m, Boolean reDraw, Boolean showMillMessage);
	
	public GameState undoMove(Move m, Boolean reDrawBoard); 
	
	public void playBestMove();

	public void setFlightStatus(Boolean inFlight);
}
