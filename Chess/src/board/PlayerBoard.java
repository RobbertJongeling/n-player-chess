package board;

import game.Player;
import pieces.*;

/**
 *
 * @author Robbert
 */
public class PlayerBoard {

	private Player player;
	private PlayerBoard leftNeighbour, rightNeighbour;
	private PlayerField[][] playerfields;

	public PlayerBoard(Player player) {
		this.player = player;

		playerfields = new PlayerField[8][4];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				playerfields[i][j] = new PlayerField(i, j, player.getId());
			}
		}
		for(int i = 0; i<8; i++) {
			playerfields[i][1].addPiece(new Pawn(player));
		}
		playerfields[0][0].addPiece(new Rook(player));
		playerfields[7][0].addPiece(new Rook(player));
		playerfields[1][0].addPiece(new Knight(player));
		playerfields[6][0].addPiece(new Knight(player));
		playerfields[2][0].addPiece(new Bishop(player));
		playerfields[5][0].addPiece(new Bishop(player));
		playerfields[3][0].addPiece(new Queen(player));
		playerfields[4][0].addPiece(new King(player));
		
	}

	public void setLeftNeighbour(PlayerBoard neighbour) {
		this.leftNeighbour = neighbour;
	}

	public void setRightNeighbour(PlayerBoard neighbour) {
		this.rightNeighbour = neighbour;
	}

	public PlayerField getFieldAt(Coordinate c) {
		return getPlayerFieldAt(c.getX(), c.getY());
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * @pre 0 <= x <= 8, 0 <= y <= 8
	 * @param x coordinate based on 8x8 board, indexed from 0 to 7
	 * @param y coordinate based on 8x8 board, indexed from 0 to 7
	 * @return field at this coordinate, may be on this player board or on either of its neighbours
	 */
	public PlayerField getPlayerFieldAt(int x, int y) {
		if (y >= 0 && y <= 3) {
			return this.playerfields[x][y];
		} else {
			if (x >= 0 && x <= 3) {				
				return this.leftNeighbour.getPlayerFieldAt(7 - x, 7 - y);
			} else {
				return this.rightNeighbour.getPlayerFieldAt(7 - x, 7 - y);
			}
		}
	}
	
	public PlayerBoard getRightNeighbour() {
		return rightNeighbour;	
	}
	
	public PlayerBoard getLeftNeighbour() {
		return leftNeighbour;
	}
}
