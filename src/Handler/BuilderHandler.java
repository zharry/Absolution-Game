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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GameObject.CheckPoint;
import GameObject.GameObject;
import GameObject.Player;
import GameObject.PointColObj;
import GameObject.Tile;
import MainGame.AbsolutionGame;
import MainGame.DropdownItem;
import MainGame.DropdownSelect;
import MainGame.Map;

public class BuilderHandler extends JPanel {

	private static final long serialVersionUID = -1825075841239949426L;

	private GameHandler gameHandler;
	private GameInfo gameInfo;

	DropdownSelect IDSelect;
	int selectedID;
	JCheckBox gridLockCheck, gridLockShow;
	JComboBox<String> difficultySelect;
	int difficulty = 0;
	boolean gridLock, showGrid;
	private int gridLockX = 0, gridLockY = 0; // If it's not gridlocked, it's
												// the same as mouseX and mouseY
	JTextField variationSelect;
	int selectedVar;

	public BuilderHandler(GameInfo gameInfo, AbsolutionGame game) {
		this.gameInfo = gameInfo;

		setLayout(new FlowLayout(FlowLayout.LEFT, 7, 10));
		setSize(GameInfo.builderWidth, GameInfo.height);
		setPreferredSize(new Dimension(GameInfo.builderWidth, GameInfo.height));

		// GameOjbect Selection
		JLabel selectLabel = new JLabel("Create GameObject: ");
		IDSelect = new DropdownSelect();
		IDSelect.setPreferredSize(new Dimension(200, 40));
		IDSelect.setEditable(false);
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
				new DropdownItem(GameObjectRegistry.TILE_BASE, "Edge", GameObjectAssets.base[0]),
				new DropdownItem(GameObjectRegistry.POINT_START, "Start Point", GameObjectAssets.pointStart),
				new DropdownItem(GameObjectRegistry.POINT_END, "End Point", GameObjectAssets.pointEnd) });
		IDSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedID = ((DropdownItem) IDSelect.getSelectedItem()).val;
				setBuilderVar(0);
			}
		});
		add(selectLabel);
		add(IDSelect);
		// Default ID is Floor
		selectedID = GameObjectRegistry.TILE_FLOOR;

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

		// Difficulty Chooser
		JLabel difficultyText = new JLabel("Map Difficulty: ");
		difficultySelect = new JComboBox<String>(
				new String[] { "Easy (Farming Level)", "Medium (Trials Map)", "Hard (Elimination)" });
		difficultySelect.setPreferredSize(new Dimension(200, 30));
		difficultySelect.setEditable(false);
		difficultySelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (difficultySelect.getSelectedItem().equals("Easy (Farming Level)")) {
					difficulty = 0;
				} else if (difficultySelect.getSelectedItem().equals("Medium (Trials Map)")) {
					difficulty = 1;
				} else {
					difficulty = 2;
				}
			}
		});
		add(difficultyText);
		add(difficultySelect);

		// Save Button
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser c = new JFileChooser(System.getProperty("user.dir") + File.separator + "Maps");
					c.setDialogTitle("Save to File");
					c.setApproveButtonText("Save");
					int cv = c.showSaveDialog(null);
					if (cv == JFileChooser.APPROVE_OPTION) {
						// Generate Map Object from GameHandler
						Map map = new Map(difficulty);
						try {
							map.importFromHandler(gameHandler);

							// Export Map
							String filepath = c.getCurrentDirectory().toString() + "" + File.separator + ""
									+ c.getSelectedFile().getName();
							FileOutputStream fileOut = new FileOutputStream(filepath);
							ObjectOutputStream out = new ObjectOutputStream(fileOut);
							out.writeObject(map);
							out.close();
							fileOut.close();
							System.out.println("Map Saved to " + filepath);
						} catch (Exception ex) {
							System.out.println("Start/End Point Mismatch Error");
							Helper.displayError("There must be exactly one Start and one End point in the map!");
						}
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
					JFileChooser c = new JFileChooser(System.getProperty("user.dir") + File.separator + "Maps");
					c.setDialogTitle("Select Map to Edit");
					c.setApproveButtonText("Load");
					int cv = c.showSaveDialog(null);
					if (cv == JFileChooser.APPROVE_OPTION) {
						String filepath = c.getCurrentDirectory().toString() + "" + File.separator + ""
								+ c.getSelectedFile().getName();
						FileInputStream fileIn = new FileInputStream(filepath);
						ObjectInputStream in = new ObjectInputStream(fileIn);

						// Import Map
						Map m = (Map) in.readObject();
						gameHandler.importFromMap(m, true);
						difficultySelect.setSelectedIndex(m.difficulty());
						difficulty = m.difficulty();

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

	public void setGameHandler(GameHandler gameHandler) {
		this.gameHandler = gameHandler;
	}

	public void render(Graphics g) {
		// Render New GameObject Placement
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		g.drawImage(GameObjectAssets.idToAsset(selectedID, selectedVar), gridLockX, gridLockY, null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		// Render Start and End Points
		for (GameObject go : gameHandler.gameObjects()) {
			if (go instanceof CheckPoint) {
				CheckPoint cp = (CheckPoint) go;
				if (cp.isStart())
					g.drawImage(GameObjectAssets.pointStart, cp.getPos()[0], cp.getPos()[1], null);
				else
					g.drawImage(GameObjectAssets.pointEnd, cp.getPos()[0], cp.getPos()[1], null);
			}
		}
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
			gameHandler.addGameObject(new Player(gridLockX, gridLockY));
			break;
		case GameObjectRegistry.POINT_START:
			gameHandler.addGameObject(new CheckPoint(gridLockX, gridLockY, true));
			break;
		case GameObjectRegistry.POINT_END:
			gameHandler.addGameObject(new CheckPoint(gridLockX, gridLockY, false));
			break;
		default:
			gameHandler.addGameObject(new Tile(gridLockX, gridLockY, selectedID, selectedVar));
		}
		setBuilderVar(0);
	}

	public void dragAddNewGameObject() {
		// TODO Change to template based object creation
		ArrayList<GameObject> inCol = gameHandler.checkSprOverlap(new PointColObj(gridLockX, gridLockY));
		if (inCol.size() == 0)
			addNewGameObject();
	}

	public void removeGameObject() {
		ArrayList<GameObject> inCol = gameHandler.checkSprOverlap(new PointColObj(gridLockX, gridLockY));
		for (GameObject obj : inCol)
			gameHandler.removeGameObject(obj);
	}

}
