package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import renderery.HexMapPanel;

public class Main {

	public static void main(String[] args) {
		//dodanie armi 
		System.out.println("Heros-006");
		  SwingUtilities.invokeLater(() -> {
	            JFrame frame = new JFrame("Hex Heroes");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setSize(900, 700);
	            frame.add(new HexMapPanel());
	            frame.setVisible(true);
	        });
	    }
	}
