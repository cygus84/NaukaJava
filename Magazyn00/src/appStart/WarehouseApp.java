package appStart;

import javax.swing.*;

import dao.LocationDAO;
import dao.MovementDAO;
import dao.ProductDAO;
import model.Location;
import model.Product;

import java.awt.*;
import java.awt.event.*;
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
			int productId = Integer.parseInt(productIdField.getText());
			int locationId = Integer.parseInt(locationIdField.getText());
			movementDAO.moveProduct(productId, locationId);
			JOptionPane.showMessageDialog(frame, "Produkt został przemieszczony.");
			loadProducts(); // Odświeżenie tabeli produktów
		});
		moveProductPanel.add(btnMoveProduct);

		// Załaduj dane na start
		loadProducts();
		loadLocations();
	}

	// Metoda ładująca produkty do tabeli
	private void loadProducts() {
		List<Product> products = productDAO.getAll();
		String[] columnNames = { "ID", "Nazwa", "SKU", "Lokalizacja ID" };
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
		String[] columnNames = { "ID", "Magazyn ID", "Regał", "Półka" };
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
}
