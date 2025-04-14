package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.ProductDAO;
import model.Product;
import ui.AddProductDialog;
import ui.EditProductDialog;

import java.awt.BorderLayout;
import java.util.List;

public class WarehouseApp {
	 private JFrame frame;
	    private JTable productTable;
	    private DefaultTableModel tableModel;
	    private final ProductDAO productDAO = new ProductDAO();

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new WarehouseApp().initialize());
	    }

	    public void initialize() {
	        frame = new JFrame("System Magazynowy");
	        frame.setSize(700, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout());

	        tableModel = new DefaultTableModel(new Object[]{"ID", "Nazwa", "SKU", "Lokalizacja"}, 0);
	        productTable = new JTable(tableModel);
	        frame.add(new JScrollPane(productTable), BorderLayout.CENTER);

	        JPanel panel = new JPanel();
	        JButton addProductButton = new JButton("Dodaj produkt");
	        JButton moveProductButton = new JButton("Przenieś");
	        panel.add(moveProductButton);
	        JButton editProductButton = new JButton("Edytuj");
	        JButton deleteProductButton = new JButton("Usuń");

	        panel.add(addProductButton);
	        panel.add(editProductButton);
	        panel.add(deleteProductButton);
	        frame.add(panel, BorderLayout.SOUTH);

	        // Obsługa przycisków
	        addProductButton.addActionListener(e -> {
	            AddProductDialog dialog = new AddProductDialog(frame, this::refreshProductTable);
	            dialog.setVisible(true);
	        });

	        editProductButton.addActionListener(e -> {
	            int row = productTable.getSelectedRow();
	            if (row == -1) return;

	            int productId = (int) productTable.getValueAt(row, 0);
	            Product product = productDAO.getById(productId);
	            EditProductDialog dialog = new EditProductDialog(frame, product);
	            dialog.setVisible(true);
	            refreshProductTable();
	        });

	        deleteProductButton.addActionListener(e -> {
	            int row = productTable.getSelectedRow();
	            if (row == -1) return;

	            int productId = (int) productTable.getValueAt(row, 0);
	            productDAO.delete(productId);
	            refreshProductTable();
	        });

	        refreshProductTable();
	        frame.setVisible(true);
	        frame.setLocationRelativeTo(null);
	    }

	    public void refreshProductTable() {
	        tableModel.setRowCount(0);
	        List<Product> products = productDAO.getAll();
	        for (Product p : products) {
	            tableModel.addRow(new Object[]{
	                p.getId(), p.getName(), p.getSku(), p.getCurrentLocationId()
	            });
	        }
	    }
}
