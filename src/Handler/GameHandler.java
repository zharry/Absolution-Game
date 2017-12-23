package Handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

import Game.AbsolutionGame;
import Game.Map;
import GameObject.CheckPoint;
import GameObject.GameObject;
import GameObject.LineColObjHor;
import GameObject.LineColObjVert;
import GameObject.Player;
import GameObject.PointColObj;
import GameObject.SkillDrop;
import GameObject.Tile;

public class GameHandler {

	private Random r = new Random();
	private AbsolutionGame mainRef;
	private GameInfo gameInfo;

	private int[] spawn;
	private boolean ui;

	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ArrayList<String> easyMaps = new ArrayList<String>();
	private ArrayList<String> medMaps = new ArrayList<String>();
	private ArrayList<String> hardMaps = new ArrayList<String>();
	private ArrayList<String> allMaps = new ArrayList<String>();

	private CheckPoint lastEnd;
	private int stage;

	public GameHandler(GameInfo g, AbsolutionGame main) {
		this.mainRef = main;
		this.gameInfo = g;
		this.stage = 0;
		this.ui = true;
	}

	public ArrayList<GameObject> gameObjects() {
		return this.gameObjects;
	}

	public void replaceMap(Map m) {
		gameObjects = m.gameObjects();
	}

	public void addMap(Map m) {
		String mapName = m.getName();
		allMaps.add(mapName);
		switch (m.difficulty()) {
		case 0:
			easyMaps.add(mapName);
			break;
		case 1:
			medMaps.add(mapName);
			break;
		default:
			hardMaps.add(mapName);
		}
	}

	public void loadNextMap() {
		stage++;

		// Generate Next Map
		// TODO Re-add level difficulty scaling
		String mapName = allMaps.get(r.nextInt(allMaps.size()));
		System.out.println("Stage: " + stage);
		System.out.println("Next Map: " + mapName);

		// Re-fetch Map File
		FileInputStream fileIn;
		Map map = null;
		try {
			fileIn = new FileInputStream("Maps" + File.separator + mapName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Load map into GameObjects
		if (stage == 1) {
			gameObjects = map.gameObjects();
			lastEnd = map.getPoints().get(1);
			for (GameObject go : map.gameObjects())
				if (go instanceof CheckPoint)
					if (((CheckPoint) go).isStart()) {
						spawn = go.getPos();
						spawn[1] -= 16;
					}
		} else {
			CheckPoint start = map.getPoints().get(0), end = map.getPoints().get(1);
			for (GameObject go : map.gameObjects()) {
				if (!(go instanceof CheckPoint))
					go.setPos(new int[] { go.getPos()[0] - start.getPos()[0] + lastEnd.getPos()[0],
							go.getPos()[1] - start.getPos()[1] + lastEnd.getPos()[1] });
				gameObjects.add(go);
			}
			lastEnd.setPos(new int[] { end.getPos()[0] - start.getPos()[0] + lastEnd.getPos()[0],
					end.getPos()[1] - start.getPos()[1] + lastEnd.getPos()[1] });
		}
	}

	public int[] getSpawn() {
		return spawn;
	}

	public void toggleUI() {
		ui = !ui;
	}

	public void render(Graphics g) {
		for (GameObject object : gameObjects)
			if (!(object instanceof Player))
				object.render(g);
		mainRef.player().render(g);
	}

	@SuppressWarnings("static-access")
	public void renderIU(Graphics g) {
		// Render UI
		if (ui) {
			g.drawImage(GameObjectAssets.skillBar, gameInfo.width / 2 - 360 / 2, gameInfo.height - 80, null);
			int dX = 314, dY = 573, xInc = 44, skill = 0;
			for (; skill < 6; skill++) {
				g.setColor(Color.WHITE);
				dX += xInc;
				if (mainRef.player().getInv()[skill] != 0)
					g.drawString(mainRef.player().getInv()[skill] + "", dX, dY);
			}
			if (mainRef.player().getInv()[skill] != 0)
				g.drawString(mainRef.player().getInv()[skill] + "", dX += 72, dY);
		}

		// Draw Score counter
		g.setColor(Color.WHITE);
		Font def = g.getFont();
		g.setFont(def.deriveFont(18.0f));
		g.drawString("Score: " + (stage - 3), 780, 585);
		g.setFont(def);
	}

	public void tick() {
		// Generate Map as player moves
		if (mainRef.player().getPos()[1] <= lastEnd.getPos()[1] + 2000)
			loadNextMap();

		for (GameObject go : gameObjects)
			go.tick();

		// Check Player Collision
		// Top-Left
		PointColObj cTL = new PointColObj(mainRef.player().getPos()[0] + mainRef.player().getCol()[2],
				mainRef.player().getPos()[1] + mainRef.player().getCol()[3]);
		// Top-Right
		PointColObj cTR = new PointColObj(mainRef.player().getPos()[0] + mainRef.player().getCol()[2],
				mainRef.player().getPos()[1] + mainRef.player().getCol()[3] + mainRef.player().getCol()[1]);
		// Bottom-Left
		PointColObj cBL = new PointColObj(
				mainRef.player().getPos()[0] + mainRef.player().getCol()[2] + mainRef.player().getCol()[0],
				mainRef.player().getPos()[1] + mainRef.player().getCol()[3]);
		// Bottom-Right
		PointColObj cBR = new PointColObj(
				mainRef.player().getPos()[0] + mainRef.player().getCol()[2] + mainRef.player().getCol()[0],
				mainRef.player().getPos()[1] + mainRef.player().getCol()[3] + mainRef.player().getCol()[1]);

		// Inverse Collision, no part of the player's ColBox should be not
		// colliding...
		if (checkCollisionWith(cTL).size() == 0 || checkCollisionWith(cTR).size() == 0
				|| checkCollisionWith(cBL).size() == 0 || checkCollisionWith(cBR).size() == 0) {
			// If it would have left the bounding box of the floor, reverse the
			// player's posiiton
			mainRef.player().setMoveBack(true);
		} else
			mainRef.player().setMoveBack(false);

		// Check General Collision
		ArrayList<GameObject> allCol = checkCollisionWith(mainRef.player());
		for (GameObject go : allCol) {
			if (go instanceof Tile) {
				Tile gameTile = (Tile) go;
				if (gameTile.getType() == GameObjectRegistry.TILE_WALL
						|| gameTile.getType() == GameObjectRegistry.TILE_BASE
						|| gameTile.getType() == GameObjectRegistry.TILE_DOOR
						|| gameTile.getType() == GameObjectRegistry.TILE_SEWER
						|| (gameTile.getType() == GameObjectRegistry.TILE_WATERFALL && gameTile.getVar() == 0)) {
					mainRef.player().setMoveBack(true);
					break;
				}
			} else if (go instanceof LineColObjHor || go instanceof LineColObjVert) {
				mainRef.player().setMoveBack(true);
				break;
			} else if (go instanceof SkillDrop) {
				SkillDrop d = (SkillDrop) go;
				mainRef.player().getInv()[d.getVariation()]++;
				removeGameObject(go);
			}

		}
	}

	public ArrayList<GameObject> checkCollisionWith(GameObject obj) {
		ArrayList<GameObject> inCollision = new ArrayList<GameObject>();
		for (GameObject go : gameObjects)
			if (obj.isCollide(go))
				inCollision.add(go);
		return inCollision;
	}

	public ArrayList<GameObject> checkSprOverlap(GameObject obj) {
		ArrayList<GameObject> inCollision = new ArrayList<GameObject>();
		for (GameObject go : gameObjects)
			if (obj.isSprCollide(go))
				inCollision.add(go);
		return inCollision;
	}

	public void addGameObject(GameObject obj) {
		gameObjects.add(obj);
	}

	public void removeGameObject(GameObject obj) {
		gameObjects.remove(obj);
	}

}
