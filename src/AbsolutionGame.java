import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Harry
 * 11/10/2017
 * For: Ms.S
 * ICS4U1-02
 */

public class AbsolutionGame extends JFrame implements MouseMotionListener, MouseListener, KeyListener {

	// Constants
	private static final long serialVersionUID = 1687245374428417477L;
	static final String ASSETSDIR = "Assets/", VERSION = "v0.1";
	boolean DEBUG = false;

	// Panel Variables
	Container contentPane;
	GameHandler handler;
	JPanel gamePanel;
	Helper helper;

	// Game Variables
	int height = 640, width = 1040, gameWidth = 1040, builderWidth = 214;
	boolean running, mouseDragging = false;
	int fps, fpsProc = 0, tps = 60, curTps, mouseX = 0, mouseY = 0, mouseButton = MouseEvent.NOBUTTON;
	int gridLockX = 0, gridLockY = 0; // If it's not gridlocked, it's the same
										// as mouseX and mouseY
	AbsolutionGame thisGame;

	// World Builder Variables
	boolean isBuilder;
	JPanel builderPanel;
	BuilderHandler builderHandler;
	JTextField IDSelect;
	int selectedID;
	JCheckBox gridLockCheck;
	boolean gridLock;
	JTextField rotateSelect;
	int rotateDegs;

	public AbsolutionGame(String s, boolean worldBuilder, boolean debug) {
		super(s);

		thisGame = this;
		DEBUG = debug;
		isBuilder = worldBuilder;
		helper = new Helper(thisGame);
		handler = new GameHandler(thisGame);

		if (worldBuilder) {
			// Initialize World Builder Variables
			width += builderWidth;
			builderHandler = new BuilderHandler(thisGame, handler);

			// World Builder JPanel
			builderPanel = new JPanel();
			builderPanel.setSize(builderWidth, height);
			builderPanel.setPreferredSize(new Dimension(builderWidth, height));

			// Create GameObject ID Selection
			JLabel selectLabel = new JLabel("GameObject ID: ");
			IDSelect = new JTextField(5);
			IDSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						selectedID = Integer.parseInt(IDSelect.getText());
					} catch (Exception ex) {
					}
				}
			});
			builderPanel.add(selectLabel);
			builderPanel.add(IDSelect);

			// Lock Builder to 16x16 Grid
			gridLockCheck = new JCheckBox("Lock to 16x16 Grid");
			gridLockCheck.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						gridLock = gridLockCheck.isSelected();
					} catch (Exception ex) {
					}
				}
			});
			builderPanel.add(gridLockCheck);

			// Object Creation with Rotation
			JLabel rotateLabel = new JLabel("Rotation: (CW From North)");
			rotateSelect = new JTextField(15);
			rotateSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						rotateDegs = Integer.parseInt(rotateSelect.getText());
						if (rotateDegs < 0 || rotateDegs > 360) {
							rotateDegs = 0;
							throw new Exception();
						}
					} catch (Exception ex) {
					}
				}
			});
			builderPanel.add(rotateLabel);
			builderPanel.add(rotateSelect);

			// Save Button
			JButton saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						FileOutputStream fileOut = new FileOutputStream("save.ser");
						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(handler);
						out.close();
						fileOut.close();
						System.out.println("Saved to save.ser");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			builderPanel.add(saveButton);

			// Load Button
			JButton loadButton = new JButton("Load");
			loadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						FileInputStream fileIn = new FileInputStream("save.ser");
						ObjectInputStream in = new ObjectInputStream(fileIn);
						handler = (GameHandler) in.readObject();
						// TODO Fix references
						builderHandler = new BuilderHandler(thisGame, handler);
						in.close();
						fileIn.close();
						System.out.println("Loaded from save.ser");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			builderPanel.add(loadButton);
		}

		// Initialize Sprites
		initSpirtes();

		// Initialize JFrame
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		// Initialize JPanel for Game Display
		gamePanel = new JPanel() {
			private static final long serialVersionUID = 4719297929190470247L;

			@Override
			public void paint(Graphics g) {
				// Reset Frame
				g.setColor(new Color(0xC0C0C0));
				g.fillRect(0, 0, width, height);
				// Render Game
				handler.render(g);
				if (isBuilder) {
					builderHandler.render(g);
					helper.drawBuilderGrid(g);
				}
				if (DEBUG) {
					helper.drawDebug(g);
					if (isBuilder)
						helper.drawBuilderDebug(g);
					helper.reset();
				}
				fpsProc++;
			}
		};
		gamePanel.addMouseListener(this);
		gamePanel.addMouseMotionListener(this);
		gamePanel.addKeyListener(this);

		// Finish JFrame and Game Panel
		gamePanel.setSize(gameWidth, height);
		gamePanel.setPreferredSize(new Dimension(gameWidth, height));
		contentPane.add(gamePanel, BorderLayout.CENTER);
		if (isBuilder)
			contentPane.add(builderPanel, BorderLayout.EAST);
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
				if (!isBuilder)
					handler.tick();
				else
					builderHandler.tick();
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

	// Game Assets
	static BufferedImage[] sprPlayer = new BufferedImage[361];

	public void initSpirtes() {
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
		// Debug Mode Selection
		int debug = JOptionPane.showOptionDialog(null, "Print Debug Information on-screen?", "Absolution " + VERSION,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "Yes", "No" }, "Yes");

		// Launcher
		int select = JOptionPane.showOptionDialog(null, "Welcome to Absolution!", "Absolution " + VERSION,
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				new Object[] { "Start Challenge", "Create World", "Highscores (Official Maps)" }, "Start Challenge");

		switch (select) {
		case 0: // Start Challenge
			new AbsolutionGame("Absolution " + VERSION, false, debug == 0);
			break;
		case 1: // Create World
			new AbsolutionGame("Absolution " + VERSION + " | World Builder", true, debug == 0);
			break;
		case 2: // View Highscores
		default:
			// Close Launcher
			System.exit(0);
		}
	}

	// Listeners
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseDragging = true;
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gridLockX = (mouseX = e.getX());
		gridLockY = (mouseY = e.getY());
		if (gridLock) {
			gridLockX = (mouseX / 16) * 16;
			gridLockY = (mouseY / 16) * 16;
		}
		if (isBuilder)
			if (mouseDragging && mouseButton == MouseEvent.BUTTON1)
				builderHandler.dragAddNewGameObject();
			else if (mouseDragging && mouseButton == MouseEvent.BUTTON3)
				builderHandler.removeGameObject();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		if (isBuilder)
			if (mouseButton == MouseEvent.BUTTON1)
				builderHandler.addNewGameObject();
			else if (mouseButton == MouseEvent.BUTTON3)
				builderHandler.removeGameObject();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseButton = MouseEvent.NOBUTTON;
		mouseDragging = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if (isBuilder)
			switch (k) {
			case KeyEvent.VK_O:
				helper.setBuilderRotateDegs((rotateDegs + 1) % 360);
				break;
			case KeyEvent.VK_P:
				helper.setBuilderRotateDegs((rotateDegs + 360 - 1) % 360);
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
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
