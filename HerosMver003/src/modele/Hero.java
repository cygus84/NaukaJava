package modele;

import java.awt.Color;

import mapa.Hex;

public class Hero {
	private Hex position;
    private Color color;

    public Hero(Hex start, Color color) {
        this.position = start;
        this.color = color;
    }

    public Hex getPosition() {
        return position;
    }

    public void moveTo(Hex newPos) {
        this.position = newPos;
    }

    public Color getColor() {
        return color;
    }
}
