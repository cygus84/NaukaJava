package mapa;

import mapObject.MapObject;
import terrainType.TerrainType;

public class Hex {
	 public final int q, r;
	    private TerrainType terrain;
	    private MapObject object;

	    public Hex(int q, int r) {
	        this.q = q;
	        this.r = r;
	        this.terrain = TerrainType.GRASS;
	        this.object = MapObject.NONE;
	    }

	    public int getS() {
	        return -q - r;
	    }

	    public void setTerrain(TerrainType terrain) {
	        this.terrain = terrain;
	    }

	    public TerrainType getTerrain() {
	        return terrain;
	    }

	    public MapObject getObject() {
	        return object;
	    }

	    public void setObject(MapObject object) {
	        this.object = object;
	    }
	    
	    public int distanceTo(Hex other) {
	        return (Math.abs(q - other.q) + Math.abs(r - other.r) + Math.abs(getS() - other.getS())) / 2;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj instanceof Hex other) {
	            return q == other.q && r == other.r;
	        }
	        return false;
	    }

	    @Override
	    public int hashCode() {
	        return q * 1000 + r;
	    }
}
