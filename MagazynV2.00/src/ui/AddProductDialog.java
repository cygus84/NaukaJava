package ui;

import javax.swing.*;

import dao.ProductDAO;
import model.Product;

import java.awt.*;
import java.awt.event.ActionEvent;

public class AddProductDialog extends JDialog {
	private JTextField nameField;
	private JTextField skuField;
	private JTextField locationIdField;
	private final ProductDAO productDAO = new ProductDAO();
	private final Runnable onSaveCallback;

	public AddProductDialog(Frame owner, Runnable onSaveCallback) {
		super(owner, "Dodaj produkt", true);
		this.onSaveCallback = onSaveCallback;

		setLayout(new GridLayout(4, 2, 10, 10));

		add(new JLabel("Nazwa:"));
		nameField = new JTextField();
		add(nameField);

		add(new JLabel("SKU:"));
		skuField = new JTextField();
		add(skuField);

		add(new JLabel("ID lokalizacji:"));
		locationIdField = new JTextField();
		add(locationIdField);

		JButton saveButton = new JButton("Zapisz");
		saveButton.addActionListener(this::onSave);
		add(saveButton);

		JButton cancelButton = new JButton("Anuluj");
		cancelButton.addActionListener(e -> dispose());
		add(cancelButton);

		setSize(300, 200);
		setLocationRelativeTo(owner);
	}

	private void onSave(ActionEvent e) {
		String name = nameField.getText();
		String sku = skuField.getText();
		int locationId;

		try {
			locationId = Integer.parseInt(locationIdField.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Niepoprawny ID lokalizacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Product product = new Product(name, sku, locationId);
		productDAO.save(product);
		onSaveCallback.run();
		dispose();
	}
}
