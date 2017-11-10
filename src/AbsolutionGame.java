import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Harry
 * 11/10/2017
 * For: Ms.S
 * ICS4U1-02
 */

public class AbsolutionGame extends JFrame implements MouseMotionListener, MouseListener, KeyListener {

	// Constants
	private static final long serialVersionUID = 1687245374428417477L;
	final static String ASSETSDIR = "Assets/", VERSION = "v0.1";
	final boolean DEBUG = true;

	// Panel Variables
	GameHandler handler;
	JPanel gamePanel;

	// Game Variables
	int height = 600, width = 1066;
	boolean running;
	static int fps, fpsProc = 0, tps = 60, curTps;

	public AbsolutionGame() {
		super("Absolution " + VERSION);

		// Initialize Sprites
		initSpirtes();

		// Initialize JFrame
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		// Initialize JPanel for Game Display
		handler = new GameHandler();
		gamePanel = new JPanel() {
			private static final long serialVersionUID = 4719297929190470247L;

			@Override
			public void paint(Graphics g) {
				// Reset Frame
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, width, height);
				// Render Game
				handler.render(g);
				if (DEBUG)
					Helper.drawDebug(g);
				fpsProc++;
			}
		};
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		// Finish JFrame and JPanel
		this.add(gamePanel);
		this.setVisible(true);

		// Game Loop
		running = true;
		long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
		double ns = 1000000000 / (double) tps, delta = 0;
		int tpsProc = 0;
		while (running) {
			long curTime = System.nanoTime();
			delta += (curTime - lastTime) / ns;
			lastTime = curTime;
			while (delta >= 1) {
				// Process Game Changes
				handler.tick();
				tpsProc++;
				delta--;
			}
			// Update the Graphics
			gamePanel.repaint();
			// Display FPS and TPS
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = fpsProc;
				curTps = tpsProc;
				fpsProc = 0;
				tpsProc = 0;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}

	}

	static BufferedImage[] sprPlayer = new BufferedImage[361];

	public static void initSpirtes() {
		// Load Sprites
		try {
			sprPlayer[0] = ImageIO.read(new File(ASSETSDIR + "Player.png"));
		} catch (IOException e) {
			Helper.displayError("Failed to load Game Assets, please re-download the game and try again!");
			System.exit(-1);
		}
		// Calculate Rotations
		AffineTransformOp op;
		for (int i = 1; i < 361; i++) {
			op = new AffineTransformOp(AffineTransform.getRotateInstance(Math.toRadians(i), sprPlayer[0].getWidth() / 2,
					sprPlayer[0].getHeight() / 2), AffineTransformOp.TYPE_BILINEAR);
			sprPlayer[i] = op.filter(sprPlayer[0], null);
		}
	}

	public static void main(String[] args) {
		new AbsolutionGame();
	}

	// Listeners
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	// Unused Methods
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
