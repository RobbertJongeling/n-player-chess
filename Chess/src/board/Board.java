package board;

import game.Move;
import pieces.Piece;
import game.Player;
import java.util.ArrayList;
import java.util.List;
import pieces.Pawn;
import pieces.Queen;

/**
 *
 * @author Robbert
 */
public class Board {

	private PlayerBoard[] playerboards;

	/**
	 * create board
	 * @param players 
	 */
	public Board(Player[] players) {
		playerboards = new PlayerBoard[players.length];
		for (int i = 0; i < players.length; i++) {
			playerboards[i] = new PlayerBoard(players[i]);
		}

		for (int i = 0; i < playerboards.length; i++) {
			if (i == 0) {
				playerboards[0].setLeftNeighbour(playerboards[playerboards.length - 1]);
			} else {
				playerboards[i].setLeftNeighbour(playerboards[i - 1]);
			}
			playerboards[i].setRightNeighbour(playerboards[(i + 1) % playerboards.length]);
		}
	}

	public List<PlayerField> getLegalFields(int p, int i, int j) {
		return getLegalFields(playerboards[p].getPlayerFieldAt(i, j));
	}

	/**
	 * get legal fields of move from this field
	 * @param pb playerboard on which field lies
	 * @param from field from which we want to move
	 */
	public List<PlayerField> getLegalFields(PlayerField from) {
		PlayerBoard pb = playerboards[from.getPlayerId()];
		Piece piece = from.getPiece();
		if (piece != null) {
			List<List<PlayerField>> legalFields = piece.getLegalFieldsInOrder(pb, from);
			if (legalFields != null) {
				List<PlayerField> toKeep = new ArrayList<PlayerField>();

				for (int l = 0; l < legalFields.size(); l++) {
					List<PlayerField> toKeepLocal = new ArrayList<PlayerField>();

					List<PlayerField> lf = legalFields.get(l);
					for (int i = 0; i < lf.size(); i++) {
						PlayerField f = lf.get(i);
						Piece p = f.getPiece();
						if (p != null) {
							if (p.getPlayer().getId() != piece.getPlayer().getId()) {
								toKeepLocal.add(f);
							}
							break;
						} else {
							toKeepLocal.add(f);
						}
					}
					toKeep.addAll(toKeepLocal);
				}

				return toKeep;
			}
		}

		return new ArrayList<PlayerField>();
	}

	public PlayerBoard[] getPlayerboards() {
		return playerboards;
	}

	public PlayerField getPlayerFieldAt(int playerboard, int x, int y) {
		return playerboards[playerboard].getPlayerFieldAt(x, y);
	}

	public void performMove(Move todo) {
		PlayerField from = todo.getFrom();
		PlayerField to = todo.getTo();
		Piece toMove = from.getPiece();
		if (to.getCoordinate().getY() == 0 && toMove.getClass() == Pawn.class) {
			toMove = new Queen(toMove.getPlayer());//TODO be able to choose
		}
		from.removePiece();
		to.setPiece(toMove);
	}
}
