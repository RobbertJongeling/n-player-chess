package view;

import board.Board;
import board.PlayerBoard;
import game.Game;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import pieces.Piece;

/**
 *
 * @author Robbert
 */
public class View implements GLEventListener, MouseListener {

	private Game game;
	//private float r = 1.0f;
	private float r = 0.4f;
	private boolean calced = false;
	private Point[][] points;
	private DrawField[][] drawfields;
	//private final float Z = -3.0f;
	private final float Z = -1.0f;
	private MouseEvent mouse;
	private GLU glu;

	public View(Game game) {
		this.game = game;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// Use debug pipeline
		// drawable.setGL(new DebugGL(drawable.getGL()));

		GL gl = drawable.getGL();
								
		glu = new GLU();
		System.out.println("INIT GL IS: " + gl.getClass().getName());

		// Enable VSync
		gl.setSwapInterval(1);

		// Setup the drawing area and shading mode
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL gl = drawable.getGL();
		glu = new GLU();

		if (height <= 0) { // avoid a divide by zero error!

			height = 1;
		}
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		//glu.gluPerspective(45.0f, h, 1.0, 20.0);
		glu.gluPerspective(45.0f, h, 1.0, 100.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void update(GLCanvas drawable) {
		display(drawable);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		// Clear the drawing area
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		// Reset the current matrix to the "identity"
		gl.glLoadIdentity();

		//for mouse catching
		if (mouse != null) {
			catchMouse(gl);
			mouse = null;
		}

		drawBoard(gl);

		// Flush all drawing operations to the graphics card
		gl.glFlush();
	}

	private void catchMouse(GL gl) {
		int viewport[] = new int[4];
		double mvmatrix[] = new double[16];
		double projmatrix[] = new double[16];
		int realy = 0;// GL y coord pos
		double wcoord[] = new double[4];// wx, wy, wz;// returned xyz coords
		int x = mouse.getX(), y = mouse.getY();
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, mvmatrix, 0);
		gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, projmatrix, 0);
		/* note viewport[3] is height of window in pixels */
		realy = viewport[3] - (int) y - 1;
		//System.out.println("Coordinates at cursor are (" + x + ", " + realy);
		glu.gluUnProject((double) x, (double) realy, 0, //		
				mvmatrix, 0,
				projmatrix, 0,
				viewport, 0,
				wcoord, 0);
		/*System.out.println("World coords at z=-3.0 are ( " //
		+ wcoord[0] + ", " + wcoord[1] + ", " + wcoord[2]
		+ ")");*/

		//handleSelection(wcoord[0]*3, wcoord[1]*3);
		handleSelection(wcoord[0], wcoord[1]);
	}

	/**
	 * 
	 * @param x world coordinate
	 * @param y world coordingate
	 */
	private void handleSelection(double x, double y) {
		double rad = Math.sqrt(x * x + y * y);
		double theta = 2 * Math.PI / game.getNrPlayers();	
		double acos = Math.acos(x / rad);		
		
		if(y < 0) {
			acos = 2 * Math.PI - acos;
		}
		
		int boardnr = (int) (acos / theta);//floor
		double xin0 = x, yin0 = y;
		
		if (boardnr > 0) {
			acos -= (boardnr*theta);
			xin0 = Math.cos(acos) * rad;
			//yin0 = Math.sin(asin) * rad;
			yin0 = Math.sqrt(rad*rad - xin0*xin0);
		}
				
		Point toTest = new Point(xin0, yin0);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				if (contains(toTest, drawfields[i][j].getPoints())) {
					//board.getPlayerboards()[boardnr].getFieldAt(i, j).toggleSelected();
					game.selectField(boardnr, i, j);
					return;
				}
			}
		}
	}

	private boolean contains(Point test, Point[] boundary) {
		for(int i = 0; i<boundary.length; i++) {
			Point a = boundary[i];
			Point b = boundary[(i+1)%boundary.length];
			Point va = new Point(a.getX() - test.getX(), a.getY() - test.getY());
			Point vb = new Point(b.getX() - test.getX(), b.getY() - test.getY());
			double z = va.getX() * vb.getY() - vb.getX() * va.getY();
			//System.out.println("test: " + test.toString() + " a: " + a.toString() + " b: " + b.toString() + " va: " + va.toString() + " vb: " + vb.toString() + " z: " + z);
			if(z > 0) {
				return false;
			}
		}
		return true;
	}
	
	private void drawBoard(GL gl) {
		//for world drawing
		PlayerBoard[] pboards = game.getBoard().getPlayerboards();
		int n = pboards.length;
		double theta = 2 * Math.PI / n;

		if (!calced || points == null) {
			calc(theta);
			calced = true;
		}

		for (int i = 0; i < n; i++) {//TODO 1->n
			gl.glPushMatrix();
			gl.glTranslatef(0.0f, 0.0f, Z);
			gl.glRotated(180 / Math.PI * i * theta/* - 90 - 90 / Math.PI * theta*/, 0, 0, 1);
			drawPlayerboard(gl, pboards[i], theta, i);
			gl.glPopMatrix();
		}
	}

	public void drawPlayerboard(GL gl, PlayerBoard pb, double theta, int playernr) {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				//draw field
				DrawField drawfield = drawfields[i][j];
				Point[] drawfieldpoints = drawfield.getPoints();
				Point tl = drawfieldpoints[0];
				Point tr = drawfieldpoints[1];
				Point br = drawfieldpoints[2];
				Point bl = drawfieldpoints[3];

				float[] fieldcolor = drawfield.getColor();
				gl.glColor3f(fieldcolor[0], fieldcolor[1], fieldcolor[2]);

				//draw fields
				gl.glBegin(GL.GL_QUADS);
				gl.glVertex3d(tl.getX(), tl.getY(), 0);
				gl.glVertex3d(tr.getX(), tr.getY(), 0);
				gl.glVertex3d(br.getX(), br.getY(), 0);
				gl.glVertex3d(bl.getX(), bl.getY(), 0);
				gl.glEnd();

				if (pb.getPlayerFieldAt(i, j).isSelected()) {
					gl.glColor4f(0, 0.5f, 0.5f, 1);
					gl.glBegin(GL.GL_QUADS);
					gl.glVertex3d(tl.getX(), tl.getY(), 0);
					gl.glVertex3d(tr.getX(), tr.getY(), 0);
					gl.glVertex3d(br.getX(), br.getY(), 0);
					gl.glVertex3d(bl.getX(), bl.getY(), 0);
					gl.glEnd();
				}
				
				if (pb.getPlayerFieldAt(i, j).isProjected()) {
					gl.glColor4f(0.5f, 0.5f, 0, 1);
					gl.glBegin(GL.GL_QUADS);
					gl.glVertex3d(tl.getX(), tl.getY(), 0);
					gl.glVertex3d(tr.getX(), tr.getY(), 0);
					gl.glVertex3d(br.getX(), br.getY(), 0);
					gl.glVertex3d(bl.getX(), bl.getY(), 0);
					gl.glEnd();
				}

				Piece piece = pb.getPlayerFieldAt(i, j).getPiece();
				if (piece != null) {
					Color color = piece.getPlayer().getColor();
					Point[] piecePoints = piece.getPolygon();
					Point toTranslate = drawfield.getMiddle();

					gl.glPushMatrix();
					gl.glTranslated(toTranslate.getX(), toTranslate.getY(), 0f);					
					double extraRot = (pb.getPlayer().getId() != piece.getPlayer().getId()) ? 180 : 0; 					
					gl.glRotated((90 / Math.PI * theta + 90) - extraRot, 0, 0, 1);
					double toScale = drawfield.getScale();
					gl.glScaled(toScale, toScale, 0);
					gl.glBegin(GL.GL_POLYGON);
					gl.glColor3d(color.getRed(), color.getGreen(), color.getBlue());
					for (Point p : piecePoints) {
						gl.glVertex3d(p.getX(), p.getY(), 0);
					}
					gl.glEnd();
					gl.glPopMatrix();

				}
			}
		}
	}

	private void calc(double theta) {
		double lx = Math.cos(theta) * r, ly = Math.sin(theta) * r;
		double lmx = Math.cos(3 * theta / 4) * r, lmy = Math.sin(3 * theta / 4) * r;
		double rmx = Math.cos(theta / 4) * r, rmy = Math.sin(theta / 4) * r;
		double rx = r, ry = 0;
		float cx = 0.0f, cy = 0.0f;

		points = new Point[9][5];
		Line fromLine = new Line(rmx, rmy, lmx, lmy);
		for (int i = 0; i < 9; i++) {
			Point fromPoint = fromLine.getEqualPart(i, 8);
			Point toPoint = null;
			if (i < 4) {
				Line toLine = new Line(rx, ry, cx, cy);
				toPoint = toLine.getEqualPart(i, 4);
			}
			if (i == 4) {
				toPoint = new Point(cx, cy);
			}
			if (i > 4) {
				Line toLine = new Line(cx, cy, lx, ly);
				toPoint = toLine.getEqualPart(i - 4, 4);
			}
			for (int j = 0; j < 5; j++) {
				Line tmpLine = new Line(fromPoint, toPoint);
				points[i][j] = tmpLine.getEqualPart(j, 4);
			}
		}

		drawfields = new DrawField[8][4];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 4; j++) {
				//draw field
				DrawField df = new DrawField();
				Point tl = points[i + 1][j];
				Point tr = points[i][j];
				Point br = points[i][j + 1];
				Point bl = points[i + 1][j + 1];
				df.setPoints(new Point[]{tl, tr, br, bl});

				if ((i + j) % 2 == 0) {
					df.setColor(new float[]{0.15f, 0.1f, 0.0f});
				} else {
					df.setColor(new float[]{1.0f, 1.0f, 1.0f});
				}

				Point middle = getMiddle(tl, tr, br, bl);
				df.setMiddle(middle);
				if (i >= 4) {
					df.setScale(drawfields[7 - i][j].getScale());
				} else {
					df.setScale(getScale(middle, tl, tr, br, bl));
				}
				drawfields[i][j] = df;
			}
		}

	}

	private Point getMiddle(Point tl, Point tr, Point br, Point bl) {
		return new Point(((tl.getX() + tr.getX() + br.getX() + bl.getX()) / 4), ((tl.getY() + tr.getY() + br.getY() + bl.getY()) / 4));
	}

	private double getScale(Point middle, Point tl, Point tr, Point br, Point bl) {
		double mx = middle.getX();
		double my = middle.getY();
		double xwidth = Math.min((mx - tl.getX() + mx - bl.getX()) / 2, (tr.getX() - mx + br.getX() - mx) / 2);
		double ywidth = Math.min((my - bl.getY() + my - br.getY()) / 2, (tl.getY() - my + tr.getY() - my) / 2);
		return Math.min(xwidth, ywidth)/* / Math.sqrt(2)*/;//TODO verify
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouse = e;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet.");
	}
}
