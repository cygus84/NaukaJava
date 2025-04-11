package model;

public class Location {
	private int id;
	private int warehouseId;
	private String rack;
	private String shelf;

	public Location(int id, int warehouseId, String rack, String shelf) {
		this.id = id;
		this.warehouseId = warehouseId;
		this.rack = rack;
		this.shelf = shelf;
	}

	public Location(int warehouseId, String rack, String shelf) {
		this.warehouseId = warehouseId;
		this.rack = rack;
		this.shelf = shelf;
	}

	// Gettery i settery
	public int getId() {
		return id;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public String getRack() {
		return rack;
	}

	public String getShelf() {
		return shelf;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public void setRack(String rack) {
		this.rack = rack;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
}
