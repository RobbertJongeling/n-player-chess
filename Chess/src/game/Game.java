package game;

import board.Board;
import board.Coordinate;
import board.PlayerField;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import view.ViewMaker;

/**
 *
 * @author Robbert
 */
public class Game {

	private Board board;
	private Player[] players;
	private int n;
	private ViewMaker viewMaker;
	private PlayerField selected;
	private List<PlayerField> projected;
	//TODO dynamic in menu
	private Color colors[] = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN};
	int turn;

	public Game(int n) {
		this.n = n;
		this.turn = 0;
	}

	public void start() {
		players = new Player[n];
		for (int i = 0; i < n; i++) {
			players[i] = new Player(i, colors[i % colors.length]);
		}
		board = new Board(players);

		viewMaker = new ViewMaker(this);
		viewMaker.start();
		log("Player 0 to move");//TODO 
	}

	public void selectField(int p, int i, int j) {
		boolean same = false;
		boolean onproj = false;

		PlayerField clicked = board.getPlayerFieldAt(p, i, j);

		//select of selected
		if (selected != null && selected.equals(clicked)) {
			same = true;
		}

		//select of projected, other than same
		if (projected != null) {
			for (PlayerField proj : projected) {
				Coordinate c = proj.getCoordinate();
				if (proj.equals(clicked)) {
					if (selected.getPiece().getPlayer().getId() == turn) {
						Move move = new Move(selected, clicked);
						if (isLegal(move)) {
							log("Player " + turn + " move: " + move.toString());
							board.performMove(move);
							if (endsGame()) {
								//TODO implement
							} else {
								turn = (turn + 1) % players.length;
								log("Player " + turn + " to move");
							}
						}
					}

					onproj = true;
				}
			}
		}

		//unselect
		if (selected != null) {
			selected.unSelect();
			for (PlayerField proj : projected) {
				proj.unProject();
			}
			selected = null;
			projected = null;
		}

		//select
		if (!same && !onproj) {
			//selected = board.getPlayerboards()[p].getPlayerFieldAt(i, j);
			selected = clicked;
			projected = new ArrayList<PlayerField>();
			projected.addAll(board.getLegalFields(selected));
			//TODO: 
			/*List<Move> legal = board.getLegalMoves(selected);
			for (Move m : legal) {
			projected.add(m.getTo());
			}*/

			selected.select();
			for (PlayerField proj : projected) {
				proj.project();
			}
		}
	}

	public int getNrPlayers() {
		return n;
	}

	public Board getBoard() {
		return board;
	}

	public void log(String text) {
		viewMaker.log(text);
	}

	private boolean isLegal(Move move) {
		//TODO implement
		//not legal if check yourself
		return true;
	}

	private boolean endsGame() {
		//TODO implement
		//engds game depending on game mdoe
		return false;
	}
}
