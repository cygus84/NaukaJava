package ui;

import javax.swing.*;

import dao.ProductDAO;
import model.Product;

import java.awt.*;
import java.awt.event.ActionEvent;

public class EditProductDialog extends JDialog {
	 private JTextField nameField;
	    private JTextField skuField;
	    private JTextField locationIdField;
	    private final Product product;
	    private final ProductDAO productDAO = new ProductDAO();

	    public EditProductDialog(Frame owner, Product product) {
	        super(owner, "Edytuj produkt", true);
	        this.product = product;

	        setLayout(new GridLayout(4, 2, 10, 10));

	        add(new JLabel("Nazwa:"));
	        nameField = new JTextField(product.getName());
	        add(nameField);

	        add(new JLabel("SKU:"));
	        skuField = new JTextField(product.getSku());
	        add(skuField);

	        add(new JLabel("ID lokalizacji:"));
	        locationIdField = new JTextField(String.valueOf(product.getCurrentLocationId()));
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

	        product.setName(name);
	        product.setSku(sku);
	        product.setCurrentLocationId(locationId);

	        productDAO.update(product);
	        dispose();
	    }
}
