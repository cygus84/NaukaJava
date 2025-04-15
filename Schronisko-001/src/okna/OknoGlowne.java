package okna;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bazaDanych.BD;
import modele.Kot;
import modeleList.ModelListyKot;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class OknoGlowne extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ModelListyKot koty;

	/**
	 * Create the frame.
	 */
	public OknoGlowne() {
		setTitle("Schronisko Koty ver-001");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 698);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));
		
		JPanel panelGorny = new JPanel();
		contentPane.add(panelGorny, BorderLayout.NORTH);
		
		JPanel panelDolny = new JPanel();
		contentPane.add(panelDolny, BorderLayout.SOUTH);
		
		JScrollPane spLista = new JScrollPane();
		contentPane.add(spLista, BorderLayout.CENTER);
		
		JList<Kot> lKoty = new JList<Kot>(); // wazne dodac nawiasy diamtowe do obslugi kot
		koty = new ModelListyKot(lKoty); // wazne  zaincijowanie modelu listy
		spLista.setViewportView(lKoty);
		
		// pierwsze ladowanie danych
		BD.pobierzDaneKotow(koty);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
