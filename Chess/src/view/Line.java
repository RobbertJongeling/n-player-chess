package view;

/**
 *
 * @author Robbert
 */
public class Line {
    
    private Point from, to;
    
    public Line(Point from, Point to) {
        this.from = from;
        this.to = to;
    }
    
    public Line(double fromx, double fromy, double tox, double toy) {
        this.from = new Point(fromx, fromy);
        this.to = new Point(tox, toy);
    }
    
    public Point getFrom() {
        return from;        
    }
    
    public Point getTo() {
        return to;
    }
    
    /**
     * returns for this line the i-th of n+1 points equally distributed over the line
     * @param i
     * @param n
     * @return 
     */
    public Point getEqualPart(int i, int n) {     
        if(i == 0) {
            return this.from;
        } else 
        if(i == n+1) {
            return this.to;
        } else {
            double fx = from.getX();
            double fy = from.getY();
            double tx = to.getX();
            double ty = to.getY();
            return new Point(fx + i*((tx-fx)/n), fy + i*((ty-fy)/n));
        }
    }
}
