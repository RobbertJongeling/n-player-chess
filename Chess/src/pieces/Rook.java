package pieces;

import board.Coordinate;
import board.PlayerField;
import board.PlayerBoard;
import game.Player;
import java.util.ArrayList;
import java.util.List;
import view.Point;

/**
 *
 * @author Robbert
 */
public class Rook extends Piece {

	public Rook(Player player) {
		super(player);
	}

	public Rook() {
	}

	@Override
	public List<List<PlayerField>> getLegalFieldsInOrder(PlayerBoard pb, PlayerField from) {
		Coordinate c = from.getCoordinate();
		List<List<PlayerField>> listOfFields = new ArrayList<List<PlayerField>>();

		listOfFields.add(getFieldListToAdd(pb, c, 0));//left
		listOfFields.add(getFieldListToAdd(pb, c, 2));//up
		listOfFields.add(getFieldListToAdd(pb, c, 4));//right
		listOfFields.add(getFieldListToAdd(pb, c, 6));//down

		return listOfFields;
	}

	private List<PlayerField> getFieldListToAdd(PlayerBoard pb, Coordinate c, int direction) {
		int x = c.getX();
		int y = c.getY();
		List<PlayerField> fields = new ArrayList<PlayerField>();
		int i = 1;

		switch (direction) {
			case 0:
				while (x - i >= 0) {
					fields.add(pb.getPlayerFieldAt(x - i, y));
					i++;
				}
				break;
			case 2:
				while (y + i <= 7) {
					fields.add(pb.getPlayerFieldAt(x, y + i));
					i++;
				}
				break;
			case 4:
				while (x + i <= 7) {
					fields.add(pb.getPlayerFieldAt(x + i, y));
					i++;
				}
				break;
			case 6:
				while (y - i >= 0) {
					fields.add(pb.getPlayerFieldAt(x, y - i));
					i++;
				}
				break;
		}
		return fields;
	}

	@Override
	public String getLetter() {
		return "R";
	}

	@Override
	public Point[] getPolygon() {
		return new Point[]{
					new Point(-0.5, 0.5),
					new Point(0.5, 0.5),
					new Point(0.5, -0.5),
					new Point(-0.5, -0.5)
				};
	}
}
