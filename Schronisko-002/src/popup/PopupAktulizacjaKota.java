package popup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bazaDanych.BD;
import modele.Kot;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PopupAktulizacjaKota extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField tfNazwaKota;
	private Kot kot;

	/**
	 * Create the dialog.
	 */
	public PopupAktulizacjaKota() {
		ustaw(new Kot());
	}

	public PopupAktulizacjaKota(Kot kot) {
		ustaw(kot);
	}

	private void ustaw(Kot kot) {
		this.kot = kot;
		setTitle("Aktulizacja kota");
		setBounds(100, 100, 602, 139);
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		getContentPane().setLayout(borderLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(5, 5));
		{
			JLabel eNazwaKota = new JLabel("Nazwa kota");
			eNazwaKota.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(eNazwaKota, BorderLayout.WEST);
		}
		{
			tfNazwaKota = new JTextField();
			tfNazwaKota.setText(kot.getNazwa()); //wazne przypisanie nazwy
			tfNazwaKota.setFont(new Font("Tahoma", Font.PLAIN, 16));
			tfNazwaKota.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(tfNazwaKota, BorderLayout.CENTER);
			tfNazwaKota.setColumns(10);
		}
		{
			JButton btnZapisz = new JButton("Zapisz");
			btnZapisz.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					zapiszDaneKota();
				}
			});
			btnZapisz.setFont(new Font("Tahoma", Font.PLAIN, 16));
			contentPanel.add(btnZapisz, BorderLayout.EAST);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btZamknik = new JButton("Zamknij");
				btZamknik.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						zamknijPoup();
					}
				});
				btZamknik.setFont(new Font("Tahoma", Font.PLAIN, 16));
				btZamknik.setActionCommand("Cancel");
				buttonPane.add(btZamknik);
			}
		}

		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void zamknijPoup() {
		// aktulizacja danych w data modelu

		dispose();
	}

	private void zapiszDaneKota() {
		BD.zapiszDaneKota(tfNazwaKota.getText(), kot.getId());
		zamknijPoup();
	}

}
