package modele;

import mapa.Hex;

public class Hero {
	private Hex position;

	public Hero(Hex startPos) {
		this.position = startPos;
	}

	public Hex getPosition() {
		return position;
	}

	public void moveTo(Hex newPosition) {
		this.position = newPosition;
	}
}
