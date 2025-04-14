package ui;

import javax.swing.*;

import dao.LocationDAO;
import dao.ProductDAO;
import model.Location;
import model.Product;

import java.awt.*;
import java.util.List;

public class EditProductDialog extends JDialog{
	 private final JTextField nameField;
	    private final JTextField skuField;
	    private final JComboBox<Location> locationComboBox;
	    private final JButton saveButton;
	    private final ProductDAO productDAO = new ProductDAO();

	    public EditProductDialog(JFrame parent, Product product) {
	        super(parent, "Edytuj produkt", true);
	        setLayout(new GridLayout(4, 2, 10, 10));
	        setSize(350, 200);
	        setLocationRelativeTo(parent);

	        add(new JLabel("Nazwa:"));
	        nameField = new JTextField(product.getName());
	        add(nameField);

	        add(new JLabel("SKU:"));
	        skuField = new JTextField(product.getSku());
	        add(skuField);

	        add(new JLabel("Lokalizacja:"));
	        locationComboBox = new JComboBox<>();
	        loadLocations(product.getCurrentLocationId());
	        add(locationComboBox);

	        saveButton = new JButton("Zapisz");
	        saveButton.addActionListener(e -> saveProduct(product));
	        add(saveButton);

	        JButton cancelButton = new JButton("Anuluj");
	        cancelButton.addActionListener(e -> dispose());
	        add(cancelButton);
	    }

	    private void loadLocations(int selectedLocationId) {
	        LocationDAO locationDAO = new LocationDAO();
	        List<Location> locations = locationDAO.getAll();

	        for (Location loc : locations) {
	            locationComboBox.addItem(loc);
	            if (loc.getId() == selectedLocationId) {
	                locationComboBox.setSelectedItem(loc);
	            }
	        }
	    }

	    private void saveProduct(Product product) {
	        String newName = nameField.getText().trim();
	        String newSku = skuField.getText().trim();
	        Location selectedLocation = (Location) locationComboBox.getSelectedItem();

	        if (newName.isEmpty() || newSku.isEmpty() || selectedLocation == null) {
	            JOptionPane.showMessageDialog(this, "Wszystkie pola są wymagane.");
	            return;
	        }

	        product.setName(newName);
	        product.setSku(newSku);
	        product.setCurrentLocationId(selectedLocation.getId());

	        productDAO.update(product);
	        dispose();
	    }
}
