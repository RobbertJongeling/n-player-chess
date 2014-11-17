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
public class Pawn extends Piece {
    
    public Pawn(Player player) {
        super(player);
    }

    @Override
    public List<List<PlayerField>> getLegalFieldsInOrder(PlayerBoard pb, PlayerField from) {
        Coordinate c = from.getCoordinate();
        int x = c.getX();
        int y = c.getY();
        List<List<PlayerField>> listOfFields = new ArrayList<List<PlayerField>>();

		if(from.getPlayerId() == player.getId()) {
			List<PlayerField> oneForward = getFieldListToAdd(pb, x, y + 1);
			listOfFields.add(oneForward);			
			listOfFields.add(getHitFieldListToAdd(pb, x+1, y + 1));
			listOfFields.add(getHitFieldListToAdd(pb, x-1, y + 1));
			if(y==1 && pb.getPlayer().getId() == this.player.getId() && oneForward.size() > 0) {				
				listOfFields.add(getFieldListToAdd(pb, x, y + 2));
			}
		} else {
			listOfFields.add(getFieldListToAdd(pb, x, y - 1));
			listOfFields.add(getHitFieldListToAdd(pb, x+1, y - 1));
			listOfFields.add(getHitFieldListToAdd(pb, x-1, y - 1));
		}

        return listOfFields;
    }

    private List<PlayerField> getFieldListToAdd(PlayerBoard pb, int x, int y) {
        List<PlayerField> fields = new ArrayList<PlayerField>();
		if(x >= 0 && x <= 7) {
			PlayerField toAdd = (pb.getPlayerFieldAt(x, y));
			if(toAdd.getPiece() == null) {
				fields.add(toAdd);
			}
		}		
        return fields;
    }

	private List<PlayerField> getHitFieldListToAdd(PlayerBoard pb, int x, int y) {//TODO enpassent 
		List<PlayerField> fields = new ArrayList<PlayerField>();
		if(x >= 0 && x <= 7) {
			PlayerField toAdd = (pb.getPlayerFieldAt(x, y));
			Piece toHit = toAdd.getPiece();
			if(toHit != null && toHit.player.getId() != player.getId()) {
				fields.add(toAdd);
			}
		}		
        return fields;
	}
	
	@Override
	public String getLetter() {
		return "P";
	}

	@Override
	public Point[] getPolygon() {
		return new Point[] {
			new Point(-0.1, 0),
			new Point(-0.1, 0.5), 
			new Point(0.1,0.5), 
			new Point(0.1,0), 			 			
			new Point(0.5,-0.5),
			new Point(-0.5,-0.5)			
		};//TODO draw something better
	}
}
