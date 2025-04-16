package renderery;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class HexMapPanel extends JPanel {
	private static final int HEX_SIZE = 40;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawHexGrid((Graphics2D) g);
	}

	private void drawHexGrid(Graphics2D g2d) {
		int width = getWidth();
		int height = getHeight();

		for (int q = -5; q <= 5; q++) {
			for (int r = -5; r <= 5; r++) {
				int[] pixel = hexToPixel(q, r);
				drawHex(g2d, pixel[0], pixel[1]);
			}
		}
	}

	private int[] hexToPixel(int q, int r) {
		double x = HEX_SIZE * Math.sqrt(3) * (q + r / 2.0);
		double y = HEX_SIZE * 3.0 / 2.0 * r;
		return new int[] { (int) x + 400, (int) y + 300 }; // przesunięcie do środka
	}

	private void drawHex(Graphics2D g2d, int x, int y) {
		Path2D hex = new Path2D.Double();
		for (int i = 0; i < 6; i++) {
			double angle = Math.PI / 3.0 * i;
			double px = x + HEX_SIZE * Math.cos(angle);
			double py = y + HEX_SIZE * Math.sin(angle);
			if (i == 0)
				hex.moveTo(px, py);
			else
				hex.lineTo(px, py);
		}
		hex.closePath();
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.draw(hex);
	}
}
