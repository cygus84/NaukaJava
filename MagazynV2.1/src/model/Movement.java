package model;

import java.time.LocalDateTime;

public class Movement {
	 private int id;
	    private int productId;
	    private int oldLocationId;
	    private int newLocationId;
	    private LocalDateTime timestamp;

	    // Konstruktor
	    public Movement(int productId, int oldLocationId, int newLocationId, LocalDateTime timestamp) {
	        this.productId = productId;
	        this.oldLocationId = oldLocationId;
	        this.newLocationId = newLocationId;
	        this.timestamp = timestamp;
	    }

	    // Gettery i settery
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getProductId() {
	        return productId;
	    }

	    public void setProductId(int productId) {
	        this.productId = productId;
	    }

	    public int getOldLocationId() {
	        return oldLocationId;  // Getter dla starego ID lokalizacji
	    }

	    public void setOldLocationId(int oldLocationId) {
	        this.oldLocationId = oldLocationId;
	    }

	    public int getNewLocationId() {
	        return newLocationId;
	    }

	    public void setNewLocationId(int newLocationId) {
	        this.newLocationId = newLocationId;
	    }

	    public LocalDateTime getTimestamp() {
	        return timestamp;
	    }

	    public void setTimestamp(LocalDateTime timestamp) {
	        this.timestamp = timestamp;
	    }
}
