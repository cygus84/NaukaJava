package renderery;

import javax.swing.*;

import mapa.Hex;
import modele.Hero;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.Map;

public class HexMapPanel extends JPanel {
	  private static final int HEX_SIZE = 40;
	    private static final int MAP_RADIUS = 5;
	    private final Map<Hex, Polygon> hexes = new HashMap<>();
	    private final Hero hero;

	    public HexMapPanel() {
	        this.hero = new Hero(new Hex(0, 0));
	        addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                for (Map.Entry<Hex, Polygon> entry : hexes.entrySet()) {
	                    if (entry.getValue().contains(e.getPoint())) {
	                        hero.moveTo(entry.getKey());
	                        repaint();
	                        break;
	                    }
	                }
	            }
	        });
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        hexes.clear();
	        Graphics2D g2d = (Graphics2D) g;

	        for (int q = -MAP_RADIUS; q <= MAP_RADIUS; q++) {
	            for (int r = Math.max(-MAP_RADIUS, -q - MAP_RADIUS); r <= Math.min(MAP_RADIUS, -q + MAP_RADIUS); r++) {
	                Hex hex = new Hex(q, r);
	                Polygon p = createHexPolygon(hex);
	                hexes.put(hex, p);
	                g2d.setColor(Color.LIGHT_GRAY);
	                g2d.draw(p);

	                if (hex.equals(hero.getPosition())) {
	                    g2d.setColor(Color.BLUE);
	                    g2d.fill(p);
	                }
	            }
	        }
	    }

	    private Polygon createHexPolygon(Hex hex) {
	        int[] center = hexToPixel(hex.q, hex.r);
	        int[] xs = new int[6];
	        int[] ys = new int[6];

	        for (int i = 0; i < 6; i++) {
	            double angle = Math.PI / 3 * i;
	            xs[i] = (int) (center[0] + HEX_SIZE * Math.cos(angle));
	            ys[i] = (int) (center[1] + HEX_SIZE * Math.sin(angle));
	        }

	        return new Polygon(xs, ys, 6);
	    }

	    private int[] hexToPixel(int q, int r) {
	        double x = HEX_SIZE * Math.sqrt(3) * (q + r / 2.0);
	        double y = HEX_SIZE * 3.0 / 2.0 * r;
	        return new int[]{(int) x + getWidth() / 2, (int) y + getHeight() / 2};
	    }
}
