package model;

public class Warehouse {
	private int id;
	private String name;

	public Warehouse(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Warehouse(String name) {
		this.name = name;
	}

	// Gettery i settery
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
