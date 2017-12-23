package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GameObject.Player;
import Handler.BuilderHandler;
import Handler.GameHandler;
import Handler.GameInfo;
import Handler.GameObjectAssets;
import Handler.Helper;

/*
 * Harry
 * 11/10/2017
 * For: Ms.S
 * ICS4U1-02
 */

public class AbsolutionGame extends JFrame implements MouseMotionListener, MouseListener, KeyListener {

	// Constants
	private static final long serialVersionUID = 1687245374428417477L;
	private static final String VERSION = "v1.1";
	private boolean DEBUG = false;

	// Panel Variables
	private Container contentPane;
	private GameHandler handler;
	private JPanel gamePanel;
	private Helper helper;
	private Object lock;

	// World Builder Variables
	private boolean isBuilder;
	private BuilderHandler builderHandler; // JPanel + Handler Bundle

	// Game Variables
	private GameInfo gameInfo;
	private Player player;

	public Player player() {
		return player;
	}

	public AbsolutionGame(String s, boolean worldBuilder) {
		super(s);
		lock = new Object();
		isBuilder = worldBuilder;

		// Initialize Sprites
		GameObjectAssets.initSprites();

		// Initialize Game Handlers
		gameInfo = new GameInfo();
		helper = new Helper(gameInfo, this);
		handler = new GameHandler(gameInfo, this);
		helper.setGameHandler(handler);

		if (worldBuilder) {
			// Initialize World Builder Variables
			GameInfo.width += GameInfo.builderWidth;
			builderHandler = new BuilderHandler(gameInfo, this, helper);

			// Update Handler References
			builderHandler.setGameHandler(handler);

			// Create Null Player Outside of Map
			player = new Player(-100, -100);
			handler.addGameObject(player);
		} else {
			// Initialize Maps
			File mapsDir = new File("Maps");
			for (File map : mapsDir.listFiles()) {
				try {
					// Load Map
					FileInputStream fileIn = new FileInputStream(map);
					ObjectInputStream in = new ObjectInputStream(fileIn);

					// Import Map File Locations and Templates into Game
					Map m = (Map) in.readObject();
					handler.addMap(m);
					in.close();
					fileIn.close();
					System.out.println(map + " Loaded");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			// Load Starting Maps
			for (int i = 0; i < 2; i++)
				handler.loadNextMap();

			// Create Player
			int[] startLoc = handler.getSpawn();
			System.out.println("Spawn at: " + startLoc[0] + "," + startLoc[1]);
			player = new Player(startLoc[0], startLoc[1]);
			handler.addGameObject(player);
		}

		// Initialize JFrame
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(GameInfo.width, GameInfo.height));
		this.setMaximumSize(new Dimension(GameInfo.width, GameInfo.height));
		this.setMinimumSize(new Dimension(GameInfo.width, GameInfo.height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		// Initialize JPanel for Game Display
		gamePanel = new JPanel() {
			private static final long serialVersionUID = 4719297929190470247L;

			@SuppressWarnings("static-access")
			@Override
			public void paint(Graphics g) {
				synchronized (lock) {

					// Reset Frame
					g.setColor(new Color(0x211E27));
					g.fillRect(0, 0, GameInfo.width, GameInfo.height);

					// Move Camera to Player
					if (!isBuilder)
						g.translate(-player.getPos()[0] + gameInfo.width / 2,
								-player.getPos()[1] + gameInfo.height / 2);

					// Render Game
					handler.render(g);
					if (isBuilder) {
						builderHandler.render(g);
						builderHandler.drawBuilderGrid(g);
					}

					// Reset Camera
					if (!isBuilder) {
						g.translate(player.getPos()[0] - gameInfo.width / 2, player.getPos()[1] - gameInfo.height / 2);
						// Render UI
						handler.renderIU(g);
					}

					// Render Debug
					if (DEBUG) {
						helper.drawDebug(g);
						if (isBuilder)
							builderHandler.drawBuilderDebug(g);
						else {
							helper.drawPlayerDebug(g);
							helper.drawDebugColBox(g);
						}
						helper.reset();
					}

					gameInfo.setFPSProc(gameInfo.getFPSProc() + 1);

					// If the Game is over
					if (gameInfo.isGameOver()) {
						g.setColor(Color.GRAY);
						g.fillRect(0, 0, gameInfo.width, gameInfo.height);
						g.setColor(Color.BLACK);
						g.drawString("Game Over!", gameInfo.width / 2, gameInfo.height / 2);
					}
				}
			}
		};
		gamePanel.addMouseListener(this);
		gamePanel.addMouseMotionListener(this);
		gamePanel.addKeyListener(this);

		// Finish JFrame and Game Panel
		gamePanel.setSize(GameInfo.gameWidth, GameInfo.height);
		gamePanel.setPreferredSize(new Dimension(GameInfo.gameWidth, GameInfo.height));
		contentPane.add(gamePanel, BorderLayout.CENTER);
		if (isBuilder)
			contentPane.add(builderHandler, BorderLayout.EAST);
		this.setVisible(true);

		// Game Loop
		gameInfo.setState(true);
		long lastTime = System.nanoTime(), timer = System.currentTimeMillis();
		double ns = 1000000000 / (double) gameInfo.getTargetTPS(), delta = 0;
		int tpsProc = 0;
		while (gameInfo.getState()) {
			long curTime = System.nanoTime();
			delta += (curTime - lastTime) / ns;
			lastTime = curTime;
			synchronized (lock) {
				while (delta >= 1) {
					// Process Game Changes
					if (!isBuilder)
						handler.tick();
					else
						builderHandler.tick();
					tpsProc++;
					delta--;
				}
			}
			// Update the Graphics
			gamePanel.repaint();
			// Display FPS and TPS
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				gameInfo.setFPS(gameInfo.getFPSProc());
				gameInfo.setCurTPS(tpsProc);
				gameInfo.setFPSProc(0);
				tpsProc = 0;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}

	}

	public static void main(String[] args) {

		// Launcher
		int select = JOptionPane.showOptionDialog(null, "Welcome to Absolution!", "Absolution " + VERSION,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				new Object[] { "Start Challenge", "Create World" }, "Start Challenge");

		switch (select) {
		case 0: // Start Challenge
			JOptionPane.showOptionDialog(null,
					"How to play:\n" + "1. Use WASD or UP/DOWN/LEFT/RIGHT to move.\n"
							+ "2. Collect Skills by running into them. \n"
							+ "3. The World is infinite, as it takes all the maps in the Maps folder and places them back to back.\n"
							+ "4. The score is shown on the bottom right corner, it is based on the number 'maps' you've been through. \n"
							+ "5. Press 'I' to toggle the hotbar \n"
							+ "6. Press 'F3' to show/hide debug information and collision boxes.",
					"Info for Absolution " + VERSION, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "Ok" }, "Ok");
			new AbsolutionGame("Absolution " + VERSION, false);
			break;
		case 1: // Create World
			JOptionPane.showOptionDialog(null,
					"How to play:\n" + "1. Select a GameObject in the dropdown.\n"
							+ "2. Use 'O' and 'P' to scroll through different block variations.\n"
							+ "3. Hover over the Left drawing panel to see a preview of the block.\n"
							+ "4. Toggle Grid Locking and grid visibilty with the radio buttons.\n"
							+ "5. Left Click to place down selected block.\n"
							+ "6. Right Click remove blocks surround your cursor.\n"
							+ "7. Each Map must have a 'Start' and 'End' Point. This is where maps will be joined in Challenge Mode.\n"
							+ "8. Press 'F3' to show/hide debug information.",
					"Info for Absolution Map Builder " + VERSION, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
					new Object[] { "Ok" }, "Ok");
			new AbsolutionGame("Absolution " + VERSION + " | World Builder", true);
			break;
		case 2: // View Highscores
			// TODO Highscore for later...
		default:
			// Close Launcher
			System.exit(0);
		}
	}

	// Listeners
	@Override
	public void mouseDragged(MouseEvent e) {
		gameInfo.setMouse(-1, -1, -1, true);
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gameInfo.setMouse(e.getX(), e.getY(), -1, gameInfo.getMouseDragging());
		if (isBuilder) {
			builderHandler.setGridLockPos();
			if (gameInfo.getMouseDragging() && gameInfo.getMouseButton() == MouseEvent.BUTTON1)
				builderHandler.dragAddNewGameObject();
			else if (gameInfo.getMouseDragging() && gameInfo.getMouseButton() == MouseEvent.BUTTON3)
				builderHandler.removeGameObject();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gameInfo.setMouse(-1, -1, e.getButton(), gameInfo.getMouseDragging());
		if (isBuilder)
			if (gameInfo.getMouseButton() == MouseEvent.BUTTON1)
				builderHandler.addNewGameObject();
			else if (gameInfo.getMouseButton() == MouseEvent.BUTTON3)
				builderHandler.removeGameObject();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gameInfo.setMouse(-1, -1, MouseEvent.NOBUTTON, false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == KeyEvent.VK_O)
			if (isBuilder)
				builderHandler.incVar();
		if (k == KeyEvent.VK_P)
			if (isBuilder)
				builderHandler.decVar();
		if (k == KeyEvent.VK_F3)
			DEBUG = !DEBUG;
		if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP)
			player.setMoveUp(true);
		if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN)
			player.setMoveDown(true);
		if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT)
			player.setMoveLeft(true);
		if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT)
			player.setMoveRight(true);
		if (k == KeyEvent.VK_I)
			handler.toggleUI();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP)
			player.setMoveUp(false);
		if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN)
			player.setMoveDown(false);
		if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT)
			player.setMoveLeft(false);
		if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT)
			player.setMoveRight(false);
	}

	public void mouseEntered(MouseEvent e) {
		gamePanel.requestFocus();
	}

	// Unused Methods
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
