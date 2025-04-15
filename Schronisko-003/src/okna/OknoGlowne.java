package okna;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bazaDanych.BD;
import modele.Kot;
import modeleList.ModelListyKot;
import popup.PopupAktulizacjaKota;
import popup.PopupDodajKota;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class OknoGlowne extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ModelListyKot koty;
	private JLabel etykietIloscKotow;

	/**
	 * Create the frame.
	 */
	public OknoGlowne() {
		setTitle("Schronisko Koty Ver-002");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 920, 698);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));
		
		JPanel panelGorny = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelGorny.getLayout();
		flowLayout.setHgap(25);
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelGorny, BorderLayout.NORTH);
		
		etykietIloscKotow = new JLabel("Ilosc: " + getIloscKotow()); // dodanie metody 1 ilosc kotow 
		etykietIloscKotow.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panelGorny.add(etykietIloscKotow);
		
		JPanel panelDolny = new JPanel();
		contentPane.add(panelDolny, BorderLayout.SOUTH);
		
		JButton btDodajK = new JButton("Dodaj kota");
		btDodajK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajKota();
			}
		});
		panelDolny.add(btDodajK);
		
		JScrollPane spLista = new JScrollPane();
		contentPane.add(spLista, BorderLayout.CENTER);
		
		JList<Kot> lKoty = new JList<Kot>(); // wazne dodac nawiasy diamtowe do obslugi kot
		koty = new ModelListyKot(lKoty); // wazne  zaincijowanie modelu listy
		spLista.setViewportView(lKoty);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(lKoty, popupMenu);
		
		JMenuItem pmiUsunKota = new JMenuItem("Usun kota");
		pmiUsunKota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indeks = koty.wybranyKot();
				if (indeks >= 0) {
					usunKota(indeks);
				}
			}
		});
		popupMenu.add(pmiUsunKota);
		
		JMenuItem pmiAktualizacjaNazwy = new JMenuItem("Aktulizacja nazwy");
		pmiAktualizacjaNazwy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int indeks = koty.getSelectedIndex();
				aktuliazacjaKota(indeks);
			}
		});
		popupMenu.add(pmiAktualizacjaNazwy);
		
	
		// pierwsze ladowanie danych
		BD.pobierzDaneKotow(koty);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private long getIloscKotow() {
		return BD.pobierzIloscKotow();
	}
	
	private void dodajKota() {
		new PopupDodajKota();
		BD.pobierzDaneKotow(koty);
		etykietIloscKotow.setText("Ilosc: " + getIloscKotow());
	}
	
	private void usunKota(int idKota) {
		BD.usunKota(idKota);
		BD.pobierzDaneKotow(koty);
		etykietIloscKotow.setText("Ilosc: " + getIloscKotow());
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private void aktuliazacjaKota(int indeks) {
		if(indeks >= 0) {
			new PopupAktulizacjaKota(koty.get(indeks));
			BD.pobierzDaneKotow(koty);
		}
	}
}
