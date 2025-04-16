package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import renderery.HexMapPanel;

public class Main {

	public static void main(String[] args) {
		//dodanie klasy heros
		System.out.println("Heros-002");
		 SwingUtilities.invokeLater(() -> {
	            JFrame frame = new JFrame("Hex Heroes");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setSize(800, 600);
	            frame.add(new HexMapPanel());
	            frame.setVisible(true);
	        });
	    }
	}
