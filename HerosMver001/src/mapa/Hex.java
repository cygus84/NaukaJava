package mapa;

public class Hex {
	public final int q;
	public final int r;

	public Hex(int q, int r) {
		this.q = q;
		this.r = r;
	}

	public int getS() {
		return -q - r;
	}

	public int distance(Hex other) {
		return (Math.abs(q - other.q) + Math.abs(r - other.r) + Math.abs(getS() - other.getS())) / 2;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hex) {
			Hex other = (Hex) obj;
			return this.q == other.q && this.r == other.r;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return q * 1000 + r;
	}
}
