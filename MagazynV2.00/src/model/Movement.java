package model;

import java.time.LocalDateTime;

public class Movement {
	   private int id;
	    private int productId;
	    private int fromLocationId;
	    private int toLocationId;
	    private LocalDateTime timestamp;

	    public Movement(int id, int productId, int fromLocationId, int toLocationId, LocalDateTime timestamp) {
	        this.id = id;
	        this.productId = productId;
	        this.fromLocationId = fromLocationId;
	        this.toLocationId = toLocationId;
	        this.timestamp = timestamp;
	    }

	    public Movement(int productId, int fromLocationId, int toLocationId, LocalDateTime timestamp) {
	        this.productId = productId;
	        this.fromLocationId = fromLocationId;
	        this.toLocationId = toLocationId;
	        this.timestamp = timestamp;
	    }

		public int getId() {
			return id;
		}

		public int getProductId() {
			return productId;
		}

		public int getFromLocationId() {
			return fromLocationId;
		}

		public int getToLocationId() {
			return toLocationId;
		}

		public LocalDateTime getTimestamp() {
			return timestamp;
		}
	    
	    
}
