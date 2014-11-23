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
}
