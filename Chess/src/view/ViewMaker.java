package view;

import com.sun.opengl.util.Animator;
import game.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCanvas;
import javax.swing.JPanel;

/**
 *
 * @author Robbert
 */
public class ViewMaker {
        
    private final int WIDTH = 1200, HEIGHT = 900;
    
    private GLCanvas canvas;   
    private View view; 
    private Game game;
	private LogPanel logPanel;
	
    public ViewMaker(Game game) {
        this.game = game;		
    }
	
	public void start() {
		Frame frame = new Frame("Chess");
        canvas = new GLCanvas();
				
        view = new View(game);		        
		canvas.addGLEventListener(view);  
		canvas.addMouseListener(view);
		
		frame.setSize(WIDTH, HEIGHT);
		this.logPanel = new view.LogPanel();
		logPanel.setPreferredSize(new Dimension(1200, 100));
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(logPanel, BorderLayout.SOUTH);
		
		
		
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
	
	public void log(String text) {
		logPanel.log(text);
	}
}
