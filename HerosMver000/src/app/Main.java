package app;

import javax.swing.JFrame;

import renderery.HexMapPanel;

public class Main {

	public static void main(String[] args) {
	      JFrame frame = new JFrame("Hex Map");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(800, 600);
	        frame.add(new HexMapPanel());
	        frame.setVisible(true);
	    }
	}
