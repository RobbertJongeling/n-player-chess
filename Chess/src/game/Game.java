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
	private ViewMaker view;
	private PlayerField selected;
	private List<PlayerField> projected;
	private List<Move> moves;
	//TODO dynamic in menu
	private Color colors[] = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN};
	int turn;
	
	public Game(int n) {
		this.n = n;
		this.turn = 0;
		moves = new ArrayList<Move>();
	}

	public void start() {
		players = new Player[n];
		for (int i = 0; i < n; i++) {
			players[i] = new Player(i, colors[i % colors.length]);
		}
		board = new Board(players);

		view = new ViewMaker(this);
	}

	public void selectField(int p, int i, int j) {
		//System.out.println("selected board: " + p + ", field: (" + i + ", " + j + ")");
		boolean same = false;
		boolean onproj = false;
		if (selected != null && selected.getPlayerId() == p && selected.getCoordinate().getX() == i && selected.getCoordinate().getY() == j) {
			same = true;
		}

		if (projected != null) {
			for (PlayerField proj : projected) {
				Coordinate c = proj.getCoordinate();
				if (proj.getPlayerId() == p && c.getX() == i && c.getY() == j) {
					//System.out.println("move to: (" + i + ", " + j + ")");
					if(selected.getPiece().getPlayer().getId() == turn) {
						Move move = new Move(selected, board.getPlayerFieldAt(p, i, j));
						board.performMove(move);
						turn = (turn + 1) % players.length;			
						moves.add(move);
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

		if (!same && !onproj) {
			selected = board.getPlayerboards()[p].getPlayerFieldAt(i, j);
			projected = new ArrayList<PlayerField>();
			projected.addAll(board.getLegalFields(selected));

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
}
