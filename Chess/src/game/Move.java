package game;

import board.PlayerField;

/**
 *
 * @author Robbert
 */
public class Move {
	
	private PlayerField from,to;
	
	//you gotta move
	public Move(PlayerField from, PlayerField to) {
		this.from = from;
		this.to = to;
	}

	public PlayerField getFrom() {
		return from;
	}

	public PlayerField getTo() {
		return to;
	}
	
	@Override
	public String toString() {
		String connector = "->";
		if(to.getPiece() != null) {
			connector = "x";
		}
		return from.getPiece().getLetter() + from.getPlayerId() + from.getCoordinate().toString() + connector + to.getPlayerId() + to.getCoordinate().toString();
	}
}
