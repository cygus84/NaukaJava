package modele;

import java.awt.Color;

import mapa.Hex;

public class Hero {
	 private Hex position;
	    private Color color;
	    private int hp = 100;

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

	    public int getHp() {
	        return hp;
	    }

	    public void damage(int amount) {
	        hp -= amount;
	        if (hp < 0) hp = 0;
	    }

	    public boolean isAlive() {
	        return hp > 0;
	    }
}
