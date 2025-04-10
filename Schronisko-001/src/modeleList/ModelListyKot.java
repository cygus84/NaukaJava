package modeleList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import modele.Kot;

@SuppressWarnings("serial")
public class ModelListyKot extends DefaultListModel<Kot> {
	
	private JList<Kot> lista;
	
	public ModelListyKot(JList<Kot> lista) {
		super();
		// renderer
		lista.setModel(this); // dodanie modelu do listy
		this.lista = lista;
	}
	
	// CRUD
	
	// dodawanie kota
	public void dodajKota(int id, String nazwa) {
		add(0, new Kot(id, nazwa));
		lista.updateUI();
	}
}
