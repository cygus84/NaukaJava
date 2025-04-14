package appStart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.LocationDAO;
import dao.MovementDAO;
import dao.ProductDAO;
import dataBase.DatabaseInitializer;
import dataBase.DatabaseManager;
import model.Location;
import model.Product;
import ui.AddProductDialog;
import ui.EditProductDialog;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WarehouseApp {
	private JFrame frame;
	private JTable productTable;
	private JTable locationTable;
	private ProductDAO productDAO;
	private LocationDAO locationDAO;
	private MovementDAO movementDAO;

	public static void main(String[] args) {
		  EventQueue.invokeLater(() -> {
	            try {
	                WarehouseApp window = new WarehouseApp();
	                window.frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	    }

	    public WarehouseApp() {
	    	  // Inicjalizacja bazy danych
	        DatabaseInitializer.initializeDatabase();
	        productDAO = new ProductDAO();
	        locationDAO = new LocationDAO();
	        movementDAO = new MovementDAO();
	        
	        
	        initialize();
	    }

	    private void initialize() {
	        frame = new JFrame();
	        frame.setBounds(100, 100, 800, 600);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(new BorderLayout());

	        // Tworzenie zakładek
	        JTabbedPane tabbedPane = new JTabbedPane();
	        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

	        // Panel dla produktów
	        JPanel productPanel = new JPanel();
	        tabbedPane.addTab("Produkty", null, productPanel, null);
	        productPanel.setLayout(new BorderLayout());

	        // Tabela produktów
	        productTable = new JTable();
	        JScrollPane productScrollPane = new JScrollPane(productTable);
	        productPanel.add(productScrollPane, BorderLayout.CENTER);

	        // Przycisk do odświeżenia listy produktów
	        JButton btnRefreshProducts = new JButton("Odśwież");
	        btnRefreshProducts.addActionListener(e -> loadProducts());
	        productPanel.add(btnRefreshProducts, BorderLayout.SOUTH);
	        
	        JButton editProductButton = new JButton("Edytuj produkt");
	        editProductButton.addActionListener(e -> {
	            int selectedRow = productTable.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(frame, "Wybierz produkt do edycji.");
	                return;
	            }

	            int productId = (int) productTable.getValueAt(selectedRow, 0);
	            Product product = productDAO.getById(productId);

	            EditProductDialog dialog = new EditProductDialog(frame, product);
	            dialog.setVisible(true);
	            refreshProductTable();
	        });
	        
	        JButton deleteProductButton = new JButton("Usuń produkt");

	        
	        JButton addProductButton = new JButton("Dodaj produkt");
	        frame.add(addProductButton);
	       
	        addProductButton.addActionListener(e -> {
	            AddProductDialog dialog = new AddProductDialog(frame, this::refreshProductTable);
	            dialog.setVisible(true);
	        });
	        
	        editProductButton.addActionListener(e -> {
	            int selectedRow = productTable.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(frame, "Wybierz produkt do edycji.");
	                return;
	            }

	            int productId = (int) productTable.getValueAt(selectedRow, 0);
	            String currentName = (String) productTable.getValueAt(selectedRow, 1);
	            String currentSku = (String) productTable.getValueAt(selectedRow, 2);

	            String newName = JOptionPane.showInputDialog(frame, "Nowa nazwa produktu:", currentName);
	            if (newName == null) return; // anulowano

	            String newSku = JOptionPane.showInputDialog(frame, "Nowy SKU:", currentSku);
	            if (newSku == null) return;

	            ProductDAO dao = new ProductDAO();
	            dao.update(new Product(productId, newName, newSku, -1)); // -1 = nie zmieniamy lokalizacji

	            refreshProductTable();
	        });
	        
	        
	        deleteProductButton.addActionListener(e -> {
	            int selectedRow = productTable.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(frame, "Wybierz produkt do usunięcia.");
	                return;
	            }

	            int productId = (int) productTable.getValueAt(selectedRow, 0);

	            int confirm = JOptionPane.showConfirmDialog(frame,
	                "Czy na pewno chcesz usunąć ten produkt?", "Potwierdź usunięcie",
	                JOptionPane.YES_NO_OPTION);

	            if (confirm == JOptionPane.YES_OPTION) {
	                ProductDAO dao = new ProductDAO();
	                dao.delete(productId);
	                refreshProductTable();
	            }
	        });

	       
	        
	         
	        // Panel do dodawania nowych produktów
	        JPanel addProductPanel = new JPanel();
	        addProductPanel.setLayout(new GridLayout(3, 2));
	        productPanel.add(addProductPanel, BorderLayout.NORTH);

	        JLabel lblProductName = new JLabel("Nazwa Produktu:");
	        addProductPanel.add(lblProductName);
	        JTextField productNameField = new JTextField();
	        addProductPanel.add(productNameField);

	        JLabel lblProductSku = new JLabel("SKU:");
	        addProductPanel.add(lblProductSku);
	        JTextField productSkuField = new JTextField();
	        addProductPanel.add(productSkuField);

	        JLabel lblProductLocation = new JLabel("Lokalizacja ID:");
	        addProductPanel.add(lblProductLocation);
	        JTextField productLocationField = new JTextField();
	        addProductPanel.add(productLocationField);

	        JButton btnAddProduct = new JButton("Dodaj Produkt");
	        btnAddProduct.addActionListener(e -> {
	            String name = productNameField.getText();
	            String sku = productSkuField.getText();
	            String locationIdText = productLocationField.getText();

	            // Walidacja danych
	            if (name.isEmpty() || sku.isEmpty() || locationIdText.isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Wszystkie pola muszą być wypełnione!", "Błąd", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            try {
	                int locationId = Integer.parseInt(locationIdText);
	                productDAO.insert(new Product(name, sku, locationId));
	                loadProducts(); // Odświeżenie tabeli
	                JOptionPane.showMessageDialog(frame, "Produkt został dodany.");
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(frame, "ID lokalizacji musi być liczbą!", "Błąd", JOptionPane.ERROR_MESSAGE);
	            }
	        });
	        addProductPanel.add(btnAddProduct);

	        // Panel dla lokalizacji
	        JPanel locationPanel = new JPanel();
	        tabbedPane.addTab("Lokalizacje", null, locationPanel, null);
	        locationPanel.setLayout(new BorderLayout());

	        // Tabela lokalizacji
	        locationTable = new JTable();
	        JScrollPane locationScrollPane = new JScrollPane(locationTable);
	        locationPanel.add(locationScrollPane, BorderLayout.CENTER);

	        // Przycisk do odświeżenia listy lokalizacji
	        JButton btnRefreshLocations = new JButton("Odśwież");
	        btnRefreshLocations.addActionListener(e -> loadLocations());
	        locationPanel.add(btnRefreshLocations, BorderLayout.SOUTH);

	        // Panel do dodawania nowych lokalizacji
	        JPanel addLocationPanel = new JPanel();
	        addLocationPanel.setLayout(new GridLayout(3, 2));
	        locationPanel.add(addLocationPanel, BorderLayout.NORTH);

	        JLabel lblWarehouseId = new JLabel("ID Magazynu:");
	        addLocationPanel.add(lblWarehouseId);
	        JTextField warehouseIdField = new JTextField();
	        addLocationPanel.add(warehouseIdField);

	        JLabel lblRack = new JLabel("Regał:");
	        addLocationPanel.add(lblRack);
	        JTextField rackField = new JTextField();
	        addLocationPanel.add(rackField);

	        JLabel lblShelf = new JLabel("Półka:");
	        addLocationPanel.add(lblShelf);
	        JTextField shelfField = new JTextField();
	        addLocationPanel.add(shelfField);

	        JButton btnAddLocation = new JButton("Dodaj Lokalizację");
	        btnAddLocation.addActionListener(e -> {
	            String warehouseIdText = warehouseIdField.getText();
	            String rack = rackField.getText();
	            String shelf = shelfField.getText();

	            // Walidacja danych
	            if (warehouseIdText.isEmpty() || rack.isEmpty() || shelf.isEmpty()) {
	                JOptionPane.showMessageDialog(frame, "Wszystkie pola muszą być wypełnione!", "Błąd", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            try {
	                int warehouseId = Integer.parseInt(warehouseIdText);
	                locationDAO.insert(new Location(warehouseId, rack, shelf));
	                loadLocations(); // Odświeżenie tabeli
	                JOptionPane.showMessageDialog(frame, "Lokalizacja została dodana.");
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(frame, "ID magazynu musi być liczbą!", "Błąd", JOptionPane.ERROR_MESSAGE);
	            }
	        });
	        addLocationPanel.add(btnAddLocation);

	        // Panel do przemieszczania produktów
	        JPanel moveProductPanel = new JPanel();
	        tabbedPane.addTab("Przemieszczanie", null, moveProductPanel, null);
	        moveProductPanel.setLayout(new GridLayout(3, 2));

	        // Komponenty do przemieszczania
	        JLabel lblProductId = new JLabel("ID Produktu:");
	        moveProductPanel.add(lblProductId);

	        JTextField productIdField = new JTextField();
	        moveProductPanel.add(productIdField);

	        JLabel lblLocationId = new JLabel("ID Nowej Lokalizacji:");
	        moveProductPanel.add(lblLocationId);

	        JTextField locationIdField = new JTextField();
	        moveProductPanel.add(locationIdField);

	        JButton btnMoveProduct = new JButton("Przemieść");
	        btnMoveProduct.addActionListener(e -> {
	            try {
	                int productId = Integer.parseInt(productIdField.getText());
	                int locationId = Integer.parseInt(locationIdField.getText());
	                movementDAO.moveProduct(productId, locationId);
	                JOptionPane.showMessageDialog(frame, "Produkt został przemieszczony.");
	                loadProducts(); // Odświeżenie tabeli produktów
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(frame, "ID produktu i ID lokalizacji muszą być liczbami!", "Błąd", JOptionPane.ERROR_MESSAGE);
	            }
	        });
	        moveProductPanel.add(btnMoveProduct);

	        // Załaduj dane na start
	        loadProducts();
	        loadLocations();
	    }

	    // Metoda ładująca produkty do tabeli
	    private void loadProducts() {
	        List<Product> products = productDAO.getAll();
	        String[] columnNames = {"ID", "Nazwa", "SKU", "Lokalizacja ID"};
	        Object[][] data = new Object[products.size()][4];

	        for (int i = 0; i < products.size(); i++) {
	            Product product = products.get(i);
	            data[i][0] = product.getId();
	            data[i][1] = product.getName();
	            data[i][2] = product.getSku();
	            data[i][3] = product.getCurrentLocationId();
	        }

	        productTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
	    }

	    // Metoda ładująca lokalizacje do tabeli
	    private void loadLocations() {
	        List<Location> locations = locationDAO.getAll();
	        String[] columnNames = {"ID", "Magazyn ID", "Regał", "Półka"};
	        Object[][] data = new Object[locations.size()][4];

	        for (int i = 0; i < locations.size(); i++) {
	            Location location = locations.get(i);
	            data[i][0] = location.getId();
	            data[i][1] = location.getWarehouseId();
	            data[i][2] = location.getRack();
	            data[i][3] = location.getShelf();
	        }

	        locationTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
	    }
	    
	    private void refreshProductTable() {
            List<Product> products = productDAO.getAll();

            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nazwa", "SKU", "Lokalizacja"}, 0);
            for (Product p : products) {
                model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getSku(),
                    p.getCurrentLocationId()
                });
            }

            productTable.setModel(model);
        }
	    
	    public Product getById(int id) {
	        String sql = "SELECT * FROM products WHERE id = ?";
	        try (Connection conn = DatabaseManager.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, id);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                return new Product(
	                        rs.getInt("id"),
	                        rs.getString("name"),
	                        rs.getString("sku"),
	                        rs.getInt("location_id")
	                );
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
