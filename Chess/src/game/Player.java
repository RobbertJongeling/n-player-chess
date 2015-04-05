package game;

import java.awt.Color;


/**
 *
 * @author Robbert
 */
public class Player {
    
    private int id;
	private Color color;
    
    public Player(int id, Color color) {
        this.id = id;
		this.color = color;
    }
    
    public int getId() {
        return id;
    }
	
	public Color getColor() {
		return color;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Player)) {
			return false;
		} else {
			Player p = (Player) obj;
			return (p.id == this.id);
		}
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + this.id;
		return hash;
	}
}
