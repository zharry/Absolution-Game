package Handler;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameObject.GameObject;
import GameObject.GameObjectHelper;
import GameObject.GameObjectRegistry;
import GameObject.Player;
import MainGame.AbsolutionGame;

public class BuilderHandler extends JPanel {

	public GameHandler gameHandler;
	private GameInfo gameInfo;
	private AbsolutionGame mainReference;

	JTextField IDSelect;
	int selectedID;
	JCheckBox gridLockCheck;
	boolean gridLock;
	private int gridLockX = 0, gridLockY = 0; // If it's not gridlocked, it's
												// the same as mouseX and mouseY
	JTextField rotateSelect;
	int rotateDegs;

	public BuilderHandler(GameInfo gameInfo, AbsolutionGame mainReference, GameHandler gameHandler) {
		this.gameInfo = gameInfo;
		this.gameHandler = gameHandler;

		setSize(GameInfo.builderWidth, GameInfo.height);
		setPreferredSize(new Dimension(GameInfo.builderWidth, GameInfo.height));

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
		add(selectLabel);
		add(IDSelect);

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
		add(gridLockCheck);

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
		add(rotateLabel);
		add(rotateSelect);

		// Save Button
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileOutputStream fileOut = new FileOutputStream("save.ser");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(mainReference.handler);
					out.close();
					fileOut.close();
					System.out.println("Saved to save.ser");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		add(saveButton);

		// Load Button
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileInputStream fileIn = new FileInputStream("save.ser");
					ObjectInputStream in = new ObjectInputStream(fileIn);
					mainReference.reloadHandler((GameHandler) in.readObject());
					// TODO Fix references
					in.close();
					fileIn.close();
					System.out.println("Loaded from save.ser");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		add(loadButton);
	}

	public void render(Graphics g) {
		// Render New GameObject Placement
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		BufferedImage toDraw = null;
		switch (selectedID) {
		case GameObjectRegistry.PLAYER:
			toDraw = GameObjectHelper.sprPlayer[rotateDegs];
			break;
		}
		g.drawImage(toDraw, gridLockX, gridLockY, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	public void tick() {

	}

	public void setGridLockPos() {
		gridLockX = gameInfo.getMouseX();
		gridLockY = gameInfo.getMouseY();
		if (gridLock) {
			gridLockX = (gameInfo.getMouseX() / 16) * 16;
			gridLockY = (gameInfo.getMouseY() / 16) * 16;
		}
	}

	public void incRotate() {
		setBuilderRotateDegs((rotateDegs + 1) % 360);
	}

	public void decRotate() {
		setBuilderRotateDegs((rotateDegs + 360 - 1) % 360);
	}

	public void setBuilderRotateDegs(int rotate) {
		rotateDegs = rotate;
		rotateSelect.setText(rotate + "");
	}

	public void addNewGameObject() {
		switch (selectedID) {
		case GameObjectRegistry.PLAYER:
			gameHandler.addGameObject(new Player(gridLockX, gridLockY, rotateDegs, 32, 32));
		}
	}

	public void dragAddNewGameObject() {
		// TODO Change to template based object creation
		ArrayList<GameObject> inCol = gameHandler
				.checkCollisionWith(new Player(gridLockX, gridLockY, rotateDegs, 32, 32));
		if (inCol.size() == 0)
			addNewGameObject();
	}

	public void removeGameObject() {
		// TODO Change to point-rect collision
		ArrayList<GameObject> inCol = gameHandler
				.checkCollisionWith(new Player(gridLockX, gridLockY, rotateDegs, 1, 1));
		for (GameObject obj : inCol)
			gameHandler.removeGameObject(obj);
	}

}
