package Handler;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameObject.GameObject;
import GameObject.GameObjectAssets;
import GameObject.GameObjectRegistry;
import GameObject.Player;
import GameObject.PointColObj;
import GameObject.Tile;
import MainGame.AbsolutionGame;
import MainGame.DropdownItem;
import MainGame.DropdownSelect;

public class BuilderHandler extends JPanel {

	public GameHandler gameHandler;
	private GameInfo gameInfo;
	private AbsolutionGame mainReference;

	JTextField IDSelect;
	int selectedID;
	JCheckBox gridLockCheck, gridLockShow;
	boolean gridLock, showGrid;
	private int gridLockX = 0, gridLockY = 0; // If it's not gridlocked, it's
												// the same as mouseX and mouseY
	JTextField variationSelect;
	int selectedVar;

	public BuilderHandler(GameInfo gameInfo, AbsolutionGame mainReference, GameHandler gameHandler) {
		this.gameInfo = gameInfo;
		this.gameHandler = gameHandler;

		setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
		setSize(GameInfo.builderWidth, GameInfo.height);
		setPreferredSize(new Dimension(GameInfo.builderWidth, GameInfo.height));

		// GameOjbect Selection
		JLabel selectLabel = new JLabel("Create GameObject: ");
		DropdownSelect IDSelect = new DropdownSelect();
		IDSelect.setPreferredSize(new Dimension(200, 30));
		IDSelect.setEditable(true);
		IDSelect.addItems(new DropdownItem[] {
				new DropdownItem(GameObjectRegistry.TILE_FLOOR, "Floor", GameObjectAssets.floor[1]),
				new DropdownItem(GameObjectRegistry.TILE_WATER, "Water", GameObjectAssets.water[0]),
				new DropdownItem(GameObjectRegistry.TILE_BORDER, "Border", GameObjectAssets.border[30]),
				new DropdownItem(GameObjectRegistry.TILE_BORDERWATER, "Border with Water",
						GameObjectAssets.borderWater[0]),
				new DropdownItem(GameObjectRegistry.TILE_DOOR, "Door", GameObjectAssets.door[2]),
				new DropdownItem(GameObjectRegistry.TILE_SEWER, "Sewer", GameObjectAssets.sewer[2]),
				new DropdownItem(GameObjectRegistry.TILE_WATERFALL, "Waterfall", GameObjectAssets.waterfall[0]),
				new DropdownItem(GameObjectRegistry.TILE_STAIRS, "Stairs", GameObjectAssets.stairs[0]),
				new DropdownItem(GameObjectRegistry.TILE_WALL, "Wall", GameObjectAssets.wall[0]),
				new DropdownItem(GameObjectRegistry.TILE_BASE, "Edge", GameObjectAssets.base[0]), });
		IDSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedID = ((DropdownItem) IDSelect.getSelectedItem()).val;
				setBuilderVar(0);
			}
		});
		add(selectLabel);
		add(IDSelect);
		// Default ID is 11
		selectedID = 11;

		// Object Creation with Variations
		JLabel rotateLabel = new JLabel("Variation ID: ");
		variationSelect = new JTextField(11);
		variationSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedVar = Integer.parseInt(variationSelect.getText());
				} catch (Exception ex) {
				}
			}
		});
		add(rotateLabel);
		add(variationSelect);
		setBuilderVar(0);

		// Lock Builder to 16x16 Grid
		gridLockCheck = new JCheckBox("Lock to Grid");
		gridLockCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gridLock = gridLockCheck.isSelected();
					if (!gridLock) {
						gridLockShow.setSelected(false);
						showGrid = false;
					} else {
						gridLockShow.setSelected(true);
						showGrid = true;
					}
				} catch (Exception ex) {
				}
			}
		});
		add(gridLockCheck);
		gridLockCheck.setSelected(true);
		gridLock = true;

		// Lock Builder to 16x16 Grid
		gridLockShow = new JCheckBox("Show Grid");
		gridLockShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showGrid = gridLockShow.isSelected();
				} catch (Exception ex) {
				}
			}
		});
		add(gridLockShow);
		gridLockShow.setSelected(true);
		showGrid = true;

		// Save Button
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser c = new JFileChooser(System.getProperty("user.dir"));
					c.setDialogTitle("Save to File");
					c.setApproveButtonText("Save");
					int cv = c.showSaveDialog(null);
					if (cv == JFileChooser.APPROVE_OPTION) {
						String filepath = c.getCurrentDirectory().toString() + "" + File.separator + ""
								+ c.getSelectedFile().getName();
						FileOutputStream fileOut = new FileOutputStream(filepath);
						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(mainReference.handler);
						out.close();
						fileOut.close();
						System.out.println("Saved to " + filepath);
					}

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
					JFileChooser c = new JFileChooser(System.getProperty("user.dir"));
					c.setDialogTitle("Select Map to Edit");
					c.setApproveButtonText("Load");
					int cv = c.showSaveDialog(null);
					if (cv == JFileChooser.APPROVE_OPTION) {
						String filepath = c.getCurrentDirectory().toString() + "" + File.separator + ""
								+ c.getSelectedFile().getName();
						FileInputStream fileIn = new FileInputStream(filepath);
						ObjectInputStream in = new ObjectInputStream(fileIn);
						mainReference.reloadHandler((GameHandler) in.readObject());
						in.close();
						fileIn.close();
						System.out.println("Loaded from " + filepath);
					}
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
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		g.drawImage(GameObjectAssets.idToAsset(selectedID, selectedVar), gridLockX, gridLockY, null);
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

	public void incVar() {
		setBuilderVar(selectedVar + 1);
	}

	public void decVar() {
		setBuilderVar(selectedVar - 1);
	}

	public void setBuilderVar(int var) {
		selectedVar = var;
		variationSelect.setText(var + "");
	}

	public void addNewGameObject() {
		switch (selectedID) {
		case GameObjectRegistry.PLAYER:
			gameHandler.addGameObject(new Player(gridLockX, gridLockY, 32, 32));
			break;
		default:
			gameHandler.addGameObject(new Tile(gridLockX, gridLockY, selectedID, selectedVar));
		}
		setBuilderVar(0);
	}

	public void dragAddNewGameObject() {
		// TODO Change to template based object creation
		ArrayList<GameObject> inCol = gameHandler.checkSprOverlap(new Player(gridLockX, gridLockY, 32, 32));
		if (inCol.size() == 0)
			addNewGameObject();
	}

	public void removeGameObject() {
		ArrayList<GameObject> inCol = gameHandler.checkSprOverlap(new PointColObj(gridLockX, gridLockY, 1, 1));
		for (GameObject obj : inCol)
			gameHandler.removeGameObject(obj);
	}

}
