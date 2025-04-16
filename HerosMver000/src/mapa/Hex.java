package mapa;

public class Hex {
	public final int q; // współrzędna axial (q)
	public final int r; // współrzędna axial (r)

	public Hex(int q, int r) {
		this.q = q;
		this.r = r;
	}

	public int getS() {
		return -q - r; // dopełnienie współrzędnych cube
	}

	public double distance(Hex other) {
		return (Math.abs(q - other.q) + Math.abs(r - other.r) + Math.abs(getS() - other.getS())) / 2.0;
	}
}
