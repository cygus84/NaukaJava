package renderery;

import javax.swing.*;

import hexgame.BattleScreen;
import mapObject.MapObject;
import mapa.Hex;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import modele.Hero;
import modele.Unit;
import terrainType.TerrainType;

public class HexMapPanel extends JPanel {
	private static final int HEX_SIZE = 40;
	private static final int MAP_RADIUS = 5;

	private final Map<Hex, Polygon> hexPolygons = new HashMap<>();
	private final ArrayList<Hex> mapHexes = new ArrayList<>();
	private final ArrayList<Hero> heroes = new ArrayList<>();
	private int activeHeroIndex = 0;

	public HexMapPanel() {
		generateMap();

		// Dodaj bohaterów
		Hero blue = new Hero(getHex(0, 0), Color.BLUE, 30, 10);
		blue.addUnit(new Unit("Piknier", 40, 10, 5));
		blue.addUnit(new Unit("Łucznik", 30, 12, 3));
		
		
		Hero red = new Hero(getHex(2, -2), Color.RED, 25, 8);
		red.addUnit(new Unit("Łucznik", 30, 12, 3));
		red.addUnit(new Unit("Pikinier", 40, 10, 5));
		
		heroes.add(blue);
		heroes.add(red);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Hex clickedHex : mapHexes) {
					Polygon p = hexPolygons.get(clickedHex);
					if (p != null && p.contains(e.getPoint())) {
						Hero active = getActiveHero();

						// Walka: sprawdź, czy na klikniętym heksie lub obok jest wróg
						for (Hero other : new ArrayList<>(heroes)) {
							if (other != active && other.isAlive()) {
								int dist = active.getPosition().distanceTo(other.getPosition());
								if (clickedHex.equals(other.getPosition()) || dist == 1) {
									  new BattleScreen(new JFrame(), active, other);
									    if (!other.isAlive()) heroes.remove(other);
									    if (!active.isAlive()) heroes.remove(active);
									    repaint();
									    return;
								}
							}
						}

						// Jeśli nie walka, to przemieść bohatera
						active.moveTo(clickedHex);
						repaint();
						return;
					}
				}
			}
		});

		JButton nextTurn = new JButton("Next Turn");
		nextTurn.addActionListener(e -> {
			activeHeroIndex = (activeHeroIndex + 1) % heroes.size();
			repaint();
		});

		this.setLayout(null);
		nextTurn.setBounds(10, 10, 100, 30);
		this.add(nextTurn);
	}

	private void flashHex(Hex hex, Color flashColor, int durationMs) {
		new Thread(() -> {
			Color original = Color.WHITE;
			try {
				Graphics2D g2 = (Graphics2D) getGraphics();
				Polygon p = hexPolygons.get(hex);
				if (p != null) {
					g2.setColor(flashColor);
					g2.fill(p);
					Thread.sleep(durationMs);
					repaint(); // odśwież normalnie
				}
			} catch (Exception ignored) {
			}
		}).start();
	}

	private void generateMap() {
		mapHexes.clear();
		Random rand = new Random();

		for (int q = -MAP_RADIUS; q <= MAP_RADIUS; q++) {
			for (int r = Math.max(-MAP_RADIUS, -q - MAP_RADIUS); r <= Math.min(MAP_RADIUS, -q + MAP_RADIUS); r++) {
				Hex hex = new Hex(q, r);

				// Losowy teren
				int t = rand.nextInt(100);
				if (t < 10)
					hex.setTerrain(TerrainType.WATER);
				else if (t < 20)
					hex.setTerrain(TerrainType.MOUNTAIN);

				// Losowy obiekt
				int o = rand.nextInt(100);
				if (o < 5)
					hex.setObject(MapObject.CASTLE);
				else if (o < 10)
					hex.setObject(MapObject.MINE);

				mapHexes.add(hex);
			}
		}
	}

	private Hero getActiveHero() {
		return heroes.get(activeHeroIndex);
	}

	private Hex getHex(int q, int r) {
		return mapHexes.stream().filter(h -> h.q == q && h.r == r).findFirst().orElse(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		hexPolygons.clear();
		Graphics2D g2 = (Graphics2D) g;

		for (Hex hex : mapHexes) {
			Polygon poly = createHexPolygon(hex.q, hex.r);
			hexPolygons.put(hex, poly);

			// Kolor terenu
			g2.setColor(switch (hex.getTerrain()) {
			case GRASS -> new Color(100, 200, 100);
			case WATER -> new Color(100, 100, 255);
			case MOUNTAIN -> new Color(120, 120, 120);
			});
			g2.fill(poly);
			g2.setColor(Color.BLACK);
			g2.draw(poly);

			// Obiekt
			if (hex.getObject() != MapObject.NONE) {
				g2.setColor(Color.ORANGE);
				g2.fillOval(poly.getBounds().x + 10, poly.getBounds().y + 10, 20, 20);
				g2.setColor(Color.BLACK);
				g2.drawString(hex.getObject().name().substring(0, 1), poly.getBounds().x + 17, poly.getBounds().y + 25);
			}
		}

		// Rysowanie bohaterów
		for (Hero hero : heroes) {
			Hex pos = hero.getPosition();
			Polygon poly = hexPolygons.get(pos);
			Rectangle bounds = poly.getBounds();
			g2.setColor(hero.getColor());
			g2.fillOval(bounds.x + 10, bounds.y + 10, 20, 20);

			// Pasek życia
			int hpWidth = (int) (20 * (hero.getHp() / 100.0));
			g2.setColor(Color.RED);
			g2.fillRect(bounds.x + 10, bounds.y + 5, 20, 4);
			g2.setColor(Color.GREEN);
			g2.fillRect(bounds.x + 10, bounds.y + 5, hpWidth, 4);

		}

		// Highlight aktywnego bohatera
		Hex activeHex = getActiveHero().getPosition();
		g2.setColor(Color.YELLOW);
		g2.draw(hexPolygons.get(activeHex));
	}

	private Polygon createHexPolygon(int q, int r) {
		int[] center = hexToPixel(q, r);
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
		return new int[] { (int) x + getWidth() / 2, (int) y + getHeight() / 2 };
	}
}
