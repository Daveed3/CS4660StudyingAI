import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardView extends JPanel {

	/** The individual views representing the squares on the board. */
	private SquareView[][] squareViews;

	private Game g;
	
	public static final int BOARD_HEIGHT = 546;
	public static final int BOARD_WIDTH = 546;

	public BoardView(Game g) {
		setMinimumSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setMaximumSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		setLayout(new GridLayout(7, 7, 0, 0));
		setOpaque(true);
		setBackground(Color.GRAY);
		
		this.g = g;
		this.squareViews = new SquareView[7][7];

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				this.squareViews[i][j] = new SquareView(this.g.getGameBoard().getSquares()[i][j]);
				add(this.squareViews[i][j]);
			}
		}
	}

	@Override
	public void paintChildren(Graphics g) {
		super.paintChildren(g);
	}

	@Override
	/**
	 * Sets the board lines
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.BLACK);
		// Outer Rectangle
		g2.drawLine(58, 38, 238, 38); // 0 to 1
		g2.drawLine(238 + 39, 38, 457, 38); // 1 to 2
		g2.drawLine(457 + 19, 39, 457 + 19, 238); // 2 to 3
		g2.drawLine(457 + 19, 238 + 39, 457 + 19, 438 + 19); // 3 to 4
		g2.drawLine(457 + 38, 438 + 38, 238 + 19, 438 + 38); // 4 to 5
		g2.drawLine(238, 438 + 38, 58, 438 + 38); // 5 to 6
		g2.drawLine(38, 438 + 19, 38, 238 + 38); // 6 to 7
		g2.drawLine(38, 238, 38, 38); // 7 to 0

		// Middle Rectangle
		g2.drawLine(74 + 58, 78 + 34, 238, 78 + 34); // 8 to 9
		g2.drawLine(238 + 39, 74 + 34, 384, 74 + 34); // 9 to 10
		g2.drawLine(384 + 19, 74 + 58, 384 + 19, 238); // 10 to 11
		g2.drawLine(384 + 19, 238, 384 + 19, 384); // 11 to 12
		g2.drawLine(384, 384 + 19, 238 + 39, 384 + 19); // 12 to 13
		g2.drawLine(238, 384 + 19, 74 + 34, 384 + 19); // 13 to 14
		g2.drawLine(78 + 34, 384, 78 + 34, 234 + 39); // 14 to 15
		g2.drawLine(78 + 34, 238, 78 + 34, 74 + 34); // 15 to 8

		// Inner Rectangle
		g2.drawLine(152 + 19, 152 + 34, 238, 152 + 34); // 16 to 17
		g2.drawLine(238 + 39, 152 + 34, 312, 152 + 34); // 17 to 18
		g2.drawLine(312 + 19, 152 + 39, 312 + 19, 238); // 18 to 19
		g2.drawLine(312 + 19, 234 + 39, 312 + 19, 312); // 19 to 20
		g2.drawLine(312, 312 + 19, 234 + 39, 312 + 19); // 20 to 21
		g2.drawLine(238, 312 + 19, 152 + 39, 312 + 19); // 21 to 22
		g2.drawLine(152 + 34, 312, 152 + 34, 238 + 39); // 22 to 23
		g2.drawLine(152 + 34, 238, 152 + 34, 152 + 39); // 23 to 16

		// Connecting the rectangles
		g2.drawLine(238 + 19, 38, 238 + 19, 78 + 38); // 1 to 9
		g2.drawLine(238 + 19, 74 + 58, 238 + 19, 152 + 39); // 9 to 17

		g2.drawLine(457, 238 + 19, 384 + 39, 238 + 19); // 3 to 11
		g2.drawLine(390, 238 + 19, 312 + 39, 238 + 19); // 11 to 19

		g2.drawLine(238 + 19, 438 + 19, 238 + 19, 384 + 39); // 5 to 13
		g2.drawLine(238 + 19, 384, 238 + 19, 312 + 39); // 13 to 21

		g2.drawLine(58, 238 + 19, 74 + 19, 238 + 19); // 7 to 15
		g2.drawLine(74 + 58, 238 + 19, 152 + 19, 238 + 19); // 15 to 23
	}
}
