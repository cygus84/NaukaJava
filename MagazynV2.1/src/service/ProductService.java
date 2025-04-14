package service;

import java.time.LocalDateTime;

import dao.MovementDAO;
import dao.ProductDAO;
import model.Movement;
import model.Product;

public class ProductService {
	  private final ProductDAO productDAO = new ProductDAO();
	    private final MovementDAO movementDAO = new MovementDAO();

	    // Funkcja przenoszenia produktu
	    public void moveProduct(Product product, int newLocationId) {
	        int oldLocationId = product.getCurrentLocationId();

	        // Sprawdzenie, czy produkt nie jest już w tej lokalizacji
	        if (oldLocationId == newLocationId) {
	            System.out.println("Produkt już jest w tej lokalizacji.");
	            return;
	        }

	        // Utworzenie obiektu ruchu
	        Movement movement = new Movement(
	                product.getId(),
	                oldLocationId,
	                newLocationId,
	                LocalDateTime.now()
	        );

	        // Zapisanie ruchu
	        movementDAO.save(movement);

	        // Aktualizacja lokalizacji produktu
	        productDAO.updateLocation(product, newLocationId);

	        System.out.println("Produkt został przeniesiony z lokalizacji " + oldLocationId + " do " + newLocationId);
	    }
}
