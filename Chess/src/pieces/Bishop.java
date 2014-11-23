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
public class Bishop extends Piece {

	public Bishop(Player player) {
		super(player);
	}

	public Bishop() {
	}

	@Override
	public List<List<PlayerField>> getLegalFieldsInOrder(PlayerBoard pb, PlayerField from) {
		Coordinate c = from.getCoordinate();
		List<List<PlayerField>> listOfFields = new ArrayList<List<PlayerField>>();

		listOfFields.add(getFieldListToAdd(pb, c, 1));//upleft
		listOfFields.add(getFieldListToAdd(pb, c, 3));//upright
		listOfFields.add(getFieldListToAdd(pb, c, 5));//downright
		listOfFields.add(getFieldListToAdd(pb, c, 7));//downleft

		return listOfFields;
	}

	private List<PlayerField> getFieldListToAdd(PlayerBoard pb, Coordinate c, int direction) {
		int x = c.getX();
		int y = c.getY();
		List<PlayerField> fields = new ArrayList<PlayerField>();
		int i = 1;
		switch (direction) {
			case 1:
				while (x - i >= 0 && y + i <= 7) {
					if (y + i < 4) {
						fields.add(pb.getPlayerFieldAt(x - i, y + i));
					} else {
						if (x - i >= 4) {
							fields.add(pb.getRightNeighbour().getPlayerFieldAt(7 - (x - i), 7 - (y + i)));
						} else {
							if(x+y >= 7) {
								fields.add(pb.getRightNeighbour().getPlayerFieldAt(7 - (x - i), 7 - (y + i)));
							} else {
								fields.add(pb.getLeftNeighbour().getPlayerFieldAt(7 - (x - i), 7 - (y + i)));
							}
						}
					}
					i++;
				}
				break;
			case 3:
				while (x + i <= 7 && y + i <= 7) {
					if (y + i < 4) {
						fields.add(pb.getPlayerFieldAt(x + i, y + i));
					} else {
						if (x + i >= 4) {
							if(7-x + y >= 8) {
								fields.add(pb.getLeftNeighbour().getPlayerFieldAt(7 - (x + i), 7 - (y + i)));
							} else {
								fields.add(pb.getRightNeighbour().getPlayerFieldAt(7 - (x + i), 7 - (y + i)));
							}
						} else {
							if(x+y >= 7) {
								fields.add(pb.getRightNeighbour().getPlayerFieldAt(7 - (x + i), 7 - (y + i)));
							} else {
								fields.add(pb.getLeftNeighbour().getPlayerFieldAt(7 - (x + i), 7 - (y + i)));
							}
						}
					}
					i++;
				}
				break;
			case 5:
				while (x + i <= 7 && y - i >= 0) {
					fields.add(pb.getPlayerFieldAt(x + i, y - i));
					i++;
				}
				break;
			case 7:
				while (x - i >= 0 && y - i >= 0) {
					fields.add(pb.getPlayerFieldAt(x - i, y - i));
					i++;
				}
				break;
		}
		return fields;
	}

	@Override
	public String getLetter() {
		return "B";
	}

	@Override
	public Point[] getPolygon() {
		return new Point[]{
					new Point(0, 0.5),
					new Point(0.5, -0.5),
					new Point(-0.5, -0.5)
				};
	}
}
