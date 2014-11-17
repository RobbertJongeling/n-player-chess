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
public class Knight extends Piece {

    public Knight(Player player) {
        super(player);
    }

    @Override
    public List<List<PlayerField>> getLegalFieldsInOrder(PlayerBoard pb, PlayerField from) {
        Coordinate c = from.getCoordinate();
        int x = c.getX();
        int y = c.getY();
        List<List<PlayerField>> listOfFields = new ArrayList<List<PlayerField>>();

        listOfFields.add(getFieldListToAdd(pb, x - 2, y + 1));
        listOfFields.add(getFieldListToAdd(pb, x - 1, y + 2));
        listOfFields.add(getFieldListToAdd(pb, x + 1, y + 2));
        listOfFields.add(getFieldListToAdd(pb, x + 2, y + 1));
        listOfFields.add(getFieldListToAdd(pb, x + 2, y - 1));
        listOfFields.add(getFieldListToAdd(pb, x + 1, y - 2));
        listOfFields.add(getFieldListToAdd(pb, x - 1, y - 2));
        listOfFields.add(getFieldListToAdd(pb, x - 2, y - 1));

        return listOfFields;
    }

    private List<PlayerField> getFieldListToAdd(PlayerBoard pb, int x, int y) {
        List<PlayerField> fields = new ArrayList<PlayerField>();
		if(x >= 0 && x <= 7 && y >= 0 && y <= 7) {
			fields.add(pb.getPlayerFieldAt(x, y));
		}
        return fields;
    }

	@Override
	public String getLetter() {
		return "N";
	}

	@Override
	public Point[] getPolygon() {
		return new Point[] {
			new Point(-0.25,0),
			new Point(-0.25,0.5),
			new Point(0.25,0.5),
			new Point(0.5,-0.5),
			new Point(-0.5,-0.5)
		};
	}
}
