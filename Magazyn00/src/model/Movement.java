package model;

import java.time.LocalDateTime;

public class Movement {
	private int id;
	private int productId;
	private Integer fromLocationId;
	private int toLocationId;
	private LocalDateTime movedAt;

	public Movement(int id, int productId, Integer fromLocationId, int toLocationId, LocalDateTime movedAt) {
		this.id = id;
		this.productId = productId;
		this.fromLocationId = fromLocationId;
		this.toLocationId = toLocationId;
		this.movedAt = movedAt;
	}

	public Movement(int productId, Integer fromLocationId, int toLocationId) {
		this.productId = productId;
		this.fromLocationId = fromLocationId;
		this.toLocationId = toLocationId;
		this.movedAt = LocalDateTime.now();
	}

	// Gettery i settery
	public int getId() {
		return id;
	}

	public int getProductId() {
		return productId;
	}

	public Integer getFromLocationId() {
		return fromLocationId;
	}

	public int getToLocationId() {
		return toLocationId;
	}

	public LocalDateTime getMovedAt() {
		return movedAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public void setFromLocationId(Integer fromLocationId) {
		this.fromLocationId = fromLocationId;
	}

	public void setToLocationId(int toLocationId) {
		this.toLocationId = toLocationId;
	}

	public void setMovedAt(LocalDateTime movedAt) {
		this.movedAt = movedAt;
	}
}
