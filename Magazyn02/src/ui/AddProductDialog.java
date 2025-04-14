package ui;

import javax.swing.*;

import dao.LocationDAO;
import dao.ProductDAO;
import model.Location;
import model.Product;

import java.awt.*;
import java.util.List;

public class AddProductDialog extends JDialog  {
	private final JTextField nameField;
    private final JTextField skuField;
    private final JComboBox<Location> locationComboBox;
    private final JButton addButton;
    private final ProductDAO productDAO = new ProductDAO();

    public AddProductDialog(JFrame parent, Runnable onProductAdded) {
        super(parent, "Dodaj nowy produkt", true);
        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(350, 200);
        setLocationRelativeTo(parent);

        add(new JLabel("Nazwa:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("SKU:"));
        skuField = new JTextField();
        add(skuField);

        add(new JLabel("Lokalizacja:"));
        locationComboBox = new JComboBox<>();
        loadLocations();
        add(locationComboBox);

        addButton = new JButton("Dodaj");
        addButton.addActionListener(e -> addProduct(onProductAdded));
        add(addButton);

        JButton cancelButton = new JButton("Anuluj");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void loadLocations() {
        LocationDAO locationDAO = new LocationDAO();
        List<Location> locations = locationDAO.getAll();

        for (Location loc : locations) {
            locationComboBox.addItem(loc);
        }
    }

    private void addProduct(Runnable onProductAdded) {
        String name = nameField.getText().trim();
        String sku = skuField.getText().trim();
        Location selectedLocation = (Location) locationComboBox.getSelectedItem();

        if (name.isEmpty() || sku.isEmpty() || selectedLocation == null) {
            JOptionPane.showMessageDialog(this, "Wszystkie pola są wymagane.");
            return;
        }

        Product newProduct = new Product(name, sku, selectedLocation.getId());
        productDAO.save(newProduct);
        onProductAdded.run(); // Odśwież tabelę
        dispose();
    }
}
