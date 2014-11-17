package view;

import board.Board;
import com.sun.opengl.util.Animator;
import game.Game;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCanvas;

/**
 *
 * @author Robbert
 */
public class ViewMaker {
        
    private final int WIDTH = 1200, HEIGHT = 900;
    
    private GLCanvas canvas;   
    private View view; 
    private Game game;
	
    public ViewMaker(Game game) {
        this.game = game;
		Frame frame = new Frame("Chess");
        canvas = new GLCanvas();
		
		
        view = new View(game);		        
		canvas.addGLEventListener(view);  
		canvas.addMouseListener(view);
        frame.add(canvas);
        frame.setSize(WIDTH, HEIGHT);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }
    
    public void drawBoard() {        
        view.update(canvas);        
    }
}
