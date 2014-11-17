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
public class Queen extends Piece {
    
    public Queen(Player player) {
        super(player);
    }

    @Override
    public List<List<PlayerField>> getLegalFieldsInOrder(PlayerBoard pb, PlayerField from) {
        Coordinate c = from.getCoordinate();
        List<List<PlayerField>> listOfFields = new ArrayList<List<PlayerField>>();

        Rook tmpRook = new Rook();
        listOfFields.addAll(tmpRook.getLegalFieldsInOrder(pb, from));
        Bishop tmpBishop = new Bishop();
        listOfFields.addAll(tmpBishop.getLegalFieldsInOrder(pb, from));
        
        return listOfFields;
    }

	@Override
	public String getLetter() {
		return "Q";
	}

	@Override
	public Point[] getPolygon() {
		return new Point[] {
			new Point(-0.2,0),
			new Point(-0.5,0.5),
			new Point(-0.1,0),
			new Point(0,0.5),
			new Point(0.1,0),
			new Point(0.5,0.5),
			new Point(0.2,0),
			new Point(0.5,-0.5),
			new Point(-0.5,-0.5)
		};
	}
}
