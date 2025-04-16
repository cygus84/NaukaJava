package modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import mapa.Hex;

public class Hero {
	private Hex position;
	private Color color;
	private int hp = 100;
	private int attack;
	private int defense;
	private final ArrayList<Unit> army = new ArrayList<Unit>();

	public Hero(Hex start, Color color, int attack, int defense) {
		this.position = start;
		this.color = color;
		this.attack = attack;
		this.defense = defense;
	}
	
	public void addUnit(Unit unit) {
	    army.add(unit);
	}

	public ArrayList<Unit> getArmy() {
	    return army;
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

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public void damage(int amount) {
		hp -= amount;
		if (hp < 0)
			hp = 0;
	}

	public boolean isAlive() {
		return hp > 0;
	}
}
