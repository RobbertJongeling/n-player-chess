package board;

/**
 *
 * @author Robbert
 */
public class Coordinate {
    
    private int x,y; 
    private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
	
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
	
	@Override
	public String toString() {
		return letters[x] + (y+1);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Coordinate)) {
			return false;
		} else {
			Coordinate c = (Coordinate) obj;
			return (c.x == this.x && c.y == this.y);
		}
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 47 * hash + this.x;
		hash = 47 * hash + this.y;
		return hash;
	}
}
