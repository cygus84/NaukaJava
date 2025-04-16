package modele;

public class Unit {
	private final String name;
	private int hp;
	private final int attack;
	private final int defense;

	public Unit(String name, int hp, int attack, int defense) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
	}

	public String getName() {
		return name;
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

	public boolean isAlive() {
		return hp > 0;
	}

	public void damage(int dmg) {
		hp -= dmg;
		if (hp < 0)
			hp = 0;
	}

	@Override
	public String toString() {
		return name + " (HP: " + hp + ")";
	}
}
