import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class CellView extends JComponent implements MouseListener {

	/** Board area that the CellView is associated with */
	private SquareView squareView;

	private Color color;

	private double x;

	private double y;
	
	private Boolean cellSelected;
	
	private JLabel cellIDLabel;

	private static final int CELL_VIEW_WIDTH = 39;
	private static final int CELL_VIEW_HEIGHT = 39;
	private static final int CELL_VIEW_X_OFFSET = 19;
	private static final int CELL_VIEW_Y_OFFSET = 19;

	public CellView(Color c, double x, double y) {
		setMinimumSize(new Dimension(CELL_VIEW_WIDTH, CELL_VIEW_HEIGHT));
		setMaximumSize(new Dimension(CELL_VIEW_WIDTH, CELL_VIEW_HEIGHT));
		setPreferredSize(new Dimension(CELL_VIEW_WIDTH, CELL_VIEW_HEIGHT));

		setLayout(new GridLayout(1, 1));
		setOpaque(false);
		this.color = c;
		this.x = x;
		this.y = y;
		this.cellSelected = false;
		
		this.addMouseListener(this);
	}
	
	public void setCellViewLabel(int gameCellID){
		this.cellIDLabel = new JLabel(Integer.toString(gameCellID));
		this.cellIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.cellIDLabel.setVerticalAlignment(SwingConstants.CENTER);
		add(this.cellIDLabel);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getCellX() {
		return x;
	}

	public void setCellX(double x) {
		this.x = x;
	}

	public double getCellY() {
		return y;
	}

	public void setCellY(double y) {
		this.y = y;
	}

	public void setSquareView(SquareView squareView) {
		this.squareView = squareView;
	}

	@Override
	/** 
	 * Paints the circles on the board
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(this.color);
		Ellipse2D.Double cellCircle = new Ellipse2D.Double(this.x + CELL_VIEW_X_OFFSET, this.y + CELL_VIEW_Y_OFFSET,
				CELL_VIEW_WIDTH, CELL_VIEW_HEIGHT);
		g2.fill(cellCircle);
		
		if(this.cellSelected){
			outlineCell();
		}
	}
	
	public void outlineCell(){
		Graphics g = this.getGraphics();
		g.setColor(Color.BLACK);
		g.drawOval((int)this.x + CELL_VIEW_X_OFFSET, (int)this.y + CELL_VIEW_Y_OFFSET,CELL_VIEW_WIDTH, CELL_VIEW_HEIGHT);
		
		this.cellSelected = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		GameCell gc = this.squareView.getSquare().getGameCell();

		// Only report selected cells when it's player 1's turn
		if (this.squareView.getSquare().getBoard().getGame().getCurrentPlayer() == this.squareView
				.getSquare().getBoard().getGame().getPlayer1()) {
			
			this.cellSelected = true;
			revalidate();
			repaint();
			
			this.squareView.getSquare().getBoard().getGame().getCellsInMove().add(gc);

			// This should only happen when player 1 gets a new mill and is
			// selecting a cell to clear
			if (this.squareView
					.getSquare()
					.getBoard()
					.getGame()
					.getGameState()
					.getPhase()
					.equals(this.squareView.getSquare().getBoard().getGame().PHASE_CLEAR_CELL)
					&& this.squareView.getSquare().getBoard().getAllCells()
							.get(gc.getID()).getColor() != this.squareView
							.getSquare().getBoard().getGame().getPlayer2()
							.getColor()) {

				this.squareView.getSquare().getBoard().getGame()
						.setCellToClear(gc.getID());
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
