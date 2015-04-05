package board;

import pieces.Piece;

/**
 *
 * @author Robbert
 */
public class PlayerField {
    
    //coordinate
    private Coordinate coordinate;
    //piece
    private Piece piece = null;
	
	private boolean selected = false;
	private boolean projected = false;
    
	private int player;
	
    public PlayerField(Coordinate c, int player) {
        this.coordinate = c;
		this.player = player;
    }
    
    public PlayerField(Coordinate c, Piece piece, int player) {
        this.coordinate = c;
        this.piece = piece;
		this.player = player;
    }
    
    public PlayerField(int x, int y, int player) {        
        this.coordinate = new Coordinate(x,y);
		this.player = player;
    }
    
    public PlayerField(int x, int y, Piece piece, int player) {        
        this.coordinate = new Coordinate(x,y);
        this.piece = piece;
		this.player = player;
    }
    
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void removePiece() {
		this.piece = null;
	}
	
    public Piece getPiece() {
        return piece;
    }
    
    public Coordinate getCoordinate() {
        return coordinate;
    }
	
	public void project() {
		projected = true;
	}
	
	public void unProject() {
		projected = false;
	}
	
	public boolean isProjected() {
		return projected;
	}
	
	public void unSelect() {
		selected = false;
	}
	
	public void select() {
		selected = true;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public int getPlayerId() {
		return player;
	}
}
