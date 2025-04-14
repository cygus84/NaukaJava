package ui;

import javax.swing.*;

import dao.MovementDAO;
import dao.ProductDAO;
import model.Movement;
import model.Product;

import java.awt.*;
import java.time.LocalDateTime;

public class MoveProductDialog extends JDialog {
	private JTextField newLocationField;
	private final Product product;
	private final ProductDAO productDAO = new ProductDAO();
	private final MovementDAO movementDAO = new MovementDAO();
	private final Runnable onSaveCallback;

	public MoveProductDialog(Frame owner, Product product, Runnable onSaveCallback) {
		super(owner, "Przenieś produkt", true);
		this.product = product;
		this.onSaveCallback = onSaveCallback;

		setLayout(new GridLayout(3, 2, 10, 10));

		add(new JLabel("Produkt:"));
		add(new JLabel(product.getName()));

		add(new JLabel("Nowa lokalizacja (ID):"));
		newLocationField = new JTextField();
		add(newLocationField);

		JButton moveButton = new JButton("Przenieś");
		moveButton.addActionListener(e -> onMove());
		add(moveButton);

		JButton cancelButton = new JButton("Anuluj");
		cancelButton.addActionListener(e -> dispose());
		add(cancelButton);

		setSize(300, 150);
		setLocationRelativeTo(owner);
	}

	private void onMove() {
		int newLocationId;

		try {
			newLocationId = Integer.parseInt(newLocationField.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Nieprawidłowy ID lokalizacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
			return;
		}

		int oldLocationId = product.getCurrentLocationId();
		if (oldLocationId == newLocationId) {
			JOptionPane.showMessageDialog(this, "Produkt już znajduje się w tej lokalizacji.");
			return;
		}

		// Zapisz ruch
		Movement movement = new Movement(product.getId(), oldLocationId, newLocationId, LocalDateTime.now());
		movementDAO.save(movement);

		// Zaktualizuj lokalizację produktu
		product.setCurrentLocationId(newLocationId);
		productDAO.update(product);

		onSaveCallback.run();
		dispose();
	}
}
