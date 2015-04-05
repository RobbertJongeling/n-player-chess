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
}
