package model;

public class Product {
	private int id;
	private String name;
	private String sku;
	private int currentLocationId;

	public Product(int id, String name, String sku, int currentLocationId) {
		this.id = id;
		this.name = name;
		this.sku = sku;
		this.currentLocationId = currentLocationId;
	}

	public Product(String name, String sku, int currentLocationId) {
		this.name = name;
		this.sku = sku;
		this.currentLocationId = currentLocationId;
	}

	// Gettery i settery
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getSku() {
		return sku;
	}

	public int getCurrentLocationId() {
		return currentLocationId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setCurrentLocationId(int currentLocationId) {
		this.currentLocationId = currentLocationId;
	}
}
