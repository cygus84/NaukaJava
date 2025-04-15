package modele;

public class Kot {

	private int id;
	private String nazwa;

	public Kot() {
		ustaw(0, "brak");
	}

	public Kot(int id, String nazwa) {
		ustaw(id, nazwa);
	}

	private void ustaw(int id, String nazwa) {
		this.id = id;
		this.nazwa = nazwa;
	}

	public int getId() {
		return id;
	}

	public String getNazwa() {
		return nazwa;
	}

	@Override
	public String toString() {
		return "Kot [id=" + id + ", nazwa='" + nazwa + "']";
	}

}
