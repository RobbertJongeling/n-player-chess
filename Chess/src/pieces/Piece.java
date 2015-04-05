package pieces;

import board.PlayerField;
import board.PlayerBoard;
import game.Player;
import java.util.List;
import view.Point;

/**
 *
 * @author Robbert
 */
public abstract class Piece {
    
    protected Player player;
    
    public Piece() {
        
    }
    
    public Piece(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
	
	public abstract String getLetter();		
    
    public abstract List<List<PlayerField>> getLegalFieldsInOrder(PlayerBoard pb, PlayerField from);

	public abstract Point[] getPolygon();
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Piece)) {
			return false;
		} else {
			Piece p = (Piece) obj;
			return (p.player.equals(this.player));
		}
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 37 * hash + (this.player != null ? this.player.hashCode() : 0);
		return hash;
	}
}
