import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SquareView extends JPanel {

	/** Logical model of this square. */
	private Square square;

	/** The graphical view of the GameCell associated with this square. */
	private CellView cellView;

	public SquareView(Square s) {
		setLayout(new GridLayout(1, 1));
		setOpaque(false);
		
		setSquare(s);
		s.setSquareView(this);
		setCellView();
	}

	/** Assigns a square to this squareView. */
	public void setSquare(Square s) {
		this.square = s;
	}
	
	/** Assigns a square to this squareView. */
	public Square getSquare() {
		return this.square;
	}

	public CellView getCellView() {
		return this.cellView;
	}

	/**
	 * Tells the square which CellView it is associated with. The cellID
	 * corresponds to the appropriate GameCellID
	 */
	public void setCellView() {
		GameCell gameCell = this.square.getGameCell();
		if (gameCell != null) {
			this.cellView = new CellView(gameCell.getColor(), this.square.getPointX(), this.square.getPointY());
			this.cellView.setSquareView(this);
			this.cellView.setCellViewLabel(gameCell.getID());
			add(this.cellView);
		}
	}
	
	public void setCellView(CellView cv){
		this.cellView = cv;
		this.cellView.setSquareView(this);
		add(this.cellView);
	}
	
	@Override
	/** 
	 * Draws the cell after calling super.paintChildren().
	 */
	public void paintChildren(Graphics g) {
		super.paintChildren(g);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
