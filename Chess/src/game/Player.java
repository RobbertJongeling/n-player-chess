package game;

import java.awt.Color;


/**
 *
 * @author Robbert
 */
public class Player {
    
    private int id;
	private Color colour;
    
    public Player(int id, Color colour) {
        this.id = id;
		this.colour = colour;
    }
    
    public int getId() {
        return id;
    }
	
	public Color getColor() {
		return colour;
	}
}
